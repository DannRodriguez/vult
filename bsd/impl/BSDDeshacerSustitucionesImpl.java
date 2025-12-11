package mx.ine.sustseycae.bsd.impl;

import mx.ine.sustseycae.as.ASDeshacerSustituciones;
import mx.ine.sustseycae.bsd.BSDDeshacerSustituciones;
import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;
import mx.ine.sustseycae.models.requests.DTORequestConsultaDeshacerSustituciones;
import mx.ine.sustseycae.models.requests.DTORequestDeshacerSustitucion;
import mx.ine.sustseycae.util.Exceptions.ExceptionValidacionAreZore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BSDDeshacerSustitucionesImpl implements BSDDeshacerSustituciones {

        @Autowired
        private ASDeshacerSustituciones asDeshacerSustituciones;

        @Override
        public List<VOConsultaDesSustitucionesSupycap> consultaDeshacerSustitucion(
                DTORequestConsultaDeshacerSustituciones deshacerSustituciones) {
            return asDeshacerSustituciones.consultaDeshacerSustitucion(deshacerSustituciones);
        }

        @Override
        public List<VOConsultaDesSustitucionesSupycap> consultaSustitucionesDeshechas(
                DTORequestConsultaDeshacerSustituciones deshacerSustituciones) {
            return asDeshacerSustituciones.consultaSustitucionesDeshechas(deshacerSustituciones);
        }

        @Override
        public void deshacerSustitucion(DTORequestDeshacerSustitucion requestSustitucion)
                throws ExceptionValidacionAreZore {
                asDeshacerSustituciones.deshacerSustitucion(requestSustitucion);
        }
}
