package mx.ine.sustseycae.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class DTOUsuarioLogin extends User {

	private static final long serialVersionUID = -6838220487208818463L;

	private Integer idSistema;

	private Integer idProcesoElectoral;
	private Integer idDetalle;
	private Integer idEstado;
	private String estado;
	private Integer idDistrito;
	private String distrito;

	private String nombre;
	private String tratamiento;
	private String mail;
	private String puesto;
	private String rolUsuario;
	private String areaAdscripcion;
	private String ambito;
	private String tipoUsuario;

	@JsonIgnore
	private List<String> rolesUsuario;

	private Integer idAsociacion;
	private Integer tipoAsociacion;
	private Integer idEstadoAsoc;

	private String ip;

	@JsonIgnore
	private String version;

	private String tknA;
	private String tknR;

	@JsonIgnore
	private Integer tipoToken;

	public DTOUsuarioLogin(String username, String claveUs, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, claveUs, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	@Override
	@JsonIgnore
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return super.getPassword();
	}

}
