package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class DTORequestDistritosWSControl implements Serializable {

    @NotNull(message = Constantes.MSG_REQUEST_SISTEMA_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_SISTEMA_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_SISTEMA_MAX)
    private Integer idSistema;

    @NotNull(message = Constantes.MSG_REQUEST_PROCESO_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_PROCESO_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_PROCESO_MAX)
    private Integer idProceso;

    @NotNull(message = Constantes.MSG_REQUEST_DETALLE_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_DETALLE_MIN)
    @Max(value = 9999999, message = Constantes.MSG_REQUEST_DETALLE_MAX)
    private Integer idDetalle;

    @NotNull(message = Constantes.MSG_REQUEST_ESTADO_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_ESTADO_MIN)
    @Max(value = 99, message = Constantes.MSG_REQUEST_ESTADO_MAX)
    private Integer idEstado;

    @Min(value = 0)
    @Max(value = 99)
    private Integer idDistrito;

}
