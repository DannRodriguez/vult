package mx.ine.sustseycae.as.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.ine.parametrizacion.controller.EstatusController;
import mx.ine.parametrizacion.model.dto.DTOAccionModulo;
import mx.ine.parametrizacion.model.dto.DTOEstado;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.request.ModelRequestModulo;
import mx.ine.sustseycae.as.ASMenu;
import mx.ine.sustseycae.dto.db.DTOCFechas;
import mx.ine.sustseycae.helper.impl.HLPMenuWS;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.repositories.RepoJPACFechas;
import mx.ine.sustseycae.repositories.RepoJPACParametros;
import mx.ine.sustseycae.repositories.RepoJPAParticipacionGeografica;
import mx.ine.sustseycae.repositoriesadmin.RepoGeograficoInterface;
import mx.ine.sustseycae.repositoriesadmin.RepoMenuInterface;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Service("asMenu")
public class ASMenuImpl implements ASMenu {

    @Autowired
    private EstatusController estatusController;

    @Autowired
    private RepoJPAParticipacionGeografica repoJPAParticipacionGeografica;

    @Autowired
    private RepoJPACFechas repoJPACFechas;

    @Autowired
    @Qualifier("repoGeografico")
    private RepoGeograficoInterface repoGeografico;

    @Autowired
    @Qualifier("repoMenu")
    private RepoMenuInterface repoMenu;

    @Autowired
    private RepoJPACParametros reporJPAParametros;

    private static final Logger logger = Logger.getLogger(ASMenuImpl.class);

    @Override
    public ModelGenericResponse obtieneEstadosMultiProceso(Integer idSistema, String ambito) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            List<DTOEstado> estados;
            if (obtenerParametroUsarWS(0, 0, 0, 0) == 1) {
                estados = repoGeografico.obtieneEstadosConProcesosVigentesWS(idSistema);

            } else {
                estados = repoGeografico.obtieneEstadosConProcesosVigentes(idSistema);
            }

            if (ambito != null && ApplicationUtil.isRolOC(ambito) && estados != null && !estados.isEmpty()
                    && estados.get(0).getIdEstado() != null && !estados.get(0).getIdEstado().equals(0)) {
                estados.add(0, new DTOEstado(0, "OFICINAS CENTRALES"));
            }
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
            response.setData(estados);
            return response;
        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneEstadosMultiProceso: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener los estados.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtieneProcesosDetalleMultiProceso(Integer idSistema, Integer idEstado,
            Integer idDistrito, String ambito) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);

            if (obtenerParametroUsarWS(0, 0, 0, 0) == 1) {
                response.setData(repoGeografico.obtieneProcesosWS(idSistema, idEstado, idDistrito, ambito));

            } else {
                response.setData(repoGeografico.obtieneProcesos(idSistema, idEstado, idDistrito, ambito));
            }
            return response;
        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneProcesosDetalleMultiProceso: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener los procesos electorales.");
            return response;
        }

    }

    @Override
    public ModelGenericResponse obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
            if (obtenerParametroUsarWS(0, 0, 0, 0) == 1) {

                response.setData(repoGeografico.obtieneDistritosWS(idEstado, idProceso, idDetalle, idDistrito,
                        idSistema));

            } else {
                response.setData(
                        repoGeografico.obtieneDistritos(idEstado, idProceso, idDetalle, idDistrito, idSistema));
            }
            return response;
        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneDistritos: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener los distritos.");
            return response;
        }
    }

    @Override
    public Integer obtieneParticipacion(Integer idProceso, Integer idDetalle, Integer idEstado, Integer idDistrito,
            String ambito) {
        return repoJPAParticipacionGeografica.getParticipacion(idProceso, idDetalle, idEstado, idDistrito, ambito);
    }

    @Override
    public List<DTOMenu> obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, Integer idMunicipio, String grupoSistema) throws Exception {
        try {
            if (obtenerParametroUsarWS(0, 0, 0, 0) == 1) {

                return HLPMenuWS.mapperMenuLateralWS(repoMenu.obtieneMenuLateralWS(idSistema, idProceso, idDetalle,
                        idEstado, idDistrito, grupoSistema));

            } else {
                return repoMenu.obtieneMenuLateral(idSistema, idProceso, idDetalle, idEstado, idDistrito, grupoSistema);
            }

        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneMenuLateral: ", e);
            throw e;
        }

    }

    @Override
    public ModelGenericResponse obtieneEstatusModulo(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            ModelRequestModulo parametros = new ModelRequestModulo();
            parametros.setIdSistema(idSistema);
            parametros.setIdProceso(idProceso);
            parametros.setIdDetalle(idDetalle);
            parametros.setIdEstado(idEstado);
            parametros.setIdDistrito(idDistrito != null ? idDistrito : 0);
            parametros.setIdMunicipio(null);
            parametros.setGrupoSistema(grupoSistema);
            parametros.setIdModulo(idModulo);
            parametros.setJndi("estatusSUSTSUPYCAPjndi");
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
            response.setData(estatusController.obtieneEstatusModulo(parametros));
            return response;

        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneEstatusModulo: ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener el estatus módulo.");
            return response;
        }
    }

    @Override
    public Integer obtieneEtapaCapacitacion(Integer idProceso, Integer idDetalle) {
        DTOCFechas fechaInicio2daEtapa = repoJPACFechas.findById_idProcesoElectoralAndId_IdDetalleProcesoAndId_IdFecha(
                idProceso, idDetalle, Constantes.ID_FECHA_INCIO_2DA_ETAPA);
        Date dateInicio2daEtapa = java.sql.Date.valueOf(fechaInicio2daEtapa.getFecha());
        boolean is2daEtapa = new Date().after(dateInicio2daEtapa);

        return is2daEtapa ? 2 : 1;
    }

    private Integer obtenerParametroUsarWS(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idEstado,
            Integer idDistrito) {
        BigDecimal result = reporJPAParametros.obtieneParametrosRecursivamente(idProcesoElectoral, idDetalleProceso,
                idEstado, idDistrito, Constantes.ID_PARAMETRO_USAR_WS_ADMIN_DECEYEC);
        return result == null ? 0 : result.intValue();
    }

    @Override
    public List<DTOAccionModulo> obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo)
            throws Exception {
        try {
            List<DTOMenu> menu;
            if (obtenerParametroUsarWS(0, 0, 0, 0) == 1) {

                menu = HLPMenuWS.mapperMenuLateralWS(repoMenu.obtieneMenuAccionesWS(idSistema, idProceso, idDetalle,
                        idEstado, idDistrito, grupoSistema));

            } else {
                menu = repoMenu.obtieneMenuAcciones(idSistema, idProceso, idDetalle, idEstado, idDistrito,
                        grupoSistema);
            }
            return HLPMenuWS.mapperMenuAcciones(idModulo, menu);
        } catch (Exception e) {
            logger.error("ERROR ASMenuImpl -obtieneMenuAcciones: ", e);
            throw e;
        }
    }

}
