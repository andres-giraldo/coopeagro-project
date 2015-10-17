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
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanAgricultor")
@ManagedBean
@RequestScoped
public class BeanAgricultor implements Serializable{
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Agricultor agricultor = new Agricultor();
    List<Agricultor> agricultores = new ArrayList<Agricultor>();
    /**
     * Creates a new instance of BeanAgricultor
     */
    public BeanAgricultor() {
        //AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
        //controller.findAgricultorEntities();
        /*for (Agricultor agr : controller.findAgricultorEntities()) {
            System.out.println(agr);
        }*/
        agricultores = listarAgricultores();
    }
    
    public String guardar(){
        FacesMessage msg = null;
        try {
            AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
            controller.create(agricultor);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue insertado con éxito");
        } catch (Exception ex) {
            Logger.getLogger(BeanAgricultor.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        agricultores = listarAgricultores();
        return "Agricultor";
    }
    
    public String eliminar(String id, TiposDocumento tipo){
        FacesMessage msg = null;
        try {
            Agricultor agricultorEliminar = new Agricultor();
            agricultorEliminar.getLlavePrimaria().setDocumento(id);
            agricultorEliminar.getLlavePrimaria().setTipoDocumento(tipo);
            AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
            controller.destroy(agricultorEliminar.getLlavePrimaria());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue eliminado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BeanAgricultor.class.getName()).log(Level.SEVERE, null, ex);
        }
        agricultores = listarAgricultores();
        return "Agricultor";
    }
    
    public String editar(){
        FacesMessage msg = null;
        try {
            AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
            controller.edit(agricultor);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "El registro fue editado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(BeanAgricultor.class.getName()).log(Level.SEVERE, null, ex);
        }
        agricultores = listarAgricultores();
        return "Agricultor";
    }
    
    public String prepararEdicion(String id, TiposDocumento tipo){
        Agricultor agricultorConsultar = new Agricultor();
        agricultorConsultar.getLlavePrimaria().setDocumento(id);
        agricultorConsultar.getLlavePrimaria().setTipoDocumento(tipo);
        AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
        agricultor = controller.findAgricultor(agricultorConsultar.getLlavePrimaria());
        return "Agricultor";
    }
    
    private List<Agricultor> listarAgricultores(){
        AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
        return controller.findAgricultorEntities();
    }
    
    public TiposDocumento[] getTiposDocumentoValues() {
        return TiposDocumento.values();
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public List<Agricultor> getAgricultores() {
        return agricultores;
    }

    public void setAgricultores(List<Agricultor> agricultores) {
        this.agricultores = agricultores;
    }
}
