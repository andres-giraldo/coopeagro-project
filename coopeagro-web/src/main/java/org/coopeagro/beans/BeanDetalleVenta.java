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
import org.coopeagro.controladores.DetalleVentaJpaController;
import org.coopeagro.controladores.VentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
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
public class BeanDetalleVenta implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    DetalleVenta detalleVenta = new DetalleVenta();
    Venta venta = new Venta();
    List<DetalleVenta> detallesVenta = new ArrayList<DetalleVenta>();
    List<Producto> productos = new ArrayList<Producto>();
    List<Venta> ventas = new ArrayList<Venta>();
    /**
     * Creates a new instance of BeanDetalleVenta
     */
    public BeanDetalleVenta() {
        //DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
        detallesVenta = listarDetalleVentas();
        productos = listarProductos();
        ventas = listarVentas();
    }
    
    public String guardar(){
        FacesMessage msg = null;
        try {
            DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
            controller.create(detalleVenta);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            double total = controller.getTotal(detalleVenta.getVenta().getNumeroPedido());
            venta = controller.findVenta(detalleVenta.getVenta().getNumeroPedido());
            venta.setTotal(total);
            //venta.setNumeroPedido(detalleVenta.getVenta().getNumeroPedido());
            controller.edit(venta);
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        detallesVenta = listarDetalleVentas();
        return "DetalleVenta";
    }
    
    public String eliminar(Integer id){
        FacesMessage msg = null;
        try {
            DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
            controller.destroy(id);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue eliminado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            double total = controller.getTotal(id);
            venta = controller.findVenta(id);
            venta.setTotal(total);
            //venta.setNumeroPedido(detalleVenta.getVenta().getNumeroPedido());
            controller.edit(venta);
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        detallesVenta = listarDetalleVentas();
        return "DetalleVenta";
    }
    
    public String editar(){
        FacesMessage msg = null;
        try {
            DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
            controller.edit(detalleVenta);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue editado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        detallesVenta = listarDetalleVentas();
        return "DetalleVenta";
    }
    
    public String prepararEdicion(Integer id){
        DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
        detalleVenta = controller.findDetalleVenta(id);
        return "DetalleVenta";
    }
    
    private List<DetalleVenta> listarDetalleVentas(){
        DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
        return controller.findDetalleVentaEntities();
    }
    
    private List<Producto> listarProductos(){
        List<Producto> p = null;
        try {
            DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
            p = controller.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private List<Venta> listarVentas(){
        List<Venta> c = null;
        try {
            DetalleVentaJpaController controller = (DetalleVentaJpaController) servletContext.getAttribute("detalleVentaJpaController");
            c = controller.getAllVentas();
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
