package mx.ine.sustseycae.repositoriesadmin.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.ine.parametrizacion.controller.MenuController;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.request.ModelRequestMenu;
import mx.ine.sustseycae.models.requests.DTORequestMenuWSControl;
import mx.ine.sustseycae.repositoriesadmin.RepoMenuInterface;
import mx.ine.sustseycae.util.Constantes;

@Service("repoMenu")
@Scope("prototype")
public class RepoMenuImpl implements RepoMenuInterface, Serializable {

    @Autowired
    private MenuController menuController;

    @Autowired
    @Qualifier("restClientAdminDECEYEC")
    private RestClient restclientAdmin;

    @Override
    public List<DTOMenu> obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, String grupoSistema) {

        ModelRequestMenu parametros = new ModelRequestMenu();
        parametros.setIdSistema(idSistema);
        parametros.setIdProceso(idProceso);
        parametros.setIdDetalle(idDetalle);
        parametros.setIdEstado(idEstado);
        parametros.setIdDistrito(idDistrito != null ? idDistrito : 0);
        parametros.setIdMunicipio(null);
        parametros.setGrupoSistema(grupoSistema);
        parametros.setJndi(Constantes.JNDI_MENU_SUSTSUPYCAP);
        return menuController.obtieneMenuLateral(parametros);
    }

    @Override
    public String obtieneMenuLateralWS(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema) throws Exception {

        return obtieneMenuANDMenuAccionesWS(idSistema, idProceso, idDetalle, idEstado, idDistrito, grupoSistema,
                Constantes.API_MENU_LATERAL);
    }

    @Override
    public List<DTOMenu> obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, String grupoSistema) {
        ModelRequestMenu parametros = new ModelRequestMenu();
        parametros.setIdSistema(idSistema);
        parametros.setIdProceso(idProceso);
        parametros.setIdDetalle(idDetalle);
        parametros.setIdEstado(idEstado);
        parametros.setIdDistrito(idDistrito != null ? idDistrito : 0);
        parametros.setIdMunicipio(null);
        parametros.setGrupoSistema(grupoSistema);
        parametros.setJndi(Constantes.JNDI_MENU_SUSTSUPYCAP);
        return menuController.obtieneMenuAcciones(parametros);
    }

    @Override
    public String obtieneMenuAccionesWS(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema) throws Exception {
        return obtieneMenuANDMenuAccionesWS(idSistema, idProceso, idDetalle, idEstado, idDistrito, grupoSistema,
                Constantes.API_MENU_ACCIONES);
    }

    private String obtieneMenuANDMenuAccionesWS(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, String grupoSistema, String url) throws Exception {

        DTORequestMenuWSControl dtoWsControl = new DTORequestMenuWSControl(idSistema, idProceso, idDetalle,
                idEstado, idDistrito, null, grupoSistema);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtoWsControl);

        // Crea la peticion al Servicio Web
        String response = restclientAdmin.post().uri(Constantes.WSPARAM_MENU + Constantes.API_MENU + url)
                .contentType(MediaType.APPLICATION_JSON).body(json).retrieve().body(String.class);

        response = response == null ? "" : response.replace("\"subMenus\":", "\"submenusMap\":");
        response = response.replace("\"modulos\":", "\"modulosMap\":");

        return response;
    }

}
