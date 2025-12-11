package mx.ine.sustseycae.as;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.models.requests.DTORequestCerrarSesion;
import mx.ine.sustseycae.models.requests.DTORequestIniciarSesion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseLogin;

public interface ASLogin {

    public ModelResponseLogin login(DTORequestIniciarSesion loginRequest, HttpServletRequest request);

    public ModelGenericResponse tokenRefresh(String tokenRefresh);

    public ModelGenericResponse cierraSesion(DTORequestCerrarSesion loginRequest);

    public ModelGenericResponse cierraSesionForc(DTORequestCerrarSesion loginRequest);

}
