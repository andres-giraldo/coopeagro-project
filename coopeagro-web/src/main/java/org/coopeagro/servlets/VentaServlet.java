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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.ejb.ClienteSessionBeanRemote;
import org.coopeagro.ejb.DetalleVentaSessionBeanRemote;
import org.coopeagro.ejb.InventarioSessionBeanRemote;
import org.coopeagro.ejb.ProductoSessionBeanRemote;
import org.coopeagro.ejb.VentaSessionBeanRemote;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.EstadosPedido;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.entidades.Venta;
import org.coopeagro.serviceLocator.CoopeagroServiceLocator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author YEISSON
 */
public class VentaServlet extends HttpServlet {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    public static List<Object[]> res = new ArrayList<Object[]>();
    
    @EJB
    private VentaSessionBeanRemote ventaBean = null;
    @EJB
    private ClienteSessionBeanRemote clienteBean = null;
    /*@EJB
    private EmpleadoSessionBeanRemote empleadoBean = null;*/
    @EJB
    private DetalleVentaSessionBeanRemote detalleVentaBean = null;
    @EJB
    private ProductoSessionBeanRemote productoBean = null;
    @EJB
    private InventarioSessionBeanRemote inventarioBean = null;
    
    @Resource(mappedName = "jms/CoopeagroQueue")
    private Queue coopeagroQueue;

    @Resource(mappedName = "jms/CoopeagroQueueConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    public VentaServlet() {
        Properties props = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CoopeagroServiceLocator.properties");
        if (inputStream != null) {
            try {
                props.load(inputStream);
                CoopeagroServiceLocator cesl = new CoopeagroServiceLocator(props);
                ventaBean = cesl.<VentaSessionBeanRemote>getEJBInstance("ejb/VentaBean");
                clienteBean = cesl.<ClienteSessionBeanRemote>getEJBInstance("ejb/ClienteBean");
                //empleadoBean = cesl.<EmpleadoSessionBeanRemote>getEJBInstance("ejb/EmpleadoBean");
                detalleVentaBean = cesl.<DetalleVentaSessionBeanRemote>getEJBInstance("ejb/DetalleVentaBean");
                productoBean = cesl.<ProductoSessionBeanRemote>getEJBInstance("ejb/ProductoBean");
                inventarioBean = cesl.<InventarioSessionBeanRemote>getEJBInstance("ejb/InventarioBean");
            } catch (NamingException | IOException ex) {
                Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Presocesses resequests fores both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request seresvlet resequest
     * @param response seresvlet resesponse
     * @throws ServletException if a seresvlet-specific eresresores occuress
     * @throws IOException if an I/O eresresores occuress
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String mensajeExito = "";
        String mensajeError = "";
        String mensajeAlerta = "";
        String redireccion = "jsp/VentasConsulta.jsp";
        long total;
        double promedio;

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        
        String anno;
        String mes;
        
        String parametro = request.getParameter("parametro");
        String id = request.getParameter("idVenta");
        String fecha = request.getParameter("fecha");
        String fechaEntrega = request.getParameter("fechaEntrega");
        String direccion = request.getParameter("direccion");
        String remitente = request.getParameter("remitente");
        String idCliente = request.getParameter("idCliente");
        String cliente = request.getParameter("cliente");
        String[] idProductos = request.getParameterValues("idProductos");
        String[] valores = request.getParameterValues("valores");
        String[] cantidades = request.getParameterValues("cantidades");
        
        switch (accion) {
            case "total":
                anno = request.getParameter("year");
                mes = request.getParameter("month");
                if (anno.isEmpty()) {
                    anno = "0";
                }
                mensajeAlerta = validarDatos(anno, mes);
                if (mensajeAlerta.isEmpty()) {
                    total = TotalVentasTiempo(anno, mes);
                    if (total > 0) {
                        //mensajeExito = "El agricultor ha sido guardado con éxito";
                        request.setAttribute("year", anno);
                        request.setAttribute("month", mes);
                        request.setAttribute("total", total);
                    }else{
                        mensajeError = "No se han encontrado ventas en el periodo de tiempo especificado";
                    }
                }
                break;
            /*case "empleado":
                //listarResultados(response, accion);
                res = TotalVentasEmpleado();
                //request.setAttribute("resultados", TotalVentasEmpleado());
                break;*/
            case "cliente":
                //listarResultados(response, accion);
                res = TotalVentasCliente();
                //request.setAttribute("resultados", TotalVentasCliente());
                break;
            case "promedio":
                promedio = PromedioVentas();
                if (promedio > 0) {
                    request.setAttribute("promedio", promedio);
                }else{
                    mensajeError = "No se han encontrado ventas registradas";
                }
                break;
            case "limpiar":
                request.setAttribute("year", "");
                request.setAttribute("month", "0");
                request.setAttribute("total", "");
                request.setAttribute("promedio", "");
                res.clear();
                break;
            case "nuevaVenta":
                redireccion = "jsp/Ventas.jsp";
                request.setAttribute("fecha", sdf.format(new Date()));
                break;
            case "completarCliente":
                JSONArray array = completarCliente(parametro);
                response.getWriter().write(array.toJSONString());
                break;
            case "completarProducto":
                array = completarProducto(parametro);
                response.getWriter().write(array.toJSONString());
                break;
            case "guardarVenta":
                mensajeAlerta = validarGuardado(fecha, fechaEntrega, idCliente, idProductos, direccion, remitente, cantidades);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = guardarVenta(fecha, fechaEntrega, idCliente, idProductos, valores, cantidades, direccion, remitente);
                    if(mensajeError.isEmpty()){
                        mensajeExito = "La compra ha sido guardada con éxito";
                        break;
                    }
                }
                request.setAttribute("fecha", fecha);
                request.setAttribute("fechaEntrega", fechaEntrega);
                request.setAttribute("direccion", direccion);
                request.setAttribute("remitente", remitente);
                request.setAttribute("idCliente", idCliente);
                request.setAttribute("cliente", cliente);
                redireccion = "jsp/Ventas.jsp";
                break;
            case "listar":
                listarVentas(response);
                break;
            case "cancelar":
                mensajeError = cancelarVenta(id);
                if(mensajeError.isEmpty()){
                    mensajeExito = "La venta ha sido cancelada con éxito";
                    break;
                }
                break;
            case "detalles":
                SimpleDateFormat sdfe = new SimpleDateFormat("dd/MM/yyyy");
                Venta venta = ventaBean.findVenta(Integer.parseInt(id));
                request.setAttribute("idVenta", venta.getNumeroPedido());
                request.setAttribute("fecha", sdf.format(venta.getFechaPedido()));
                request.setAttribute("fechaEntrega", sdfe.format(venta.getFechaEstimadaEntrega()));
                request.setAttribute("direccion", venta.getDireccion());
                request.setAttribute("remitente", venta.getRemitente());
                request.setAttribute("totalPedido", venta.getTotal());
                request.setAttribute("cliente", venta.getCliente().getNombre() + " " + venta.getCliente().getApellidoUno() + 
                        " " + venta.getCliente().getApellidoDos());
                request.setAttribute("detalles", ventaBean.getDetalles(Integer.parseInt(id)));
                redireccion = "jsp/DetalleVentas.jsp";
                break;
            case "regresarCompra":
                redireccion = "jsp/Ventas.jsp";
            default:
                break;
        }
        if (!accion.equals("completarCliente") && !accion.equals("completarProducto") && !accion.equals("listar")) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }
    
    private JSONArray completarCliente(String parametro) {
        JSONArray array = new JSONArray();
        //AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        List<Cliente> listaClientes = clienteBean.completarCliente(parametro);
        //List<Agricultor> listaAgricultores = agricultorJpaController.completarAgricultor(parametro);
        for (Cliente cliente : listaClientes) {
            JSONObject object = new JSONObject();
            object.put("label", cliente.getNombre() + " "
                    + cliente.getApellidoUno() + " "
                    + cliente.getApellidoDos());
            object.put("value", cliente.getLlavePrimaria().getTipoDocumento().getTipoDocumento() + "," + cliente.getLlavePrimaria().getDocumento());
            array.add(object);
        }
        return array;
    }

    private JSONArray completarProducto(String parametro) {
        JSONArray array = new JSONArray();
        //ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        List<Producto> listaProductos = productoBean.completarProducto(parametro);
        //List<Producto> listaProductos = productoJpaController.completarProducto(parametro);
        for (Producto producto : listaProductos) {
            JSONObject object = new JSONObject();
            object.put("label", producto.getCodigo() + " " + producto.getNombre());
            object.put("value", producto.getId());
            object.put("nombre", producto.getNombre());
            object.put("valor", producto.getValor());
            object.put("codigo", producto.getCodigo());
            object.put("cantidad", 1);
            array.add(object);
        }
        return array;
    }
    
    private String validarDatos(String anno, String mes) {
        String validacion = "";
        if (anno.equals("0") && mes.equals("0")) {
            validacion += "Debe ingresar el campo 'Año' o 'Mes' \n";
        }else{
            if (!anno.isEmpty()) {
                try {
                    Integer.valueOf(anno);
                } catch (NumberFormatException e) {
                    validacion += "El valor a ingresar en el campo 'Año' debe ser numérico \n";
                }
            }else{
                if(!mes.isEmpty()){
                    try {
                        Integer.valueOf(anno);
                    } catch (NumberFormatException e) {
                        validacion += "El valor a ingresar en el campo 'Mes' debe ser numérico \n";
                    }
                }
            }
        }
        return validacion;
    }
    
    private long TotalVentasTiempo(String anno, String mes) {
        //VentaJpaController ventaJpaController = (VentaJpaController) getServletContext().getAttribute("ventaJpaController");
        long tv = ventaBean.getTotalVentasTiempo(Integer.parseInt(anno), Integer.parseInt(mes));
        //long tv = ventaJpaController.getTotalVentasTiempo(Integer.parseInt(anno), Integer.parseInt(mes));
        return tv;
    }
    
    private double PromedioVentas(){
        //VentaJpaController ventaJpaController = (VentaJpaController) getServletContext().getAttribute("ventaJpaController");
        double promedio = ventaBean.getPromedioVentas();
        //double promedio = ventaJpaController.getPromedioVentas();
        return promedio;
    }
    
    private List<Object[]> TotalVentasCliente(){
        //VentaJpaController ventaJpaController = (VentaJpaController) getServletContext().getAttribute("ventaJpaController");
        return ventaBean.getTotalVentasCliente();
        //return ventaJpaController.getTotalVentasCliente();
    }
    
    private String validarGuardado(String fecha, String fechaEntrega, String cliente, String[] idProductos, String direccion, String remitente, String[] cantidades) {
        SimpleDateFormat sdfe = new SimpleDateFormat("dd/MM/yyyy");
        String error = "";
        if (fecha == null || fecha.isEmpty()) {
            error += "El campo 'Fecha de venta' es obligatorio \n";
        } else {
            try {
                sdf.parse(fecha);
            } catch (ParseException e) {
                error += "El campo 'Fecha de venta' no se encuentra en el formato 'dia/mes/año horas:minutos'";
            }
        }
        if (fechaEntrega == null || fechaEntrega.isEmpty()) {
            error += "El campo 'Fecha de entrega' es obligatorio \n";
        } else {
            try {
                sdfe.parse(fechaEntrega);
            } catch (ParseException e) {
                error += "El campo 'Fecha de entrega' no se encuentra en el formato 'dia/mes/año'";
            }
        }
        if (direccion == null || direccion.isEmpty()) {
            error += "El campo 'Dirección' es obligatorio \n";
        }
        if (remitente == null || remitente.isEmpty()) {
            error += "El campo 'Remitente' es obligatorio \n";
        }
        if (cliente == null || cliente.isEmpty()) {
            error += "El campo 'Cliente' es obligatorio \n";
        } else {
            String[] llavePrimaria = cliente.split(",");
            if (llavePrimaria == null || llavePrimaria.length != 2) {
                error += "El campo 'Cliente' no fue seleccionado correctamente \n";
            } else {
                //AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
                try {
                    Cliente c = clienteBean.findCliente(new PersonaPK(llavePrimaria[1], TiposDocumento.valueOf(llavePrimaria[0])));
                    //Agricultor a = agricultorJpaController.findAgricultor(new PersonaPK(llavePrimaria[1], TiposDocumento.valueOf(llavePrimaria[0])));
                    if (c == null) {
                        error += "El 'Cliente' ingresado no existe en la base de datos \n";
                    }
                } catch (Exception e) {
                    error += "El 'Cliente' ingresado no existe en la base de datos \n";
                }
            }
        }
        if (idProductos == null || idProductos.length == 0) {
            error += "Debe adicionar algún producto a la venta \n";
        }else{
            for (int i = 0; i < cantidades.length; i++) {
                Inventario inventario = inventarioBean.getMax(Integer.valueOf(idProductos[i]));
                if (Double.valueOf(cantidades[i]) > (inventario.getCantidadTotal()-inventario.getCantidadComprometida())) {
                    error += "La cantidad del producto no debe ser superior a la registrada en el inventario \n";
                }
            }
        }
        return error;
    }

    private String guardarVenta(String fecha, String fechaEntrega, String cliente, String[] idProductos, String[] valores, String[] cantidades, String direccion, String remitente) {
        SimpleDateFormat sdfe = new SimpleDateFormat("dd/MM/yyyy");
        String error = "";
        try {
            //CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
            System.out.println(cliente);
            String[] llavePrimariaE = cliente.split(",");
            //EmpleadoJpaController empleadoJpaController = (EmpleadoJpaController) getServletContext().getAttribute("empleadoJpaController");
            Cliente c = clienteBean.findCliente(new PersonaPK(llavePrimariaE[1], TiposDocumento.valueOf(llavePrimariaE[0])));
            //Empleado e = empleadoJpaController.findEmpleado(new PersonaPK(llavePrimariaE[1], TiposDocumento.valueOf(llavePrimariaE[0])));

            ventaBean.create(new Venta(sdfe.parse(fechaEntrega), c, direccion, remitente, sdf.parse(fecha), EstadosPedido.APROBADO, 0d));
            //compraJpaController.create(new Compra(a, sdf.parse(fecha), e, EstadosPedido.APROBADO, 0d));
            
            Venta venta = ventaBean.getMaxOrder();
            //Compra compra = compraJpaController.getMaxOrder();
            //DetalleCompraJpaController detalleCompraJpaController = (DetalleCompraJpaController)getServletContext().getAttribute("detalleCompraJpaController");
            //ProductoJpaController productoJpaController = (ProductoJpaController)getServletContext().getAttribute("productoJpaController");
            //InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
            
            Double totalPedido = 0d;
            for (int i = 0; i < idProductos.length; i++) {
                Producto producto = productoBean.findProducto(Integer.valueOf(idProductos[i]));
                //Producto producto = productoJpaController.findProducto(Integer.valueOf(idProductos[i]));
                totalPedido += Double.valueOf(cantidades[i]) * Double.valueOf(valores[i]);
                detalleVentaBean.create(new DetalleVenta(Double.valueOf(cantidades[i]), Double.valueOf(valores[i]), venta, producto));
                //detalleCompraJpaController.create(new DetalleCompra(Double.valueOf(cantidades[i]), Double.valueOf(valores[i]), compra, producto));
                Inventario inventario = inventarioBean.getMax(Integer.valueOf(idProductos[i]));
                //Inventario inventario = inventarioJpaController.getMax(Integer.valueOf(idProductos[i]));
                if (inventario.getId() != 0) {
                    inventarioBean.create(new Inventario(sdf.parse(fecha), producto, inventario.getCantidadComprometida()+Double.valueOf(cantidades[i]), inventario.getCantidadTotal()));
                    //inventarioJpaController.create(new Inventario(sdf.parse(fecha), producto, inventario.getCantidadComprometida(), inventario.getCantidadTotal()+Double.valueOf(cantidades[i])));
                }else{
                    inventarioBean.create(new Inventario(sdf.parse(fecha), producto, Double.valueOf(cantidades[i]), 0.0));
                    //inventarioJpaController.create(new Inventario(sdf.parse(fecha), producto, 0.0, Double.valueOf(cantidades[i])));
                }
            }
            venta.setTotal(totalPedido);
            ventaBean.edit(venta);
            //compraJpaController.edit(compra);
            sendMessage();
        } catch (Exception ex) {
            error += "La venta no pudo ser guardada";
        }
        return error;
    }
    
    private String cancelarVenta(String id) {
        String error = "";
        try {
            Venta venta = ventaBean.findVenta(Integer.parseInt(id));
            if (!venta.getEstado().getEstadoPedido().equals("CANCELADO")) {
                if (ventaBean.pagosVenta(Integer.parseInt(id)) == 0) {
                    venta.setEstado(EstadosPedido.CANCELADO);
                    ventaBean.edit(venta);
                    for (DetalleVenta dv : ventaBean.getDetalles(venta.getNumeroPedido())) {
                        Inventario inventario = inventarioBean.getMax(dv.getProducto().getId());
                        inventarioBean.create(new Inventario(new Date(), dv.getProducto(), inventario.getCantidadComprometida()-dv.getCantidad(), inventario.getCantidadTotal()));
                    }
                }else{
                   error += "La venta ya se encuentra paga"; 
                }
            }else{
               error += "La venta ya se encuentra cancelada"; 
            }
        } catch (Exception ex) {
            error += "La venta no pudo ser cancelada";
        }
        return error;
    }
    
    private void listarVentas(HttpServletResponse response) throws ServletException, IOException {
        //ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        List<Venta> listaVentas = ventaBean.findVentaEntities(10, 0);
        //List<Producto> listaProductos = productoJpaController.findProductoEntities(10, 0);
        //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Fecha</th>");
        out.println(            "<th>Cliente</th>");
        out.println(            "<th>Dirección</th>");
        out.println(            "<th>Remitente</th>");
        out.println(            "<th>Total</th>");
        out.println(            "<th>Estado</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaVentas != null && !listaVentas.isEmpty()){
            for (Venta venta : listaVentas) {
                out.println("<tr>");			
                out.println(    "<td>"+sdf.format(venta.getFechaPedido())+"</td>");
                out.println(    "<td>"+venta.getCliente().getLlavePrimaria().getDocumento()+" - "+
                        venta.getCliente().getLlavePrimaria().getTipoDocumento().getTipoDocumento()+"</td>");
                out.println(    "<td>"+venta.getDireccion()+"</td>");
                out.println(    "<td>"+venta.getRemitente()+"</td>");
                out.println(    "<td>$"+venta.getTotal()+"</td>");
                out.println(    "<td>"+venta.getEstado()+"</td>");
                out.println(    "<td>");
                out.println(        "<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id=\"detalleVenta\" value=\"detalles\" onclick=\"consultarDetalles("+venta.getNumeroPedido()+")\">Detalles</button>");
                out.println(        "<button class=\"btn btn-default\" type=\"submit\" name=\"accion\" id=\"cancelarVenta\" value=\"cancelar\" onclick=\"cancelar("+venta.getNumeroPedido()+")\">Cancelar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"8\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
        out.println("</table>");
    }
    
    private void sendMessage() {
        QueueConnection connection = null;
        QueueSession session = null;
        QueueSender sender = null;
        try {
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = session.createSender(coopeagroQueue);
            TextMessage msg = session.createTextMessage();
            msg.setText("Mensaje de prueba");
            msg.setStringProperty("RECIPIENT", "MDB");
            msg.setJMSReplyTo(coopeagroQueue);
            sender.send(msg);
            //out.println("<h1>Message sent successfully</h1>");
            System.out.println("Message sent successfully");
        } catch (JMSException ex) {
            Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
            //out.println("<h1>Sending message failed</h1>");
            System.out.println("Sending message failed");
        } finally {
            if (sender != null) {
                try {
                    sender.close();
                } catch (JMSException ex) {
                    Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(VentaServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /*private List<Object[]> TotalVentasEmpleado(){
        //VentaJpaController ventaJpaController = (VentaJpaController) getServletContext().getAttribute("ventaJpaController");
        return ventaBean.getTotalVentasEmpleado();
        //return ventaJpaController.getTotalVentasEmpleado();
    }*/

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request seresvlet resequest
     * @param response seresvlet resesponse
     * @throws ServletException if a seresvlet-specific eresresores occuress
     * @throws IOException if an I/O eresresores occuress
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request seresvlet resequest
     * @param response seresvlet resesponse
     * @throws ServletException if a seresvlet-specific eresresores occuress
     * @throws IOException if an I/O eresresores occuress
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returesns a shorest descresiption of the seresvlet.
     *
     * @return a Stresing containing seresvlet descresiption
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}   
