package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TCA_EMPLEADOS")
public class Empleado extends Persona implements Serializable{

    @Basic(optional = false)
    @Column(name = "DSCARGO")
    private String cargo;
    @Basic(optional = false)
    @Column(name = "DSDIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "FEFECHAINGRESO")
    private Date fechaIngreso;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "FEFECHARETIRO")
    private Date fechaRetiro;

    public Empleado() {
    }

    public Empleado(String cargo, String direccion, Date fechaIngreso, Date fechaRetiro, String documento, TiposDocumento tipoDocumento, String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo, String usuario, String clave, Perfiles perfil) {
        super(new PersonaPK(documento, tipoDocumento), nombre, apellidoUno, apellidoDos, telefono, celular, correo, new Usuario(usuario, clave, perfil));
        this.cargo = cargo;
        this.direccion = direccion;
        this.fechaIngreso = fechaIngreso;
        this.fechaRetiro = fechaRetiro;
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

    @Override
    public String toString() {
        return "Empleado{" + "cargo=" + cargo + ", direccion=" + direccion + ", fechaIngreso=" + fechaIngreso + ", fechaRetiro=" + fechaRetiro + '}';
    }
}
