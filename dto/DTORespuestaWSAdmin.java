package mx.ine.sustseycae.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORespuestaWSAdmin {

    private Integer codigoRespuesta;
    private String descripcionRespuesta;
    private Integer codigoError;
    private String descripcionError;
    private String detalle;
    private List<Usuario> usuarios;
    private List<ParametrosLdap> parametrosLdap;
    private List<Atributo> listUids;
    private List<String> cuentasProcesadas;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Usuario {

        private String dn;
        private String uid;
        private String password;
        private List<Atributo> atributos;
        private List<Atributo> attrCtaLdapExt;
        private List<String> roles;
        private List<DTOCPermisosCta> permisosAsignados;
        private Integer codigoError;
        private Integer accionError;
        private String mensajeError;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ParametrosLdap {

        private String alias;
        private String base;
        private String ldapUserDn;
        private String ldapPass;
        private Integer grupoLdap;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Atributo {

        private String nombre;
        private List<String> valores;

    }

}
