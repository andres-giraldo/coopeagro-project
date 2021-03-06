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
@Table(name="TCA_PAGO_COMPRAS")
public class PagoCompra implements Serializable{
    @TableGenerator(name="PagoCompraGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="PAGO_COMPRA_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PagoCompraGen")
    @Column(name = "DNIPAGOCOMPRA")
    private Integer id;
    @Basic(optional = false)
    @Column(name="NMCANTIDADCANCELADA")
    private Double cantidadCancelada;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPAGO")
    private Date fechaPago;
    @OneToOne
    @JoinColumn(name = "DNIPEDIDO_COMPRA", referencedColumnName = "DNIPEDIDO", nullable = false)
    private Compra compra;
    
    public PagoCompra(){
        this.compra = new Compra();
    }

    public PagoCompra(Double cantidadCancelada, Date fechaPago, Compra compra) {
        this.cantidadCancelada = cantidadCancelada;
        this.fechaPago = fechaPago;
        this.compra = compra;
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public String toString() {
        return "PagoCompra{" + "id=" + id + ", cantidadCancelada=" + cantidadCancelada + ", fechaPago=" + fechaPago + ", compra=" + compra + '}';
    }
}
