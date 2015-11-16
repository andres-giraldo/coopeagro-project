package org.coopeagro.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.coopeagro.controladores.VentaJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

@EJB(mappedName = "ejb/VentaBean")
@Stateless
public class VentaSessionBean implements VentaSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

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
        } catch (Exception ex) {
            Logger.getLogger(VentaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
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

    @Override
    public List<Object[]> getTotalVentasEmpleado() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        return ventaJpaController.getTotalVentasEmpleado();
    }

    @Override
    public double getPromedioVentas() {
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        double promedio = ventaJpaController.getPromedioVentas();
        return promedio;
    }
}
