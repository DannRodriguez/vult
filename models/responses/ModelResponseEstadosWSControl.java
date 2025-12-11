package mx.ine.sustseycae.models.responses;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.parametrizacion.model.dto.DTOEstado;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseEstadosWSControl implements  Serializable{

    private Integer code;
    private String status;
    private String message;
    private String causa;
    private List<DTOEstado> data;

}
