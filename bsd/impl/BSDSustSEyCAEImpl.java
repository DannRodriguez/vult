package mx.ine.sustseycae.bsd.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import mx.ine.sustseycae.as.ASSustSEyCAEInterface;
import mx.ine.sustseycae.bsd.BSDBitacoraDesempenioInterface;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.bsd.BSDSustSEyCAEInterface;
import mx.ine.sustseycae.dto.DTOExpedienteBitacora;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.helper.HLPCuentasSustitucionesInterface;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdSustSEyCAE")
@Scope("prototype")
public class BSDSustSEyCAEImpl implements BSDSustSEyCAEInterface {

    private static final Log log = LogFactory.getLog(BSDSustSEyCAEImpl.class);

    @Autowired
    @Qualifier("asSustSEyCAE")
    private ASSustSEyCAEInterface asSustSEyCAE;

    @Autowired
    @Qualifier("hlpCuentasSustituciones")
    private HLPCuentasSustitucionesInterface hlpCuentasSustituciones;

    @Autowired
    @Qualifier("bsdBitacoraDesempenio")
    private BSDBitacoraDesempenioInterface bsdBitacoraDesempenio;

    @Autowired
    @Qualifier("bsdCommons")
    private BSDCommons bsdCommons;

    @Override
    public String guardarSustitucionSEyCAE(DTORequestSutSEyCAE request, MultipartFile fileExpediente) throws Exception {
        DTOAspirantes sustitutoSE = null;
        DTOAspirantes sustitutoCAE = null;
        List<DTOAspirantes> aspirantesGestionarCuentas = new ArrayList<>();

        boolean exitoAlmacenarBitacora = guardarBitacora(request, fileExpediente);
        if (request.getProcesarSoloBitacora() != null && request.getProcesarSoloBitacora().equals(1)) {
            return Constantes.MSG_EXITO_SUST_BITACORA;

        }
        boolean isSustitucionPendiente = request.getEsPendiente().equals(1);
        if (isSustitucionPendiente && (request.getSustitucionPrevia() == null
                || request.getSustitucionPrevia().getIdSustitucion() == null)) {
            throw new Exception(" ERROR BSDSustSEyCAEImpl - guardarSustitucionSEyCAE - sustitución pendiente");
        }
        DTOAspirantes sustituido = obtenerAspirante(request.getIdProcesoElectoral(), request.getIdDetalleProceso(),
                request.getIdParticipacion(), request.getIdSustituido());

        if (request.getIdSustitutoSupervisor() != null && !request.getIdSustitutoSupervisor().equals(0)) {
            sustitutoSE = obtenerAspirante(request.getIdProcesoElectoral(), request.getIdDetalleProceso(),
                    request.getIdParticipacion(), request.getIdSustitutoSupervisor());
        }
        if (request.getIdSustitutoCapacitador() != null && !request.getIdSustitutoCapacitador().equals(0)) {
            sustitutoCAE = obtenerAspirante(request.getIdProcesoElectoral(), request.getIdDetalleProceso(),
                    request.getIdParticipacion(), request.getIdSustitutoCapacitador());
        }
        if (isSustitucionPendiente) {
            aspirantesGestionarCuentas = guardarSustitucionPendiente(request, sustitutoSE, sustitutoCAE);
        } else {
            if (sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_SE)) {
                aspirantesGestionarCuentas = asSustSEyCAE.guardarSustitucionSE(sustituido, sustitutoSE, sustitutoCAE,
                        request);
            } else if (sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
                aspirantesGestionarCuentas = asSustSEyCAE.guardarSustitucionCAE(sustituido, sustitutoCAE, request);
            }
        }
        guardarFotosAspirantes(request);
        if (aspirantesGestionarCuentas != null && !aspirantesGestionarCuentas.isEmpty()) {
            hlpCuentasSustituciones.adminCuentasSustitucion(aspirantesGestionarCuentas.get(0),
                    aspirantesGestionarCuentas.get(1), aspirantesGestionarCuentas.get(2), request.getUsuario());
        }
        return exitoAlmacenarBitacora ? Constantes.MSG_EXITO_SUST : Constantes.MSG_EXITO_SUST_NO_BITACORA;

    }

    private DTOAspirantes obtenerAspirante(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante) {
        DTOAspirantes sustituido = asSustSEyCAE.obtenerAspirantePorPK(idProcesoElectoral, idDetalleProceso,
                idParticipacion, idAspirante);
        if (sustituido == null) {
            throw new IllegalArgumentException("ERROR BSDSustSEyCAEImpl - obtenerAspirante " + ", idDetalle: "
                    + idDetalleProceso + ", idParticipacion: " + idParticipacion + ", idAspirante: " + idAspirante);
        }
        return sustituido;
    }

    private DTORequestBitacora generarInfoBitacora(DTORequestSutSEyCAE request, MultipartFile archivoExpediente) {
        DTORequestBitacora requestBitacora = new DTORequestBitacora();
        requestBitacora.setIdProcesoElectoral(request.getIdProcesoElectoral());
        requestBitacora.setIdDetalleProceso(request.getIdDetalleProceso());
        requestBitacora.setIdParticipacion(request.getIdParticipacion());
        requestBitacora.setIdAspirante(request.getIdSustituido());
        requestBitacora.setIdBitacoraDesempenio(request.getIdBitacoraDesempenio());
        requestBitacora.setOrigenBitacora(Constantes.ORIGEN_MODULO_SUST);
        requestBitacora.setTipoAccion(request.getTipoAccionBitacora());
        requestBitacora.setEvaluacionDesempenio(request.getEvaluacionDesempenio());

        DTOExpedienteBitacora expedienteDesempenio = request.getExpedienteDesempenio();
        if (expedienteDesempenio != null && archivoExpediente != null) {
            expedienteDesempenio.setArchivoExpediente(archivoExpediente);
        }
        requestBitacora.setExpedienteDesempenio(expedienteDesempenio);
        requestBitacora.setUsuario(request.getUsuario());
        return requestBitacora;
    }

    private boolean guardarBitacora(DTORequestSutSEyCAE request, MultipartFile fileExpediente) {
        try {
            if (request.getExisteInfoBitacora() != null && request.getExisteInfoBitacora().equals(1)) {
                DTORequestBitacora requestBitacora = generarInfoBitacora(request, fileExpediente);
                VOBitacoraDesempenio vo = bsdBitacoraDesempenio.obtenerExpedienteDesempenio(requestBitacora);
                if (vo != null && vo.getIdAspirante() != null) {
                    requestBitacora.setTipoAccion(Constantes.FLUJO_MODIFICA);
                }
                bsdBitacoraDesempenio.guardarBitacora(requestBitacora);
            }
            return true;
        } catch (Exception e) {
            log.error("ERROR BSDSustSEyCAEImpl - guardarBitacora: " + request.getIdDetalleProceso() + " - "
                    + request.getIdParticipacion() + " - " + request.getSustitucionSE() + " - "
                    + request.getSustitucionCAE() + " - ", e);
            return false;
        }
    }

    private List<DTOAspirantes> guardarSustitucionPendiente(DTORequestSutSEyCAE request, DTOAspirantes sustitutoSE,
            DTOAspirantes sustitutoCAE) throws Exception {
        if (request.getSustitucionPrevia().getIdPuestoSustituido().equals(Constantes.ID_PUESTO_SE)
                && sustitutoSE != null && sustitutoSE.getId() != null && sustitutoSE.getId().getIdAspirante() != null) {
            return asSustSEyCAE.guardarSustitucionPendienteSE(request.getSustitucionPrevia(), sustitutoSE, sustitutoCAE,
                    request);

        } else if (request.getSustitucionPrevia().getIdPuestoSustituido().equals(Constantes.ID_PUESTO_CAE)
                && sustitutoCAE != null && sustitutoCAE.getId() != null
                && sustitutoCAE.getId().getIdAspirante() != null) {
            return asSustSEyCAE.guardarSustitucionPendienteCAE(request.getSustitucionPrevia(), sustitutoCAE, request);
        }
        return Collections.emptyList();
    }

    private boolean guardarFotosAspirantes(DTORequestSutSEyCAE request) {
        try {

            if (request.getImagenB64Sustituido() != null && !request.getImagenB64Sustituido().isBlank()
                    && request.getExtensionImagenSustituido() != null
                    && !request.getExtensionImagenSustituido().isBlank()) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                        request.getExtensionImagenSustituido(), request.getIdProcesoElectoral(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(), request.getIdSustituido());
            }

            if (request.getImagenB64SustitutoSupervisor() != null
                    && !request.getImagenB64SustitutoSupervisor().isBlank()
                    && request.getExtensionImagenSustitutoSupervisor() != null
                    && !request.getExtensionImagenSustitutoSupervisor().isBlank()) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoSupervisor(),
                        request.getExtensionImagenSustitutoSupervisor(), request.getIdProcesoElectoral(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        request.getIdSustitutoSupervisor());
            }

            if (request.getImagenB64SustitutoCapacitador() != null
                    && !request.getImagenB64SustitutoCapacitador().isBlank()
                    && request.getExtensionImagenSustitutoCapacitador() != null
                    && !request.getExtensionImagenSustitutoCapacitador().isBlank()) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCapacitador(),
                        request.getExtensionImagenSustitutoCapacitador(), request.getIdProcesoElectoral(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        request.getIdSustitutoCapacitador());
            }
            return true;
        } catch (Exception e) {
            log.error("ERROR BSDSustSEyCAEImpl - guardarFotosAspirantes: " + request.getIdDetalleProceso() + " - "
                    + request.getIdParticipacion() + " - " + request.getSustitucionSE() + " - "
                    + request.getSustitucionCAE() + " - ", e);
            return false;
        }
    }

    @Override
    public String modificarSustitucionSEyCAE(DTORequestSutSEyCAE request, MultipartFile fileExpediente)
            throws Exception {

        asSustSEyCAE.modificarSustitucionSEyCAE(request);
        boolean exitoAlmacenarBitacora = guardarBitacora(request, fileExpediente);
        guardarFotosAspirantes(request);

        return exitoAlmacenarBitacora ? Constantes.MSG_EXITO_SUST : Constantes.MSG_EXITO_SUST_NO_BITACORA;
    }

}
