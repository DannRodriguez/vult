package mx.ine.sustseycae.bo.impl;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.sustseycae.bo.BOValidacionesCuentasInterface;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentasId;
import mx.ine.sustseycae.helper.impl.HLPCorreoAdminNotificador;
import mx.ine.sustseycae.util.AcuseCuenta;
import mx.ine.sustseycae.util.Constantes;

@Component("boValidacionesCuentas")
@Scope("prototype")
public class BOValidacionesCuentasImpl implements BOValidacionesCuentasInterface {

    private static final Log log = LogFactory.getLog(BOValidacionesCuentasImpl.class);

    @Autowired
    @Qualifier("pathGlusterSistDECEYEC")
    String pathGlusterSistDECEYEC;

    private static final String WEB = "web";
    private static final String ACUSES_CUENTAS = "acusesCuentas";
    private static final String CAE = "cae_";
    private static final String SE = "se_";
    private static final String PDF = ".pdf";

    @Override
    public Boolean isPuestoSE(Integer idPuesto) {
        return idPuesto.equals(Constantes.ID_PUESTO_SE) || idPuesto.equals(Constantes.ID_PUESTO_SE_TMP)
                || idPuesto.equals(Constantes.ID_PUESTO_REC_SE);
    }

    @Override
    public Boolean isPuestoCAE(Integer idPuesto) {
        return idPuesto.equals(Constantes.ID_PUESTO_CAE) || idPuesto.equals(Constantes.ID_PUESTO_CAE_TMP)
                || idPuesto.equals(Constantes.ID_PUESTO_REC_CAE);
    }

    @Override
    public DTOCreacionCuentas aspiranteToDTOCreacionCuentas(DTOAspirantes aspiranteCuentaCrear, Integer idZoreAre,
            Integer numZoreAre, Integer tipoSO, String usuario, String ipUsuario) {

        DTOCreacionCuentas creacionCta = new DTOCreacionCuentas();
        DTOCreacionCuentasId id = new DTOCreacionCuentasId();
        id.setIdDetalleProceso(aspiranteCuentaCrear.getIdDetalleProceso());
        id.setIdParticipacion(aspiranteCuentaCrear.getIdParticipacion());
        id.setIdAspirante(aspiranteCuentaCrear.getIdAspirante());

        creacionCta.setIdProcesoElectoral(aspiranteCuentaCrear.getIdProcesoElectoral());
        creacionCta.setId(id);
        creacionCta.setIdSO(tipoSO);
        creacionCta.setEstatusCuenta(Constantes.ESTATUS_CUENTA_SIN_CREAR);
        creacionCta.setEstatusPermiso(Constantes.ESTATUS_PERMISOS_SIN_ASIGNAR);
        creacionCta.setIdZoreAre(idZoreAre);
        creacionCta.setNumZoreAre(numZoreAre);
        creacionCta.setIdSistema(Constantes.ID_SISTEMA);
        creacionCta.setUsuario(usuario);
        creacionCta.setIpUsuario(ipUsuario);
        creacionCta.setFechaHora(new Date());

        return creacionCta;
    }

    @Override
    public byte[] guardarPdf(DTOAspirantes aspirante, DTORespuestaWSAdmin.Usuario usuario, String urlPoliticasUso,
            String urlCambioContra, String usuarioQueCreaCuenta, Integer idPuesto, Boolean generarBytes,
            String carpetaSupycap) throws Exception {
        try {
            String pathGluster = pathGlusterSistDECEYEC.endsWith("/") ? pathGlusterSistDECEYEC
                    : pathGlusterSistDECEYEC + "/";
            StringBuilder path = new StringBuilder();
            String puestoAsp = (isPuestoSE(idPuesto)) ? SE : CAE;
            path.append(pathGluster);
            path.append(carpetaSupycap);
            path.append(File.separator);
            path.append(aspirante.getIdProcesoElectoral());
            path.append(File.separator);
            path.append(aspirante.getIdDetalleProceso());
            path.append(File.separator);
            path.append(WEB);
            path.append(File.separator);
            path.append(ACUSES_CUENTAS);
            path.append(File.separator);
            path.append(puestoAsp);
            path.append(aspirante.getIdParticipacion());
            path.append("_");
            path.append(aspirante.getIdAspirante());
            path.append(PDF);

            File file = new File(path.toString());

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            Map<String, Set<String>> map = ListaAtributosToMap(usuario.getAtributos());
            String nombreCompleto = map.get("sn").toArray()[0] + " " + map.get("givenName").toArray()[0];

            byte[] datos = AcuseCuenta.generarComprobante(nombreCompleto, usuario.getUid(), usuario.getPassword(),
                    urlPoliticasUso, urlCambioContra, usuario.getPermisosAsignados(), usuarioQueCreaCuenta,
                    carpetaSupycap);
            Files.write(file.toPath(), datos);

            if (!generarBytes) {
                datos = null;
            }

            return datos;

        } catch (Exception e) {
            log.error("ERROR BOValidacionesAsigCargosImpl - guardarPdf: ", e);
            throw new Exception("Error al generar el comprobante de la cuenta " + usuario.getUid() + ".");
        }
    }

    public static Map<String, Set<String>> ListaAtributosToMap(List<DTORespuestaWSAdmin.Atributo> atributos) {
        Map<String, Set<String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (atributos != null) {
            for (DTORespuestaWSAdmin.Atributo attr : atributos) {
                String nombre = attr.getNombre();
                if (nombre != null) {
                    if (map.containsKey(nombre)) {
                        map.get(attr.getNombre()).addAll(attr.getValores());
                    } else {
                        Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                        set.addAll(attr.getValores());
                        map.put(attr.getNombre(), set);
                    }
                }
            }
        }
        return map;
    }

    @Override
    public String getNombreComprobante(Integer estado, Integer idEntorno, Integer areZore, Integer puesto)
            throws Exception {
        StringBuilder cadena = new StringBuilder();
        cadena.append(isPuestoSE(puesto) ? Constantes.ETIQUETA_CARATULA_SE : Constantes.ETIQUETA_CARATULA_CAE);
        cadena.append(normalizaNumero(estado));
        cadena.append(normalizaNumero(idEntorno));
        cadena.append(normalizaNumero(areZore));
        cadena.append(Constantes.ETIQUETA_CARGOS_NOMBRE_COMPROBANTE);
        return cadena.toString();
    }

    public static String normalizaNumero(int num) {
        String val = String.valueOf(num);
        if (val.length() == 1) {
            return "0" + val;
        } else if (val.length() > 1 && val.length() <= 2) {
            return val;
        }
        return val;
    }

    @Override
    public void enviarComprobante(DTOAspirantes aspirante, DTORespuestaWSAdmin.Usuario usuario,
            String correoNotificacion, boolean envioNotificacion, String cuenta, String nombre, String urlPoliticasUso,
            String urlCambioContra, String usuarioQueCreaCuenta, Integer idPuesto, String carpetaSupycap,
            List<DTOCParametros> listParamCorreo, String cuentaNotificacion) throws Exception {
        try {
            byte[] datos = guardarPdf(aspirante, usuario, urlPoliticasUso, urlCambioContra, usuarioQueCreaCuenta,
                    idPuesto, true, carpetaSupycap);

            if (validarParametro(listParamCorreo, Constantes.ID_PARAMETRO_ENVIAR_CORREO)) {
                Map<String, Set<String>> map = ListaAtributosToMap(usuario.getAtributos());
                String nombreCompleto = map.get("sn").toArray()[0] + " " + map.get("givenName").toArray()[0];

                HLPCorreoAdminNotificador notificador = new HLPCorreoAdminNotificador();
                List<String> listaCorreos = new ArrayList<>();

                if (validarParametro(listParamCorreo, Constantes.ID_PARAMETRO_USAR_CORREO_INSTITUCIONAL)) {

                    String[] cuentasInstitucionales = cuentaNotificacion != null ? cuentaNotificacion.split(",") : null;
                    notificador.setCorreos(Arrays.asList(cuentasInstitucionales));
                    listaCorreos = Arrays.asList(cuentasInstitucionales);

                } else {

                    List<String> correo = Arrays.asList(map.get("mail").toArray()[0].toString());
                    notificador.setCorreos(correo);
                    listaCorreos.add(correoNotificacion);

                }
                notificador.setBytes(datos);

                notificador.setNombreComprobante(nombre);
                notificador.envioAcuseConPdf(false, nombreCompleto, usuario.getUid(), pathGlusterSistDECEYEC,
                        carpetaSupycap);

                if (envioNotificacion && correoNotificacion != null && !correoNotificacion.equals("")) {
                    notificador = new HLPCorreoAdminNotificador();

                    byte[] comprobante = AcuseCuenta.generarComprobante(nombreCompleto, usuario.getUid(), null, null,
                            null, usuario.getPermisosAsignados(), usuarioQueCreaCuenta, carpetaSupycap);

                    notificador.setNombreComprobante(nombre);
                    notificador.setCorreos(listaCorreos);
                    notificador.setBytes(comprobante);
                    notificador.envioNotificacionConPdf(false, carpetaSupycap);
                }
            }

        } catch (Exception e) {
            log.error("ERROR BOValidacionesAsigCargosImpl - enviarComprobante[" + usuario.toString() + "]", e);
            this.guardarPdf(aspirante, usuario, urlPoliticasUso, urlCambioContra, usuarioQueCreaCuenta, idPuesto, false,
                    carpetaSupycap);
            throw new Exception(Constantes.MENSAJE_ADMIN_USUARIOS_ERROR_COMPROBANTE + " " + usuario.getUid() + ".");

        }

    }

    private boolean validarParametro(List<DTOCParametros> listParamCorreo, Integer idParametro) {
        if (listParamCorreo == null || listParamCorreo.isEmpty()) {
            return false;
        } else {
            Optional<DTOCParametros> resultado = listParamCorreo.stream()
                    .filter(x -> x.getId().getIdParametro().equals(idParametro)).findFirst();
            return resultado.isPresent() && resultado.get().getValorParametro().equals(1);
        }
    }

}
