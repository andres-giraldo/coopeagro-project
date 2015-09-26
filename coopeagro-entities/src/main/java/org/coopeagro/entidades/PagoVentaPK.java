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
public class PagoVentaPK implements Serializable{
    
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPAGO")
    private Date fechaPago;
    @OneToOne
    @JoinColumn(name = "DNI_VENTA")
    private Venta venta;

    public PagoVentaPK() {
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
}
