/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.TiposDocumento;

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
    List<Empleado> empleados = new ArrayList<Empleado>();
    /**
     * Creates a new instance of BeanEmpleado
     */
    public BeanEmpleado() {
        EmpleadoJpaController controller = (EmpleadoJpaController) servletContext.getAttribute("empleadoJpaController");
    }
    
    public SelectItem[] getTiposDocumentoValues() {
        SelectItem[] items = new SelectItem[TiposDocumento.values().length];
        int i = 0;
        for(TiposDocumento td: TiposDocumento.values()) {
          items[i++] = new SelectItem(td, td.getTipoDocumento());
        }
        return items;
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
}
