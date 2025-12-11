package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDSustRescision;
import mx.ine.sustseycae.controllers.ControllerSustRescision;
import mx.ine.sustseycae.models.requests.DTORequestModificarSustRescision;
import mx.ine.sustseycae.models.requests.DTORequestObtenerAspSustituto;
import mx.ine.sustseycae.models.requests.DTORequestSustRescision;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.ApplicationUtil;

@Controller
public class ControllerSustRescisionImpl implements ControllerSustRescision {

    @Autowired
    private BSDSustRescision bsdSustRescision;

    @Override
    public ModelGenericResponse guardarModificarSustRescision(@Valid DTORequestSustRescision requestSustRescision,
            HttpServletRequest request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(200);
            requestSustRescision.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
            response.setData(bsdSustRescision.guardarModificarSustRescision(requestSustRescision));
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
            response.setData(false);
        }
        return response;
    }

    @Override
    public ModelGenericResponse guardarSustRescisionPendiente(@Valid DTORequestSustRescision requestSustRescision,
            HttpServletRequest request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(200);
            requestSustRescision.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
            response.setData(bsdSustRescision.guardarSustRescisionPendiente(requestSustRescision));
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
            response.setData(false);
        }
        return response;
    }

    @Override
    public ModelGenericResponse obtenerSustitucionPendiente(@Valid DTORequestObtenerAspSustituto request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(200);
            response.setData(bsdSustRescision.obtenerSustitucionPendiente(
                    request.getIdDetalleProceso(),
                    request.getIdParticipacion(),
                    request.getIdAspirante()));
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
            response.setData(false);
        }
        return response;
    }

    @Override
    public ModelGenericResponse modificarSustRescision(@Valid DTORequestModificarSustRescision requestSustRescision,
            HttpServletRequest request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            response.setCode(200);
            requestSustRescision.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
            response.setData(bsdSustRescision.modificarSustRescision(requestSustRescision));
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
            response.setData(false);
        }
        return response;
    }

}
