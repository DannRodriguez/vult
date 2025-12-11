package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "SUPYCAP", name = "GRUPOS_DEFAULT_SISTEMA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOGruposDefaultSistema implements Serializable {

	private static final long serialVersionUID = 6265202544016465916L;

	@EmbeddedId
	private DTOGruposDefaultSistemaId id;

	@Column(name = "GRUPO_DEFAULT", nullable = true, length = 50)
	private String grupoDefault;

	@Column(name = "NOMBRE_SISTEMA", nullable = true, length = 150)
	private String nombreSistema;

	@Column(name = "DESCRIPCION_ROL", nullable = true, length = 150)
	private String descripcionRol;

	@Column(name = "ID_GRUPO", nullable = true, precision = 2, scale = 0)
	private Integer idGrupo;

}
