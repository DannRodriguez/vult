package mx.ine.sustseycae.dto.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOMovBitacoraLoginId implements Serializable {

    @Serial
    private static final long serialVersionUID = 2790154598412132764L;

    @Column(name = "ID_BITACORA", nullable = false, precision = 10, scale = 0)
    private Integer idBitacora;

    @Column(name = "ID_SISTEMA", nullable = false, precision = 3, scale = 0)
    private Integer idSistema;

}
