package mx.ine.sustseycae.util;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Scope("prototype")
@Component("util")
public class ApplicationUtil implements Serializable {

	private static final long serialVersionUID = -5909475402579789815L;

	private static final String UNKNOWN = "unknown";

	private ApplicationUtil() {
		throw new IllegalStateException("ApplicationUtil is an utility class");
	}

	public static void obtieneRespuestaExito(ModelGenericResponse response) {
		response.setCode(Constantes.RESPONSE_CODE_200);
		response.setStatus(Constantes.ESTATUS_EXITO);
		response.setMessage(Constantes.EMPTY);
		response.setCausa(Constantes.EMPTY);
	}

	public static void obtieneRespuestaError(ModelGenericResponse response, String causa) {
		response.setCode(Constantes.RESPONSE_CODE_500);
		response.setStatus(Constantes.ESTATUS_ERROR);
		response.setMessage(Constantes.MSG_ERROR_WS);
		response.setCausa(causa != null ? causa : Constantes.CAUSA_ERROR_CAU);
	}

	public static void obtieneRespuestaBad(ModelGenericResponse response, String causa) {
		response.setCode(Constantes.RESPONSE_CODE_400);
		response.setStatus(Constantes.ESTATUS_ERROR);
		response.setMessage(Constantes.MSG_ERROR_WS);
		response.setCausa(causa != null ? causa : Constantes.CAUSA_ERROR_CAU);
	}

	public static boolean isRolOC(String rol) {
		String[] datosRol = rol.split("\\.");
		return datosRol.length > 0
				&& datosRol[datosRol.length - 1].equalsIgnoreCase("OC");
	}

	public static String convertDateAString(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return localDate.format(formatter);
	}

	public static Date convertStringADate(String fechaStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate localDate = LocalDate.parse(fechaStr, formatter);
			return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static String obtenerIpCliente(HttpServletRequest request) {
		try {
			String ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;

		} catch (Exception e) {
			return null;
		}
	}

	public static String verifyPathSuffix(String path) {
		if (path == null) {
			return null;
		}

		return (path.endsWith("/")
				|| path.endsWith("\\")) ? path
						: path + File.separator;
	}

}
