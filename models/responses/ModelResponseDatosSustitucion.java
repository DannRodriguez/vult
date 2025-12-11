package mx.ine.sustseycae.models.responses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseDatosSustitucion implements Serializable {

    private static final long serialVersionUID = 6535681155632034318L;

    private DTOSustitucionesSupycap dtoSustituciones;

    private String descripcionCausaVacante;

    private Byte caeSustitucionDeSE;

}
