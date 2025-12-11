package mx.ine.sustseycae.as;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTOAccionModulo;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface ASMenu {

    public ModelGenericResponse obtieneEstadosMultiProceso(Integer idSistema, String ambito);

    public ModelGenericResponse obtieneProcesosDetalleMultiProceso(Integer idSistema, Integer idEstado,
            Integer idDistrito, String ambito);

    public ModelGenericResponse obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema);

    public Integer obtieneParticipacion(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito,
            String ambito);

    public List<DTOMenu> obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema) throws Exception;

    public ModelGenericResponse obtieneEstatusModulo(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo);

    public Integer obtieneEtapaCapacitacion(Integer idProceso, Integer idDetalle);

    public List<DTOAccionModulo> obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo) throws Exception;
}
