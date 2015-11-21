package org.coopeagro.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.controladores.UsuarioJpaController;
import org.coopeagro.ejb.UsuarioSessionBeanRemote;
import org.coopeagro.entidades.Usuario;

/**
 *
 * @author Pipe
 */
public class InicioSesionServlet extends HttpServlet {
    
    private UsuarioSessionBeanRemote usuarioBean = null;

    public InicioSesionServlet() {
        try {
            Context context = new InitialContext();
            usuarioBean = (UsuarioSessionBeanRemote) context.lookup("ejb/UsuarioBean");
        } catch (NamingException ex) {
            Logger.getLogger(InicioSesionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String mensajeError = "";
        String mensajeAlerta = "";
        String redireccion = "jsp/InicioSesion.jsp";
        String accion = request.getParameter("accion");
        if (accion != null && accion.equals("ingresar")) {
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");

            mensajeAlerta = validarDatos(usuario, clave);
            if (mensajeAlerta.isEmpty()) {
                mensajeError = validarIngreso(usuario, clave);
                if (mensajeError.isEmpty()) {
                    redireccion = "Producto";
                } else {
                    request.setAttribute("usuario", usuario);
                }
            } else {
                request.setAttribute("usuario", usuario);
            }
        }

        request.setAttribute("mensajeError", mensajeError);
        request.setAttribute("mensajeAlerta", mensajeAlerta);
        request.getRequestDispatcher(redireccion).forward(request, response);
    }

    private String validarDatos(String usuario, String clave) {
        String validacion = "";
        if (usuario == null || usuario.isEmpty()) {
            validacion += "Debe ingresar el campo 'Usuario' \n";
        }
        if (clave == null || clave.isEmpty()) {
            validacion += "Debe ingresar el campo 'Clave'";
        }
        return validacion;
    }

    private String validarIngreso(String usuario, String clave) {
        String validacion = "";
        UsuarioJpaController usuarioJpaController = (UsuarioJpaController) getServletContext().getAttribute("usuarioJpaController");
        Usuario objetoUsuario;
        try {
            //objetoUsuario = usuarioBean.findUsuarioForUserName(usuario);
            objetoUsuario = usuarioJpaController.findUsuarioForUserName(usuario);
        } catch (Exception e) {
            objetoUsuario = null;
        }
        if (objetoUsuario != null) {
            if (objetoUsuario.getClave() == null || !objetoUsuario.getClave().equals(clave)) {
                validacion += "La clave es incorrecta para el usuario ingresado";
            }
        } else {
            //validacion += "El usuario ingresado no está registrado en el sistema";
        }
        return validacion;
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
