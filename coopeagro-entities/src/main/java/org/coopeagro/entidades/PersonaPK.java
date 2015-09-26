package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PersonaPK implements Serializable {

    @Column(name = "DNI")
    private String documento;
    @Enumerated(EnumType.STRING)
    @Column(name = "DSTIPODOCUMENTO")
    private TiposDocumento tipoDocumento;

    public PersonaPK() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TiposDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TiposDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
