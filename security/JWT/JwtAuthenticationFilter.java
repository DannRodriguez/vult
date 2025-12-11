package mx.ine.sustseycae.security.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.ine.sustseycae.dto.DTOUserToken;
import mx.ine.sustseycae.repositories.RepoJPAMovBitacoraLogin;
import mx.ine.sustseycae.security.LDAP.CustomUserDetailsService;
import mx.ine.sustseycae.util.Constantes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Log LOGGER = LogFactory.getLog(JwtAuthenticationFilter.class);

	@Autowired
	private JWTService jwtService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private RepoJPAMovBitacoraLogin repoJPAMovBitacora;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		try {
			if (request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}

			String authHeader = request.getHeader("Authorization");
			String bearerToken = null;

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				bearerToken = authHeader.substring(7);
			}

			if (StringUtils.hasText(bearerToken)) {
				DTOUserToken user = jwtService.getUserFromTokenA(bearerToken);

				Integer esValidToken = repoJPAMovBitacora.validarTokenAcceso(
						Constantes.ID_SISTEMA,
						user.getUsername(),
						user.getIdTokenAcceso());

				if (esValidToken == null || esValidToken.equals(0)) {
					throw new IllegalArgumentException("El token ya no es válido.");
				}

				UserDetails userDetails = customUserDetailsService.cargarUsuarioToken(user);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (SignatureException ex) {
			response.sendError(Constantes.CODIGO_900_TKN_FIRMA_INCORRECTA);
		} catch (MalformedJwtException ex) {
			response.sendError(Constantes.CODIGO_901_TKN_MALFORMADO);
		} catch (ExpiredJwtException ex) {
			response.sendError(Constantes.CODIGO_902_TKN_EXPIRADO);
		} catch (UnsupportedJwtException ex) {
			response.sendError(Constantes.CODIGO_903_TKN_NO_SOPORTADO);
		} catch (IllegalArgumentException ex) {
			response.sendError(Constantes.CODIGO_904_TKN_NO_VALIDO);
		} catch (Exception ex) {
			LOGGER.error("Error al autenticar al usuario en el contexto de seguridad", ex);
		}

		filterChain.doFilter(request, response);
	}

}
