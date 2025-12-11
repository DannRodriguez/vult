package mx.ine.sustseycae.controllers.impl;

import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bo.BOValidacionesBitacoraDesempInterface;
import mx.ine.sustseycae.bsd.BSDBitacoraDesempenioInterface;
import mx.ine.sustseycae.controllers.ControllerBitacoraDesempenio;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Controller
public class ControllerBitacoraDesempenioImpl implements ControllerBitacoraDesempenio {

	private static final Log log = LogFactory.getLog(ControllerBitacoraDesempenioImpl.class);
	
	@Autowired
	@Qualifier("bsdBitacoraDesempenio")
	private BSDBitacoraDesempenioInterface bsdBitacoraDesempenio;
	
	@Autowired
	@Qualifier("boValidacionesBitacoraDesemp")
	private BOValidacionesBitacoraDesempInterface boValidacionesBitacoraDesemp;
	
	
	@Override
	public ModelGenericResponse obtenerAspiranteBitacora(DTORequestBitacora request) {
		 return ejecutarConValidacion(
            request,
            bsdBitacoraDesempenio::obtenerInfoAspiranteBitacora,
            validation -> null,
            Constantes.MSG_ERROR_INFO_BITACORA
        );
	}

	@Override
	public ModelGenericResponse obtenerExpedienteDesemp(DTORequestBitacora request) {
		 return ejecutarConValidacion(
            request,
            bsdBitacoraDesempenio::obtenerExpedienteDesempenio,
            validation -> null,
            Constantes.MSG_ERROR_INFO_BITACORA
        );
	}

	@Override
	public ModelGenericResponse obtenerExpedienteB64(DTORequestBitacora request) {
		return ejecutarConValidacion(
            request,
            bsdBitacoraDesempenio::obtenerBase64Expediente,
            validation -> null,
            Constantes.MSG_ERROR_INFO_BITACORA
        );
	}

	@Override
	public ModelGenericResponse obtenerEvaluacionDesemp(DTORequestBitacora request) {
		 return ejecutarConValidacion(
            request,
            bsdBitacoraDesempenio::obtenerEvaluacionDesempenio,
            validation -> null,
            Constantes.MSG_ERROR_INFO_BITACORA
        );
	}

	@Override
	public ModelGenericResponse guardarBitacoraDesemp(MultipartFile fileExpediente, DTORequestBitacora request) {
		
		 return ejecutarConValidacion(
            request,
            r -> {
                if (fileExpediente != null) {
                    r.getExpedienteDesempenio().setArchivoExpediente(fileExpediente);
                }
                return bsdBitacoraDesempenio.guardarBitacora(r);
            },
            boValidacionesBitacoraDesemp::validaRequestAlmacenarBitacora,
            Constantes.MSG_ERROR_GUARDAR_BITACORA
        );
	}

	@Override
	public ModelGenericResponse eliminarBitacoraDesemp(@Valid DTORequestBitacora request) {
	
		return ejecutarConValidacion(
            request,
            r -> {
                bsdBitacoraDesempenio.eliminarBitacoraDesempenio(
                    r.getIdDetalleProceso(),
                    r.getIdParticipacion(),
                    r.getIdBitacoraDesempenio(),
                    r.getIdAspirante()
                );
                return null;
            },
            validation -> null,
            Constantes.MSG_ERROR_ELIMINAR_BITACORA
        );
	}

	private ModelGenericResponse ejecutarConValidacion(
        DTORequestBitacora request,
        ThrowingFunction<DTORequestBitacora, Object> servicio,
        Function<DTORequestBitacora, String> validador,
        String mensajeErrorLog) {

    ModelGenericResponse response = new ModelGenericResponse();

    try {
        String msjValidacion = validador.apply(request);
        if (msjValidacion == null || msjValidacion.isBlank()) {
            Object result = servicio.apply(request);
            response.setData(result);
			ApplicationUtil.obtieneRespuestaExito(response);
        } else {
            ApplicationUtil.obtieneRespuestaBad(response, msjValidacion);
        }
    } catch (Exception e) {
        log.error(mensajeErrorLog + request, e);
        ApplicationUtil.obtieneRespuestaError(response, null);
    }

    return response;
	}
    
	@FunctionalInterface
    private interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }
    
}
