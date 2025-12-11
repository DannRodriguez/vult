package mx.ine.sustseycae.models.requests;

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
public class ModelRequestListaResrvervaCAE implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2022831015175257758L;

    @PositiveOrZero
    @Digits(integer = 5, fraction = 0)
    private Integer idProceso;

    @PositiveOrZero
    @Digits(integer = 7, fraction = 0)
    private Integer idDetalle;

    @PositiveOrZero
    @Digits(integer = 3, fraction = 0)
    private Integer idParticipacion;

    private Integer idPuesto;
    private Integer estatus;
    private Integer filtro;
    private Integer idMunicipio;
    private Integer idLocalidad;
    private Integer idSedeReclutamiento;
    private Integer seccion1;
    private Integer seccion2;

}
