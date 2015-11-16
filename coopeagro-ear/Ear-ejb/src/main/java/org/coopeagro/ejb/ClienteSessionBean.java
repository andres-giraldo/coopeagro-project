package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.coopeagro.controladores.ClienteJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.PersonaPK;

@EJB(mappedName = "ejb/ClienteBean")
@Stateless
public class ClienteSessionBean implements ClienteSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(Cliente cliente) {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.create(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Cliente cliente) {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy(PersonaPK id) {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Cliente findCliente(PersonaPK id) {
        ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
        return clienteJpaController.findCliente(id);
    }

    @Override
    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
        return clienteJpaController.findClienteEntities(maxResults, firstResult);
    }
}
