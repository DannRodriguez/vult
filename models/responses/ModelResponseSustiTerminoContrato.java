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
public class ModelResponseSustiTerminoContrato {

	private VOSustitucionesSupycap sustitucionSE;
	private VOSustitucionesSupycap sustitucionCAE;

	private DTOAspirante seIncapacitado;
	private DTOAspirante seTemporal;

	private DTOAspirante caeIncapacitado;
	private DTOAspirante caeTemporal;

}
