package mx.ine.sustseycae.bsd.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.as.ASSustTerminoContrato;
import mx.ine.sustseycae.bsd.BSDSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseSustiTerminoContrato;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdSustTerminoContrato")
@Scope("prototype")
public class BSDSustTerminoContratoImpl implements BSDSustTerminoContrato {

	@Autowired
	@Qualifier("asSustTerminoContratoImpl")
	private ASSustTerminoContrato asSustTerminoContrato;

	@Override
	public ModelResponseSustiTerminoContrato obtenerSustitutosSustTermino(ModelRequestSustTerminoContrato request) {

		if (Constantes.FLUJO_CAPTURA.equals(request.getTipoFlujo())) {
			return asSustTerminoContrato.obtenerSustitutosSustTerminoCaptura(request);
		} else {
			return asSustTerminoContrato.obtenerSustitutosSustTerminoConsulta(request);
		}

	}

	@Override
	public boolean guardarSustitucionTerminoContrato(ModelRequestSustituciones request) {

		if (Constantes.FLUJO_CAPTURA.equals(request.getTipoFlujo())) {
			return asSustTerminoContrato.guardarSustitucionTerminoContratoCaptura(request);
		} else {
			return asSustTerminoContrato.guardarSustitucionTerminoContratoModifica(request);
		}
	}

}
