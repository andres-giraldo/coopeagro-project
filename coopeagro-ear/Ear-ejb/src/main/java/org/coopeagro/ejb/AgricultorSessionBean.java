package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.coopeagro.controladores.AgricultorJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.PersonaPK;

@EJB(mappedName = "ejb/AgricultorBean")
@Stateless
public class AgricultorSessionBean implements AgricultorSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(Agricultor agricultor) {
        try {
            AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
            agricultorJpaController.create(agricultor);
        } catch (Exception ex) {
            Logger.getLogger(AgricultorSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Agricultor agricultor) {
        try {
            AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
            agricultorJpaController.edit(agricultor);
        } catch (Exception ex) {
            Logger.getLogger(AgricultorSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy(PersonaPK id) {
        try {
            AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
            agricultorJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AgricultorSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Agricultor findAgricultor(PersonaPK id) {
        AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
        return agricultorJpaController.findAgricultor(id);
    }

    @Override
    public List<Agricultor> findAgricultorEntities(int maxResults, int firstResult) {
        AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
        return agricultorJpaController.findAgricultorEntities(maxResults, firstResult);
    }

    @Override
    public List<Agricultor> completarAgricultor(String parametro) {
        AgricultorJpaController agricultorJpaController = new AgricultorJpaController(emf);
        return agricultorJpaController.completarAgricultor(parametro);
    }
}
