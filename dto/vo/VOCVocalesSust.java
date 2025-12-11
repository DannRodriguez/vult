package mx.ine.sustseycae.dto.vo;

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
public class VOCVocalesSust implements Serializable {

    private static final long serialVersionUID = 132082832L;    

    private Integer idProcesoElectoral;
    private Integer idDetalleProceso;
    private Integer idResponsableBitacora;
    private String tipoPuesto;
    private Integer idPuesto;
    private String puesto;
}
