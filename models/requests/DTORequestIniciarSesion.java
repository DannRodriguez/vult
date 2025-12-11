package mx.ine.sustseycae.models.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DTORequestIniciarSesion implements Serializable {

    @Serial
    private static final long serialVersionUID = -2748545329405496259L;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String usuario;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String password;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 3, fraction = 0)
    private Integer idSistema;

    private String versionAplicacion;

}
