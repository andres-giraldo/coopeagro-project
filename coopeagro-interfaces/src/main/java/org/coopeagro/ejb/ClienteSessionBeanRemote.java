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

/**
 *
 * @author YEISSON
 */
@Remote
public interface ClienteSessionBeanRemote {

    void create(Cliente cliente);

    void edit(Cliente cliente);

    void destroy(PersonaPK id);

    Cliente findCliente(PersonaPK id);

    List<Cliente> findClienteEntities(int maxResults, int firstResult);
    
}
