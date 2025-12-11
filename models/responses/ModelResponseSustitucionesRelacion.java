package mx.ine.sustseycae.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseSustitucionesRelacion {

    private VOSustitucionesSupycap primeraSustitucion;
    private VOSustitucionesSupycap segundaSustitucion;

    private DTOAspirante sustitutoUno;
    private DTOAspirante sustitutoDos;

    private boolean esSustitucionCausante = false;

}
