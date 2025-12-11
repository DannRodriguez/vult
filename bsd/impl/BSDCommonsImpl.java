package mx.ine.sustseycae.bsd.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.DatatypeConverter;
import mx.ine.sustseycae.as.impl.ASCommonsImpl;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.dto.DTOListaSustituido;
import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.models.requests.DTORequestListaSustituido;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Service("bsdCommons")
@Scope("prototype")
public class BSDCommonsImpl implements BSDCommons {

    private Log log = LogFactory.getLog(BSDCommonsImpl.class);

    @Autowired
    private ASCommonsImpl asCommons;

    @Autowired
    @Qualifier("pathGlusterSistDECEYEC")
    private String pathGlusterSistDECEYEC;

    @Override
    public DTOListaSustituido obtenerListaSustituido(DTORequestListaSustituido requestListaSustituido) {
        return asCommons.obtenerListaSustituido(requestListaSustituido);
    }

    @Override
    public DTOAspirante obtenerInfoSustituido(DTORequestListaSustituido requestListaSustituido) {
        return asCommons.obtenerInfoSustituido(requestListaSustituido);
    }

    @Override
    public ModelGenericResponse obtenerBase64FotoGluster(String urlFoto) {
        ModelGenericResponse response = new ModelGenericResponse();
        try {
            File expediente = new File(urlFoto);
            if (expediente.exists()) {
                String mimeType = URLConnection.guessContentTypeFromName(expediente.getName());
                String base64 = "";

                try (InputStream file = new FileInputStream(urlFoto)) {
                    byte[] imageBytes = new byte[file.available()];
                    if (file.read(imageBytes) > 0) {
                        base64 = java.util.Base64.getEncoder().encodeToString(imageBytes);
                    }
                    response.setData("data:" + mimeType + ";base64," + base64);
                    response.setCode(Constantes.RESPONSE_CODE_200);
                    response.setMessage(Constantes.ESTATUS_EXITO);
                    return response;
                }

            } else {
                response.setStatus(Constantes.ESTATUS_ADVERTENCIA);
                response.setCode(Constantes.RESPONSE_CODE_400);
                response.setMessage("No se encontró la foto del aspirante en gluster.");
                return response;
            }
        } catch (Exception e) {
            log.error("ERROR BSDCommonsImpl -obtenerBase64FotoGluster: " + urlFoto + " - ", e);
            response.setStatus(Constantes.ESTATUS_ERROR);
            response.setCode(Constantes.RESPONSE_CODE_500);
            response.setMessage("Ocurrió un error al obtener la foto del aspirante.");
            return response;
        }
    }

    @Override
    public String almacenarFotoAspirante(String base64Foto, String extensionArchivo, Integer idProcesoElectoral,
            Integer idDetalleProceso, Integer idParticipacion, Integer idAspirante) {
        String fullPathFoto = "";

        if (base64Foto != null && !base64Foto.isBlank()) {
            try {
                DTOCEtiquetas etiquetaRutaFoto = asCommons.obtenerEtiquetaCEtiquetas(
                        idProcesoElectoral,
                        idDetalleProceso,
                        Constantes.ID_ETIQUETA_CARPETA_FOTOS);
                String nombreArchivoFoto = new StringBuilder("P").append(idProcesoElectoral)
                        .append("D").append(idDetalleProceso)
                        .append("PART").append(idParticipacion)
                        .append("ASP").append(idAspirante)
                        .append(extensionArchivo).toString();

                fullPathFoto = ApplicationUtil.verifyPathSuffix(pathGlusterSistDECEYEC)
                        + ApplicationUtil.verifyPathSuffix(etiquetaRutaFoto.getEtiqueta())
                        + nombreArchivoFoto;

                String base64File = base64Foto.split(",")[1];
                byte[] decodedBytesFotos = DatatypeConverter.parseBase64Binary(base64File);

                File file = new File(fullPathFoto);

                FileUtils.writeByteArrayToFile(file, decodedBytesFotos);

                asCommons.actualizarURLFotoAspirante(
                        fullPathFoto,
                        idProcesoElectoral,
                        idDetalleProceso,
                        idParticipacion,
                        idAspirante);

            } catch (Exception e) {
                fullPathFoto = "";
                log.error("BSDCommonsImpl -almacenarFotoAspirante: ", e);
            }
        }

        return fullPathFoto;
    }

}
