package mx.ine.sustseycae.as;

import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface ASFiltrosListaReserva {

    public ModelGenericResponse obtenerMunicipios(Integer idEstado, Integer idDistrito, Integer idCorte);

    public ModelGenericResponse obtenerLocalidadesPorMunicipio(Integer idEstado, Integer idMunicipio, Integer idDistrito,
            Integer idCorte, Integer idProceso, Integer idDetalle, Integer idParticipacion);

    public ModelGenericResponse obtenerSedes(Integer idProceso, Integer idDetalle, Integer idParticipacion);

    public ModelGenericResponse obtenerSecciones(Integer idProceso, Integer idDetalle, Integer idParticipacion);
}
