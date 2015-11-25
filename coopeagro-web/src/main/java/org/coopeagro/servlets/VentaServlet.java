/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.coopeagro.ejb.ClienteSessionBeanRemote;
import org.coopeagro.ejb.DetalleVentaSessionBeanRemote;
import org.coopeagro.ejb.InventarioSessionBeanRemote;
import org.coopeagro.ejb.ProductoSessionBeanRemote;
import org.coopeagro.ejb.VentaSessionBeanRemote;
import org.coopeagro.serviceLocator.CoopeagroServiceLocator;

/**
 *
 * @author YEISSON
 */
public class VentaServlet extends HttpServlet {
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

        String mensajeError = "";
        String mensajeAlerta = "";
        String redireccion = "jsp/Ventas.jsp";
        long total;
        double promedio;

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        
        String anno;
        String mes;
        
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
            default:
                break;
        }
        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher(redireccion).forward(request, response);
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
