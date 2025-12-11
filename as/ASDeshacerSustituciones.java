package mx.ine.sustseycae.as;

import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestConsultaDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestDeshacerSustitucion;
import mx.ine.sustseycae.util.Exceptions.ExceptionValidacionAreZore;

import java.util.List;

public interface ASDeshacerSustituciones {
    public List<VOConsultaDesSustitucionesSupycap> consultaDeshacerSustitucion (DTORequestConsultaDeshacerSustituciones deshacerSustituciones);
    public List<VOConsultaDesSustitucionesSupycap> consultaSustitucionesDeshechas (DTORequestConsultaDeshacerSustituciones deshacerSustituciones);
    public void deshacerSustitucion (DTORequestDeshacerSustitucion requestSustitucion) throws ExceptionValidacionAreZore;
}
