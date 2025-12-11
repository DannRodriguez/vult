package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCVocalesSustId implements Serializable {

	private static final long serialVersionUID = 905450454L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false)
	private Integer idProcesoElectoral;

	@Column(name = "ID_DETALLE_PROCESO", nullable = false)
	private Integer idDetalleProceso;

	@Column(name = "ID_RESPONSABLE_BITACORA", nullable = false)
	private Integer idResponsableBitacora;

}
