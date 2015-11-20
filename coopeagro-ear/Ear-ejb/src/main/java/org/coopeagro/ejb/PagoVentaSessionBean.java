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
import org.coopeagro.controladores.PagoVentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.PagoVenta;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

@Stateless
@EJB(name = "ejb/PagoVentaBean", beanInterface = PagoVentaSessionBeanRemote.class)
public class PagoVentaSessionBean implements PagoVentaSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(PagoVenta pagoVenta) {
        PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
        pagoVentaJpaController.create(pagoVenta);
    }

    @Override
    public void edit(PagoVenta pagoVenta) throws InexistenteException {
        try {
            PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
            pagoVentaJpaController.edit(pagoVenta);
        } catch (Exception ex) {
            Logger.getLogger(PagoVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
            pagoVentaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PagoVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public PagoVenta findPagoVenta(int id) {
        PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
        return pagoVentaJpaController.findPagoVenta(id);
    }

    @Override
    public List<PagoVenta> findPagoVentaEntities(int maxResults, int firstResult) {
        PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
        return pagoVentaJpaController.findPagoVentaEntities(maxResults, firstResult);
    }

    @Override
    public List<Venta> getAllVentas() {
        List<Venta> ventas = new ArrayList<Venta>();
        try {
            PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
            ventas = pagoVentaJpaController.getAllVentas();
        } catch (Exception ex) {
            Logger.getLogger(PagoVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ventas;
    }    
}
