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
import org.coopeagro.controladores.InventarioJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.Producto;

@EJB(mappedName = "ejb/InventarioBean")
@Stateless
public class InventarioSessionBean implements InventarioSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(Inventario inventario) {
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        inventarioJpaController.create(inventario);
    }
    
    @Override
    public void edit(Inventario inventario) {
        try {
            InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
            inventarioJpaController.edit(inventario);
        } catch (Exception ex) {
            Logger.getLogger(InventarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy(int id) {
        try {
            InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
            inventarioJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(InventarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Inventario findInventario(int id) {
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        return inventarioJpaController.findInventario(id);
    }
    
    @Override
    public List<Inventario> findInventarioEntities(int maxResults, int firstResult) {
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        return inventarioJpaController.findInventarioEntities(maxResults, firstResult);
    }
    
    @Override
    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<Producto>();
        try {
            InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
            productos = inventarioJpaController.getAllProducts();
        } catch (Exception ex) {
            Logger.getLogger(InventarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productos;
    }
    
    @Override
    public Double getDisponibilidad(int producto) {
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        double disponibilidad = inventarioJpaController.getDisponibilidad(producto);
        return disponibilidad;
    }

    @Override
    public Inventario getMax(int producto) {
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        return inventarioJpaController.getMax(producto);
    }
}
