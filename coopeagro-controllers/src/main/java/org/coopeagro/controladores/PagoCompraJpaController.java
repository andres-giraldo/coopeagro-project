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
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.PagoCompra;

/**
 *
 * @author YEISSON
 */
public class PagoCompraJpaController implements Serializable {

    public PagoCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoCompra pagoCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagoCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoCompra pagoCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagoCompra = em.merge(pagoCompra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoCompra.getId();
                if (findPagoCompra(id) == null) {
                    throw new NonexistentEntityException("The pagoCompra with id " + id + " no longer exists.");
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
            PagoCompra pagoCompra;
            try {
                pagoCompra = em.getReference(PagoCompra.class, id);
                pagoCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoCompra with id " + id + " no longer exists.", enfe);
            }
            em.remove(pagoCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoCompra> findPagoCompraEntities() {
        return findPagoCompraEntities(true, -1, -1);
    }

    public List<PagoCompra> findPagoCompraEntities(int maxResults, int firstResult) {
        return findPagoCompraEntities(false, maxResults, firstResult);
    }

    private List<PagoCompra> findPagoCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoCompra.class));
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

    public PagoCompra findPagoCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoCompra> rt = cq.from(PagoCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Compra> getAllCompras() throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("select c from Compra c");
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
}
