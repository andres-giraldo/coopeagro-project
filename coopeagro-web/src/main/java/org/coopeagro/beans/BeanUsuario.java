/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.UsuarioJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Perfiles;
import org.coopeagro.entidades.Usuario;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanUsuario")
@ManagedBean
@RequestScoped
public class BeanUsuario implements Serializable {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Usuario usuario = new Usuario();
    List<Usuario> usuarios = new ArrayList<Usuario>();
    /**
     * Creates a new instance of BeanUsuario
     */
    public BeanUsuario() {
        usuarios = listarUsuarios();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            controller.create(usuario);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        usuarios = listarUsuarios();
    }
    
    public String eliminar(Integer id){
        try {
            UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuarios = listarUsuarios();
        return "Usuario";
    }
    
    public String editar(){
        try {
            UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            controller.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(BeanUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        usuarios = listarUsuarios();
        return "Usuario";
    }
    
    public String prepararEdicion(Integer id){
        UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
        usuario = controller.findUsuario(id);
        return "Usuario";
    }
    
    private List<Usuario> listarUsuarios(){
        UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
        return controller.findUsuarioEntities();
    }
    
    public Perfiles[] getPerfilesValues() {
        return Perfiles.values();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
