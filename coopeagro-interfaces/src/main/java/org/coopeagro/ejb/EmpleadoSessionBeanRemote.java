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

/**
 *
 * @author YEISSON
 */
@Remote
public interface EmpleadoSessionBeanRemote {

    void create(Empleado empleado);

    void edit(Empleado empleado);

    void destroy(PersonaPK id);

    Empleado findEmpleado(PersonaPK id);

    List<Empleado> findEmpleadoEntities(int maxResults, int firstResult);

    List<Empleado> completarEmpleado(String parametro);
    
}
