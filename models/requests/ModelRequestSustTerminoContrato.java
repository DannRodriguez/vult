package mx.ine.sustseycae.models.requests;

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
public class ModelRequestSustTerminoContrato {

	@NotNull
	@PositiveOrZero
	@Digits(integer = 5, fraction = 0)
	private Integer idProceso;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 7, fraction = 0)
	private Integer idDetalleProceso;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 9, fraction = 0)
	private Integer idParticipacion;

	private Integer idAspirante;
	private Integer idSustitucion;

	@NotNull
	@PositiveOrZero
	private Integer tipoFlujo;

}
