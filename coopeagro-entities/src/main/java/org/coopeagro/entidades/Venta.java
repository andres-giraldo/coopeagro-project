package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_VENTAS")
public class Venta extends Pedido implements Serializable{

    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAESTIMADAENTREGA")
    private Date fechaEstimadaEntrega;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_CLIENTE"),
        @JoinColumn(name = "DSTIPODOCUMENTO_CLIENTE")
    })
    private Cliente cliente;
    @Column(name="DSDIRECCION")
    private String direccion;
    @Column(name="DSREMITENTE")
    private String remitente;
    
    public Venta() {
    }

    public Venta(Date fechaEstimadaEntrega, Cliente cliente, String direccion, String remitente, Date fechaPedido, Empleado empleado, EstadosPedido estado, Double total) {
        super(fechaPedido, empleado, estado, total);
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
        this.cliente = cliente;
        this.direccion = direccion;
        this.remitente = remitente;
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
        return "Venta{" + "fechaEstimadaEntrega=" + fechaEstimadaEntrega + ", cliente=" + cliente + ", direccion=" + direccion + ", remitente=" + remitente + '}';
    }
}
