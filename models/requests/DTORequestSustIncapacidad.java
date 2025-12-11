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
public class DTORequestSustIncapacidad {

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

    private Boolean esPendiente;

    private Integer idSustituido;
    private String fechaBajaSustituido;

    private Integer idCausaVacante;
    private Integer tipoCausaVacante;
    private String observacionesBajaSustituido;
    private String imagenB64Sustituido;
    private String extensionArchivoSustituido;

    private Integer idSustitutoSupervisor;
    private String dateSuplenteSupervisor;
    private String imagenB64SustitutoSE;
    private String extensionArchivoSustitutoSE;

    private Integer idSustituidoCapacitador;
    private String dateSuplenteCapacitador;
    private String imagenB64SustitutoCAE;
    private String extensionArchivoSustitutoCAE;
    private String cuentaSuplenteCapacitador;

    private String usuario;
    private String ipUsuario;

}
