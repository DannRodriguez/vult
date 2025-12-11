package mx.ine.sustseycae.dto.vo;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.util.Constantes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VOAspiranteBitacora implements Serializable {

	private static final long serialVersionUID = 4101230936359494298L;

	@NotNull(message = Constantes.MSG_REQUEST_PROCESO_NOT_NULL)
	@Min(value = 0, message = Constantes.MSG_REQUEST_PROCESO_MIN)
	@Max(value = 99999, message = Constantes.MSG_REQUEST_PROCESO_MAX)
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
	private Integer folio;
	private String nombreCompleto;
	private String claveElector;
	private Integer idPuesto;
	private String puesto;
	private String evaluacionIntegralSE;
	private String evaluacionIntegralCAE;
	private String presentaEntrevistaSE;
	private String declinoEntrevistaSE;
	private String declinoEntrevistaCAE;
	private String participoProceso;
	private String cualProceso;
	private String participoSE;
	private String participoCAE;
	private String participoOtro;
	private Integer idBitacoraDesempenio;
	private Integer devolvioPrendas;
	private String fechaSustAlta;

}