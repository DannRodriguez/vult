package mx.ine.sustseycae.repositories.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import mx.ine.sustseycae.dto.DTOConsultaIntegrantes;
import mx.ine.sustseycae.models.requests.ModelRequestIntegrantesSesiones;
import mx.ine.sustseycae.repositories.RepoSesionesIntegrantesInterface;

@Service("repoSesionesIntegrantes")
@Scope("prototype")
public class RepoSesionesIntegrantesImpl implements RepoSesionesIntegrantesInterface {

	private static final Log log = LogFactory.getLog(RepoSesionesIntegrantesImpl.class);

	@Override
	public DTOConsultaIntegrantes obtenerIntegrantesWsSesiones(String hostWSSesiones, List<Integer> tipoIntegrante,
			Integer idEstado,
			Integer idDistritoFederal, List<String> tipoPuesto, List<Integer> estatus, Integer idProceso,
			Integer idDetalleProceso, String tipoOrdenamiento, String campoOrdenamiento) throws Exception {

		String response;

		ModelRequestIntegrantesSesiones requestIntegrantesSesiones = new ModelRequestIntegrantesSesiones(tipoIntegrante,
				idEstado,
				idDistritoFederal, tipoPuesto,
				estatus, idProceso,
				idDetalleProceso, tipoOrdenamiento,
				campoOrdenamiento);

		Gson gson = new Gson();
		String jsonRequest = gson.toJson(requestIntegrantesSesiones);

		URL urlWS = new URL(hostWSSesiones.endsWith("/") ? hostWSSesiones + "ws-sesionesConsejo/ws/integrantes/consulta"
				: hostWSSesiones + "/ws-sesionesConsejo/ws/integrantes/consulta");
		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, null, new java.security.SecureRandom());
		HttpsURLConnection conn = (HttpsURLConnection) urlWS.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; utf-8");
		conn.setRequestProperty("Accept", "application/json");
		conn.setSSLSocketFactory(sc.getSocketFactory());

		try (OutputStream os = conn.getOutputStream()) {
			byte[] input = jsonRequest.getBytes(StandardCharsets.UTF_8);
			os.write(input, 0, input.length);
			os.flush();
		}

		if (conn.getResponseCode() != 200) {
			log.error("ERROR  ASBitacoraDesempenoImpl.obtenerIntegrantesWsSesiones: " + jsonRequest + ": "
					+ conn.getResponseCode());
			return null;
		}

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
			response = br.readLine();
		}

		conn.disconnect();

		return gson.fromJson(response, DTOConsultaIntegrantes.class);

	}
}
