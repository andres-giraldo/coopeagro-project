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
}
