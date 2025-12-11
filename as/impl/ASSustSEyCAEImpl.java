package mx.ine.sustseycae.as.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASSustSEyCAEInterface;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCFechas;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPACFechas;
import mx.ine.sustseycae.repositories.RepoJPASustitucionesSupycap;
import mx.ine.sustseycae.util.Constantes;

@Service("asSustSEyCAE")
@Scope("prototype")
public class ASSustSEyCAEImpl implements ASSustSEyCAEInterface {

    private static final boolean NO_ES_SUSTITUTO_DE_SE = false;
    private static final boolean ES_SUSTITUTO_DE_SE = true;

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Autowired
    private RepoJPASustitucionesSupycap repoJPASustitucionesSupycap;

    @Autowired
    private RepoJPACFechas repoJPACFechas;

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public List<DTOAspirantes> guardarSustitucionSE(DTOAspirantes funcionarioSESustituido,
            DTOAspirantes sustitutoSupervisor, DTOAspirantes sustitutoCapacitador, DTORequestSutSEyCAE request)
            throws Exception {

        List<DTOAspirantes> aspirantesCuentas = new ArrayList<>();
        DTOAspirantes aspiranteCrearCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteModificarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOSustitucionesSupycap sustitucionSE = new DTOSustitucionesSupycap();

        Integer etapa = obtieneEtapaActual(request.getIdDetalleProceso());
        cargarInfoSustituidoEnSustitucion(sustitucionSE, funcionarioSESustituido, request, NO_ES_SUSTITUTO_DE_SE, null);
        DTOAspirantes aspiranteEliminarCuenta = funcionarioSESustituido;

        if (sustitutoSupervisor != null && sustitutoSupervisor.getId() != null
                && sustitutoSupervisor.getId().getIdAspirante() != null) {
            cargarInfoSustitutoEnSustitucion(sustitucionSE, sustitutoSupervisor, request, ES_SUSTITUTO_DE_SE);
            asignarCorreo(sustitutoSupervisor, request.getCorreoSustitutoSupervisor());

            if (sustitutoSupervisor.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {

                DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();
                cargarInfoSustituidoEnSustitucion(sustitucionCAE, sustitutoSupervisor, request, ES_SUSTITUTO_DE_SE,
                        sustitucionSE.getIdRelacionSustituciones());

                if (sustitutoCapacitador != null && sustitutoCapacitador.getId() != null
                        && sustitutoCapacitador.getId().getIdAspirante() != null) {

                    cargarInfoSustitutoEnSustitucion(sustitucionCAE, sustitutoCapacitador, request, false);
                    cargarInfoSustituidoEnSustituto(sustitutoSupervisor, sustitutoCapacitador, request);
                    asignarCorreo(sustitutoCapacitador, request.getCorreoSustitutoCapacitador());
                    cargarInfoSustituidoEnSustituto(funcionarioSESustituido, sustitutoSupervisor, request);
                    generarInfoPuestoSustituido(funcionarioSESustituido, request);

                    aspiranteModificarCuenta = sustitutoSupervisor;
                    aspiranteCrearCuenta = sustitutoCapacitador;
                    funcionarioSESustituido.setFechaHora(new Date());
                    sustitutoSupervisor.setFechaHora(new Date());
                    sustitutoCapacitador.setFechaHora(new Date());
                    asignarEtapaFecha(sustitucionSE, etapa);
                    asignarEtapaFecha(sustitucionCAE, etapa);

                    Integer idSsutitucionBD = repoJPASustitucionesSupycap
                            .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1);
                    sustitucionSE.getId().setIdSustitucion(idSsutitucionBD);
                    sustitucionCAE.getId().setIdSustitucion(idSsutitucionBD + 1);

                    repoJPASustitucionesSupycap.save(sustitucionSE);
                    repoJPASustitucionesSupycap.save(sustitucionCAE);
                    repoJPAAspirantes.save(funcionarioSESustituido);
                    repoJPAAspirantes.save(sustitutoSupervisor);
                    repoJPAAspirantes.save(sustitutoCapacitador);

                } else {
                    cargarInfoSustituidoEnSustituto(funcionarioSESustituido, sustitutoSupervisor, request);
                    generarInfoPuestoSustituido(funcionarioSESustituido, request);
                    aspiranteModificarCuenta = sustitutoSupervisor;
                    funcionarioSESustituido.setFechaHora(new Date());
                    sustitutoSupervisor.setFechaHora(new Date());
                    asignarEtapaFecha(sustitucionSE, etapa);
                    asignarEtapaFecha(sustitucionCAE, etapa);

                    Integer idSsutitucionBD = repoJPASustitucionesSupycap
                            .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1);
                    sustitucionSE.getId().setIdSustitucion(idSsutitucionBD);
                    sustitucionCAE.getId().setIdSustitucion(idSsutitucionBD + 1);

                    repoJPASustitucionesSupycap.save(sustitucionSE);
                    repoJPASustitucionesSupycap.save(sustitucionCAE);
                    repoJPAAspirantes.save(funcionarioSESustituido);
                    repoJPAAspirantes.save(sustitutoSupervisor);
                }

            } else {
                cargarInfoSustituidoEnSustituto(funcionarioSESustituido, sustitutoSupervisor, request);
                generarInfoPuestoSustituido(funcionarioSESustituido, request);
                funcionarioSESustituido.setFechaHora(new Date());
                sustitutoSupervisor.setFechaHora(new Date());
                asignarEtapaFecha(sustitucionSE, etapa);

                if (sustitutoSupervisor.getIdPuesto().equals(Constantes.ID_PUESTO_CAE_PROMOCION)) {
                    aspiranteModificarCuenta = sustitutoSupervisor;
                } else {
                    aspiranteCrearCuenta = sustitutoSupervisor;
                }
                sustitucionSE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                        .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

                repoJPASustitucionesSupycap.save(sustitucionSE);
                repoJPAAspirantes.save(funcionarioSESustituido);
                repoJPAAspirantes.save(sustitutoSupervisor);

            }

        } else {
            generarInfoPuestoSustituido(funcionarioSESustituido, request);
            funcionarioSESustituido.setFechaHora(new Date());
            asignarEtapaFecha(sustitucionSE, etapa);
            sustitucionSE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                    .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

            repoJPASustitucionesSupycap.save(sustitucionSE);
            repoJPAAspirantes.save(funcionarioSESustituido);
        }

        aspirantesCuentas.add(aspiranteCrearCuenta);
        aspirantesCuentas.add(aspiranteModificarCuenta);
        aspirantesCuentas.add(aspiranteEliminarCuenta);
        return aspirantesCuentas;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public List<DTOAspirantes> guardarSustitucionPendienteSE(VOSustitucionesSupycap sustitucionPrevia,
            DTOAspirantes sustitutoSE, DTOAspirantes sustitutoCae, DTORequestSutSEyCAE request) throws Exception {

        List<DTOAspirantes> aspirantesCuentas = new ArrayList<>();
        DTOAspirantes aspiranteCrearCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteModificarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        Integer etapa = obtieneEtapaActual(request.getIdDetalleProceso());

        DTOSustitucionesSupycap sustitucionSE = obtenerSustitucionPendiente(sustitucionPrevia.getIdDetalleProceso(),
                sustitucionPrevia.getIdParticipacion(), sustitucionPrevia.getIdAspiranteSutituido());
        if (sustitucionSE == null
                || !sustitucionSE.getId().getIdSustitucion().equals(sustitucionPrevia.getIdSustitucion())
                || !sustitucionSE.getIdRelacionSustituciones().equals(sustitucionPrevia.getIdRelacionSustituciones())) {
            throw new Exception("ERROR ASSustSEyCAEImpl - guardarSustitucionPendienteSE - idDetalle: "
                    + sustitucionPrevia.getIdDetalleProceso() + ", idParticipacion"
                    + sustitucionPrevia.getIdParticipacion() + ", idAspirante: "
                    + sustitucionPrevia.getIdAspiranteSutituido());
        }

        cargarInfoSustitutoEnSustitucion(sustitucionSE, sustitutoSE, request, true);
        asignarCorreo(sustitutoSE, request.getCorreoSustitutoSupervisor());

        if (sustitutoSE.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
            DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();
            cargarInfoSustituidoEnSustitucion(sustitucionCAE, sustitutoSE, request, ES_SUSTITUTO_DE_SE,
                    sustitucionSE.getIdRelacionSustituciones());

            if (sustitutoCae != null && sustitutoCae.getId() != null && sustitutoCae.getId().getIdAspirante() != null) {

                cargarInfoSustitutoEnSustitucion(sustitucionCAE, sustitutoCae, request, false);
                cargarInfoSustituidoEnSustituto(sustitutoSE, sustitutoCae, request);
                asignarCorreo(sustitutoCae, request.getCorreoSustitutoCapacitador());
                cargarInfoSustituidoEnSustituto(sustitucionSE, sustitutoSE, request);
                aspiranteModificarCuenta = sustitutoSE;
                aspiranteCrearCuenta = sustitutoCae;
                sustitutoSE.setFechaHora(new Date());
                sustitutoCae.setFechaHora(new Date());
                asignarEtapaFecha(sustitucionSE, etapa);
                asignarEtapaFecha(sustitucionCAE, etapa);

                sustitucionCAE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                        .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

                repoJPASustitucionesSupycap.save(sustitucionSE);
                repoJPASustitucionesSupycap.save(sustitucionCAE);
                repoJPAAspirantes.save(sustitutoSE);
                repoJPAAspirantes.save(sustitutoCae);

            } else {
                cargarInfoSustituidoEnSustituto(sustitucionSE, sustitutoSE, request);
                aspiranteModificarCuenta = sustitutoSE;
                sustitutoSE.setFechaHora(new Date());
                asignarEtapaFecha(sustitucionSE, etapa);
                asignarEtapaFecha(sustitucionCAE, etapa);

                sustitucionCAE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                        .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

                repoJPASustitucionesSupycap.save(sustitucionSE);
                repoJPASustitucionesSupycap.save(sustitucionCAE);
                repoJPAAspirantes.save(sustitutoSE);
            }

        } else {
            cargarInfoSustituidoEnSustituto(sustitucionSE, sustitutoSE, request);
            sustitutoSE.setFechaHora(new Date());
            asignarEtapaFecha(sustitucionSE, etapa);
            if (sustitutoSE.getIdPuesto().equals(Constantes.ID_PUESTO_CAE_PROMOCION)) {
                aspiranteModificarCuenta = sustitutoSE;
            } else {
                aspiranteCrearCuenta = sustitutoSE;
            }

            repoJPASustitucionesSupycap.save(sustitucionSE);
            repoJPAAspirantes.save(sustitutoSE);
        }
        aspirantesCuentas.add(aspiranteCrearCuenta);
        aspirantesCuentas.add(aspiranteModificarCuenta);
        aspirantesCuentas.add(null);
        return aspirantesCuentas;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public List<DTOAspirantes> guardarSustitucionCAE(DTOAspirantes funcionarioCAESustituido,
            DTOAspirantes sustitutoCapacitador, DTORequestSutSEyCAE request) throws Exception {
        List<DTOAspirantes> aspirantesCuentas = new ArrayList<>();
        DTOAspirantes aspiranteCrearCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteModificarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteEliminarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();

        Integer etapa = obtieneEtapaActual(request.getIdDetalleProceso());
        cargarInfoSustituidoEnSustitucion(sustitucionCAE, funcionarioCAESustituido, request, NO_ES_SUSTITUTO_DE_SE,
                null);

        if (sustitutoCapacitador != null && sustitutoCapacitador.getId() != null
                && sustitutoCapacitador.getId().getIdAspirante() != null) {

            this.cargarInfoSustitutoEnSustitucion(sustitucionCAE, sustitutoCapacitador, request, false);
            cargarInfoSustituidoEnSustituto(funcionarioCAESustituido, sustitutoCapacitador, request);
            asignarCorreo(sustitutoCapacitador, request.getCorreoSustitutoCapacitador());
            generarInfoPuestoSustituido(funcionarioCAESustituido, request);
            aspiranteCrearCuenta = sustitutoCapacitador;
            funcionarioCAESustituido.setFechaHora(new Date());
            sustitutoCapacitador.setFechaHora(new Date());
            asignarEtapaFecha(sustitucionCAE, etapa);
            if (!funcionarioCAESustituido.getIdPuesto().equals(Constantes.ID_PUESTO_PROMOCION)) {
                aspiranteEliminarCuenta = funcionarioCAESustituido;
            }

            sustitucionCAE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                    .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

            repoJPASustitucionesSupycap.save(sustitucionCAE);
            repoJPAAspirantes.save(funcionarioCAESustituido);
            repoJPAAspirantes.save(sustitutoCapacitador);

        } else {
            generarInfoPuestoSustituido(funcionarioCAESustituido, request);
            funcionarioCAESustituido.setFechaHora(new Date());
            asignarEtapaFecha(sustitucionCAE, etapa);
            if (!funcionarioCAESustituido.getIdPuesto().equals(Constantes.ID_PUESTO_PROMOCION)) {
                aspiranteEliminarCuenta = funcionarioCAESustituido;
            }

            sustitucionCAE.getId().setIdSustitucion(repoJPASustitucionesSupycap
                    .encontrarMaxId(request.getIdDetalleProceso(), request.getIdParticipacion()).orElse(1));

            repoJPASustitucionesSupycap.save(sustitucionCAE);
            repoJPAAspirantes.save(funcionarioCAESustituido);
        }

        aspirantesCuentas.add(aspiranteCrearCuenta);
        aspirantesCuentas.add(aspiranteModificarCuenta);
        aspirantesCuentas.add(aspiranteEliminarCuenta);
        return aspirantesCuentas;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public List<DTOAspirantes> guardarSustitucionPendienteCAE(VOSustitucionesSupycap sustitucionPrevia,
            DTOAspirantes sustitutoCapacitador, DTORequestSutSEyCAE request) throws Exception {

        List<DTOAspirantes> aspirantesCuentas = new ArrayList<>();
        DTOAspirantes aspiranteCrearCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteModificarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;
        DTOAspirantes aspiranteEliminarCuenta = Constantes.ASPIRANTE_SIN_INFORMACION;

        Integer etapa = obtieneEtapaActual(request.getIdDetalleProceso());

        DTOSustitucionesSupycap sustitucionCAE = obtenerSustitucionPendiente(sustitucionPrevia.getIdDetalleProceso(),
                sustitucionPrevia.getIdParticipacion(), sustitucionPrevia.getIdAspiranteSutituido());
        if (sustitucionCAE == null
                || !sustitucionCAE.getId().getIdSustitucion().equals(sustitucionPrevia.getIdSustitucion())
                || !sustitucionCAE.getIdRelacionSustituciones()
                        .equals(sustitucionPrevia.getIdRelacionSustituciones())) {
            throw new Exception("ERROR ASSustSEyCAEImpl - guardarSustitucionPendienteCAE - : idDetalle: "
                    + sustitucionPrevia.getIdDetalleProceso() + ", idParticipacion"
                    + sustitucionPrevia.getIdParticipacion() + ", idAspirante: "
                    + sustitucionPrevia.getIdAspiranteSutituido());
        }

        this.cargarInfoSustitutoEnSustitucion(sustitucionCAE, sustitutoCapacitador, request, false);

        cargarInfoSustituidoEnSustituto(sustitucionCAE, sustitutoCapacitador, request);
        sustitutoCapacitador.setCorreoCtaCreada(request.getCorreoSustitutoCapacitador());
        aspiranteCrearCuenta = sustitutoCapacitador;

        sustitutoCapacitador.setFechaHora(new Date());
        asignarEtapaFecha(sustitucionCAE, etapa);

        repoJPASustitucionesSupycap.save(sustitucionCAE);
        repoJPAAspirantes.save(sustitutoCapacitador);

        aspirantesCuentas.add(aspiranteCrearCuenta);
        aspirantesCuentas.add(aspiranteModificarCuenta);
        aspirantesCuentas.add(aspiranteEliminarCuenta);
        return aspirantesCuentas;

    }

    @Override
    public DTOAspirantes obtenerAspirantePorPK(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante) {
        return repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);
    }

    @Override
    public List<VOSustitucionesSupycap> obtenerInfoSustitucionPorSustituido(Integer idProcesoElectoral,
            Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSustituido) {
        return repoJPASustitucionesSupycap.obtenerInfoSustitucionPorFiltro(idProcesoElectoral, idDetalleProceso,
                idParticipacion, idAspiranteSustituido, null, null);
    }

    private DTOSustitucionesSupycap cargarInfoSustituidoEnSustitucion(DTOSustitucionesSupycap sustitucion,
            DTOAspirantes sustituido, DTORequestSutSEyCAE request, boolean isCAESustitutoDeSE,
            String idRelacionSustitucion) throws Exception {
        if (sustitucion != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            DTOSustitucionesSupycapId id = new DTOSustitucionesSupycapId();
            id.setIdDetalleProceso(sustituido.getIdDetalleProceso());
            id.setIdParticipacion(sustituido.getIdParticipacion());

            sustitucion.setIdProcesoElectoral(sustituido.getIdProcesoElectoral());
            sustitucion.setId(id);
            sustitucion.setIdAspiranteSutituido(sustituido.getIdAspirante());
            sustitucion.setIdPuestoSustituido(sustituido.getIdPuesto());
            sustitucion.setCorreoCtaCreadaSustituido(sustituido.getCorreoCtaCreada());
            sustitucion.setCorreoCtaNotifSustituido(sustituido.getCorreoCtaNotificacion());
            sustitucion.setIdAZonaResponsabilidad1e(sustituido.getIdZonaResponsabilidad1e());
            sustitucion.setIdZonaResponsabilidad2e(sustituido.getIdZonaResponsabilidad2e());
            sustitucion.setIdAreaResponsabilidad1e(sustituido.getIdAreaResponsabilidad1e());
            sustitucion.setIdAreaResponsabilidad2e(sustituido.getIdAreaResponsabilidad2e());
            sustitucion.setUidCuentaSustituido(sustituido.getUidCuenta());
            sustitucion.setFechaBaja(!isCAESustitutoDeSE ? dateFormat.parse(request.getFechaBajaSustituido())
                    : dateFormat.parse(request.getDateSustitutoSupervisor()));

            sustitucion.setTipoCausaVacante(
                    !isCAESustitutoDeSE ? request.getTipoCausaVacante() : Constantes.TIPO_OTRAS_CAUSAS);
            sustitucion.setIdCausaVacante(
                    !isCAESustitutoDeSE ? request.getIdCausaVacante() : Constantes.ID_CAUSA_VACANTE_PROMOCION);
            sustitucion.setObservaciones(!isCAESustitutoDeSE
                    ? (request.getObservacionesSustituido() != null ? request.getObservacionesSustituido().trim()
                    : null)
                    : null);
            sustitucion.setIdRelacionSustituciones(
                    !isCAESustitutoDeSE ? UUID.randomUUID().toString() : idRelacionSustitucion);

            sustitucion.setFechaSustitucion(dateFormat.parse(dateFormat.format(new Date())));
            sustitucion.setFechaMovimiento(new Date());
            sustitucion.setIpUsuario(request.getIpUsario());
            sustitucion.setUsuario(request.getUsuario());
        }

        return sustitucion;
    }

    private DTOSustitucionesSupycap cargarInfoSustitutoEnSustitucion(DTOSustitucionesSupycap sustitucion,
            DTOAspirantes sustituto, DTORequestSutSEyCAE request, boolean isSustitutoSE) throws Exception {

        if (sustitucion != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            sustitucion.setIdAspiranteSutituto(sustituto.getIdAspirante());
            sustitucion.setIdPuestoSustituto(sustituto.getIdPuesto());
            sustitucion.setCorreoCtaCreadaSustituto(sustituto.getCorreoCtaCreada());
            sustitucion.setCorreoCtaNotifSustituto(sustituto.getCorreoCtaCreada());
            sustitucion.setUidCuentaSustituto(sustituto.getUidCuenta());
            sustitucion.setDeclinoCargo(sustituto.getDeclinoCargo());
            sustitucion.setFechaAlta(dateFormat.parse(
                    isSustitutoSE ? request.getDateSustitutoSupervisor() : request.getDateSustitutoCapacitador()));

            sustitucion.setFechaSustitucion(dateFormat.parse(dateFormat.format(new Date())));
            sustitucion.setFechaMovimiento(new Date());
            sustitucion.setIpUsuario(request.getIpUsario());
            sustitucion.setUsuario(request.getUsuario());
        }

        return sustitucion;
    }

    private DTOAspirantes cargarInfoSustituidoEnSustituto(Object objSustituido, DTOAspirantes sustituto,
            DTORequestSutSEyCAE request) {

        if (objSustituido != null && sustituto != null) {
            if (objSustituido instanceof DTOAspirantes sustituido) {
                sustituto.setIdPuesto(sustituido.getIdPuesto());
                sustituto.setIdZonaResponsabilidad1e(sustituido.getIdZonaResponsabilidad1e());
                sustituto.setIdZonaResponsabilidad2e(sustituido.getIdZonaResponsabilidad2e());
                sustituto.setIdAreaResponsabilidad1e(sustituido.getIdAreaResponsabilidad1e());
                sustituto.setIdAreaResponsabilidad2e(sustituido.getIdAreaResponsabilidad2e());

            } else if (objSustituido instanceof DTOSustitucionesSupycap sustitucion) {

                sustituto.setIdPuesto(sustitucion.getIdPuestoSustituido());
                sustituto.setIdZonaResponsabilidad1e(sustitucion.getIdAZonaResponsabilidad1e());
                sustituto.setIdZonaResponsabilidad2e(sustitucion.getIdZonaResponsabilidad2e());
                sustituto.setIdAreaResponsabilidad1e(sustitucion.getIdAreaResponsabilidad1e());
                sustituto.setIdAreaResponsabilidad2e(sustitucion.getIdAreaResponsabilidad2e());
            }

            sustituto.setExisteSustitucion("S");
            sustituto.setIdSistemaActualiza(Constantes.ID_SISTEMA);
            sustituto.setIpUsuario(request.getIpUsario());
            sustituto.setUsuario(request.getUsuario());
        }

        return sustituto;
    }

    private DTOAspirantes generarInfoPuestoSustituido(DTOAspirantes sustituido, DTORequestSutSEyCAE request) {

        if (sustituido != null) {

            if (request.getTipoCausaVacante().equals(Constantes.TIPO_OTRAS_CAUSAS)
                    && request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_PROMOCION)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_PROMOCION);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_OTRAS_CAUSAS)
                    && request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_FALLECIMIENTO)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_FALLECIMIENTO);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_OTRAS_CAUSAS)
                    && request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_DECLINAR)) {
                sustituido.setDeclinoCargo(sustituido.getIdPuesto());
                sustituido.setIdPuesto(Constantes.ID_PUESTO_LISTA_RESERVA);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_CAUSA_RESCISION)
                    && sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_SE)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_RESCISION_SE);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_CAUSA_RESCISION)
                    && sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_RESCISION_CAE);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_CAUSA_TERMINACION)
                    && sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_SE)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_RECONTRATACION_SE);
            } else if (request.getTipoCausaVacante().equals(Constantes.TIPO_CAUSA_TERMINACION)
                    && sustituido.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
                sustituido.setIdPuesto(Constantes.ID_PUESTO_RECONTRATACION_CAE);
            }

            sustituido.setIdZonaResponsabilidad1e(null);
            sustituido.setIdZonaResponsabilidad2e(null);
            sustituido.setIdAreaResponsabilidad1e(null);
            sustituido.setIdAreaResponsabilidad2e(null);

            sustituido.setExisteSustitucion("S");
            sustituido.setIdSistemaActualiza(Constantes.ID_SISTEMA);
            sustituido.setIpUsuario(request.getIpUsario());
            sustituido.setUsuario(request.getUsuario());
        }

        return sustituido;
    }

    private DTOSustitucionesSupycap obtenerSustitucionPendiente(Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspiranteSutituido) {
        List<DTOSustitucionesSupycap> sustituciones = repoJPASustitucionesSupycap
                .findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoOrderById_IdSustitucionDesc(
                        idDetalleProceso, idParticipacion, idAspiranteSutituido);
        return sustituciones != null && !sustituciones.isEmpty() ? sustituciones.get(0) : null;
    }

    @Override
    public DTOSustitucionesSupycap obtenerSustitucionPorId(Integer idDetalleProceso, Integer idParticipacion,
            Integer idSustitucion) {
        return repoJPASustitucionesSupycap.findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucion(
                idDetalleProceso, idParticipacion, idSustitucion);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public void modificarSustitucionSEyCAE(DTORequestSutSEyCAE request) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        VOSustitucionesSupycap sustitucion = null;
        boolean sustitucionSE = false;
        boolean sustitucionCAE = false;

        if ((request.getCaeSustitucionDeSE() == null || !request.getCaeSustitucionDeSE().equals(1))
                && request.getSustitucionCAE() != null && request.getSustitucionCAE().getIdSustitucion() != null) {
            sustitucion = request.getSustitucionCAE();
            sustitucionCAE = true;
        }
        if (request.getSustitucionSE() != null && request.getSustitucionSE().getIdSustitucion() != null) {
            sustitucion = request.getSustitucionSE();
            sustitucionSE = true;
        }
        if (sustitucion != null) {
            DTOSustitucionesSupycap sustBD = obtenerSustitucionPorId(sustitucion.getIdDetalleProceso(),
                    sustitucion.getIdParticipacion(), sustitucion.getIdSustitucion());

            if (sustBD == null || !sustBD.getIdAspiranteSutituido().equals(sustitucion.getIdAspiranteSutituido())
                    || !sustBD.getIdRelacionSustituciones().equals(sustitucion.getIdRelacionSustituciones())) {
                throw new Exception("ERROR ASSustSEyCAEImpl - modificarSustitucionSEyCAE - " + ", idDetalle: "
                        + sustitucion.getIdDetalleProceso() + ", idParticipacion: " + sustitucion.getIdParticipacion()
                        + ", idSustitucion: " + sustitucion.getIdSustitucion() + ", idAspirante: "
                        + sustitucion.getIdAspiranteSutituido() + ", idRelacionSustituciones: "
                        + sustitucion.getIdRelacionSustituciones());
            }

            if (!sustBD.getTipoCausaVacante().equals(request.getTipoCausaVacante())) {
                throw new Exception("ERROR ASSustSEyCAEImpl - modificarSustitucionSEyCAE - tipo causa vacante - "
                        + ", idDetalle: " + request.getSustitucionSE().getIdDetalleProceso() + ", idParticipacion: "
                        + request.getSustitucionSE().getIdParticipacion() + ", idSustitucion: "
                        + request.getSustitucionSE().getIdSustitucion());
            }

            Integer nuevoIdPuesto = null;
            if (sustBD.getTipoCausaVacante().equals(Constantes.TIPO_OTRAS_CAUSAS)
                    && !sustBD.getIdCausaVacante().equals(request.getIdCausaVacante())) {
                if (request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_PROMOCION)) {
                    nuevoIdPuesto = Constantes.ID_PUESTO_PROMOCION;
                } else if (request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_FALLECIMIENTO)) {
                    nuevoIdPuesto = Constantes.ID_PUESTO_FALLECIMIENTO;
                } else if (request.getIdCausaVacante().equals(Constantes.ID_CAUSA_VACANTE_DECLINAR)) {
                    nuevoIdPuesto = Constantes.ID_PUESTO_LISTA_RESERVA;
                }
            }
            if (request.getIdCausaVacante() != null && !request.getIdCausaVacante().equals(0)) {
                sustBD.setIdCausaVacante(request.getIdCausaVacante());
            }
            if (request.getFechaBajaSustituido() != null) {
                sustBD.setFechaBaja(dateFormat.parse(request.getFechaBajaSustituido()));
            }
            if (request.getObservacionesSustituido() != null) {
                sustBD.setObservaciones(request.getObservacionesSustituido().trim());
            }
            if ((sustitucionSE && (request.getDateSustitutoSupervisor() != null
                    && !request.getDateSustitutoSupervisor().isBlank()))
                    || (sustitucionCAE && (request.getDateSustitutoCapacitador() != null
                    && !request.getDateSustitutoCapacitador().isBlank()))) {
                sustBD.setFechaAlta(dateFormat.parse(
                        sustitucionSE ? request.getDateSustitutoSupervisor() : request.getDateSustitutoCapacitador()));
            }
            if (nuevoIdPuesto != null) {
                Integer declinoCargo = nuevoIdPuesto.equals(Constantes.ID_PUESTO_LISTA_RESERVA)
                        ? sustBD.getIdPuestoSustituido()
                        : null;

                repoJPAAspirantes.updateIdPuestoDeclinoCargoAspirante(sustBD.getIdProcesoElectoral(),
                        sustBD.getId().getIdDetalleProceso(), sustBD.getId().getIdParticipacion(),
                        sustBD.getIdAspiranteSutituido(), nuevoIdPuesto, declinoCargo);
            }
            sustBD.setFechaHora(new Date());
            sustBD.setIpUsuario(request.getIpUsario());
            sustBD.setUsuario(request.getUsuario());
            repoJPASustitucionesSupycap.save(sustBD);

            if (sustitucionSE && request.getSustitucionCAE() != null
                    && request.getSustitucionCAE().getIdSustitucion() != null
                    && (request.getDateSustitutoSupervisor() != null
                    || request.getDateSustitutoCapacitador() != null)) {

                VOSustitucionesSupycap sustCAE = request.getSustitucionCAE();
                DTOSustitucionesSupycap sustCAEBD = repoJPASustitucionesSupycap
                        .findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucionAndIdRelacionSustituciones(
                                sustCAE.getIdDetalleProceso(), sustCAE.getIdParticipacion(), sustCAE.getIdSustitucion(),
                                sustCAE.getIdRelacionSustituciones());
                if (sustCAEBD == null) {
                    throw new Exception("ERROR ASSustSEyCAEImpl - modificarSustitucionSEyCAE - sustitución de CAE - "
                            + ", idDetalle: " + sustCAE.getIdDetalleProceso() + ", idParticipacion: "
                            + sustCAE.getIdParticipacion() + ", idSustitucion: " + sustCAE.getIdSustitucion());
                }

                if (!sustBD.getIdRelacionSustituciones().equals(sustCAEBD.getIdRelacionSustituciones())
                        || !sustBD.getIdAspiranteSutituto().equals(sustCAEBD.getIdAspiranteSutituido())) {
                    throw new Exception(
                            "ERROR ASSustSEyCAEImpl - modificarSustitucionSEyCAE - relación del CAE con el SE - "
                            + ", idDetalle: " + sustCAE.getIdDetalleProceso() + ", idParticipacion: "
                            + sustCAE.getIdParticipacion() + ", idSustitucion: " + sustCAE.getIdSustitucion()
                            + ", idRelacionSustituciones: " + sustBD.getIdRelacionSustituciones());
                }

                if (request.getDateSustitutoSupervisor() != null) {
                    sustCAEBD.setFechaBaja(dateFormat.parse(request.getDateSustitutoSupervisor()));
                }

                if (request.getDateSustitutoCapacitador() != null) {
                    sustCAEBD.setFechaAlta(dateFormat.parse(request.getDateSustitutoCapacitador()));
                }

                sustCAEBD.setFechaHora(new Date());
                sustCAEBD.setIpUsuario(request.getIpUsario());
                sustCAEBD.setUsuario(request.getUsuario());
                repoJPASustitucionesSupycap.save(sustCAEBD);
            }

        }
    }

    private DTOAspirantes asignarCorreo(DTOAspirantes aspirante, String correo) {
        if (correo != null && !correo.isBlank()) {
            aspirante.setCorreoCtaCreada(correo);
        }
        return aspirante;
    }

    private void asignarEtapaFecha(DTOSustitucionesSupycap sustitucion, Integer etapa) {
        sustitucion.setEtapa(etapa);
        sustitucion.setFechaHora(new Date());
    }

    private Integer obtieneEtapaActual(Integer idDetalleProceso) {
        DTOCFechas fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProceso(Constantes.ID_FECHA_INCIO_2DA_ETAPA,
                idDetalleProceso);

        return fecha == null ? repoJPACFechas.obtenerFecha(Constantes.ID_FECHA_INCIO_2DA_ETAPA, 0)
                : repoJPACFechas.obtenerFecha(Constantes.ID_FECHA_INCIO_2DA_ETAPA, idDetalleProceso);
    }

}
