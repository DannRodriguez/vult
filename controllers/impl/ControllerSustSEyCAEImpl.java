package mx.ine.sustseycae.controllers.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.bsd.BSDSustSEyCAEInterface;
import mx.ine.sustseycae.controllers.ControllerSustSEyCAE;
import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Controller
public class ControllerSustSEyCAEImpl implements ControllerSustSEyCAE {

    private static final Log log = LogFactory.getLog(ControllerSustSEyCAEImpl.class);

    @Autowired
    @Qualifier("bsdSustSEyCAE")
    private BSDSustSEyCAEInterface bsdSustSEyCAE;

    @Override
    public ModelGenericResponse guardarSustitucionSEyCAE(MultipartFile fileExpediente, DTORequestSutSEyCAE requestSust,
            HttpServletRequest request) {

        ModelGenericResponse response = new ModelGenericResponse();

        try {
            requestSust.setIpUsario(ApplicationUtil.obtenerIpCliente(request));
            if (requestSust.getTipoAccion().equals(Constantes.FLUJO_CAPTURA)) {
                response.setMessage(bsdSustSEyCAE.guardarSustitucionSEyCAE(requestSust, fileExpediente));

            } else if (requestSust.getTipoAccion().equals(Constantes.FLUJO_MODIFICA)) {
                response.setMessage(bsdSustSEyCAE.modificarSustitucionSEyCAE(requestSust, fileExpediente));
            }
            response.setCode(200);
            return response;
        } catch (Exception e) {
            log.error("ERROR ControllerSustSEyCAEImpl - guardarSustitucionSEyCAE: ", e);
            response.setCode(500);
            response.setCausa(e.getMessage());
            return response;
        }
    }

}
