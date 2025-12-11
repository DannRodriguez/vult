package mx.ine.sustseycae.dto.vo;

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
public class VOComboPendientes implements Serializable {

    @Serial
    private static final long serialVersionUID = -2658867280757002428L;

    private String id;
    private String value;
    private String causa;

}
