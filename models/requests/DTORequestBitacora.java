package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.DTOEvaluacionBitacora;
import mx.ine.sustseycae.dto.DTOExpedienteBitacora;
import mx.ine.sustseycae.util.Constantes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORequestBitacora implements Serializable {

	private static final long serialVersionUID = 1134298848381195898L;

	private Integer idProcesoElectoral;

	@NotNull(message = Constantes.MSG_REQUEST_DETALLE_NOT_NULL)
	@Min(value = 0, message = Constantes.MSG_REQUEST_DETALLE_MIN)
	@Max(value = 9999999, message = Constantes.MSG_REQUEST_DETALLE_MAX)
	private Integer idDetalleProceso;

	@NotNull(message = Constantes.MSG_REQUEST_PARTICIPACION_NOT_NULL)
	@Min(value = 0, message = Constantes.MSG_REQUEST_PARTICIPACION_MIN)
	@Max(value = 999, message = Constantes.MSG_REQUEST_PARTICIPACION_MAX)
	private Integer idParticipacion;

	private Integer idAspirante;

	private Integer idBitacoraDesempenio;

	private Integer origenBitacora;

	private Integer tipoAccion;

	private String docFotoB64;

	private String extensionFoto;

	private String devolvioPrendas;

	private DTOEvaluacionBitacora evaluacionDesempenio;

	private DTOExpedienteBitacora expedienteDesempenio;

	private String usuario;

}
