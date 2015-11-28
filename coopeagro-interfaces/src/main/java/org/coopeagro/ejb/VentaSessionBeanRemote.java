/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface VentaSessionBeanRemote {

    long getTotalVentasTiempo(int anno, int mes);

    List<Object[]> getTotalVentasCliente();

    //List<Object[]> getTotalVentasEmpleado();

    double getPromedioVentas();

    void create(Venta venta);

    void edit(Venta venta)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    Venta findVenta(int id);

    List<Venta> findVentaEntities(int maxResults, int firstResult);

    List<Cliente> getAllCustomers();

    List<Empleado> getAllEmployees();

    Venta getMaxOrder();

    List<DetalleVenta> getDetalles(int venta);

    long pagosVenta(int venta);
    
}
