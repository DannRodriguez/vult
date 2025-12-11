package mx.ine.sustseycae.as;

import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface ASListaReservaSeInterface {

    public ModelGenericResponse obtenerListaReservaSe(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idPuesto, Integer[] estatus, Integer filtro, Integer idMunicipio, Integer idLocalidad,
            Integer idSede, Integer seccion1, Integer seccion2);

    public ModelGenericResponse actualizarLista(DTOAspirantesId id, Integer estatus, String ip, String user);
}
