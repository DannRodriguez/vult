package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class DTORequestProcesosWSControl implements Serializable {

    @NotNull(message = Constantes.MSG_REQUEST_SISTEMA_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_SISTEMA_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_SISTEMA_MAX)
    private Integer idSistema;

    @NotNull(message = Constantes.MSG_REQUEST_ESTADO_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_ESTADO_MIN)
    @Max(value = 99, message = Constantes.MSG_REQUEST_ESTADO_MAX)
    private Integer idEstado;

    @Min(value = 0)
    @Max(value = 99)
    private Integer idDistrito;

    @Size(min = 1, max = 1)
    @Pattern(message = Constantes.MSG_REQUEST_AMBITO_PATTERN, regexp = "[LF]")
    private String ambitoUsuario;

    @Size(min = 1, max = 1)
    @Pattern(message = Constantes.MSG_REQUEST_VIGENCIA_PATTERN, regexp = "[SNA]")
    private String vigente;
}
