package mx.ine.sustseycae.bsd.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASBitacoraDesempenioInterface;
import mx.ine.sustseycae.bo.BOValidacionesBitacoraDesempInterface;
import mx.ine.sustseycae.bsd.BSDBitacoraDesempenioInterface;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.dto.DTOConsultaIntegrantes;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.responses.ModelResponseBitacora;
import mx.ine.sustseycae.models.responses.ModelResponseIntegrantesSesiones;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdBitacoraDesempenio")
@Scope("prototype")
public class BSDBitacoraDesempenioImpl implements BSDBitacoraDesempenioInterface {
	
	private static final  Log log = LogFactory.getLog(BSDBitacoraDesempenioImpl.class);
	
	/**
	 * 1: para Vocales.
	 * 2: para Consejeros.
	 * 3: para Representantes de Partidos Políticos y Candidaturas Independientes
	 */
	private static final List<Integer> TIPO_INTEGRANTES_WS_SESIONES = List.of(1);

	/**
	 * “P”: para puestos propietarios
	 * "S”: para puestos suplentes
	 */
	private static final List<String> TIPO_PUESTO_WS_SESIONES = List.of("P","S");
	
	/**
	 *  1: para Activos.
	 *  2: para Bajas.
	 */
	private static final List<Integer> TIPO_ESTATUS_WS_SESIONES = List.of(1);
	
	private static final String TIPO_ORDENAMIENTO_WS_SESIONES = "ascendente";
	private static final String CAMPO_ORDENAMIENTO_WS_SESIONES = "nombre";
	
	@Autowired
	@Qualifier("asBitacoraDesempenio")
	private ASBitacoraDesempenioInterface asBitacoraDesempenio;

	@Autowired
	@Qualifier("boValidacionesBitacoraDesemp")
	private BOValidacionesBitacoraDesempInterface boValidacionesBitacoraDesemp;
	
	@Autowired
	@Qualifier("pathGlusterSistDECEYEC")
	String pathGlusterSistDECEYEC;
	
	@Autowired
	@Qualifier("bsdCommons")
    private BSDCommons bsdCommons;
	
	@Override
	public VOAspiranteBitacora obtenerInfoAspiranteBitacora(DTORequestBitacora request) {
		
		return asBitacoraDesempenio.obtenerAspiranteBitacora(
			request.getIdDetalleProceso(),
			request.getIdParticipacion(),
			request.getIdAspirante());
	}

	@Override
	public VOBitacoraDesempenio obtenerExpedienteDesempenio(DTORequestBitacora request) throws Exception{
		
		VOBitacoraDesempenio bitacoraDesempenio = 
		asBitacoraDesempenio.obtenerBitacoraDesempenioAspirante(
			request.getIdDetalleProceso(),
			request.getIdParticipacion(),
			request.getIdAspirante());
		
		if(bitacoraDesempenio != null && bitacoraDesempenio.getRutaDocumentos() != null) {
			File expediente = new File(bitacoraDesempenio.getRutaDocumentos());
			
			if(expediente.exists()) {
				Long dimension = expediente.length();
				bitacoraDesempenio.setDimensionArchivo(dimension);
			}
		}
		
		return bitacoraDesempenio;
	}

	@Override
	public  ResponseEntity<Object> obtenerBase64Expediente(DTORequestBitacora request) throws Exception {
		
		VOBitacoraDesempenio bitacoraDesempenio = 
		asBitacoraDesempenio.obtenerBitacoraDesempenioAspirante(
			request.getIdDetalleProceso(),
			request.getIdParticipacion(),
			request.getIdAspirante());

		Path path = Paths.get(bitacoraDesempenio.getRutaDocumentos());

		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			log.error("Archivo no encontrado: " + path);
			throw new FileNotFoundException(Constantes.MSG_ERROR_RUTA_ARCHIVO_BITACORA);
		}

		String mimeType = Files.probeContentType(path);
		if (mimeType == null) {
			mimeType = "application/octet-stream"; 
		}

		try {
			byte[] fileBytes = Files.readAllBytes(path);
			String base64 = Base64.getEncoder().encodeToString(fileBytes);
			return ResponseEntity.ok("data:" + mimeType + ";base64," + base64);
			
		} catch (IOException e) {
			log.error(Constantes.MSG_ERROR_LEER_ARCHIVO_BITACORA + path + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public ModelResponseBitacora obtenerEvaluacionDesempenio(DTORequestBitacora request) throws Exception {
		ModelResponseBitacora modelResponse = new ModelResponseBitacora();

		modelResponse.setCatFrecuencias(asBitacoraDesempenio.obtenerCFrecuencias());
		modelResponse.setCatImpactos(asBitacoraDesempenio.obtenerCImpactos());
		modelResponse.setCatValoracionesRiesgo(asBitacoraDesempenio.obtenerCValoracionRiesgo());
		modelResponse.setBitacoraDesempenio(
			asBitacoraDesempenio.obtenerBitacoraDesempenioAspirante(
				request.getIdDetalleProceso(),
				request.getIdParticipacion(),
				request.getIdAspirante()
			)
		);

    	cargarIntegrantesYResponsables(request, modelResponse);

    	return modelResponse;
	}


	@Override
	public String guardarBitacora(DTORequestBitacora request) throws Exception {
		
		DTOBitacoraDesempenio bitacoraDesempenioBD = boValidacionesBitacoraDesemp.dtoRequestToBitacoraDesempenio(request);
		List<DTOResponsablesBitacoraDesempenio> listResponsablesBD = boValidacionesBitacoraDesemp.obtenerResponsablesBitacora(request);
		
		VOBitacoraDesempenio bitacoraDesempenioPrev = new VOBitacoraDesempenio();
		if(request.getTipoAccion().equals(Constantes.FLUJO_MODIFICA)) {
			bitacoraDesempenioPrev = 
			asBitacoraDesempenio.obtenerBitacoraDesempenioAspirante(	
				request.getIdDetalleProceso(),
				request.getIdParticipacion(),
				request.getIdAspirante());
		}
		
		if(request.getOrigenBitacora().equals(1) && request.getDocFotoB64() != null && !request.getDocFotoB64().isBlank()) {
				bsdCommons.almacenarFotoAspirante(
					request.getDocFotoB64(), 
					request.getExtensionFoto(), 
					request.getIdProcesoElectoral(), 
					request.getIdDetalleProceso(),
					request.getIdParticipacion(),
					request.getIdAspirante());
			}
		
		
		String fullPathDocumento = "";
		boolean documentoGuardado = false;
		boolean falloAlmacenarDocumento = false;
		boolean existeArchivoExp = (request.getExpedienteDesempenio() != null 
									&& request.getExpedienteDesempenio().getArchivoExpediente() != null);
		
		if(existeArchivoExp){
			try {
				String pathGluster = pathGlusterSistDECEYEC;
				pathGluster = (pathGluster.endsWith("/") || pathGluster.endsWith("\\"))?pathGluster : pathGluster+File.separator;
				
				DTOCEtiquetas carpetaSupycap = 
				asBitacoraDesempenio.obtenerEtiquetaCEtiquetas(
					request.getIdProcesoElectoral(), 
					request.getIdDetalleProceso(), 
					Constantes.ID_ETIQUETA_CARPETA_SUPYCAP);
				
				String pathCarpetaExpediente = boValidacionesBitacoraDesemp.buildCarpetaExpedienteGluster(request, carpetaSupycap.getEtiqueta());
				pathCarpetaExpediente = (pathCarpetaExpediente.endsWith("/") || pathCarpetaExpediente.endsWith("\\"))?pathCarpetaExpediente : pathCarpetaExpediente+File.separator;
				String nombreArchivoDoc = boValidacionesBitacoraDesemp.buildNombreExpediente(request);
				
				fullPathDocumento = pathGluster+pathCarpetaExpediente+nombreArchivoDoc;
				
				InputStream streamArchivoExp = request.getExpedienteDesempenio().getArchivoExpediente().getInputStream();
				byte[] decodedBytesFile = new byte[streamArchivoExp.available()];
				streamArchivoExp.read(decodedBytesFile);
				
				File file = new File(fullPathDocumento);
				
				FileUtils.writeByteArrayToFile(file, decodedBytesFile);
				documentoGuardado = true;
				   
			}catch(Exception e) {
				fullPathDocumento ="";
				log.error(Constantes.MSG_ERROR_GUARDAR_ARCHIVO_BITACORA,e);
			}
		}
		
		if(documentoGuardado) {
			bitacoraDesempenioBD.setRutaDocumentos(fullPathDocumento);
			bitacoraDesempenioBD.setNombreDocumento(request.getExpedienteDesempenio().getNombreDocto());
		}
		else if(!documentoGuardado && existeArchivoExp) {
			falloAlmacenarDocumento = true;
			if(request.getTipoAccion().equals(Constantes.FLUJO_MODIFICA)) {
				bitacoraDesempenioBD.setDocumentoCorreo(bitacoraDesempenioPrev.getDocumentoCorreo());
				bitacoraDesempenioBD.setDocumentoCitatorio(bitacoraDesempenioPrev.getDocumentoCitatorio());
				bitacoraDesempenioBD.setDocumentoConstancia(bitacoraDesempenioPrev.getDocumentoConstancia());
				bitacoraDesempenioBD.setRutaDocumentos(bitacoraDesempenioPrev.getRutaDocumentos());
				bitacoraDesempenioBD.setNombreDocumento(bitacoraDesempenioPrev.getNombreDocumento());
				
			}else if(request.getTipoAccion().equals(Constantes.FLUJO_CAPTURA)) {
				bitacoraDesempenioBD.setDocumentoCorreo(null);
				bitacoraDesempenioBD.setDocumentoCitatorio(null);
				bitacoraDesempenioBD.setDocumentoConstancia(null);
				bitacoraDesempenioBD.setRutaDocumentos(null);
				bitacoraDesempenioBD.setNombreDocumento(null);
			}		
		}
		
		if(request.getExpedienteDesempenio() != null 
				&& !existeArchivoExp && request.getTipoAccion().equals(Constantes.FLUJO_MODIFICA)) {//por si no se cambió el documento en moficiar
			bitacoraDesempenioBD.setRutaDocumentos(bitacoraDesempenioPrev.getRutaDocumentos());
			bitacoraDesempenioBD.setNombreDocumento(bitacoraDesempenioPrev.getNombreDocumento());
		}
		
		String rutaPreviaDoc = "";
		if(request.getExpedienteDesempenio() != null 
				&& request.getExpedienteDesempenio().getEliminarExpediente() != null 
					&& request.getExpedienteDesempenio().getEliminarExpediente().equals(1)) {
			bitacoraDesempenioBD.setDocumentoCorreo(null);
			bitacoraDesempenioBD.setDocumentoCitatorio(null);
			bitacoraDesempenioBD.setDocumentoConstancia(null);
			bitacoraDesempenioBD.setRutaDocumentos(null);
			bitacoraDesempenioBD.setNombreDocumento(null);
			
			rutaPreviaDoc = bitacoraDesempenioPrev.getRutaDocumentos();
		}
		
		try {
			
			asBitacoraDesempenio.guardarBitacoraDesempenio(bitacoraDesempenioBD, listResponsablesBD, request.getIdBitacoraDesempenio());
			
		}catch(Exception e) {
			log.error(Constantes.MSG_ERROR_GUARDAR_BITACORA, e);
			
			if(documentoGuardado)
				new File(fullPathDocumento).delete();
			
			throw new Exception(Constantes.MSG_ERROR_GUARDAR_BITACORA + e.getMessage());
		}
		
		//realizar eliminación del documento si fue especificado
		if(request.getExpedienteDesempenio() != null 
				&& request.getExpedienteDesempenio().getEliminarExpediente() != null 
				&& request.getExpedienteDesempenio().getEliminarExpediente().equals(1)) {
					try {
						new File(rutaPreviaDoc).delete();
						
					}catch(Exception e) {
						log.error(Constantes.MSG_ERROR_GUARDAR_ELLIM_ARCHIVO_BITACORA + rutaPreviaDoc +" - ",e);
					}
		}
		
		String mensajeGuardado = Constantes.MSG_EXITO_GUARDAR_BITACORA;
		
		if(falloAlmacenarDocumento)
			mensajeGuardado = Constantes.MSG_EXITO_GUARDAR_BITACORA_ERROR;
		
		return mensajeGuardado;
		
	}

	@Override
	public void eliminarBitacoraDesempenio(Integer idDetalleProceso, Integer idParticipacion,
			Integer idBitacoraDesempenio, Integer idAspirante) {
		asBitacoraDesempenio.eliminarBitacoraDesempenio(idDetalleProceso, idParticipacion, idBitacoraDesempenio, idAspirante);
		
	}

	private void cargarIntegrantesYResponsables(DTORequestBitacora request, 
                                            ModelResponseBitacora modelResponse
                                            ) throws Exception {
		DTOParticipacionGeografica geo = asBitacoraDesempenio.obtenerParticipacionGeo(
			request.getIdDetalleProceso(),
			request.getIdParticipacion()
		);
		DTOConsultaIntegrantes consultaIntegrantes = null;
		try {
			consultaIntegrantes = asBitacoraDesempenio.obtenerIntegrantesWsSesiones(
				TIPO_INTEGRANTES_WS_SESIONES,
				geo.getIdEstado(),
				geo.getIdDistrito(),
				TIPO_PUESTO_WS_SESIONES,
				TIPO_ESTATUS_WS_SESIONES,
				geo.getIdProcesoElectoral(),
				request.getIdDetalleProceso(),
				TIPO_ORDENAMIENTO_WS_SESIONES,
				CAMPO_ORDENAMIENTO_WS_SESIONES
			);
		} catch (Exception ex) {
			log.error("No se pudo obtener integrantes del WS, se usará catálogo estático", ex);
		}
		if (consultaIntegrantes != null && consultaIntegrantes.getIntegrantes() != null) {
			List<ModelResponseIntegrantesSesiones> integrantes = consultaIntegrantes.getIntegrantes();
			
			integrantes = integrantes.stream()
						.filter(i -> "VE".equals(i.getIniciales()) ||
									"VOE".equals(i.getIniciales()) ||
									"VCEEC".equals(i.getIniciales()))
						.sorted(Comparator.comparing(ModelResponseIntegrantesSesiones::getIdPuesto))
						.toList();
			
			modelResponse.setIntegrantesWS(integrantes);
		}else{
			List<ModelResponseIntegrantesSesiones> integrantes = asBitacoraDesempenio.obtenerCatalogoVocalesSust(geo.getIdProcesoElectoral(),
				request.getIdDetalleProceso()).stream()
			.map(vo -> {
			ModelResponseIntegrantesSesiones dto = new ModelResponseIntegrantesSesiones();
				dto.setIdIntegrante(0);//Identificar que viene de BD y no de WS
				dto.setIdPuesto(vo.getIdPuesto());
				dto.setIniciales(vo.getPuesto()); 
				dto.setTipoPuesto(vo.getTipoPuesto());
				dto.setTipoIntegrante(vo.getTipoPuesto());

			return dto;
			})
			.toList();

			modelResponse.setIntegrantesWS(integrantes);
		}

		if (modelResponse.getBitacoraDesempenio() != null &&
			modelResponse.getBitacoraDesempenio().getIdBitacoraDesempenio() != null) {

			modelResponse.setResponsablesBitacora(
				asBitacoraDesempenio.obtenerResponsablesBitacoraDesempenio(
					request.getIdDetalleProceso(),
					request.getIdParticipacion(),
					modelResponse.getBitacoraDesempenio().getIdBitacoraDesempenio()
				)
			);
    	}
}


}
