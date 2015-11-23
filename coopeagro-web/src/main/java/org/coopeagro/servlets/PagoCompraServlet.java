/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.ejb.CompraSessionBeanRemote;
import org.coopeagro.ejb.PagoCompraSessionBeanRemote;
import org.coopeagro.serviceLocator.CoopeagroServiceLocator;

/**
 *
 * @author YEISSON
 */
public class PagoCompraServlet extends HttpServlet {
    
    @EJB
    private PagoCompraSessionBeanRemote pagoCompraBean = null;
    @EJB
    private CompraSessionBeanRemote compraBean = null;

    public PagoCompraServlet() {
        Properties props = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CoopeagroServiceLocator.properties");
        if (inputStream != null) {
            try {
                props.load(inputStream);
                CoopeagroServiceLocator cesl = new CoopeagroServiceLocator(props);
                pagoCompraBean = cesl.<PagoCompraSessionBeanRemote>getEJBInstance("ejb/PagoCompraBean");
                compraBean = cesl.<CompraSessionBeanRemote>getEJBInstance("ejb/CompraBean");
            } catch (NamingException | IOException ex) {
                Logger.getLogger(PagoCompraServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        //PagoCompraJpaController controller = (PagoCompraJpaController) getServletContext().getAttribute("pagoCompraJpaController");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletPagoCompra</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletPagoCompra at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
