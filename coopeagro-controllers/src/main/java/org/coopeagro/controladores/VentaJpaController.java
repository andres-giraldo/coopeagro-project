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
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.PagoVenta;
import org.coopeagro.entidades.Venta;

/**
 *
 * @author YEISSON
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            venta = em.merge(venta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getNumeroPedido();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getNumeroPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Cliente> getAllCustomers() throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("select c from Cliente c");
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
    
    /*public double getTotal(int numeroPedido){
        double total;
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select SUM(dv.cantidad*dv.precio) from DetalleVenta dv where dv.venta.numeroPedido = :numeroPedido");
        q.setParameter("numeroPedido", numeroPedido);
        total = (Double)q.getSingleResult();
        return total;
    }*/
    
    public long getTotalVentasTiempo(int anno, int mes){
        long count;
        EntityManager em = getEntityManager();
        Query q = null;
        if (anno != 0 && mes != 0) {
            q = em.createQuery("select COUNT(v.numeroPedido) from Venta v where EXTRACT(YEAR from v.fechaPedido) = :annoPedido and "
                    + "EXTRACT(MONTH from v.fechaPedido) = :mesPedido");
            q.setParameter("annoPedido", anno);
            q.setParameter("mesPedido", mes);
        }else if(anno != 0){
            q = em.createQuery("select COUNT(v.numeroPedido) from Venta v where EXTRACT(YEAR from v.fechaPedido) = :annoPedido");
            q.setParameter("annoPedido", anno);
        }else if(mes != 0){
            q = em.createQuery("select COUNT(v.numeroPedido) from Venta v where EXTRACT(MONTH from v.fechaPedido) = :mesPedido");
            q.setParameter("mesPedido", mes);
        }
        if (!q.getResultList().isEmpty()) {
            count = (Long)q.getSingleResult();
        }else{
            count = 0;
        }
        return count;
    }
    
    public List<Object[]> getTotalVentasCliente(){
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select cl.llavePrimaria.documento, cl.llavePrimaria.tipoDocumento, cl.nombre, cl.apellidoUno, cl.apellidoDos, COUNT(v.numeroPedido) from Venta v JOIN v.cliente cl GROUP BY cl");
        return q.getResultList();
    }
    
    /*public List<Object[]> getTotalVentasEmpleado(){
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select e.llavePrimaria.documento, e.llavePrimaria.tipoDocumento, e.nombre, e.apellidoUno, e.apellidoDos, COUNT(v.numeroPedido) from Venta v JOIN v.empleado e GROUP BY e");
        return q.getResultList();
    }*/
    
    public double getPromedioVentas(){
        double promedio;
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        Root venta = cq.from(Venta.class);
        Expression avgExpression = cb.avg(venta.get("total"));
        cq.select(avgExpression);
        Query q = em.createQuery(cq);
        if (!q.getResultList().isEmpty()) {
            promedio = (Double)q.getSingleResult();
        }else{
            promedio = 0.0;
        }
        return promedio;
    }
    
    public Venta getMaxOrder(){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq = cb.createQuery(Venta.class);
        Root<Venta> venta = cq.from(Venta.class);
        cq.select(venta);
        cq.orderBy(cb.desc(venta.get("numeroPedido")));
        Query q = em.createQuery(cq);
        q.setMaxResults(1);
        return (Venta) q.getSingleResult();
    }
    
    public List<DetalleVenta> getDetalles(int venta){
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DetalleVenta> cq = cb.createQuery(DetalleVenta.class);
        Root<DetalleVenta> dv = cq.from(DetalleVenta.class);
        cq.select(dv);
        Predicate criteria = cb.conjunction();
        cq.where(cb.and(criteria));
        if (venta != 0) {
            ParameterExpression<Integer> ventaParameter = cb.parameter(Integer.class, "venta");
            criteria = cb.and(criteria, cb.equal(dv.get("venta").get("numeroPedido"), ventaParameter));
        }
        if (!criteria.getExpressions().isEmpty()) {
            cq.where(cb.and(criteria));
        }
        Query q = em.createQuery(cq);
        if (venta != 0) {
            q.setParameter("venta", venta);
        }
        return q.getResultList();
    }
    
    public long pagosVenta(int venta){
        long pagos;
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<PagoVenta> pc = cq.from(PagoVenta.class);
        cq.select(cb.countDistinct(pc));
        Predicate criteria = cb.conjunction();
        cq.where(cb.and(criteria));
        if (venta != 0) {
            ParameterExpression<Integer> compraParameter = cb.parameter(Integer.class, "venta");
            criteria = cb.and(criteria, cb.equal(pc.get("venta").get("numeroPedido"), compraParameter));
        }
        if (!criteria.getExpressions().isEmpty()) {
            cq.where(cb.and(criteria));
        }
        Query q = em.createQuery(cq);
        if (venta != 0) {
            q.setParameter("venta", venta);
        }
        if (!q.getResultList().isEmpty()) {
            pagos = (Long)q.getSingleResult();
        }else{
            pagos = 0;
        }
        return pagos;
    }
}
