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
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.EstadosPedido;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanCompra")
@ManagedBean
@RequestScoped
public class BeanCompra {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Compra compra = new Compra();
    List<Compra> compras = new ArrayList<Compra>();
    List<Agricultor> proveedores = new ArrayList<Agricultor>();
    List<Empleado> empleados = new ArrayList<Empleado>();
    /**
     * Creates a new instance of BeanCompra
     */
    public BeanCompra() {
        CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
    }
    
    public String guardar(){
        return "DetalleCompra";
    }
    
    public SelectItem[] getEstadoValues() {
        SelectItem[] items = new SelectItem[EstadosPedido.values().length];
        int i = 0;
        for(EstadosPedido ep: EstadosPedido.values()) {
          items[i++] = new SelectItem(ep, ep.getEstadoPedido());
        }
        return items;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Agricultor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Agricultor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
