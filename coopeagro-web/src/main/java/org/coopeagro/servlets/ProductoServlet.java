/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Producto;
import org.json.simple.JSONObject;

/**
 *
 * @author sala306
 */
public class ProductoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String mensajeExito = "";
        String mensajeError = "";
        String mensajeAlerta = "";
        String redireccion = "jsp/Productos.jsp";

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String id = request.getParameter("idProducto");
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String valor = request.getParameter("valor");

        switch (accion) {
            case "guardar":
                mensajeAlerta = validarDatos(codigo, nombre, valor);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = guardarProducto(id, codigo, nombre, valor);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El producto ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("idProducto", id);
                request.setAttribute("codigo", codigo);
                request.setAttribute("nombre", nombre);
                request.setAttribute("valor", valor);
                break;
            case "eliminar":
                if (eliminarProducto(id)) {
                    mensajeExito = "El producto ha sido eliminado con éxito";
                } else {
                    mensajeError = "El producto no pudo ser eliminado";
                }
                break;
            case "consultar":
                JSONObject producto = consultarProducto(id);
                response.getWriter().write(producto.toString());
                break;
            case "listar":
                listarProductos(response, codigo, nombre);
                break;
            default:
                break;
        }

        if (!accion.equals("consultar") && !accion.equals("listar")) {
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    private String validarDatos(String codigo, String nombre, String valor) {
        String validacion = "";
        if (codigo == null || codigo.isEmpty()) {
            validacion += "Debe ingresar el campo 'Código' \n";
        }
        if (nombre == null || nombre.isEmpty()) {
            validacion += "Debe ingresar el campo 'Nombre' \n";
        }
        if (valor == null || valor.isEmpty()) {
            validacion += "Debe ingresar el campo 'Precio de venta' \n";
        } else {
            try {
                Double.valueOf(valor);
            } catch (NumberFormatException e) {
                validacion += "El valor a ingresar en el campo 'Valor' debe ser numérico \n";
            }
        }
        return validacion;
    }

    private String guardarProducto(String id, String codigo, String nombre, String valor) {
        String error = "";
        ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        Producto producto;
        try {
            producto = productoJpaController.findProductByCodigo(codigo);
        } catch (Exception e) {
            producto = null;
        }
        if (id != null && !id.isEmpty()) {
            if (producto == null || producto.getId().toString().equals(id)) {
                try {
                    productoJpaController.edit(new Producto(Integer.valueOf(id), codigo, nombre, Double.valueOf(valor)));
                } catch (Exception ex) {
                    error = "El producto no pudo ser guardado";
                }
            } else {
                error = "Ya existe un producto con el código ingresado";
            }
        } else {
            if (producto == null) {
                try {
                    productoJpaController.create(new Producto(codigo, nombre, Double.valueOf(valor)));
                } catch (NumberFormatException e) {
                    error = "El producto no pudo ser guardado";
                }
            } else {
                error = "Ya existe un producto con el código ingresado";
            }
        }
        return error;
    }

    private boolean eliminarProducto(String id) {
        boolean eliminacion = false;
        ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        try {
            productoJpaController.destroy(Integer.valueOf(id));
            eliminacion = true;
        } catch (NumberFormatException | NonexistentEntityException ex) {
        }
        return eliminacion;
    }

    private JSONObject consultarProducto(String id) {
        JSONObject jsonProducto = new JSONObject();
        ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        Producto producto;
        try {
            producto = productoJpaController.findProducto(Integer.valueOf(id));
        } catch (NumberFormatException e) {
            producto = null;
        }
        if (producto != null) {
            jsonProducto.put("id", producto.getId());
            jsonProducto.put("codigo", producto.getCodigo());
            jsonProducto.put("nombre", producto.getNombre());
            jsonProducto.put("valor", producto.getValor());
        }
        return jsonProducto;
    }
    
    private void listarProductos(HttpServletResponse response, String codigo, String nombre) throws ServletException, IOException {
        ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        List<Producto> listaProductos = productoJpaController.findProductoEntities(10, 0);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Código</th>");
        out.println(            "<th>Nombre</th>");
        out.println(            "<th>Precio de venta</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaProductos != null && !listaProductos.isEmpty()){
            for (Producto producto : listaProductos) {
                out.println("<tr>");			
                out.println(    "<td>"+producto.getCodigo()+"</td>");
                out.println(    "<td>"+producto.getNombre()+"</td>");
                out.println(    "<td>$"+producto.getValor()+"</td>");
                out.println(    "<td>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarProducto("+producto.getId()+");\">Editar</button>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery('#idProducto').val('"+producto.getId()+"');\">Eliminar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"4\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
        out.println("</table>");
    }

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
}