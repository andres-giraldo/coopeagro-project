/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
