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
public class DTOCImpactosId implements Serializable {

	private static final long serialVersionUID = 8522568567190083511L;

	@Column(name = "ID_IMPACTO", nullable = false)
	private Integer idImpacto;

}
