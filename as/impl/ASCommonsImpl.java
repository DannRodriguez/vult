package mx.ine.sustseycae.as.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASCommons;
import mx.ine.sustseycae.dto.DTOListaSustituido;
import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOCFechas;
import mx.ine.sustseycae.dto.vo.VOCombo;
import mx.ine.sustseycae.dto.vo.VOComboPendientes;
import mx.ine.sustseycae.dto.vo.VOInfoSustituido;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPACCausasVacante;
import mx.ine.sustseycae.repositories.RepoJPACEtiquetas;
import mx.ine.sustseycae.repositories.RepoJPACFechas;
import mx.ine.sustseycae.util.Constantes;

@Service("asCommons")
public class ASCommonsImpl implements ASCommons {

    @Autowired
    private RepoJPACCausasVacante repoJPACCausasVacante;

    @Autowired
    private RepoJPACFechas repoJPACFechas;

    @Autowired
    private RepoJPACEtiquetas repoJPACEtiquetas;

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Override
    public DTOListaSustituido obtenerListaSustituido(DTORequestListaSustituido requestListaSustituido) {
        try {
            DTOListaSustituido lista = new DTOListaSustituido();

            Integer etapa = obtieneEtapaActual(requestListaSustituido.getIdDetalleProceso());

            List<VOComboPendientes> listaPendientes = null;
            List<VOCombo> listaSupervisor = null;
            List<VOCombo> listaCapacitador = null;

            if (Constantes.FLUJO_CAPTURA.equals(requestListaSustituido.getTipoFlujo())) {
                if (Constantes.SUST_INCAPACIDAD.equals(requestListaSustituido.getModuloSust())
                        || Constantes.SUST_RESC_CONTRATO
                                .equals(requestListaSustituido.getModuloSust())
                        || Constantes.SUST_SEyCAE
                                .equals(requestListaSustituido.getModuloSust())) {

                    listaSupervisor = repoJPACCausasVacante.obtenerListaSupervisor(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(1),
                            etapa);

                    listaCapacitador = repoJPACCausasVacante.obtenerListaCapacitador(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(2),
                            etapa);

                } else if (Constantes.SUST_TERM_CONTRATO
                        .equals(requestListaSustituido.getModuloSust())) {

                    listaSupervisor = repoJPACCausasVacante.obtenerListaSupervisorTermContrato(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);

                    listaCapacitador = repoJPACCausasVacante.obtenerListaCapacitadorTermContrato(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);
                } else {

                    listaSupervisor = repoJPACCausasVacante.obtenerListaSupervisor(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(1, 10),
                            etapa);

                    listaCapacitador = repoJPACCausasVacante.obtenerListaCapacitador(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(2, 11),
                            etapa);
                }

                if (Constantes.SUST_INCAPACIDAD.equals(requestListaSustituido.getModuloSust())) {
                    listaPendientes = repoJPACCausasVacante.obtenerPendientesAdmin(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion());
                } else if (Constantes.SUST_SEyCAE.equals(requestListaSustituido.getModuloSust())
                        || Constantes.SUST_RESC_CONTRATO
                                .equals(requestListaSustituido.getModuloSust())) {
                    listaPendientes = repoJPACCausasVacante.obtenerPendientesSustSeCae(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion());
                } else {
                    listaPendientes = new ArrayList<>();
                }

            } else {
                if (Constantes.SUST_INCAPACIDAD.equals(requestListaSustituido.getModuloSust())) {
                    listaSupervisor = repoJPACCausasVacante.obtenerListaSupervisorCM(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(1, 3),
                            etapa);
                    listaCapacitador = repoJPACCausasVacante.obtenerListaCapacitadorCM(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(1, 3),
                            etapa);
                } else if (Constantes.SUST_TERM_CONTRATO
                        .equals(requestListaSustituido.getModuloSust())) {
                    listaSupervisor = repoJPACCausasVacante.obtenerListaSupervisorCM(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(2),
                            etapa);
                    listaCapacitador = repoJPACCausasVacante.obtenerListaCapacitadorCM(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            Arrays.asList(2),
                            etapa);

                } else if (Constantes.SUST_RESC_CONTRATO
                        .equals(requestListaSustituido.getModuloSust())) {
                    listaSupervisor = repoJPACCausasVacante.obtenListaSESustituidosRescision(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);
                    listaCapacitador = repoJPACCausasVacante.obtenListaCESustituidosRescision(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);
                } else if (Constantes.SUST_SEyCAE.equals(requestListaSustituido.getModuloSust())) {
                    listaSupervisor = repoJPACCausasVacante.obtenListaSESustituidos(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);
                    listaCapacitador = repoJPACCausasVacante.obtenListaCESustituidos(
                            requestListaSustituido.getIdDetalleProceso(),
                            requestListaSustituido.getIdParticipacion(),
                            etapa);
                }

                listaPendientes = new ArrayList<>();
            }

            lista.setPendientesPorCubrir(listaPendientes);
            lista.setSupervisor(listaSupervisor);
            lista.setCapacitador(listaCapacitador);

            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DTOAspirante obtenerInfoSustituido(DTORequestListaSustituido requestListaSustituido) {
        return obtenerInfoSustituido(
                requestListaSustituido.getIdDetalleProceso(),
                requestListaSustituido.getIdParticipacion(),
                requestListaSustituido.getIdAspirante());
    }

    @Override
    public DTOAspirante obtenerInfoSustituido(Integer idDetallePoceso, Integer idParticipacion,
            Integer idAspirante) {
        try {
            List<DTOAspirante> listAspirantes = repoJPACCausasVacante.getAspiranteInfo(
                    idDetallePoceso,
                    idParticipacion,
                    idAspirante);

            Integer etapa = obtieneEtapaActual(idDetallePoceso);

            if (listAspirantes.isEmpty()) {
                return null;
            }

            DTOAspirante aspirante = listAspirantes.get(0);

            if (Constantes.ID_PUESTO_SE.equals(aspirante.getIdPuesto())
                    || Constantes.ID_PUESTO_SE_TMP.equals(aspirante.getIdPuesto())
                    || Constantes.ID_PUESTO_SE_INCAP.equals(aspirante.getIdPuesto())) {
                List<VOInfoSustituido> infoSE = repoJPACCausasVacante.getDatosInfoSE(
                        idDetallePoceso,
                        idParticipacion,
                        idAspirante,
                        etapa,
                        etapa == 1 ? aspirante.getIdZonaResponsabilidad() : aspirante.getIdZonaResponsabilidad2e());

                if (!infoSE.isEmpty()) {
                    aspirante.setNumeroAreaResponsabilidad(
                            infoSE.get(0).getIdAreaResponsabilidad());
                    aspirante.setNumeroZonaResponsabilidad(
                            infoSE.get(0).getIdZonaResponsabilidad());
                }

                aspirante.setFechaInicioContratacion(
                        obtieneFecha(aspirante, Constantes.F_INI_CONTRATACION_SE));
                aspirante.setFechaFinContratacion(
                        obtieneFecha(aspirante, Constantes.F_FIN_CONTRATACION_SE));

            } else if (aspirante.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)
                    || aspirante.getIdPuesto().equals(Constantes.ID_PUESTO_CAE_TMP)
                    || aspirante.getIdPuesto().equals(Constantes.ID_PUESTO_CAE_INCAP)) {
                List<VOInfoSustituido> infoCAE = repoJPACCausasVacante.getDatosInfoCAE(
                        idDetallePoceso,
                        idParticipacion,
                        idAspirante,
                        etapa);

                if (!infoCAE.isEmpty()) {
                    aspirante.setNumeroAreaResponsabilidad(
                            infoCAE.get(0).getIdAreaResponsabilidad());
                    aspirante.setNumeroZonaResponsabilidad(
                            infoCAE.get(0).getIdZonaResponsabilidad());
                }

                aspirante.setFechaInicioContratacion(
                        obtieneFecha(aspirante, Constantes.F_INI_CONTRATACION_CAE));
                aspirante.setFechaFinContratacion(
                        obtieneFecha(aspirante, Constantes.F_FIN_CONTRATACION_CAE));
            }

            aspirante.setNombreCargo(repoJPACCausasVacante.getNombreCargo(aspirante.getIdPuesto()));

            return aspirante;
        } catch (Exception e) {
            return null;
        }
    }

    private String obtieneFecha(DTOAspirante aspirante, Integer idFecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DTOCFechas fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProcesoAndId_IdEstadoAndId_IdDistrito(
                idFecha,
                aspirante.getIdDetalleProceso(),
                aspirante.getIdEstadoAsp(),
                aspirante.getIdParticipacion());

        if (fecha == null) {
            fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProcesoAndId_IdEstadoAndId_IdDistrito(
                    idFecha,
                    aspirante.getIdDetalleProceso(),
                    aspirante.getIdEstadoAsp(),
                    0);
        }

        if (fecha == null) {
            fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProcesoAndId_IdEstadoAndId_IdDistrito(
                    idFecha,
                    aspirante.getIdDetalleProceso(),
                    0,
                    0);
        }

        if (fecha == null) {
            fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProcesoAndId_IdEstadoAndId_IdDistrito(
                    idFecha,
                    0,
                    0,
                    0);
        }

        return fecha == null ? null : fecha.getFecha().format(formatter);
    }

    @Override
    public DTOCEtiquetas obtenerEtiquetaCEtiquetas(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idEtiqueta) {
        DTOCEtiquetas etiqueta = repoJPACEtiquetas
                .findTopById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdEtiqueta(
                        idProcesoElectoral, idDetalleProceso, idEtiqueta);

        if (etiqueta == null) {
            etiqueta = repoJPACEtiquetas.findTopById_IdProcesoElectoralAndId_IdEtiqueta(idProcesoElectoral,
                    idEtiqueta);
        }

        return etiqueta;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public void actualizarURLFotoAspirante(String urlFoto, Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante) {
        repoJPAAspirantes.updateURLFotoAspirante(
                urlFoto,
                idProcesoElectoral,
                idDetalleProceso,
                idParticipacion,
                idAspirante);
    }

    @Override
    public Integer obtieneEtapaActual(Integer idDetalleProceso) {
        DTOCFechas fecha = repoJPACFechas.findById_IdFechaAndId_IdDetalleProceso(
                Constantes.ID_FECHA_INCIO_2DA_ETAPA,
                idDetalleProceso);

        return fecha == null ? repoJPACFechas.obtenerFecha(Constantes.ID_FECHA_INCIO_2DA_ETAPA, 0)
                : repoJPACFechas.obtenerFecha(Constantes.ID_FECHA_INCIO_2DA_ETAPA, idDetalleProceso);
    }

}
