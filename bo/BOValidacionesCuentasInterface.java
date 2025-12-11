package mx.ine.sustseycae.bo;

import java.util.List;

import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;

public interface BOValidacionesCuentasInterface {

    public Boolean isPuestoSE(Integer idPuesto);

    public Boolean isPuestoCAE(Integer idPuesto);

    public DTOCreacionCuentas aspiranteToDTOCreacionCuentas(DTOAspirantes aspiranteCuentaCrear, Integer idZoreAre,
            Integer numZoreAre, Integer tipoSO, String usuario, String ipUsuario);

    public byte[] guardarPdf(DTOAspirantes aspirante, DTORespuestaWSAdmin.Usuario usuario, String urlPoliticasUso,
            String urlCambioContra, String usuarioQueCreaCuenta, Integer idPuesto, Boolean generarBytes,
            String carpetaSupycap) throws Exception;

    public String getNombreComprobante(Integer estado, Integer idEntorno, Integer areZore, Integer puesto)
            throws Exception;

    public void enviarComprobante(DTOAspirantes aspirante, DTORespuestaWSAdmin.Usuario usuario,
            String correoNotificacion, boolean envioNotificacion, String cuenta, String nombre,
            String urlPoliticasUso, String urlCambioContra, String usuarioQueCreaCuenta, Integer idPuesto,
            String carpetaSupycap, List<DTOCParametros> listParamCorreo, String cuentaNotificacion)
            throws Exception;
}
