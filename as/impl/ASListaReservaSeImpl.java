package mx.ine.sustseycae.as.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASListaReservaSeInterface;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.util.Constantes;

@Service("asListaReservaSe")
public class ASListaReservaSeImpl implements ASListaReservaSeInterface {

    private static final Logger logger = Logger.getLogger(ASListaReservaSeImpl.class);

    @Autowired
    private RepoJPAAspirantes repoJPAAspirantes;

    @Override
    public ModelGenericResponse obtenerListaReservaSe(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idPuesto, Integer[] estatus, Integer filtro, Integer idMunicipio, Integer idLocalidad,
            Integer idSede, Integer seccion1, Integer seccion2) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
            ArrayList<Integer> estatusArray = new ArrayList<>();
            estatusArray.addAll(Arrays.asList(estatus));
            response.setData(repoJPAAspirantes.obtenerListaReservaSe(idProceso, idDetalle, idParticipacion, idPuesto,
                    estatusArray, filtro, idMunicipio, idLocalidad, idSede, seccion1, seccion2));
        } catch (Exception e) {
            logger.error("ERROR ASListaReservaSeImpl -obtenerListaReservaSe: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener la lista de reserva de los SE.");
        }
        return response;
    }

    @Override
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
        Exception.class})
    public ModelGenericResponse actualizarLista(DTOAspirantesId id, Integer estatus, String ip, String user) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            DTOAspirantes aspirante = repoJPAAspirantes
                    .findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
                            id.getIdProcesoElectoral(), id.getIdDetalleProceso(), id.getIdParticipacion(),
                            id.getIdAspirante());

            aspirante.setEstatusListaReserva(estatus);
            aspirante.setIpUsuario(ip);
            aspirante.setUsuario(user);
            aspirante.setFechaHora(new Date());

            repoJPAAspirantes.save(aspirante);

            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
        } catch (Exception e) {
            logger.error("ERROR ASListaReservaSeImpl -actualizarLista: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al actualizar el estatus del aspirante.");
        }
        return response;
    }

}
