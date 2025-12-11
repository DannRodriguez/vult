package mx.ine.sustseycae.as;

import java.util.List;

import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;

public interface ASSustSEyCAEInterface {

    public List<DTOAspirantes> guardarSustitucionSE(DTOAspirantes funcionarioSESustituido,
            DTOAspirantes sustitutoSupervisor, DTOAspirantes sustitutoCapacitador,
            DTORequestSutSEyCAE request) throws Exception;

    public List<DTOAspirantes> guardarSustitucionPendienteSE(VOSustitucionesSupycap sustitucionPrevia,
            DTOAspirantes sustitutoSupervisor, DTOAspirantes sustitutoCapacitador,
            DTORequestSutSEyCAE request) throws Exception;

    public List<DTOAspirantes> guardarSustitucionCAE(DTOAspirantes funcionarioCAESustituido,
            DTOAspirantes sustitutoCapacitador, DTORequestSutSEyCAE request) throws Exception;

    public List<DTOAspirantes> guardarSustitucionPendienteCAE(VOSustitucionesSupycap sustitucionPrevia,
            DTOAspirantes sustitutoCapacitador, DTORequestSutSEyCAE request) throws Exception;

    public DTOAspirantes obtenerAspirantePorPK(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante);

    public List<VOSustitucionesSupycap> obtenerInfoSustitucionPorSustituido(Integer idProcesoElectoral,
            Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSustituido);

    public DTOSustitucionesSupycap obtenerSustitucionPorId(Integer idDetalleProceso, Integer idParticipacion,
            Integer idSustitucion);

    public void modificarSustitucionSEyCAE(DTORequestSutSEyCAE request) throws Exception;

}
