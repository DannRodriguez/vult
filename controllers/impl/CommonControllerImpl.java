package mx.ine.sustseycae.controllers.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import mx.ine.sustseycae.as.impl.ASCatalogosSupycapImpl;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.bsd.impl.BSDCatalogosSupycapImpl;
import mx.ine.sustseycae.controllers.CommonController;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;
import mx.ine.sustseycae.models.requests.DTORequestLoadFoto;
import mx.ine.sustseycae.models.requests.DTORequestObtenerAspSustituto;
import mx.ine.sustseycae.models.requests.DTORequestObtenerSustitutos;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseInfoSustitucion;

@Controller
public class CommonControllerImpl implements CommonController {

    private static final Log log = LogFactory.getLog(CommonControllerImpl.class);

    @Autowired
    @Qualifier("bsdCommons")
    private BSDCommons bsdCommons;

    @Autowired
    private BSDCatalogosSupycapImpl bsdCatalogosSupycap;

    @Autowired
    private ASCatalogosSupycapImpl asCatalogosSupycap;

    @Override
    public ModelGenericResponse obtenerListaSustituido(DTORequestListaSustituido requestListaSustituido) {
        ModelGenericResponse response = new ModelGenericResponse();
        response.setCode(200);
        response.setData(bsdCommons.obtenerListaSustituido(requestListaSustituido));
        return response;
    }

    @Override
    public ModelGenericResponse obtenerInfoSustituido(DTORequestListaSustituido requestListaSustituido) {
        ModelGenericResponse response = new ModelGenericResponse();
        response.setCode(200);
        response.setData(bsdCommons.obtenerInfoSustituido(requestListaSustituido));
        return response;
    }

    @Override
    public ModelGenericResponse obtenerListaSustitutosSupervisores(
            @Valid @RequestBody DTORequestObtenerSustitutos sustRequest) {
        return bsdCatalogosSupycap.obtenerListaSustitutosSupervisores(sustRequest.getIdDetalleProceso(),
                sustRequest.getIdParticipacion());
    }

    @Override
    public ModelGenericResponse obtenerListaSustitutosCapacitadores(@Valid DTORequestObtenerSustitutos sustRequest) {
        return bsdCatalogosSupycap.obtenerListaSustitutosCapacitadores(sustRequest.getIdDetalleProceso(),
                sustRequest.getIdParticipacion());
    }

    @Override
    public ModelGenericResponse obtenerAspiranteSustituto(@Valid DTORequestObtenerAspSustituto sustitutoRequest) {
        return bsdCatalogosSupycap.obtenerSustituto(sustitutoRequest.getIdProcesoElectoral(),
                sustitutoRequest.getIdDetalleProceso(), sustitutoRequest.getIdParticipacion(),
                sustitutoRequest.getIdAspirante());
    }

    @Override
    public ModelGenericResponse obtenerFotoAsp(DTORequestLoadFoto requestFoto) {
        return bsdCommons.obtenerBase64FotoGluster(requestFoto.getUrlFoto());
    }

    @Override
    public ModelGenericResponse obtenerInformacionSustitucion(@Valid DTORequestObtenerAspSustituto sustitutoRequest) {
        ModelGenericResponse response = new ModelGenericResponse();
        ModelResponseInfoSustitucion responseInfoSustitucion = bsdCatalogosSupycap.obtenerInformacionSustitucion(
                sustitutoRequest.getIdProcesoElectoral(), sustitutoRequest.getIdDetalleProceso(),
                sustitutoRequest.getIdParticipacion(), sustitutoRequest.getIdAspirante(),
                sustitutoRequest.getTipoCausaVacante(), sustitutoRequest.getIdSustitucion());

        response.setCode(200);
        response.setMessage("Obteniendo informacion de la sustitucion");
        response.setData(responseInfoSustitucion);
        return response;
    }
}