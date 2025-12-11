package mx.ine.sustseycae.models.responses;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.parametrizacion.model.dto.DTODistrito;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseDistritosWSControl implements Serializable{

    private Integer code;
    private String status;
    private String message;
    private String causa;
    private List<DTODistrito> data;

}
