/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "CompraBean", mappedName = "ejb/CompraBean", beanInterface = CompraSessionBeanRemote.class)
@Stateless
public class CompraSessionBean implements CompraSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

    @Override
    public void create(Compra compra) {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        compraJpaController.create(compra);
    }
    
    @Override
    public void edit(Compra compra) throws InexistenteException {
        try {
            CompraJpaController compraJpaController = new CompraJpaController(emf);
            compraJpaController.edit(compra);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            CompraJpaController compraJpaController = new CompraJpaController(emf);
            compraJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Compra findCompra(int id) {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.findCompra(id);
    }
    
    @Override
    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.findCompraEntities(maxResults, firstResult);
    }
    
    @Override
    public List<Agricultor> getAllAgricultors() {
        List<Agricultor> agricultores = new ArrayList<Agricultor>();
        try {
            CompraJpaController compraJpaController = new CompraJpaController(emf);
            agricultores = compraJpaController.getAllAgricultors();
        } catch (Exception ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agricultores;
    }
    
    @Override
    public List<Empleado> getAllEmployees() {
        List<Empleado> empleados = new ArrayList<Empleado>();
        try {
            CompraJpaController compraJpaController = new CompraJpaController(emf);
            empleados = compraJpaController.getAllEmployees();
        } catch (Exception ex) {
            Logger.getLogger(CompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
    
    @Override
    public long getTotalComprasTiempo(int anno, int mes) {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        long tc = compraJpaController.getTotalComprasTiempo(anno, mes);
        return tc;
    }

    @Override
    public List<Object[]> getTotalComprasAgricultor() {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.getTotalComprasAgricultor();
    }

    @Override
    public List<Object[]> getTotalComprasEmpleado() {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.getTotalComprasEmpleado();
    }

    @Override
    public double getPromedioCompras() {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        double promedio = compraJpaController.getPromedioCompras();
        return promedio;
    }

    @Override
    public Compra getMaxOrder() {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.getMaxOrder();
    }

    @Override
    public List<DetalleCompra> getDetalles(int compra) {
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        return compraJpaController.getDetalles(compra);
    }
}
