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
public class ModelResponseSustitutos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6039964669069855975L;

    private Integer idDetalleProceso;
    private Integer idParticipacion;
    private Integer idAspirante;
    private Integer folio;
    private String title;
    private Integer value;
    private Integer key;
    private Integer categoria;
    private Boolean disabled;

}
