package org.coopeagro.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TCA_PAGO_COMPRAS")
public class PagoCompra implements Serializable{
    
    @EmbeddedId
    private PagoCompraPK llavePrimaria;
    @Column(name="NMCANTIDADCANCELADA")
    private BigDecimal cantidadCancelada;
    
    public PagoCompra(){
    }

    public BigDecimal getCantidadCancelada() {
        return cantidadCancelada;
    }

    public void setCantidadCancelada(BigDecimal cantidadCancelada) {
        this.cantidadCancelada = cantidadCancelada;
    }

    public PagoCompraPK getLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(PagoCompraPK llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    @Override
    public String toString() {
        return "PagoCompra{" + "llavePrimaria=" + llavePrimaria + ", cantidadCancelada=" + cantidadCancelada + '}';
    }
}
