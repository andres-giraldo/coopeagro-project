package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class Persona implements Serializable{

    @EmbeddedId
    private PersonaPK llavePrimaria;
    @Basic(optional = false)
    @Column(name="DSNOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name="DSAPELLIDOUNO")
    private String apellidoUno;
    @Basic(optional = true)
    @Column(name="DSAPELLIDODOS")
    private String apellidoDos;
    @Basic(optional = false)
    @Column(name="DSTELEFONO")
    private String telefono;
    @Basic(optional = true)
    @Column(name="DSCELULAR")
    private String celular;
    @Basic(optional = true)
    @Column(name="DSCORREO")
    private String correo;
    @OneToOne
    @JoinColumn(name = "DNIUSUARIO", referencedColumnName = "DNIUSUARIO", nullable = true)
    private Usuario usuario;
    
    public Persona(){
    }

    public Persona(PersonaPK llavePrimaria, String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo, Usuario usuario) {
        this.llavePrimaria = llavePrimaria;
        this.nombre = nombre;
        this.apellidoUno = apellidoUno;
        this.apellidoDos = apellidoDos;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Persona{" + "llavePrimaria=" + llavePrimaria + ", nombre=" + nombre + ", apellidoUno=" + apellidoUno + ", apellidoDos=" + apellidoDos + ", telefono=" + telefono + ", celular=" + celular + ", correo=" + correo + ", usuario=" + usuario + '}';
    }
}
