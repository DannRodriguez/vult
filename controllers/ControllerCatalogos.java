package mx.ine.sustseycae.controllers;

import mx.ine.sustseycae.models.requests.ModelRequestFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("ws")
public interface ControllerCatalogos {

   @PostMapping(path = "/obtenerListaCausasVacante")
   public @ResponseBody ModelGenericResponse obtenerListaCausasVacante();

   @PostMapping(path = "/obtenerFechasSustituciones", consumes = "application/json", produces = "application/json")
   public @ResponseBody ModelGenericResponse obtenerFechasSustituciones(
         @RequestBody @Valid ModelRequestFechasSustituciones request);

}
