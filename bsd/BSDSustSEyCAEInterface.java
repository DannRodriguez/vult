package mx.ine.sustseycae.bsd;

import org.springframework.web.multipart.MultipartFile;

import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;

public interface BSDSustSEyCAEInterface {

    public String guardarSustitucionSEyCAE(DTORequestSutSEyCAE request, MultipartFile fileExpediente) throws Exception;

    public String modificarSustitucionSEyCAE(DTORequestSutSEyCAE request, MultipartFile fileExpediente)
            throws Exception;

}
