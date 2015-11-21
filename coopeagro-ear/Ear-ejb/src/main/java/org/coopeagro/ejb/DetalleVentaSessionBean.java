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
import org.coopeagro.controladores.DetalleVentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

@Stateless
@EJB(name = "DetalleCompraBean", mappedName = "ejb/DetalleVentaBean", beanInterface = DetalleVentaSessionBeanRemote.class)
public class DetalleVentaSessionBean implements DetalleVentaSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(DetalleVenta detalleVenta) {
        DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
        detalleVentaJpaController.create(detalleVenta);
    }

    @Override
    public void edit(DetalleVenta detalleVenta) throws InexistenteException {
        try {
            DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
            detalleVentaJpaController.edit(detalleVenta);
        } catch (Exception ex) {
            Logger.getLogger(DetalleVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
            detalleVentaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public DetalleVenta findDetalleVenta(int id) {
        DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
        return detalleVentaJpaController.findDetalleVenta(id);
    }

    @Override
    public List<DetalleVenta> findDetalleVentaEntities(int maxResults, int firstResult) {
        DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
        return detalleVentaJpaController.findDetalleVentaEntities(maxResults, firstResult);
    }

    @Override
    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
            productos = detalleVentaJpaController.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(DetalleVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    @Override
    public List<Venta> getAllVentas() {
        List<Venta> ventas = new ArrayList<Venta>();
        try {
            DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
            ventas = detalleVentaJpaController.getAllVentas();
        } catch (Exception ex) {
            Logger.getLogger(DetalleVentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ventas;
    }
}
