package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TCA_CLIENTES")
public class Cliente extends Persona implements Serializable{
    
    public Cliente(){
    } 
}
