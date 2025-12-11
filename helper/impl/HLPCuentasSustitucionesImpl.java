package mx.ine.sustseycae.helper.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.ine.sustseycae.bsd.impl.BSDAdminGestionCuentas;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.helper.HLPCuentasSustitucionesInterface;

@Service("hlpCuentasSustituciones")
@Scope("prototype")
public class HLPCuentasSustitucionesImpl implements HLPCuentasSustitucionesInterface {

    private static final Log log = LogFactory.getLog(HLPCuentasSustitucionesImpl.class);

    private final DTOAspirantes SIN_INFORMACION = null;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void adminCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta, DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        try {

            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(aspiranteCrearCuenta,
                    aspiranteModificarCuenta, aspiranteEliminarCuenta, usuario);

            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();

        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - adminCuentasSustitucion ", e);
        }

    }

    @Override
    public void crearCuentaSustitucion(DTOAspirantes aspiranteCrearCuenta, String usuario) {
        try {

            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(aspiranteCrearCuenta,
                    SIN_INFORMACION, SIN_INFORMACION, usuario);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();
        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - crearCuentaSustitucion ", e);
        }

    }

    @Override
    public void modificarCuentaSustitucion(DTOAspirantes aspiranteModificarCuenta, String usuario) {
        try {
            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(SIN_INFORMACION,
                    aspiranteModificarCuenta, SIN_INFORMACION, usuario);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();
        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - modificarCuentaSustitucion ", e);
        }

    }

    @Override
    public void eliminarCuentaSustitucion(DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        try {
            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(SIN_INFORMACION, SIN_INFORMACION,
                    aspiranteEliminarCuenta, usuario);
            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();
        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - eliminarCuentaSustitucion ", e);
        }

    }

    @Override
    public void crearYeliminarCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        try {
            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(aspiranteCrearCuenta,
                    SIN_INFORMACION, aspiranteEliminarCuenta, usuario);

            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();

        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - crearYeliminarCuentasSustitucion ", e);
        }
    }

    @Override
    public void modificarYeliminarCuentasSustitucion(DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        try {
            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(SIN_INFORMACION,
                    aspiranteModificarCuenta, aspiranteEliminarCuenta, usuario);

            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();

        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - modificarYeliminarCuentasSustitucion ", e);
        }

    }

    @Override
    public void crearYmodificarCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta,
            DTOAspirantes aspiranteModificarCuenta, String usuario) {
        try {
            BSDAdminGestionCuentas bsdAdminGestionCuentas = new BSDAdminGestionCuentas(aspiranteCrearCuenta,
                    aspiranteModificarCuenta, SIN_INFORMACION, usuario);

            applicationContext.getAutowireCapableBeanFactory().autowireBean(bsdAdminGestionCuentas);
            bsdAdminGestionCuentas.start();

        } catch (Exception e) {
            log.error("ERROR HLPCuentasSustitucionesImpl - crearYmodificarCuentasSustitucion ", e);
        }

    }

}
