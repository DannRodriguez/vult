package mx.ine.sustseycae.bsd;

import org.springframework.http.ResponseEntity;

import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;
import mx.ine.sustseycae.models.responses.ModelResponseBitacora;
public interface BSDBitacoraDesempenioInterface {
	
	
	public VOAspiranteBitacora obtenerInfoAspiranteBitacora (DTORequestBitacora request);
	
	public VOBitacoraDesempenio obtenerExpedienteDesempenio(DTORequestBitacora request) throws Exception;
	
	public  ResponseEntity<Object> obtenerBase64Expediente(DTORequestBitacora request) throws Exception;
    
    public ModelResponseBitacora obtenerEvaluacionDesempenio(DTORequestBitacora request) throws Exception;
	
	public String guardarBitacora(DTORequestBitacora request) throws Exception;
	
	public void eliminarBitacoraDesempenio(Integer idDetalleProceso, Integer idParticipacion, Integer idBitacoraDesempenio, Integer idAspirante);
	

}
