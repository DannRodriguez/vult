package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDSustTerminoContrato;
import mx.ine.sustseycae.controllers.ControllerSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerSustTerminoContratoImpl implements ControllerSustTerminoContrato {

	@Autowired
	@Qualifier("bsdSustTerminoContrato")
	private BSDSustTerminoContrato bsdSustTerminoContrato;

	@Override
	public ModelGenericResponse obtenerSustitutosSustTermino(@Valid ModelRequestSustTerminoContrato request) {
		ModelGenericResponse response = new ModelGenericResponse();
		try {
			response.setCode(200);
			response.setData(bsdSustTerminoContrato.obtenerSustitutosSustTermino(request));
		} catch (Exception e) {
			response.setCode(500);
			response.setCausa(e.getMessage());
		}
		return response;
	}

	@Override
	public ModelGenericResponse guardarSustitucionTerminoContrato(@Valid ModelRequestSustituciones request) {
		ModelGenericResponse response = new ModelGenericResponse();
		try {
			response.setCode(200);
			response.setData(bsdSustTerminoContrato.guardarSustitucionTerminoContrato(request));
		} catch (Exception e) {
			response.setCode(500);
			response.setCausa(e.getMessage());
		}
		return response;
	}

}
