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
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.Empleado;

/**
 *
 * @author YEISSON
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            compra = em.merge(compra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getNumeroPedido();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getNumeroPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Agricultor> getAllAgricultors() throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("select a from Agricultor a");
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
    
    public List<Empleado> getAllEmployees() throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("select e from Empleado e");
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
    
    public double getTotal(int numeroPedido){
        double total;
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select SUM(dc.cantidad*dc.precio) from DetalleCompra dc where dc.compra.numeroPedido = :numeroPedido");
        q.setParameter("numeroPedido", numeroPedido);
        total = (Double)q.getSingleResult();
        return total;
    }
    
    public long getTotalComprasTiempo(int anno, int mes){
        long count;
        EntityManager em = getEntityManager();
        Query q = null;
        if (anno != 0 && mes != 0) {
            q = em.createQuery("select COUNT(c.numeroPedido) from Compra c where EXTRACT(YEAR from c.fechaPedido) = :annoPedido and "
                    + "EXTRACT(MONTH from c.fechaPedido) = :mesPedido");
            q.setParameter("annoPedido", anno);
            q.setParameter("mesPedido", mes);
        }else if(anno != 0){
            q = em.createQuery("select COUNT(c.numeroPedido) from Compra c where EXTRACT(YEAR from c.fechaPedido) = :annoPedido");
            q.setParameter("annoPedido", anno);
        }else if(mes != 0){
            q = em.createQuery("select COUNT(c.numeroPedido) from Compra c where EXTRACT(MONTH from c.fechaPedido) = :mesPedido");
            q.setParameter("mesPedido", mes);
        }
        count = (Long) q.getSingleResult();
        return count;
    }
    
    public List<Object[]> getTotalComprasAgricultor(){
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select a.llavePrimaria.documento, a.llavePrimaria.tipoDocumento, a.nombre, a.apellidoUno, a.apellidoDos, COUNT(c.numeroPedido) from Compra c JOIN c.agricultor a GROUP BY a");
        return q.getResultList();
    }
    
    public List<Object[]> getTotalComprasEmpleado(){
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select e.llavePrimaria.documento, e.llavePrimaria.tipoDocumento, e.nombre, e.apellidoUno, e.apellidoDos, COUNT(c.numeroPedido) from Compra c JOIN c.empleado e GROUP BY e");
        return q.getResultList();
    }
    
    public double getPromedioCompras(){
        double total;
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root compra = cq.from(Compra.class);
        Expression avgExpression = cb.avg(compra.get("total"));
        cq.select(avgExpression);
        Query q = em.createQuery(cq);
        total = (Double)q.getSingleResult();
        return total;
    }
}
