package mx.ine.sustseycae.as.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mx.ine.sustseycae.as.ASGestionCuentasInterface;
import mx.ine.sustseycae.bo.BOValidacionesCuentasInterface;
import mx.ine.sustseycae.dto.DTOCPermisosCta;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin.Usuario;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentasId;
import mx.ine.sustseycae.dto.db.DTOGruposDefaultSistema;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.repositories.RepoAUSInterface;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPACEtiquetas;
import mx.ine.sustseycae.repositories.RepoJPACParametros;
import mx.ine.sustseycae.repositories.RepoJPACParametrosCtasSeCae;
import mx.ine.sustseycae.repositories.RepoJPACreacionCuentas;
import mx.ine.sustseycae.repositories.RepoJPAGruposDefaultSistema;
import mx.ine.sustseycae.repositories.RepoJPAParticipacionGeografica;
import mx.ine.sustseycae.util.Constantes;

@Service("asGestionCuentas")
@Scope("prototype")
public class ASGestionCuentasImpl implements ASGestionCuentasInterface {

    private static final Log log = LogFactory.getLog(ASGestionCuentasImpl.class);

    private static final String CAE = "cae_";
    private static final String SE = "se_";
    private static final String ETIQUETA_QUARTZ_AMBITO = "F";
    private static final String TIPO_CUENTA_EXTERNA = "E";
    private static final Integer TIPO_SO_SE = 7;
    private static final Integer TIPO_SO_CAE = 6;
    private static final String DOMINIO_CORREO = "@gmail.com";

    @Autowired
    private RepoJPAParticipacionGeografica repoJPAParticipacionGeografica;

    @Autowired
    private RepoJPACreacionCuentas repoJPACreacionCuentas;

    @Autowired
    private RepoJPAGruposDefaultSistema repoJPAGruposDefaultSistema;

    @Autowired
    @Qualifier("repoAUS")
    private RepoAUSInterface repoAUS;

    @Autowired
    @Qualifier("boValidacionesCuentas")
    private BOValidacionesCuentasInterface boValidacionesCuentas;

    @Autowired
    private RepoJPACParametrosCtasSeCae repoJPACParametrosCtasSeCae;

    @Autowired
    private RepoJPACEtiquetas repoJPACEtiquetas;

    @Autowired
    private RepoJPACParametros repoJPACParametros;

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Override
    public Usuario crearCuenta(DTOAspirantes aspirante, DTOCreacionCuentas cuentaPendiente) {

        DTOParticipacionGeografica participacionGeo = repoJPAParticipacionGeografica
                .findById_IdDetalleProcesoAndId_IdParticipacion(aspirante.getIdDetalleProceso(),
                        aspirante.getIdParticipacion());

        JsonObject jsonRequestCuenta = requestDatosCreacionCuenta(aspirante, participacionGeo, cuentaPendiente);

        DTORespuestaWSAdmin.Usuario usuarioAUS = new DTORespuestaWSAdmin.Usuario();
        try {
            DTORespuestaWSAdmin response = repoAUS.crearCuenta(jsonRequestCuenta);

            if (!response.getCodigoRespuesta().equals(1)) {
                usuarioAUS.setCodigoError(response.getCodigoError());
                usuarioAUS.setMensajeError(response.getDescripcionError() + " - " + response.getDetalle());
                throw new Exception("ERROR ASGestionCuentasImpl - crearCuenta - CodigoError:" + response.toString()
                        + " - request: " + jsonRequestCuenta.toString());
            }

            if (response.getUsuarios() == null || response.getUsuarios().isEmpty()) {
                usuarioAUS.setCodigoError(101);
                usuarioAUS.setMensajeError("El servicio de creación de cuenta no devolvió al usuario creado");
                throw new Exception("ERROR ASGestionCuentasImpl - crearCuenta - usuario creado " + response.toString()
                        + " - request: " + jsonRequestCuenta.toString());
            }

            usuarioAUS = response.getUsuarios().get(0);
            usuarioAUS.setMensajeError("");

        } catch (Exception e) {
            log.error("ERROR ASGestionCuentasImpl - crearCuenta: ", e);
            if (usuarioAUS.getCodigoError() == null) {
                usuarioAUS.setCodigoError(100);
            }
        }

        return usuarioAUS;
    }

    @Override
    public boolean eliminarCuenta(DTOCreacionCuentas cuentaPendiente, String usuario, String ipUsuario,
            Integer ldapCuentas) {

        try {
            JsonObject requestEliminar = requestDatosEliminarCuenta(cuentaPendiente, usuario, ipUsuario, ldapCuentas);
            DTORespuestaWSAdmin response = repoAUS.eliminarCuenta(requestEliminar);

            if (!response.getCodigoRespuesta().equals(1)) {
                throw new Exception("ERROR ASGestionCuentasImpl - eliminarCuenta - CodigoError: "
                        + requestEliminar.toString() + ":" + response.toString());
            }

            if (response.getCuentasProcesadas() == null || response.getCuentasProcesadas().isEmpty()) {
                throw new Exception(" ERROR ASGestionCuentasImpl - eliminarCuenta - CuentasProcesadas: "
                        + requestEliminar.toString() + ":" + response.toString());
            }
            return true;
        } catch (Exception e) {
            log.error("ERROR ASGestionCuentasImpl - eliminarCuenta: ", e);
            return false;
        }
    }

    private JsonObject requestDatosCreacionCuenta(DTOAspirantes aspirante, DTOParticipacionGeografica participacionGeo,
            DTOCreacionCuentas cuentaPendiente) {
        Integer idTipoSO = boValidacionesCuentas.isPuestoSE(aspirante.getIdPuesto()) ? TIPO_SO_SE : TIPO_SO_CAE;

        JsonObject jsonRequest = new JsonObject();
        JsonObject persona = new JsonObject();
        persona.addProperty("nombre", aspirante.getNombre());
        persona.addProperty("primerApellido", aspirante.getApellidoPaterno());
        persona.addProperty("segundoApellido", aspirante.getApellidoMaterno());
        persona.addProperty("mail", aspirante.getCorreoCtaCreada());
        persona.addProperty("telefono", aspirante.getTelefonoCtaCreada());

        JsonArray atributos = new JsonArray();
        atributos.add(makeJsonAtributo("idEstado", Arrays.asList(participacionGeo.getIdEstado().toString())));
        atributos.add(makeJsonAtributo("ambito", Arrays.asList(ETIQUETA_QUARTZ_AMBITO)));
        atributos.add(makeJsonAtributo("idMunicipio", Arrays.asList("0")));
        atributos.add(makeJsonAtributo("idDistrito", Arrays.asList(participacionGeo.getIdDistrito().toString())));
        atributos.add(makeJsonAtributo("idSO", Arrays.asList(aspirante.getIdAspirante().toString())));
        atributos.add(makeJsonAtributo("idTipoSO", Arrays.asList(idTipoSO.toString())));
        atributos.add(makeJsonAtributo("idEntidadSO", Arrays.asList(participacionGeo.getIdEstado().toString())));
        atributos.add(makeJsonAtributo("idRegiduria", Arrays.asList("0")));
        atributos.add(
                makeJsonAtributo("idProcesoElectoral", Arrays.asList(aspirante.getIdProcesoElectoral().toString())));
        atributos.add(makeJsonAtributo("idDetalle", Arrays.asList(aspirante.getIdDetalleProceso().toString())));
        atributos.add(makeJsonAtributo("objectClass", Arrays.asList("account", "posixAccount", "IFEAccount")));

        JsonArray attrCtaLdapExt = new JsonArray();

        StringBuilder nombreAsociacion = new StringBuilder();
        nombreAsociacion.append(aspirante.getApellidoPaterno() != null ? aspirante.getApellidoPaterno() : "")
                .append(" ");
        nombreAsociacion.append(aspirante.getApellidoMaterno() != null ? aspirante.getApellidoMaterno() : "")
                .append(" ");
        nombreAsociacion.append(aspirante.getNombre() != null ? aspirante.getNombre() : "");

        attrCtaLdapExt.add(makeJsonAtributo("nombreAsociacion", Arrays.asList(nombreAsociacion.toString())));
        attrCtaLdapExt.add(makeJsonAtributo("tipoCuenta", Arrays.asList(TIPO_CUENTA_EXTERNA)));

        jsonRequest.add("persona", persona);
        jsonRequest.add("atributos", atributos);
        jsonRequest.add("attrCtaLdapExt", attrCtaLdapExt);
        jsonRequest.addProperty("idSistema", Constantes.ID_SISTEMA);
        jsonRequest.addProperty("usuario", cuentaPendiente.getUsuario());
        jsonRequest.addProperty("ip", cuentaPendiente.getIpUsuario());
        jsonRequest.addProperty("debugMode", true);

        return jsonRequest;
    }

    private JsonObject requestDatosEliminarCuenta(DTOCreacionCuentas cuentaPendiente, String usuario, String ipUsuario,
            Integer ldapCuentas) {
        JsonObject jsonRequest = new JsonObject();
        jsonRequest.addProperty("grupoLdap", ldapCuentas);
        JsonArray uidsJson = new JsonArray();
        uidsJson.add(cuentaPendiente.getUidCuenta());
        jsonRequest.add("listaUids", uidsJson);
        jsonRequest.addProperty("idSistema", Constantes.ID_SISTEMA);
        jsonRequest.addProperty("usuario", usuario);
        jsonRequest.addProperty("ip", ipUsuario);
        jsonRequest.addProperty("debugMode", true);
        return jsonRequest;
    }

    private JsonObject makeJsonAtributo(String nombre, List<String> valores) {
        JsonObject json = new JsonObject();
        json.addProperty("nombre", nombre);
        JsonArray jsonValores = new JsonArray();
        valores.stream().forEach(jsonValores::add);
        json.add("valores", jsonValores);
        return json;
    }

    private List<JsonObject> requestDatosPermisos(List<DTOGruposDefaultSistema> permisos, String usuario,
            String ipUsuario) {
        List<JsonObject> requests = new ArrayList<>();

        for (DTOGruposDefaultSistema permiso : permisos) {
            JsonObject jsonRequest = new JsonObject();
            jsonRequest.addProperty("grupo", permiso.getGrupoDefault());
            jsonRequest.addProperty("idSistema", permiso.getId().getIdSistema());
            jsonRequest.addProperty("usuario", usuario);
            jsonRequest.addProperty("ip", ipUsuario);
            jsonRequest.addProperty("debugMode", true);
            jsonRequest.addProperty("tipoCuenta", "E");
            jsonRequest.addProperty("rol", permiso.getDescripcionRol());
            jsonRequest.addProperty("nombreSistema", permiso.getNombreSistema());
            requests.add(jsonRequest);
        }

        return requests;
    }

    @Override
    public DTOCreacionCuentas obtenerCreacionCtaAspirante(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante) {
        return repoJPACreacionCuentas
                .findByIdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);
    }

    @Override
    public String obtenerDescripcionParametroCtasSeCae(Integer idParametro) {
        return repoJPACParametrosCtasSeCae.obtenerEtiquetaAcuse(idParametro);
    }

    @Override
    public List<DTOCParametros> obtenerCParametros(Integer idProcesoElectoral, Integer idDetalleProceso,
            Set<Integer> idParametros) {
        List<DTOCParametros> listEtiquetas = repoJPACParametros
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParametroIn(idProcesoElectoral,
                        idDetalleProceso, idParametros);
        if (listEtiquetas != null && !listEtiquetas.isEmpty()) {
            return listEtiquetas;
        } else {
            return repoJPACParametros.findById_IdProcesoElectoralAndId_IdParametroIn(idProcesoElectoral, idParametros);
        }
    }

    @Override
    public DTOParticipacionGeografica obtenerParticipacionGeo(Integer idDetalleProceso, Integer idParticipacion) {
        return repoJPAParticipacionGeografica.findById_IdDetalleProcesoAndId_IdParticipacion(idDetalleProceso,
                idParticipacion);
    }

    @Override
    public DTORespuestaWSAdmin.Usuario obtenerUsuarioPorMail(Integer grupoLdap, String mail) {
        DTORespuestaWSAdmin.Usuario usuarioAUS = new DTORespuestaWSAdmin.Usuario();

        try {
            JsonObject request = new JsonObject();
            request.addProperty("grupoLdap", grupoLdap);
            request.addProperty("mail", mail);
            DTORespuestaWSAdmin response = repoAUS.obtenerUsuarioPorMail(request);

            if (!response.getCodigoRespuesta().equals(1)) {
                usuarioAUS.setCodigoError(response.getCodigoError());
                usuarioAUS.setMensajeError(response.getDescripcionError() + " - " + response.getDetalle());
                throw new Exception("ERROR ASGestionCuentasImpl - obtenerUsuarioPorMail - CodigoErrorcódigo:"
                        + response.toString() + " - request: " + request.toString());
            }

            if (response.getUsuarios() == null || response.getUsuarios().isEmpty()) {
                usuarioAUS.setCodigoError(101);
                usuarioAUS.setMensajeError("El servicio de consultar usuario por mail no devolvió al usuario");
                throw new Exception("ERROR ASGestionCuentasImpl - obtenerUsuarioPorMail - sin Usuario: "
                        + response.toString() + " - request: " + request.toString());
            }

            usuarioAUS = response.getUsuarios().get(0);
            usuarioAUS.setMensajeError("");

        } catch (Exception e) {
            log.error("ERROR ASGestionCuentasImpl - obtenerUsuarioPorMail." + " request - {grupoLdap:" + grupoLdap
                    + ", mail: " + mail + "}", e);
            if (usuarioAUS.getCodigoError() == null) {
                usuarioAUS.setCodigoError(100);
            }
        }

        return usuarioAUS;

    }

    @Override
    public Integer obtenerNumeroARE(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAre) {
        return repoJPACreacionCuentas.getNumeroAre(idProcesoElectoral, idDetalleProceso, idParticipacion, idAre);
    }

    @Override
    public Integer obtenerNumeroZORE(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idZore) {
        return repoJPACreacionCuentas.getNumeroZore(idProcesoElectoral, idDetalleProceso, idParticipacion, idZore);
    }

    @Override
    public void guardarOActalizarCreacionCuenta(DTOCreacionCuentas creacionCuenta) {
        repoJPACreacionCuentas.saveAndFlush(creacionCuenta);
    }

    @Override
    public String creaEstructuraCorreoSistema(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idAspirante, Integer idPuesto) {
        StringBuilder correo = new StringBuilder();
        correo.append(Boolean.TRUE.equals((boValidacionesCuentas.isPuestoSE(idPuesto))) ? SE : CAE);
        correo.append(idProceso);
        correo.append("_");
        correo.append(idDetalle);
        correo.append("_");
        correo.append(idParticipacion);
        correo.append("_");
        correo.append(idAspirante);
        correo.append(DOMINIO_CORREO);
        return correo.toString();
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public void actualizarAspiranteUidServicoUsado(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante, String uidCuenta, Integer serviocioUsado, String usuario,
            String ipUsuario) {
        repoJPACreacionCuentas.updateUidCuentaAndServicioUsadoAspirante(uidCuenta, serviocioUsado, usuario, ipUsuario,
                new Date(), Constantes.ID_SISTEMA, idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);

    }

    @Override
    public List<DTOGruposDefaultSistema> obtenerPermisosCuenta(Integer idActor) {
        return repoJPAGruposDefaultSistema.findById_IdActor(idActor);
    }

    @Override
    public List<DTOCPermisosCta> asignarPermisosCuenta(List<DTOGruposDefaultSistema> permisos, String uid,
            String ipEjecucion, String usuario) {
        List<JsonObject> requestPermisos = requestDatosPermisos(permisos, usuario, ipEjecucion);
        List<DTOCPermisosCta> permisosAgregados = repoAUS.asignarPermisosCuenta(requestPermisos, uid);
        return permisosAgregados;
    }

    @Override
    public DTOCEtiquetas obtenerEtiquetaPorIdEtiquetaProceso(Integer idProcesoElectoral, Integer idEtiqueta) {
        return repoJPACEtiquetas.findTopById_IdProcesoElectoralAndId_IdEtiqueta(idProcesoElectoral, idEtiqueta);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public void eliminarRegistroCreacionCuenta(DTOCreacionCuentasId IdCreacionCuenta) {
        repoJPACreacionCuentas.deleteById(IdCreacionCuenta);
    }

    @Override
    public DTOAspirantes obtenerAspirante(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspirante) {
        return repoJPAAspirantes
                .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                        idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);
    }

}
