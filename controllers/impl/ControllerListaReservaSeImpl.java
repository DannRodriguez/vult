package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDListaReservaSeInterface;
import mx.ine.sustseycae.controllers.ControllerListaReservaSe;
import mx.ine.sustseycae.models.requests.ModelRequestActualizaLstRsvSe;
import mx.ine.sustseycae.models.requests.ModelRequestListaReservaSe;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerListaReservaSeImpl implements ControllerListaReservaSe {

    @Autowired
    private BSDListaReservaSeInterface bsdListaReservaSe;

    @Override
    public ModelGenericResponse obtenerListaReservaSe(@Valid ModelRequestListaReservaSe model) {
        return bsdListaReservaSe.obtenerListaReservaSe(model.getIdProceso(), model.getIdDetalle(),
                model.getIdParticipacion(), model.getIdPuesto(), model.getEstatus(), model.getFiltro(),
                model.getIdMunicipio(), model.getIdLocalidad(), model.getIdSede(), model.getSeccion1(),
                model.getSeccion2());
    }

    @Override
    public ModelGenericResponse actualizarListaReservaSe(@Valid ModelRequestActualizaLstRsvSe model) {
        return bsdListaReservaSe.actualizarLista(model.getIdProceso(), model.getIdDetalle(),
                model.getIdParticipacion(), model.getIdAspirante(), model.getEstatus(), model.getIp(),
                model.getUser());
    }

}
