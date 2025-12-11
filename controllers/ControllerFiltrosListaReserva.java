package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.ine.sustseycae.models.requests.ModelRequestFiltroLR;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerFiltrosListaReserva {

    @PostMapping(path = "/obtenerMunicipios")
    public @ResponseBody
    ModelGenericResponse obtenerMunicipios(@RequestBody @Valid ModelRequestFiltroLR model);

    @PostMapping(path = "/obtenerLocalidadesPorMunicipio")
    public @ResponseBody
    ModelGenericResponse obtenerLocalidadesPorMunicipio(
            @RequestBody @Valid ModelRequestFiltroLR model);

    @PostMapping(path = "/obtenerSedes")
    public @ResponseBody
    ModelGenericResponse obtenerSedes(@RequestBody @Valid ModelRequestFiltroLR model);

    @PostMapping(path = "/obtenerSecciones")
    public @ResponseBody
    ModelGenericResponse obtenerSecciones(@RequestBody @Valid ModelRequestFiltroLR model);

}
