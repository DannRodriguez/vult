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
public class DTODistritoDetalle implements Serializable {

	private static final long serialVersionUID = 2516496287214867943L;

	private Integer idDistrito;
	private String nombreDistrito;

}
