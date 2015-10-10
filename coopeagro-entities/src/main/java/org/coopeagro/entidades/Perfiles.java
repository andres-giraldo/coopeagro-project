package org.coopeagro.entidades;

import java.io.Serializable;

public enum Perfiles implements Serializable {
    ADMINISTRADOR("ADMINISTRADOR"),
    EMPLEADO("EMPLEADO");
    private String perfil;

    private Perfiles(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
