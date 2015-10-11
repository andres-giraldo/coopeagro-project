package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity()
@Table(name = "TCA_USUARIOS")
public class Usuario implements Serializable{
    @TableGenerator(name="UsuarioGen", 
        table="TCA_SQ", 
        pkColumnName="GEN_KEY", 
        valueColumnName="GEN_VALUE", 
        pkColumnValue="USUARIO_SEQ", 
        initialValue=0,
        allocationSize=1
    )
    
    @Basic(optional = false)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="UsuarioGen")
    @Column(name = "DNIUSUARIO")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DSUSUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "DSCLAVE")
    private String clave;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "DSPERFIL")
    private Perfiles perfil;

    public Usuario() {
    }
    
    public Usuario(String usuario, String clave, Perfiles perfil) {
        this.usuario = usuario;
        this.clave = clave;
        this.perfil = perfil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", usuario=" + usuario + ", clave=" + clave + ", perfil=" + perfil + '}';
    }
}
