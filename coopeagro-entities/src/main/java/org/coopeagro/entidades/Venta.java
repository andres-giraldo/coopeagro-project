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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TCA_VENTAS")
public class Venta extends Pedido implements Serializable {

    @TableGenerator(name = "VentaGen",
            table = "TCA_SQ",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "VENTA_SEQ",
            initialValue = 0,
            allocationSize = 1
    )

    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "VentaGen")
    @Column(name = "DNIPEDIDO")
    private Integer numeroPedido;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "FEFECHAESTIMADAENTREGA")
    private Date fechaEstimadaEntrega;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_CLIENTE", referencedColumnName = "DNI", nullable = false),
        @JoinColumn(name = "DSTIPODOCUMENTO_CLIENTE", referencedColumnName = "DSTIPODOCUMENTO", nullable = false)
    })
    private Cliente cliente;
    @Basic(optional = false)
    @Column(name = "DSDIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "DSREMITENTE")
    private String remitente;

    public Venta() {
        this.cliente = new Cliente();
    }

    public Venta(Date fechaEstimadaEntrega, Cliente cliente, String direccion, String remitente, Date fechaPedido, Empleado empleado, EstadosPedido estado, Double total) {
        super(fechaPedido, empleado, estado, total);
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.cliente = cliente;
        this.direccion = direccion;
        this.remitente = remitente;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Date getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(Date fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Venta{" + "numeroPedido=" + numeroPedido + ", fechaEstimadaEntrega=" + fechaEstimadaEntrega + ", cliente=" + cliente + ", direccion=" + direccion + ", remitente=" + remitente + '}';
    }
    
}
