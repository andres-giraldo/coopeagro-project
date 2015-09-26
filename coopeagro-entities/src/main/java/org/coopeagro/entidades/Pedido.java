package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
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
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="PedidoGen")
    @Column(name="DNI")
    private Integer numeroPedido;
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAPEDIDO")
    private Date fechaPedido;
    @Enumerated(EnumType.STRING)
    @Column(name="DSTIPOPEDIDO")
    private TiposPedido tipoPedido;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_EMPLEADO"),
        @JoinColumn(name = "DSTIPODOCUMENTO_EMPLEADO")
    })
    private Empleado empleado;
    @Enumerated(EnumType.STRING)
    @Column(name="DSESTADO")
    private EstadosPedido estado;
    @Transient
    private Double total;
    
    public Pedido(){
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

    public TiposPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TiposPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
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
        return "Pedido{" + "numeroPedido=" + numeroPedido + ", fechaPedido=" + fechaPedido + ", tipoPedido=" + tipoPedido + ", empleado=" + empleado + ", estado=" + estado + ", total=" + total + '}';
    }
}
