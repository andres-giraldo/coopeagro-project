package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TCA_CLIENTES")
public class Cliente extends Persona implements Serializable{
    
    public Cliente(){
    }

    public Cliente(String documento, TiposDocumento tipoDocumento, String nombre, String apellidoUno, String apellidoDos, String telefono, String celular, String correo) {
        super(new PersonaPK(documento, tipoDocumento), nombre, apellidoUno, apellidoDos, telefono, celular, correo);
    }
}
