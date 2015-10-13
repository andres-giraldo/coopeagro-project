/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

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
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.entidades.Usuario;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanEmpleado")
@ManagedBean
@RequestScoped
public class BeanEmpleado {
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
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
            //UsuarioJpaController controllerUsu = (UsuarioJpaController) servletContext.getAttribute("usuarioJpaController");
            //controllerUsu.create(usuario);
            controller.create(empleado);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        empleados = listarEmpleados();
    }
    
    public String eliminar(String id, TiposDocumento tipo){
        try {
            Empleado empleadoEliminar = new Empleado();
            empleadoEliminar.getLlavePrimaria().setDocumento(id);
            empleadoEliminar.getLlavePrimaria().setTipoDocumento(tipo);
            EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
            controller.destroy(empleadoEliminar.getLlavePrimaria());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        empleados = listarEmpleados();
        return "Empleado";
    }
    
    public String editar(){
        try {
            EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
            controller.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(BeanEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        empleados = listarEmpleados();
        return "Empleado";
    }
    
    public String prepararEdicion(String id, TiposDocumento tipo){
        Empleado empleadoConsultar = new Empleado();
        empleadoConsultar.getLlavePrimaria().setDocumento(id);
        empleadoConsultar.getLlavePrimaria().setTipoDocumento(tipo);
        EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
        empleado = controller.findEmpleado(empleadoConsultar.getLlavePrimaria());
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
