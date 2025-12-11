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
public class DTOCorreoServicio implements Serializable {

    private String usernameFrom;
    private String passwordFrom;
    private String cuentaDeEnvio;
    private String usernameFromExc;
    private String passwordFromExc;
    private String cuentaDeEnvioExc;
}
