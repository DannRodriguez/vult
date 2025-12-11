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
public class DTORequestEstadosWSControl implements Serializable {

    @NotNull(message = Constantes.MSG_REQUEST_SISTEMA_NOT_NULL)
    @Min(value = 0, message = Constantes.MSG_REQUEST_SISTEMA_MIN)
    @Max(value = 99999, message = Constantes.MSG_REQUEST_SISTEMA_MAX)
    private Integer idSistema;

}
