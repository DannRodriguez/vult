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
public class DTOCValoracionRiesgoDesempenioId implements Serializable {

	private static final long serialVersionUID = 6478176461063864285L;

	@Column(name = "ID_FRECUENCIA", nullable = false)
	private Integer idFrecuencia;

	@Column(name = "ID_IMPACTO", nullable = false)
	private Integer idImpacto;

	@Column(name = "ID_VALORACION_RIESGO", nullable = false)
	private Integer idValoracionRiesgo;

}
