package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_EMPLEADOS")
public class Empleado extends Persona implements Serializable{

    @Column(name = "DSCARGO")
    private String cargo;
    @Column(name = "DSDIRECCION")
    private String direccion;
    @Temporal(TemporalType.DATE)
    @Column(name = "FEFECHAINGRESO")
    private Date fechaIngreso;
    @Temporal(TemporalType.DATE)
    @Column(name = "FEFECHARETIRO")
    private Date fechaRetiro;
    @Column(name = "DSUSUARIO")
    private String usuario;
    @Column(name = "DSCLAVE")
    private String clave;

    public Empleado() {
    }

    public Empleado(String cargo, String direccion, Date fechaIngreso, Date fechaRetiro, String usuario, String clave, String documento, TiposDocumento tipoDocumento, String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo) {
        super(new PersonaPK(documento, tipoDocumento), nombre, apellidoUno, apellidoDos, telefono, celular, correo);
        this.cargo = cargo;
        this.direccion = direccion;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Empleado{" + "cargo=" + cargo + ", direccion=" + direccion + ", fechaIngreso=" + fechaIngreso + ", fechaRetiro=" + fechaRetiro + ", usuario=" + usuario + ", clave=" + clave + '}';
    }
}
