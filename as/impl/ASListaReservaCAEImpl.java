package mx.ine.sustseycae.as.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASListaReservaCAEInterface;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.util.Constantes;

@Service("asListaReservaCAE")
@Scope("prototype")
public class ASListaReservaCAEImpl implements ASListaReservaCAEInterface {

    private Log log = LogFactory.getLog(ASListaReservaCAEImpl.class);

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Override
    public ModelGenericResponse obtenerListaReservaCAE(ModelRequestListaResrvervaCAE model) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
            response.setData(repoJPAAspirantes.obtenerListaReservaCAE(model.getIdProceso(),
                    model.getIdDetalle(), model.getIdParticipacion(), model.getIdPuesto(), model.getEstatus(),
                    model.getFiltro(), model.getIdMunicipio(), model.getIdLocalidad(), model.getIdSedeReclutamiento(),
                    model.getSeccion1(), model.getSeccion2()));

        } catch (Exception e) {
            log.error("ERROR ASListaReservaCAEImpl -obtenerListaReservaCAE: ", e);
            response.setCode(Constantes.CODIGO_500);
            response.setCausa(e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public ModelGenericResponse guardarCambiosListaReservaCAE(VOListaReservaCAE aspirante) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            repoJPAAspirantes.updateListaReservaCAEAspirantes(aspirante.getIdProceso(), aspirante.getIdDetalle(),
                    aspirante.getIdParticipacion(), aspirante.getIdAspirante(), aspirante.getEstatusListaReserva(),
                    aspirante.getObservaciones());

            repoJPAAspirantes.updateListaReservaCAEEvaCurricular(aspirante.getIdProceso(), aspirante.getIdDetalle(),
                    aspirante.getIdParticipacion(), aspirante.getIdAspirante(), aspirante.getRepresentantePP(),
                    aspirante.getMilitantePP());

            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setStatus(Constantes.ESTATUS_EXITO);
        } catch (Exception e) {
            log.error("ERROR ASListaReservaCAEImpl -guardarCambiosListaReservaCAE: ", e);
            response.setCode(Constantes.CODIGO_500);
            response.setCausa(e.getMessage());
        }
        return response;
    }
}
