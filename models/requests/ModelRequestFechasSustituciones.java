package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class ModelRequestFechasSustituciones implements Serializable {

	private static final long serialVersionUID = 1864107792903104057L;

	@Min(value = 0, message = "El proceso electoral debe de ser mayor o igual a cero")
	@Max(value = 99999, message = "El proceso electoral no debe de ser mayor a 99999")
	private Integer idProceso;

	@Min(value = 0, message = "El detalle debe de ser mayor o igual a cero")
	@Max(value = 9999999, message = "El detalle no debe de ser mayor a 9999999")
	private Integer idDetalle;

}
