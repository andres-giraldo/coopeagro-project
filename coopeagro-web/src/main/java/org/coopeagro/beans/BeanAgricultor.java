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
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
@Named(value = "beanAgricultor")
@ManagedBean
@RequestScoped
public class BeanAgricultor {
    private final ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    Agricultor agricultor = new Agricultor();
    List<Agricultor> agricultores = new ArrayList<Agricultor>();
    /**
     * Creates a new instance of BeanAgricultor
     */
    public BeanAgricultor() {
        AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
        //controller.findAgricultorEntities();
        for (Agricultor agr : controller.findAgricultorEntities()) {
            System.out.println(agr);
        }
        //agricultores = listarAgricultores();
    }
    
    public void guardar(){
        //AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
    }
    
    /*private List<Agricultor> listarAgricultores(){
        AgricultorJpaController controller = (AgricultorJpaController) servletContext.getAttribute("agricultorJpaController");
        return controller.findAgricultorEntities();
    }*/
    
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