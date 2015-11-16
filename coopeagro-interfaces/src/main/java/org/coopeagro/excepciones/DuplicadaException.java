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
public class DuplicadaException extends Exception {

    /**
     * Creates a new instance of <code>PreexistingEntityException</code> without
     * detail message.
     */
    public DuplicadaException() {
    }

    /**
     * Constructs an instance of <code>PreexistingEntityException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicadaException(String msg) {
        super(msg);
    }
}
