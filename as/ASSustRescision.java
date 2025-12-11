package mx.ine.sustseycae.as;

import java.util.Optional;

import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.models.responses.ModelResponseDatosSustitucion;

public interface ASSustRescision {

	public Optional<DTOAspirantes> buscarAspirante(DTOAspirantesId id);

	public Integer findIdSustitucion(Integer idDetalleProceso, Integer idParticipacion);

	public Optional<DTOSustitucionesSupycap> obtenerSustitucion(Integer idDetalleProceso,
			Integer idParticipacion, Integer idSustitucion);

	public DTOSustitucionesSupycap obtenerSustitucionPendiente(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idParticipacion, Integer idAspirante, Integer tipoCausaVacante);

	public ModelResponseDatosSustitucion obtenerDatosSustitucionPendiente(Integer idDetalleProceso,
			Integer idParticipacion, Integer idAspirante);

	public void guardaAspirante(DTOAspirantes aspirante, String usuario, String ipUsuario);

	public void guardaSustitucion(DTOSustitucionesSupycap sustitucion, String usuario, String ipUsuario);

}
