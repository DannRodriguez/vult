package mx.ine.sustseycae.controllers.impl;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.bsd.BSDLogin;
import mx.ine.sustseycae.controllers.ControllerLogin;
import mx.ine.sustseycae.models.requests.DTORequestCerrarSesion;
import mx.ine.sustseycae.models.requests.DTORequestIniciarSesion;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ControllerLoginImpl implements ControllerLogin {

    @Autowired
    private BSDLogin bsdLogin;

    @Override
    public ModelGenericResponse login(DTORequestIniciarSesion loginRequest, HttpServletRequest request) {
        return bsdLogin.login(loginRequest, request);
    }

    @Override
    public ModelGenericResponse refreshToken(Map<String, Object> body, HttpServletRequest request) {
        return bsdLogin.tokenRefresh((String) body.get("tknR"));
    }

    @Override
    public ModelGenericResponse cierraSesion(DTORequestCerrarSesion loginRequest) {
        return bsdLogin.cierraSesion(loginRequest);
    }

    @Override
    public ModelGenericResponse cierraSesionForc(DTORequestCerrarSesion loginRequest) {
        return bsdLogin.cierraSesionForc(loginRequest);
    }

}
