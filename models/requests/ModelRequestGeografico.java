package mx.ine.sustseycae.models.requests;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.util.Constantes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelRequestGeografico implements Serializable {

    @Serial
    private static final long serialVersionUID = 5436837641037312260L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Min(value = 0, message = Constantes.MSG_REQUEST_SISTEMA_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_SISTEMA_MAX)
    private Integer idSistema;

    @Min(value = 0, message = Constantes.MSG_REQUEST_PROCESO_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_PROCESO_MAX)
    private Integer idProceso;

    @Min(value = 0, message = Constantes.MSG_REQUEST_DETALLE_MIN)
    @Max(value = 9999999, message = Constantes.MSG_REQUEST_DETALLE_MAX)
    private Integer idDetalle;

    @Min(value = 0, message = Constantes.MSG_REQUEST_ESTADO_MIN)
    @Max(value = 99, message = Constantes.MSG_REQUEST_ESTADO_MAX)
    private Integer idEstado;

    @Min(value = 0, message = "El distrito debe de ser mayor o igual a cero")
    @Max(value = 99, message = "El distrito no debe de ser mayor a 2 caracteres")
    private Integer idDistrito;

    @Min(value = 0, message = "El municipio debe de ser mayor o igual a cero")
    @Max(value = 99, message = "El municipio no debe de ser mayor a 2 caracteres")
    private Integer idMunicipio;

    @Size(min = 1, max = 500)
    @Pattern(message = "El tipo de captura puede contener solo letras, numeros y puntos", regexp = "[a-zA-Z0-9. ]+")
    private String tipoCapturaSistema;

    @Size(min = 1, max = 1)
    @Pattern(message = Constantes.MSG_REQUEST_AMBITO_PATTERN, regexp = "[LF]")
    private String ambitoUsuario;

    @Size(min = 1, max = 500)
    @Pattern(message = Constantes.MSG_REQUEST_GRUPO_PATTERN, regexp = "[a-zA-Z0-9._ ]+")
    private String rol;
}
