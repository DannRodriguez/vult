package mx.ine.sustseycae.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

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
public class DTOExpedienteBitacora implements Serializable {

	private static final long serialVersionUID = -3330821055656660574L;

	private MultipartFile archivoExpediente;
	private String nombreDocto;
	private String extensionArchivo;
	private String correo;
	private String citatorio;
	private String constancia;
	private Integer eliminarExpediente;

}
