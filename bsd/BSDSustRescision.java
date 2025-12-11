package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.models.requests.DTORequestModificarSustRescision;
import mx.ine.sustseycae.models.requests.DTORequestSustRescision;
import mx.ine.sustseycae.models.responses.ModelResponseDatosSustitucion;

public interface BSDSustRescision {

	public boolean guardarModificarSustRescision(DTORequestSustRescision request);

	public boolean guardarSustRescisionPendiente(DTORequestSustRescision request);

	public ModelResponseDatosSustitucion obtenerSustitucionPendiente(Integer idDetalleProceso, Integer idParticipacion,
			Integer idAspiranteSustituido);

	public boolean modificarSustRescision(DTORequestModificarSustRescision request);

}
