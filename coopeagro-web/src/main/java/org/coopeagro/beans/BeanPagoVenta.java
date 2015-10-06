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
import org.coopeagro.controladores.PagoVentaJpaController;
import org.coopeagro.entidades.PagoVenta;
import org.coopeagro.entidades.Venta;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanPagoVenta")
@ManagedBean
@RequestScoped
public class BeanPagoVenta {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    PagoVenta pagoVenta = new PagoVenta();
    List<PagoVenta> pagosVenta = new ArrayList<PagoVenta>();
    List<Venta> ventas = new ArrayList<Venta>();
    /**
     * Creates a new instance of BeanPagoVenta
     */
    public BeanPagoVenta() {
        PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
    }

    public PagoVenta getPagoVenta() {
        return pagoVenta;
    }

    public void setPagoVenta(PagoVenta pagoVenta) {
        this.pagoVenta = pagoVenta;
    }

    public List<PagoVenta> getPagosVenta() {
        return pagosVenta;
    }

    public void setPagosVenta(List<PagoVenta> pagosVenta) {
        this.pagosVenta = pagosVenta;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}
