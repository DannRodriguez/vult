package mx.ine.sustseycae.bsd.impl;

import mx.ine.sustseycae.as.ASSustIncapacidad;
import mx.ine.sustseycae.bsd.BSDSustIncapacidad;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelResponseSustitucionesRelacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BSDSustIncapacidadImpl implements BSDSustIncapacidad {

    @Autowired
    private ASSustIncapacidad asSustIncapacidad;

    @Override
    public boolean guardarSustIncapacidad(DTORequestSustIncapacidad request) {
        if (!request.getEsPendiente()) {
            return asSustIncapacidad.insertaSustitucion(request);
        } else {
            return asSustIncapacidad.actualizaPendiente(request);
        }
    }

    @Override
    public boolean modificarSustIncapacidad(DTORequestModifSustIncap request) {
        return asSustIncapacidad.modificaSustitucion(request);
    }

    @Override
    public VOSustitucionesSupycap obtenerInfoSustitucion(DTORequestSustIncapacidad request) {
        return asSustIncapacidad.obtenerInfoSustitucion(request);
    }

    @Override
    public ModelResponseSustitucionesRelacion consultaSustitucionesRelacion(
            DTORequestSustIncapacidad request) {
        return asSustIncapacidad.consultaSustitucionesRelacion(request);
    }

}
