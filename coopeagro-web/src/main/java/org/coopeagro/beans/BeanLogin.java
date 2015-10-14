/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.coopeagro.controladores.UsuarioJpaController;
import org.primefaces.context.RequestContext;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanLogin")
@ManagedBean
@SessionScoped
public class BeanLogin implements Serializable {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String nombre = "";
    private String clave = "";
    private boolean logeado = false;
    /**
     * Creates a new instance of BeanLogin
     */
    public BeanLogin() {
    }
    
    public void login(ActionEvent actionEvent) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage msg = null;
            UsuarioJpaController controller = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            if (controller.validarInicio(nombre, clave)== true) {
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);
            } else {
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                        "Credenciales no válidas");
            }
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("estaLogeado", logeado);
            if (logeado)
                context.addCallbackParam("view", "faces/Menu.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(BeanLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
  }
    
    public boolean estaLogeado() {
        return logeado;
    }

    public String getNombre() {
      return nombre;
    }

    public void setNombre(String nombre) {
      this.nombre = nombre;
    }

    public String getClave() {
      return clave;
    }

    public void setClave(String clave) {
      this.clave = clave;
    }
}
