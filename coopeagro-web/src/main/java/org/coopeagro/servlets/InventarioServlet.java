/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.ejb.InventarioSessionBeanRemote;
import org.coopeagro.ejb.ProductoSessionBeanRemote;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.Producto;
import org.coopeagro.excepciones.InexistenteException;
import org.coopeagro.serviceLocator.CoopeagroServiceLocator;
import org.json.simple.JSONObject;

/**
 *
 * @author YEISSON
 */
public class InventarioServlet extends HttpServlet {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    @EJB
    private InventarioSessionBeanRemote inventarioBean = null;
    @EJB
    private ProductoSessionBeanRemote productoBean = null;

    public InventarioServlet() {
        Properties props = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CoopeagroServiceLocator.properties");
        if (inputStream != null) {
            try {
                props.load(inputStream);
                CoopeagroServiceLocator cesl = new CoopeagroServiceLocator(props);
                inventarioBean = cesl.<InventarioSessionBeanRemote>getEJBInstance("ejb/InventarioBean");
                productoBean = cesl.<ProductoSessionBeanRemote>getEJBInstance("ejb/ProductoBean");
            } catch (NamingException | IOException ex) {
                Logger.getLogger(InventarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String mensajeExito = "";
        String mensajeError = "";
        String mensajeAlerta = "";
        String redireccion = "jsp/Inventarios.jsp";

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String id = request.getParameter("idInventario");
        String fecha = request.getParameter("fecha");
        String idProducto = request.getParameter("producto");
        String cantidadComprometida = request.getParameter("cantidadComprometida");
        String cantidadTotal = request.getParameter("cantidadTotal");
        //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInventario = null;
        double disponibilidad = 0;
        
        switch (accion) {
            case "guardar":
                mensajeAlerta = validarDatos(fecha, idProducto, cantidadComprometida, cantidadTotal);
                if (mensajeAlerta.isEmpty()) {
                    try {
                        fechaInventario = sdf.parse(fecha);
                    } catch (ParseException ex) {
                        Logger.getLogger(AgricultorServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mensajeError = guardarInventario(id, fechaInventario, idProducto, cantidadComprometida, cantidadTotal);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El registro del inventario ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idInventario", id);
                request.setAttribute("fecha", fecha);
                request.setAttribute("producto", idProducto);
                request.setAttribute("cantidadComprometida", cantidadComprometida);
                request.setAttribute("cantidadTotal", cantidadTotal);
                break;
            case "eliminar":
                if (eliminarInventario(id)) {
                    mensajeExito = "El registro del inventario ha sido eliminado con éxito";
                } else {
                    mensajeError = "El registro del inventario no pudo ser eliminado";
                }
                break;
            case "consultar":
                JSONObject inventario = consultarInventario(id);
                response.getWriter().write(inventario.toString());
                break;
            case "disponibilidad":
                if (!idProducto.equalsIgnoreCase("")) {
                    disponibilidad = disponibilidadProducto(idProducto);
                    if (disponibilidad > 0) {
                        request.setAttribute("disponibilidad", disponibilidad);
                    }else{
                        mensajeError = "No se han encontrado registros de inventario para el producto especificado";
                    }
                }else{
                   mensajeAlerta = "Debe ingresar el campo 'Producto'"; 
                }
                break;
            case "listar":
                listarInventarios(response, fecha, idProducto);
                break;
            case "cantidadTotal":
                JSONObject jsonInventario = new JSONObject();
                if (idProducto == null || idProducto.isEmpty()) {
                    jsonInventario.put("cantidadTotal", "");
                }else{
                    jsonInventario.put("cantidadTotal", disponibilidadProducto(idProducto));
                }
                response.getWriter().write(jsonInventario.toString());
                break;
            default:
                break;
        }
        
        if (!accion.equals("consultar") && !accion.equals("listar") && !accion.equals("cantidadTotal")) {
            request.setAttribute("fecha", sdf.format(new Date()));
            request.setAttribute("productos", obtenerProductos());
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }
    
    private String validarDatos(String fecha, String idProducto, String cantidadComprometida, String cantidadTotal) {
        String validacion = "";
        int cc = 0;
        int ct = 0;
        if (fecha == null || fecha.isEmpty()) {
            validacion += "Debe ingresar el campo 'Fecha' \n";
        }
        if (idProducto == null || idProducto.isEmpty()) {
            validacion += "Debe ingresar el campo 'Producto' \n";
        }
        if (cantidadComprometida == null || cantidadComprometida.isEmpty()) {
            validacion += "Debe ingresar el campo 'Cantidad comprometida' \n";
            cc = 1;
        } else {
            try {
                Double.valueOf(cantidadComprometida);
            } catch (NumberFormatException e) {
                validacion += "El valor a ingresar en el campo 'Cantidad comprometida' debe ser numérico \n";
                cc = 1;
            }
        }
        if (cantidadTotal == null || cantidadTotal.isEmpty()) {
            validacion += "Debe ingresar el campo 'Cantidad total' \n";
            ct = 1;
        } else {
            try {
                Double.valueOf(cantidadComprometida);
            } catch (NumberFormatException e) {
                validacion += "El valor a ingresar en el campo 'Cantidad total' debe ser numérico \n";
                ct = 1;
            }
        }
        if (cc == 0 && ct == 0) {
            if (Double.parseDouble(cantidadComprometida)> Double.parseDouble(cantidadTotal)) {
                validacion += "El valor a ingresar en el campo 'Cantidad comprometida' no puede ser mayor a 'Cantidad total' \n";
            }
        }
        return validacion;
    }

    private boolean eliminarInventario(String id) {
        boolean eliminacion = false;
        //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
        try {
            inventarioBean.destroy(Integer.valueOf(id));
            //inventarioJpaController.destroy(Integer.valueOf(id));
            eliminacion = true;
        //} catch (NumberFormatException | NonexistentEntityException ex) {
        } catch (InexistenteException ex) {
            Logger.getLogger(InventarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //}
        return eliminacion;
    }

    private JSONObject consultarInventario(String id) {
        JSONObject jsonInventario = new JSONObject();
        //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
        Inventario inventario;
        //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        try {
            inventario = inventarioBean.findInventario(Integer.valueOf(id));
            //inventario = inventarioJpaController.findInventario(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            inventario = null;
        }
        if (inventario != null) {
            jsonInventario.put("id", inventario.getId());
            jsonInventario.put("fecha", sdf.format(inventario.getFecha()));
            jsonInventario.put("producto", inventario.getProducto().getId());
            jsonInventario.put("cantidadComprometida", inventario.getCantidadComprometida());
            jsonInventario.put("cantidadTotal", inventario.getCantidadTotal());
        }
        return jsonInventario;
    }

    private void listarInventarios(HttpServletResponse response, String fecha, String idProducto) throws IOException {
        //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
        List<Inventario> listaInventarios = inventarioBean.findInventarioEntities(10, 0);
        //List<Inventario> listaInventarios = inventarioJpaController.findInventarioEntities(10, 0); 
        //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");
        out.println(            "<th>Fecha</th>");
        out.println(            "<th>Producto</th>");
        out.println(            "<th>Comprometida</th>");
        out.println(            "<th>Total</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaInventarios != null && !listaInventarios.isEmpty()){
            for (Inventario inventario : listaInventarios) {
                out.println("<tr>");			
                out.println(    "<td>"+sdf.format(inventario.getFecha())+"</td>");
                out.println(    "<td>"+inventario.getProducto().getNombre()+"</td>");
                out.println(    "<td>"+inventario.getCantidadComprometida()+" "+inventario.getProducto().getUnidadMedida()+"</td>");
                out.println(    "<td>"+inventario.getCantidadTotal()+" "+inventario.getProducto().getUnidadMedida()+"</td>");
                out.println(    "<td>");
                //out.println(        "<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarInventario("+inventario.getId()+");\">Editar</button>");
                //out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idInventario').val('"+inventario.getId()+"');\">Eliminar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"7\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
        out.println("</table>");
    }

    private String guardarInventario(String id, Date fecha, String idProducto, String cantidadComprometida, String cantidadTotal) {
        String error = "";
        //ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        Producto producto;
        producto = productoBean.findProducto(Integer.parseInt(idProducto));
        //producto = productoJpaController.findProducto(Integer.parseInt(idProducto));
        producto.setId(Integer.parseInt(idProducto));
        //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
        Inventario inventario;
        try {
            inventario = inventarioBean.findInventario(Integer.parseInt(id));
            //inventario = inventarioJpaController.findInventario(Integer.parseInt(id));
        } catch (Exception e) {
            inventario = null;
        }
        if (id != null && !id.isEmpty()) {
            if (inventario == null || inventario.getId().toString().equals(id)) {
                try {
                    inventarioBean.edit(new Inventario(Integer.valueOf(id), fecha, producto, Double.valueOf(cantidadComprometida), Double.valueOf(cantidadTotal)));
                    //inventarioJpaController.edit(new Inventario(Integer.valueOf(id), fecha, producto, Double.valueOf(cantidadComprometida), Double.valueOf(cantidadTotal)));
                } catch (Exception ex) {
                    error = "El registro del inventario no pudo ser guardado";
                }
            }
        } else {
            if (inventario == null) {
                try {
                    inventarioBean.create(new Inventario(fecha, producto, Double.valueOf(cantidadComprometida), Double.valueOf(cantidadTotal)));
                    //inventarioJpaController.create(new Inventario(fecha, producto, Double.valueOf(cantidadComprometida), Double.valueOf(cantidadTotal)));
                } catch (NumberFormatException e) {
                    error = "El registro del inventario no pudo ser guardado";
                }
            }
        }
        return error;
    }
    
    private double disponibilidadProducto(String idProducto){
        //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
        double disponibilidad = inventarioBean.getDisponibilidad(Integer.parseInt(idProducto));
        //double disponibilidad = inventarioJpaController.getDisponibilidad(Integer.parseInt(idProducto));
        return disponibilidad;
    }
    
    private List<Producto> obtenerProductos(){
        List<Producto> productos = null;
        try {
            //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
            productos = inventarioBean.getAllProducts();
            //productos = inventarioJpaController.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(InventarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
