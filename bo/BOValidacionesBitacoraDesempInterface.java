package mx.ine.sustseycae.bo;

import java.util.List;

import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.models.requests.DTORequestBitacora;

public interface BOValidacionesBitacoraDesempInterface {
	
	public String validaRequestAlmacenarBitacora(DTORequestBitacora request);
	
	public DTOBitacoraDesempenio dtoRequestToBitacoraDesempenio(DTORequestBitacora request) throws Exception;

	public List<DTOResponsablesBitacoraDesempenio> obtenerResponsablesBitacora(DTORequestBitacora request) throws Exception;
	
	public String buildNombreFoto(DTORequestBitacora request);

	public String buildCarpetaExpedienteGluster(DTORequestBitacora request, String carpetaSupycap);
	
	public String buildNombreExpediente(DTORequestBitacora request);
	
}
