package mx.ine.sustseycae.dto;

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
public class DTOAccionesModulo implements Serializable {

	private static final long serialVersionUID = -5167046537038193541L;

	private String urlModulo;
	private Integer idAccion;
	private String accionDescrip;
	private String tipoJunta;
	private String estatus;

}
