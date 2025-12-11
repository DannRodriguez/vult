package mx.ine.sustseycae.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class DTORequestListaSustituido {

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 0)
    private Integer idProceso;

    @NotNull
    @Positive
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalleProceso;

    @NotNull
    @Positive
    @Digits(integer = 9, fraction = 0)
    private Integer idParticipacion;

    @Positive
    private Integer moduloSust;

    @Positive
    private Integer tipoFlujo;

    @Positive
    private Integer idAspirante;

}
