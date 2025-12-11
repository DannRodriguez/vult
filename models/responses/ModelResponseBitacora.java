package mx.ine.sustseycae.models.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOCFrecuencias;
import mx.ine.sustseycae.dto.vo.VOCImpactos;
import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;
import mx.ine.sustseycae.dto.vo.VOResponsableBitacoraDesempenio;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseBitacora implements Serializable{

	private static final long serialVersionUID = 6500110380392311839L;
	
	@JsonProperty("catFrecuencias")
	private List<VOCFrecuencias> catFrecuencias;
	@JsonProperty("catImpactos")
	private List<VOCImpactos> catImpactos;
	@JsonProperty("catValoracionesRiesgo")
	private List<VOCValoracionRiesgo> catValoracionesRiesgo;
	private VOAspiranteBitacora aspiranteBitacora;
	private List<ModelResponseIntegrantesSesiones> integrantesWS;
	
	private VOBitacoraDesempenio bitacoraDesempenio;
	private List<VOResponsableBitacoraDesempenio> responsablesBitacora;
	
}
