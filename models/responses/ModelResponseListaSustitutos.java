package mx.ine.sustseycae.models.responses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class ModelResponseListaSustitutos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5312035723908046271L;

    private List<ModelResponseCategoriaSustitutos> categoriasSustitutos;
    private Date fechaInicioRegistroSustituciones;
    private Date fechaFinRegistroSustituciones;

}
