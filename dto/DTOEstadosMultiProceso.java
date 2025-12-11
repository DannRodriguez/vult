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
public class DTOEstadosMultiProceso implements Serializable {

	private static final long serialVersionUID = -6499161582188721152L;

	private Integer idEstado;
	private String nombreEstado;

}
