package mx.ine.sustseycae.repositoriesadmin;

import java.util.List;

import com.fasterxml.jackson.core.JacksonException;

import mx.ine.parametrizacion.model.dto.DTODetalleProceso;
import mx.ine.parametrizacion.model.dto.DTODistrito;
import mx.ine.parametrizacion.model.dto.DTOEstado;

public interface RepoGeograficoInterface {

    /**
     * Método que obtiene los estados con procesos vigentes de amdin INE
     * mediante el jar de parametrización
     *
     * @param idSistema
     * @return
     */
    public List<DTOEstado> obtieneEstadosConProcesosVigentes(Integer idSistema);

    /**
     * Método que obtiene los estados con procesos vigentes de amdin DECEYEC
     * mediante el WS de control deceyec
     *
     * @param idSistema
     * @return
     * @throws Exception
     */
    public List<DTOEstado> obtieneEstadosConProcesosVigentesWS(Integer idSistema) throws Exception;

    /**
     * Método que obtiene los procesos de amdin INE mediante el jar de
     * parametrización
     *
     * @param idSistema
     * @param idEstado
     * @param idDistrito
     * @param ambito
     * @return
     */
    public List<DTODetalleProceso> obtieneProcesos(Integer idSistema, Integer idEstado, Integer idDistrito,
            String ambito);

    /**
     * Método que obtiene los procesos de amdin DECEYEC mediante el WS de
     * control deceyec
     *
     * @param idSistema
     * @param idEstado
     * @param idDistrito
     * @param ambito
     * @return
     * @throws JacksonException
     */
    public List<DTODetalleProceso> obtieneProcesosWS(Integer idSistema, Integer idEstado, Integer idDistrito,
            String ambito) throws JacksonException;

    /**
     * Método que obtiene los distritos federales de amdin INE mediante el jar
     * de parametrización
     *
     * @param idEstado
     * @param idProceso
     * @param idDetalle
     * @param idDistrito
     * @param idSistema
     * @return
     */
    public List<DTODistrito> obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema);

    /**
     * Método que obtiene los distritos federales de amdin DECEYEC mediante el
     * WS de control deceyec
     *
     * @param idEstado
     * @param idProceso
     * @param idDetalle
     * @param idDistrito
     * @param idSistema
     * @return
     * @throws JacksonException
     */
    public List<DTODistrito> obtieneDistritosWS(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema) throws JacksonException;

}
