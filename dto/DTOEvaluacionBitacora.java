package mx.ine.sustseycae.dto;

import java.io.Serializable;
import java.util.List;

import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;
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
public class DTOEvaluacionBitacora implements Serializable {

	private static final long serialVersionUID = 3742287602634729821L;

	private List<ModelResponseIntegrantesSesiones> responsables;
	private VOCValoracionRiesgo valoracionRiesgo;
	private String observaciones;

}
