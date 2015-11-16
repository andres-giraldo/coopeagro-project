/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Inventario;
import org.coopeagro.entidades.Producto;

/**
 *
 * @author YEISSON
 */
@Remote
public interface InventarioSessionBeanRemote {

    Double getDisponibilidad(int producto);

    org.coopeagro.entidades.Inventario getMax(int producto);

    void create(Inventario inventario);

    void edit(Inventario inventario);

    void destroy(int id);

    Inventario findInventario(int id);

    List<Inventario> findInventarioEntities(int maxResults, int firstResult);

    List<Producto> getAllProducts();
    
}
