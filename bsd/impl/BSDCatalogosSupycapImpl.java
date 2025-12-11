package mx.ine.sustseycae.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import mx.ine.sustseycae.as.impl.ASCatalogosSupycapImpl;
import mx.ine.sustseycae.bsd.BSDCatalogosSupycap;
import mx.ine.sustseycae.dto.db.DTOCCausasVacante;
import mx.ine.sustseycae.models.requests.ModelRequestFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseFechasSustituciones;
import mx.ine.sustseycae.models.responses.ModelResponseInfoSustitucion;

@Controller
public class BSDCatalogosSupycapImpl implements BSDCatalogosSupycap {

    @Autowired
    private ASCatalogosSupycapImpl asCatalogosSupycap;

    @Override
    public List<DTOCCausasVacante> obtenerListaCausasVacante() {
        return asCatalogosSupycap.obtenerListaCausasVacante();
    }

    @Override
    public ModelResponseFechasSustituciones obtenerFechasSustituciones(ModelRequestFechasSustituciones request) {
        return asCatalogosSupycap.obtenerFechasSustituciones(request);
    }

    @Override
    public ModelGenericResponse obtenerListaSustitutosSupervisores(Integer idDetalleProceso,
            Integer idParticipacion) {
        return asCatalogosSupycap.obtenerListaSustitutosSupervisores(idDetalleProceso, idParticipacion);
    }

    @Override
    public ModelGenericResponse obtenerListaSustitutosCapacitadores(Integer idDetalleProceso, Integer idParticipacion) {
        return asCatalogosSupycap.obtenerListaSustitutosCapacitadores(idDetalleProceso, idParticipacion);
    }

    @Override
    public ModelGenericResponse obtenerSustituto(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante) {
        return asCatalogosSupycap.obtenerAspiranteSustituto(idProcesoElectoral, idDetalleProceso, idParticipacion,
                idAspirante);
    }

    @Override
    public ModelResponseInfoSustitucion obtenerInformacionSustitucion(Integer idProcesoElectoral,
            Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspiranteSustituido, Integer tipoCausaVacante, Integer idSustitucion) {
        return asCatalogosSupycap.obtenerInformacionSustitucion(idProcesoElectoral, idDetalleProceso, idParticipacion,
                idAspiranteSustituido, tipoCausaVacante, idSustitucion);
    }

}
