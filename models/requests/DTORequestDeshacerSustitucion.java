package mx.ine.sustseycae.models.requests;

import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;

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
public class DTORequestDeshacerSustitucion extends DTORequestConsultaDeshacerSustituciones{
    private VOConsultaDesSustitucionesSupycap sustitucionADeshacer;
    private String user;
    private String ipUsuario;

}