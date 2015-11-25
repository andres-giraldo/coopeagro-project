package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class Pedido implements Serializable{
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FEFECHAPEDIDO")
    private Date fechaPedido;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name="DSESTADO")
    private EstadosPedido estado;
    @Column(name="NMTOTAL")
    private Double total;
    
    public Pedido(){
        this.total = 0.0;
    }

    public Pedido(Date fechaPedido, EstadosPedido estado, Double total) {
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadosPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadosPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{fechaPedido=" + fechaPedido + ", estado=" + estado + ", total=" + total + '}';
    }
}
