package mx.ine.sustseycae.bsd.impl;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASGestionCuentasInterface;
import mx.ine.sustseycae.bo.BOValidacionesCuentasInterface;
import mx.ine.sustseycae.bsd.BSDGestionCuentasInterface;
import mx.ine.sustseycae.dto.DTOCPermisosCta;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentasId;
import mx.ine.sustseycae.dto.db.DTOGruposDefaultSistema;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdGestionCuentas")
@Scope("prototype")
public class BSDGestionCuentasImpl implements BSDGestionCuentasInterface {

    private static final Log log = LogFactory.getLog(BSDGestionCuentasImpl.class);

    @Autowired
    @Qualifier("asGestionCuentas")
    private ASGestionCuentasInterface asGestionCuentas;

    @Autowired
    @Qualifier("boValidacionesCuentas")
    private BOValidacionesCuentasInterface boValidacionesCuentas;

    @Autowired
    @Qualifier("pathGlusterSistDECEYEC")
    String pathGlusterSistDECEYEC;

    private static final String WEB = "web";
    private static final String ACUSES_CUENTAS = "acusesCuentas";
    private static final String CAE = "cae_";
    private static final String SE = "se_";
    private static final String PDF = ".pdf";

    private static final Integer TIPO_SO_SE = 7;
    private static final Integer TIPO_SO_CAE = 6;
    private String ipEjecucion = "";
    private String usuario = "";
    private String urlPoliticas = "";
    private String urlCambioContra = "";
    private String carpetaSupycap = "";
    private List<DTOCParametros> listParamCorreo = new ArrayList<>();
    private String correoPersonalizado = "";

    @Override
    public void adminCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta, DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        try {

            InetAddress inetAddress = InetAddress.getLocalHost();
            ipEjecucion = inetAddress.getHostAddress();
            if (usuario == null) {
                this.usuario = Constantes.ETIQUETA_USUARIO_SUST;
            } else {
                this.usuario = usuario;
            }

            urlPoliticas = asGestionCuentas
                    .obtenerDescripcionParametroCtasSeCae(Constantes.ID_PARAM_ADMIN_CUENTAS_POLITICAS);
            urlCambioContra = asGestionCuentas
                    .obtenerDescripcionParametroCtasSeCae(Constantes.ID_PARAM_ADMIN_CUENTAS_CAMBIO_CONTRA);

            Integer idProcesoEtq = 0;
            if (aspiranteCrearCuenta != null && aspiranteCrearCuenta.getIdProcesoElectoral() != null) {
                idProcesoEtq = aspiranteCrearCuenta.getIdProcesoElectoral();
            } else if (aspiranteModificarCuenta != null && aspiranteModificarCuenta.getIdProcesoElectoral() != null) {
                idProcesoEtq = aspiranteModificarCuenta.getIdProcesoElectoral();
            } else if (aspiranteEliminarCuenta != null && aspiranteEliminarCuenta.getIdProcesoElectoral() != null) {
                idProcesoEtq = aspiranteEliminarCuenta.getIdProcesoElectoral();
            }

            Set<Integer> setIdParametros = new HashSet<>();
            setIdParametros.add(Constantes.ID_PARAMETRO_ENVIAR_CORREO);
            setIdParametros.add(Constantes.ID_PARAMETRO_USAR_CORREO_INSTITUCIONAL);
            listParamCorreo = asGestionCuentas.obtenerCParametros(idProcesoEtq, 0, setIdParametros);
            DTOCEtiquetas etiqueta = asGestionCuentas.obtenerEtiquetaPorIdEtiquetaProceso(idProcesoEtq,
                    Constantes.ID_ETIQUETA_CORREO_INSTITUCIONAL);
            correoPersonalizado = etiqueta != null ? etiqueta.getEtiqueta() : "";

            DTOCEtiquetas etiquetaCarpetaSupycap = asGestionCuentas.obtenerEtiquetaPorIdEtiquetaProceso(idProcesoEtq,
                    Constantes.ID_ETIQUETA_CARPETA_SUPYCAP);
            carpetaSupycap = etiquetaCarpetaSupycap != null ? etiquetaCarpetaSupycap.getEtiqueta() : "";

            if (aspiranteCrearCuenta != null && aspiranteCrearCuenta.getId() != null) {
                procesarCreacionCuenta(aspiranteCrearCuenta);
            }

            if (aspiranteModificarCuenta != null && aspiranteModificarCuenta.getId() != null) {
                procesarModificarCuenta(aspiranteModificarCuenta);
            }

            if (aspiranteEliminarCuenta != null && aspiranteEliminarCuenta.getId() != null) {
                procesarEliminarCuenta(aspiranteEliminarCuenta);
            }

        } catch (Exception e) {
            log.error("ERROR BSDGestionCuentasImpl - adminCuentasSustitucion ", e);
        }

    }

    private void procesarCreacionCuenta(DTOAspirantes aspiranteCrearCuenta) {

        Map<String, Integer> zoreAre = obtenerIdNumeroZoreAre(aspiranteCrearCuenta);
        Integer tipoSO = Boolean.TRUE.equals(boValidacionesCuentas.isPuestoSE(aspiranteCrearCuenta.getIdPuesto()))
                ? TIPO_SO_SE
                : TIPO_SO_CAE;
        try {

            DTOCreacionCuentas creacionCuentaAspirante = asGestionCuentas.obtenerCreacionCtaAspirante(
                    aspiranteCrearCuenta.getIdProcesoElectoral(), aspiranteCrearCuenta.getIdDetalleProceso(),
                    aspiranteCrearCuenta.getIdParticipacion(), aspiranteCrearCuenta.getIdAspirante());

            if (creacionCuentaAspirante == null || creacionCuentaAspirante.getEstatusCuenta()
                    .equals(Constantes.ESTATUS_CUENTA_PENDIENTE_ELIMINAR)) {

                boolean cuentaEliminada = false;
                if (creacionCuentaAspirante != null && creacionCuentaAspirante.getEstatusCuenta()
                        .equals(Constantes.ESTATUS_CUENTA_PENDIENTE_ELIMINAR)) {
                    cuentaEliminada = procesarCuentaPendienteEliminar(aspiranteCrearCuenta, creacionCuentaAspirante);
                }

                if (creacionCuentaAspirante == null || cuentaEliminada) {

                    DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(
                            aspiranteCrearCuenta, zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO, usuario,
                            ipEjecucion);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);

                    crearCuentaAsignarPermisosYGenerarComprobante(aspiranteCrearCuenta, creacionCta, tipoSO);

                } else {
                    DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(
                            aspiranteCrearCuenta, zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO, usuario,
                            ipEjecucion);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);
                }

            } else {
                DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(
                        aspiranteCrearCuenta, zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO, usuario,
                        ipEjecucion);
                asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);
            }

        } catch (Exception e) {
            DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(aspiranteCrearCuenta,
                    zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO, usuario, ipEjecucion);
            asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);
            log.error("ERROR BSDGestionCuentasImpl - procesarCreacionCuenta: " + aspiranteCrearCuenta.getIdToString()
                    + "-", e);
        }
    }

    private void procesarModificarCuenta(DTOAspirantes aspiranteModificarCuenta) {

        Map<String, Integer> zoreAre = obtenerIdNumeroZoreAre(aspiranteModificarCuenta);
        Integer tipoSO = Boolean.TRUE.equals(boValidacionesCuentas.isPuestoSE(aspiranteModificarCuenta.getIdPuesto()))
                ? TIPO_SO_SE
                : TIPO_SO_CAE;
        try {
            DTOCreacionCuentas creacionCuentaAspirante = asGestionCuentas.obtenerCreacionCtaAspirante(
                    aspiranteModificarCuenta.getIdProcesoElectoral(), aspiranteModificarCuenta.getIdDetalleProceso(),
                    aspiranteModificarCuenta.getIdParticipacion(), aspiranteModificarCuenta.getIdAspirante());

            if (creacionCuentaAspirante != null
                    && (creacionCuentaAspirante.getEstatusCuenta().equals(Constantes.ESTATUS_CUENTA_CREADA)
                    || creacionCuentaAspirante.getEstatusCuenta()
                            .equals(Constantes.ESTATUS_CUENTA_PENDIENTE_MODIFICAR))) {

                creacionCuentaAspirante.setIdZoreAre(zoreAre.get("idZoreAre"));
                creacionCuentaAspirante.setNumZoreAre(zoreAre.get("numZoreAre"));
                creacionCuentaAspirante.setIdSO(tipoSO);
                asignarPermisosCuenta(aspiranteModificarCuenta.getIdPuesto(), creacionCuentaAspirante, tipoSO);

            } else {
                if (creacionCuentaAspirante == null) {
                    DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(
                            aspiranteModificarCuenta, zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO,
                            usuario, ipEjecucion);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);

                } else {
                    creacionCuentaAspirante.setEstatusCuenta(Constantes.ESTATUS_CUENTA_PENDIENTE_MODIFICAR);
                    creacionCuentaAspirante.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_SIN_ASIGNAR);
                    asignarUsuarioFecha(creacionCuentaAspirante);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCuentaAspirante);
                }
            }

        } catch (Exception e) {
            DTOCreacionCuentas creacionCta = boValidacionesCuentas.aspiranteToDTOCreacionCuentas(
                    aspiranteModificarCuenta, zoreAre.get("idZoreAre"), zoreAre.get("numZoreAre"), tipoSO, usuario,
                    ipEjecucion);
            creacionCta.setEstatusCuenta(Constantes.ESTATUS_CUENTA_CREADA);
            creacionCta.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_SIN_ASIGNAR);
            asignarUsuarioFecha(creacionCta);
            asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCta);

            log.error("ERROR BSDGestionCuentasImpl - procesarModificarCuenta: "
                    + aspiranteModificarCuenta.getIdToString() + "-", e);
        }

    }

    private void procesarEliminarCuenta(DTOAspirantes aspiranteEliminarCuenta) {
        try {
            DTOCreacionCuentas creacionCuentaAspirante = asGestionCuentas.obtenerCreacionCtaAspirante(
                    aspiranteEliminarCuenta.getIdProcesoElectoral(), aspiranteEliminarCuenta.getIdDetalleProceso(),
                    aspiranteEliminarCuenta.getIdParticipacion(), aspiranteEliminarCuenta.getIdAspirante());

            if (creacionCuentaAspirante != null) {
                if (creacionCuentaAspirante.getEstatusCuenta().equals(Constantes.ESTATUS_CUENTA_CREADA)
                        || creacionCuentaAspirante.getEstatusCuenta()
                                .equals(Constantes.ESTATUS_CUENTA_PENDIENTE_MODIFICAR)
                        || creacionCuentaAspirante.getEstatusCuenta()
                                .equals(Constantes.ESTATUS_CUENTA_PENDIENTE_ELIMINAR)) {

                    Boolean cuentaEliminada;
                    DTOParticipacionGeografica geo = asGestionCuentas.obtenerParticipacionGeo(
                            aspiranteEliminarCuenta.getIdDetalleProceso(),
                            aspiranteEliminarCuenta.getIdParticipacion());

                    Set<Integer> setIdParametros = new HashSet<>();
                    setIdParametros.add(Constantes.ID_PARAM_LDAP_CUENTAS);
                    List<DTOCParametros> cParametros = asGestionCuentas.obtenerCParametros(geo.getIdProcesoElectoral(),
                            geo.getId().getIdDetalleProceso(), setIdParametros);

                    if (cParametros != null && !cParametros.isEmpty()) {

                        Integer ldapCuenta = cParametros.get(0).getValorParametro();
                        cuentaEliminada = asGestionCuentas.eliminarCuenta(creacionCuentaAspirante, usuario, ipEjecucion,
                                ldapCuenta);
                        if (Boolean.TRUE.equals(cuentaEliminada)) {

                            asGestionCuentas.eliminarRegistroCreacionCuenta(creacionCuentaAspirante.getId());
                            asGestionCuentas.actualizarAspiranteUidServicoUsado(
                                    aspiranteEliminarCuenta.getIdProcesoElectoral(),
                                    aspiranteEliminarCuenta.getIdDetalleProceso(),
                                    aspiranteEliminarCuenta.getIdParticipacion(),
                                    aspiranteEliminarCuenta.getIdAspirante(), null, null, usuario, ipEjecucion);

                        } else {
                            creacionCuentaAspirante.setEstatusCuenta(Constantes.ESTATUS_CUENTA_PENDIENTE_ELIMINAR);
                            asignarUsuarioFecha(creacionCuentaAspirante);
                            asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCuentaAspirante);
                        }
                    }
                } else {
                    asGestionCuentas.eliminarRegistroCreacionCuenta(creacionCuentaAspirante.getId());
                }
            }

        } catch (Exception e) {
            log.error("ERROR BSDGestionCuentasImpl - procesarEliminarCuenta ", e);
        }
    }

    private boolean procesarCuentaPendienteEliminar(DTOAspirantes aspirante,
            DTOCreacionCuentas creacionCuentaAspirante) {

        boolean cuentaEliminada;
        try {
            DTOParticipacionGeografica geo = asGestionCuentas.obtenerParticipacionGeo(aspirante.getIdDetalleProceso(),
                    aspirante.getIdParticipacion());
            Set<Integer> setIdParametros = new HashSet<>();
            setIdParametros.add(Constantes.ID_PARAM_LDAP_CUENTAS);

            List<DTOCParametros> cParametros = asGestionCuentas.obtenerCParametros(geo.getIdProcesoElectoral(),
                    geo.getId().getIdDetalleProceso(), setIdParametros);
            if (cParametros == null || cParametros.isEmpty()) {
                return false;
            }
            Integer ldapCuenta = cParametros.get(0).getValorParametro();

            DTORespuestaWSAdmin.Usuario usuarioAUS = asGestionCuentas.obtenerUsuarioPorMail(ldapCuenta,
                    creacionCuentaAspirante.getCorreoCuentaCreada());
            creacionCuentaAspirante = asGestionCuentas.obtenerCreacionCtaAspirante(aspirante.getIdProcesoElectoral(),
                    aspirante.getIdDetalleProceso(), aspirante.getIdParticipacion(), aspirante.getIdAspirante());

            if (creacionCuentaAspirante != null) {
                if (creacionCuentaAspirante.getEstatusCuenta().equals(Constantes.ESTATUS_CUENTA_PENDIENTE_ELIMINAR)) {

                    creacionCuentaAspirante.setUidCuenta(usuarioAUS.getUid());
                    cuentaEliminada = asGestionCuentas.eliminarCuenta(creacionCuentaAspirante, usuario, ipEjecucion,
                            ldapCuenta);
                    if (cuentaEliminada) {
                        try {
                            asGestionCuentas.actualizarAspiranteUidServicoUsado(
                                    creacionCuentaAspirante.getIdProcesoElectoral(),
                                    creacionCuentaAspirante.getId().getIdDetalleProceso(),
                                    creacionCuentaAspirante.getId().getIdParticipacion(),
                                    creacionCuentaAspirante.getId().getIdAspirante(), null, null, usuario, ipEjecucion);
                        } catch (Exception e) {
                            log.error("ERROR BSDGestionCuentasImpl - actualizarAspiranteUidServicoUsado ", e);
                        }

                    }

                } else {
                    cuentaEliminada = false;
                }

            } else {
                cuentaEliminada = true;
            }
        } catch (Exception e) {
            log.error("ERROR BSDGestionCuentasImpl - procesarCuentaPendienteEliminar ", e);
            cuentaEliminada = false;
        }
        return cuentaEliminada;
    }

    private Map<String, Integer> obtenerIdNumeroZoreAre(DTOAspirantes aspiranteCuentaCrear) {
        Map<String, Integer> mapIdZoreAre = new HashMap<>();
        Integer idZoreAre;
        Integer numZoreAre;

        if (Boolean.TRUE.equals(boValidacionesCuentas.isPuestoSE(aspiranteCuentaCrear.getIdPuesto()))) {
            idZoreAre = aspiranteCuentaCrear.getIdZonaResponsabilidad2e() == null
                    ? aspiranteCuentaCrear.getIdZonaResponsabilidad1e()
                    : aspiranteCuentaCrear.getIdZonaResponsabilidad2e();

            numZoreAre = asGestionCuentas.obtenerNumeroZORE(aspiranteCuentaCrear.getIdProcesoElectoral(),
                    aspiranteCuentaCrear.getIdDetalleProceso(), aspiranteCuentaCrear.getIdParticipacion(), idZoreAre);

        } else {
            idZoreAre = aspiranteCuentaCrear.getIdAreaResponsabilidad2e() == null
                    ? aspiranteCuentaCrear.getIdAreaResponsabilidad1e()
                    : aspiranteCuentaCrear.getIdAreaResponsabilidad2e();
            numZoreAre = asGestionCuentas.obtenerNumeroARE(aspiranteCuentaCrear.getIdProcesoElectoral(),
                    aspiranteCuentaCrear.getIdDetalleProceso(), aspiranteCuentaCrear.getIdParticipacion(), idZoreAre);
        }

        mapIdZoreAre.put("idZoreAre", idZoreAre);
        mapIdZoreAre.put("numZoreAre", numZoreAre);

        return mapIdZoreAre;

    }

    private void crearCuentaAsignarPermisosYGenerarComprobante(DTOAspirantes aspirante,
            DTOCreacionCuentas cuentaPendiente, Integer tipoSO) {

        boolean correoCreadoPorSistema = false;
        if (aspirante.getCorreoCtaCreada() == null || aspirante.getCorreoCtaCreada().isBlank()) {
            String correoCreadoSistema = asGestionCuentas.creaEstructuraCorreoSistema(aspirante.getIdProcesoElectoral(),
                    aspirante.getIdDetalleProceso(), aspirante.getIdParticipacion(), aspirante.getIdAspirante(),
                    aspirante.getIdPuesto());
            correoCreadoPorSistema = true;
            aspirante.setCorreoCtaCreada(correoCreadoSistema);
        }

        boolean asignarPermisos = false;
        boolean generarComprobante = false;
        DTORespuestaWSAdmin.Usuario usuarioAUS = null;

        for (int i = 1; i <= Constantes.INTENTOS_CREAR_CUENTA; i++) {
            usuarioAUS = asGestionCuentas.crearCuenta(aspirante, cuentaPendiente);
            if (usuarioAUS != null && usuarioAUS.getMensajeError() != null && usuarioAUS.getMensajeError().isBlank()) {
                break;
            }
        }

        if (usuarioAUS != null && usuarioAUS.getMensajeError() != null && usuarioAUS.getMensajeError().isBlank()) {
            asignarPermisos = true;
            generarComprobante = true;

            cuentaPendiente.setCorreoCuentaCreada(aspirante.getCorreoCtaCreada());
            cuentaPendiente.setCorreoCuentaNotificacion(aspirante.getCorreoCtaNotificacion());
            cuentaPendiente.setTelefonoCuentaCreada(aspirante.getTelefonoCtaCreada());
            cuentaPendiente.setEstatusCuenta(Constantes.ESTATUS_CUENTA_CREADA);
            cuentaPendiente.setIdSistema(Constantes.ID_SISTEMA);
            cuentaPendiente.setUidCuenta(usuarioAUS.getUid());
            asignarUsuarioFecha(cuentaPendiente);

            aspirante.setUidCuenta(usuarioAUS.getUid());
            aspirante.setServicioUsadoCta(Constantes.VALOR_SERVICIO_AUS);
            if (correoCreadoPorSistema) {
                aspirante.setCorreoCtaCreada(null);
            }

            asGestionCuentas.actualizarAspiranteUidServicoUsado(aspirante.getIdProcesoElectoral(),
                    aspirante.getIdDetalleProceso(), aspirante.getIdParticipacion(), aspirante.getIdAspirante(),
                    usuarioAUS.getUid(), Constantes.VALOR_SERVICIO_AUS, usuario, ipEjecucion);
            asGestionCuentas.guardarOActalizarCreacionCuenta(cuentaPendiente);
        }

        if (asignarPermisos) {
            List<DTOGruposDefaultSistema> permisos = asGestionCuentas.obtenerPermisosCuenta(tipoSO);
            List<DTOCPermisosCta> permisosAsignados = null;

            if (permisos != null && !permisos.isEmpty()) {
                for (int i = 1; i <= Constantes.INTENTOS_ASIGNAR_PERMISOS; i++) {
                    permisosAsignados = asGestionCuentas.asignarPermisosCuenta(permisos, aspirante.getUidCuenta(),
                            ipEjecucion, usuario);

                    if (permisosAsignados != null && !permisosAsignados.isEmpty()) {
                        break;
                    }
                }
            }

            if (permisosAsignados != null && !permisosAsignados.isEmpty() && usuarioAUS != null) {
                cuentaPendiente.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_ASIGNADOS);
                usuarioAUS.setPermisosAsignados(permisosAsignados);
            } else {
                cuentaPendiente.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_SIN_ASIGNAR);
            }

            asignarUsuarioFecha(cuentaPendiente);
            asGestionCuentas.guardarOActalizarCreacionCuenta(cuentaPendiente);
        }

        if (generarComprobante) {

            if (correoCreadoPorSistema) {
                try {
                    boValidacionesCuentas.guardarPdf(aspirante, usuarioAUS, urlPoliticas, urlCambioContra, usuario,
                            aspirante.getIdPuesto(), false, carpetaSupycap);
                } catch (Exception e) {
                    cuentaPendiente.setCodigoError(Constantes.CODIGO_ERROR_GENERAR_ACUSE);
                    cuentaPendiente.setMensajeError(e.getMessage());
                    asignarUsuarioFecha(cuentaPendiente);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(cuentaPendiente);
                    log.error("ERROR BSDGestionCuentasImpl - guardarPdf ", e);
                }
            } else {
                try {
                    DTOParticipacionGeografica geo = asGestionCuentas
                            .obtenerParticipacionGeo(aspirante.getIdDetalleProceso(), aspirante.getIdParticipacion());
                    String nombreComprobante = boValidacionesCuentas.getNombreComprobante(geo.getIdEstado(),
                            geo.getIdDistrito(), cuentaPendiente.getNumZoreAre(), aspirante.getIdPuesto());
                    boValidacionesCuentas.enviarComprobante(aspirante, usuarioAUS, aspirante.getCorreoCtaNotificacion(),
                            true, null, nombreComprobante, urlPoliticas, urlCambioContra, usuario,
                            aspirante.getIdPuesto(), carpetaSupycap, listParamCorreo, correoPersonalizado);
                } catch (Exception e) {
                    cuentaPendiente.setCodigoError(Constantes.CODIGO_ERROR_GENERAR_ACUSE);
                    cuentaPendiente.setMensajeError(e.getMessage());
                    asignarUsuarioFecha(cuentaPendiente);
                    asGestionCuentas.guardarOActalizarCreacionCuenta(cuentaPendiente);
                    log.error("ERROR BSDGestionCuentasImpl - enviarComprobante ", e);
                }
            }
        }

    }

    @Override
    public DTOAspirantes obtenerAspirante(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspirante) {
        return asGestionCuentas.obtenerAspirante(idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);
    }

    public void asignarPermisosCuenta(Integer idPuesto, DTOCreacionCuentas creacionCuentaAspirante, Integer tipoSO) {

        List<DTOGruposDefaultSistema> permisos = asGestionCuentas.obtenerPermisosCuenta(tipoSO);
        List<DTOCPermisosCta> permisosAsignados = null;

        if (permisos != null && !permisos.isEmpty()) {
            for (int i = 1; i <= Constantes.INTENTOS_ASIGNAR_PERMISOS; i++) {
                permisosAsignados = asGestionCuentas.asignarPermisosCuenta(permisos,
                        creacionCuentaAspirante.getUidCuenta(), ipEjecucion, usuario);

                if (permisosAsignados != null && !permisosAsignados.isEmpty()) {
                    break;
                }
            }
        }
        boolean actualizarAcuse = false;
        Integer idProcesoElectoral = creacionCuentaAspirante.getIdProcesoElectoral();
        DTOCreacionCuentasId idCreacionCuenta = creacionCuentaAspirante.getId();

        if (permisosAsignados != null && !permisosAsignados.isEmpty()) {
            creacionCuentaAspirante.setEstatusCuenta(Constantes.ESTATUS_CUENTA_CREADA);
            creacionCuentaAspirante.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_ASIGNADOS);
            actualizarAcuse = true;
        } else {
            creacionCuentaAspirante.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_SIN_ASIGNAR);
        }

        asignarUsuarioFecha(creacionCuentaAspirante);
        asGestionCuentas.guardarOActalizarCreacionCuenta(creacionCuentaAspirante);
        if (actualizarAcuse) {
            actualizarAcuseCuenta(idProcesoElectoral, idCreacionCuenta.getIdDetalleProceso(),
                    idCreacionCuenta.getIdParticipacion(), idCreacionCuenta.getIdAspirante(), idPuesto);
        }

    }

    private void actualizarAcuseCuenta(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspirante, Integer idPuesto) {

        try {
            String pathGluster = pathGlusterSistDECEYEC.endsWith("/") ? pathGlusterSistDECEYEC
                    : pathGlusterSistDECEYEC + "/";

            StringBuilder pathAcuseActual = new StringBuilder();
            String nuevoPuestoAsp = Boolean.TRUE.equals((boValidacionesCuentas.isPuestoSE(idPuesto))) ? SE : CAE;
            String anteriorPuestoAsp = Boolean.TRUE.equals((boValidacionesCuentas.isPuestoSE(idPuesto))) ? CAE : SE;

            pathAcuseActual.append(pathGluster);
            pathAcuseActual.append(carpetaSupycap);
            pathAcuseActual.append(File.separator);
            pathAcuseActual.append(idProcesoElectoral);
            pathAcuseActual.append(File.separator);
            pathAcuseActual.append(idDetalleProceso);
            pathAcuseActual.append(File.separator);
            pathAcuseActual.append(WEB);
            pathAcuseActual.append(File.separator);
            pathAcuseActual.append(ACUSES_CUENTAS);
            pathAcuseActual.append(File.separator);
            StringBuilder pathNuevoAcuse = new StringBuilder(pathAcuseActual);
            pathAcuseActual.append(anteriorPuestoAsp);
            pathAcuseActual.append(idParticipacion);
            pathAcuseActual.append("_");
            pathAcuseActual.append(idAspirante);
            pathAcuseActual.append(PDF);

            pathNuevoAcuse.append(nuevoPuestoAsp);
            pathNuevoAcuse.append(idParticipacion);
            pathNuevoAcuse.append("_");
            pathNuevoAcuse.append(idAspirante);
            pathNuevoAcuse.append(PDF);

            File acuseActual = new File(pathAcuseActual.toString());
            File acuseNuevo = new File(pathNuevoAcuse.toString());
            acuseActual.renameTo(acuseNuevo);

        } catch (Exception e) {
            log.error("ERROR BSDGestionCuentasImpl - actualizarAcuseCuenta: ", e);
        }

    }

    private void asignarUsuarioFecha(DTOCreacionCuentas creacionCuentaAspirante) {
        creacionCuentaAspirante.setIpUsuario(ipEjecucion);
        creacionCuentaAspirante.setFechaHora(new Date());
        creacionCuentaAspirante.setUsuario(usuario);
    }

}
