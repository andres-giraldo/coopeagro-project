package org.coopeagro.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.coopeagro.controladores.EmpleadoJpaController;
import org.coopeagro.controladores.exceptions.NonexistentEntityException;
import org.coopeagro.controladores.exceptions.PreexistingEntityException;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.excepciones.DuplicadaException;
import org.coopeagro.excepciones.InexistenteException;

@EJB(name = "EmpleadoBean", mappedName = "ejb/EmpleadoBean", beanInterface = EmpleadoSessionBeanRemote.class)
@Stateless
public class EmpleadoSessionBean implements EmpleadoSessionBeanRemote {

    @PersistenceUnit(unitName = "coopeagroPU")
    EntityManagerFactory emf;

    @Override
    public void create(Empleado empleado) throws DuplicadaException {
        try {
            EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
            empleadoJpaController.create(empleado);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new DuplicadaException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void edit(Empleado empleado) throws InexistenteException {
        try {
            EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
            empleadoJpaController.edit(empleado);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy(PersonaPK id) throws InexistenteException {
        try {
            EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
            empleadoJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new InexistenteException(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Empleado findEmpleado(PersonaPK id) {
        EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
        return empleadoJpaController.findEmpleado(id);
    }

    @Override
    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
        return empleadoJpaController.findEmpleadoEntities(maxResults, firstResult);
    }

    @Override
    public List<Empleado> completarEmpleado(String parametro) {
        EmpleadoJpaController empleadoJpaController = new EmpleadoJpaController(emf);
        return empleadoJpaController.completarEmpleado(parametro);
    }
}
