/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.PagoVenta;
import org.coopeagro.entidades.Venta;
import org.coopeagro.excepciones.InexistenteException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface PagoVentaSessionBeanRemote {

    void create(PagoVenta pagoVenta);

    void edit(PagoVenta pagoVenta)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    PagoVenta findPagoVenta(int id);

    List<PagoVenta> findPagoVentaEntities(int maxResults, int firstResult);

    List<Venta> getAllVentas();
    
}
