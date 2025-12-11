package mx.ine.sustseycae.bsd.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASListaReservaSeInterface;
import mx.ine.sustseycae.bsd.BSDListaReservaSeInterface;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Service("bsdListaReservaSe")
@Scope("prototype")
public class BSDListaReservaSeImpl implements BSDListaReservaSeInterface {

    @Autowired
    private ASListaReservaSeInterface asListaReservaSe;

    @Override
    public ModelGenericResponse obtenerListaReservaSe(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idPuesto, Integer[] estatus, Integer filtro, Integer idMunicipio, Integer idLocalidad,
            Integer idSede, Integer seccion1, Integer seccion2) {
        return asListaReservaSe.obtenerListaReservaSe(idProceso, idDetalle, idParticipacion, idPuesto, estatus, filtro,
                idMunicipio, idLocalidad, idSede, seccion1, seccion2);
    }

    @Override
    public ModelGenericResponse actualizarLista(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idAspirante, Integer estatus, String ip, String user) {
        DTOAspirantesId id = new DTOAspirantesId();
        id.setIdProcesoElectoral(idProceso);
        id.setIdDetalleProceso(idDetalle);
        id.setIdParticipacion(idParticipacion);
        id.setIdAspirante(idAspirante);

        return asListaReservaSe.actualizarLista(id, estatus, ip, user);
    }

}
