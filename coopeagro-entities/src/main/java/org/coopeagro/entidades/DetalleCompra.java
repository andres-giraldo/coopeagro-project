package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="TCA_DETALLE_COMPRAS")
public class DetalleCompra implements Serializable{
    
    @TableGenerator(name="DetalleCompraGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="DETALLE_COMPRA_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DetalleCompraGen")
    @Column(name="DNI")
    private Integer id;
    @Basic(optional = false)
    @Column(name="NMCANTIDAD")
    private Double cantidad;
    @Basic(optional = false)
    @Column(name="NMPRECIO")
    private Double precio;
    @ManyToOne
    @JoinColumn(name = "DNIPEDIDO_COMPRA_FK", referencedColumnName = "DNIPEDIDO_COMPRA", nullable = false)
    private Compra compra;
    @ManyToOne
    @JoinColumn(name = "DNIPRODUCTO_FK", referencedColumnName = "DNIPRODUCTO", nullable = false)
    private Producto producto;
    
    public DetalleCompra(){
    }

    public DetalleCompra(Double cantidad, Double precio, Compra compra, Producto producto) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.compra = compra;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public String toString() {
        return "DetalleCompra{" + "id=" + id + ", cantidad=" + cantidad + ", precio=" + precio + ", compra=" + compra + ", producto=" + producto + '}';
    }
}
