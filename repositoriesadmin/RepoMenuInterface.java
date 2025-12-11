package mx.ine.sustseycae.repositoriesadmin;

import java.util.List;

import mx.ine.parametrizacion.model.dto.DTOMenu;

public interface RepoMenuInterface {

    /**
     * Método que obtiene lista de menu lateral de amdin INE mediante el jar de
     * parametrización
     *
     * @param idSistema
     * @param idProceso
     * @param idDetalle
     * @param idEstado
     * @param idDistrito
     * @param grupoSistema
     * @return
     */
    public List<DTOMenu> obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema);

    /**
     * Método que obtiene lista de menu lateral de admin DECEYEC mediante el WS
     * de control deceyec
     *
     * @param idSistema
     * @param idProceso
     * @param idDetalle
     * @param idEstado
     * @param idDistrito
     * @param grupoSistema
     * @return
     * @throws Exception
     */
    public String obtieneMenuLateralWS(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema) throws Exception;

    /**
     * Método que obtiene lista del menu acciones de amdin INE mediante el jar
     * de parametrización
     *
     * @param idSistema
     * @param idProceso
     * @param idDetalle
     * @param idEstado
     * @param idDistrito
     * @param grupoSistema
     * @return
     */
    public List<DTOMenu> obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema);

    /**
     * Método que obtiene lista del menu acciones de admin DECEYEC mediante el
     * WS de control deceyec
     *
     * @param idSistema
     * @param idProceso
     * @param idDetalle
     * @param idEstado
     * @param idDistrito
     * @param grupoSistema
     * @return
     * @throws Exception
     */
    public String obtieneMenuAccionesWS(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String grupoSistema) throws Exception;
}
