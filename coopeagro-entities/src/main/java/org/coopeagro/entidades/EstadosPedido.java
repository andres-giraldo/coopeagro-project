package org.coopeagro.entidades;

import java.io.Serializable;

public enum EstadosPedido implements Serializable{
    
    APROBADO("APROBADO"),
    PENDIENTE("PENDIENTE"),
    CANCELADO("CANCELADO");
    
    private String estadoPedido;
    
    private EstadosPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }
}
