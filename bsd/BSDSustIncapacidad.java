package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelResponseSustitucionesRelacion;

public interface BSDSustIncapacidad {

    public boolean guardarSustIncapacidad(DTORequestSustIncapacidad request);

    public boolean modificarSustIncapacidad(DTORequestModifSustIncap request);

    public VOSustitucionesSupycap obtenerInfoSustitucion(DTORequestSustIncapacidad request);

    public ModelResponseSustitucionesRelacion consultaSustitucionesRelacion(
            DTORequestSustIncapacidad request);

}
