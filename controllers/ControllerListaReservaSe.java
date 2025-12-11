package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.ModelRequestActualizaLstRsvSe;
import mx.ine.sustseycae.models.requests.ModelRequestListaReservaSe;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerListaReservaSe {

    @PostMapping(path = "/obtenerListaReservaSe")
    public @ResponseBody
    ModelGenericResponse obtenerListaReservaSe(
            @RequestBody @Valid ModelRequestListaReservaSe model);

    @PostMapping(path = "/actualizarListaReservaSe")
    public @ResponseBody
    ModelGenericResponse actualizarListaReservaSe(
            @RequestBody @Valid ModelRequestActualizaLstRsvSe model);

}
