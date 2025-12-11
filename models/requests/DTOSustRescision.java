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
public class DTOSustRescision implements Serializable {

	private static final long serialVersionUID = 8568750278889528550L;

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;

	private Integer idAspiranteSustituido;

	private Integer idCausaVacante;
	private Integer tipoCausaVacante;
	private String observaciones;
	private String fechaBaja;
	private String fechaAlta;

	private Integer idAspiranteSustituto;
	private String imgB64;
	private String extensionArchivo;
	private String correoCuenta;

	private String usuario;

}
