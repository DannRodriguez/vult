package mx.ine.sustseycae.bsd.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.ine.sustseycae.bsd.BSDGestionCuentasInterface;
import mx.ine.sustseycae.dto.db.DTOAspirantes;

public class BSDAdminGestionCuentas extends Thread {

    private static final Log log = LogFactory.getLog(BSDAdminGestionCuentas.class);

    @Autowired
    @Qualifier("bsdGestionCuentas")
    private BSDGestionCuentasInterface bsdGestionCuentas;

    private DTOAspirantes aspiranteCrearCuenta;
    private DTOAspirantes aspiranteModificarCuenta;
    private DTOAspirantes aspiranteEliminarCuenta;
    private String usuario;

    public BSDAdminGestionCuentas(DTOAspirantes aspiranteCrearCuenta, DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario) {
        this.aspiranteCrearCuenta = aspiranteCrearCuenta;
        this.aspiranteModificarCuenta = aspiranteModificarCuenta;
        this.aspiranteEliminarCuenta = aspiranteEliminarCuenta;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        try {

            bsdGestionCuentas.adminCuentasSustitucion(aspiranteCrearCuenta, aspiranteModificarCuenta,
                    aspiranteEliminarCuenta, usuario);

        } catch (Exception e) {
            log.error("ERROR BSDAdminGestionCuentas - run ", e);
        }

    }

}
