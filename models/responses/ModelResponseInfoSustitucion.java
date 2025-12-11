package mx.ine.sustseycae.models.responses;

import java.io.Serializable;

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
public class ModelResponseInfoSustitucion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8464004617706147732L;

    private ModelResponseDatosSustitucion dtoSustitucionSE;
    private ModelResponseDatosSustitucion dtoSustitucionCAE;
    private ModelResponseDatosAspirante datosSustitutoSE;
    private ModelResponseDatosAspirante datosSustitutoCAE;

}
