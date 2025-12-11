package mx.ine.sustseycae.models.requests;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelRequestIntegrantesSesiones implements Serializable {

	private static final long serialVersionUID = -246430590575964513L;

	private List<Integer> tipoIntegrante;
	private Integer idEstado;
	private Integer idDistritoFederal;
	private List<String> tipoPuesto;
	private List<Integer> estatus;
	private Integer idProceso;
	private Integer idDetalleProceso;
	private String tipoOrdenamiento;
	private String campoOrdenamiento;

	public ModelRequestIntegrantesSesiones() {
		super();
	}

	public ModelRequestIntegrantesSesiones(List<Integer> tipoIntegrante, Integer idEstado, Integer idDistritoFederal,
			List<String> tipoPuesto, List<Integer> estatus, Integer idProceso, Integer idDetalleProceso,
			String tipoOrdenamiento, String campoOrdenamiento) {

		this.tipoIntegrante = tipoIntegrante;
		this.idEstado = idEstado;
		this.idDistritoFederal = idDistritoFederal;
		this.tipoPuesto = tipoPuesto;
		this.estatus = estatus;
		this.idProceso = idProceso;
		this.idDetalleProceso = idDetalleProceso;
		this.tipoOrdenamiento = tipoOrdenamiento;
		this.campoOrdenamiento = campoOrdenamiento;
	}

}
