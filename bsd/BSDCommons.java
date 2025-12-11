package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.dto.DTOListaSustituido;
import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

public interface BSDCommons {

    public DTOListaSustituido obtenerListaSustituido(DTORequestListaSustituido requestListaSustituido);

    public DTOAspirante obtenerInfoSustituido(DTORequestListaSustituido requestListaSustituido);

    public ModelGenericResponse obtenerBase64FotoGluster(String urlFoto);

    public String almacenarFotoAspirante(String base64Foto, String extensionArchivo, Integer idProcesoElectoral,
            Integer idDetalleProceso, Integer idParticipacion, Integer idAspirante);

}
