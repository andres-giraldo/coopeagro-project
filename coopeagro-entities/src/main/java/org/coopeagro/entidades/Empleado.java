package org.coopeagro.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    @OneToOne
    @JoinColumn(name = "DNIUSUARIO")
    private Usuario usuario;

    public Empleado() {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "cargo=" + cargo + ", direccion=" + direccion + ", fechaIngreso=" + fechaIngreso + ", fechaRetiro=" + fechaRetiro + ", usuario=" + usuario + '}';
    }
}
