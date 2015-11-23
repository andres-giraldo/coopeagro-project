package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.TiposDocumento;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "ProductoBean", mappedName = "ejb/ProductoBean", beanInterface = ProductoSessionBeanRemote.class)
@Stateless
public class ProductoSessionBean implements ProductoSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

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
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ProductoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(ProductoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
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
