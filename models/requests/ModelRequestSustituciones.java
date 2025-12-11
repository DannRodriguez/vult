package mx.ine.sustseycae.models.requests;

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
public class ModelRequestSustituciones {

	private VOSustitucionesSupycap sustitucionSE;
	private VOSustitucionesSupycap sustitucionCAE;

	private DTOAspirante seIncapacitado;
	private DTOAspirante seTemporal;

	private DTOAspirante caeIncapacitado;
	private DTOAspirante caeTemporal;

	private String fechaBaja;
	private String usuario;
	private String observaciones;

	private String imagenB641;
	private String extensionImagen1;
	private Integer idAspiranteImagen1;

	private String imagenB642;
	private String extensionImagen2;
	private Integer idAspiranteImagen2;

	private String imagenB643;
	private String extensionImagen3;
	private Integer idAspiranteImagen3;

	private Integer tipoFlujo;

}
