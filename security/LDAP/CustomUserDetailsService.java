package mx.ine.sustseycae.security.LDAP;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import mx.ine.serviceldap.controller.AuthenticationController;
import mx.ine.serviceldap.dto.DTOUsuario;
import mx.ine.sustseycae.dto.DTOUserToken;
import mx.ine.sustseycae.dto.DTOUsuarioLogin;
import mx.ine.sustseycae.repositoriesadmin.RepoJPAGruposSistema;
import mx.ine.sustseycae.util.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("authenticationController")
    private AuthenticationController authenticationController;

    @Autowired
    private RepoJPAGruposSistema repoJPAGruposSistema;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public UserDetails cargarUsuario(Authentication authentication) throws Exception {

        try {
            List<String> roles = repoJPAGruposSistema.getGrupoSistemas(Constantes.ID_SISTEMA);

            DTOUsuario usuario = authenticationController.authenticate(
                    roles,
                    authentication.getPrincipal().toString(),
                    authentication.getCredentials().toString());

            if (usuario == null) {
                throw new BadCredentialsException("mensaje_login_credenciales_invalidas");
            }

            Set<GrantedAuthority> authorities = obtenerPermisos(usuario.getRoles());

            DTOUsuarioLogin user = new DTOUsuarioLogin(
                    authentication.getPrincipal().toString(),
                    "",
                    true,
                    true,
                    true,
                    true,
                    authorities);

            obtenerInfoGeneral(user, usuario);

            return user;
        } catch (Exception ex) {
            throw new BadCredentialsException("Ocurrió un error. Comunícate al CAU.");
        }
    }

    public UserDetails cargarUsuarioToken(DTOUserToken userToken) {
        DTOUsuario usuario = userToken.getUsuario();

        usuario.setRoles(new ArrayList<>());
        if (userToken.getRolUsuario() != null) {
            usuario.getRoles().add(userToken.getRolUsuario());
        }

        Set<GrantedAuthority> authorities = obtenerPermisos(usuario.getRoles());

        DTOUsuarioLogin user = new DTOUsuarioLogin(
                usuario.getUid(),
                userToken.getClaveUs(),
                true,
                true,
                true,
                true,
                authorities);

        user.setIdSistema(userToken.getIdSistema());

        user.setIdEstado(usuario.getIdEstado());
        user.setEstado(usuario.getEstado());
        user.setIdDistrito(usuario.getIdDistrito());
        user.setDistrito(usuario.getDistrito());

        user.setNombre(usuario.getCn());
        user.setMail(usuario.getMail());
        user.setPuesto(usuario.getTituloPersonal());
        user.setAreaAdscripcion(usuario.getOu());
        user.setRolesUsuario(usuario.getRoles());

        user.setIdAsociacion(usuario.getIdAsociacion());
        user.setTipoAsociacion(usuario.getTipoAsociacion());

        user.setVersion(userToken.getVersionApp());
        user.setTipoToken(userToken.getTipoToken());

        return user;
    }

    private Set<GrantedAuthority> obtenerPermisos(List<String> roles) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        for (String rol : roles) {
            StringBuilder sb = new StringBuilder();
            sb.append("ROLE_").append(rol.toUpperCase());
            GrantedAuthority authority = new CustomGrantedAuthority(sb.toString());
            authorities.add(authority);
        }

        return authorities;
    }

    private void obtenerInfoGeneral(DTOUsuarioLogin user, DTOUsuario usuario) {
        user.setIdEstado(usuario.getIdEstado() == null ? 0 : usuario.getIdEstado());
        user.setEstado(usuario.getEstado());
        user.setIdDistrito(usuario.getIdDistrito() == null ? 0 : usuario.getIdDistrito());
        user.setDistrito(usuario.getDistrito());

        user.setNombre(usuario.getCn());
        user.setTratamiento(usuario.getTratamiento());
        user.setMail(usuario.getMail());
        user.setPuesto(usuario.getTituloPersonal());
        user.setAreaAdscripcion(usuario.getOu());
        user.setAmbito(usuario.getAmbito() == null ? "F" : usuario.getAmbito());
        user.setTipoUsuario(usuario.getTipo());

        user.setRolesUsuario(usuario.getRoles());
        user.setRolUsuario(usuario.getRoles().get(0));

        user.setIdAsociacion(usuario.getIdAsociacion());
        user.setTipoAsociacion(usuario.getTipoAsociacion());
        user.setIdEstadoAsoc(usuario.getIdEstadoAsociacion());

        user.setIp(obtenerIP());
    }

    private String obtenerIP() {
        String ip = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return null;
    }

}
