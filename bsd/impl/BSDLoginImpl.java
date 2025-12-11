package mx.ine.sustseycae.bsd.impl;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.as.ASLogin;
import mx.ine.sustseycae.bsd.BSDLogin;
import mx.ine.sustseycae.models.requests.DTORequestCerrarSesion;
import mx.ine.sustseycae.models.requests.DTORequestIniciarSesion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import mx.ine.sustseycae.models.responses.ModelResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BSDLoginImpl implements BSDLogin {

    @Autowired
    private ASLogin asLogin;

    @Override
    public ModelResponseLogin login(DTORequestIniciarSesion loginRequest, HttpServletRequest request) {
        return asLogin.login(loginRequest, request);
    }

    @Override
    public ModelGenericResponse tokenRefresh(String tokenRefresh) {
        return asLogin.tokenRefresh(tokenRefresh);
    }

    @Override
    public ModelGenericResponse cierraSesion(DTORequestCerrarSesion loginRequest) {
        return asLogin.cierraSesion(loginRequest);
    }

    @Override
    public ModelGenericResponse cierraSesionForc(DTORequestCerrarSesion loginRequest) {
        return asLogin.cierraSesionForc(loginRequest);
    }

}
