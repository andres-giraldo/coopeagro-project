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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
public class InventarioJpaController implements Serializable {

    public InventarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inventario inventario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(inventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inventario inventario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            inventario = em.merge(inventario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inventario.getId();
                if (findInventario(id) == null) {
                    throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.");
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
            Inventario inventario;
            try {
                inventario = em.getReference(Inventario.class, id);
                inventario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventario with id " + id + " no longer exists.", enfe);
            }
            em.remove(inventario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inventario> findInventarioEntities() {
        return findInventarioEntities(true, -1, -1);
    }

    public List<Inventario> findInventarioEntities(int maxResults, int firstResult) {
        return findInventarioEntities(false, maxResults, firstResult);
    }

    private List<Inventario> findInventarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inventario.class));
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

    public Inventario findInventario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inventario.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inventario> rt = cq.from(Inventario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Producto> getAllProducts() throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("select p from Producto p");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Double getDisponibilidad(int producto){
        Double cantidadDisponible;
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root<Inventario> inventario = cq.from(Inventario.class);
        Expression<Double> cantidadTotal = inventario.get("cantidadTotal");
        Expression<Double> cantidadComprometida = inventario.get("cantidadComprometida");
        cq.select(cb.diff(cantidadTotal, cantidadComprometida));
        Predicate criteria = cb.conjunction();
        cq.where(cb.and(criteria));
        if (producto != 0) {
            ParameterExpression<Integer> productoParameter = cb.parameter(Integer.class, "producto");
            criteria = cb.and(criteria, cb.equal(inventario.get("producto").get("id"), productoParameter));
        }
        if (!criteria.getExpressions().isEmpty()) {
            cq.where(cb.and(criteria));
        }
        cq.orderBy(cb.desc(inventario.get("id")));
        Query q = em.createQuery(cq);
        if (producto != 0) {
            q.setParameter("producto", producto);
        }
        q.setMaxResults(1);
        if (!q.getResultList().isEmpty()) {
            cantidadDisponible = (Double) q.getSingleResult();
        }else{
            cantidadDisponible = 0.0;
        }
        return cantidadDisponible;
    }
    
    public Inventario getMax(int producto){
        Inventario inv = new Inventario();
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Inventario> cq = cb.createQuery(Inventario.class);
        Root<Inventario> inventario = cq.from(Inventario.class);
        cq.select(inventario);
        Predicate criteria = cb.conjunction();
        cq.where(cb.and(criteria));
        if (producto != 0) {
            ParameterExpression<Integer> productoParameter = cb.parameter(Integer.class, "producto");
            criteria = cb.and(criteria, cb.equal(inventario.get("producto").get("id"), productoParameter));
        }
        if (!criteria.getExpressions().isEmpty()) {
            cq.where(cb.and(criteria));
        }
        cq.orderBy(cb.desc(inventario.get("id")));
        Query q = em.createQuery(cq);
        if (producto != 0) {
            q.setParameter("producto", producto);
        }
        q.setMaxResults(1);
        if (!q.getResultList().isEmpty()) {
            inv = (Inventario) q.getSingleResult();
        }
        return inv;
    }
}
