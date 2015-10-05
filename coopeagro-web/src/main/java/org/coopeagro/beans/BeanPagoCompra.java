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
import org.coopeagro.controladores.PagoCompraJpaController;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.PagoCompra;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanPagoCompra")
@ManagedBean
@RequestScoped
public class BeanPagoCompra {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    PagoCompra pagoCompra = new PagoCompra();
    List<PagoCompra> pagosCompra = new ArrayList<PagoCompra>();
    List<Compra> compras = new ArrayList<Compra>();
    /**
     * Creates a new instance of BeanPagoCompra
     */
    public BeanPagoCompra() {
        PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
    }

    public PagoCompra getPagoCompra() {
        return pagoCompra;
    }

    public void setPagoCompra(PagoCompra pagoCompra) {
        this.pagoCompra = pagoCompra;
    }

    public List<PagoCompra> getPagosCompra() {
        return pagosCompra;
    }

    public void setPagosCompra(List<PagoCompra> pagosCompra) {
        this.pagosCompra = pagosCompra;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}
