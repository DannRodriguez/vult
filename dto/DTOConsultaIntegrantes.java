package mx.ine.sustseycae.dto;

import java.io.Serializable;
import java.util.List;

import mx.ine.sustseycae.models.responses.ModelResponseIntegrantesSesiones;

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
public class DTOConsultaIntegrantes implements Serializable {

	private static final long serialVersionUID = -2780643664956468721L;

	private Object wsrest;
	private Integer code;
	private String status;
	private String message;
	private String causa;
	List<ModelResponseIntegrantesSesiones> integrantes;

}
