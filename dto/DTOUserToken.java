package mx.ine.sustseycae.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.serviceldap.dto.DTOUsuario;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTOUserToken implements Serializable {

	private static final long serialVersionUID = -187673736872108163L;

	private DTOUsuario usuario;
	private Integer tipoToken;
	private String claveUs;
	private String rolUsuario;
	private String versionApp;
	private Integer idSistema;
	private String idTokenAcceso;
	private String username;

}