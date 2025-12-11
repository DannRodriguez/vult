package mx.ine.sustseycae.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
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
public class DTORequestModifSustIncap {

    @NotNull
    @PositiveOrZero
    @Digits(integer = 5, fraction = 0)
    private Integer idProceso;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalleProceso;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 9, fraction = 0)
    private Integer idParticipacion;

    private Integer idSustitucion;

    private Integer tipoFlujo;

    private Integer idSustituidoPrimeraSust;
    private String fechaBajaSustituido;
    private String observacionesSustituido;
    private String imagenB64Sustituido;
    private String extensionArchivoSustituido;
    private String fechaAltaSustitutoPrimeraSust;

    private Integer idSustituidoSegundaSust;
    private String fechaAltaSustitutoSegundaSust;

    private String imagenB64SustitutoSE;
    private String extensionArchivoSustitutoSE;

    private String imagenB64SustitutoCAE;
    private String extensionArchivoSustitutoCAE;

    private String usuario;
    private String ipUsuario;

}
