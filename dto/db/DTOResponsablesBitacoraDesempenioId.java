package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class DTOResponsablesBitacoraDesempenioId implements Serializable {

	private static final long serialVersionUID = 1866670149415212436L;

	@Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
	private Integer idDetalleProceso;

	@Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
	private Integer idParticipacion;

	@Column(name = "ID_BITACORA_DESEMPENIO", nullable = false, precision = 6, scale = 0)
	private Integer idBitacoraDesempenio;

	@Column(name = "ID_RESPONSABLE_BITACORA", nullable = false, precision = 10, scale = 0)
	private Integer idResponsableBitacora;

}
