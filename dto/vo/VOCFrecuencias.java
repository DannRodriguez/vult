package mx.ine.sustseycae.dto.vo;

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
public class VOCFrecuencias implements Serializable {

	private static final long serialVersionUID = -919149070565435484L;

	private Integer idFrecuencia;
	private String descripcionFrecuencia;

}
