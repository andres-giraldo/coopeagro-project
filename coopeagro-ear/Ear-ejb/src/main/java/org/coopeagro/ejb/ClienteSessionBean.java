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
import org.coopeagro.excepciones.DuplicadaException;
import org.coopeagro.excepciones.InexistenteException;

@Stateless
@EJB(name = "ClienteBean", mappedName = "ejb/ClienteBean", beanInterface = ClienteSessionBeanRemote.class)
public class ClienteSessionBean implements ClienteSessionBeanRemote {

    @PersistenceContext(unitName = "coopeagroPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("coopeagroPU");

    @Override
    public void create(Cliente cliente) throws DuplicadaException {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.create(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new DuplicadaException(ex.getMessage());
        }
    }

    @Override
    public void edit(Cliente cliente) throws InexistenteException {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        }
    }

    @Override
    public void destroy(PersonaPK id) throws InexistenteException {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
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
