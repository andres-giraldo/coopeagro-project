package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
public class Pedido implements Serializable{
    
    @TableGenerator(name="PedidoGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="PEDIDO_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PedidoGen")
    @Column(name="DNIPEDIDO")
    private Integer numeroPedido;
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
    @Transient
    private Double total;
    
    public Pedido(){
    }

    public Pedido(Date fechaPedido, Empleado empleado, EstadosPedido estado, Double total) {
        this.fechaPedido = fechaPedido;
        this.empleado = empleado;
        this.estado = estado;
        this.total = total;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
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
        return "Pedido{" + "numeroPedido=" + numeroPedido + ", fechaPedido=" + fechaPedido + ", empleado=" + empleado + ", estado=" + estado + ", total=" + total + '}';
    }
}
