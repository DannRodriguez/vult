package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.ModelRequestGeografico;
import mx.ine.sustseycae.models.requests.ModelRequestMenu;
import mx.ine.sustseycae.models.requests.ModelRequestModulo;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerMenu {

    @PostMapping(path = "/obtieneEstadosMultiProceso")
    public @ResponseBody
    ModelGenericResponse obtieneEstadosMultiProceso(
            @RequestBody @Valid ModelRequestGeografico model);

    @PostMapping(path = "/obtieneProcesosDetalleMultiProceso")
    public @ResponseBody
    ModelGenericResponse obtieneProcesosDetalleMultiProceso(
            @RequestBody @Valid ModelRequestGeografico model);

    @PostMapping(path = "/obtieneDistritos")
    public @ResponseBody
    ModelGenericResponse obtieneDistritos(@RequestBody @Valid ModelRequestGeografico model);

    @PostMapping(path = "/obtieneParticipacion")
    public @ResponseBody
    ModelGenericResponse obtieneParticipacion(@RequestBody @Valid ModelRequestGeografico model);

    @PostMapping(path = "/obtieneMenuLateral")
    public @ResponseBody
    ModelGenericResponse obtieneMenuLateral(@RequestBody @Valid ModelRequestMenu model);

    @PostMapping(path = "/obtieneEstatusModulo")
    public @ResponseBody
    ModelGenericResponse obtieneEstatusModulo(@RequestBody @Valid ModelRequestModulo model);

    @PostMapping(path = "/obtieneMenuAcciones")
    public @ResponseBody
    ModelGenericResponse obtieneMenuAcciones(@RequestBody @Valid ModelRequestModulo model);

}
