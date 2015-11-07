package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class Pedido implements Serializable{
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPEDIDO")
    private Date fechaPedido;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_EMPLEADO", referencedColumnName = "DNI", nullable = false),
        @JoinColumn(name = "DSTIPODOCUMENTO_EMPLEADO", referencedColumnName = "DSTIPODOCUMENTO", nullable = false)
    })
    private Empleado empleado;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name="DSESTADO")
    private EstadosPedido estado;
    @Column(name="NMTOTAL")
    private Double total;
    
    public Pedido(){
        this.empleado = new Empleado();
        this.total = 0.0;
    }

    public Pedido(Date fechaPedido, Empleado empleado, EstadosPedido estado, Double total) {
        this.fechaPedido = fechaPedido;
        this.empleado = empleado;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EstadosPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadosPedido estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedido{fechaPedido=" + fechaPedido + ", empleado=" + empleado + ", estado=" + estado + ", total=" + total + '}';
    }
}
