/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Remote
public interface ProductoSessionBeanRemote {

    List<Object[]> getProductsAgricultor(String documento, org.coopeagro.entidades.TiposDocumento tipoDocumento);

    Producto findProductByCodigo(String codigo);

    List<Producto> completarProducto(String parametro);

    void create(Producto producto);

    void edit(Producto producto);

    void destroy(int id);

    Producto findProducto(int id);

    List<Producto> findProductoEntities(int maxResults, int firstResult);
    
}
