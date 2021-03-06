package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.UsuarioJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.controladores.exceptions.PreexistingEntityException;
import org.coopeagro.entidades.Usuario;
import org.coopeagro.excepciones.DuplicadaException;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "UsuarioBean", mappedName = "ejb/UsuarioBean", beanInterface = UsuarioSessionBeanRemote.class)
@Stateless
public class UsuarioSessionBean implements UsuarioSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

    @Override
    public void create(Usuario usuario) throws DuplicadaException {
        try {
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
            usuarioJpaController.create(usuario);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new DuplicadaException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void edit(Usuario usuario) throws InexistenteException {
        try {
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
            usuarioJpaController.edit(usuario);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void destroy(int id) throws InexistenteException {
        try {
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
            usuarioJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Usuario findUsuario(int id) {
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
        return usuarioJpaController.findUsuario(id);
    }
    
    @Override
    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
        return usuarioJpaController.findUsuarioEntities(maxResults, firstResult);
    }
    
    @Override
    public boolean validarInicio(String usuario, String clave) {
        try {
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
            return usuarioJpaController.validarInicio(usuario, clave);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Usuario findUsuarioForUserName(String usuario) {
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(emf);
        return usuarioJpaController.findUsuarioForUserName(usuario);
    }
}
