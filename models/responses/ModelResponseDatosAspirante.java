package mx.ine.sustseycae.models.responses;

import java.io.Serializable;
import java.util.Date;

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
public class ModelResponseDatosAspirante implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8058771295041272295L;

    private Integer folioAspirante;
    private String nombreAspirante;
    private String claveElectorFuar;
    private Integer idPuesto;
    private String cargoActual;
    private Integer idZore;
    private Integer numZore;
    private Integer idAre;
    private Integer numAre;
    private String ares;
    private Date fechaAlta;
    private String urlFoto;
    private String correoElectronico;

}
