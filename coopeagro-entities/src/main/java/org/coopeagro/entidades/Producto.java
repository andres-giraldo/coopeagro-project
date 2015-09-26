package org.coopeagro.entidades;

import java.io.Serializable;
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
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ProductoGen")
    @Column(name="DNIPRODUCTO")
    private Integer codigo;
    @Column(name="DSNOMBRE")
    private String nombre;
    
    public Producto(){
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
