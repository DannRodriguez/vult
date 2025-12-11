package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface BSDListaReservaSeInterface {

    public ModelGenericResponse obtenerListaReservaSe(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idPuesto, Integer[] estatus, Integer filtro, Integer idMunicipio, Integer idLocalidad,
            Integer idSede, Integer seccion1, Integer seccion2);

    public ModelGenericResponse actualizarLista(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idAspirante, Integer estatus, String ip, String user);

}
