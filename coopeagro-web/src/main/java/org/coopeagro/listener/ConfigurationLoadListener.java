/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.coopeagro.controllers.AgricultorJpaController;
import org.coopeagro.controllers.ClienteJpaController;
import org.coopeagro.controllers.CompraJpaController;
import org.coopeagro.controllers.DetalleCompraJpaController;
import org.coopeagro.controllers.DetalleVentaJpaController;
import org.coopeagro.controllers.EmpleadoJpaController;
import org.coopeagro.controllers.InventarioJpaController;
import org.coopeagro.controllers.PagoCompraJpaController;
import org.coopeagro.controllers.PagoVentaJpaController;
import org.coopeagro.controllers.ProductoJpaController;
import org.coopeagro.controllers.UsuarioJpaController;
import org.coopeagro.controllers.VentaJpaController;

/**
 * Web application lifecycle listener.
 *
 * @author sala306
 */
public class ConfigurationLoadListener implements ServletContextListener {

    /* Cuando se despliega la aplicación se llama este método */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");
        sce.getServletContext().setAttribute("entityManagerFactory", emf);
        
        AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
        sce.getServletContext().setAttribute("agricultorJpaController", agricultorJpaController
                );
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
        
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
        sce.getServletContext().setAttribute("usuarioJpaController", usuarioJpaController);
        
        VentaJpaController ventaJpaController = new VentaJpaController(emf);
        sce.getServletContext().setAttribute("ventaJpaController", ventaJpaController);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
