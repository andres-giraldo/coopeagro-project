package org.coopeagro.entidades;

import java.io.Serializable;

public enum TiposDocumento implements Serializable {

    NIT("NIT"),
    TI("TI"),
    CC("CC"),
    CE("CE"),
    PAS("PAS");
    private String tipoDocumento;

    private TiposDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
