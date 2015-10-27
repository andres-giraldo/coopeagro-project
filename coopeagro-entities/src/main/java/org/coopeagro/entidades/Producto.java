package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="TCA_PRODUCTOS")
public class Producto implements Serializable{
    
    @TableGenerator(name="ProductoGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="PRODUCTO_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ProductoGen")
    @Column(name="DNIPRODUCTO")
    private Integer id;
    @Basic(optional = false)
    @Column(name="DSCODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name="DSNOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name="NMVALOR")
    private Double valor;
    
    public Producto(){
    }

    public Producto(String codigo, String nombre, Double valor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Producto(Integer id, String codigo, String nombre, Double valor) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", valor=" + valor + '}';
    }
}
