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
public class DTOCFrecuenciasId implements Serializable {

	private static final long serialVersionUID = -5249921314021204762L;

	@Column(name = "ID_FRECUENCIA", nullable = false)
	private Integer idFrecuencia;

}
