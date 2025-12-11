package mx.ine.sustseycae.models.responses;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModelResponseIntegrantesSesiones implements Serializable {

	private static final long serialVersionUID = 8217138947494692285L;
	
	private Integer idIntegrante;
    private String tipoIntegrante; 
    private Integer idEstado; 
    private Integer idDistritoFederal; 
    private String iniciales; 
    private String descripcionPuesto; 
    private String nombreIntegrante; 
    private String primerApellidoIntegrante; 
    private String segundoApellidoIntegrante; 
    private String correoElectronico; 
    private String gradoAcademico; 
    private String estatus; 
    private Integer idPuesto; 
    private Integer idPuestoVocal;
    private String curp; 
    private String urlFirma; 
    private String siglas; 
    private String tipoPuesto; 
    private Integer idProcesoElectoral; 
    private Integer idDetalleProceso;

}
