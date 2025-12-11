package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestModificarSustRescision;
import mx.ine.sustseycae.models.requests.DTORequestObtenerAspSustituto;
import mx.ine.sustseycae.models.requests.DTORequestSustRescision;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerSustRescision {

        @PostMapping(path = "/guardarSustRescision", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse guardarModificarSustRescision(
                        @Valid @RequestBody DTORequestSustRescision requestSustRescision,
                        HttpServletRequest request);

        @PostMapping(path = "/guardarSustRescisionPendiente", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse guardarSustRescisionPendiente(
                        @Valid @RequestBody DTORequestSustRescision requestSustRescision,
                        HttpServletRequest request);

        @PostMapping(path = "/obtenerSustitucionPendiente", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse obtenerSustitucionPendiente(
                        @Valid @RequestBody DTORequestObtenerAspSustituto request);

        @PostMapping(path = "/modificarSustRescision", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse modificarSustRescision(
                        @Valid @RequestBody DTORequestModificarSustRescision requestSustRescision,
                        HttpServletRequest request);

}
