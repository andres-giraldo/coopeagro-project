package org.coopeagro.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.VentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "VentaBean", mappedName = "ejb/VentaBean", beanInterface = VentaSessionBeanRemote.class)
@Stateless
public class VentaSessionBean implements VentaSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

    @Override
    public void create(Venta venta) {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        ventaJpaController.create(venta);
    }
    
    @Override
    public void edit(Venta venta) throws InexistenteException {
        try {
            VentaJpaController ventaJpaController = new VentaJpaController(emf);
            ventaJpaController.edit(venta);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            VentaJpaController ventaJpaController = new VentaJpaController(emf);
            ventaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Venta findVenta(int id) {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.findVenta(id);
    }
    
    @Override
    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.findVentaEntities(maxResults, firstResult);
    }
    
    @Override
    public List<Cliente> getAllCustomers() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        try {
            VentaJpaController ventaJpaController = new VentaJpaController(emf);
            clientes = ventaJpaController.getAllCustomers();
        } catch (Exception ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }
    
    @Override
    public List<Empleado> getAllEmployees() {
        List<Empleado> empleados = new ArrayList<Empleado>();
        try {
            VentaJpaController ventaJpaController = new VentaJpaController(emf);
            empleados = ventaJpaController.getAllEmployees();
        } catch (Exception ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }
    
    @Override
    public long getTotalVentasTiempo(int anno, int mes) {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        long tv = ventaJpaController.getTotalVentasTiempo(anno, mes);
        return tv;
    }

    @Override
    public List<Object[]> getTotalVentasCliente() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.getTotalVentasCliente();
    }

    /*@Override
    public List<Object[]> getTotalVentasEmpleado() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.getTotalVentasEmpleado();
    }*/

    @Override
    public double getPromedioVentas() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        double promedio = ventaJpaController.getPromedioVentas();
        return promedio;
    }

    @Override
    public Venta getMaxOrder() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.getMaxOrder();
    }

    @Override
    public List<DetalleVenta> getDetalles(int venta) {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.getDetalles(venta);
    }
}
