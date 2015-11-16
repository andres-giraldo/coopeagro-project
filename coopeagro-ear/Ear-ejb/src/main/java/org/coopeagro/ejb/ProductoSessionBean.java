package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.excepciones.InexistenteException;

@EJB(mappedName = "ejb/ProductoBean")
@Stateless
public class ProductoSessionBean implements ProductoSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(Producto producto) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        productoJpaController.create(producto);
    }
    
    @Override
    public void edit(Producto producto) throws InexistenteException {
        try {
            ProductoJpaController productoJpaController = new ProductoJpaController(emf);
            productoJpaController.edit(producto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }
    
    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            ProductoJpaController productoJpaController = new ProductoJpaController(emf);
            productoJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }
    
    @Override
    public Producto findProducto(int id) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        return productoJpaController.findProducto(id);
    }
    
    @Override
    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        return productoJpaController.findProductoEntities(maxResults, firstResult);
    }
    
    @Override
    public List<Object[]> getProductsAgricultor(String documento, TiposDocumento tipoDocumento) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        return productoJpaController.getProductsAgricultor(documento, tipoDocumento);
    }

    @Override
    public Producto findProductByCodigo(String codigo) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        return productoJpaController.findProductByCodigo(codigo);
    }

    @Override
    public List<Producto> completarProducto(String parametro) {
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        return productoJpaController.completarProducto(parametro);
    }
}
