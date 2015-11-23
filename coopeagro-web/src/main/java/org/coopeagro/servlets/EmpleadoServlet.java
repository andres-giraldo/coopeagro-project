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
import org.coopeagro.ejb.EmpleadoSessionBeanRemote;
import org.coopeagro.ejb.UsuarioSessionBeanRemote;
import org.coopeagro.serviceLocator.CoopeagroServiceLocator;

/**
 *
 * @author YEISSON
 */
public class EmpleadoServlet extends HttpServlet {

    @EJB
    private EmpleadoSessionBeanRemote empleadoBean = null;
    @EJB
    private UsuarioSessionBeanRemote usuarioBean = null;

    public EmpleadoServlet() {
        Properties props = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("CoopeagroServiceLocator.properties");
        if (inputStream != null) {
            try {
                props.load(inputStream);
                CoopeagroServiceLocator cesl = new CoopeagroServiceLocator(props);
                empleadoBean = cesl.<EmpleadoSessionBeanRemote>getEJBInstance("ejb/EmpleadoBean");
                usuarioBean = cesl.<UsuarioSessionBeanRemote>getEJBInstance("ejb/UsuarioBean");
            } catch (NamingException | IOException ex) {
                Logger.getLogger(EmpleadoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        //EmpleadoJpaController controller = (EmpleadoJpaController) getServletContext().getAttribute("empleadoJpaController");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletEmpleado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServletEmpleado at " + request.getContextPath() + "</h1>");
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
