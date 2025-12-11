package mx.ine.sustseycae.as.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASCommons;
import mx.ine.sustseycae.as.ASSustIncapacidad;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.helper.HLPCuentasSustitucionesInterface;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelResponseSustitucionesRelacion;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPACCausasVacante;
import mx.ine.sustseycae.repositories.RepoJPASustitucionesSupycap;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Service("asSustIncapacidad")
public class ASSustIncapacidadImpl implements ASSustIncapacidad {

    private static final String SIN_OBSERVACIONES = "Sin observaciones";

    @Autowired
    private BSDCommons bsdCommons;

    @Autowired
    private ASCommons asCommons;

    @Autowired
    private HLPCuentasSustitucionesInterface hlpCuentasSustituciones;

    @Autowired
    private RepoJPASustitucionesSupycap repoJPASustitucionesSupycap;

    @Autowired
    private RepoJPACCausasVacante repoJPACCausasVacante;

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
        Exception.class}, transactionManager = "transactionManager")
    public boolean insertaSustitucion(DTORequestSustIncapacidad request) {

        String idRelacion = UUID.randomUUID().toString();

        DTOAspirantes sustituido = repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustituido());
        DTOAspirantes sustitutoSE = repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustitutoSupervisor());
        DTOAspirantes sustitutoCAE = repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustituidoCapacitador());

        if (!request.getIdSustitutoSupervisor().equals(0) && !request.getIdSustituidoCapacitador().equals(0)) {

            DTOAspirantes aspSustitutoSE = new DTOAspirantes(sustitutoSE);

            boolean guardaPrimeraSust = generaSustitucion(request, sustituido, sustitutoSE,
                    Constantes.ID_PUESTO_SE_INCAP, Constantes.ID_PUESTO_SE_TMP, null,
                    request.getFechaBajaSustituido(), request.getDateSuplenteSupervisor(),
                    idRelacion);

            if (guardaPrimeraSust && request.getImagenB64Sustituido() != null) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                        request.getExtensionArchivoSustituido(), request.getIdProceso(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        sustituido.getIdAspirante());
            }

            request.setIdCausaVacante(3);
            request.setObservacionesBajaSustituido(SIN_OBSERVACIONES);

            boolean guardaSegundaSust = generaSustitucion(request, aspSustitutoSE, sustitutoCAE,
                    Constantes.ID_PUESTO_SE_TMP, Constantes.ID_PUESTO_CAE_TMP, sustitutoSE,
                    request.getDateSuplenteSupervisor(), request.getDateSuplenteCapacitador(),
                    idRelacion);

            if (guardaSegundaSust && request.getImagenB64SustitutoSE() != null) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoSE(),
                        request.getExtensionArchivoSustitutoSE(), request.getIdProceso(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        aspSustitutoSE.getIdAspirante());
            }

            if (guardaSegundaSust && request.getImagenB64SustitutoCAE() != null) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCAE(),
                        request.getExtensionArchivoSustitutoCAE(), request.getIdProceso(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        sustitutoCAE.getIdAspirante());
            }

        } else if (!request.getIdSustitutoSupervisor().equals(0)) {
            DTOAspirantes aspSustitutoSE = new DTOAspirantes(sustitutoSE);
            boolean guardaPrimeraSust = generaSustitucion(request, sustituido, sustitutoSE,
                    Constantes.ID_PUESTO_SE_INCAP, Constantes.ID_PUESTO_SE_TMP, null,
                    request.getFechaBajaSustituido(), request.getDateSuplenteSupervisor(),
                    idRelacion);

            if (guardaPrimeraSust && request.getImagenB64Sustituido() != null) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                        request.getExtensionArchivoSustituido(), request.getIdProceso(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        sustituido.getIdAspirante());
            }

            if (Constantes.ID_PUESTO_CAE.equals(aspSustitutoSE.getIdPuesto())) {
                request.setIdCausaVacante(3);
                request.setObservacionesBajaSustituido(SIN_OBSERVACIONES);
                generaSustitucion(request, aspSustitutoSE, new DTOAspirantes(),
                        Constantes.ID_PUESTO_SE_TMP, null, sustitutoSE,
                        request.getDateSuplenteSupervisor(), "null", idRelacion);
            }

            if (guardaPrimeraSust && request.getImagenB64SustitutoSE() != null) {
                bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoSE(),
                        request.getExtensionArchivoSustitutoSE(), request.getIdProceso(),
                        request.getIdDetalleProceso(), request.getIdParticipacion(),
                        aspSustitutoSE.getIdAspirante());
            }

        } else {
            if (!request.getIdSustituidoCapacitador().equals(0)) {
                boolean guardaPrimeraSust = generaSustitucion(request, sustituido, sustitutoCAE,
                        Constantes.ID_PUESTO_SE.equals(sustituido.getIdPuesto())
                        ? Constantes.ID_PUESTO_SE_INCAP
                        : Constantes.ID_PUESTO_CAE_INCAP,
                        Constantes.ID_PUESTO_CAE_TMP, sustituido,
                        request.getFechaBajaSustituido(),
                        Constantes.ID_PUESTO_SE.equals(sustituido.getIdPuesto())
                        ? request.getDateSuplenteSupervisor()
                        : request.getDateSuplenteCapacitador(),
                        idRelacion);

                if (guardaPrimeraSust && request.getImagenB64Sustituido() != null) {
                    bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                            request.getExtensionArchivoSustituido(), request.getIdProceso(),
                            request.getIdDetalleProceso(), request.getIdParticipacion(),
                            sustituido.getIdAspirante());
                }

                if (guardaPrimeraSust && request.getImagenB64SustitutoCAE() != null) {
                    bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCAE(),
                            request.getExtensionArchivoSustitutoCAE(),
                            request.getIdProceso(), request.getIdDetalleProceso(),
                            request.getIdParticipacion(), sustitutoCAE.getIdAspirante());

                }

            } else {
                boolean guardaPrimeraSust = generaSustitucion(request, sustituido, new DTOAspirantes(),
                        Constantes.ID_PUESTO_SE.equals(sustituido.getIdPuesto())
                        ? Constantes.ID_PUESTO_SE_INCAP
                        : Constantes.ID_PUESTO_CAE_INCAP,
                        null, null, request.getFechaBajaSustituido(), "null", idRelacion);

                if (guardaPrimeraSust && request.getImagenB64Sustituido() != null) {
                    bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                            request.getExtensionArchivoSustituido(), request.getIdProceso(),
                            request.getIdDetalleProceso(), request.getIdParticipacion(),
                            sustituido.getIdAspirante());
                }
            }

        }

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
        Exception.class}, transactionManager = "transactionManager")
    public boolean actualizaPendiente(DTORequestSustIncapacidad request) {

        DTOAspirantes aspSustituido = repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustituido());

        if (Constantes.ID_PUESTO_SE_INCAP.equals(aspSustituido.getIdPuesto())) {

            if (!request.getIdSustitutoSupervisor().equals(0)
                    && !request.getIdSustituidoCapacitador().equals(0)) {
                DTOAspirantes aspSustituto = repoJPAAspirantes
                        .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                request.getIdSustitutoSupervisor());
                DTOAspirantes respAspSustituto = new DTOAspirantes(aspSustituto);

                List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustitucion(),
                        request.getIdSustituido());

                if (!sustituciones.isEmpty()) {
                    VOSustitucionesSupycap sustitucion = sustituciones.get(0);

                    Date fechaCompletadoAntesNuevoRegistro = new Date();
                    DTOAspirantes sustitutoDelCae = repoJPAAspirantes
                            .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                    request.getIdProceso(),
                                    request.getIdDetalleProceso(),
                                    request.getIdParticipacion(),
                                    request.getIdSustituidoCapacitador());
                    request.setIdCausaVacante(3);
                    request.setObservacionesBajaSustituido(SIN_OBSERVACIONES);
                    boolean guardaSegundaSust = generaSustitucion(request, respAspSustituto,
                            sustitutoDelCae, Constantes.ID_PUESTO_SE_TMP,
                            Constantes.ID_PUESTO_CAE_TMP, respAspSustituto,
                            request.getDateSuplenteSupervisor(),
                            request.getDateSuplenteCapacitador(),
                            sustitucion.getIdRelacionSustituciones());

                    if (guardaSegundaSust && request.getImagenB64SustitutoCAE() != null) {
                        bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCAE(),
                                request.getExtensionArchivoSustitutoCAE(),
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                sustitutoDelCae.getIdAspirante());
                    }

                    mapeaGuardaSustitucion(sustitucion, aspSustituto,
                            request.getDateSuplenteSupervisor(),
                            fechaCompletadoAntesNuevoRegistro, request.getUsuario(),
                            request.getIpUsuario());

                    if (aspSustituto.getId() != null) {
                        if (aspSustituto.getIdPuesto().equals(3)) {
                            aspSustituto.setDeclinoCargo(null);
                        }
                        aspSustituto.setIdPuesto(Constantes.ID_PUESTO_SE_TMP);
                        aspSustituto.setExisteSustitucion("S");
                        if (Constantes.ID_PUESTO_SE
                                .equals(sustitucion.getIdPuestoSustituido())) {
                            aspSustituto.setIdZonaResponsabilidad1e(
                                    sustitucion.getIdAZonaResponsabilidad1e());
                            aspSustituto.setIdZonaResponsabilidad2e(
                                    sustitucion.getIdZonaResponsabilidad2e());
                            aspSustituto.setIdAreaResponsabilidad1e(null);
                            aspSustituto.setIdAreaResponsabilidad2e(null);
                        } else if (Constantes.ID_PUESTO_CAE
                                .equals(sustitucion.getIdPuestoSustituido())) {
                            aspSustituto.setIdZonaResponsabilidad1e(null);
                            aspSustituto.setIdZonaResponsabilidad2e(null);
                            aspSustituto.setIdAreaResponsabilidad1e(
                                    sustitucion.getIdAreaResponsabilidad1e());
                            aspSustituto.setIdAreaResponsabilidad2e(
                                    sustitucion.getIdAreaResponsabilidad2e());
                        }

                        guardaAspirante(aspSustituto, request.getUsuario(),
                                request.getIpUsuario());

                        if (request.getImagenB64SustitutoSE() != null) {
                            bsdCommons.almacenarFotoAspirante(
                                    request.getImagenB64SustitutoSE(),
                                    request.getExtensionArchivoSustitutoSE(),
                                    request.getIdProceso(),
                                    request.getIdDetalleProceso(),
                                    request.getIdParticipacion(),
                                    aspSustituto.getIdAspirante());
                        }
                    }

                }

            } else {

                DTOAspirantes aspSustituto = repoJPAAspirantes
                        .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                Constantes.ID_PUESTO_SE_INCAP.equals(aspSustituido
                                        .getIdPuesto()) ? request
                                                .getIdSustitutoSupervisor()
                                        : request.getIdSustituidoCapacitador());
                DTOAspirantes respAspSustituto = new DTOAspirantes(aspSustituto);

                List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), request.getIdSustitucion(),
                        request.getIdSustituido());

                if (!sustituciones.isEmpty()) {
                    VOSustitucionesSupycap sustitucion = sustituciones.get(0);

                    Date fechaCompletadoAntesNuevoRegistro = new Date();
                    if (Constantes.ID_PUESTO_SE_INCAP.equals(aspSustituido.getIdPuesto())
                            && Constantes.ID_PUESTO_CAE
                                    .equals(respAspSustituto.getIdPuesto())) {
                        request.setIdCausaVacante(3);
                        request.setObservacionesBajaSustituido(SIN_OBSERVACIONES);
                        generaSustitucion(request, respAspSustituto, new DTOAspirantes(),
                                Constantes.ID_PUESTO_SE_TMP, null, null,
                                request.getDateSuplenteSupervisor(), "null",
                                sustitucion.getIdRelacionSustituciones());
                    }

                    mapeaGuardaSustitucion(sustitucion, aspSustituto,
                            request.getDateSuplenteSupervisor(),
                            fechaCompletadoAntesNuevoRegistro, request.getUsuario(),
                            request.getIpUsuario());

                    repoJPASustitucionesSupycap
                            .saveAndFlush(new DTOSustitucionesSupycap(sustitucion));

                    if (aspSustituto.getId() != null) {
                        Integer puestoOriginalSustituto = aspSustituto.getIdPuesto();
                        if (aspSustituto.getIdPuesto().equals(3)) {
                            aspSustituto.setDeclinoCargo(null);
                        }
                        aspSustituto.setIdPuesto(Constantes.ID_PUESTO_SE_TMP);
                        aspSustituto.setExisteSustitucion("S");
                        if (Constantes.ID_PUESTO_SE
                                .equals(sustitucion.getIdPuestoSustituido())) {
                            aspSustituto.setIdZonaResponsabilidad1e(
                                    sustitucion.getIdAZonaResponsabilidad1e());
                            aspSustituto.setIdZonaResponsabilidad2e(
                                    sustitucion.getIdZonaResponsabilidad2e());
                            aspSustituto.setIdAreaResponsabilidad1e(null);
                            aspSustituto.setIdAreaResponsabilidad2e(null);
                        } else if (Constantes.ID_PUESTO_CAE
                                .equals(sustitucion.getIdPuestoSustituido())) {
                            aspSustituto.setIdZonaResponsabilidad1e(null);
                            aspSustituto.setIdZonaResponsabilidad2e(null);
                            aspSustituto.setIdAreaResponsabilidad1e(
                                    sustitucion.getIdAreaResponsabilidad1e());
                            aspSustituto.setIdAreaResponsabilidad2e(
                                    sustitucion.getIdAreaResponsabilidad2e());
                        }
                        if (puestoOriginalSustituto == 3 && (request
                                .getCuentaSuplenteCapacitador() != null
                                || !request.getCuentaSuplenteCapacitador().isBlank())) {
                            aspSustituto.setCorreoCtaCreada(
                                    request.getCuentaSuplenteCapacitador());
                        }

                        guardaAspirante(aspSustituto, request.getUsuario(),
                                request.getIpUsuario());

                        if (request.getImagenB64SustitutoSE() != null) {
                            bsdCommons.almacenarFotoAspirante(
                                    request.getImagenB64SustitutoSE(),
                                    request.getExtensionArchivoSustitutoSE(),
                                    request.getIdProceso(),
                                    request.getIdDetalleProceso(),
                                    request.getIdParticipacion(),
                                    aspSustituto.getIdAspirante());
                        }

                        if (Constantes.ID_PUESTO_CAE.equals(puestoOriginalSustituto)
                                || Constantes.ID_PUESTO_SE_TMP
                                        .equals(puestoOriginalSustituto)) {
                            hlpCuentasSustituciones.modificarCuentaSustitucion(aspSustituto,
                                    request.getUsuario());
                        } else {
                            hlpCuentasSustituciones.crearCuentaSustitucion(aspSustituto,
                                    request.getUsuario());
                        }
                    }

                }
            }

        } else if (Constantes.ID_PUESTO_SE_TMP.equals(aspSustituido.getIdPuesto())
                || Constantes.ID_PUESTO_CAE_INCAP.equals(aspSustituido.getIdPuesto())) {
            List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(request.getIdProceso(),
                    request.getIdDetalleProceso(), request.getIdParticipacion(),
                    request.getIdSustitucion(), request.getIdSustituido());

            if (!sustituciones.isEmpty()) {
                VOSustitucionesSupycap sustitucion = sustituciones.get(0);

                DTOAspirantes sustitutoDelCae = repoJPAAspirantes
                        .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                request.getIdSustituidoCapacitador());

                mapeaGuardaSustitucion(sustitucion, sustitutoDelCae,
                        request.getDateSuplenteCapacitador(), new Date(), request.getUsuario(),
                        request.getIpUsuario());

                if (sustitutoDelCae.getId() != null) {
                    Integer puestoOriginalSustitutoCae = sustitutoDelCae.getIdPuesto();
                    if (sustitutoDelCae.getIdPuesto().equals(3)) {
                        sustitutoDelCae.setDeclinoCargo(null);
                    }
                    sustitutoDelCae.setIdPuesto(Constantes.ID_PUESTO_CAE_TMP);
                    sustitutoDelCae.setExisteSustitucion("S");
                    if (Constantes.ID_PUESTO_SE.equals(sustitucion.getIdPuestoSustituido())) {
                        sustitutoDelCae.setIdZonaResponsabilidad1e(
                                sustitucion.getIdAZonaResponsabilidad1e());
                        sustitutoDelCae.setIdZonaResponsabilidad2e(
                                sustitucion.getIdZonaResponsabilidad2e());
                        sustitutoDelCae.setIdAreaResponsabilidad1e(null);
                        sustitutoDelCae.setIdAreaResponsabilidad2e(null);
                    } else if (Constantes.ID_PUESTO_CAE
                            .equals(sustitucion.getIdPuestoSustituido())) {
                        sustitutoDelCae.setIdZonaResponsabilidad1e(null);
                        sustitutoDelCae.setIdZonaResponsabilidad2e(null);
                        sustitutoDelCae.setIdAreaResponsabilidad1e(
                                sustitucion.getIdAreaResponsabilidad1e());
                        sustitutoDelCae.setIdAreaResponsabilidad2e(
                                sustitucion.getIdAreaResponsabilidad2e());
                    }

                    if (puestoOriginalSustitutoCae == 3) {
                        sustitutoDelCae.setCorreoCtaCreada(
                                request.getCuentaSuplenteCapacitador());
                    }

                    guardaAspirante(sustitutoDelCae, request.getUsuario(), request.getIpUsuario());

                    if (request.getImagenB64SustitutoCAE() != null) {
                        bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCAE(),
                                request.getExtensionArchivoSustitutoCAE(),
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                sustitutoDelCae.getIdAspirante());
                    }

                    hlpCuentasSustituciones.crearCuentaSustitucion(sustitutoDelCae,
                            request.getUsuario());
                }

            }
        }

        return true;
    }

    @Override
    public boolean modificaSustitucion(DTORequestModifSustIncap request) {

        try {
            List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(request.getIdProceso(),
                    request.getIdDetalleProceso(), request.getIdParticipacion(),
                    request.getIdSustitucion(), request.getIdSustituidoPrimeraSust());

            if (!sustituciones.isEmpty()) {
                VOSustitucionesSupycap sustitucion = sustituciones.get(0);

                actualizaFechasObservacionesSustitucion(sustitucion,
                        request.getFechaAltaSustitutoPrimeraSust(),
                        request.getFechaBajaSustituido(), request.getObservacionesSustituido());

                if (request.getImagenB64Sustituido() != null
                        && request.getExtensionArchivoSustituido() != null) {
                    bsdCommons.almacenarFotoAspirante(request.getImagenB64Sustituido(),
                            request.getExtensionArchivoSustituido(), request.getIdProceso(),
                            request.getIdDetalleProceso(), request.getIdParticipacion(),
                            request.getIdSustituidoPrimeraSust());
                }

                if (request.getImagenB64SustitutoSE() != null
                        && request.getExtensionArchivoSustitutoSE() != null
                        && sustitucion.getIdAspiranteSutituto() != null) {
                    bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoSE(),
                            request.getExtensionArchivoSustitutoSE(),
                            request.getIdProceso(), request.getIdDetalleProceso(),
                            request.getIdParticipacion(),
                            sustitucion.getIdAspiranteSutituto());
                }
            }

            if (request.getIdSustituidoSegundaSust() != 0) {
                List<VOSustitucionesSupycap> segundasSustituciones = repoJPASustitucionesSupycap
                        .obtenerInfoSustitucionPorFiltro(request.getIdProceso(),
                                request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                request.getIdSustituidoSegundaSust(), null, null);

                if (!sustituciones.isEmpty()) {

                    VOSustitucionesSupycap segundaSustitucion = segundasSustituciones.get(0);

                    actualizaFechasObservacionesSustitucion(segundaSustitucion,
                            request.getFechaAltaSustitutoSegundaSust(),
                            request.getFechaAltaSustitutoPrimeraSust(), null);

                    if (request.getImagenB64SustitutoCAE() != null
                            && request.getExtensionArchivoSustitutoCAE() != null
                            && segundaSustitucion.getIdAspiranteSutituto() != null) {
                        bsdCommons.almacenarFotoAspirante(request.getImagenB64SustitutoCAE(),
                                request.getExtensionArchivoSustitutoCAE(),
                                request.getIdProceso(), request.getIdDetalleProceso(),
                                request.getIdParticipacion(),
                                segundaSustitucion.getIdAspiranteSutituto());
                    }

                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public VOSustitucionesSupycap obtenerInfoSustitucion(DTORequestSustIncapacidad request) {
        List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(request.getIdProceso(),
                request.getIdDetalleProceso(), request.getIdParticipacion(), request.getIdSustitucion(),
                request.getIdSustituido());

        if (!sustituciones.isEmpty()) {
            return obtieneSustitucionCompleta(sustituciones);
        }

        return new VOSustitucionesSupycap();
    }

    @Override
    public ModelResponseSustitucionesRelacion consultaSustitucionesRelacion(DTORequestSustIncapacidad request) {
        ModelResponseSustitucionesRelacion response = new ModelResponseSustitucionesRelacion();
        List<VOSustitucionesSupycap> sustituciones = obtieneSustituciones(request.getIdProceso(),
                request.getIdDetalleProceso(), request.getIdParticipacion(), request.getIdSustitucion(),
                request.getIdSustituido());

        if (sustituciones.isEmpty()) {
            return response;
        }

        VOSustitucionesSupycap sustitucion = obtieneSustitucionCompleta(sustituciones);

        response.setPrimeraSustitucion(sustitucion);

        if (sustitucion.getIdAspiranteSutituto() != null) {
            request.setIdSustituido(sustitucion.getIdAspiranteSutituto());
            response.setSustitutoUno(asCommons.obtenerInfoSustituido(request.getIdDetalleProceso(),
                    request.getIdParticipacion(), sustitucion.getIdAspiranteSutituto()));

            List<VOSustitucionesSupycap> segundaSust = repoJPASustitucionesSupycap
                    .obtenerInfoSustitucionPorFiltro(request.getIdProceso(),
                            request.getIdDetalleProceso(), request.getIdParticipacion(),
                            request.getIdSustituido(), null,
                            sustitucion.getIdRelacionSustituciones());
            if (!segundaSust.isEmpty()) {
                VOSustitucionesSupycap sustitucionDos = obtieneSustitucionCompleta(segundaSust);
                response.setSegundaSustitucion(sustitucionDos);

                if (sustitucionDos.getIdAspiranteSutituto() != null) {
                    request.setIdSustituido(sustitucionDos.getIdAspiranteSutituto());
                    response.setSustitutoDos(asCommons.obtenerInfoSustituido(
                            request.getIdDetalleProceso(), request.getIdParticipacion(),
                            sustitucionDos.getIdAspiranteSutituto()));
                }
                response.setEsSustitucionCausante(true);
            } else {
                Integer conteoSustituciones = repoJPASustitucionesSupycap.countSustitucionesRelacion(
                        request.getIdProceso(), request.getIdDetalleProceso(),
                        request.getIdParticipacion(), sustitucion.getIdRelacionSustituciones());

                if (conteoSustituciones == 1) {
                    response.setEsSustitucionCausante(true);
                }
            }
        } else {
            Integer conteoSustituciones = repoJPASustitucionesSupycap.countSustitucionesRelacion(
                    request.getIdProceso(), request.getIdDetalleProceso(),
                    request.getIdParticipacion(), sustitucion.getIdRelacionSustituciones());

            if (conteoSustituciones == 1) {
                response.setEsSustitucionCausante(true);
            }
        }

        return response;
    }

    private boolean generaSustitucion(DTORequestSustIncapacidad request, DTOAspirantes aspiranteSustituido,
            DTOAspirantes aspiranteSustituto, Integer idPuestoSustituido, Integer idPuestoSustituto,
            DTOAspirantes aspiranteSustituidoActual, String fechaBaja, String fechaAlta,
            String idRelacion) {

        try {
            guardaSustitucion(request, aspiranteSustituido, aspiranteSustituto, fechaBaja, fechaAlta,
                    idRelacion);

            Integer idPuestoOriginalSustituido = aspiranteSustituido.getIdPuesto();
            Integer idAre1eOriginalSustituido = null;
            Integer idAre2eOriginalSustituido = null;

            aspiranteSustituido.setIdPuesto(idPuestoSustituido);
            aspiranteSustituido.setExisteSustitucion("S");

            if (aspiranteSustituidoActual != null) {

                idAre1eOriginalSustituido = aspiranteSustituido.getIdAreaResponsabilidad1e();
                idAre2eOriginalSustituido = aspiranteSustituido.getIdAreaResponsabilidad2e();

                aspiranteSustituido.setIdZonaResponsabilidad1e(
                        aspiranteSustituidoActual.getIdZonaResponsabilidad1e());
                aspiranteSustituido.setIdZonaResponsabilidad2e(
                        aspiranteSustituidoActual.getIdZonaResponsabilidad2e());
                aspiranteSustituido.setIdAreaResponsabilidad1e(
                        aspiranteSustituidoActual.getIdAreaResponsabilidad1e());
                aspiranteSustituido.setIdAreaResponsabilidad2e(
                        aspiranteSustituidoActual.getIdAreaResponsabilidad2e());
            }
            guardaAspirante(aspiranteSustituido, request.getUsuario(), request.getIpUsuario());

            if (!Constantes.ID_PUESTO_SE_TMP.equals(idPuestoSustituido)) {
                if (Constantes.ID_PUESTO_SE_INCAP.equals(idPuestoSustituido)
                        || Constantes.ID_PUESTO_CAE_INCAP.equals(idPuestoSustituido)) {
                    hlpCuentasSustituciones.eliminarCuentaSustitucion(aspiranteSustituido,
                            request.getUsuario());
                } else {
                    hlpCuentasSustituciones.modificarCuentaSustitucion(aspiranteSustituido,
                            request.getUsuario());
                }
            } else if (Constantes.ID_PUESTO_SE_TMP.equals(idPuestoSustituido)
                    && Constantes.ID_PUESTO_CAE.equals(idPuestoOriginalSustituido)) {
                hlpCuentasSustituciones.modificarCuentaSustitucion(aspiranteSustituido,
                        request.getUsuario());
            }
            if (aspiranteSustituto.getId() != null) {
                if (aspiranteSustituto.getIdPuesto().equals(3)) {
                    aspiranteSustituto.setDeclinoCargo(null);
                }
                Integer puestoOriginalSustituto = aspiranteSustituto.getIdPuesto();
                aspiranteSustituto.setIdPuesto(idPuestoSustituto);
                aspiranteSustituto.setExisteSustitucion("S");
                if (Constantes.ID_PUESTO_SE.equals(idPuestoOriginalSustituido)
                        || Constantes.ID_PUESTO_SE_TMP.equals(idPuestoOriginalSustituido)) {
                    aspiranteSustituto.setIdZonaResponsabilidad1e(
                            aspiranteSustituido.getIdZonaResponsabilidad1e());
                    aspiranteSustituto.setIdZonaResponsabilidad2e(
                            aspiranteSustituido.getIdZonaResponsabilidad2e());
                    aspiranteSustituto.setIdAreaResponsabilidad1e(null);
                    aspiranteSustituto.setIdAreaResponsabilidad2e(null);

                } else if (Constantes.ID_PUESTO_CAE.equals(idPuestoOriginalSustituido)
                        || Constantes.ID_PUESTO_CAE_TMP.equals(idPuestoOriginalSustituido)) {
                    if (idAre1eOriginalSustituido == null && idAre2eOriginalSustituido == null) {
                        aspiranteSustituto.setIdZonaResponsabilidad1e(null);
                        aspiranteSustituto.setIdZonaResponsabilidad2e(null);
                        aspiranteSustituto.setIdAreaResponsabilidad1e(
                                aspiranteSustituido.getIdAreaResponsabilidad1e());
                        aspiranteSustituto.setIdAreaResponsabilidad2e(
                                aspiranteSustituido.getIdAreaResponsabilidad2e());
                    } else {
                        aspiranteSustituto.setIdZonaResponsabilidad1e(null);
                        aspiranteSustituto.setIdZonaResponsabilidad2e(null);
                        aspiranteSustituto
                                .setIdAreaResponsabilidad1e(idAre1eOriginalSustituido);
                        aspiranteSustituto
                                .setIdAreaResponsabilidad2e(idAre2eOriginalSustituido);
                    }
                }

                if (puestoOriginalSustituto == 3) {
                    aspiranteSustituto.setCorreoCtaCreada(request.getCuentaSuplenteCapacitador());
                }
                guardaAspirante(aspiranteSustituto, request.getUsuario(), request.getIpUsuario());

                if (puestoOriginalSustituto == 3) {
                    hlpCuentasSustituciones.crearCuentaSustitucion(aspiranteSustituto,
                            request.getUsuario());
                } else {
                    hlpCuentasSustituciones.modificarCuentaSustitucion(aspiranteSustituto,
                            request.getUsuario());
                }
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void guardaSustitucion(DTORequestSustIncapacidad request, DTOAspirantes aspiranteSustituido,
            DTOAspirantes aspiranteSustituto, String fechaBaja, String fechaAlta, String idRelacion) {

        Integer etapa = 1;
        try {
            etapa = asCommons.obtieneEtapaActual(request.getIdDetalleProceso());
        } catch (Exception e) {

        }

        DTOSustitucionesSupycapId sustitucionesSupycapId = new DTOSustitucionesSupycapId();
        sustitucionesSupycapId.setIdDetalleProceso(request.getIdDetalleProceso());
        sustitucionesSupycapId.setIdParticipacion(request.getIdParticipacion());
        sustitucionesSupycapId.setIdSustitucion(repoJPASustitucionesSupycap
                .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

        DTOSustitucionesSupycap sustitucionesSupycap = new DTOSustitucionesSupycap();
        sustitucionesSupycap.setId(sustitucionesSupycapId);
        sustitucionesSupycap.setIdProcesoElectoral(request.getIdProceso());
        sustitucionesSupycap.setIdRelacionSustituciones(idRelacion);

        sustitucionesSupycap.setIdAspiranteSutituido(aspiranteSustituido.getIdAspirante());
        sustitucionesSupycap.setIdPuestoSustituido(aspiranteSustituido.getIdPuesto());
        sustitucionesSupycap.setUidCuentaSustituido(aspiranteSustituido.getUidCuenta());
        sustitucionesSupycap.setCorreoCtaCreadaSustituido(aspiranteSustituido.getCorreoCtaCreada());
        sustitucionesSupycap.setCorreoCtaNotifSustituido(aspiranteSustituido.getCorreoCtaNotificacion());
        sustitucionesSupycap.setIdAreaResponsabilidad1e(aspiranteSustituido.getIdAreaResponsabilidad1e());
        sustitucionesSupycap.setIdAZonaResponsabilidad1e(aspiranteSustituido.getIdZonaResponsabilidad1e());
        sustitucionesSupycap.setIdAreaResponsabilidad2e(aspiranteSustituido.getIdAreaResponsabilidad2e());
        sustitucionesSupycap.setIdZonaResponsabilidad2e(aspiranteSustituido.getIdZonaResponsabilidad2e());

        sustitucionesSupycap.setIdAspiranteSutituto(
                aspiranteSustituto.getId() != null ? aspiranteSustituto.getId().getIdAspirante()
                : null);
        sustitucionesSupycap.setIdPuestoSustituto(
                aspiranteSustituto.getIdPuesto() != null ? aspiranteSustituto.getIdPuesto() : null);
        sustitucionesSupycap.setUidCuentaSustituto(
                aspiranteSustituto.getUidCuenta() != null ? aspiranteSustituto.getUidCuenta() : null);
        sustitucionesSupycap.setCorreoCtaCreadaSustituto(aspiranteSustituto.getCorreoCtaCreada() != null
                ? aspiranteSustituto.getCorreoCtaCreada()
                : null);
        sustitucionesSupycap.setCorreoCtaNotifSustituto(aspiranteSustituto.getCorreoCtaNotificacion() != null
                ? aspiranteSustituto.getCorreoCtaNotificacion()
                : null);
        sustitucionesSupycap.setDeclinoCargo(
                aspiranteSustituto.getDeclinoCargo() != null ? aspiranteSustituto.getDeclinoCargo()
                : null);

        sustitucionesSupycap.setIdCausaVacante(request.getIdCausaVacante());
        sustitucionesSupycap.setTipoCausaVacante(request.getTipoCausaVacante());
        sustitucionesSupycap.setObservaciones(request.getObservacionesBajaSustituido());

        sustitucionesSupycap.setEtapa(etapa);
        sustitucionesSupycap.setFechaBaja(ApplicationUtil.convertStringADate(fechaBaja));
        sustitucionesSupycap.setFechaAlta(ApplicationUtil.convertStringADate(fechaAlta));
        sustitucionesSupycap.setFechaSustitucion(new Date());
        sustitucionesSupycap.setFechaMovimiento(new Date());
        sustitucionesSupycap.setFechaHora(new Date());

        sustitucionesSupycap.setIpUsuario(request.getIpUsuario());
        sustitucionesSupycap.setUsuario(request.getUsuario());

        repoJPASustitucionesSupycap.saveAndFlush(sustitucionesSupycap);
    }

    private void mapeaGuardaSustitucion(VOSustitucionesSupycap sustitucion, DTOAspirantes sustituto,
            String fechaAlta, Date fechaMovimiento, String usuario, String ipUsuario) {
        sustitucion.setIdAspiranteSutituto(
                sustituto.getId() != null ? sustituto.getId().getIdAspirante() : null);
        sustitucion.setIdPuestoSustituto(sustituto.getIdPuesto() != null ? sustituto.getIdPuesto() : null);
        sustitucion.setCorreoCtaCreadaSustituto(
                sustituto.getCorreoCtaCreada() != null ? sustituto.getCorreoCtaCreada() : null);
        sustitucion.setCorreoCtaNotifSustituto(
                sustituto.getCorreoCtaNotificacion() != null ? sustituto.getCorreoCtaNotificacion()
                : null);
        sustitucion.setUidCuentaSustituto(sustituto.getUidCuenta() != null ? sustituto.getUidCuenta() : null);
        sustitucion.setDeclinoCargo(sustituto.getDeclinoCargo() != null ? sustituto.getDeclinoCargo() : null);
        sustitucion.setFechaAlta(ApplicationUtil.convertStringADate(fechaAlta));
        sustitucion.setIpUsuario(ipUsuario);
        sustitucion.setUsuario(usuario);
        sustitucion.setFechaMovimiento(fechaMovimiento);
        sustitucion.setFechaHora(fechaMovimiento);

        repoJPASustitucionesSupycap.saveAndFlush(new DTOSustitucionesSupycap(sustitucion));
    }

    private void guardaAspirante(DTOAspirantes aspirante, String usuario, String ipUsuario) {
        aspirante.setUsuario(usuario);
        aspirante.setIpUsuario(ipUsuario);
        aspirante.setFechaHora(new Date());
        aspirante.setIdSistemaActualiza(Constantes.ID_SISTEMA);
        repoJPAAspirantes.saveAndFlush(aspirante);
    }

    private List<VOSustitucionesSupycap> obtieneSustituciones(Integer idProceso, Integer idDetalle,
            Integer idParticipacion, Integer idSustitucion, Integer idSustituido) {
        List<VOSustitucionesSupycap> sustituciones = new ArrayList<>();
        if (idSustitucion != null && idSustitucion != 0) {
            sustituciones.add(repoJPASustitucionesSupycap.obtenerInfoSustitucionById(idProceso, idDetalle,
                    idParticipacion, idSustitucion));
        } else {
            sustituciones = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(idProceso,
                    idDetalle, idParticipacion, idSustituido, null, null);
        }
        return sustituciones;
    }

    private VOSustitucionesSupycap obtieneSustitucionCompleta(List<VOSustitucionesSupycap> sustituciones) {
        VOSustitucionesSupycap sustitucion = sustituciones.get(0);
        sustitucion.setFechaBajaString(ApplicationUtil.convertDateAString(sustitucion.getFechaBaja()));
        sustitucion.setCausaString(repoJPACCausasVacante.findById_IdCausaVacanteAndIdTipoCausaVacante(
                sustitucion.getIdCausaVacante(), sustitucion.getTipoCausaVacante()).getDescripcion());
        return sustitucion;
    }

    private void actualizaFechasObservacionesSustitucion(VOSustitucionesSupycap sustitucion, String fechaAlta,
            String fechaBaja, String observaciones) {

        Date fechaAltaDate = ApplicationUtil.convertStringADate(fechaAlta);
        if (fechaAltaDate != null) {
            sustitucion.setFechaAlta(fechaAltaDate);
        }

        Date fechaBajaDate = ApplicationUtil.convertStringADate(fechaBaja);
        if (fechaBajaDate != null) {
            sustitucion.setFechaBaja(fechaBajaDate);
        }

        if (observaciones != null) {
            sustitucion.setObservaciones(observaciones);
        }

        repoJPASustitucionesSupycap.saveAndFlush(new DTOSustitucionesSupycap(sustitucion));
    }

}
