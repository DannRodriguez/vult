package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDListaReservaCAEInterface;
import mx.ine.sustseycae.controllers.ControllerListaReservaCAE;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerListaReservaCAEImpl implements ControllerListaReservaCAE {

    @Autowired
    @Qualifier("bsdListaReservaCAE")
    private BSDListaReservaCAEInterface bsdListReservaCAE;

    @Override
    public ModelGenericResponse obtenerListaReservaCAE(@Valid ModelRequestListaResrvervaCAE model) {
        return bsdListReservaCAE.obtenerListaReservaCAE(model);
    }

    @Override
    public ModelGenericResponse guardarCambiosListaReservaCAE(@Valid VOListaReservaCAE aspirante) {
        return bsdListReservaCAE.guardarCambiosListaReservaCAE(aspirante);
    }

}
