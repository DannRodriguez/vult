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
public class DTOGruposDefaultSistemaId implements Serializable {

	private static final long serialVersionUID = -803641131906191019L;

	@NotNull
	@Column(name = "ID_ACTOR", nullable = false, precision = 2, scale = 0)
	private Integer idActor;

	@NotNull
	@Column(name = "ID_SISTEMA", nullable = false, precision = 4, scale = 0)
	private Integer idSistema;

}
