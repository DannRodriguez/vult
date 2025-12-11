package mx.ine.sustseycae.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestCerrarSesion;
import mx.ine.sustseycae.models.requests.DTORequestIniciarSesion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("ws")
public interface ControllerLogin {

    @PostMapping(path = "/loginUser")
    public @ResponseBody ModelGenericResponse login(@Valid @RequestBody DTORequestIniciarSesion loginRequest,
            HttpServletRequest request);

    @PostMapping(path = "/refreshToken")
    public @ResponseBody ModelGenericResponse refreshToken(@RequestBody Map<String, Object> body,
            HttpServletRequest request);

    @PostMapping(path = "/cierraSesion", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse cierraSesion(@Valid @RequestBody DTORequestCerrarSesion loginRequest);

    @PostMapping(path = "/cierraSesionForc", consumes = "application/json", produces = "application/json")
    public @ResponseBody ModelGenericResponse cierraSesionForc(@Valid @RequestBody DTORequestCerrarSesion loginRequest);

}
