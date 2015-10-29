/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.controladores.ClienteJpaController;
import org.coopeagro.controladores.CompraJpaController;
import org.coopeagro.controladores.DetalleCompraJpaController;
import org.coopeagro.controladores.DetalleVentaJpaController;
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.controladores.InventarioJpaController;
import org.coopeagro.controladores.PagoCompraJpaController;
import org.coopeagro.controladores.PagoVentaJpaController;
import org.coopeagro.controladores.ProductoJpaController;
import org.coopeagro.controladores.UsuarioJpaController;
import org.coopeagro.controladores.VentaJpaController;

/**
 * Web application lifecycle listener.
 *
 * @author sala306
 */
public class ConfigurationLoadListener implements ServletContextListener {
    EntityManagerFactory emf = null;
    /* Cuando se despliega la aplicación se llama este método */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("coopeagroPU");
        sce.getServletContext().setAttribute("entityManagerFactory", emf);
        
        AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
        sce.getServletContext().setAttribute("agricultorJpaController", agricultorJpaController);
        
        ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
        sce.getServletContext().setAttribute("clienteJpaController", clienteJpaController);
        
        CompraJpaController compraJpaController = new CompraJpaController(emf);
        sce.getServletContext().setAttribute("compraJpaController", compraJpaController);
        
        DetalleCompraJpaController detalleCompraJpaController = new DetalleCompraJpaController(emf);
        sce.getServletContext().setAttribute("detalleCompraJpaController", detalleCompraJpaController);
        
        DetalleVentaJpaController detalleVentaJpaController = new DetalleVentaJpaController(emf);
        sce.getServletContext().setAttribute("detalleVentaJpaController", detalleVentaJpaController);
        
        EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
        sce.getServletContext().setAttribute("empleadoJpaController", empleadoJpaController);
        
        InventarioJpaController inventarioJpaController = new InventarioJpaController(emf);
        sce.getServletContext().setAttribute("inventarioJpaController", inventarioJpaController);
        
        PagoCompraJpaController pagoCompraJpaController = new PagoCompraJpaController(emf);
        sce.getServletContext().setAttribute("pagoCompraJpaController", pagoCompraJpaController);
        
        PagoVentaJpaController pagoVentaJpaController = new PagoVentaJpaController(emf);
        sce.getServletContext().setAttribute("pagoVentaJpaController", pagoVentaJpaController);
        
        ProductoJpaController productoJpaController = new ProductoJpaController(emf);
        sce.getServletContext().setAttribute("productoJpaController", productoJpaController);
        
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        sce.getServletContext().setAttribute("ventaJpaController", ventaJpaController);
        
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
        sce.getServletContext().setAttribute("usuarioJpaController", usuarioJpaController);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(emf.isOpen()){
            emf.close();
        }
    }
}
