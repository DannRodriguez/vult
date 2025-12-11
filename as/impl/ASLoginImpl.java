package mx.ine.sustseycae.as.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.as.ASLogin;
import mx.ine.sustseycae.dto.DTOUserToken;
import mx.ine.sustseycae.dto.DTOUsuarioLogin;
import mx.ine.sustseycae.dto.db.DTOMovBitacoraLogin;
import mx.ine.sustseycae.dto.db.DTOMovBitacoraLoginId;
import mx.ine.sustseycae.models.requests.DTORequestCerrarSesion;
import mx.ine.sustseycae.models.requests.DTORequestIniciarSesion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseLogin;
import mx.ine.sustseycae.repositories.RepoJPAMovBitacoraLogin;
import mx.ine.sustseycae.security.JWT.JWTService;
import mx.ine.sustseycae.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("asLogin")
public class ASLoginImpl implements ASLogin {

    @Resource(lookup = "java:/util/tiempoReLogin")
    private String tiempoReLogin;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RepoJPAMovBitacoraLogin repoJPAMovBitacora;

    @Override
    public ModelGenericResponse tokenRefresh(String tokenRefresh) {
        ModelGenericResponse response = new ModelGenericResponse();

        try {
            DTOUserToken userToken = jwtService.getUserFromTokenR(tokenRefresh);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userToken.getUsername(),
                            userToken.getClaveUs()));

            response.setCode(200);
            response.setData(jwtService.generateTokenA(
                    (DTOUsuarioLogin) authentication.getPrincipal(),
                    UUID.fromString(userToken.getIdTokenAcceso())));
        } catch (BadCredentialsException e) {
            response.setCode(Constantes.CODIGO_USUARIO_PASSWORD_INCORRECTOS);
            response.setMessage("Usuario y/o password incorrectos.");
        } catch (ExpiredJwtException e) {
            response.setCode(Constantes.CODIGO_902_TKN_EXPIRADO);
            response.setMessage("Token expirado.");
        } catch (Exception e) {
            response.setCode(Constantes.CODIGO_500);
            response.setMessage("Ocurrió un error al obtener el token de refresh.");
        }
        return response;
    }

    @Override
    public ModelGenericResponse cierraSesion(DTORequestCerrarSesion loginRequest) {
        return cierraSesionBitacora(loginRequest);
    }

    @Override
    public ModelGenericResponse cierraSesionForc(DTORequestCerrarSesion loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsuario(),
                            loginRequest.getPassword()));
        } catch (Exception e) {
            ModelGenericResponse response = new ModelGenericResponse();
            response.setCode(Constantes.CODIGO_USUARIO_PASSWORD_INCORRECTOS);
            response.setMessage("El password proporcionado no es correcto.");
            return response;
        }

        return cierraSesionBitacora(loginRequest);
    }

    private ModelGenericResponse cierraSesionBitacora(DTORequestCerrarSesion loginRequest) {
        ModelGenericResponse response = new ModelGenericResponse();

        try {
            boolean existeSesion = repoJPAMovBitacora.existsByUsuarioAndIdIdSistemaAndEstatus(
                    loginRequest.getUsuario(),
                    loginRequest.getIdSistema(),
                    Constantes.ESTATUS_ACTIVO_MOVBLOGIN);

            if (!existeSesion) {
                response.setCode(Constantes.CODIGO_NO_EXISTE_SESION);
                response.setMessage("No existe registro de la sesión.");
            } else {
                response.setCode(200);
                repoJPAMovBitacora.actualizaSesionAEstatusInactivo(
                        loginRequest.getIdSistema(),
                        loginRequest.getUsuario(),
                        new Date());
            }

        } catch (Exception e) {
            response.setCode(Constantes.CODIGO_500);
            response.setMessage("Ocurrió un error al cerrar la sesión.");
        }

        return response;
    }

    @Override
    public ModelResponseLogin login(DTORequestIniciarSesion loginRequest, HttpServletRequest request) {
        ModelResponseLogin response = new ModelResponseLogin();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsuario(),
                            loginRequest.getPassword()));

            boolean sesionActiva = repoJPAMovBitacora.existsByUsuarioAndIdIdSistemaAndEstatus(
                    loginRequest.getUsuario(),
                    loginRequest.getIdSistema(),
                    Constantes.ESTATUS_ACTIVO_MOVBLOGIN);

            if (sesionActiva) {
                boolean accesoXTiempo = repoJPAMovBitacora.validaLoginHoraAcceso(
                        loginRequest.getIdSistema(),
                        loginRequest.getUsuario(),
                        new Date(),
                        Integer.parseInt(tiempoReLogin)) == 1;

                if (!accesoXTiempo) {
                    response.setCode(Constantes.CODIGO_USUARIO_CON_SESION_ACTIVA);
                    response.setMessage(
                            "El usuario cuenta con una sesi\u00f3n activa. Intentar después de " + tiempoReLogin
                                    + " minutos.");
                    return response;
                }

                repoJPAMovBitacora.actualizaSesionAEstatusInactivo(
                        loginRequest.getIdSistema(),
                        loginRequest.getUsuario(),
                        new Date());
            }

            DTOUsuarioLogin usuario = (DTOUsuarioLogin) authentication.getPrincipal();
            usuario.setIdSistema(loginRequest.getIdSistema());
            UUID tokenUUID = UUID.randomUUID();

            usuario.setTknA(jwtService.generateTokenA(usuario, tokenUUID));
            usuario.setTknR(jwtService.generateTokenR(usuario, loginRequest.getPassword(), tokenUUID));

            Claims claims = jwtService.getClaimsTokenA(usuario.getTknA());

            DTOMovBitacoraLogin movBitacora = new DTOMovBitacoraLogin();
            movBitacora.setId(new DTOMovBitacoraLoginId(
                    repoJPAMovBitacora.getNextValSequenceMovBitacoraLogin(),
                    loginRequest.getIdSistema()));
            movBitacora.setUsuario(loginRequest.getUsuario());
            movBitacora.setFechaLogueo(new Date());
            movBitacora.setVersionAplicacion(loginRequest.getVersionAplicacion());
            movBitacora.setEstatus(Constantes.ESTATUS_ACTIVO_MOVBLOGIN);
            movBitacora.setRolUsuario(usuario.getRolUsuario());
            movBitacora.setIpUsuario(getClientIP(request));
            movBitacora.setIpIntra(request.getLocalAddr());
            movBitacora.setIdTokenAcceso(claims.get(Constantes.UUIDJWT).toString());
            movBitacora.setFechaExpiracionTa(claims.getExpiration());

            repoJPAMovBitacora.saveAndFlush(movBitacora);

            response.setDatosUsuario(usuario);
            response.setCode(200);

        } catch (BadCredentialsException e) {
            response.setCode(Constantes.CODIGO_USUARIO_PASSWORD_INCORRECTOS);
            response.setMessage("Usuario y/o password incorrectos.");
        } catch (Exception e) {
            response.setCode(Constantes.CODIGO_500);
            response.setMessage("Ocurrió un error al iniciar la sesión.");
        }
        return response;
    }

    private String getClientIP(HttpServletRequest request) {
        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isBlank()) {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

}
