package mx.ine.sustseycae.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.validation.Valid;
import mx.ine.sustseycae.bsd.BSDMenu;
import mx.ine.sustseycae.controllers.ControllerMenu;
import mx.ine.sustseycae.models.requests.ModelRequestGeografico;
import mx.ine.sustseycae.models.requests.ModelRequestMenu;
import mx.ine.sustseycae.models.requests.ModelRequestModulo;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@Controller
public class ControllerMenuImpl implements ControllerMenu {

    @Autowired
    private BSDMenu bsdMenu;

    @Override
    public ModelGenericResponse obtieneEstadosMultiProceso(@Valid ModelRequestGeografico model) {
        return bsdMenu.obtieneEstadosMultiProceso(model.getIdSistema(), model.getRol());
    }

    @Override
    public ModelGenericResponse obtieneProcesosDetalleMultiProceso(@Valid ModelRequestGeografico model) {
        return bsdMenu.obtieneProcesosDetalleMultiProceso(model.getIdSistema(), model.getIdEstado(),
                model.getIdDistrito(), model.getAmbitoUsuario());
    }

    @Override
    public ModelGenericResponse obtieneDistritos(@Valid ModelRequestGeografico model) {
        return bsdMenu.obtieneDistritos(model.getIdEstado(), model.getIdProceso(), model.getIdDetalle(),
                model.getIdDistrito(), model.getIdSistema());
    }

    @Override
    public ModelGenericResponse obtieneParticipacion(@Valid ModelRequestGeografico model) {
        return bsdMenu.obtieneParticipacionAndEtapa(model.getIdProceso(), model.getIdDetalle(), model.getIdEstado(),
                model.getIdDistrito(), model.getAmbitoUsuario(), model.getTipoCapturaSistema());
    }

    @Override
    public ModelGenericResponse obtieneMenuLateral(@Valid ModelRequestMenu model) {
        return bsdMenu.obtieneMenuLateral(model.getIdSistema(), model.getIdProceso(), model.getIdDetalle(),
                model.getIdEstado(), model.getIdDistrito(), model.getIdMunicipio(), model.getGrupoSistema());
    }

    @Override
    public ModelGenericResponse obtieneEstatusModulo(@Valid ModelRequestModulo model) {
        return bsdMenu.obtieneEstatusModulo(model.getIdSistema(), model.getIdProceso(), model.getIdDetalle(),
                model.getIdEstado(), model.getIdDistrito(), model.getIdMunicipio(), model.getGrupoSistema(),
                model.getIdModulo());
    }

    @Override
    public ModelGenericResponse obtieneMenuAcciones(@Valid ModelRequestModulo model) {
        return bsdMenu.obtieneMenuAcciones(model.getIdSistema(), model.getIdProceso(), model.getIdDetalle(),
                model.getIdEstado(), model.getIdDistrito(), model.getIdMunicipio(), model.getGrupoSistema(),
                model.getIdModulo());
    }

}
