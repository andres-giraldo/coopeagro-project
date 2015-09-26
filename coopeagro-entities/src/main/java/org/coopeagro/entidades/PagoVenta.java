package org.coopeagro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
    private BigDecimal cantidadCancelada;

    public PagoVenta() {
    }

    public PagoVentaPK getLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(PagoVentaPK llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    public BigDecimal getCantidadCancelada() {
        return cantidadCancelada;
    }

    public void setCantidadCancelada(BigDecimal cantidadCancelada) {
        this.cantidadCancelada = cantidadCancelada;
    }

    @Override
    public String toString() {
        return "PagoVenta{" + "llavePrimaria=" + llavePrimaria + ", cantidadCancelada=" + cantidadCancelada + '}';
    }
}
