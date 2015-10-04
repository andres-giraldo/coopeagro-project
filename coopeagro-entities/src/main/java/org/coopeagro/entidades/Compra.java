package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
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

    public Compra(Agricultor proveedor, Date fechaPedido, TiposPedido tipoPedido, Empleado empleado, EstadosPedido estado, Double total) {
        super(fechaPedido, tipoPedido, empleado, estado, total);
        this.proveedor = proveedor;
    }

    public Agricultor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Agricultor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Compra{" + "proveedor=" + proveedor + '}';
    }
}
