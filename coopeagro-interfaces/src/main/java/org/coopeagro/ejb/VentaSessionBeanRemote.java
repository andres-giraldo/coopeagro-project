/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.Venta;

/**
 *
 * @author YEISSON
 */
@Remote
public interface VentaSessionBeanRemote {

    long getTotalVentasTiempo(int anno, int mes);

    List<Object[]> getTotalVentasCliente();

    List<Object[]> getTotalVentasEmpleado();

    double getPromedioVentas();

    void create(Venta venta);

    void edit(Venta venta);

    void destroy(int id);

    Venta findVenta(int id);

    List<Venta> findVentaEntities(int maxResults, int firstResult);

    List<Cliente> getAllCustomers();

    List<Empleado> getAllEmployees();
    
}
