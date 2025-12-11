package mx.ine.sustseycae.as.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASFiltrosListaReserva;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.repositories.RepoJPAParticipacionGeografica;
import mx.ine.sustseycae.util.Constantes;

@Service("asFiltros")
public class ASFiltrosListaReservaImpl implements ASFiltrosListaReserva {

    private Log log = LogFactory.getLog(ASFiltrosListaReservaImpl.class);

    @Autowired
    private RepoJPAParticipacionGeografica repoJPAParticipacionGeografica;

    @Override
    public ModelGenericResponse obtenerMunicipios(Integer idEstado, Integer idDistrito, Integer idCorte) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
            response.setData(repoJPAParticipacionGeografica.getMunicipios(idEstado, idDistrito, idCorte));
            return response;
        } catch (Exception e) {
            log.error("ERROR ASFiltrosListaReservaImpl -obtenerMunicipios: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener los municipios.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtenerLocalidadesPorMunicipio(Integer idEstado, Integer idMunicipio, Integer idDistrito,
            Integer idCorte, Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
            response.setData(repoJPAParticipacionGeografica.getLocalidadesPorMunicipio(idEstado, idMunicipio, idDistrito, idCorte,
                    idProceso, idDetalle, idParticipacion));
            return response;
        } catch (Exception e) {
            log.error("ERROR ASFiltrosListaReservaImpl -obtenerLocalidadesPorMunicipio: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener las localidades.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtenerSedes(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
            response.setData(repoJPAParticipacionGeografica.getSedes(idProceso, idDetalle, idParticipacion));
            return response;
        } catch (Exception e) {
            log.error("ERROR ASFiltrosListaReservaImpl -obtenerSedes: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener las sedes de reclutamiento.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtenerSecciones(Integer idProceso, Integer idDetalle, Integer idParticipacion) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
            response.setData(repoJPAParticipacionGeografica.getSecciones(idProceso, idDetalle, idParticipacion));
            return response;
        } catch (Exception e) {
            log.error("ERROR ASFiltrosListaReservaImpl -obtenerSecciones: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener las secciones.");
            return response;
        }
    }

}
