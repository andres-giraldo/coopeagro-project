/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Compra;
import org.coopeagro.entidades.DetalleCompra;
import org.coopeagro.entidades.Producto;
import org.coopeagro.excepciones.InexistenteException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface DetalleCompraSessionBeanRemote {

    void create(DetalleCompra detalleCompra);

    void edit(DetalleCompra detalleCompra)throws InexistenteException, Exception;

    void destroy(int id)throws InexistenteException;

    DetalleCompra findDetalleCompra(int id);

    List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult);

    List<Producto> getAllProducts();

    List<Compra> getAllCompras();
    
}
