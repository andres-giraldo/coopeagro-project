/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Empleado;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.excepciones.InexistenteException;
import org.coopeagro.excepciones.DuplicadaException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface EmpleadoSessionBeanRemote {

    void create(Empleado empleado)throws DuplicadaException, Exception;

    void edit(Empleado empleado)throws InexistenteException, Exception;

    void destroy(PersonaPK id)throws InexistenteException;

    Empleado findEmpleado(PersonaPK id);

    List<Empleado> findEmpleadoEntities(int maxResults, int firstResult);

    List<Empleado> completarEmpleado(String parametro);
    
}
