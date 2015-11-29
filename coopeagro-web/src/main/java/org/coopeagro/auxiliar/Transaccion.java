/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.auxiliar;

import java.io.Serializable;
import org.coopeagro.entidades.PersonaPK;

/**
 *
 * @author YEISSON
 */
public class Transaccion implements Serializable{
    private PersonaPK personaOrigen;
    private PersonaPK personaDestino;
    private Double total;

    public PersonaPK getPersonaOrigen() {
        return personaOrigen;
    }

    public void setPersonaOrigen(PersonaPK personaOrigen) {
        this.personaOrigen = personaOrigen;
    }

    public PersonaPK getPersonaDestino() {
        return personaDestino;
    }

    public void setPersonaDestino(PersonaPK personaDestino) {
        this.personaDestino = personaDestino;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
