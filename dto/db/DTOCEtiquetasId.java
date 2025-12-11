package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCEtiquetasId implements Serializable {

	private static final long serialVersionUID = -4407023681397895348L;

	@NotNull
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;

	@NotNull
	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;

	@NotNull
	@Column(name = "ID_ESTADO", nullable = false, precision = 2, scale = 0)
	private Integer idEstado;

	@NotNull
	@Column(name = "ID_DISTRITO", nullable = false, precision = 2, scale = 0)
	private Integer idDistrito;

	@NotNull
	@Column(name = "ID_MUNICIPIO", nullable = false, precision = 3, scale = 0)
	private Integer idMunicipio;

	@NotNull
	@Column(name = "ID_LOCALIDAD", nullable = false, precision = 5, scale = 0)
	private Integer idLocalidad;

	@NotNull
	@Column(name = "ID_COMUNIDAD", nullable = false, precision = 4, scale = 0)
	private Integer idComunidad;

	@NotNull
	@Column(name = "ID_REGIDURIA", nullable = false, precision = 2, scale = 0)
	private Integer idRegiduria;

	@NotNull
	@Column(name = "TIPO_JUNTA", nullable = false, length = 2)
	private String tipoJunta;

	@NotNull
	@Column(name = "ID_ETIQUETA", nullable = false, precision = 3, scale = 0)
	private Integer idEtiqueta;

}
