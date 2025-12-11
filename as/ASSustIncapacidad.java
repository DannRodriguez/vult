package mx.ine.sustseycae.as;

import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelResponseSustitucionesRelacion;
import org.springframework.stereotype.Service;

@Service("asSustIncapacidad")
public interface ASSustIncapacidad {

    public boolean insertaSustitucion(DTORequestSustIncapacidad request);

    public boolean actualizaPendiente(DTORequestSustIncapacidad request);

    public boolean modificaSustitucion(DTORequestModifSustIncap request);

    public VOSustitucionesSupycap obtenerInfoSustitucion(DTORequestSustIncapacidad request);

    public ModelResponseSustitucionesRelacion consultaSustitucionesRelacion(
            DTORequestSustIncapacidad request);

}
