/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.entidades.TiposDocumento;
import org.json.simple.JSONObject;

/**
 *
 * @author sala306
 */
public class AgricultorServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
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
        String redireccion = "jsp/Agricultores.jsp";

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "";
        }
        
        String isEditar = request.getParameter("isEditar");
        String documento = request.getParameter("documento");
        String tipoDocumento = request.getParameter("tipoDocumento");
        String nombre = request.getParameter("nombre");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        String telefono = request.getParameter("telefono");
        String celular = request.getParameter("celular");
        String correo = request.getParameter("correo");
        String direccion = request.getParameter("direccion");
        String fechaRegistro = request.getParameter("fechaRegistro");
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        
        switch (accion) {
            case "guardar":
                mensajeAlerta = validarDatos(documento, tipoDocumento, nombre, apellido1, telefono, celular, correo, direccion, fechaRegistro);
                if (mensajeAlerta.isEmpty()) {
                    try {
                        fecha = formatoDelTexto.parse(fechaRegistro);
                    } catch (ParseException ex) {
                        Logger.getLogger(AgricultorServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mensajeError = guardarAgricultor(isEditar, documento, TiposDocumento.valueOf(tipoDocumento), nombre, apellido1, apellido2, telefono, celular, correo, direccion, fecha);
                    if (mensajeError.isEmpty()) {
                        mensajeExito = "El agricultor ha sido guardado con éxito";
                        break;
                    }
                }
                request.setAttribute("isEditar", isEditar);
                request.setAttribute("documento", documento);
                request.setAttribute("tipoDocumento", tipoDocumento);
                request.setAttribute("nombre", nombre);
                request.setAttribute("apellido1", apellido1);
                request.setAttribute("apellido2", apellido2);
                request.setAttribute("telefono", telefono);
                request.setAttribute("celular", celular);
                request.setAttribute("correo", correo);
                request.setAttribute("direccion", direccion);
                request.setAttribute("fechaRegistro", fechaRegistro);
                break;
            case "eliminar":
                if (eliminarAgricultor(documento, TiposDocumento.valueOf(tipoDocumento))) {
                    mensajeExito = "El agricultor ha sido eliminado con éxito";
                } else {
                    mensajeError = "El agricultor no pudo ser eliminado";
                }
                break;
            case "consultar":
                JSONObject agricultor = consultarAgricultor(documento, TiposDocumento.valueOf(tipoDocumento));
                response.getWriter().write(agricultor.toString());
                break;
            case "listar":
                listarAgricultores(response, documento);
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
    
    private String validarDatos(String documento, String tipoDocumento, String nombre, String apellido1, String telefono, String celular, String correo, String direccion, String fechaRegistro) {
        String validacion = "";
        if (documento == null || documento.isEmpty()) {
            validacion += "Debe ingresar el campo 'Documento' \n";
        }
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            validacion += "Debe ingresar el campo 'Tipo documento' \n";
        }
        if (nombre == null || nombre.isEmpty()) {
            validacion += "Debe ingresar el campo 'Nombre' \n";
        }
        if (apellido1 == null || apellido1.isEmpty()){
            validacion += "Debe ingresar el campo 'Primer apellido' \n";
        }
        if (telefono == null || telefono.isEmpty()){
            validacion += "Debe ingresar el campo 'Teléfono' \n";
        } else {
            try {
                Integer.valueOf(telefono);
            } catch (NumberFormatException e) {
                validacion += "El valor a ingresar en el campo 'Teléfono' debe ser numérico \n";
            }
        }
        if (celular == null || celular.isEmpty()){
            validacion += "Debe ingresar el campo 'Celular' \n";
        } else {
            try {
                Long.valueOf(celular);
            } catch (NumberFormatException e) {
                validacion += "El valor a ingresar en el campo 'Celular' debe ser numérico \n";
            }
        }
        if (correo == null || correo.isEmpty()){
            validacion += "Debe ingresar el campo 'Correo' \n";
        }else{
            Pattern patronEmail = Pattern.compile("[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]");
            Matcher mEmail = patronEmail.matcher(correo.toLowerCase());
            if (!mEmail.matches()){
                validacion += "El valor ingresado en el campo 'Correo' no es válido \n";  
            }
        }
        if (direccion == null || direccion.isEmpty()){
            validacion += "Debe ingresar el campo 'Dirección' \n";
        }
        if (fechaRegistro == null || fechaRegistro.isEmpty()){
            validacion += "Debe ingresar el campo 'Fecha registro' \n";
        }
        return validacion;
    }
    
    private String guardarAgricultor(String isEditar, String documento, TiposDocumento tipoDocumento, String nombre, String apellido1, String apellido2, String telefono, String celular, String correo, String direccion, Date fechaRegistro) {
        String error = "";
        AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        Agricultor agricultor;
        try {
            agricultor = agricultorJpaController.findAgricultor(new PersonaPK(documento, tipoDocumento));
        } catch (Exception e) {
            agricultor = null;
        }
        if (isEditar != null && !isEditar.isEmpty()) {
            if (agricultor == null || (agricultor.getLlavePrimaria().getDocumento().toString().equals(documento) && 
                    agricultor.getLlavePrimaria().getTipoDocumento().equals(tipoDocumento))) {
                try {
                    agricultorJpaController.edit(new Agricultor(documento, tipoDocumento, nombre, apellido1, apellido2, telefono, celular, correo, fechaRegistro, direccion));
                } catch (Exception ex) {
                    error = "El agricultor no pudo ser guardado";
                }
            } else {
                error = "Ya existe un agricultor con el documento ingresado";
            }
        } else {
            if (agricultor == null) {
                try {
                    agricultorJpaController.create(new Agricultor(documento, tipoDocumento, nombre, apellido1, apellido2, telefono, celular, correo, fechaRegistro, direccion));
                } catch (NumberFormatException e) {
                    error = "El agricultor no pudo ser guardado";
                } catch (Exception ex) {
                    Logger.getLogger(AgricultorServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                error = "Ya existe un agricultor con el documento ingresado";
            }
        }
        return error;
    }
    
    private boolean eliminarAgricultor(String documento, TiposDocumento tipoDocumento) {
        boolean eliminacion = false;
        AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        try {
            agricultorJpaController.destroy(new PersonaPK(documento, tipoDocumento));
            eliminacion = true;
        } catch (NonexistentEntityException ex) {
        }
        return eliminacion;
    }
    
    private JSONObject consultarAgricultor(String documento, TiposDocumento tipoDocumento) {
        System.out.println(documento);
        System.out.println(tipoDocumento);
        JSONObject jsonAgricultor = new JSONObject();
        AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        Agricultor agricultor;
        try {
            agricultor = agricultorJpaController.findAgricultor(new PersonaPK(documento, tipoDocumento));
        } catch (NumberFormatException e) {
            agricultor = null;
        }
        if (agricultor != null) {
            jsonAgricultor.put("isEditar", 1);
            jsonAgricultor.put("documento", agricultor.getLlavePrimaria().getDocumento());
            jsonAgricultor.put("tipoDocumento", agricultor.getLlavePrimaria().getTipoDocumento());
            jsonAgricultor.put("nombre", agricultor.getNombre());
            jsonAgricultor.put("apellido1", agricultor.getApellidoUno());
            jsonAgricultor.put("apellido2", agricultor.getApellidoDos());
            jsonAgricultor.put("telefono", agricultor.getTelefono());
            jsonAgricultor.put("celular", agricultor.getCelular());
            jsonAgricultor.put("correo", agricultor.getCorreo());
            jsonAgricultor.put("direccion", agricultor.getDireccion());
            jsonAgricultor.put("fechaRegistro", agricultor.getFechaRegistro());
        }
        return jsonAgricultor;
    }
    
    private void listarAgricultores(HttpServletResponse response, String documento) throws ServletException, IOException {
        AgricultorJpaController agricultorJpaController = (AgricultorJpaController) getServletContext().getAttribute("agricultorJpaController");
        List<Agricultor> listaAgricultores = agricultorJpaController.findAgricultorEntities(10, 0);
        PrintWriter out = response.getWriter();
        out.println("<table class=\"table table-striped table-hover table-condensed bordo-tablas\">");
        out.println(    "<thead>");
        out.println(        "<tr>");			
        out.println(            "<th>Documento</th>");
        out.println(            "<th>Tipo documento</th>");
        out.println(            "<th>Nombre</th>");
        out.println(            "<th>Primer apellido</th>");
        out.println(            "<th>Segundo apellido</th>");
        out.println(            "<th>Acciones</th>");
        out.println(        "</tr>");
        out.println(    "</thead>");
        out.println(    "<tbody>");
        if(listaAgricultores != null && !listaAgricultores.isEmpty()){
            for (Agricultor agricultor : listaAgricultores) {
                out.println("<tr>");			
                out.println(    "<td>"+agricultor.getLlavePrimaria().getDocumento()+"</td>");
                out.println(    "<td>"+agricultor.getLlavePrimaria().getTipoDocumento()+"</td>");
                out.println(    "<td>"+agricultor.getNombre()+"</td>");
                out.println(    "<td>"+agricultor.getApellidoUno()+"</td>");
                out.println(    "<td>"+agricultor.getApellidoDos()+"</td>");
                out.println(    "<td>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" onclick=\"consultarAgricultor("+agricultor.getLlavePrimaria().getDocumento()+","+agricultor.getLlavePrimaria().getTipoDocumento()+");\">Editar</button>");
                out.println(        "<button class=\"btn btn-default\" type=\"button\" data-toggle=\"modal\" data-target=\"#confirmationMessage\" onclick=\"jQuery(('#documento').val('"+agricultor.getLlavePrimaria().getDocumento()+"'), ('#tipoDocumento').val('"+agricultor.getLlavePrimaria().getTipoDocumento()+"'));\">Eliminar</button>");
                out.println(    "</td>");
                out.println("</tr>");
            }
        } else {
            out.println("   <tr>");			
            out.println(        "<td colspan=\"6\">No se encontraron registros</td>");
            out.println(    "</tr>");
        }
        out.println(    "</tbody>");
        out.println("</table>");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
    
    public static TiposDocumento[] getTiposDocumentoValues() {
        return TiposDocumento.values();
    }
}
