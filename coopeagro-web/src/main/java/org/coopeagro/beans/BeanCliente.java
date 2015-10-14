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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.ClienteJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanCliente")
@ManagedBean
@RequestScoped
public class BeanCliente implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Cliente cliente = new Cliente();
    List<Cliente> clientes = new ArrayList<Cliente>();
    /**
     * Creates a new instance of BeanCliente
     */
    public BeanCliente() {
        //ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
        clientes = listarClientes();
    }
    
    public void guardar(){
        FacesMessage msg = null;
        try {
            ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
            controller.create(cliente);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        clientes = listarClientes();
    }
    
    public String eliminar(String id, TiposDocumento tipo){
        try {
            Cliente clienteEliminar = new Cliente();
            clienteEliminar.getLlavePrimaria().setDocumento(id);
            clienteEliminar.getLlavePrimaria().setTipoDocumento(tipo);
            ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
            controller.destroy(clienteEliminar.getLlavePrimaria());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientes = listarClientes();
        return "Cliente";
    }
    
    public String editar(){
        try {
            ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
            controller.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(BeanCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientes = listarClientes();
        return "Cliente";
    }
    
    public String prepararEdicion(String id, TiposDocumento tipo){
        Cliente clienteConsultar = new Cliente();
        clienteConsultar.getLlavePrimaria().setDocumento(id);
        clienteConsultar.getLlavePrimaria().setTipoDocumento(tipo);
        ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
        cliente = controller.findCliente(clienteConsultar.getLlavePrimaria());
        return "Cliente";
    }
    
    private List<Cliente> listarClientes(){
        ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
        return controller.findClienteEntities();
    }
    
    public TiposDocumento[] getTiposDocumentoValues() {
        return TiposDocumento.values();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
