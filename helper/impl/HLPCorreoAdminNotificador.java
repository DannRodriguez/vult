package mx.ine.sustseycae.helper.impl;

import mx.ine.sustseycae.util.Constantes;

public class HLPCorreoAdminNotificador extends HLPCorreoNotificador {

    private String cuenta;

    public HLPCorreoAdminNotificador() {

    }

    public HLPCorreoAdminNotificador(String cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    protected void getAsuntoCorreo() {
        if (cuenta == null || cuenta.isEmpty()) {
            asunto.append(Constantes.ETIQUETA_CORREO_CUENTA_ASUNTO);
        } else {
            asunto.append(Constantes.ETIQUETA_ADMINUSUARIOS_CORREO_PASS_ASUNTO);
        }
    }

    @Override
    protected void getCuerpoCorreo() {
        if (cuenta == null || cuenta.isEmpty()) {
            cuerpo.append(Constantes.ETIQUETA_CORREO_CUENTA_CONTENIDO);
        } else {
            cuerpo.append(Constantes.ETIQUETA_ADMINUSUARIOS_CORREO_PASS_CONTENIDO);
        }
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

}
