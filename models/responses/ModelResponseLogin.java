package mx.ine.sustseycae.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.DTOUsuarioLogin;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseLogin extends ModelGenericResponse {

    private static final long serialVersionUID = 5540024451399744263L;

    private DTOUsuarioLogin datosUsuario;

}
