package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDFiltrosListaReserva;
import mx.ine.sustseycae.controllers.ControllerFiltrosListaReserva;
import mx.ine.sustseycae.models.requests.ModelRequestFiltroLR;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerFiltrosListaReservaImpl implements ControllerFiltrosListaReserva {

    @Autowired
    private BSDFiltrosListaReserva bsdFiltros;

    @Override
    public ModelGenericResponse obtenerMunicipios(@Valid ModelRequestFiltroLR model) {
        return bsdFiltros.obtenerMunicipios(model.getIdEstado(), model.getIdDistrito(), model.getIdCorte());
    }

    @Override
    public ModelGenericResponse obtenerLocalidadesPorMunicipio(@Valid ModelRequestFiltroLR model) {
        return bsdFiltros.obtenerLocalidadesPorMunicipio(model.getIdEstado(), model.getIdMunicipio(),
                model.getIdDistrito(), model.getIdCorte(), model.getIdProceso(), model.getIdDetalle(),
                model.getIdParticipacion());
    }

    @Override
    public ModelGenericResponse obtenerSedes(@Valid ModelRequestFiltroLR model) {
        return bsdFiltros.obtenerSedes(model.getIdProceso(), model.getIdDetalle(), model.getIdParticipacion());
    }

    @Override
    public ModelGenericResponse obtenerSecciones(@Valid ModelRequestFiltroLR model) {
        return bsdFiltros.obtenerSecciones(model.getIdProceso(), model.getIdDetalle(),
                model.getIdParticipacion());
    }

}
