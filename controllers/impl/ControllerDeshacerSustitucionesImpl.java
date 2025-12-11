package mx.ine.sustseycae.controllers.impl;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.bsd.BSDDeshacerSustituciones;
import mx.ine.sustseycae.controllers.ControllerDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestConsultaDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestDeshacerSustitucion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Exceptions.ExceptionValidacionAreZore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerDeshacerSustitucionesImpl implements ControllerDeshacerSustituciones {

        @Autowired
        private BSDDeshacerSustituciones bsdDeshacerSustituciones;

        @Override
        public ModelGenericResponse consultaDeshacerSustitucion(
                DTORequestConsultaDeshacerSustituciones deshacerSustituciones, HttpServletRequest request) {
            ModelGenericResponse response = new ModelGenericResponse();
            try {
                response.setData(bsdDeshacerSustituciones.consultaDeshacerSustitucion(deshacerSustituciones));
                ApplicationUtil.obtieneRespuestaExito(response);
            } catch (Exception e) {
                ApplicationUtil.obtieneRespuestaError(response, e.getMessage());
            }
            return response;
        }

        @Override
        public ModelGenericResponse consultaSustitucionesDeshechas(
                DTORequestConsultaDeshacerSustituciones deshacerSustituciones, HttpServletRequest request) {
            ModelGenericResponse response = new ModelGenericResponse();
            try {
                response.setData(bsdDeshacerSustituciones.consultaSustitucionesDeshechas(deshacerSustituciones));
                ApplicationUtil.obtieneRespuestaExito(response);
            } catch (Exception e) {
                ApplicationUtil.obtieneRespuestaError(response, e.getMessage());
            }
            return response;
        }

        @Override
        public ModelGenericResponse deshacerSustitucion(DTORequestDeshacerSustitucion requestSustitucion,
                HttpServletRequest request) throws ExceptionValidacionAreZore {
            ModelGenericResponse response = new ModelGenericResponse();
            try {
                requestSustitucion.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
                bsdDeshacerSustituciones.deshacerSustitucion(requestSustitucion);
                response.setData("Ok");
                ApplicationUtil.obtieneRespuestaExito(response);
            } catch (Exception e) {
                ApplicationUtil.obtieneRespuestaError(response, e.getMessage());
            }
            return response;
        }

}
