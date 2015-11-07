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

@Entity
@Table(name="TCA_COMPRAS")
public class Compra extends Pedido implements Serializable{
    @TableGenerator(name="CompraGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="COMPRA_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="CompraGen")
    @Column(name="DNIPEDIDO")
    private Integer numeroPedido;
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "DNI_AGRICULTOR", referencedColumnName = "DNI", nullable = false),
        @JoinColumn(name = "DSTIPODOCUMENTO_AGRICULTOR", referencedColumnName = "DSTIPODOCUMENTO", nullable = false)
    })
    private Agricultor agricultor;

    public Compra() {
        this.agricultor = new Agricultor();
    }

    public Compra(Agricultor proveedor, Date fechaPedido, Empleado empleado, EstadosPedido estado, Double total) {
        super(fechaPedido, empleado, estado, total);
        this.agricultor = proveedor;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    @Override
    public String toString() {
        return "Compra{" + "numeroPedido=" + numeroPedido + ", agricultor=" + agricultor + '}';
    }
    
}
