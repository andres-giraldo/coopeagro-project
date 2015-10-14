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
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanProducto")
@ManagedBean
@RequestScoped
public class BeanProducto implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Producto producto = new Producto();
    List<Producto> productos = new ArrayList<Producto>();
    /**
     * Creates a new instance of BeanProducto
     */
    public BeanProducto() {
        //ProductoJpaController controller = (ProductoJpaController)servletContext.getAttribute("productoJpaController");
        productos = listarProductos();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            ProductoJpaController controller = (ProductoJpaController) servletContext.getAttribute("productoJpaController");
            controller.create(producto);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        productos = listarProductos();
    }
    
    public String eliminar(Integer id){
        try {
            ProductoJpaController controller = (ProductoJpaController) servletContext.getAttribute("productoJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        productos = listarProductos();
        return "Producto";
    }
    
    public String editar(){
        try {
            ProductoJpaController controller = (ProductoJpaController) servletContext.getAttribute("productoJpaController");
            controller.edit(producto);
        } catch (Exception ex) {
            Logger.getLogger(BeanProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        productos = listarProductos();
        return "Producto";
    }
    
    public String prepararEdicion(Integer id){
        ProductoJpaController controller = (ProductoJpaController) servletContext.getAttribute("productoJpaController");
        producto = controller.findProducto(id);
        return "Producto";
    }
    
    private List<Producto> listarProductos(){
        ProductoJpaController controller = (ProductoJpaController) servletContext.getAttribute("productoJpaController");
        return controller.findProductoEntities();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
