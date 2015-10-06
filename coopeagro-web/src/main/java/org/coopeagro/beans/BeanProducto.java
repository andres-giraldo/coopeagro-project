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
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanProducto")
@ManagedBean
@RequestScoped
public class BeanProducto {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Producto producto = new Producto();
    List<Producto> productos = new ArrayList<Producto>();
    /**
     * Creates a new instance of BeanProducto
     */
    public BeanProducto() {
        ProductoJpaController controller = (ProductoJpaController)servletContext.getAttribute("productoJpaController");
    }
    
    public void guardar(){
        
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
