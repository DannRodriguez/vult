package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;
import mx.ine.sustseycae.models.requests.DTORequestLoadFoto;
import mx.ine.sustseycae.models.requests.DTORequestObtenerAspSustituto;
import mx.ine.sustseycae.models.requests.DTORequestObtenerSustitutos;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface CommonController {

   @PostMapping(path = "/obtenerListaSustituido", consumes = "application/json", produces = "application/json")
   public @ResponseBody ModelGenericResponse obtenerListaSustituido(
         @Valid @RequestBody DTORequestListaSustituido requestListaSustituido);

   @PostMapping(path = "/obtenerInfoSustituido", consumes = "application/json", produces = "application/json")
   public @ResponseBody ModelGenericResponse obtenerInfoSustituido(
         @Valid @RequestBody DTORequestListaSustituido requestListaSustituido);

   @PostMapping(path = "/obtenerListaSustitutosSupervisores")
   public @ResponseBody ModelGenericResponse obtenerListaSustitutosSupervisores(
         @Valid @RequestBody DTORequestObtenerSustitutos sustRequest);

   @PostMapping(path = "/obtenerListaSustitutosCapacitadores")
   public @ResponseBody ModelGenericResponse obtenerListaSustitutosCapacitadores(
         @Valid @RequestBody DTORequestObtenerSustitutos sustRequest);

   @PostMapping(path = "/obtenerAspiranteSustituto")
   public @ResponseBody ModelGenericResponse obtenerAspiranteSustituto(
         @Valid @RequestBody DTORequestObtenerAspSustituto sustitutoRequest);

   @PostMapping(path = "/obtenerInformacionSustitucion")
   public @ResponseBody ModelGenericResponse obtenerInformacionSustitucion(
         @Valid @RequestBody DTORequestObtenerAspSustituto sustitutoRequest);

   @PostMapping(path = "/obtenerFotoAsp", consumes = "application/json", produces = "application/json")
   public @ResponseBody ModelGenericResponse obtenerFotoAsp(@Valid @RequestBody DTORequestLoadFoto requestFoto);
}
