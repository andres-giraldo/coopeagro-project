/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.DetalleVenta;
import org.coopeagro.entidades.Producto;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface DetalleVentaSessionBeanRemote {

    void create(DetalleVenta detalleVenta);

    void edit(DetalleVenta detalleVenta)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    DetalleVenta findDetalleVenta(int id);

    List<DetalleVenta> findDetalleVentaEntities(int maxResults, int firstResult);

    List<Producto> getAllProducts();

    List<Venta> getAllVentas();
    
}
