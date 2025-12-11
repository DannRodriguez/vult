package mx.ine.sustseycae.repositoriesadmin.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.ine.parametrizacion.controller.GeograficoController;
import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTODistrito;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.request.ModelRequestDistritos;
import mx.ine.parametrizacion.model.request.ModelRequestEstadosConProcesosVigentes;
import mx.ine.parametrizacion.model.request.ModelRequestProcesos;
import mx.ine.sustseycae.models.requests.DTORequestDistritosWSControl;
import mx.ine.sustseycae.models.requests.DTORequestEstadosWSControl;
import mx.ine.sustseycae.models.requests.DTORequestProcesosWSControl;
import mx.ine.sustseycae.models.responses.ModelResponseDistritosWSControl;
import mx.ine.sustseycae.models.responses.ModelResponseEstadosWSControl;
import mx.ine.sustseycae.models.responses.ModelResponseProcesosWSControl;
import mx.ine.sustseycae.repositoriesadmin.RepoGeograficoInterface;
import mx.ine.sustseycae.util.Constantes;

@Service("repoGeografico")
@Scope("prototype")
public class RepoGeograficoImpl implements RepoGeograficoInterface, Serializable {

    @Autowired
    private GeograficoController geograficoController;

    @Autowired
    @Qualifier("restClientAdminDECEYEC")
    private RestClient restclientAdmin;

    @Override
    public List<DTOEstado> obtieneEstadosConProcesosVigentes(Integer idSistema) {

        ModelRequestEstadosConProcesosVigentes parametros = new ModelRequestEstadosConProcesosVigentes();
        parametros.setIdSistema(idSistema);
        parametros.setIdEstado(0);
        parametros.setJndi(Constantes.JNDI_PARAM_SUSTSUPYCAP);
        return geograficoController.obtieneEstadosConProcesosVigentes(parametros);
    }

    @Override
    public List<DTOEstado> obtieneEstadosConProcesosVigentesWS(Integer idSistema) throws Exception {
        DTORequestEstadosWSControl dtoWsControl = new DTORequestEstadosWSControl();
        dtoWsControl.setIdSistema(idSistema);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtoWsControl);

        // Crea la peticion al Servicio Web
        ModelResponseEstadosWSControl response = restclientAdmin.post()
                .uri(Constantes.WSPARAM_GEOGRAFICO + Constantes.API_GEOGRAFICO
                        + Constantes.API_GEOGRAFICO_ESTADOS_CON_PROCESO_VIG)
                .contentType(MediaType.APPLICATION_JSON).body(json).retrieve()
                .body(ModelResponseEstadosWSControl.class);
        return response == null ? new ArrayList<>() : response.getData();
    }

    @Override
    public List<DTODetalleProceso> obtieneProcesos(Integer idSistema, Integer idEstado, Integer idDistrito,
            String ambito) {
        ModelRequestProcesos parametros = new ModelRequestProcesos();
        parametros.setIdSistema(idSistema);
        parametros.setIdEstado(idEstado);
        parametros.setIdDistrito(idDistrito);
        parametros.setIdMunicipio(null);
        parametros.setAmbitoUsuario(ambito == null ? "F" : ambito);
        parametros.setVigente("A");
        parametros.setJndi(Constantes.JNDI_PARAM_SUSTSUPYCAP);
        return geograficoController.obtieneProcesos(parametros);
    }

    @Override
    public List<DTODetalleProceso> obtieneProcesosWS(Integer idSistema, Integer idEstado, Integer idDistrito,
            String ambito) throws JacksonException {

        DTORequestProcesosWSControl dtoWsControl = new DTORequestProcesosWSControl(idSistema, idEstado,
                idDistrito, ambito == null ? "F" : ambito, "A");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtoWsControl);

        // Crea la peticion al Servicio Web
        ModelResponseProcesosWSControl response = restclientAdmin.post()
                .uri(Constantes.WSPARAM_GEOGRAFICO + Constantes.API_GEOGRAFICO
                        + Constantes.API_GEOGRAFICO_PROCESOS)
                .contentType(MediaType.APPLICATION_JSON).body(json).retrieve()
                .body(ModelResponseProcesosWSControl.class);

        return response == null ? new ArrayList<>() : response.getData();
    }

    @Override
    public List<DTODistrito> obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema) {
        ModelRequestDistritos parametros = new ModelRequestDistritos();
        parametros.setIdSistema(idSistema);
        parametros.setIdProceso(idProceso);
        parametros.setIdDetalle(idDetalle);
        parametros.setIdEstado(idEstado);
        parametros.setIdDistrito(idDistrito);
        parametros.setJndi(Constantes.JNDI_PARAM_SUSTSUPYCAP);

        return geograficoController.obtieneDistritosFederales(parametros);
    }

    @Override
    public List<DTODistrito> obtieneDistritosWS(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema) throws JacksonException {

        DTORequestDistritosWSControl dtoWsControl = new DTORequestDistritosWSControl(idSistema, idProceso,
                idDetalle, idEstado, idDistrito);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtoWsControl);

        // Crea la peticion al Servicio Web
        ModelResponseDistritosWSControl response = restclientAdmin.post()
                .uri(Constantes.WSPARAM_GEOGRAFICO + Constantes.API_GEOGRAFICO
                        + Constantes.API_GEOGRAFICO_DISTRITOS_FEDERALES)
                .contentType(MediaType.APPLICATION_JSON).body(json).retrieve()
                .body(ModelResponseDistritosWSControl.class);

        return response == null ? new ArrayList<>() : response.getData();
    }

}
