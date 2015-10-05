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
import javax.servlet.ServletContext;
import org.coopeagro.controladores.DetalleCompraJpaController;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanDetalleCompra")
@ManagedBean
@RequestScoped
public class BeanDetalleCompra {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    DetalleCompra detalleCompra = new DetalleCompra();
    List<DetalleCompra> detallesCompra = new ArrayList<DetalleCompra>();
    List<Producto> productos = new ArrayList<Producto>();
    /**
     * Creates a new instance of BeanDetalleCompra
     */
    public BeanDetalleCompra() {
        DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
    }

    public DetalleCompra getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(DetalleCompra detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
