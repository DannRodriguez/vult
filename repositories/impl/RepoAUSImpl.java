package mx.ine.sustseycae.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import mx.ine.sustseycae.dto.DTOCPermisosCta;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.repositories.RepoAUSInterface;

@Service("repoAUS")
@Scope("prototype")
public class RepoAUSImpl implements RepoAUSInterface {

    private static final Log log = LogFactory.getLog(RepoAUSImpl.class);

    private static final String CREAR_CUENTA_URL = "CrearUsuarioExternoRequest";
    private static final String MODIFICAR_CUENTA_URL = "ModificarUsuarioExternoRequest";
    private static final String ELIMINAR_CUENTA_URL = "EliminarUsuarioExternoRequest";
    private static final String AGREGAR_GRUPO_URL = "agregarUsuariosAGrupoExt";
    private static final String CONSULTAR_MAIL_URL = "consultarUsuarioPorMail";

    @Autowired
    @Qualifier("restClientAUS")
    private RestClient restClientAUS;

    @Override
    public DTORespuestaWSAdmin crearCuenta(JsonObject request) throws Exception {
        String responseStr = consumeAUS(request, CREAR_CUENTA_URL);
        if (responseStr == null || responseStr.isEmpty()) {
            throw new Exception("ERROR RepoAUSImpl - crearCuenta - request:" + request.toString());
        }
        Gson gson = new Gson();
        return gson.fromJson(responseStr, DTORespuestaWSAdmin.class);
    }

    @Override
    public DTORespuestaWSAdmin eliminarCuenta(JsonObject request) throws Exception {
        try {
            String responseStr = consumeAUS(request, ELIMINAR_CUENTA_URL);
            if (responseStr == null || responseStr.isEmpty()) {
                throw new Exception("ERROR RepoAUSImpl - eliminarCuenta - request:" + request.toString());
            }
            Gson gson = new Gson();
            return gson.fromJson(responseStr, DTORespuestaWSAdmin.class);
        } catch (Exception e) {
            log.error("ERROR RepoAUSImpl - eliminarCuenta " + e);
            throw new Exception("ERROR RepoAUSImpl - eliminarCuenta " + e);
        }
    }

    @Override
    public DTORespuestaWSAdmin modificarCuenta(JsonObject request) throws Exception {
        String responseStr = consumeAUS(request, MODIFICAR_CUENTA_URL);
        if (responseStr == null || responseStr.isEmpty()) {
            throw new Exception("ERROR RepoAUSImpl - modificarCuenta - request:" + request.toString());
        }
        Gson gson = new Gson();
        return gson.fromJson(responseStr, DTORespuestaWSAdmin.class);
    }

    @Override
    public List<DTOCPermisosCta> asignarPermisosCuenta(List<JsonObject> requestPermisos, String uid) {
        List<DTOCPermisosCta> permisosAgregados = new ArrayList<>();
        JsonArray uidsJson = new JsonArray();
        uidsJson.add(uid);

        try {
            for (JsonObject request : requestPermisos) {
                String rol = request.get("rol").getAsString();
                String sistema = request.get("nombreSistema").getAsString();
                request.add("uids", uidsJson);
                // se utilizan posteriormente pero no se usan para la petición
                request.remove("rol");
                request.remove("nombreSistema");

                String responseStr = consumeAUS(request, AGREGAR_GRUPO_URL);
                if (responseStr == null || responseStr.isEmpty()) {
                    throw new Exception("ERROR RepoAUSImpl - asignarPermisosCuenta - request:" + request.toString());
                }

                Gson gson = new Gson();
                DTORespuestaWSAdmin response = gson.fromJson(responseStr, DTORespuestaWSAdmin.class);
                if (!response.getCodigoRespuesta().equals(1)) {
                    log.error("ERROR RepoAUSImpl - asignarPermisosCuenta - response: " + responseStr + "/ request:"
                            + request.toString());
                }

                for (DTORespuestaWSAdmin.Atributo atributo : response.getListUids()) {
                    if (atributo.getNombre().equals("uidsProcesados") && !atributo.getValores().isEmpty()) {
                        DTOCPermisosCta permisoCta = new DTOCPermisosCta();
                        permisoCta.setDescripcion(rol);
                        permisoCta.setSistema(sistema);
                        permisosAgregados.add(permisoCta);
                    }
                }

            }

        } catch (Exception e) {
            log.error("ERROR RepoAUSImpl - asignarPermisosCuenta: " + requestPermisos.get(0).toString() + " -", e);
        }
        return permisosAgregados;
    }

    @Override
    public DTORespuestaWSAdmin obtenerUsuarioPorMail(JsonObject request) throws Exception {
        String responseStr = consumeAUS(request, CONSULTAR_MAIL_URL);
        if (responseStr == null || responseStr.isEmpty()) {
            throw new Exception("ERROR RepoAUSImpl - obtenerUsuarioPorMail - request:" + request.toString());
        }
        Gson gson = new Gson();
        return gson.fromJson(responseStr, DTORespuestaWSAdmin.class);
    }

    private String consumeAUS(JsonObject request, String url) {
        return restClientAUS.post().uri(url).contentType(MediaType.APPLICATION_JSON).body(request.toString()).retrieve()
                .body(String.class);
    }

}
