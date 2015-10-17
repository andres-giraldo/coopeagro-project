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
import org.coopeagro.controladores.DetalleCompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanDetalleCompra")
@ManagedBean
@RequestScoped
public class BeanDetalleCompra implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    DetalleCompra detalleCompra = new DetalleCompra();
    List<DetalleCompra> detallesCompra = new ArrayList<DetalleCompra>();
    List<Producto> productos = new ArrayList<Producto>();
    List<Compra> compras = new ArrayList<Compra>();
    /**
     * Creates a new instance of BeanDetalleCompra
     */
    public BeanDetalleCompra() {
        //DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
        detallesCompra = listarDetalleCompras();
        productos = listarProductos();
        compras = listarCompras();
    }
    
    public String guardar(){
        FacesMessage msg = null;
        try {
            DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
            controller.create(detalleCompra);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        detallesCompra = listarDetalleCompras();
        return "DetalleCompra";
    }
    
    public String eliminar(Integer id){
        FacesMessage msg = null;
        try {
            DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
            controller.destroy(id);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue eliminado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        detallesCompra = listarDetalleCompras();
        return "DetalleCompra";
    }
    
    public String editar(){
        FacesMessage msg = null;
        try {
            DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
            controller.edit(detalleCompra);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue editado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        detallesCompra = listarDetalleCompras();
        return "DetalleCompra";
    }
    
    public String prepararEdicion(Integer id){
        DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
        detalleCompra = controller.findDetalleCompra(id);
        return "DetalleCompra";
    }
    
    private List<DetalleCompra> listarDetalleCompras(){
        DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
        return controller.findDetalleCompraEntities();
    }
    
    private List<Producto> listarProductos(){
        List<Producto> p = null;
        try {
            DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
            p = controller.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private List<Compra> listarCompras(){
        List<Compra> c = null;
        try {
            DetalleCompraJpaController controller = (DetalleCompraJpaController) servletContext.getAttribute("detalleCompraJpaController");
            c = controller.getAllCompras();
        } catch (Exception ex) {
            Logger.getLogger(BeanDetalleCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
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

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
}
