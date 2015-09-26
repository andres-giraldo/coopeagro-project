package org.coopeagro.entidades;

import java.io.Serializable;
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
@Table(name="TCA_DETALLE_VENTAS")
public class DetalleVenta implements Serializable{
    
    @TableGenerator(name="DetalleVentaGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="DETALLE_VENTA_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DetalleVentaGen")
    @Column(name="DNI")
    private Integer id;
    @Column(name="NMCANTIDAD")
    private Double cantidad;
    @Column(name="NMPRECIO")
    private Double precio;
    @ManyToOne
    @JoinColumn(name = "DNI_VENTA")
    private Venta venta;
    @ManyToOne
    @JoinColumn(name = "DNIPRODUCTO")
    private Producto producto;
    
    public DetalleVenta(){
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "id=" + id + ", cantidad=" + cantidad + ", precio=" + precio + ", venta=" + venta + ", producto=" + producto + '}';
    }
}
