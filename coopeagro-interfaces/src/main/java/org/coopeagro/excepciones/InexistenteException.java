/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coopeagro.excepciones;

/**
 *
 * @author YEISSON
 */
public class InexistenteException extends Exception {

    /**
     * Creates a new instance of <code>NonexistentEntityException</code> without
     * detail message.
     */
    public InexistenteException() {
    }

    /**
     * Constructs an instance of <code>NonexistentEntityException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InexistenteException(String msg) {
        super(msg);
    }
}
