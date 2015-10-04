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
import org.coopeagro.controladores.exceptions.PreexistingEntityException;
import org.coopeagro.entidades.PagoVenta;
import org.coopeagro.entidades.PagoVentaPK;

/**
 *
 * @author YEISSON
 */
public class PagoVentaJpaController implements Serializable {

    public PagoVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagoVenta pagoVenta) throws PreexistingEntityException, Exception {
        if (pagoVenta.getLlavePrimaria() == null) {
            pagoVenta.setLlavePrimaria(new PagoVentaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagoVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPagoVenta(pagoVenta.getLlavePrimaria()) != null) {
                throw new PreexistingEntityException("PagoVenta " + pagoVenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagoVenta pagoVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagoVenta = em.merge(pagoVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PagoVentaPK id = pagoVenta.getLlavePrimaria();
                if (findPagoVenta(id) == null) {
                    throw new NonexistentEntityException("The pagoVenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PagoVentaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagoVenta pagoVenta;
            try {
                pagoVenta = em.getReference(PagoVenta.class, id);
                pagoVenta.getLlavePrimaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoVenta with id " + id + " no longer exists.", enfe);
            }
            em.remove(pagoVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagoVenta> findPagoVentaEntities() {
        return findPagoVentaEntities(true, -1, -1);
    }

    public List<PagoVenta> findPagoVentaEntities(int maxResults, int firstResult) {
        return findPagoVentaEntities(false, maxResults, firstResult);
    }

    private List<PagoVenta> findPagoVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagoVenta.class));
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

    public PagoVenta findPagoVenta(PagoVentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagoVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagoVenta> rt = cq.from(PagoVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
