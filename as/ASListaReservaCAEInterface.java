package mx.ine.sustseycae.as;

import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface ASListaReservaCAEInterface {

    public ModelGenericResponse obtenerListaReservaCAE(ModelRequestListaResrvervaCAE model);

    public ModelGenericResponse guardarCambiosListaReservaCAE(VOListaReservaCAE aspirante);

}
