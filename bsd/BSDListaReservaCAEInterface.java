package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface BSDListaReservaCAEInterface {

    public ModelGenericResponse obtenerListaReservaCAE(ModelRequestListaResrvervaCAE model);

    public ModelGenericResponse guardarCambiosListaReservaCAE(VOListaReservaCAE aspirante);

}
