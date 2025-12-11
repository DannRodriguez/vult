package mx.ine.sustseycae.models.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ModelRequestModulo extends ModelRequestMenu {

    private static final long serialVersionUID = -1883739415686567388L;

    @NotNull(message = "El modulo no puede ser null")
    @Min(value = 0, message = "El detalle debe de ser mayor o igual a cero")
    @Max(value = 999999, message = "El detalle no debe de ser mayor a 999999")
    private Integer idModulo;

}
