package mx.ine.sustseycae.dto.dbadmin;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@SqlResultSetMapping(name = "gruposSistemas", classes = {
		@ConstructorResult(targetClass = String.class, columns = {
				@ColumnResult(name = "grupo", type = String.class)
		})
})

@NamedNativeQuery(name = "DTOGruposSistemas.getGrupoSistemas", query = """
		SELECT GRUPOS AS grupo
		FROM ADMIN_INE.GRUPOS_SISTEMAS
		WHERE ID_SISTEMA = :idSistema""", resultSetMapping = "gruposSistemas")

@Entity
@Table(name = "GRUPOS_SISTEMAS", schema = "ADMIN_INE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOGruposSistemas implements Serializable {

	@Serial
	private static final long serialVersionUID = 3841300086095349731L;

	@EmbeddedId
	private DTOGruposSistemasId id;

	@Column(name = "GRUPOS", unique = true, nullable = false, precision = 50, scale = 0)
	private String grupo;

	@Column(name = "DESCRIPCION", unique = true, nullable = false, precision = 300, scale = 0)
	private String descripcion;

	@Column(name = "VERSION_ROL", unique = true, nullable = false, precision = 2, scale = 0)
	private Integer versionRol;

	@Column(name = "TIPO_ACCESO", unique = true, nullable = true, precision = 1, scale = 0)
	private Integer tipoAcceso;

	@Column(name = "ID_TIPO_GRUPO", unique = true, nullable = true, precision = 2, scale = 0)
	private Integer idTipoGrupo;

	@Column(name = "TIPO_CUENTA", unique = true, nullable = false, precision = 1, scale = 0)
	private String tipoCuenta;

	@Column(name = "ID_TIPO_ACTOR", unique = true, nullable = true, precision = 2, scale = 0)
	private Integer idTipoActor;

	@Column(name = "GRUPO_DEFAULT", unique = true, nullable = true, precision = 1, scale = 0)
	private String grupoDefault;

	@Column(name = "USUARIO", unique = true, nullable = false, precision = 50, scale = 0)
	private String usuario;

	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

}