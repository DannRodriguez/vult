package mx.ine.sustseycae.models.requests;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ModelRequestFiltroLR implements Serializable {

    @Serial
    private static final long serialVersionUID = -6164806212606933713L;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 0)
    private Integer idEstado;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 0)
    private Integer idDistrito;

    @PositiveOrZero
    @Digits(integer = 6, fraction = 0)
    private Integer idCorte;

    private Integer idMunicipio;

    @PositiveOrZero
    @Digits(integer = 5, fraction = 0)
    private Integer idProceso;

    @PositiveOrZero
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalle;

    @PositiveOrZero
    @Digits(integer = 3, fraction = 0)
    private Integer idParticipacion;

}
