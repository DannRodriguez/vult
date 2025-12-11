package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseSustiTerminoContrato;

public interface BSDSustTerminoContrato {

	public ModelResponseSustiTerminoContrato obtenerSustitutosSustTermino(ModelRequestSustTerminoContrato request);

	public boolean guardarSustitucionTerminoContrato(ModelRequestSustituciones request);

}
