package mx.ine.sustseycae.models.responses;

import java.io.Serial;
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
public class ModelGenericResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 6339454417042408645L;

    private Integer code;
    private String status;
    private String message;
    private String causa;
    private Object data;

}
