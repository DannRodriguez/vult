package mx.ine.sustseycae.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import mx.ine.sustseycae.bsd.impl.BSDGestionCuentasImpl;
import mx.ine.sustseycae.dto.DTOCPermisosCta;

public class AcuseCuenta {

    private static Log log = LogFactory.getLog(BSDGestionCuentasImpl.class);

    static String path_Gluster;

    private static Context context = null;

    static {
        try {
            context = new InitialContext();
            path_Gluster = (String) context.lookup("java:/util/glusterSistDECEYEC");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] generarComprobante(String nombreCompleto, String usuario, String us_password,
            String urlPoliticas, String urlCambioContrasenia, List<DTOCPermisosCta> permisos,
            String usuarioQueCreaCuenta, String carpetaSupycap) {

        BaseColor bordePrincipal = new BaseColor(207, 149, 168);
        BaseColor gris = new BaseColor(226, 226, 226);
        BaseColor blanco = new BaseColor(255, 255, 255, 255);
        BaseColor letraGuinda = new BaseColor(98, 26, 93);
        BaseColor guindaFuerte = new BaseColor(120, 0, 71);
        BaseColor azulFuerte = new BaseColor(6, 84, 102);
        BaseColor azulBajito = new BaseColor(216, 234, 242);
        BaseColor aqua = new BaseColor(4, 119, 119);
        BaseColor fondoRosaBajito = new BaseColor(224, 209, 223);

        Font fuenteGuinda11Negrita = new Font(FontFamily.HELVETICA, 11, Font.NORMAL, letraGuinda);
        Font fuenteGuinda16Negrita = new Font(FontFamily.HELVETICA, 16, Font.BOLD, letraGuinda);
        Font fuenteGuinda13 = new Font(FontFamily.HELVETICA, 13, Font.NORMAL, letraGuinda);
        Font fuenteGuinda13Negrita = new Font(FontFamily.HELVETICA, 13, Font.BOLD, letraGuinda);
        Font fuenteGuinda9 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, letraGuinda);
        Font fuenteAzulFuerte9 = new Font(FontFamily.HELVETICA, 9, Font.NORMAL, azulFuerte);
        Font fuenteGuindaFuerte10 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, guindaFuerte);
        Font fuenteGuindaFuerte10Negrita = new Font(FontFamily.HELVETICA, 10, Font.BOLD, guindaFuerte);
        Font fuenteAqua8 = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, aqua);
        Font fuenteAqua8Subrayado = new Font(FontFamily.HELVETICA, 8, Font.UNDERLINE, aqua);
        Font fuenteGuindaFuerte8 = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, guindaFuerte);

        Calendar now = Calendar.getInstance();
        String horaCompleta = "";
        String hora = "" + now.get(Calendar.HOUR_OF_DAY);
        String minutos = "" + now.get(Calendar.MINUTE);

        if (hora.length() == 1) {
            hora = "0" + hora;
        }
        if (minutos.length() == 1) {
            minutos = "0" + minutos;
        }

        horaCompleta = hora + ":" + minutos;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.LETTER, 5, 5, 50, 5);
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.addAuthor("INE");
            document.addTitle(Constantes.ETIQUETA_ACUSE_PDF_TITULO);
            document.open();
            PdfContentByte canvas = writer.getDirectContentUnder();
            String rutaImgs = "";

            try {

                String pathGluster = path_Gluster.endsWith("/") ? path_Gluster : path_Gluster + "/";

                StringBuilder path = new StringBuilder();
                path.append(pathGluster);
                path.append(carpetaSupycap);
                path.append(File.separator);
                path.append("resources/imgSUPyCAP");
                rutaImgs = path.toString();

                String rutaCompleta = rutaImgs + File.separator + "marcaDeAgua.png";
                Image image = Image.getInstance(rutaCompleta);
                image.scaleAbsolute(PageSize.LETTER);
                image.setAbsolutePosition(0, 0);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.6f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();
            } catch (Exception e) {
                log.error("ERROR AcuseCuenta - generarComprobante - marca de agua:", e);
            }

            String fecha = date2Str(new Date());
            Image image;
            PdfPTable tableLogo = new PdfPTable(new float[]{1, 3, 1});
            tableLogo.setWidthPercentage(90.0f);
            PdfPCell cell = new PdfPCell();
            try {
                String rutaCompleta = rutaImgs + File.separator + "IDCuentas.png";
                image = Image.getInstance(rutaCompleta);
                image.setAlignment(Image.LEFT);
                image.scaleAbsolute(80, 25);
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(2);
                cell.setBorderColorTop(bordePrincipal);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setBackgroundColor(gris);
                cell.addElement(image);
            } catch (MalformedURLException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - logo cuentas: ", e);
            } catch (IOException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - logo cuentas:", e);
            }
            tableLogo.addCell(cell);
            cell = new PdfPCell();
            Paragraph p = new Paragraph(Constantes.ETIQUETA_ACUSE_TITULO, fuenteGuinda16Negrita);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(2);
            cell.setBorderColorTop(bordePrincipal);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            cell.addElement(p);
            cell.setBackgroundColor(gris);
            tableLogo.addCell(cell);
            cell = new PdfPCell();
            try {
                String rutaCompleta = rutaImgs + File.separator + "logoINE.png";
                image = Image.getInstance(rutaCompleta);
                image.setAlignment(Image.RIGHT);
                image.scaleAbsolute(80, 25);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(2);
                cell.setBorderColorTop(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthBottom(0);
                cell.setBackgroundColor(gris);
                cell.addElement(image);
            } catch (MalformedURLException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - logo INE:", e);
            } catch (IOException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - logo INE:", e);
            }
            tableLogo.addCell(cell);
            document.add(tableLogo);

            PdfPTable table = new PdfPTable(16);
            table.setWidthPercentage(90.0f);
            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthBottom(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(9);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(Constantes.ETIQUETA_ACUSE_EMISION, fuenteAzulFuerte9);
            cell.addElement(p);
            cell.setColspan(5);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(16);
            table.setWidthPercentage(90.0f);

            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(9);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(Constantes.ETIQUETA_ACUSE_FECHA, fuenteAzulFuerte9);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(fecha, fuenteAzulFuerte9);
            cell.addElement(p);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(1);
            cell.setBorderColorBottom(azulFuerte);
            cell.setColspan(2);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(Constantes.ETIQUETA_ACUSE_HORA, fuenteAzulFuerte9);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(horaCompleta, fuenteAzulFuerte9);
            cell.addElement(p);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(1);
            cell.setBorderColorBottom(azulFuerte);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(16);
            table.setWidthPercentage(90.0f);
            cell = new PdfPCell();
            try {
                String rutaCompleta = rutaImgs + File.separator + "vigneta1.png";
                image = Image.getInstance(rutaCompleta);
                image.setAlignment(Image.RIGHT);
                image.scaleAbsolute(20, 20);
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.addElement(image);
            } catch (MalformedURLException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - vigneta1:", e);
            } catch (IOException e) {
                log.error("ERROR AcuseCuenta - generarComprobante - vigneta1:", e);
            }

            table.addCell(cell);

            cell = new PdfPCell();

            if (us_password != null) {
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_SALUDO, fuenteGuinda13);
            } else {
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_SALUDO_NOTIFICA, fuenteGuinda13);
            }
            cell.addElement(p);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            cell.setColspan(14);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(90.0f);
            table.getDefaultCell().setBorderWidthLeft(2);
            table.getDefaultCell().setBorderColorLeft(bordePrincipal);
            table.getDefaultCell().setBorderWidthRight(2);
            table.getDefaultCell().setBorderColorRight(bordePrincipal);
            table.getDefaultCell().setBorderWidthBottom(0);
            table.getDefaultCell().setBorderWidthTop(0);

            PdfPTable table2 = new PdfPTable(1);
            table2.setWidthPercentage(100.0f);

            cell = new PdfPCell();
            cell.setMinimumHeight(25);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table2.addCell(cell);

            table.addCell(table2);

            document.add(table);

            table = new PdfPTable(16);
            table.setWidthPercentage(90.0f);

            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            p = new Paragraph(Constantes.ETIQUETA_ACUSE_TITULAR, fuenteGuinda13Negrita);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingRight(7);
            cell.addElement(p);
            cell.setColspan(4);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();

            cell.setColspan(9);
            cell.setBackgroundColor(gris);
            p = new Paragraph(nombreCompleto, fuenteGuinda9);
            cell.setBorderColor(blanco);
            cell.addElement(p);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(2);
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(90.0f);
            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            cell.setMinimumHeight(10);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(16);
            table.setWidthPercentage(90.0f);

            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(4);
            p = new Paragraph(Constantes.ETIQUETA_ACUSE_NOMBRE_USUARIO, fuenteGuinda13Negrita);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setPaddingRight(7);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthRight(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(9);
            cell.setBackgroundColor(gris);
            p = new Paragraph(usuario, fuenteGuinda9);
            cell.setBorderColor(blanco);
            cell.addElement(p);
            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(2);
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthLeft(0);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);

            document.add(table);

            table = new PdfPTable(1);
            table.setWidthPercentage(90.0f);
            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            cell.setMinimumHeight(10);
            table.addCell(cell);

            document.add(table);

            if (us_password != null) {
                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(4);
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_NOMBRE_CONTRASENA, fuenteGuinda13Negrita);
                p.setAlignment(Element.ALIGN_RIGHT);
                cell.addElement(p);
                cell.setPaddingRight(7);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(9);
                cell.setBackgroundColor(gris);
                p = new Paragraph(us_password, fuenteGuinda9);
                cell.setBorderColor(blanco);
                cell.addElement(p);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(2);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                document.add(table);
            }

            if (permisos != null && !permisos.isEmpty()) {
                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);
                cell = new PdfPCell();
                try {
                    String rutaCompleta = rutaImgs + File.separator + "vigneta1.png";
                    image = Image.getInstance(rutaCompleta);
                    image.setAlignment(Image.RIGHT);
                    image.scaleAbsolute(20, 20);
                    cell.setBorderWidthLeft(2);
                    cell.setBorderColorLeft(bordePrincipal);
                    cell.setBorderWidthTop(0);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthBottom(0);
                    cell.addElement(image);
                } catch (MalformedURLException e) {
                    log.error("ERROR AcuseCuenta - generarComprobante - vigneta permisos:", e);
                } catch (IOException e) {
                    log.error("ERROR AcuseCuenta - generarComprobante - vigneta permisos:", e);
                }

                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(14);
                if (us_password != null) {
                    p = new Paragraph(Constantes.ETIQUETA_ACUSE_INDICACION_PERMISOS, fuenteGuinda13);
                } else {
                    p = new Paragraph(Constantes.ETIQUETA_ACUSE_INDICACION_PERMISOS_NOTIFICA, fuenteGuinda13);
                }
                cell.addElement(p);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(7);
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_SISTEMA, fuenteGuinda11Negrita);
                cell.addElement(p);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(7);
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_PERMISO, fuenteGuinda11Negrita);
                cell.addElement(p);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                for (DTOCPermisosCta permiso : permisos) {
                    cell = new PdfPCell();
                    cell.setBorderWidthLeft(2);
                    cell.setBorderColorLeft(bordePrincipal);
                    cell.setBorderWidthTop(0);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthBottom(0);
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.setColspan(7);
                    p = new Paragraph(permiso.getSistema(), fuenteGuinda9);
                    cell.addElement(p);
                    cell.setBackgroundColor(fondoRosaBajito);
                    cell.setBorderWidthLeft(1);
                    cell.setBorderColorLeft(bordePrincipal);
                    cell.setBorderWidthBottom(1);
                    cell.setBorderColorBottom(bordePrincipal);
                    cell.setBorderWidthTop(1);
                    cell.setBorderColorTop(bordePrincipal);
                    cell.setBorderWidthRight(0);
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.setColspan(7);
                    p = new Paragraph(permiso.getDescripcion(), fuenteGuinda9);
                    cell.addElement(p);
                    cell.setBackgroundColor(blanco);
                    cell.setBorderWidthTop(1);
                    cell.setBorderColorTop(bordePrincipal);
                    cell.setBorderWidthBottom(1);
                    cell.setBorderColorBottom(bordePrincipal);
                    cell.setBorderWidthRight(1);
                    cell.setBorderColorRight(bordePrincipal);
                    cell.setBorderWidthLeft(0);
                    table.addCell(cell);

                    cell = new PdfPCell();
                    cell.setBorderWidthRight(2);
                    cell.setBorderColorRight(bordePrincipal);
                    cell.setBorderWidthLeft(0);
                    cell.setBorderWidthTop(0);
                    cell.setBorderWidthBottom(0);
                    table.addCell(cell);
                }
                document.add(table);
                table = new PdfPTable(1);
                table.setWidthPercentage(90.0f);
                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(10);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_CAMBIO_PERMISOS + " ", fuenteAzulFuerte9);
                cell.addElement(p);
                cell.setColspan(14);
                cell.setBorderWidth(4);
                cell.setPaddingLeft(15);
                cell.setPaddingTop(5);
                cell.setPaddingRight(10);
                cell.setPaddingBottom(10);
                cell.setBorderColor(azulBajito);
                cell.setBackgroundColor(blanco);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                document.add(table);
            }

            table = new PdfPTable(1);
            table.setWidthPercentage(90.0f);
            cell = new PdfPCell();
            cell.setBorderWidthLeft(2);
            cell.setBorderColorLeft(bordePrincipal);
            cell.setBorderWidthRight(2);
            cell.setBorderColorRight(bordePrincipal);
            cell.setBorderWidthTop(0);
            cell.setBorderWidthBottom(0);
            cell.setMinimumHeight(10);
            table.addCell(cell);

            document.add(table);
            if (us_password != null) {
                table = new PdfPTable(1);
                table.setWidthPercentage(90.0f);
                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(20);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(1);
                table.setWidthPercentage(90.0f);
                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(10);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(1);
                cell.setBorderColorLeft(guindaFuerte);
                cell.setBorderWidthTop(1);
                cell.setBorderColorTop(guindaFuerte);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthTop(1);
                cell.setBorderColorTop(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                cell.setColspan(13);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();

                try {
                    String rutaCompleta = rutaImgs + File.separator + "iconoAdvertencia.png";
                    image = Image.getInstance(rutaCompleta);
                    image.setAlignment(Image.RIGHT);
                    image.scaleAbsolute(20, 20);
                    cell.setBorderWidthLeft(1);
                    cell.setBorderColorLeft(guindaFuerte);
                    cell.setBorderWidthRight(1);
                    cell.setBorderColorRight(guindaFuerte);
                    cell.setBorderWidthTop(0);
                    cell.setBorderWidthBottom(0);
                    cell.addElement(image);
                } catch (MalformedURLException e) {
                    log.error("ERROR AcuseCuenta - generarComprobante - iconoAdvertencia:", e);
                } catch (IOException e) {
                    log.error("ERROR AcuseCuenta - generarComprobante - iconoAdvertencia:", e);
                }

                table.addCell(cell);

                cell = new PdfPCell();
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_CAMBIO_CONTRASENIA, fuenteGuindaFuerte10);
                p.add("\n");
                Chunk chunk = new Chunk(urlCambioContrasenia, fuenteAqua8Subrayado);
                chunk.setAnchor(urlCambioContrasenia);
                p.add(chunk);
                cell.setPaddingLeft(20);
                cell.setPaddingRight(15);
                cell.setColspan(13);
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(1);
                cell.setBorderColorLeft(guindaFuerte);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setPaddingLeft(20);
                cell.setPaddingRight(15);
                cell.setColspan(13);
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(1);
                cell.setBorderColorLeft(guindaFuerte);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_ADVERTENCIA, fuenteGuindaFuerte10);
                p.add(new Chunk(Constantes.ETIQUETA_ACUSE_ADVERTENCIA_DOS, fuenteGuindaFuerte10Negrita));
                cell.addElement(p);
                cell.setPaddingLeft(20);
                cell.setPaddingRight(15);
                cell.setColspan(13);
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(1);
                cell.setBorderColorLeft(guindaFuerte);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_POLITICAS_USO + ": ", fuenteAqua8);
                chunk = new Chunk(urlPoliticas, fuenteAqua8Subrayado);
                chunk.setAnchor(urlPoliticas);
                p.add(chunk);
                cell.addElement(p);
                cell.setPaddingLeft(20);
                cell.setPaddingRight(15);
                cell.setColspan(13);
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(1);
                cell.setBorderColorLeft(guindaFuerte);
                cell.setBorderWidthBottom(1);
                cell.setBorderColorBottom(guindaFuerte);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(1);
                cell.setBorderColorRight(guindaFuerte);
                cell.setBorderWidthBottom(1);
                cell.setBorderColorBottom(guindaFuerte);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setMinimumHeight(5);
                cell.setColspan(13);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(1);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthBottom(2);
                cell.setBorderColorBottom(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthTop(0);
                p = new Paragraph(Constantes.ETIQUETA_ACUSE_RATIFICO, fuenteGuindaFuerte8);
                p.setAlignment(Element.ALIGN_CENTER);
                cell.setPaddingTop(10);
                cell.setPaddingBottom(10);
                cell.addElement(p);
                table.addCell(cell);

                document.add(table);

            } else {
                table = new PdfPTable(16);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthRight(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(0);
                cell.setBorderWidthBottom(0);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setMinimumHeight(5);
                cell.setColspan(13);
                table.addCell(cell);

                cell = new PdfPCell();
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthTop(0);
                cell.setBorderWidthBottom(0);
                cell.setMinimumHeight(5);
                table.addCell(cell);

                document.add(table);

                table = new PdfPTable(1);
                table.setWidthPercentage(90.0f);

                cell = new PdfPCell();
                cell.setBorderWidthLeft(2);
                cell.setBorderColorLeft(bordePrincipal);
                cell.setBorderWidthBottom(2);
                cell.setBorderColorBottom(bordePrincipal);
                cell.setBorderWidthRight(2);
                cell.setBorderColorRight(bordePrincipal);
                cell.setBorderWidthTop(0);
                p = new Paragraph(" ");
                p.setAlignment(Element.ALIGN_CENTER);
                cell.setPaddingTop(10);
                cell.setPaddingBottom(10);
                cell.addElement(p);
                table.addCell(cell);

                document.add(table);
            }

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            log.error("ERROR DocumentException AcuseCuenta -generarComprobante: ", e);
        }
        return null;
    }

    public static String date2Str(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        log.debug("month+1: " + (month + 1));
        return day + "/" + (month + 1) + "/" + year;

    }

}
