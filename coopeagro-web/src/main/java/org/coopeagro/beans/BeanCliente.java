/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.coopeagro.controladores.ClienteJpaController;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanCliente")
@ManagedBean
@RequestScoped
public class BeanCliente {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Cliente cliente = new Cliente();
    List<Cliente> clientes = new ArrayList<Cliente>();
    /**
     * Creates a new instance of BeanCliente
     */
    public BeanCliente() {
        ClienteJpaController controller = (ClienteJpaController) servletContext.getAttribute("clienteJpaController");
    }
    
    public SelectItem[] getTiposDocumentoValues() {
        SelectItem[] items = new SelectItem[TiposDocumento.values().length];
        int i = 0;
        for(TiposDocumento td: TiposDocumento.values()) {
          items[i++] = new SelectItem(td, td.getTipoDocumento());
        }
        return items;
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
