/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.PagoCompra;
import org.coopeagro.excepciones.InexistenteException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface PagoCompraSessionBeanRemote {

    void create(PagoCompra pagoCompra);

    void edit(PagoCompra pagoCompra)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    PagoCompra findPagoCompra(int id);

    List<PagoCompra> findPagoCompraEntities(int maxResults, int firstResult);

    List<Compra> getAllCompras();
    
}
