package mx.ine.sustseycae.models.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORequestCerrarSesion implements Serializable {

	private static final long serialVersionUID = 837196423757829684L;

	@NotNull
	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 50)
	private String usuario;

	@Size(min = 1, max = 50)
	private String password;

	@NotNull
	@PositiveOrZero
	@Digits(integer = 3, fraction = 0)
	private Integer idSistema;

}
