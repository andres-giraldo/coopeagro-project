package org.coopeagro.servlets;

import java.io.IOException;
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
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Producto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author YEISSON
 */
public class CompraServlet extends HttpServlet {

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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
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
        CompraJpaController ventaJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        double promedio = ventaJpaController.getPromedioCompras();
        return promedio;
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
