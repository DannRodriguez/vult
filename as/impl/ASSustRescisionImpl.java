package mx.ine.sustseycae.as.impl;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASSustRescision;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.dto.db.DTOCCausasVacante;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.models.responses.ModelResponseDatosSustitucion;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPASustitucionesSupycap;
import mx.ine.sustseycae.util.Constantes;
import mx.ine.sustseycae.repositories.RepoJPACCausasVacante;

@Service("asSustRescision")
public class ASSustRescisionImpl implements ASSustRescision {

	private Log log = LogFactory.getLog(ASSustRescisionImpl.class);

	@Autowired
	private RepoJPAAspirantes repoJPAAspirantes;

	@Autowired
	private RepoJPASustitucionesSupycap repoJPASustitucionesSupycap;

	@Autowired
	private RepoJPACCausasVacante repoJPACCausasVacante;

	@Override
	public Optional<DTOAspirantes> buscarAspirante(DTOAspirantesId id) {
		return repoJPAAspirantes.findById(id);
	}

	@Override
	public Integer findIdSustitucion(Integer idDetalleProceso, Integer idParticipacion) {
		return repoJPASustitucionesSupycap.encontrarMaxId(idDetalleProceso, idParticipacion)
				.orElse(1);
	}

	@Override
	public Optional<DTOSustitucionesSupycap> obtenerSustitucion(Integer idDetalleProceso,
			Integer idParticipacion, Integer idSustitucion) {
		try {
			return repoJPASustitucionesSupycap.findById(new DTOSustitucionesSupycapId(
					idDetalleProceso,
					idParticipacion,
					idSustitucion));
		} catch (Exception e) {
			log.error("ERROR ASSustRescisionImpl -obtenerSustitucion: ", e);
			return Optional.empty();
		}
	}

	@Override
	public DTOSustitucionesSupycap obtenerSustitucionPendiente(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idParticipacion, Integer idAspirante, Integer tipoCausaVacante) {
		try {
			return repoJPASustitucionesSupycap
					.findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndTipoCausaVacante(
							idDetalleProceso,
							idParticipacion,
							idAspirante,
							tipoCausaVacante);
		} catch (Exception e) {
			log.error("ERROR ASSustRescisionImpl -obtenerSustitucionPendiente: ", e);
			return null;
		}
	}

	@Override
	public ModelResponseDatosSustitucion obtenerDatosSustitucionPendiente(Integer idDetalleProceso,
			Integer idParticipacion, Integer idAspirante) {
		try {
			ModelResponseDatosSustitucion response = new ModelResponseDatosSustitucion();
			DTOSustitucionesSupycap sustitucion = repoJPASustitucionesSupycap
					.findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndIdPuestoSustituido(
							idDetalleProceso,
							idParticipacion,
							idAspirante,
							Constantes.ID_PUESTO_SE);

			if (sustitucion == null) {
				sustitucion = repoJPASustitucionesSupycap
						.findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndIdPuestoSustituido(
								idDetalleProceso,
								idParticipacion,
								idAspirante,
								Constantes.ID_PUESTO_CAE);
			}

			if (sustitucion == null) {
				sustitucion = repoJPASustitucionesSupycap
						.findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndTipoCausaVacanteAndIdCausaVacante(
								idDetalleProceso,
								idParticipacion,
								idAspirante,
								3,
								2);
			}

			if (sustitucion != null) {
				DTOCCausasVacante causaVacante = repoJPACCausasVacante.findById_IdCausaVacanteAndIdTipoCausaVacante(
						sustitucion.getIdCausaVacante() != null ? sustitucion.getIdCausaVacante() : 0,
						sustitucion.getTipoCausaVacante() != null ? sustitucion.getTipoCausaVacante() : 0);
				response.setDtoSustituciones(sustitucion);
				response.setDescripcionCausaVacante(causaVacante.getDescripcion());
			}

			return response;
		} catch (Exception e) {
			log.error(
					"ERROR ASSustRescisionImpl -obtenerDatosSustitucionPendiente: ", e);
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, transactionManager = "transactionManager")
	public void guardaAspirante(DTOAspirantes aspirante, String usuario, String ipUsuario) {
		aspirante.setUsuario(usuario);
		aspirante.setIpUsuario(ipUsuario);
		aspirante.setFechaHora(new Date());
		aspirante.setIdSistemaActualiza(Constantes.ID_SISTEMA);
		repoJPAAspirantes.saveAndFlush(aspirante);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {
			Exception.class }, transactionManager = "transactionManager")
	public void guardaSustitucion(DTOSustitucionesSupycap sustitucion, String usuario, String ipUsuario) {
		sustitucion.setFechaHora(new Date());
		sustitucion.setUsuario(usuario);
		sustitucion.setIpUsuario(ipUsuario);
		repoJPASustitucionesSupycap.saveAndFlush(sustitucion);
	}

}
