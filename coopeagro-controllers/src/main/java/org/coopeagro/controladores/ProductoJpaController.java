/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            producto = em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> getProductsAgricultor(String documento, TiposDocumento tipoDocumento) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<DetalleCompra> dc = cq.from(DetalleCompra.class);
            Join<DetalleCompra, Producto> productos = dc.join("producto");
            Join<DetalleCompra, Compra> compras = dc.join("compra");
            Join<Compra, Agricultor> agricultores = compras.join("agricultor");
            cq.multiselect(dc, productos, compras, agricultores);
            Predicate criteria = cb.conjunction();
            if (documento != null) {
                ParameterExpression<String> documentoParameter = cb.parameter(String.class, "documento");
                criteria = cb.and(criteria, cb.equal(agricultores.get("llavePrimaria").get("documento"), documentoParameter));
            }
            if (tipoDocumento != null) {
                ParameterExpression<TiposDocumento> tipoDocumentoParameter = cb.parameter(TiposDocumento.class, "tipoDocumento");
                criteria = cb.and(criteria, cb.equal(agricultores.get("llavePrimaria").get("tipoDocumento"), tipoDocumentoParameter));
            }
            if (!criteria.getExpressions().isEmpty()) {
                cq.where(cb.and(criteria));
            }
            Query q = em.createQuery(cq);
            if (documento != null) {
                q.setParameter("documento", documento);
            }
            if (tipoDocumento != null) {
                q.setParameter("tipoDocumento", tipoDocumento);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProductByCodigo(String codigo) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :codigo");
            query.setParameter("codigo", codigo);
            Producto producto = (Producto) query.getSingleResult();
            return producto;
        } finally {
            em.close();
        }
    }

    public List<Producto> completarProducto(String parametro) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
            Root<Producto> pw = cq.from(Producto.class);
            cq.select(pw);
            if (parametro != null && !parametro.isEmpty()) {
                cq.where(cb.or(
                        cb.like(cb.lower(pw.<String>get("codigo")), "%" + parametro.toLowerCase() + "%"),
                        cb.like(cb.lower(pw.<String>get("nombre")), "%" + parametro.toLowerCase() + "%")
                ));
            }
            Query query = em.createQuery(cq);
            query.setMaxResults(10);
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
