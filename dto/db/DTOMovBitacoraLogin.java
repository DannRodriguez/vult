package mx.ine.sustseycae.dto.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(schema = "SUPYCAP", name = "MOV_BITACORA_LOGIN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "idTokenAcceso", "fechaExpiracionTa" })
public class DTOMovBitacoraLogin implements Serializable {

        @Serial
        private static final long serialVersionUID = -3285279580064838475L;

        @EmbeddedId
        private DTOMovBitacoraLoginId id;

        @Column(name = "USUARIO", nullable = false, length = 50)
        private String usuario;

        @Column(name = "FECHA_LOGUEO", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        private Date fechaLogueo;

        @Column(name = "FECHA_CIERRE_SESION", nullable = true)
        private Date fechaCierreSesion;

        @Column(name = "VERSION_APLICACION", nullable = true, length = 50)
        private String versionAplicacion;

        @Column(name = "ESTATUS", nullable = false, precision = 1, scale = 0)
        private Integer estatus;

        @Column(name = "ROL_USUARIO", nullable = true, precision = 50, scale = 0)
        private String rolUsuario;

        @Column(name = "IP_USUARIO", nullable = true, precision = 15, scale = 0)
        private String ipUsuario;

        @Column(name = "IP_INTRA", nullable = true, precision = 15, scale = 0)
        private String ipIntra;

        @Column(name = "ID_TOKEN_ACCESO", nullable = true, precision = 100, scale = 0)
        private String idTokenAcceso;

        @Column(name = "FECHA_EXPIRACION_TA", nullable = true)
        private Date fechaExpiracionTa;

}
