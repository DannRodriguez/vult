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
public class DTOCCausasVacanteId implements Serializable {

    @Serial
    private static final long serialVersionUID = 7753031726243435796L;

    @Column(name = "ID_CAUSA_VACANTE", nullable = false, precision = 2, scale = 0)
    private Integer idCausaVacante;

    @Column(name = "TIPO_CAUSA_VACANTE", nullable = false, precision = 2, scale = 0)
    private Integer tipoCausaVacante;

}
