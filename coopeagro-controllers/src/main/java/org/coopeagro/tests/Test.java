/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.coopeagro.tests;

import java.util.Arrays;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.controladores.InventarioJpaController;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.VentaJpaController;
import org.coopeagro.entidades.TiposDocumento;

/**
 *
 * @author YEISSON
 */
public class Test {
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        for (Object[] pa : productoJpaController.getProductsAgricultor("2000", TiposDocumento.CC)) {
            System.out.println(pa[1]);
        }
            
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        long tc = compraJpaController.getTotalComprasTiempo(0, 10);
        System.out.println(tc);

        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        long tv = ventaJpaController.getTotalVentasTiempo(2015, 11);
        System.out.println(tv);
        
        //VentaJpaController ventaJpaController = new VentaJpaController(emf);
        for (Object[] tvc : ventaJpaController.getTotalVentasCliente()) {
            System.out.println("Ventas x cliente"+Arrays.toString(tvc));
        }
        
        //CompraJpaController compraJpaController = new CompraJpaController(emf);
        for (Object[] tca : compraJpaController.getTotalComprasAgricultor()) {
            System.out.println(Arrays.toString(tca));
        }
        
        //VentaJpaController ventaJpaController = new VentaJpaController(emf);
        for (Object[] tve : ventaJpaController.getTotalVentasEmpleado()) {
            System.out.println("Ventas x empleado"+tve[0]);
            System.out.println("Ventas x empleado"+tve[1]);
            System.out.println("Ventas x empleado"+tve[2]);
            System.out.println("Ventas x empleado"+tve[3]);
            System.out.println("Ventas x empleado"+tve[4]);
            System.out.println("Ventas x empleado"+tve[5]);
        }
        
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        double dp = inventarioJpaController.getDisponibilidad(1);
        System.out.println(dp);
        
        //VentaJpaController ventaJpaController = new VentaJpaController(emf);
//        double total = ventaJpaController.getPromedioVentas();
//        System.out.println(total);
        
        System.out.println(inventarioJpaController.getMax(2).toString());
    }
}
