package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TCA_USUARIOS")
public class Usuario implements Serializable {

    @Id
    @Column(name = "DNIUSUARIO")
    private String usuario;
    @Column(name = "DSCLAVE")
    private String clave;
    @Enumerated(EnumType.STRING)
    @Column(name = "DSPERFIL")
    private Perfiles perfil;

    public Usuario() {
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

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }
}
