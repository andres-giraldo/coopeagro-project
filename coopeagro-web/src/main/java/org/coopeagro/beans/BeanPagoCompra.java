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
import org.coopeagro.controladores.PagoCompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
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
        //PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
        pagosCompra = listarPagoCompra();
        compras = listarCompras();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
            controller.create(pagoCompra);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        pagosCompra = listarPagoCompra();
    }
    
    public String eliminar(Integer id){
        try {
            PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanPagoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        pagosCompra = listarPagoCompra();
        return "PagoCompra";
    }
    
    public String editar(){
        try {
            PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
            controller.edit(pagoCompra);
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        pagosCompra = listarPagoCompra();
        return "PagoCompra";
    }
    
    public String prepararEdicion(Integer id){
        PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
        pagoCompra = controller.findPagoCompra(id);
        return "PagoCompra";
    }
    
    private List<PagoCompra> listarPagoCompra(){
        PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
        return controller.findPagoCompraEntities();
    }
    
    private List<Compra> listarCompras(){
        List<Compra> c = null;
        try {
            PagoCompraJpaController controller = (PagoCompraJpaController) servletContext.getAttribute("pagoCompraJpaController");
            c = controller.getAllCompras();
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
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
