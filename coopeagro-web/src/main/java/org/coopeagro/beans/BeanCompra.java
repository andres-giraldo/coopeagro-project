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
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.EstadosPedido;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanCompra")
@ManagedBean
@RequestScoped
public class BeanCompra {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Compra compra = new Compra();
    List<Compra> compras = new ArrayList<Compra>();
    List<Agricultor> proveedores = new ArrayList<Agricultor>();
    List<Empleado> empleados = new ArrayList<Empleado>();
    /**
     * Creates a new instance of BeanCompra
     */
    public BeanCompra() {
        //CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
        compras = listarCompras();
        proveedores = listarProveedores();
        empleados = listarEmpleados();
    }
    
    public EstadosPedido[] getEstadoValues() {
        return EstadosPedido.values();
    }
    
    public TiposDocumento[] getTiposDocumentoValues() {
        return TiposDocumento.values();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
            controller.create(compra);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        compras = listarCompras();
    }
    
    public String eliminar(Integer id){
        try {
            CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        compras = listarCompras();
        return "Compra";
    }
    
    public String editar(){
        try {
            CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
            controller.edit(compra);
        } catch (Exception ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        compras = listarCompras();
        return "Compra";
    }
    
    public String prepararEdicion(Integer id){
        CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
        compra = controller.findCompra(id);
        return "Compra";
    }
    
    private List<Compra> listarCompras(){
        CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
        return controller.findCompraEntities();
    }
    
    private List<Agricultor> listarProveedores(){
        List<Agricultor> p = null;
        try {
            CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
            p = controller.getAllAgricultors();
        } catch (Exception ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private List<Empleado> listarEmpleados(){
        List<Empleado> e = null;
        try {
            CompraJpaController controller = (CompraJpaController) servletContext.getAttribute("compraJpaController");
            e = controller.getAllEmployees();
        } catch (Exception ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Agricultor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Agricultor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
