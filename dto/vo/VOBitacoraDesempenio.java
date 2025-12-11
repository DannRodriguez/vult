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
public class VOBitacoraDesempenio implements Serializable {

	private static final long serialVersionUID = -1344274324456462473L;

	private Integer idProcesoElectoral;
	private Integer idDetalleProceso;
	private Integer idParticipacion;
	private Integer idBitacoraDesempenio;
	private Integer idAspirante;
	private String devolvioPrendas;
	private String rutaDocumentos;
	private String documentoCorreo;
	private String documentoCitatorio;
	private String documentoConstancia;
	private Integer idImpacto;
	private Integer idFrecuencia;
	private Integer idValoracionRiesgo;
	private String observaciones;
	private String nombreDocumento;
	private Long dimensionArchivo;

}
