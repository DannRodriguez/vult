package mx.ine.sustseycae.as;

import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseSustiTerminoContrato;

public interface ASSustTerminoContrato {

	public ModelResponseSustiTerminoContrato obtenerSustitutosSustTerminoCaptura(ModelRequestSustTerminoContrato susti);

	public ModelResponseSustiTerminoContrato obtenerSustitutosSustTerminoConsulta(
			ModelRequestSustTerminoContrato susti);

	public boolean guardarSustitucionTerminoContratoCaptura(ModelRequestSustituciones susti);

	public boolean guardarSustitucionTerminoContratoModifica(ModelRequestSustituciones susti);
}
