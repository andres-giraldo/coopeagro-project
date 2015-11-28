package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.ClienteJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.controladores.exceptions.PreexistingEntityException;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.excepciones.DuplicadaException;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "ClienteBean", mappedName = "ejb/ClienteBean", beanInterface = ClienteSessionBeanRemote.class)
@Stateless
public class ClienteSessionBean implements ClienteSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

    @Override
    public void create(Cliente cliente) throws DuplicadaException {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.create(cliente);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new DuplicadaException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Cliente cliente) throws InexistenteException {
        try {
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.edit(cliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ClienteSessionBean.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
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

    @Override
    public List<Cliente> completarCliente(String parametro) {
        ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
        return clienteJpaController.completarCliente(parametro);
    }
}
