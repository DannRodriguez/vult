package mx.ine.sustseycae.as;

import mx.ine.sustseycae.dto.DTOListaSustituido;
import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;

public interface ASCommons {

    public DTOListaSustituido obtenerListaSustituido(DTORequestListaSustituido requestListaSustituido);

    public DTOAspirante obtenerInfoSustituido(DTORequestListaSustituido requestListaSustituido);

    public DTOAspirante obtenerInfoSustituido(Integer idDetallePoceso, Integer idParticipacion,
            Integer idAspirante);

    public DTOCEtiquetas obtenerEtiquetaCEtiquetas(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idEtiqueta);

    public void actualizarURLFotoAspirante(String urlFoto, Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante);

    public Integer obtieneEtapaActual(Integer idDetalleProceso);

}
