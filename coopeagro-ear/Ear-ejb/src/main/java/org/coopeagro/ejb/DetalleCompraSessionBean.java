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
import org.coopeagro.controladores.DetalleCompraJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Producto;
import org.coopeagro.excepciones.InexistenteException;

@Stateless
@EJB(name = "ejb/DetalleCompraBean", beanInterface = DetalleCompraSessionBeanRemote.class)
public class DetalleCompraSessionBean implements DetalleCompraSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(DetalleCompra detalleCompra) {
        DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
        detalleCompraJpaController.create(detalleCompra);
    }

    @Override
    public void edit(DetalleCompra detalleCompra) throws InexistenteException {
        try {
            DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
            detalleCompraJpaController.edit(detalleCompra);
        } catch (Exception ex) {
            Logger.getLogger(DetalleCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
            detalleCompraJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public DetalleCompra findDetalleCompra(int id) {
        DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
        return detalleCompraJpaController.findDetalleCompra(id);
    }

    @Override
    public List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult) {
        DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
        return detalleCompraJpaController.findDetalleCompraEntities(maxResults, firstResult);
    }

    @Override
    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
            productos = detalleCompraJpaController.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(DetalleCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }

    @Override
    public List<Compra> getAllCompras() {
        List<Compra> compras = new ArrayList<Compra>();
        try {
            DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
            compras = detalleCompraJpaController.getAllCompras();
        } catch (Exception ex) {
            Logger.getLogger(DetalleCompraSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compras;
    }
}
