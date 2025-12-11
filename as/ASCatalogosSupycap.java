package mx.ine.sustseycae.as;

import java.util.List;

import mx.ine.sustseycae.dto.db.DTOCCausasVacante;
import mx.ine.sustseycae.models.requests.ModelRequestFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseInfoSustitucion;

public interface ASCatalogosSupycap {

    public List<DTOCCausasVacante> obtenerListaCausasVacante();

    public ModelResponseFechasSustituciones obtenerFechasSustituciones(ModelRequestFechasSustituciones request);

    public ModelGenericResponse obtenerListaSustitutosSupervisores(Integer idDetalleProceso,
            Integer idParticipacion);

    public ModelGenericResponse obtenerListaSustitutosCapacitadores(Integer idDetalleProceso,
            Integer idParticipacion);

    public ModelGenericResponse obtenerAspiranteSustituto(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante);

    public ModelResponseInfoSustitucion obtenerInformacionSustitucion(Integer idProcesoElectoral,
            Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSustituido, Integer tipoCausaVacante,
            Integer idSustitucion);

}
