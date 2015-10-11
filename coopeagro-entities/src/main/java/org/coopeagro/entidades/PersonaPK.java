package org.coopeagro.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class PersonaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "DNI")
    private String documento;
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "DSTIPODOCUMENTO")
    private TiposDocumento tipoDocumento;

    public PersonaPK() {
    }

    public PersonaPK(String documento, TiposDocumento tipoDocumento) {
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
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
