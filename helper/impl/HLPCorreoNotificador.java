package mx.ine.sustseycae.helper.impl;

import java.io.File;
import java.util.List;

import mx.ine.sustseycae.util.Constantes;
import mx.ine.sustseycae.util.ServicioPostal;

public abstract class HLPCorreoNotificador {

    protected List<String> correos;
    protected StringBuilder asunto;
    protected StringBuilder cuerpo;
    protected List<File> adjuntos;
    protected String nombreComprobante;
    protected byte[] bytes;

    public HLPCorreoNotificador() {
        asunto = new StringBuilder();
        cuerpo = new StringBuilder();
    }

    public HLPCorreoNotificador(List<String> correos) {
        this();
        this.correos = correos;
    }

    public void envioNotificacionConPdf(boolean correoError, String carpetaSupycap) throws Exception {
        getAsuntoCorreo();
        StringBuilder sb = new StringBuilder();
        sb.append(Constantes.ETIQUETA_CORREOCREACIONCTA_P1).append("\n\n");
        sb.append(Constantes.ETIQUETA_CORREOCREACIONCTA_P2_NOT).append("\n\n");
        sb.append(Constantes.ETIQUETA_CORREOCREACIONCTA_P3_NOT).append("\n\n");
        sb.append(Constantes.ETIQUETA_CORREOCREACIONCTA_P4);

        ServicioPostal.envioMensajeConPdf(asunto.toString(), sb.toString(), correos, bytes, nombreComprobante, null,
                null, null, carpetaSupycap);
    }

    public void envioAcuseConPdf(boolean correoError, String nombreSO, String uid, String rutaGlusterSistemasDeceyec,
            String carpetaSupycap) throws Exception {
        getAsuntoCorreo();
        ServicioPostal.envioMensajeConPdf(asunto.toString(), "", correos, bytes, nombreComprobante, nombreSO,
                uid, rutaGlusterSistemasDeceyec, carpetaSupycap);

    }

    public String getNombreComprobante() {
        return nombreComprobante;
    }

    public void setNombreComprobante(String nombreComprobante) {
        this.nombreComprobante = nombreComprobante;
    }

    protected abstract void getAsuntoCorreo();

    protected abstract void getCuerpoCorreo();

    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }

    public List<File> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<File> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
