package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TCA_COMPRAS")
public class Compra extends Pedido implements Serializable{
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_AGRICULTOR"),
        @JoinColumn(name = "DSTIPODOCUMENTO_AGRICULTOR")
    })
    private Agricultor proveedor;

    public Compra() {
    }

    public Agricultor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Agricultor proveedor) {
        this.proveedor = proveedor;
    }
}
