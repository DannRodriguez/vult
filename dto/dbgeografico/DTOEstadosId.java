package mx.ine.sustseycae.dto.dbgeografico;

import java.io.Serial;
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
public class DTOEstadosId implements Serializable {

	@Serial
	private static final long serialVersionUID = 5074163580824546212L;

	@Column(name = "ID_ESTADO", unique = true, nullable = false, precision = 3, scale = 0)
	private Integer idEstado;

	@Column(name = "ID_CORTE", unique = true, nullable = false, precision = 6, scale = 0)
	private Integer idCorte;

}
