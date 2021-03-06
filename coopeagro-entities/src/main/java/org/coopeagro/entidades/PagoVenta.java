package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_PAGO_VENTAS")
public class PagoVenta implements Serializable{
     @TableGenerator(name="PagoVentaGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="PAGO_VENTA_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false) 
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PagoVentaGen")
    @Column(name = "DNIPAGOVENTA")
    private Integer id;
    @Basic(optional = false)
    @Column(name="NMCANTIDADCANCELADA")
    private Double cantidadCancelada;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPAGO")
    private Date fechaPago;
    @OneToOne
    @JoinColumn(name = "DNIPEDIDO_VENTA", referencedColumnName = "DNIPEDIDO", nullable = false)
    private Venta venta;
    
    public PagoVenta() {
        this.venta = new Venta();
    }

    public PagoVenta(Double cantidadCancelada, Date fechaPago, Venta venta) {
        this.cantidadCancelada = cantidadCancelada;
        this.fechaPago = fechaPago;
        this.venta = venta;
    }
    
    public Double getCantidadCancelada() {
        return cantidadCancelada;
    }

    public void setCantidadCancelada(Double cantidadCancelada) {
        this.cantidadCancelada = cantidadCancelada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PagoVenta{" + "id=" + id + ", cantidadCancelada=" + cantidadCancelada + ", fechaPago=" + fechaPago + ", venta=" + venta + '}';
    }
}
