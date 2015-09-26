package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_INVENTARIOS")
public class Inventario implements Serializable{
    
    @TableGenerator(name="InventarioGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="INVENTARIO_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="InventarioGen")
    @Column(name="DNI")
    private Integer id;
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHA")
    private Date fecha;
    @ManyToOne
    @JoinColumn(name = "DNIPRODUCTO")
    private Producto producto;
    @Column(name="NMCANTIDADCOMPROMETIDA")
    private Double cantidadComprometida;
    @Column(name="NMCANTIDADTOTAL")
    private Double cantidadTotal;
    
    public Inventario(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getCantidadComprometida() {
        return cantidadComprometida;
    }

    public void setCantidadComprometida(Double cantidadComprometida) {
        this.cantidadComprometida = cantidadComprometida;
    }

    public Double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
