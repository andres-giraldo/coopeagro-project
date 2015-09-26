package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Persona implements Serializable{

    @EmbeddedId
    private PersonaPK llavePrimaria;
    @Column(name="DSNOMBRE")
    private String nombre;
    @Column(name="DSAPELLIDOUNO")
    private String apellidoUno;
    @Column(name="DSAPELLIDODOS")
    private String apellidoDos;
    @Column(name="DSTELEFONO")
    private String telefono;
    @Column(name="DSCELULAR")
    private String celular;
    @Column(name="DSCORREO")
    private String correo;
    
    public Persona(){
    }

    public Persona(PersonaPK llavePrimaria, String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo) {
        this.llavePrimaria = llavePrimaria;
        this.nombre = nombre;
        this.apellidoUno = apellidoUno;
        this.apellidoDos = apellidoDos;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
    }

    public PersonaPK getLlavePrimaria() {
        return llavePrimaria;
    }

    public void setLlavePrimaria(PersonaPK llavePrimaria) {
        this.llavePrimaria = llavePrimaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoUno() {
        return apellidoUno;
    }

    public void setApellidoUno(String apellidoUno) {
        this.apellidoUno = apellidoUno;
    }

    public String getApellidoDos() {
        return apellidoDos;
    }

    public void setApellidoDos(String apellidoDos) {
        this.apellidoDos = apellidoDos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Persona{" + "llavePrimaria=" + llavePrimaria + ", nombre=" + nombre + ", apellidoUno=" + apellidoUno + ", apellidoDos=" + apellidoDos + ", telefono=" + telefono + ", celular=" + celular + ", correo=" + correo + '}';
    }
}
