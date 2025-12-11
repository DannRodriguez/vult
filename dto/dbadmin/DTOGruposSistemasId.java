package mx.ine.sustseycae.dto.dbadmin;

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
public class DTOGruposSistemasId implements Serializable {

	@Serial
	private static final long serialVersionUID = 4034102682998771477L;

	@Column(name = "ID_SISTEMA", unique = true, nullable = false, precision = 3, scale = 0)
	private Integer idSistema;

	@Column(name = "ID_GRUPO", unique = true, nullable = false, precision = 2, scale = 0)
	private Integer idGrupo;

}
