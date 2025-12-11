package mx.ine.sustseycae.controllers.impl;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.bsd.BSDSustIncapacidad;
import mx.ine.sustseycae.controllers.ControllerSustIncapacidad;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseSustitucionesRelacion;
import mx.ine.sustseycae.util.ApplicationUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerSustIncapacidadImpl implements ControllerSustIncapacidad {

    @Autowired
    private BSDSustIncapacidad bsdSustIncapacidad;

    @Override
    public ModelGenericResponse guardarSustIncapacidad(DTORequestSustIncapacidad reqSustIncapacidad,
            HttpServletRequest request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            reqSustIncapacidad.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
            bsdSustIncapacidad.guardarSustIncapacidad(reqSustIncapacidad);
            response.setCode(200);
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public ModelGenericResponse modificarSustIncapacidad(DTORequestModifSustIncap requestModifSustIncap,
            HttpServletRequest request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            requestModifSustIncap.setIpUsuario(ApplicationUtil.obtenerIpCliente(request));
            bsdSustIncapacidad.modificarSustIncapacidad(requestModifSustIncap);
            response.setCode(200);
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
        }
        return response;
    }

    @Override
    public ModelGenericResponse obtenerInfoSustitucion(DTORequestSustIncapacidad request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            VOSustitucionesSupycap sustitucion = bsdSustIncapacidad.obtenerInfoSustitucion(request);
            response.setCode(200);
            response.setData(sustitucion);
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
        }
        return response;
    }

    @Override
    public ModelGenericResponse consultaSustitucionesRelacion(DTORequestSustIncapacidad request) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            ModelResponseSustitucionesRelacion sustitucion = bsdSustIncapacidad.consultaSustitucionesRelacion(request);
            response.setCode(200);
            response.setData(sustitucion);
        } catch (Exception e) {
            response.setCode(500);
            response.setCausa(e.getMessage());
        }
        return response;
    }

}
