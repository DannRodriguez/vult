package mx.ine.sustseycae.bsd.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.sustseycae.as.ASMenu;
import mx.ine.sustseycae.bsd.BSDMenu;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdMenu")
@Scope("prototype")
public class BSDMenuImpl implements BSDMenu {

    @Autowired
    private ASMenu asMenu;

    @Override
    public ModelGenericResponse obtieneEstadosMultiProceso(Integer idSistema, String ambito) {
        return asMenu.obtieneEstadosMultiProceso(idSistema, ambito);
    }

    @Override
    public ModelGenericResponse obtieneProcesosDetalleMultiProceso(Integer idSistema, Integer idEstado,
            Integer idDistrito, String ambito) {
        return asMenu.obtieneProcesosDetalleMultiProceso(idSistema, idEstado, idDistrito, ambito);
    }

    @Override
    public ModelGenericResponse obtieneDistritos(Integer idEstado, Integer idProceso, Integer idDetalle,
            Integer idDistrito, Integer idSistema) {
        return asMenu.obtieneDistritos(idEstado, idProceso, idDetalle, idDistrito, idSistema);
    }

    @Override
    public ModelGenericResponse obtieneParticipacionAndEtapa(Integer idProceso, Integer idDetalle, Integer idEstado,
            Integer idDistrito, String ambito, String tipoCapturaSistema) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            if (tipoCapturaSistema.equalsIgnoreCase("D")) {
                Map<String, Integer> participacionYEtapa = new HashMap<>();

                participacionYEtapa.put("idParticipacion",
                        asMenu.obtieneParticipacion(idProceso, idDetalle, idEstado, idDistrito, ambito));
                participacionYEtapa.put("etapaCapacitacion", asMenu.obtieneEtapaCapacitacion(idProceso, idDetalle));

                response.setCode(Constantes.RESPONSE_CODE_200);
                response.setMessage(Constantes.ESTATUS_EXITO);
                response.setData(participacionYEtapa);
                return response;
            } else {
                response.setStatus(Constantes.ESTATUS_ERROR);
                response.setCode(Constantes.RESPONSE_CODE_500);
                response.setMessage("Tipo de captura no implementado al obtener la participacion geografica.");
                return response;
            }

        } catch (Exception e) {
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener la participación y la etapa de capacitación.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtieneMenuLateral(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, Integer idMunicipio, String grupoSistema) {
        int peticion = 0;
        ModelGenericResponse response = new ModelGenericResponse();

        try {
            List<DTOMenu> data = null;
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);

            if (grupoSistema.toUpperCase().contains("OC")) {
                peticion = 3;
            } else if (grupoSistema.toUpperCase().contains("JL")) {
                peticion = 2;
            } else if (grupoSistema.toUpperCase().contains("JD")) {
                peticion = 1;
            }

            for (int i = 1; i <= peticion; i++) {
                switch (i) {
                    case 1 -> {
                        idEstado = idEstado != null && idEstado != 0 ? idEstado : 99;
                        idDistrito = idDistrito != null && idDistrito != 0 ? idDistrito : 99;
                    }
                    case 2 -> {
                        idEstado = idEstado != null && idEstado != 0 ? idEstado : 99;
                        idDistrito = 0;
                    }
                    case 3 -> {
                        idEstado = 0;
                        idDistrito = 0;
                    }
                    default -> {
                        break;
                    }
                }
                if (data == null || data.isEmpty()) {
                    data = asMenu.obtieneMenuLateral(idSistema, idProceso, idDetalle, idEstado, idDistrito, idMunicipio,
                            grupoSistema);
                }
            }
            response.setData(data);
            return response;
        } catch (Exception e) {
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener el menu lateral.");
            return response;
        }
    }

    @Override
    public ModelGenericResponse obtieneEstatusModulo(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo) {
        return asMenu.obtieneEstatusModulo(idSistema, idProceso, idDetalle, idEstado, idDistrito, idMunicipio,
                grupoSistema, idModulo);
    }

    @Override
    public ModelGenericResponse obtieneMenuAcciones(Integer idSistema, Integer idProceso, Integer idDetalle,
            Integer idEstado, Integer idDistrito, Integer idMunicipio, String grupoSistema, Integer idModulo) {

        ModelGenericResponse response = new ModelGenericResponse();
        try {
            int peticion = 0;
            Object data = null;

            if (grupoSistema.toUpperCase().contains("OC")) {
                peticion = 3;
            } else if (grupoSistema.toUpperCase().contains("JL")) {
                peticion = 2;
            } else if (grupoSistema.toUpperCase().contains("JD")) {
                peticion = 1;
            }

            for (int i = 1; i <= peticion; i++) {
                switch (i) {
                    case 1 -> {
                        idEstado = idEstado != null && idEstado != 0 ? idEstado : 99;
                        idDistrito = idDistrito != null && idDistrito != 0 ? idDistrito : 99;
                    }
                    case 2 -> {
                        idEstado = idEstado != null && idEstado != 0 ? idEstado : 99;
                        idDistrito = 0;
                    }
                    case 3 -> {
                        idEstado = 0;
                        idDistrito = 0;
                    }
                    default -> {
                        break;
                    }
                }
                if (data == null) {
                    data = asMenu.obtieneMenuAcciones(idSistema, idProceso, idDetalle, idEstado, idDistrito,
                            idMunicipio, grupoSistema, idModulo);
                }
            }
            response.setCode(Constantes.RESPONSE_CODE_200);
            response.setMessage(Constantes.ESTATUS_EXITO);
            response.setData(data);
            return response;
        } catch (Exception e) {
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrio un error al obtener el menu acciones.");
            return response;
        }
    }

}
