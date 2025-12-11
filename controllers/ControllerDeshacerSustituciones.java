package mx.ine.sustseycae.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.DTORequestConsultaDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestDeshacerSustitucion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.Exceptions.ExceptionValidacionAreZore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ws")
public interface ControllerDeshacerSustituciones {
        @PostMapping(path = "/consultaDeshacerSustitucion", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse consultaDeshacerSustitucion(
                @Valid @RequestBody DTORequestConsultaDeshacerSustituciones deshacerSustituciones,
                HttpServletRequest request);

        @PostMapping(path = "/consultaSustitucionesDeshechas", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse consultaSustitucionesDeshechas(
                @Valid @RequestBody DTORequestConsultaDeshacerSustituciones deshacerSustituciones,
                HttpServletRequest request);

        @PostMapping(path = "/deshacerSustitucion", consumes = "application/json", produces = "application/json")
        public @ResponseBody ModelGenericResponse deshacerSustitucion(
                @Valid @RequestBody DTORequestDeshacerSustitucion requestSustitucion, HttpServletRequest request)
                throws ExceptionValidacionAreZore;
}
