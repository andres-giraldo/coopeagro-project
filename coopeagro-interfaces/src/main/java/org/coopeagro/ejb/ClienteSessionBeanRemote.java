/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Cliente;
import org.coopeagro.entidades.PersonaPK;
import org.coopeagro.excepciones.InexistenteException;
import org.coopeagro.excepciones.DuplicadaException;

/**
 *
 * @author YEISSON
 */
@Remote
public interface ClienteSessionBeanRemote {

    void create(Cliente cliente)throws DuplicadaException, Exception;

    void edit(Cliente cliente)throws InexistenteException, Exception;

    void destroy(PersonaPK id)throws InexistenteException;

    Cliente findCliente(PersonaPK id);

    List<Cliente> findClienteEntities(int maxResults, int firstResult);

    List<Cliente> completarCliente(String parametro);
    
}
