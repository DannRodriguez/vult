package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerBitacoraDesempenio {

	
	   @PostMapping(path = "/obtenerAspiranteBitacora")
	   public @ResponseBody ModelGenericResponse obtenerAspiranteBitacora(@Valid @RequestBody DTORequestBitacora request);
	
	   @PostMapping(path = "/obtenerExpedienteDesemp")
	   public @ResponseBody ModelGenericResponse obtenerExpedienteDesemp(@Valid @RequestBody DTORequestBitacora request);
	
	   @PostMapping(path = "/obtenerExpedienteB64")
	   public @ResponseBody ModelGenericResponse obtenerExpedienteB64(@Valid @RequestBody DTORequestBitacora request);
	
	   @PostMapping(path = "/obtenerEvaluacionDesemp")
	   public @ResponseBody ModelGenericResponse obtenerEvaluacionDesemp(@Valid @RequestBody DTORequestBitacora request);
	
	   @PostMapping(path = "/guardarBitacoraDesemp", consumes = "multipart/form-data")
	   public @ResponseBody ModelGenericResponse guardarBitacoraDesemp(@RequestPart(value = "fileExpediente", required = false) MultipartFile fileExpediente,  @RequestPart("requestBitacora") DTORequestBitacora request);
	
	   @PostMapping(path = "/eliminarBitacoraDesemp")
	   public @ResponseBody ModelGenericResponse eliminarBitacoraDesemp(@Valid @RequestBody DTORequestBitacora request);
	
}
