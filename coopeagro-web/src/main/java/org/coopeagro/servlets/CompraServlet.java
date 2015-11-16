package org.coopeagro.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.controladores.DetalleCompraJpaController;
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.controladores.InventarioJpaController;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.EstadosPedido;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.TiposDocumento;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author YEISSON
 */
public class CompraServlet extends HttpServlet {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public static List<Object[]> res = new ArrayList<>();

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
        String redireccion = "jsp/ComprasConsulta.jsp";
        long total;
        double promedio;

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }

        String anno;
        String mes;

        String parametro = request.getParameter("parametro");
        String fecha = request.getParameter("fecha");
        String idAgricultor = request.getParameter("idAgricultor");
        String idEmpleado = request.getParameter("idEmpleado");
        String agricultor = request.getParameter("agricultor");
        String empleado = request.getParameter("empleado");
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
                    total = TotalComprasTiempo(anno, mes);
                    if (total > 0) {
                        request.setAttribute("year", anno);
                        request.setAttribute("month", mes);
                        request.setAttribute("total", total);
                    } else {
                        mensajeError = "No se han encontrado compras en el periodo de tiempo especificado";
                    }
                }
                break;
            case "agricultor":
                res = TotalComprasAgricultor();
                break;
            case "empleado":
                res = TotalComprasEmpleado();
                break;
            case "promedio":
                promedio = PromedioCompras();
                if (promedio > 0) {
                    request.setAttribute("promedio", promedio);
                } else {
                    mensajeError = "No se han encontrado compras registradas";
                }
                break;
            case "limpiar":
                request.setAttribute("year", "");
                request.setAttribute("month", "0");
                request.setAttribute("total", "");
                request.setAttribute("promedio", "");
                res.clear();
                break;
            case "nuevaCompra":
                redireccion = "jsp/Compras.jsp";
                request.setAttribute("fecha", sdf.format(new Date()));
                break;
            case "completarAgricultor":
                JSONArray array = completarAgricultor(parametro);
                response.getWriter().write(array.toJSONString());
                break;
            case "completarEmpleado":
                array = completarEmpleado(parametro);
                response.getWriter().write(array.toJSONString());
                break;
            case "completarProducto":
                array = completarProducto(parametro);
                response.getWriter().write(array.toJSONString());
                break;
            case "guardarCompra":
                mensajeAlerta = validarGuardado(fecha, idAgricultor, idEmpleado, idProductos);
                if (mensajeAlerta.isEmpty()) {
                    mensajeError = guardarCompra(fecha, idAgricultor, idEmpleado, idProductos, valores, cantidades);
                    if(mensajeError.isEmpty()){
                        mensajeExito = "La compra ha sido guardada con éxito";
                        break;
                    }
                }
                request.setAttribute("fecha", fecha);
                request.setAttribute("idAgricultor", idAgricultor);
                request.setAttribute("idEmpleado", idEmpleado);
                request.setAttribute("agricultor", agricultor);
                request.setAttribute("empleado", empleado);
                redireccion = "jsp/Compras.jsp";
                break;
            default:
                break;
        }
        if (!accion.equals("completarAgricultor") && !accion.equals("completarEmpleado") && !accion.equals("completarProducto")) {
            request.setAttribute("mensajeError", mensajeError);
            request.setAttribute("mensajeExito", mensajeExito);
            request.setAttribute("mensajeAlerta", mensajeAlerta);
            request.getRequestDispatcher(redireccion).forward(request, response);
        }
    }

    private JSONArray completarAgricultor(String parametro) {
        JSONArray array = new JSONArray();
        AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        List<Agricultor> listaAgricultores = agricultorJpaController.completarAgricultor(parametro);
        for (Agricultor agricultor : listaAgricultores) {
            JSONObject object = new JSONObject();
            object.put("label", agricultor.getNombre() + " "
                    + agricultor.getApellidoUno() + " "
                    + agricultor.getApellidoDos());
            object.put("value", agricultor.getLlavePrimaria().getTipoDocumento().getTipoDocumento() + "," + agricultor.getLlavePrimaria().getDocumento());
            array.add(object);
        }
        return array;
    }

    private JSONArray completarEmpleado(String parametro) {
        JSONArray array = new JSONArray();
        EmpleadoJpaController empleadoJpaController = (EmpleadoJpaController) getServletContext().getAttribute("empleadoJpaController");
        List<Empleado> listaEmpleados = empleadoJpaController.completarEmpleado(parametro);
        for (Empleado empleado : listaEmpleados) {
            JSONObject object = new JSONObject();
            object.put("label", empleado.getNombre() + " "
                    + empleado.getApellidoUno() + " "
                    + empleado.getApellidoDos());
            object.put("value", empleado.getLlavePrimaria().getTipoDocumento().getTipoDocumento() + "," + empleado.getLlavePrimaria().getDocumento());
            array.add(object);
        }
        return array;
    }

    private JSONArray completarProducto(String parametro) {
        JSONArray array = new JSONArray();
        ProductoJpaController productoJpaController = (ProductoJpaController) getServletContext().getAttribute("productoJpaController");
        List<Producto> listaProductos = productoJpaController.completarProducto(parametro);
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
        } else {
            if (!anno.isEmpty()) {
                try {
                    Integer.valueOf(anno);
                } catch (NumberFormatException e) {
                    validacion += "El valor a ingresar en el campo 'Año' debe ser numérico \n";
                }
            } else {
                if (!mes.isEmpty()) {
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

    private long TotalComprasTiempo(String anno, String mes) {
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        long tc = compraJpaController.getTotalComprasTiempo(Integer.parseInt(anno), Integer.parseInt(mes));
        return tc;
    }

    private List<Object[]> TotalComprasAgricultor() {
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        return compraJpaController.getTotalComprasAgricultor();
    }

    private List<Object[]> TotalComprasEmpleado() {
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        return compraJpaController.getTotalComprasEmpleado();
    }

    private double PromedioCompras() {
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        double promedio = compraJpaController.getPromedioCompras();
        return promedio;
    }

    private String validarGuardado(String fecha, String agricultor, String empleado, String[] idProductos) {
        String error = "";
        if (fecha == null || fecha.isEmpty()) {
            error += "El campo 'Fecha de compra' es obligatorio \n";
        } else {
            try {
                sdf.parse(fecha);
            } catch (ParseException e) {
                error += "El campo 'Fecha de compra' no se encuentra en el formato 'dia/mes/año horas:minutos'";
            }
        }
        if (agricultor == null || agricultor.isEmpty()) {
            error += "El campo 'Agricultor' es obligatorio \n";
        } else {
            String[] llavePrimaria = agricultor.split(",");
            if (llavePrimaria == null || llavePrimaria.length != 2) {
                error += "El campo 'Agricultor' no fue seleccionado correctamente \n";
            } else {
                AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
                try {
                    Agricultor a = agricultorJpaController.findAgricultor(new PersonaPK(llavePrimaria[1], TiposDocumento.valueOf(llavePrimaria[0])));
                    if (a == null) {
                        error += "El 'Agricultor' ingresado no existe en la base de datos \n";
                    }
                } catch (Exception e) {
                    error += "El 'Agricultor' ingresado no existe en la base de datos \n";
                }
            }
        }
        if (empleado == null || empleado.isEmpty()) {
            error += "El campo 'Empleado' es obligatorio \n";
        } else {
            String[] llavePrimaria = empleado.split(",");
            if (llavePrimaria == null || llavePrimaria.length != 2) {
                error += "El campo 'Empleado' no fue seleccionado correctamente \n";
            } else {
                EmpleadoJpaController empleadoJpaController = (EmpleadoJpaController) getServletContext().getAttribute("empleadoJpaController");
                try {
                    Empleado e = empleadoJpaController.findEmpleado(new PersonaPK(llavePrimaria[1], TiposDocumento.valueOf(llavePrimaria[0])));
                    if (e == null) {
                        error += "El 'Empleado' ingresado no existe en la base de datos \n";
                    }
                } catch (Exception e) {
                    error += "El 'Empleado' ingresado no existe en la base de datos \n";
                }
            }
        }
        if (idProductos == null || idProductos.length == 0) {
            error = "Debe adicionar algún producto a la compra \n";
        }
        return error;
    }

    private String guardarCompra(String fecha, String agricultor, String empleado, String[] idProductos, String[] valores, String[] cantidades) {
        String error = "";
        try {
            CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
            String[] llavePrimariaE = empleado.split(",");
            EmpleadoJpaController empleadoJpaController = (EmpleadoJpaController) getServletContext().getAttribute("empleadoJpaController");
            Empleado e = empleadoJpaController.findEmpleado(new PersonaPK(llavePrimariaE[1], TiposDocumento.valueOf(llavePrimariaE[0])));

            String[] llavePrimariaA = agricultor.split(",");
            AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
            Agricultor a = agricultorJpaController.findAgricultor(new PersonaPK(llavePrimariaA[1], TiposDocumento.valueOf(llavePrimariaA[0])));
            compraJpaController.create(new Compra(a, sdf.parse(fecha), e, EstadosPedido.APROBADO, 0d));
            
            Compra compra = compraJpaController.getMaxOrder();
            DetalleCompraJpaController detalleCompraJpaController = (DetalleCompraJpaController)getServletContext().getAttribute("detalleCompraJpaController");
            ProductoJpaController productoJpaController = (ProductoJpaController)getServletContext().getAttribute("productoJpaController");
            InventarioJpaController inventarioJpaController = (InventarioJpaController) getServletContext().getAttribute("inventarioJpaController");
            
            Double totalPedido = 0d;
            for (int i = 0; i < idProductos.length; i++) {
                Producto producto = productoJpaController.findProducto(Integer.valueOf(idProductos[i]));
                totalPedido += Double.valueOf(cantidades[i]) * Double.valueOf(valores[i]);
                detalleCompraJpaController.create(new DetalleCompra(Double.valueOf(cantidades[i]), Double.valueOf(valores[i]), compra, producto));
                Inventario inventario = inventarioJpaController.getMax(Integer.valueOf(idProductos[i]));
                if (inventario.getId() != 0) {
                    inventarioJpaController.create(new Inventario(sdf.parse(fecha), producto, inventario.getCantidadComprometida(), inventario.getCantidadTotal()+Double.valueOf(cantidades[i])));
                }else{
                    inventarioJpaController.create(new Inventario(sdf.parse(fecha), producto, 0.0, Double.valueOf(cantidades[i])));
                }
            }
            compra.setTotal(totalPedido);
            compraJpaController.edit(compra);
        } catch (Exception ex) {
            error += "La compra no pudo ser guardada";
        }
        return error;
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
