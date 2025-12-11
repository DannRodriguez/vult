package mx.ine.sustseycae.dto.db;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOAspirante implements Serializable {

	@Serial
	private static final long serialVersionUID = 4046403619706921375L;

	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idAspirante;
	private String claveElectorFuar;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private Integer idPuesto;
	private Integer idZonaResponsabilidad;
	private String idAreaResponsabilidad;
	private Integer idZonaResponsabilidad2e;
	private String idAreaResponsabilidad2e;
	private String cargoAnterior;
	private Integer folio;
	private String urlFoto;
	private Integer declinoCargo;
	private String correoCtaCreada;
	private String correoCtaNotificacion;
	private Integer idEstadoAsp;
	private String uidCuenta;
	private String fecha;
	private String observaciones;
	private String descripcion;
	private String numeroAreaResponsabilidad;
	private Integer numeroZonaResponsabilidad;
	private String nombreCargo;
	private String participoProceso;
	private String cualProceso;
	private String participoSE;
	private String participoCAE;
	private String participoOtroEspecifique;
	private String fechaInicioContratacion;
	private String fechaFinContratacion;

}