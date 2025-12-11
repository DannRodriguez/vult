package mx.ine.sustseycae.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import mx.ine.sustseycae.models.requests.DTORequestSutSEyCAE;
import mx.ine.sustseycae.models.responses.ModelGenericResponse;

@RestController
@RequestMapping("ws")
public interface ControllerSustSEyCAE {

    @PostMapping(path = "/guardarSustitucionSEyCAE", consumes = "multipart/form-data", produces = "application/json")
    public @ResponseBody
    ModelGenericResponse guardarSustitucionSEyCAE(
            @RequestPart(value = "fileExpediente", required = false) MultipartFile fileExpediente,
            @RequestPart("requestSustSEyCAE") DTORequestSutSEyCAE requestSust, HttpServletRequest request);

}
