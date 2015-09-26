package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class PagoCompraPK implements Serializable{
    
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPAGO")
    private Date fechaPago;
    @OneToOne
    @JoinColumn(name = "DNI_COMPRA")
    private Compra compra;

    public PagoCompraPK() {
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public String toString() {
        return "PagoCompraPK{" + "fechaPago=" + fechaPago + ", compra=" + compra + '}';
    }
}
