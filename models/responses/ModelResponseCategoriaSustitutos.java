package mx.ine.sustseycae.models.responses;

import java.io.Serializable;
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
public class ModelResponseCategoriaSustitutos implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4249464717580368621L;

    private String title;
    private Integer value;
    private Integer key;
    private Boolean selectable;
    private List<Object> children;

}
