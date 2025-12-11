package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.models.requests.ModelRequestListaResrvervaCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerListaReservaCAE {
	
	@PostMapping(path = "/obtenerListaReservaCAE", consumes = "application/json", produces = "application/json")
	   public @ResponseBody ModelGenericResponse obtenerListaReservaCAE(@RequestBody @Valid ModelRequestListaResrvervaCAE model);
	  
	@PostMapping(path = "/guardarCambiosListaReservaCAE", consumes = "application/json", produces = "application/json")
	   public @ResponseBody ModelGenericResponse guardarCambiosListaReservaCAE(@RequestBody @Valid VOListaReservaCAE aspirante);
	 
}
