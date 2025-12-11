package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import mx.ine.sustseycae.bsd.impl.BSDCatalogosSupycapImpl;
import mx.ine.sustseycae.controllers.ControllerCatalogos;
import mx.ine.sustseycae.models.requests.ModelRequestFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerCatalogosImpl implements ControllerCatalogos {

	@Autowired
	private BSDCatalogosSupycapImpl bsdCatalogosSupycap;

	@Override
	public ModelGenericResponse obtenerListaCausasVacante() {
		ModelGenericResponse response = new ModelGenericResponse();
		response.setCode(200);
		response.setData(bsdCatalogosSupycap.obtenerListaCausasVacante());
		return response;
	}

	@Override
	public ModelGenericResponse obtenerFechasSustituciones(ModelRequestFechasSustituciones request) {
		ModelGenericResponse response = new ModelGenericResponse();
		try {
			response.setCode(200);
			response.setData(bsdCatalogosSupycap.obtenerFechasSustituciones(request));
		} catch (Exception e) {
			response.setCode(500);
			response.setCausa(e.getMessage());
		}
		return response;
	}

}
