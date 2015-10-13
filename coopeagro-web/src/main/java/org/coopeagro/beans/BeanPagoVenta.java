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
import org.coopeagro.controladores.PagoVentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
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
        //PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
        pagosVenta = listarPagoVenta();
        ventas = listarVentas();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
            controller.create(pagoVenta);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        pagosVenta = listarPagoVenta();
    }
    
    public String eliminar(Integer id){
        try {
            PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanPagoVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        pagosVenta = listarPagoVenta();
        return "PagoVenta";
    }
    
    public String editar(){
        try {
            PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
            controller.edit(pagoVenta);
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        pagosVenta = listarPagoVenta();
        return "PagoVenta";
    }
    
    public String prepararEdicion(Integer id){
        PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
        pagoVenta = controller.findPagoVenta(id);
        return "PagoVenta";
    }
    
    private List<PagoVenta> listarPagoVenta(){
        PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
        return controller.findPagoVentaEntities();
    }
    
    private List<Venta> listarVentas(){
        List<Venta> v = null;
        try {
            PagoVentaJpaController controller = (PagoVentaJpaController) servletContext.getAttribute("pagoVentaJpaController");
            v = controller.getAllVentas();
        } catch (Exception ex) {
            Logger.getLogger(BeanPagoVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return v;
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
