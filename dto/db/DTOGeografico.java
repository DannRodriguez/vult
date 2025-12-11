package mx.ine.sustseycae.dto.db;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DTOGeografico implements Serializable {

	@Serial
	private static final long serialVersionUID = -7363456281886696707L;

	private String nombreEdoDto;
	private Integer identificador;

}
