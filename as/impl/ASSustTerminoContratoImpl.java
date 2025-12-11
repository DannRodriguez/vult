package mx.ine.sustseycae.as.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASSustTerminoContrato;
import mx.ine.sustseycae.bsd.impl.BSDCommonsImpl;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseSustiTerminoContrato;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPACCausasVacante;
import mx.ine.sustseycae.repositories.RepoJPASustitucionesSupycap;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Service("asSustTerminoContratoImpl")
@Scope("prototype")
public class ASSustTerminoContratoImpl implements ASSustTerminoContrato {

    @Autowired
    private RepoJPASustitucionesSupycap repoJPASustitucionesSupycap;

    @Autowired
    private RepoJPACCausasVacante repoJPACCausasVacante;

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Autowired
    private ASCommonsImpl asCommons;

    @Autowired
    private BSDCommonsImpl bsdCommons;

    @Override
    public ModelResponseSustiTerminoContrato obtenerSustitutosSustTerminoCaptura(
            ModelRequestSustTerminoContrato request) {
        ModelResponseSustiTerminoContrato response = new ModelResponseSustiTerminoContrato();

        List<VOSustitucionesSupycap> voSusti = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(
                request.getIdProceso(), request.getIdDetalleProceso(), request.getIdParticipacion(), null,
                request.getIdAspirante(), null);

        if (voSusti.isEmpty()) {
            return response;
        }

        VOSustitucionesSupycap primeraSustitucion = obtieneSustitucionCompleta(voSusti);

        if (primeraSustitucion.getIdPuestoSustituido().equals(1)) {
            response.setSustitucionSE(primeraSustitucion);

            response.setSeIncapacitado(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituido()));

            response.setSeTemporal(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto()));

            List<VOSustitucionesSupycap> voSusti2 = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(
                    primeraSustitucion.getIdProcesoElectoral(), primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto(), null,
                    primeraSustitucion.getIdRelacionSustituciones());

            if (!voSusti2.isEmpty()) {
                VOSustitucionesSupycap segundaSustitucion = obtieneSustitucionCompleta(voSusti2);

                response.setSustitucionCAE(segundaSustitucion);

                response.setCaeTemporal(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituto()));
            }

        } else if (primeraSustitucion.getIdPuestoSustituido().equals(2)) {
            response.setSustitucionCAE(primeraSustitucion);

            List<VOSustitucionesSupycap> voSusti2 = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(
                    primeraSustitucion.getIdProcesoElectoral(), primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), null, primeraSustitucion.getIdAspiranteSutituido(),
                    primeraSustitucion.getIdRelacionSustituciones());

            if (!voSusti2.isEmpty()) {
                VOSustitucionesSupycap segundaSustitucion = obtieneSustitucionCompleta(voSusti2);

                response.setSustitucionSE(segundaSustitucion);

                response.setSeIncapacitado(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituido()));

                response.setSeTemporal(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituto()));

            } else {
                response.setCaeIncapacitado(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                        primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituido()));
            }

            response.setCaeTemporal(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto()));
        }

        return response;
    }

    @Override
    public ModelResponseSustiTerminoContrato obtenerSustitutosSustTerminoConsulta(
            ModelRequestSustTerminoContrato request) {
        ModelResponseSustiTerminoContrato response = new ModelResponseSustiTerminoContrato();

        List<VOSustitucionesSupycap> voSusti = new ArrayList<>();

        if (request.getIdSustitucion() != null && request.getIdSustitucion() != 0) {
            voSusti.add(repoJPASustitucionesSupycap.obtenerInfoSustitucionById(request.getIdProceso(),
                    request.getIdDetalleProceso(), request.getIdParticipacion(), request.getIdSustitucion()));
        } else {
            voSusti = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(request.getIdProceso(),
                    request.getIdDetalleProceso(), request.getIdParticipacion(), request.getIdAspirante(), null, null);
        }

        if (voSusti.isEmpty()) {
            return response;
        }

        VOSustitucionesSupycap primeraSustitucion = obtieneSustitucionCompleta(voSusti);

        if (primeraSustitucion.getIdPuestoSustituido().equals(10)) {
            response.setSustitucionSE(primeraSustitucion);

            response.setSeIncapacitado(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto()));

            response.setSeTemporal(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituido()));

            List<VOSustitucionesSupycap> voSusti2 = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(
                    primeraSustitucion.getIdProcesoElectoral(), primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), null, primeraSustitucion.getIdAspiranteSutituido(),
                    primeraSustitucion.getIdRelacionSustituciones());

            if (!voSusti2.isEmpty()) {
                VOSustitucionesSupycap segundaSustitucion = obtieneSustitucionCompleta(voSusti2);

                response.setSustitucionCAE(segundaSustitucion);

                response.setCaeTemporal(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituido()));
            }

        } else if (primeraSustitucion.getIdPuestoSustituido().equals(11)) {
            response.setSustitucionCAE(primeraSustitucion);

            List<VOSustitucionesSupycap> voSusti2 = repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(
                    primeraSustitucion.getIdProcesoElectoral(), primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto(), null,
                    primeraSustitucion.getIdRelacionSustituciones());

            if (!voSusti2.isEmpty()) {
                VOSustitucionesSupycap segundaSustitucion = obtieneSustitucionCompleta(voSusti2);

                response.setSustitucionSE(segundaSustitucion);

                response.setSeIncapacitado(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituto()));

                response.setSeTemporal(asCommons.obtenerInfoSustituido(segundaSustitucion.getIdDetalleProceso(),
                        segundaSustitucion.getIdParticipacion(), segundaSustitucion.getIdAspiranteSutituido()));

            } else {
                response.setCaeIncapacitado(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                        primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituto()));
            }

            response.setCaeTemporal(asCommons.obtenerInfoSustituido(primeraSustitucion.getIdDetalleProceso(),
                    primeraSustitucion.getIdParticipacion(), primeraSustitucion.getIdAspiranteSutituido()));
        }

        return response;
    }

    @Override
    public boolean guardarSustitucionTerminoContratoCaptura(ModelRequestSustituciones request) {
        String idRelacion = UUID.randomUUID().toString();

        if (request.getSustitucionSE() != null) {

            DTOAspirantes seIncapacitado = repoJPAAspirantes
                    .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                            request.getSustitucionSE().getIdProcesoElectoral(),
                            request.getSustitucionSE().getIdDetalleProceso(),
                            request.getSustitucionSE().getIdParticipacion(),
                            request.getSustitucionSE().getIdAspiranteSutituido());

            DTOAspirantes seTemporal = repoJPAAspirantes
                    .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                            request.getSustitucionSE().getIdProcesoElectoral(),
                            request.getSustitucionSE().getIdDetalleProceso(),
                            request.getSustitucionSE().getIdParticipacion(),
                            request.getSustitucionSE().getIdAspiranteSutituto());

            DTOSustitucionesSupycap primeraSustitucion = formarSustitucion(request.getSustitucionSE(), seIncapacitado,
                    seTemporal, idRelacion, request.getFechaBaja(), request.getObservaciones(), request.getUsuario());

            repoJPASustitucionesSupycap.saveAndFlush(primeraSustitucion);

            if (request.getSustitucionCAE() != null) {
                DTOAspirantes caeTemporal = repoJPAAspirantes
                        .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                request.getSustitucionCAE().getIdProcesoElectoral(),
                                request.getSustitucionCAE().getIdDetalleProceso(),
                                request.getSustitucionCAE().getIdParticipacion(),
                                request.getSustitucionCAE().getIdAspiranteSutituto());

                DTOSustitucionesSupycap segundaSustitucion = formarSustitucion(request.getSustitucionCAE(), seTemporal,
                        caeTemporal, idRelacion, request.getFechaBaja(), request.getObservaciones(), request.getUsuario());

                repoJPASustitucionesSupycap.saveAndFlush(segundaSustitucion);

                caeTemporal.setIdPuesto(request.getSustitucionCAE().getIdPuestoSustituto());
                limpiarAREyZORE(caeTemporal);
                caeTemporal.setEstatusListaReserva(0);
                repoJPAAspirantes.saveAndFlush(caeTemporal);

                seTemporal.setIdAreaResponsabilidad1e(request.getSustitucionCAE().getIdAreaResponsabilidad1e());
                seTemporal.setIdZonaResponsabilidad1e(request.getSustitucionCAE().getIdAZonaResponsabilidad1e());
                seTemporal.setIdAreaResponsabilidad2e(request.getSustitucionCAE().getIdAreaResponsabilidad2e());
                seTemporal.setIdZonaResponsabilidad2e(request.getSustitucionCAE().getIdZonaResponsabilidad2e());

            } else {
                limpiarAREyZORE(seTemporal);
            }

            seIncapacitado.setIdAreaResponsabilidad1e(request.getSustitucionSE().getIdAreaResponsabilidad1e());
            seIncapacitado.setIdZonaResponsabilidad1e(request.getSustitucionSE().getIdAZonaResponsabilidad1e());
            seIncapacitado.setIdAreaResponsabilidad2e(request.getSustitucionSE().getIdAreaResponsabilidad2e());
            seIncapacitado.setIdZonaResponsabilidad2e(request.getSustitucionSE().getIdZonaResponsabilidad2e());
            seIncapacitado.setIdPuesto(request.getSustitucionSE().getIdPuestoSustituido());
            repoJPAAspirantes.saveAndFlush(seIncapacitado);

            seTemporal.setIdPuesto(request.getSustitucionSE().getIdPuestoSustituto());
            seTemporal.setDeclinoCargo(request.getSustitucionSE().getDeclinoCargo());
            repoJPAAspirantes.saveAndFlush(seTemporal);

            almacenarFotos(request, false);
            return true;

        } else if (request.getCaeIncapacitado() != null) {

            DTOAspirantes caeIncapacitado = repoJPAAspirantes
                    .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                            request.getSustitucionCAE().getIdProcesoElectoral(),
                            request.getSustitucionCAE().getIdDetalleProceso(),
                            request.getSustitucionCAE().getIdParticipacion(),
                            request.getSustitucionCAE().getIdAspiranteSutituido());

            DTOAspirantes caeTemporal = repoJPAAspirantes
                    .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                            request.getSustitucionCAE().getIdProcesoElectoral(),
                            request.getSustitucionCAE().getIdDetalleProceso(),
                            request.getSustitucionCAE().getIdParticipacion(),
                            request.getSustitucionCAE().getIdAspiranteSutituto());

            DTOSustitucionesSupycap primeraSustitucion = formarSustitucion(request.getSustitucionCAE(), caeIncapacitado,
                    caeTemporal, idRelacion, request.getFechaBaja(), request.getObservaciones(), request.getUsuario());

            caeIncapacitado.setIdPuesto(request.getSustitucionCAE().getIdPuestoSustituido());
            caeIncapacitado.setIdAreaResponsabilidad1e(request.getSustitucionCAE().getIdAreaResponsabilidad1e());
            caeIncapacitado.setIdZonaResponsabilidad1e(request.getSustitucionCAE().getIdAZonaResponsabilidad1e());
            caeIncapacitado.setIdAreaResponsabilidad2e(request.getSustitucionCAE().getIdAreaResponsabilidad2e());
            caeIncapacitado.setIdZonaResponsabilidad2e(request.getSustitucionCAE().getIdZonaResponsabilidad2e());
            repoJPAAspirantes.saveAndFlush(caeIncapacitado);

            caeTemporal.setIdPuesto(request.getSustitucionCAE().getIdPuestoSustituto());
            caeTemporal.setDeclinoCargo(request.getSustitucionCAE().getDeclinoCargo());
            limpiarAREyZORE(caeTemporal);
            caeTemporal.setEstatusListaReserva(0);
            repoJPAAspirantes.saveAndFlush(caeTemporal);

            repoJPASustitucionesSupycap.saveAndFlush(primeraSustitucion);

            almacenarFotos(request, true);

            return true;
        }
        return false;
    }

    @Override
    public boolean guardarSustitucionTerminoContratoModifica(ModelRequestSustituciones request) {

        if (request.getSeTemporal() != null && request.getSeTemporal().getIdPuesto() != null
                && !request.getSeTemporal().getIdPuesto().equals(Constantes.ID_PUESTO_RECONTRATACION_SE)
                && (request.getSeTemporal().getDeclinoCargo() == null
                || !request.getSeTemporal().getDeclinoCargo().equals(Constantes.DECLINO_CARGO))
                && !request.getSeTemporal().getIdPuesto().equals(Constantes.ID_PUESTO_LISTA_RESERVA)
                && !request.getSeTemporal().getIdPuesto().equals(Constantes.ID_PUESTO_RESCISION_SE)
                && !request.getSeTemporal().getIdPuesto().equals(Constantes.ID_PUESTO_RESCISION_CAE)
                && !request.getSeTemporal().getIdPuesto().equals(Constantes.ID_PUESTO_PROMOCION)
                && request.getSustitucionCAE() == null) {
            return false;
        }

        if (request.getSustitucionSE() != null) {
            actualizaFechaObservacionesSustitucion(request.getSustitucionSE().getIdDetalleProceso(),
                    request.getSustitucionSE().getIdParticipacion(), request.getSustitucionSE().getIdSustitucion(),
                    request.getFechaBaja(), request.getObservaciones());
        }

        if (request.getSustitucionCAE() != null) {
            actualizaFechaObservacionesSustitucion(request.getSustitucionCAE().getIdDetalleProceso(),
                    request.getSustitucionCAE().getIdParticipacion(), request.getSustitucionCAE().getIdSustitucion(),
                    request.getFechaBaja(), request.getObservaciones());
        }

        almacenarFotos(request, (request.getSustitucionCAE() != null));

        return true;

    }

    private VOSustitucionesSupycap obtieneSustitucionCompleta(List<VOSustitucionesSupycap> sustituciones) {
        VOSustitucionesSupycap sustitucion = sustituciones.get(0);
        sustitucion.setFechaBajaString(ApplicationUtil.convertDateAString(sustitucion.getFechaBaja()));
        sustitucion.setCausaString(repoJPACCausasVacante.findById_IdCausaVacanteAndIdTipoCausaVacante(
                sustitucion.getIdCausaVacante(), sustitucion.getTipoCausaVacante()).getDescripcion());
        return sustitucion;
    }

    private void actualizaFechaObservacionesSustitucion(Integer idDetalle, Integer idParticipacion,
            Integer idSustitucion, String fechaBaja, String observaciones) {
        DTOSustitucionesSupycap sustitucion = repoJPASustitucionesSupycap
                .findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucion(idDetalle, idParticipacion,
                        idSustitucion);

        sustitucion.setFechaBaja(ApplicationUtil.convertStringADate(fechaBaja));
        sustitucion.setObservaciones(observaciones);

        repoJPASustitucionesSupycap.saveAndFlush(sustitucion);
    }

    private void almacenarFotos(ModelRequestSustituciones request, boolean isCAE) {
        Integer idProceso;
        Integer idDetalle;
        Integer idParticipacion;

        if (isCAE) {
            idProceso = request.getSustitucionCAE().getIdProcesoElectoral();
            idDetalle = request.getSustitucionCAE().getIdDetalleProceso();
            idParticipacion = request.getSustitucionCAE().getIdParticipacion();
        } else {
            idProceso = request.getSustitucionSE().getIdProcesoElectoral();
            idDetalle = request.getSustitucionSE().getIdDetalleProceso();
            idParticipacion = request.getSustitucionSE().getIdParticipacion();
        }

        if (request.getImagenB641() != null) {
            bsdCommons.almacenarFotoAspirante(request.getImagenB641(), request.getExtensionImagen1(), idProceso,
                    idDetalle, idParticipacion, request.getIdAspiranteImagen1());
        }

        if (request.getImagenB642() != null) {
            bsdCommons.almacenarFotoAspirante(request.getImagenB642(), request.getExtensionImagen2(), idProceso,
                    idDetalle, idParticipacion, request.getIdAspiranteImagen2());
        }

        if (request.getImagenB643() != null) {
            bsdCommons.almacenarFotoAspirante(request.getImagenB643(), request.getExtensionImagen3(), idProceso,
                    idDetalle, idParticipacion, request.getIdAspiranteImagen3());
        }
    }

    private DTOSustitucionesSupycap formarSustitucion(VOSustitucionesSupycap sustitucion,
            DTOAspirantes aspiranteSustituido, DTOAspirantes aspiranteSustituto, String idRelacion, String fechaBaja,
            String observaciones, String usuario) {

        DTOSustitucionesSupycapId id = new DTOSustitucionesSupycapId();
        id.setIdDetalleProceso(sustitucion.getIdDetalleProceso());
        id.setIdParticipacion(sustitucion.getIdParticipacion());
        id.setIdSustitucion(repoJPASustitucionesSupycap
                .encontrarMaxId(sustitucion.getIdDetalleProceso(), sustitucion.getIdParticipacion()).orElse(1));

        DTOSustitucionesSupycap sust = new DTOSustitucionesSupycap();
        sust.setId(id);

        sust.setIdProcesoElectoral(sustitucion.getIdProcesoElectoral());
        sust.setIdRelacionSustituciones(idRelacion);

        sust.setIdAspiranteSutituido(aspiranteSustituto.getId().getIdAspirante());
        sust.setIdPuestoSustituido(aspiranteSustituto.getIdPuesto());
        sust.setCorreoCtaCreadaSustituido(aspiranteSustituto.getCorreoCtaCreada());
        sust.setCorreoCtaNotifSustituido(aspiranteSustituto.getCorreoCtaNotificacion());
        sust.setUidCuentaSustituido(aspiranteSustituto.getUidCuenta());
        sust.setDeclinoCargo(aspiranteSustituto.getDeclinoCargo());

        sust.setIdAspiranteSutituto(aspiranteSustituido.getId().getIdAspirante());
        sust.setIdPuestoSustituto(aspiranteSustituido.getIdPuesto());
        sust.setCorreoCtaCreadaSustituto(aspiranteSustituido.getCorreoCtaCreada());
        sust.setCorreoCtaNotifSustituto(aspiranteSustituido.getCorreoCtaNotificacion());
        sust.setUidCuentaSustituto(aspiranteSustituido.getUidCuenta());

        sust.setIdAreaResponsabilidad1e(sustitucion.getIdAreaResponsabilidad1e());
        sust.setIdAZonaResponsabilidad1e(sustitucion.getIdAZonaResponsabilidad1e());
        sust.setIdAreaResponsabilidad2e(sustitucion.getIdAreaResponsabilidad2e());
        sust.setIdZonaResponsabilidad2e(sustitucion.getIdZonaResponsabilidad2e());

        sust.setIdCausaVacante(Constantes.SUST_TERM_CONTRATO);
        sust.setTipoCausaVacante(Constantes.SUST_SEyCAE);
        sust.setEtapa(asCommons.obtieneEtapaActual(sustitucion.getIdDetalleProceso()));
        sust.setFechaBaja(ApplicationUtil.convertStringADate(fechaBaja));
        sust.setFechaAlta(null);
        sust.setFechaSustitucion(new Date());
        sust.setObservaciones(observaciones);

        sust.setIpUsuario(sustitucion.getIpUsuario());
        sust.setUsuario(usuario);
        sust.setFechaMovimiento(new Date());
        sust.setFechaHora(new Date());

        return sust;
    }

    private void limpiarAREyZORE(DTOAspirantes dtoAspirantes) {
        if (dtoAspirantes != null) {
            dtoAspirantes.setIdAreaResponsabilidad1e(null);
            dtoAspirantes.setIdZonaResponsabilidad1e(null);
            dtoAspirantes.setIdAreaResponsabilidad2e(null);
            dtoAspirantes.setIdZonaResponsabilidad2e(null);
        }
    }

}
