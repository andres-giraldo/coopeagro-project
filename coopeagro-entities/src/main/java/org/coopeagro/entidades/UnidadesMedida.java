package org.coopeagro.entidades;

import java.io.Serializable;

public enum UnidadesMedida implements Serializable{
    KILO("KILO"),
    LIBRA("LIBRA"),
    LITRO("LITRO"),
    RACIMO("RACIMO"),
    BULTO("BULTO"),
    HUACAL("HUACAL"),
    UNIDAD("UNIDAD"),
    PAQUETE("PAQUETE");
    
    private String unidadMedida;
    
    private UnidadesMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
