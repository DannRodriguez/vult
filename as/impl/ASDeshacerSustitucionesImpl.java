package mx.ine.sustseycae.as.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASDeshacerSustituciones;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.helper.impl.HLPCuentasSustitucionesImpl;
import mx.ine.sustseycae.models.requests.DTORequestConsultaDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestDeshacerSustitucion;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPADesSustitucionesSupycap;
import mx.ine.sustseycae.repositories.RepoJPASustitucionesSupycap;
import mx.ine.sustseycae.util.Constantes;
import mx.ine.sustseycae.util.Exceptions.ExceptionValidacionAreZore;

@Controller
public class ASDeshacerSustitucionesImpl implements ASDeshacerSustituciones {

        private static final Log log = LogFactory.getLog(ASDeshacerSustitucionesImpl.class);

        @Autowired
        private RepoJPADesSustitucionesSupycap repoJPADesSustitucionesSupycap;

        @Autowired
        private RepoJPAAspirantes repoJPAAspirantes;

        @Autowired
        private RepoJPASustitucionesSupycap repoJPASustitucionesSupycap;

        @Autowired
        private ASCommonsImpl asCommons;

        @Autowired
        private HLPCuentasSustitucionesImpl hlpCuentasSustituciones;

        @Override
        public List<VOConsultaDesSustitucionesSupycap> consultaDeshacerSustitucion(
                        DTORequestConsultaDeshacerSustituciones deshacerSustituciones) {
                return repoJPADesSustitucionesSupycap
                                .consultaDesSustituciones(
                                                deshacerSustituciones.getIdProcesoElectoral(),
                                                deshacerSustituciones.getIdDetalleProceso(),
                                                deshacerSustituciones.getIdParticipacion());

        }

        @Override
        public List<VOConsultaDesSustitucionesSupycap> consultaSustitucionesDeshechas(
                        DTORequestConsultaDeshacerSustituciones deshacerSustituciones) {
                return repoJPADesSustitucionesSupycap.consultaSustitucionesDeshechas(
                                deshacerSustituciones.getIdProcesoElectoral(),
                                deshacerSustituciones.getIdDetalleProceso(),
                                deshacerSustituciones.getIdParticipacion());
        }

        @Override
        @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
                        Exception.class }, transactionManager = "transactionManager")
        public void deshacerSustitucion(DTORequestDeshacerSustitucion requestSustitucion)
                        throws ExceptionValidacionAreZore {
                List<VOConsultaDesSustitucionesSupycap> listaDeshacer = repoJPADesSustitucionesSupycap
                                .consultaDesSustitucionesRelacionadas(requestSustitucion.getIdProcesoElectoral(),
                                                requestSustitucion.getIdDetalleProceso(),
                                                requestSustitucion.getIdParticipacion(),
                                                requestSustitucion.getSustitucionADeshacer()
                                                                .getId_relacion_sustituciones());

                Integer idPrimerSustituido = 0;
                Integer idPrimerSustituto = 0;
                Integer idSegundoSustituido = 0;
                Integer idSegundoSustituto = 0;

                Integer etapa = 1;
                etapa = asCommons.obtieneEtapaActual(requestSustitucion.getIdDetalleProceso());
                if (listaDeshacer.isEmpty()) {
                        throw new ExceptionValidacionAreZore(Constantes.MSG_ERROR_DESHACER_SIN_RELACION);
                }
                if (listaDeshacer.size() == 1) {
                        VOSustitucionesSupycap primeraSustitucion = repoJPASustitucionesSupycap
                                        .obtenerInfoSustitucionById(
                                                        requestSustitucion.getIdProcesoElectoral(),
                                                        requestSustitucion.getIdDetalleProceso(),
                                                        requestSustitucion.getIdParticipacion(),
                                                        listaDeshacer.get(0).getId_sustitucion());
                        idPrimerSustituido = primeraSustitucion.getIdAspiranteSutituido();
                        DTOAspirantes primerAspiranteSustituido = obtenerAspirante(requestSustitucion,
                                        idPrimerSustituido);

                        idPrimerSustituto = primeraSustitucion.getIdAspiranteSutituto();
                        DTOAspirantes primerAspiranteSustituto = obtenerAspirante(requestSustitucion,
                                        idPrimerSustituto);

                        validaAresZores(primeraSustitucion, primerAspiranteSustituto, etapa);

                        try {
                                deshacerSustitucion(primeraSustitucion, primerAspiranteSustituido,
                                                primerAspiranteSustituto,
                                                requestSustitucion, etapa, true);
                        } catch (ExceptionValidacionAreZore e) {
                                throw new ExceptionValidacionAreZore(e.getMessage());
                        }

                } else if (listaDeshacer.size() == 2) {
                        VOSustitucionesSupycap primeraSustitucion = repoJPASustitucionesSupycap
                                        .obtenerInfoSustitucionById(
                                                        requestSustitucion.getIdProcesoElectoral(),
                                                        requestSustitucion.getIdDetalleProceso(),
                                                        requestSustitucion.getIdParticipacion(),
                                                        listaDeshacer.get(0).getId_sustitucion());
                        idPrimerSustituido = primeraSustitucion.getIdAspiranteSutituido();
                        DTOAspirantes primerAspiranteSustituido = obtenerAspirante(requestSustitucion,
                                        idPrimerSustituido);

                        idPrimerSustituto = primeraSustitucion.getIdAspiranteSutituto();
                        DTOAspirantes primerAspiranteSustituto = obtenerAspirante(requestSustitucion,
                                        idPrimerSustituto);

                        VOSustitucionesSupycap segundaSustitucion = repoJPASustitucionesSupycap
                                        .obtenerInfoSustitucionById(
                                                        requestSustitucion.getIdProcesoElectoral(),
                                                        requestSustitucion.getIdDetalleProceso(),
                                                        requestSustitucion.getIdParticipacion(),
                                                        listaDeshacer.get(1).getId_sustitucion());
                        idSegundoSustituido = segundaSustitucion.getIdAspiranteSutituido();
                        DTOAspirantes segundoAspiranteSustituido = obtenerAspirante(requestSustitucion,
                                        idSegundoSustituido);
                        idSegundoSustituto = Objects.requireNonNullElse(segundaSustitucion.getIdAspiranteSutituto(), 0);
                        DTOAspirantes segundoAspiranteSustituto = obtenerAspirante(requestSustitucion,
                                        idSegundoSustituto);

                        validaAresZores(primeraSustitucion, primerAspiranteSustituto, etapa);
                        validaAresZores(segundaSustitucion, segundoAspiranteSustituto, etapa);

                        try {
                                if ((segundaSustitucion.getIdAspiranteSutituido() != null && Objects.equals(
                                                primeraSustitucion.getIdAspiranteSutituido(),
                                                segundaSustitucion.getIdAspiranteSutituto())) &&
                                                (primeraSustitucion.getIdAspiranteSutituto() != null
                                                                && Objects.equals(segundaSustitucion
                                                                                .getIdAspiranteSutituido(),
                                                                                primeraSustitucion
                                                                                                .getIdAspiranteSutituto()))) {
                                        deshacerSustitucion(segundaSustitucion, segundoAspiranteSustituido,
                                                        segundoAspiranteSustituto,
                                                        requestSustitucion, etapa, false);
                                        deshacerSustitucion(primeraSustitucion, primerAspiranteSustituido,
                                                        primerAspiranteSustituto,
                                                        requestSustitucion, etapa, true);
                                } else {
                                        deshacerSustitucion(primeraSustitucion, primerAspiranteSustituido,
                                                        primerAspiranteSustituto,
                                                        requestSustitucion, etapa, true);
                                        deshacerSustitucion(segundaSustitucion, segundoAspiranteSustituido,
                                                        segundoAspiranteSustituto,
                                                        requestSustitucion, etapa, false);
                                }

                        } catch (ExceptionValidacionAreZore e) {
                                throw new ExceptionValidacionAreZore(e.getMessage());
                        }
                } else {
                        throw new ExceptionValidacionAreZore(Constantes.MSG_ERROR_DESHACER_MAS_RELACIONES);
                }
        }

        private DTOAspirantes obtenerAspirante(DTORequestDeshacerSustitucion requestSustitucion, Integer idAspirante) {
                if (idAspirante == null)
                        return null;
                return repoJPAAspirantes
                                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                                                requestSustitucion.getIdProcesoElectoral(),
                                                requestSustitucion.getIdDetalleProceso(),
                                                requestSustitucion.getIdParticipacion(),
                                                idAspirante);
        }

        public void deshacerSustitucion(VOSustitucionesSupycap sustitucion, DTOAspirantes sustituido,
                        DTOAspirantes sustituto, DTORequestDeshacerSustitucion requestDeshacerSustitucion,
                        Integer etapa,
                        boolean esSustDesencadenante) throws ExceptionValidacionAreZore {
                try {
                        repoJPADesSustitucionesSupycap.insertarDeshacerSustitucion(
                                        requestDeshacerSustitucion.getIdDetalleProceso(),
                                        requestDeshacerSustitucion.getIdParticipacion(),
                                        sustitucion.getIdSustitucion(),
                                        etapa,
                                        requestDeshacerSustitucion.getIpUsuario(),
                                        requestDeshacerSustitucion.getIpUsuario());
                        repoJPASustitucionesSupycap.deleteById(new DTOSustitucionesSupycapId(
                                        requestDeshacerSustitucion.getIdDetalleProceso(),
                                        requestDeshacerSustitucion.getIdParticipacion(),
                                        sustitucion.getIdSustitucion()));

                        if (sustituto != null) {
                                Integer idPuestoSustitutoAnt = sustituto.getIdPuesto();

                                sustituto.setIdPuesto(sustitucion.getIdPuestoSustituto());

                                if (Objects.equals(sustitucion.getIdCausaVacante(), 2)
                                                && Objects.equals(sustitucion.getTipoCausaVacante(), 4)) {
                                        if (List.of(Constantes.ID_PUESTO_SE_INCAP, Constantes.ID_PUESTO_CAE_INCAP)
                                                        .contains(sustituto.getIdPuesto())) {
                                                restaurarAreasYZonas(sustituto, sustitucion);

                                        } else {
                                                limpiarAreasYZonas(sustituto);
                                        }
                                } else {
                                        if (!sustituto.getIdPuesto().equals(idPuestoSustitutoAnt)) {
                                                limpiarAreasYZonas(sustituto);
                                        }
                                }

                                sustituto.setFechaHora(new Date());
                                sustituto.setExisteSustitucion(null);
                                sustituto.setUsuario(requestDeshacerSustitucion.getUser());
                                sustituto.setIpUsuario(sustitucion.getIpUsuario());
                                sustituto.setIdSistemaActualiza(Constantes.ID_SISTEMA);
                                repoJPAAspirantes.saveAndFlush(sustituto);

                                actualizarCuentaPorCambioPuesto(
                                                sustituto,
                                                idPuestoSustitutoAnt,
                                                sustitucion.getIdPuestoSustituto(),
                                                requestDeshacerSustitucion.getUser(),
                                                true);

                        }

                        Integer idPuestoSustituidoAnt = sustituido.getIdPuesto();
                        sustituido.setIdPuesto(sustitucion.getIdPuestoSustituido());
                        if (Constantes.ID_PUESTO_SE.equals(sustituido.getIdPuesto())
                                        && Constantes.ID_PUESTO_CAE.equals(idPuestoSustituidoAnt)
                                        && esSustDesencadenante) {

                                throw new ExceptionValidacionAreZore(
                                                Constantes.MSG_ERROR_DESHACER_NO_PERMITIDO
                                                                + Stream.of(sustituido.getApellidoPaterno(),
                                                                                sustituido.getApellidoMaterno(),
                                                                                sustituido.getNombre())
                                                                                .filter(Objects::nonNull)
                                                                                .collect(Collectors.joining(" "))
                                                                + " en ARE "
                                                                + (sustituido.getIdAreaResponsabilidad2e() == null
                                                                                ? sustituido.getIdAreaResponsabilidad1e()
                                                                                : sustituido.getIdAreaResponsabilidad2e())
                                                                + ".");
                        }

                        sustituido.setIdAreaResponsabilidad1e(sustitucion.getIdAreaResponsabilidad1e());
                        sustituido.setIdAreaResponsabilidad2e(sustitucion.getIdAreaResponsabilidad2e());
                        sustituido.setIdZonaResponsabilidad1e(sustitucion.getIdAZonaResponsabilidad1e());
                        sustituido.setIdZonaResponsabilidad2e(sustitucion.getIdZonaResponsabilidad2e());

                        if (Objects.equals(sustitucion.getIdCausaVacante(), 3)
                                        && Objects.equals(sustitucion.getTipoCausaVacante(), 3)) {
                                sustituido.setDeclinoCargo(null);
                        }
                        sustituido.setFechaHora(new Date());
                        sustituido.setExisteSustitucion(null);
                        sustituido.setUsuario(requestDeshacerSustitucion.getUser());
                        sustituido.setIpUsuario(sustitucion.getIpUsuario());
                        sustituido.setIdSistemaActualiza(Constantes.ID_SISTEMA);
                        repoJPAAspirantes.saveAndFlush(sustituido);

                        actualizarCuentaPorCambioPuesto(
                                        sustituido,
                                        idPuestoSustituidoAnt,
                                        sustitucion.getIdPuestoSustituido(),
                                        requestDeshacerSustitucion.getUser(),
                                        false);

                } catch (ExceptionValidacionAreZore e) {
                        log.error("ASDeshacerSustitucionesImpl.deshacerSustitucion, Se va perder un puesto: " + sustitucion.getIdSustitucion() + ": ", e);
                        throw new ExceptionValidacionAreZore(e.getMessage());
                } catch (Exception e) {
                        log.error("ASDeshacerSustitucionesImpl.deshacerSustitucion, error al deshacer la sustitución: " + sustitucion.getIdSustitucion() + ": ", e);
                }

        }

        public void validaAresZores(VOSustitucionesSupycap sustitucion, DTOAspirantes sustituto, Integer etapa)
                        throws ExceptionValidacionAreZore {
                if (sustitucion != null && sustituto != null) {

                        validarCoincidencia(sustitucion.getIdAZonaResponsabilidad1e(),
                                        sustituto.getIdZonaResponsabilidad1e(),
                                        Constantes.MSG_ERROR_DESHACER_ZORES_E1);

                        validarCoincidencia(sustitucion.getIdAreaResponsabilidad1e(),
                                        sustituto.getIdAreaResponsabilidad1e(),
                                        Constantes.MSG_ERROR_DESHACER_ARES_E1);

                        if (etapa == 2) {
                                validarCoincidencia(sustitucion.getIdZonaResponsabilidad2e(),
                                                sustituto.getIdZonaResponsabilidad2e(),
                                                Constantes.MSG_ERROR_DESHACER_ZORES_E2);

                                validarCoincidencia(sustitucion.getIdAZonaResponsabilidad1e(),
                                                sustituto.getIdZonaResponsabilidad2e(),
                                                Constantes.MSG_ERROR_DESHACER_ZORE_CAMBIO);

                                validarCoincidencia(sustitucion.getIdAreaResponsabilidad2e(),
                                                sustituto.getIdAreaResponsabilidad2e(),
                                                Constantes.MSG_ERROR_DESHACER_ARES_E2);

                                validarCoincidencia(sustitucion.getIdAreaResponsabilidad1e(),
                                                sustituto.getIdAreaResponsabilidad2e(),
                                                Constantes.MSG_ERROR_DESHACER_ARE_CAMBIO);

                        }
                }
        }

        private void validarCoincidencia(Integer valorSustitucion, Integer valorSustituto, String mensaje)
                        throws ExceptionValidacionAreZore {
                if (valorSustitucion != null && valorSustituto != null
                                && !Objects.equals(valorSustitucion, valorSustituto)) {
                        throw new ExceptionValidacionAreZore(mensaje);
                }
        }

        private void limpiarAreasYZonas(DTOAspirantes sustituto) {
                sustituto.setIdAreaResponsabilidad1e(null);
                sustituto.setIdAreaResponsabilidad2e(null);
                sustituto.setIdZonaResponsabilidad1e(null);
                sustituto.setIdZonaResponsabilidad2e(null);
        }

        private void restaurarAreasYZonas(DTOAspirantes sustituto, VOSustitucionesSupycap sustitucion) {
                sustituto.setIdAreaResponsabilidad1e(sustitucion.getIdAreaResponsabilidad1e());
                sustituto.setIdAreaResponsabilidad2e(sustitucion.getIdAreaResponsabilidad2e());
                sustituto.setIdZonaResponsabilidad1e(sustitucion.getIdAZonaResponsabilidad1e());
                sustituto.setIdZonaResponsabilidad2e(sustitucion.getIdZonaResponsabilidad2e());
        }

        private void actualizarCuentaPorCambioPuesto(
                        DTOAspirantes aspirante,
                        Integer idPuestoAnterior,
                        Integer idPuestoNuevo,
                        String usuario,
                        boolean esSustituto) {

                Set<Integer> puestosConCuenta = new HashSet<>(Set.of(
                                Constantes.ID_PUESTO_SE,
                                Constantes.ID_PUESTO_CAE));

                if (esSustituto) {
                        puestosConCuenta.add(Constantes.ID_PUESTO_SE_TMP);
                        puestosConCuenta.add(Constantes.ID_PUESTO_CAE_TMP);
                }

                boolean teniaCuenta = puestosConCuenta.contains(idPuestoAnterior);
                boolean tieneCuentaAhora = puestosConCuenta.contains(idPuestoNuevo);

                if (teniaCuenta && tieneCuentaAhora &&
                                (idPuestoNuevo.equals(Constantes.ID_PUESTO_SE) ||
                                                idPuestoNuevo.equals(Constantes.ID_PUESTO_CAE))) {
                        hlpCuentasSustituciones.modificarCuentaSustitucion(aspirante, usuario);

                } else if (!teniaCuenta && tieneCuentaAhora) {
                        hlpCuentasSustituciones.crearCuentaSustitucion(aspirante, usuario);

                } else if (teniaCuenta && !tieneCuentaAhora) {
                        hlpCuentasSustituciones.eliminarCuentaSustitucion(aspirante, usuario);
                }
        }

}
