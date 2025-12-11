package mx.ine.sustseycae.models.responses;

import java.io.Serializable;

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
public class ModelResponseFechasSustituciones implements Serializable {

	private static final long serialVersionUID = 339931576593802338L;

	private String fechaInicioSustituciones;
	private String fechaFinSustituciones;

}
