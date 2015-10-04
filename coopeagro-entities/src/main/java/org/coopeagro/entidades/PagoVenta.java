package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TCA_PAGO_VENTAS")
public class PagoVenta implements Serializable{
    
    @EmbeddedId
    private PagoVentaPK llavePrimaria;
    @Column(name="NMCANTIDADCANCELADA")
    private Double cantidadCancelada;

    public PagoVenta() {
    }

    public PagoVenta(PagoVentaPK llavePrimaria, Double cantidadCancelada) {
        this.llavePrimaria = llavePrimaria;
        this.cantidadCancelada = cantidadCancelada;
    }

    public PagoVentaPK getLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(PagoVentaPK llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    public Double getCantidadCancelada() {
        return cantidadCancelada;
    }

    public void setCantidadCancelada(Double cantidadCancelada) {
        this.cantidadCancelada = cantidadCancelada;
    }

    @Override
    public String toString() {
        return "PagoVenta{" + "llavePrimaria=" + llavePrimaria + ", cantidadCancelada=" + cantidadCancelada + '}';
    }
}
