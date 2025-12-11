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
public class VOCImpactos implements Serializable {

	private static final long serialVersionUID = 6009622537335464536L;

	private Integer idImpacto;
	private String descripcionImpacto;

}
