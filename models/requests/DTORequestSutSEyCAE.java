package mx.ine.sustseycae.models.requests;

import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.DTOEvaluacionBitacora;
import mx.ine.sustseycae.dto.DTOExpedienteBitacora;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORequestSutSEyCAE implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8644587513509964806L;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 5, fraction = 0)
    private Integer idProcesoElectoral;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalleProceso;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 9, fraction = 0)
    private Integer idParticipacion;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 6, fraction = 0)
    private Integer idSustituido;

    private Integer esPendiente;
    private String imagenB64Sustituido;
    private String extensionImagenSustituido;

    private Integer tipoCausaVacante;
    private Integer idCausaVacante;
    private String fechaBajaSustituido;
    private String observacionesSustituido;

    private Integer procesarSoloBitacora;
    private Integer existeInfoBitacora;
    private Integer idBitacoraDesempenio;
    private Integer tipoAccionBitacora;
    private Integer origenBitacora;
    private DTOExpedienteBitacora expedienteDesempenio;
    private DTOEvaluacionBitacora evaluacionDesempenio;

    private Integer idSustitutoSupervisor;
    private String dateSustitutoSupervisor;
    private String imagenB64SustitutoSupervisor;
    private String extensionImagenSustitutoSupervisor;
    private String correoSustitutoSupervisor;

    private Integer idSustitutoCapacitador;
    private String dateSustitutoCapacitador;
    private String imagenB64SustitutoCapacitador;
    private String extensionImagenSustitutoCapacitador;
    private String correoSustitutoCapacitador;

    private VOSustitucionesSupycap sustitucionPrevia;

    //flujo de modificar
    private VOSustitucionesSupycap sustitucionSE;
    private VOSustitucionesSupycap sustitucionCAE;
    private Integer caeSustitucionDeSE;

    private String devolvioPrendas;
    private Integer tipoAccion;
    private String usuario;
    private String ipUsario;

}
