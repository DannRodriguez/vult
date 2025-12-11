package mx.ine.sustseycae.models.requests;

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
public class DTORequestLoadFoto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4470485431203614136L;

    private String urlFoto;

}
