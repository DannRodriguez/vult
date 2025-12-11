package mx.ine.sustseycae.util;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.mail.MessagingException;
import mx.ine.sustseycae.dto.DTOCorreoServicio;

public class ServicioPostal {

    private static Log log = LogFactory.getLog(ServicioPostal.class);

    private static String usernameFrom;
    private static String passFrom;
    private static String cuentaDeEnvio;

    static {
        try {
            DTOCorreoServicio dtoCorreoServicio = (DTOCorreoServicio) ApplicationContextUtils.getApplicationContext().getBean("cuentaCorreo");
            if (dtoCorreoServicio != null) {
                cuentaDeEnvio = dtoCorreoServicio.getCuentaDeEnvio();
                usernameFrom = dtoCorreoServicio.getUsernameFrom();
                passFrom = dtoCorreoServicio.getPasswordFrom();
            }
        } catch (Exception e) {
            log.error("ERROR ServicioPostal - static", e);
            cuentaDeEnvio = Constantes.APPLICATION_CORREO_CUENTA;
        }
    }

    public ServicioPostal() {
    }

    public static void envia(String asunto, String contenido, List<String> arrayTO, String rutaGluster)
            throws Exception {
        Mensajero mensajero = Mensajero.getInstancia(usernameFrom, passFrom, cuentaDeEnvio);
        try {
            mensajero.envioMensajeSimple(asunto, contenido, arrayTO, rutaGluster);
        } catch (Exception e) {
            log.error("ERROR ServicioPostal - envia: ", e);
            throw new Exception("ERROR ServicioPostal - envia.", e);
        }
    }

    public static void envioMensajeConAdjunto(String asunto, String contenido, List<String> usuariosTo,
            List<File> archivos) throws Exception {
        Mensajero mensajero = Mensajero.getInstancia(usernameFrom, passFrom, cuentaDeEnvio);
        try {
            mensajero.envioMensajeConAdjunto(asunto, contenido, usuariosTo, cuentaDeEnvio, archivos);
        } catch (MessagingException e) {
            log.error("ERROR ServicioPostal - envioMensajeConAdjunto: ", e);
            throw new Exception("ERROR ServicioPostal - envioMensajeConAdjunto: ", e);
        }
    }

    public static void envioMensajeConPdf(String asunto, String contenido, List<String> usuariosTo, byte[] bytes,
            String nombreArchivo, String nombreSO, String uid, String rutaGlusterSistemasDeceyec, String carpetaSupycap)
            throws Exception {
        Mensajero mensajero = Mensajero.getInstancia(usernameFrom, passFrom, cuentaDeEnvio);
        try {
            mensajero.enviaMensajeConPdf(asunto, contenido, usuariosTo, cuentaDeEnvio, bytes, nombreArchivo, nombreSO,
                    uid, rutaGlusterSistemasDeceyec, carpetaSupycap);
        } catch (MessagingException e) {
            log.error("ERROR ServicioPostal - envioMensajeConPdf: ", e);
            throw new Exception("ERROR ServicioPostal - envioMensajeConPdf:", e);
        }
    }
}
