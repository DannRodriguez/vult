package mx.ine.sustseycae.bsd.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASListaReservaCAEInterface;
import mx.ine.sustseycae.bsd.BSDListaReservaCAEInterface;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Service("bsdListaReservaCAE")
@Scope("prototype")
public class BSDListaReservaCAEImpl implements BSDListaReservaCAEInterface {

    @Autowired
    @Qualifier("asListaReservaCAE")
    private ASListaReservaCAEInterface asListReservaCAE;

    @Override
    public ModelGenericResponse obtenerListaReservaCAE(ModelRequestListaResrvervaCAE model) {
        return asListReservaCAE.obtenerListaReservaCAE(model);
    }

    @Override
    public ModelGenericResponse guardarCambiosListaReservaCAE(VOListaReservaCAE aspirante) {
        return asListReservaCAE.guardarCambiosListaReservaCAE(aspirante);
    }

}
