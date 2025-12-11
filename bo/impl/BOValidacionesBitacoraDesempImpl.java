package mx.ine.sustseycae.bo.impl;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.sustseycae.bo.BOValidacionesBitacoraDesempInterface;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenioId;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenioId;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.responses.ModelResponseIntegrantesSesiones;
import mx.ine.sustseycae.util.Constantes;

@Component("boValidacionesBitacoraDesemp")
@Scope("prototype")
public class BOValidacionesBitacoraDesempImpl  implements BOValidacionesBitacoraDesempInterface{

	@Override
	public String validaRequestAlmacenarBitacora(DTORequestBitacora request) {
		   
		String msj;
			
			Map<Integer, String> campos = new LinkedHashMap<>();
			campos.put(request.getIdDetalleProceso(), Constantes.MSG_REQUEST_DETALLE_MIN);
			campos.put(request.getIdParticipacion(), Constantes.MSG_REQUEST_PARTICIPACION_MIN);
			campos.put(request.getIdAspirante(), Constantes.MSG_REQUEST_ASPIRANTE_MIN);
			campos.put(request.getTipoAccion(), Constantes.MSG_ERROR_ACCION_BITACORA);
			campos.put(request.getOrigenBitacora(), Constantes.MSG_ERROR_ORIGEN_BITACORA);

			msj = validaCampos(campos);
			if (msj != null) return msj;

			if (Constantes.FLUJO_MODIFICA.equals(request.getTipoAccion())) {
				msj = validaCampos(Map.of(
						request.getIdBitacoraDesempenio(), Constantes.MSG_ERROR_ID_BITACORA
				));
				if (msj != null) return msj;
			}

			if (!Constantes.FLUJO_CAPTURA.equals(request.getTipoAccion()) &&
				!Constantes.FLUJO_MODIFICA.equals(request.getTipoAccion())) {
				return Constantes.MSG_ERROR_ACCION_BITACORA;
			}

			if (!Constantes.FLUJO_CAPTURA.equals(request.getOrigenBitacora()) &&
				!Constantes.FLUJO_MODIFICA.equals(request.getOrigenBitacora())) {
				return Constantes.MSG_ERROR_ORIGEN_BITACORA;
			}
			
			if (request.getEvaluacionDesempenio() == null) {
				return Constantes.MSG_ERROR_FALTA_INFO_BITACORA;
			}

		return null; 
	}
	
	

	@Override
	public DTOBitacoraDesempenio dtoRequestToBitacoraDesempenio(DTORequestBitacora request) throws Exception{
		
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipEjecucion = inetAddress.getHostAddress();
		
		DTOBitacoraDesempenio bitacoraDesempenio = new DTOBitacoraDesempenio();
		DTOBitacoraDesempenioId id = new DTOBitacoraDesempenioId();
		id.setIdDetalleProceso(request.getIdDetalleProceso());
		id.setIdParticipacion(request.getIdParticipacion());
		
		bitacoraDesempenio.setId(id);
		bitacoraDesempenio.setIdProcesoElectoral(request.getIdProcesoElectoral());
		bitacoraDesempenio.setIdAspirante(request.getIdAspirante());
		bitacoraDesempenio.setOrigenBitacora(request.getOrigenBitacora());
		
		if(request.getDevolvioPrendas() != null)
			bitacoraDesempenio.setDevolvioPrendas(request.getDevolvioPrendas());
		
		if((request.getExpedienteDesempenio() != null)) {
			bitacoraDesempenio.setDocumentoCorreo(request.getExpedienteDesempenio().getCorreo());
			bitacoraDesempenio.setDocumentoCitatorio(request.getExpedienteDesempenio().getCitatorio());
			bitacoraDesempenio.setDocumentoConstancia(request.getExpedienteDesempenio().getConstancia());
		}
		
		bitacoraDesempenio.setIdImpacto(request.getEvaluacionDesempenio().getValoracionRiesgo().getIdImpacto());
		bitacoraDesempenio.setIdFrecuencia(request.getEvaluacionDesempenio().getValoracionRiesgo().getIdFrecuencia());
		bitacoraDesempenio.setIdValoracionRiesgo(request.getEvaluacionDesempenio().getValoracionRiesgo().getIdValoracionRiesgo());
		bitacoraDesempenio.setObservaciones(request.getEvaluacionDesempenio().getObservaciones());
		bitacoraDesempenio.setUsuario(request.getUsuario());
		bitacoraDesempenio.setIpUsuario(ipEjecucion);
		
		 return bitacoraDesempenio;
	}

	@Override
	public List<DTOResponsablesBitacoraDesempenio> obtenerResponsablesBitacora(DTORequestBitacora request)throws Exception {
		
		InetAddress inetAddress = InetAddress.getLocalHost();
		String ipEjecucion = inetAddress.getHostAddress();
		List<DTOResponsablesBitacoraDesempenio> responsablesBitacora = new ArrayList<>();
		
		var evaluacion = request.getEvaluacionDesempenio();
		
		if (evaluacion != null && evaluacion.getResponsables() != null) {
            Map<String, Integer> mapaIniciales = Map.of(
                    "VE", 1,
                    "VOE", 2,
                    "VCEEC", 3
        );

        for (ModelResponseIntegrantesSesiones integrante : evaluacion.getResponsables()) {
                DTOResponsablesBitacoraDesempenioId id = new DTOResponsablesBitacoraDesempenioId();
                id.setIdDetalleProceso(request.getIdDetalleProceso());
                id.setIdParticipacion(request.getIdParticipacion());
                id.setIdResponsableBitacora(mapaIniciales.getOrDefault(integrante.getIniciales(), 0));

                DTOResponsablesBitacoraDesempenio resp = new DTOResponsablesBitacoraDesempenio();
                resp.setId(id);
                resp.setIdPuestoFuncionario(integrante.getIdIntegrante());
                resp.setTipoPuesto(String.valueOf(integrante.getTipoIntegrante()));
                resp.setInicialesPuesto(integrante.getIniciales());
                resp.setNombre(integrante.getNombreIntegrante());
                resp.setApellidoPaterno(integrante.getPrimerApellidoIntegrante());
                resp.setApellidoMaterno(integrante.getSegundoApellidoIntegrante());
                resp.setTratamiento(integrante.getGradoAcademico());
                resp.setIdPuesto(integrante.getIdPuesto());
                resp.setUsuario(request.getUsuario());
                resp.setIpUsuario(ipEjecucion);

                responsablesBitacora.add(resp);
            }
        }

        return responsablesBitacora;
	}

	@Override
	public String buildNombreFoto(DTORequestBitacora request) {
		return ( 	 "P"+request.getIdProcesoElectoral()
							+"D"+request.getIdDetalleProceso()
							+"PART"+request.getIdParticipacion()
							+"ASP"+request.getIdAspirante()
							+request.getExtensionFoto());
	}

	@Override
	public String buildCarpetaExpedienteGluster(DTORequestBitacora request, String carpetaSupycap) {
		return carpetaSupycap+File.separator
				+request.getIdProcesoElectoral()+File.separator
				+request.getIdDetalleProceso()+File.separator
				+Constantes.CARPETA_WEB_GLUSTER+File.separator
				+Constantes.CARPETA_EXPEDIENTE_GLUSTER+File.separator;
	}

	@Override
	public String buildNombreExpediente(DTORequestBitacora request) {
		return ( "EXP_P"+request.getIdProcesoElectoral()
							+"D"+request.getIdDetalleProceso()
							+"PART"+request.getIdParticipacion()
							+"ASP"+request.getIdAspirante()
							+request.getExpedienteDesempenio().getExtensionArchivo());
	}

	private boolean validaInt(Integer valor) {
		return valor != null && valor > 0;
	}

	private String validaCampos(Map<Integer, String> campos) {
    for (Map.Entry<Integer, String> entry : campos.entrySet()) {
        if (!validaInt(entry.getKey())) {
            return entry.getValue();
        }
    }
    	return null;
	}
}
