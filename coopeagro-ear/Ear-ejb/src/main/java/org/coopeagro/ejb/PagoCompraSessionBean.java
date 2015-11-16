package org.coopeagro.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.coopeagro.controladores.PagoCompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.PagoCompra;

@EJB(mappedName = "ejb/PagoCompraBean")
@Stateless
public class PagoCompraSessionBean implements PagoCompraSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(PagoCompra pagoCompra) {
        PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
        pagoCompraJpaController.create(pagoCompra);
    }

    @Override
    public void edit(PagoCompra pagoCompra) {
        try {
            PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
            pagoCompraJpaController.edit(pagoCompra);
        } catch (Exception ex) {
            Logger.getLogger(PagoCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy(int id) {
        try {
            PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
            pagoCompraJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PagoCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public PagoCompra findPagoCompra(int id) {
        PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
        return pagoCompraJpaController.findPagoCompra(id);
    }

    @Override
    public List<PagoCompra> findPagoCompraEntities(int maxResults, int firstResult) {
        PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
        return pagoCompraJpaController.findPagoCompraEntities(maxResults, firstResult);
    }

    @Override
    public List<Compra> getAllCompras() {
        List<Compra> compras = new ArrayList<Compra>();
        try {
            PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
            compras = pagoCompraJpaController.getAllCompras();
        } catch (Exception ex) {
            Logger.getLogger(PagoCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compras;
    }
}
