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
public class DTORequestSustRescision implements Serializable {

	private static final long serialVersionUID = 2193163341354157054L;

	private DTOSustRescision datosSustitucionSe;
	private DTOSustRescision datosSustitucionCae;

	private String imgB64Sustituido;
	private String extensionArchSustituido;

	private String ipUsuario;

}
