package mx.ine.sustseycae.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.activation.CommandMap;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.activation.MailcapCommandMap;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

public final class Mensajero {

    private static Log log = LogFactory.getLog(ServicioPostal.class);

    private static Mensajero instancia;

    private String usernameFrom;
    private String pasFrom;
    private String userFrom;

    private static final String MAIL_JNDI = "java:jboss/MailSOA";
    private static final String PROTOCOLO_SMPT = "smtp";
    private static Map<String, Session> dataSourceCache = new HashMap<>();

    private Mensajero(String usernameFrom, String passwordFrom, String userFrom) {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        this.usernameFrom = usernameFrom;
        this.pasFrom = passwordFrom;
        this.userFrom = userFrom;
    }

    public static Mensajero getInstancia(String usernameFrom, String passwordFrom, String userFrom) {
        if (instancia == null) {
            instancia = new Mensajero(usernameFrom, passwordFrom, userFrom);
            return instancia;
        } else {
            return instancia;
        }
    }

    public Session getSession() {
        try {
            if (dataSourceCache.containsKey(MAIL_JNDI)) {
                return dataSourceCache.get(MAIL_JNDI);
            } else {
                Context ctx = new InitialContext();
                Session ds = (Session) ctx.lookup(MAIL_JNDI);
                dataSourceCache.put(MAIL_JNDI, ds);
                return ds;
            }
        } catch (Exception e) {
            log.error("ERROR Mensajero - getSession: ", e);
            return null;
        }
    }

    public void envioMensajeSimple(String asunto, String contenido, List<String> usuariosTo, String rutaGluster)
            throws MessagingException {
        Session session = getSession();
        if (session == null) {
            return;
        }

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userFrom));
        message.setSubject(asunto, "utf-8");
        message.setSentDate(new Date());
        message.setText(contenido);
        message.setContent(contenido, "text/html; charset=utf-8");

        int indexTipoEnvio = 0;
        int indexEmail = 1;

        for (String cuenta : usuariosTo) {
            try {
                if (cuenta.indexOf('-') != -1) {
                    String[] tipoEnvio = cuenta.split("-");
                    switch (tipoEnvio[indexTipoEnvio]) {
                        case Constantes.PARA:
                            message.addRecipients(Message.RecipientType.TO, tipoEnvio[indexEmail]);
                            break;
                        case Constantes.CC:
                            message.addRecipients(Message.RecipientType.CC, tipoEnvio[indexEmail]);
                            break;
                        case Constantes.CCO:
                            message.addRecipients(Message.RecipientType.BCC, tipoEnvio[indexEmail]);
                            break;
                    }
                } else {
                    message.addRecipients(Message.RecipientType.TO, cuenta);
                    break;
                }
            } catch (Exception e) {
                log.error("ERROR Mensajero - envioMensajeSimple [Cuenta]" + cuenta + "[Usuarios]" + usuariosTo + ": ",
                        e);
            }

        }

        if (rutaGluster != null) {

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(contenido, "text/html");
            multipart.addBodyPart(messageBodyPart);

            DataSource fds = new FileDataSource(rutaGluster + Constantes.IMAGEN_SEGUIMIENTO);
            DataSource fds2 = new FileDataSource(rutaGluster + Constantes.IMAGEN_FOOTER);

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image1>");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fds2));
            messageBodyPart.setHeader("Content-ID", "<image2>");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
        }

        message.saveChanges();
        Transport transport = session.getTransport(PROTOCOLO_SMPT);

        try {
            transport.connect(usernameFrom, pasFrom);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            log.error("ERROR Mensajero - envioMensajeSimple: ", e);
        } finally {
            transport.close();
        }
    }

    public void envioMensajeConAdjunto(String asunto, String contenido, List<String> usuariosTo, String cuentaDeEnvio,
            List<File> archivos) throws MessagingException {
        Session session = getSession();
        if (session == null) {
            return;
        }

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userFrom));
        message.setSubject(asunto);
        message.setSentDate(new Date());
        message.setText(contenido);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(contenido);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        for (int i = 0; i < archivos.size(); i++) {
            File file = archivos.get(i);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file.getAbsolutePath());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
        }

        for (String cuenta : usuariosTo) {
            message.addRecipients(Message.RecipientType.TO, cuenta);
        }

        message.saveChanges();
        Transport transport = session.getTransport(PROTOCOLO_SMPT);
        try {
            transport.connect(usernameFrom, pasFrom);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            log.error("ERROR Mensajero - envioMensajeConAdjunto: ", e);
        } finally {
            transport.close();
        }
    }

    public void enviaMensajeConPdf(String asunto, String contenido, List<String> usuariosTo, String cuentaDeEnvio,
            byte[] bytes, String nombreArchivo, String nombreSO, String uid, String rutaGlusterSistemasDeceyec,
            String carpetaSupycap) throws MessagingException {

        Session session = getSession();
        if (session == null) {
            return;
        }

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userFrom));
        message.setSubject(asunto, "utf-8");
        message.setSentDate(new Date());

        if (nombreSO != null && rutaGlusterSistemasDeceyec != null) {
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(generarPlantillaCorreoAcuseCuenta(nombreSO, uid), "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            DataSource fds = new FileDataSource(
                    rutaGlusterSistemasDeceyec + carpetaSupycap + "/resources/imgSUPyCAP/seguimiento.png");
            DataSource fds2 = new FileDataSource(
                    rutaGlusterSistemasDeceyec + carpetaSupycap + "/resources/imgSUPyCAP/footer.png");

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image1>");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fds2));
            messageBodyPart.setHeader("Content-ID", "<image2>");
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart pdfBodyParth = new MimeBodyPart();
            DataSource dataSourceh = new ByteArrayDataSource(bytes, "application/pdf");
            pdfBodyParth.setDataHandler(new DataHandler(dataSourceh));
            pdfBodyParth.setFileName(nombreArchivo + ".pdf");

            multipart.addBodyPart(pdfBodyParth);

            message.setContent(multipart);
        } else {
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(contenido, "text/html; charset=utf-8");

            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName(nombreArchivo + ".pdf");

            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            message.setContent(mimeMultipart);
        }

        for (String cuenta : usuariosTo) {
            message.addRecipients(Message.RecipientType.TO, cuenta);
        }

        message.addRecipient(Message.RecipientType.BCC,
                new InternetAddress(Constantes.CORREO_NOTIFICACION_ACUSE_CUENTA));
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress("supycapine@gmail.com"));

        message.saveChanges();

        Transport transport = session.getTransport(PROTOCOLO_SMPT);
        try {
            transport.connect(obtenerNombreUsuario(usernameFrom), pasFrom);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            log.error("ERROR Mensajero -enviaMensajeConPdf: ", e);
        } finally {
            transport.close();
            bytes = null;
        }
    }

    private String obtenerNombreUsuario(String correoIne) {
        if (correoIne != null && correoIne.contains("@")) {
            return correoIne.split("@")[0];
        }
        return correoIne;
    }

    private String generarPlantillaCorreoAcuseCuenta(String nombreAspirante, String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<div class=\"body-wrap\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;  font-size: 14px; width: 900px; background-color: #fff; margin: 0;\">\r\n"
                + "        <tbody>\r\n"
                + "            <tr style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; font-size: 14px; margin: 0;\">\r\n"
                + "                <td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;  font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\r\n"
                + "                <td class=\"container\" width=\"600\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;  font-size: 14px; vertical-align: top; display: block !important; width: 900px !important; clear: both !important; margin: 0 auto;\"\r\n"
                + "                    valign=\"top\">\r\n"
                + "                    <div class=\"content\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; font-size: 14px; width: 900px; display: block; margin: 0 auto; padding: 20px;\">\r\n"
                + "                        <div class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; font-size: 14px; border-radius: 3px; background-color: #fff; margin: 0; \">\r\n"
                + "                            <tbody>\r\n"
                + "                                <div style=\"border-bottom: 4px solid #e71b7a; font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;  font-size: 14px; margin-bottom: 15px; \">\r\n"
                + " <img src=\"cid:image1\" />" + "                                </div>\r\n"
                + "                                <div class=\"content\" style=\"font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; box-sizing: border-box; font-size: 22px; width: 900px; display: block; margin: 0 auto; margin-bottom: 30px; margin-top: 30px;\">\r\n"
                + "                                    <div class=\"main\"  cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; box-sizing: border-box; font-size: 22px; border-radius: 3px; background-color: #fff; margin-left: 15px; \">\r\n"
                + "                                        <div style=\"margin-top: 20px;\"> \u00a1Te damos la bienvenida "
                + nombreAspirante + "!</div>\r\n" + "                                    </div>\r\n"
                + "                                </div>\r\n"
                + "                                <div class=\"content\" style=\"font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; box-sizing: border-box; font-size: 22px; width: 900px; display: block; margin: 0 auto; margin-bottom: 30px; margin-top: 30px;\">\r\n"
                + "                                    <div class=\"main\"  cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: Arial, Helvetica, sans-serif; box-sizing: border-box; font-size: 16px; border-radius: 3px; background-color: #fff; margin-left: 15px; \">\r\n"
                + "                    <div style=\"text-align: justify;\">La cuenta con el nombre de usuario "
                + uid + " se cre&oacute; con &eacute;xito</div>\r\n"
                + "                    <div style=\"text-align: justify;\">en el sistema de Seguimiento de SE y CAE.</div>\r\n"
                + "                    <div style=\"text-align: justify;\">El acuse con los detalles de la nueva cuenta se encuentra adjunto en este correo.</div>\r\n <br><br>"
                + "                    <div style=\"text-align: justify;\">Agradecemos tu participaci&oacute;n.</div>\r\n <br><br>"
                + "                    <div style=\"text-align: justify;\">En caso de que tengas alguna duda con tu cuenta,</div>\r\n"
                + "                    <div style=\"text-align: justify;\">comunicate al Centro de Atenci&oacute;n a Usuarios CAU:</div>\r\n"
                + "                    <div style=\"text-align: justify;\">Servicio en l&iacute;nea: "
                + Constantes.MAIL_CAU + "</div>\r\n"
                + "                                    </div>\r\n"
                + "                                </div><br><br>\r\n"
                + "                                <div class=\"content\" style=\"width: 900px; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; box-sizing: border-box; font-size: 22px; width: 900px; display: block; margin: 0 auto; margin-bottom: 30px; margin-top: 30px;\">\r\n"
                + "                                    <div class=\"main\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family: Arial, Helvetica, sans-serif; box-sizing: border-box; font-size: 16px; border-radius: 3px;  margin: 0; text-align: center;\">\r\n"
                + "                                        <div>Este correo se envi&oacute; de manera autom&aacute;tica, favor de no responderlo.</div>\r\n"
                + "                                    </div>\r\n"
                + "                                </div>\r\n"
                + "                                <div style=\" width: 900px; font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;  font-size: 14px; margin-bottom: 15px; \">\r\n"
                + " <img src=\"cid:image2\" style=\"width: 900px; height: 110px;\"/>"
                + "                                </div>\r\n" + "                            </tbody>\r\n"
                + "                        </div>\r\n" + "                    </div>\r\n"
                + "                </td>\r\n"
                + "                <td style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif; box-sizing: border-box; font-size: 14px; vertical-align: top; margin: 0;\" valign=\"top\"></td>\r\n"
                + "            </tr>\r\n" + "        </tbody>\r\n" + "    </div>");
        return sb.toString();
    }

}
