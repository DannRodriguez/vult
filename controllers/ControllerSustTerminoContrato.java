package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.ModelRequestSustTerminoContrato;
import mx.ine.sustseycae.models.requests.ModelRequestSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerSustTerminoContrato {

	@PostMapping(path = "/obtenerSustitutosSustTermino", consumes = "application/json", produces = "application/json")
	public @ResponseBody ModelGenericResponse obtenerSustitutosSustTermino(
			@RequestBody @Valid ModelRequestSustTerminoContrato request);

	@PostMapping(path = "/guardarSustitucionTerminoContrato", consumes = "application/json", produces = "application/json")
	public @ResponseBody ModelGenericResponse guardarSustitucionTerminoContrato(
			@RequestBody @Valid ModelRequestSustituciones request);

}
