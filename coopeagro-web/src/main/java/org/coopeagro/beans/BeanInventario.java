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
import org.coopeagro.controladores.InventarioJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanInventario")
@ManagedBean
@RequestScoped
public class BeanInventario implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Inventario inventario = new Inventario();
    List<Inventario> inventarios = new ArrayList<Inventario>();
    List<Producto> productos = new ArrayList<Producto>();
    /**
     * Creates a new instance of BeanInventario
     */
    public BeanInventario() {
        //InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
        inventarios = listarInventario();
        productos = listarProductos();
    }
    
    public String guardar(){
        FacesMessage msg = null;
        try {
            InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
            controller.create(inventario);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        inventarios = listarInventario();
        return "Inventario";
    }
    
    public String eliminar(Integer id){
        FacesMessage msg = null;
        try {
            InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
            controller.destroy(id);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue eliminado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        inventarios = listarInventario();
        return "Inventario";
    }
    
    public String editar(){
        FacesMessage msg = null;
        try {
            InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
            controller.edit(inventario);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue editado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(BeanInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        inventarios = listarInventario();
        return "Inventario";
    }
    
    public String prepararEdicion(Integer id){
        InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
        inventario = controller.findInventario(id);
        return "Inventario";
    }
    
    private List<Inventario> listarInventario(){
        InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
        return controller.findInventarioEntities();
    }
    
    private List<Producto> listarProductos(){
        List<Producto> p = null;
        try {
            InventarioJpaController controller = (InventarioJpaController) servletContext.getAttribute("inventarioJpaController");
            p = controller.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(BeanInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
