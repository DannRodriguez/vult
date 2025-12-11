package mx.ine.sustseycae.dto;

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
public class DTOProcesoDetalleMultiProceso implements Serializable {

	private static final long serialVersionUID = -7250017017197805896L;

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private String nombreProceso;
	private String tipoProceso;
	private String ambitoProceso;
	private String vigente;
	private String anio;
	private String descripcionDetalle;
	private String ambitoDetalle;
	private String ambitoSistema;
	private String tipoCapturaSistema;
	private Integer corte;
	private String casillaUnica;
	private String porSeccion;
	private String coincidenciaUsuarioSistema;
	private Integer idEstado;

}
