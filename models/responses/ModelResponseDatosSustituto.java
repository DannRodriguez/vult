package mx.ine.sustseycae.models.responses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.db.DTOAspirantes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseDatosSustituto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8323221655337955330L;

    private DTOAspirantes dtoAspirante;
    private ModelResponseDatosAspirante datosAspirante;

}
