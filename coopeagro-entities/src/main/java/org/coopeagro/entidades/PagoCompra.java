package org.coopeagro.entidades;

import java.io.Serializable;
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
    private Double cantidadCancelada;
    
    public PagoCompra(){
    }

    public PagoCompra(PagoCompraPK llavePrimaria, Double cantidadCancelada) {
        this.llavePrimaria = llavePrimaria;
        this.cantidadCancelada = cantidadCancelada;
    }

    public Double getCantidadCancelada() {
        return cantidadCancelada;
    }

    public void setCantidadCancelada(Double cantidadCancelada) {
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
