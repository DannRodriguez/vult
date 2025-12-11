package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface BSDMenu {

    public ModelGenericResponse obtieneEstadosMultiProceso(Integer idSistema, String ambito);

    public ModelGenericResponse obtieneProcesosDetalleMultiProceso(Integer idSistema, Integer idEstado,
            Integer idDistrito, String ambito);

    public ModelGenericResponse obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema);

    public ModelGenericResponse obtieneParticipacionAndEtapa(Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String ambito, String tipoCapturaSistema);

    public ModelGenericResponse obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema);

    public ModelGenericResponse obtieneEstatusModulo(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo);

    public ModelGenericResponse obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo);
}
