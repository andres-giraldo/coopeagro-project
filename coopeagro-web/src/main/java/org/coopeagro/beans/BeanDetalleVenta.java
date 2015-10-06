/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.DetalleVentaJpaController;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.Venta;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanDetalleVenta")
@ManagedBean
@RequestScoped
public class BeanDetalleVenta {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    DetalleVenta detalleVenta = new DetalleVenta();
    List<DetalleVenta> detallesVenta = new ArrayList<DetalleVenta>();
    List<Producto> productos = new ArrayList<Producto>();
    List<Venta> ventas = new ArrayList<Venta>();
    /**
     * Creates a new instance of BeanDetalleVenta
     */
    public BeanDetalleVenta() {
        DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
    }

    public DetalleVenta getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleVenta detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
