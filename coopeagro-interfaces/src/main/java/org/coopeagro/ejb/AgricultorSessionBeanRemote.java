/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Agricultor;
import org.coopeagro.entidades.PersonaPK;

/**
 *
 * @author YEISSON
 */
@Remote
public interface AgricultorSessionBeanRemote {

    void create(Agricultor agricultor);

    void edit(Agricultor agricultor);

    void destroy(PersonaPK id);

    Agricultor findAgricultor(PersonaPK id);

    List<Agricultor> findAgricultorEntities(int maxResults, int firstResult);

    List<Agricultor> completarAgricultor(String parametro);
    
}
