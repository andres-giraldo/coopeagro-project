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
import org.coopeagro.controladores.VentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.EstadosPedido;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.entidades.Venta;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanVenta")
@ManagedBean
@RequestScoped
public class BeanVenta {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Venta venta = new Venta();
    List<Venta> ventas = new ArrayList<Venta>();
    List<Empleado> empleados = new ArrayList<Empleado>();
    List<Cliente> clientes = new ArrayList<Cliente>();
    /**
     * Creates a new instance of BeanVenta
     */
    public BeanVenta() {
        //VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
        ventas = listarVentas();
        empleados = listarEmpleados();
        clientes = listarClientes();
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
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            controller.create(venta);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        ventas = listarVentas();
    }
    
    public String eliminar(Integer id){
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            controller.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventas = listarVentas();
        return "Venta";
    }
    
    public String editar(){
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            controller.edit(venta);
        } catch (Exception ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventas = listarVentas();
        return "Venta";
    }
    
    public String prepararEdicion(Integer id){
        VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
        venta = controller.findVenta(id);
        return "Venta";
    }
    
    private List<Venta> listarVentas(){
        VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
        return controller.findVentaEntities();
    }
    
    private List<Cliente> listarClientes(){
        List<Cliente> p = null;
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            p = controller.getAllCustomers();
        } catch (Exception ex) {
            Logger.getLogger(BeanVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    private List<Empleado> listarEmpleados(){
        List<Empleado> e = null;
        try {
            VentaJpaController controller = (VentaJpaController) servletContext.getAttribute("ventaJpaController");
            e = controller.getAllEmployees();
        } catch (Exception ex) {
            Logger.getLogger(BeanCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
