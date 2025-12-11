package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORequestModificarSustRescision implements Serializable {

	private static final long serialVersionUID = 2697221607098246133L;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 5, fraction = 0)
	private Integer idProcesoElectoral;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 7, fraction = 0)
	private Integer idDetalleProceso;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 9, fraction = 0)
	private Integer idParticipacion;

	private Integer idSustitucionSE;
	private Integer idSustitucionCAE;

	private String fechaBaja;
	private Integer tipoCausaVacante;
	private Integer idCausaVacante;
	private String observaciones;
	private String fechaAltaCapacitador;
	private String fechaAltaSupervisor;

	private String imgB64Sustituido;
	private String extensionArchivoSustituido;

	private String imgB64SustitutoSE;
	private String extensionArchivoSustitutoSE;

	private String imgB64SustitutoCAE;
	private String extensionArchivoSustitutoCAE;

	private String usuario;
	private String ipUsuario;

}
