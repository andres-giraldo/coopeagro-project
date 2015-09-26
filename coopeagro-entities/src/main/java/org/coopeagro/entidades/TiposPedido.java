package org.coopeagro.entidades;

import java.io.Serializable;

public enum TiposPedido implements Serializable{
    COMPRA("COMPRA"),
    VENTA("VENTA");
    
    private String tipoPedido;

    private TiposPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
}
