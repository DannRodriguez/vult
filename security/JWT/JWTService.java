package mx.ine.sustseycae.security.JWT;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import mx.ine.serviceldap.dto.DTOUsuario;
import mx.ine.sustseycae.dto.DTOUserToken;
import mx.ine.sustseycae.dto.DTOUsuarioLogin;
import mx.ine.sustseycae.util.Constantes;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

@Component
public class JWTService {

	@Resource(lookup = "java:/util/jwtSK")
	private String jwtSecret;

	@Resource(lookup = "java:/util/jwtEncryptSecret")
	private String jwtEncryptSecret;

	@Resource(lookup = "java:/util/tiempoCaducidadTKA")
	private String tiempoCaducidadTKA;

	@Resource(lookup = "java:/util/tiempoCaducidadTKR")
	private String tiempoCaducidadTKR;

	private SecretKey jwtKey;
	private Password jwtEncryptKey;

	@PostConstruct
	public void generateJWTKeys() {
		jwtKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		jwtEncryptKey = Keys.password(jwtEncryptSecret.toCharArray());
	}

	public String generateTokenA(DTOUsuarioLogin usuario, UUID tokenUUID) {
		return Jwts.builder()
				.subject(usuario.getUsername())
				.claim(Constantes.NOMBRE, usuario.getNombre())
				.claim(Constantes.USUARIO, usuario.getUsername())
				.claim(Constantes.ROL, usuario.getRolesUsuario())
				.claim(Constantes.PUESTO, usuario.getPuesto())
				.claim(Constantes.CORREO, usuario.getMail())
				.claim(Constantes.AREA_ADSCRIPCION, usuario.getAreaAdscripcion())
				.claim(Constantes.ID_ESTADO, usuario.getIdEstado())
				.claim(Constantes.ID_DISTRITO, usuario.getIdDistrito())
				.claim(Constantes.TIPO_TOKEN, Constantes.TIPO_TOKEN_A)
				.claim(Constantes.UUIDJWT, tokenUUID)
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + Long.parseLong(tiempoCaducidadTKA)))
				.signWith(jwtKey)
				.compact();
	}

	public String generateTokenR(DTOUsuarioLogin usuario, String rnd, UUID tokenUUID) {
		return Jwts.builder()
				.subject(usuario.getUsername())
				.claim(Constantes.USUARIO, usuario.getUsername())
				.claim(Constantes.CLAVE_US, rnd)
				.claim(Constantes.TIPO_TOKEN, Constantes.TIPO_TOKEN_R)
				.claim(Constantes.UUIDJWT, tokenUUID)
				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + Long.parseLong(tiempoCaducidadTKR)))
				.encryptWith(jwtEncryptKey,
						Jwts.KEY.PBES2_HS512_A256KW,
						Jwts.ENC.A256GCM)
				.compact();
	}

	public DTOUserToken getUserFromTokenA(String token) {
		DTOUserToken userToken = new DTOUserToken();
		DTOUsuario usuario = new DTOUsuario();

		Claims claims = getClaimsTokenA(token);

		usuario.setUid(claims.getSubject());
		usuario.setCn((String) claims.get(Constantes.NOMBRE));
		usuario.setIdEstado((Integer) claims.get(Constantes.ID_ESTADO));
		usuario.setIdDistrito((Integer) claims.get(Constantes.ID_DISTRITO));
		usuario.setTituloPersonal((String) claims.get(Constantes.PUESTO));
		usuario.setOu((String) claims.get(Constantes.AREA_ADSCRIPCION));

		userToken.setUsername(claims.get(Constantes.USUARIO).toString());
		userToken.setClaveUs(Constantes.EMPTY);
		userToken.setTipoToken((Integer) claims.get(Constantes.TIPO_TOKEN));
		userToken.setIdTokenAcceso(claims.get(Constantes.UUIDJWT).toString());
		userToken.setUsuario(usuario);

		return userToken;
	}

	public DTOUserToken getUserFromTokenR(String token) {
		DTOUserToken userToken = new DTOUserToken();
		Claims claims = getClaimsTokenR(token);
		userToken.setUsername(claims.get(Constantes.USUARIO).toString());
		userToken.setClaveUs((String) claims.get(Constantes.CLAVE_US));
		userToken.setIdTokenAcceso(claims.get(Constantes.UUIDJWT).toString());
		return userToken;
	}

	public Claims getClaimsTokenA(String token) {
		return Jwts.parser()
				.verifyWith(jwtKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private Claims getClaimsTokenR(String token) {
		return Jwts.parser()
				.decryptWith(jwtEncryptKey)
				.build()
				.parseEncryptedClaims(token)
				.getPayload();
	}

}