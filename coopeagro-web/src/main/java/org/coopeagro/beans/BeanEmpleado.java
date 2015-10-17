/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.controladores.UsuarioJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Perfiles;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.entidades.Usuario;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanEmpleado")
@ManagedBean
@RequestScoped
public class BeanEmpleado implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Empleado empleado = new Empleado();
    Usuario usuario = new Usuario();
    List<Empleado> empleados = new ArrayList<Empleado>();
    /**
     * Creates a new instance of BeanEmpleado
     */
    public BeanEmpleado() {
        //EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
        empleados = listarEmpleados();
    }
    
    public String guardar(){
        Usuario u = new Usuario();
        FacesMessage msg = null;
        UsuarioJpaController controllerUsu = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
        usuario.setPerfil(Perfiles.EMPLEADO);
        try {
            controllerUsu.create(usuario);
            u = controllerUsu.findUsuarioForUserName(usuario.getUsuario());
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        empleado.setUsuario(u);
        EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
        try {
            controller.create(empleado);
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        empleados = listarEmpleados();
        return "Empleado";
    }
    
    public String eliminar(String id, TiposDocumento tipo, Integer usuario){
        FacesMessage msg = null;
        try {
            Empleado empleadoEliminar = new Empleado();
            empleadoEliminar.getLlavePrimaria().setDocumento(id);
            empleadoEliminar.getLlavePrimaria().setTipoDocumento(tipo);
            EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
            controller.destroy(empleadoEliminar.getLlavePrimaria());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            UsuarioJpaController controllerUsu = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            controllerUsu.destroy(usuario);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue eliminado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        empleados = listarEmpleados();
        return "Empleado";
    }
    
    public String editar(){
        FacesMessage msg = null;
        try {
            UsuarioJpaController controllerUsu = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            usuario.setPerfil(Perfiles.EMPLEADO);
            controllerUsu.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
            empleado.setUsuario(usuario);
            controller.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue editado con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        empleados = listarEmpleados();
        return "Empleado";
    }
    
    public String prepararEdicion(String id, TiposDocumento tipo, Integer idUsuario){
        Empleado empleadoConsultar = new Empleado();
        empleadoConsultar.getLlavePrimaria().setDocumento(id);
        empleadoConsultar.getLlavePrimaria().setTipoDocumento(tipo);
        EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
        empleado = controller.findEmpleado(empleadoConsultar.getLlavePrimaria());
        UsuarioJpaController controllerUsu = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
        usuario = controllerUsu.findUsuario(idUsuario);
        return "Empleado";
    }
    
    private List<Empleado> listarEmpleados(){
        EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
        return controller.findEmpleadoEntities();
    }
    
    public TiposDocumento[] getTiposDocumentoValues() {
        return TiposDocumento.values();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
