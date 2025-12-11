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
public class VOCValoracionRiesgo implements Serializable {

	private static final long serialVersionUID = 150111032178584597L;

	private Integer idImpacto;
	private Integer idFrecuencia;
	private Integer idValoracionRiesgo;
	private String descripcionRiesgo;

}
