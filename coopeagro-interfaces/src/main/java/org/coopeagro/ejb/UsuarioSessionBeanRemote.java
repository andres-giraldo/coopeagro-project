/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.coopeagro.entidades.Usuario;

/**
 *
 * @author YEISSON
 */
@Remote
public interface UsuarioSessionBeanRemote {

    boolean validarInicio(String usuario, String clave);

    Usuario findUsuarioForUserName(String usuario);

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void destroy(int id);

    Usuario findUsuario(int id);

    List<Usuario> findUsuarioEntities(int maxResults, int firstResult);
    
}
