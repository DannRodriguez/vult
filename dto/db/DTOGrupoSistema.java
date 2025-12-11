package mx.ine.sustseycae.dto.db;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DTOGrupoSistema implements Serializable {

	private static final long serialVersionUID = 4042952841998847521L;

	private Integer idSistema;
	private Integer idGrupo;
	private String grupo;
	private String descripcion;
	private Integer versionRol;
	private Integer tipoAcceso;
	private Integer idTipoGrupo;
	private String tipoCuenta;
	private Integer idTipoActor;
	private String grupoDefault;
	private String usuario;
	private Date fechaHora;

}