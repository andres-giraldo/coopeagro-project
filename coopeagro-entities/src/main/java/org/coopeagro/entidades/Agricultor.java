package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_AGRICULTORES")
public class Agricultor extends Persona implements Serializable{
    
    @Temporal(TemporalType.DATE)
    @Column(name="FEFECHAREGISTRO")
    private Date fechaRegistro;
    @Column(name="DSDIRECCION")
    private String direccion;
    
    public Agricultor(){
    }

    public Agricultor(String documento, TiposDocumento tipoDocumento,String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo, Date fechaRegistro, String direccion) {
        super(new PersonaPK(documento, tipoDocumento), nombre, apellidoUno, apellidoDos, telefono, celular, correo);
        this.fechaRegistro = fechaRegistro;
        this.direccion = direccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Agricultor{" + "fechaRegistro=" + fechaRegistro + ", direccion=" + direccion + '}';
    }
}
