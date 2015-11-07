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
import javax.persistence.criteria.Root;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.controladores.exceptions.PreexistingEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.PersonaPK;

/**
 *
 * @author YEISSON
 */
public class AgricultorJpaController implements Serializable {

    public AgricultorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agricultor agricultor) throws PreexistingEntityException, Exception {
        if (agricultor.getLlavePrimaria() == null) {
            agricultor.setLlavePrimaria(new PersonaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(agricultor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAgricultor(agricultor.getLlavePrimaria()) != null) {
                throw new PreexistingEntityException("Agricultor " + agricultor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agricultor agricultor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            agricultor = em.merge(agricultor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PersonaPK id = agricultor.getLlavePrimaria();
                if (findAgricultor(id) == null) {
                    throw new NonexistentEntityException("The agricultor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PersonaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agricultor agricultor;
            try {
                agricultor = em.getReference(Agricultor.class, id);
                agricultor.getLlavePrimaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agricultor with id " + id + " no longer exists.", enfe);
            }
            em.remove(agricultor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agricultor> findAgricultorEntities() {
        return findAgricultorEntities(true, -1, -1);
    }

    public List<Agricultor> findAgricultorEntities(int maxResults, int firstResult) {
        return findAgricultorEntities(false, maxResults, firstResult);
    }

    private List<Agricultor> findAgricultorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agricultor.class));
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

    public Agricultor findAgricultor(PersonaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agricultor.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgricultorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agricultor> rt = cq.from(Agricultor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Agricultor> completarAgricultor(String parametro) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Agricultor> cq = cb.createQuery(Agricultor.class);
            Root<Agricultor> aw = cq.from(Agricultor.class);
            cq.select(aw);
            Expression<String> nombre = cb.concat(aw.<String>get("nombre"), " ");
            Expression<String> apellidoUno = cb.concat(aw.<String>get("apellidoUno"), " ");
            if (parametro != null && !parametro.isEmpty()) {
                cq.where(cb.or(
                        cb.like(cb.lower(aw.get("llavePrimaria").<String>get("documento")), "%" + parametro.toLowerCase() + "%"),
                        cb.like(cb.lower(cb.concat(nombre, cb.concat(apellidoUno, aw.<String>get("apellidoDos")))), "%" + parametro.toLowerCase() + "%")
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
