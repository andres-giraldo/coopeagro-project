/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.controladores.CompraJpaController;

/**
 *
 * @author YEISSON
 */
public class CompraServlet extends HttpServlet {
    public static List<Object[]> res = new ArrayList<Object[]>();
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
        String redireccion = "jsp/Compras.jsp";
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
                    total = TotalComprasTiempo(anno, mes);
                    if (total > 0) {
                        //mensajeExito = "El agricultor ha sido guardado con éxito";
                        request.setAttribute("year", anno);
                        request.setAttribute("month", mes);
                        request.setAttribute("total", total);
                    }else{
                        mensajeError = "No se han encontrado compras en el periodo de tiempo especificado";
                    }
                }
                break;
            case "agricultor":
                //listarResultados(response, accion);
                res = TotalComprasAgricultor();
                //request.setAttribute("resultados", TotalVentasEmpleado());
                break;
            case "empleado":
                //listarResultados(response, accion);
                res = TotalComprasEmpleado();
                //request.setAttribute("resultados", TotalVentasEmpleado());
                break;
            case "promedio":
                promedio = PromedioCompras();
                if (promedio > 0) {
                    request.setAttribute("promedio", promedio);
                }else{
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
    
    private long TotalComprasTiempo(String anno, String mes) {
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        long tc = compraJpaController.getTotalComprasTiempo(Integer.parseInt(anno), Integer.parseInt(mes));
        return tc;
    }
    
    private List<Object[]> TotalComprasAgricultor(){
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        return compraJpaController.getTotalComprasAgricultor();
    }
    
    private List<Object[]> TotalComprasEmpleado(){
        CompraJpaController compraJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        return compraJpaController.getTotalComprasEmpleado();
    }
    
    private double PromedioCompras(){
        CompraJpaController ventaJpaController = (CompraJpaController) getServletContext().getAttribute("compraJpaController");
        double promedio = ventaJpaController.getPromedioCompras();
        return promedio;
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
