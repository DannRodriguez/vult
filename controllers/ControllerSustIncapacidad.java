package mx.ine.sustseycae.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestModifSustIncap;
import mx.ine.sustseycae.models.requests.DTORequestSustIncapacidad;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ws")
public interface ControllerSustIncapacidad {

    @PostMapping(path = "/guardarSustIncapacidad", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse guardarSustIncapacidad(
            @Valid @RequestBody DTORequestSustIncapacidad reqSustIncapacidad, HttpServletRequest request);

    @PostMapping(path = "/modificarSustIncapacidad", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse modificarSustIncapacidad(
            @Valid @RequestBody DTORequestModifSustIncap requestModifSustIncap, HttpServletRequest request);

    @PostMapping(path = "/obtenerInfoSustitucion", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse obtenerInfoSustitucion(
            @Valid @RequestBody DTORequestSustIncapacidad request);

    @PostMapping(path = "/consultaSustitucionesRelacion", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse consultaSustitucionesRelacion(
            @Valid @RequestBody DTORequestSustIncapacidad request);

}
