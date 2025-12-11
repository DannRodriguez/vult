package mx.ine.sustseycae.dto;

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
public class DTOCPermisosCta {

    private String idSistema;
    private String grupo;
    private String sistema;
    private String descripcion;
    private String idGrupo;

}