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
public class ViolacionForaneaException extends Exception {

    /**
     * Creates a new instance of <code>IllegalOrphanException</code> without
     * detail message.
     */
    public ViolacionForaneaException() {
    }

    /**
     * Constructs an instance of <code>IllegalOrphanException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ViolacionForaneaException(String msg) {
        super(msg);
    }
}
