package mx.ine.sustseycae.bsd.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import mx.ine.sustseycae.as.ASSustRescision;
import mx.ine.sustseycae.bsd.BSDCommons;
import mx.ine.sustseycae.bsd.BSDSustRescision;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.helper.HLPCuentasSustitucionesInterface;
import mx.ine.sustseycae.models.requests.DTORequestModificarSustRescision;
import mx.ine.sustseycae.models.requests.DTORequestSustRescision;
import mx.ine.sustseycae.models.requests.DTOSustRescision;
import mx.ine.sustseycae.models.responses.ModelResponseDatosSustitucion;
import mx.ine.sustseycae.util.ApplicationUtil;
import mx.ine.sustseycae.util.Constantes;

@Controller
public class BSDSustRescisionImpl implements BSDSustRescision {

	private Log log = LogFactory.getLog(BSDSustRescisionImpl.class);

	@Autowired
	private HLPCuentasSustitucionesInterface hlpCuentasSustituciones;

	@Autowired
	private ASSustRescision asSustRescision;

	@Autowired
	private BSDCommons bsdCommons;

	@Override
	public ModelResponseDatosSustitucion obtenerSustitucionPendiente(Integer idDetalleProceso,
			Integer idParticipacion, Integer idAspiranteSustituido) {
		return asSustRescision.obtenerDatosSustitucionPendiente(
				idDetalleProceso,
				idParticipacion,
				idAspiranteSustituido);
	}

	@Override
	public boolean guardarModificarSustRescision(DTORequestSustRescision request) {
		DTOSustRescision sustitucionRescisionSE = request.getDatosSustitucionSe();
		DTOSustRescision sustitucionRescisionCAE = request.getDatosSustitucionCae();

		DTOAspirantes sustitutoSE = new DTOAspirantes();
		DTOAspirantes sustitutoCAE = new DTOAspirantes();

		DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();

		String idRelacion = UUID.randomUUID().toString();
		Integer idPuestoSustitutoSE = 0;

		try {
			if (sustitucionRescisionSE != null) {
				Integer idSustitucionSE = asSustRescision.findIdSustitucion(
						sustitucionRescisionSE.getIdDetalleProceso(),
						sustitucionRescisionSE.getIdParticipacion());

				DTOAspirantes sustituido = obtenerAspirante(
						sustitucionRescisionSE.getIdProcesoElectoral(),
						sustitucionRescisionSE.getIdDetalleProceso(),
						sustitucionRescisionSE.getIdParticipacion(),
						sustitucionRescisionSE.getIdAspiranteSustituido());

				if (request.getImgB64Sustituido() != null) {
					sustituido.setUrlFoto(bsdCommons.almacenarFotoAspirante(
							request.getImgB64Sustituido(),
							request.getExtensionArchSustituido(),
							sustitucionRescisionSE.getIdProcesoElectoral(),
							sustitucionRescisionSE.getIdDetalleProceso(),
							sustitucionRescisionSE.getIdParticipacion(),
							sustitucionRescisionSE.getIdAspiranteSustituido()));
				}

				if (sustitucionRescisionCAE != null) {
					sustitutoCAE = obtenerAspirante(
							sustitucionRescisionCAE.getIdProcesoElectoral(),
							sustitucionRescisionCAE.getIdDetalleProceso(),
							sustitucionRescisionCAE.getIdParticipacion(),
							sustitucionRescisionCAE.getIdAspiranteSustituto());
					sustitutoCAE.setCorreoCtaCreada(sustitucionRescisionCAE.getCorreoCuenta());
					if (sustitucionRescisionCAE.getImgB64() != null) {
						sustitutoCAE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionCAE.getImgB64(),
								sustitucionRescisionCAE.getExtensionArchivo(),
								sustitutoCAE.getIdProcesoElectoral(),
								sustitutoCAE.getIdDetalleProceso(),
								sustitutoCAE.getIdParticipacion(),
								sustitutoCAE.getIdAspirante()));
					}
				}

				if (sustitucionRescisionSE.getIdAspiranteSustituto() != null) {
					Integer idSustitucionCAE = idSustitucionSE + 1;
					sustitutoSE = obtenerAspirante(
							sustitucionRescisionSE.getIdProcesoElectoral(),
							sustitucionRescisionSE.getIdDetalleProceso(),
							sustitucionRescisionSE.getIdParticipacion(),
							sustitucionRescisionSE.getIdAspiranteSustituto());
					sustitutoSE.setCorreoCtaCreada(sustitucionRescisionSE.getCorreoCuenta());
					if (sustitucionRescisionSE.getImgB64() != null) {
						sustitutoSE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionSE.getImgB64(),
								sustitucionRescisionSE.getExtensionArchivo(),
								sustitutoSE.getIdProcesoElectoral(),
								sustitutoSE.getIdDetalleProceso(),
								sustitutoSE.getIdParticipacion(),
								sustitutoSE.getIdAspirante()));
					}

					if (Constantes.ID_PUESTO_CAE.equals(sustitutoSE.getIdPuesto())
							|| Constantes.ID_PUESTO_CAE_TMP.equals(sustitutoSE.getIdPuesto())) {
						sustitucionCAE = obtenerObjetoSustitucion(
								sustitucionRescisionCAE != null ? sustitucionRescisionCAE : sustitucionRescisionSE,
								sustitutoSE,
								sustitutoCAE,
								idSustitucionCAE,
								idRelacion);

						sustitucionCAE.setIdCausaVacante(Constantes.ID_CAUSA_VACANTE_PROMOCION);
						sustitucionCAE.setTipoCausaVacante(Constantes.TIPO_CAUSA_VACANTE_OTRAS_CAUSAS);
						sustitucionCAE.setFechaMovimiento(new Date());
						asSustRescision.guardaSustitucion(
								sustitucionCAE,
								sustitucionRescisionSE.getUsuario(),
								request.getIpUsuario());
					}

				}

				DTOSustitucionesSupycap sustitucionSE = obtenerObjetoSustitucion(
						sustitucionRescisionSE,
						sustituido,
						sustitutoSE,
						idSustitucionSE,
						idRelacion);
				sustitucionSE.setFechaMovimiento(new Date());
				if (sustitucionRescisionCAE != null) {
					sustitutoCAE.setIdPuesto(Constantes.ID_PUESTO_CAE);
					sustitutoCAE.setIdZonaResponsabilidad1e(sustitutoSE.getIdZonaResponsabilidad1e());
					sustitutoCAE.setIdAreaResponsabilidad1e(sustitutoSE.getIdAreaResponsabilidad1e());
					sustitutoCAE.setIdZonaResponsabilidad2e(sustitutoSE.getIdZonaResponsabilidad2e());
					sustitutoCAE.setIdAreaResponsabilidad2e(sustitutoSE.getIdAreaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoCAE,
							sustitucionRescisionCAE.getUsuario(),
							request.getIpUsuario());
				}
				if (sustitucionRescisionSE.getIdAspiranteSustituto() != null) {
					if (sustitutoSE.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
						idPuestoSustitutoSE = Constantes.ID_PUESTO_CAE;
					}
					sustitutoSE.setIdPuesto(Constantes.ID_PUESTO_SE);
					sustitutoSE.setIdZonaResponsabilidad1e(sustituido.getIdZonaResponsabilidad1e());
					sustitutoSE.setIdAreaResponsabilidad1e(sustituido.getIdAreaResponsabilidad1e());
					sustitutoSE.setIdZonaResponsabilidad2e(sustituido.getIdZonaResponsabilidad2e());
					sustitutoSE.setIdAreaResponsabilidad2e(sustituido.getIdAreaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario(),
							request.getIpUsuario());
				}
				sustituido.setIdPuesto(obtenerPuesto(
						sustitucionRescisionSE.getTipoCausaVacante(),
						sustitucionRescisionSE.getIdCausaVacante(),
						Constantes.ID_PUESTO_SE));
				if (Constantes.TIPO_OTRAS_CAUSAS.equals(sustitucionRescisionSE.getTipoCausaVacante())
						&& Constantes.ID_CAUSA_VACANTE_DECLINAR.equals(sustitucionRescisionSE.getIdCausaVacante())) {
					sustituido.setDeclinoCargo(Constantes.ID_PUESTO_SE);
				}
				resetZOREAREAssigned(sustituido);
				asSustRescision.guardaAspirante(
						sustituido,
						sustitucionRescisionSE.getUsuario(),
						request.getIpUsuario());

				asSustRescision.guardaSustitucion(
						sustitucionSE,
						sustitucionRescisionSE.getUsuario(),
						request.getIpUsuario());

				hlpCuentasSustituciones.eliminarCuentaSustitucion(
						sustituido,
						sustitucionRescisionSE.getUsuario());
				if (idPuestoSustitutoSE.equals(Constantes.ID_PUESTO_CAE)) {
					hlpCuentasSustituciones.modificarCuentaSustitucion(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario());
				} else {
					hlpCuentasSustituciones.crearCuentaSustitucion(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario());
				}
				hlpCuentasSustituciones.crearCuentaSustitucion(
						sustitutoCAE,
						sustitucionRescisionSE.getUsuario());
				return true;
			} else if (sustitucionRescisionCAE != null) {
				Integer idSustitucion = asSustRescision.findIdSustitucion(
						sustitucionRescisionCAE.getIdDetalleProceso(),
						sustitucionRescisionCAE.getIdParticipacion());
				DTOAspirantes sustituido = obtenerAspirante(
						sustitucionRescisionCAE.getIdProcesoElectoral(),
						sustitucionRescisionCAE.getIdDetalleProceso(),
						sustitucionRescisionCAE.getIdParticipacion(),
						sustitucionRescisionCAE.getIdAspiranteSustituido());
				if (request.getImgB64Sustituido() != null) {
					sustituido.setUrlFoto(bsdCommons.almacenarFotoAspirante(
							request.getImgB64Sustituido(),
							request.getExtensionArchSustituido(),
							sustitucionRescisionCAE.getIdProcesoElectoral(),
							sustitucionRescisionCAE.getIdDetalleProceso(),
							sustitucionRescisionCAE.getIdParticipacion(),
							sustitucionRescisionCAE.getIdAspiranteSustituido()));
				}
				if (sustitucionRescisionCAE.getIdAspiranteSustituto() != null) {
					sustitutoCAE = obtenerAspirante(
							sustitucionRescisionCAE.getIdProcesoElectoral(),
							sustitucionRescisionCAE.getIdDetalleProceso(),
							sustitucionRescisionCAE.getIdParticipacion(),
							sustitucionRescisionCAE.getIdAspiranteSustituto());
					sustitutoCAE.setCorreoCtaCreada(sustitucionRescisionCAE.getCorreoCuenta());
					if (sustitucionRescisionCAE.getImgB64() != null) {
						sustitutoCAE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionCAE.getImgB64(),
								sustitucionRescisionCAE.getExtensionArchivo(),
								sustitutoCAE.getIdProcesoElectoral(),
								sustitutoCAE.getIdDetalleProceso(),
								sustitutoCAE.getIdParticipacion(),
								sustitutoCAE.getIdAspirante()));
					}
				}
				sustitucionCAE = obtenerObjetoSustitucion(
						sustitucionRescisionCAE,
						sustituido,
						sustitutoCAE,
						idSustitucion,
						idRelacion);
				sustitucionCAE.setFechaMovimiento(new Date());
				if (sustitucionRescisionCAE.getIdAspiranteSustituto() != null) {
					sustitutoCAE.setIdPuesto(Constantes.ID_PUESTO_CAE);
					sustitutoCAE.setIdZonaResponsabilidad1e(sustituido.getIdZonaResponsabilidad1e());
					sustitutoCAE.setIdAreaResponsabilidad1e(sustituido.getIdAreaResponsabilidad1e());
					sustitutoCAE.setIdZonaResponsabilidad2e(sustituido.getIdZonaResponsabilidad2e());
					sustitutoCAE.setIdAreaResponsabilidad2e(sustituido.getIdAreaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoCAE,
							sustitucionRescisionCAE.getUsuario(),
							request.getIpUsuario());
				}
				sustituido.setIdPuesto(obtenerPuesto(
						sustitucionRescisionCAE.getTipoCausaVacante(),
						sustitucionRescisionCAE.getIdCausaVacante(),
						Constantes.ID_PUESTO_CAE));
				if (Constantes.TIPO_OTRAS_CAUSAS.equals(sustitucionRescisionCAE.getTipoCausaVacante())
						&& Constantes.ID_CAUSA_VACANTE_DECLINAR.equals(sustitucionRescisionCAE.getIdCausaVacante())) {
					sustituido.setDeclinoCargo(Constantes.ID_PUESTO_CAE);
				}
				resetZOREAREAssigned(sustituido);
				asSustRescision.guardaAspirante(
						sustituido,
						sustitucionRescisionCAE.getUsuario(),
						request.getIpUsuario());

				asSustRescision.guardaSustitucion(
						sustitucionCAE,
						sustitucionRescisionCAE.getUsuario(),
						request.getIpUsuario());

				hlpCuentasSustituciones.eliminarCuentaSustitucion(
						sustituido,
						sustitucionRescisionCAE.getUsuario());
				hlpCuentasSustituciones.crearCuentaSustitucion(
						sustitutoCAE,
						sustitucionRescisionCAE.getUsuario());
				return true;
			}
		} catch (Exception e) {
			log.error("ERROR BSDSustRescisionImpl -guardarModificarSustRescision: ", e);
		}

		return false;
	}

	@Override
	public boolean guardarSustRescisionPendiente(DTORequestSustRescision request) {
		DTOSustRescision sustitucionRescisionSE = request.getDatosSustitucionSe();
		DTOSustRescision sustitucionRescisionCAE = request.getDatosSustitucionCae();

		DTOAspirantes sustituido = new DTOAspirantes();
		DTOAspirantes sustitutoSE = new DTOAspirantes();
		DTOAspirantes sustitutoCAE = new DTOAspirantes();

		DTOSustitucionesSupycap sustitucionSE = new DTOSustitucionesSupycap();
		DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();

		String idRelacion = UUID.randomUUID().toString();
		Integer idPuestoSustitutoSE = 0;

		try {
			if (sustitucionRescisionSE != null) {
				Integer idSustitucionSE = asSustRescision.findIdSustitucion(
						sustitucionRescisionSE.getIdDetalleProceso(),
						sustitucionRescisionSE.getIdParticipacion());

				updateAspiranteFoto(
						sustitucionRescisionSE.getIdProcesoElectoral(),
						sustitucionRescisionSE.getIdDetalleProceso(),
						sustitucionRescisionSE.getIdParticipacion(),
						sustitucionRescisionSE.getIdAspiranteSustituido(),
						request.getImgB64Sustituido(),
						request.getExtensionArchSustituido(),
						sustitucionRescisionSE.getUsuario(),
						request.getIpUsuario());

				sustitucionSE = asSustRescision.obtenerSustitucionPendiente(
						sustitucionRescisionSE.getIdProcesoElectoral(),
						sustitucionRescisionSE.getIdDetalleProceso(),
						sustitucionRescisionSE.getIdParticipacion(),
						sustitucionRescisionSE.getIdAspiranteSustituido(),
						sustitucionRescisionSE.getTipoCausaVacante());

				if (sustitucionRescisionCAE != null) {
					sustitutoCAE = obtenerAspirante(
							sustitucionRescisionSE.getIdProcesoElectoral(),
							sustitucionRescisionSE.getIdDetalleProceso(),
							sustitucionRescisionSE.getIdParticipacion(),
							sustitucionRescisionCAE.getIdAspiranteSustituto());
					sustitutoCAE.setCorreoCtaCreada(sustitucionRescisionCAE.getCorreoCuenta());
					if (sustitucionRescisionCAE.getImgB64() != null) {
						sustitutoCAE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionCAE.getImgB64(),
								sustitucionRescisionCAE.getExtensionArchivo(),
								sustitutoCAE.getIdProcesoElectoral(),
								sustitutoCAE.getIdDetalleProceso(),
								sustitutoCAE.getIdParticipacion(),
								sustitutoCAE.getIdAspirante()));
					}
				}

				if (sustitucionRescisionSE.getIdAspiranteSustituto() != null) {
					Integer idSustitucionCAE = idSustitucionSE + 1;
					sustitutoSE = obtenerAspirante(
							sustitucionRescisionSE.getIdProcesoElectoral(),
							sustitucionRescisionSE.getIdDetalleProceso(),
							sustitucionRescisionSE.getIdParticipacion(),
							sustitucionRescisionSE.getIdAspiranteSustituto());
					sustitutoSE.setCorreoCtaCreada(sustitucionRescisionSE.getCorreoCuenta());
					if (sustitucionRescisionSE.getImgB64() != null) {
						sustitutoSE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionSE.getImgB64(),
								sustitucionRescisionSE.getExtensionArchivo(),
								sustitutoSE.getIdProcesoElectoral(),
								sustitutoSE.getIdDetalleProceso(),
								sustitutoSE.getIdParticipacion(),
								sustitutoSE.getIdAspirante()));
					}

					if (sustitucionSE.getIdAspiranteSutituto() == null) {
						sustitucionSE.setIdAspiranteSutituto(sustitutoSE.getIdAspirante());
						sustitucionSE.setIdPuestoSustituto(sustitutoSE.getIdPuesto());
						sustitucionSE
								.setFechaAlta(
										ApplicationUtil.convertStringADate(sustitucionRescisionSE.getFechaAlta()));
						sustitucionSE.setCorreoCtaCreadaSustituto(sustitutoSE.getCorreoCtaCreada());
						sustitucionSE.setCorreoCtaNotifSustituto(sustitutoSE.getCorreoCtaNotificacion());
						sustitucionSE.setFechaMovimiento(new Date());
					}

					if (Constantes.ID_PUESTO_CAE.equals(sustitutoSE.getIdPuesto())
							|| Constantes.ID_PUESTO_CAE_TMP.equals(sustitutoSE.getIdPuesto())) {
						sustitucionCAE = obtenerObjetoSustitucion(
								sustitucionRescisionCAE != null ? sustitucionRescisionCAE : sustitucionRescisionSE,
								sustitutoSE,
								sustitutoCAE,
								idSustitucionCAE,
								idRelacion);
						sustitucionCAE.setIdCausaVacante(Constantes.ID_CAUSA_VACANTE_PROMOCION);
						sustitucionCAE.setTipoCausaVacante(Constantes.TIPO_CAUSA_VACANTE_OTRAS_CAUSAS);
						sustitucionCAE.setFechaMovimiento(new Date());
						sustitucionCAE.setIdRelacionSustituciones(sustitucionSE.getIdRelacionSustituciones());
						asSustRescision.guardaSustitucion(
								sustitucionCAE,
								sustitucionRescisionSE.getUsuario(),
								request.getIpUsuario());
					}
				}

				if (sustitucionRescisionCAE != null) {
					sustitutoCAE.setIdPuesto(Constantes.ID_PUESTO_CAE);
					sustitutoCAE.setIdAreaResponsabilidad1e(sustitutoSE.getIdAreaResponsabilidad1e());
					sustitutoCAE.setIdAreaResponsabilidad2e(sustitutoSE.getIdAreaResponsabilidad2e());
					sustitutoCAE.setIdZonaResponsabilidad1e(sustitutoSE.getIdZonaResponsabilidad1e());
					sustitutoCAE.setIdZonaResponsabilidad2e(sustitutoSE.getIdZonaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoCAE,
							sustitucionRescisionCAE.getUsuario(),
							request.getIpUsuario());
				}

				if (sustitucionRescisionSE.getIdAspiranteSustituto() != null) {
					if (sustitutoSE.getIdPuesto().equals(Constantes.ID_PUESTO_CAE)) {
						idPuestoSustitutoSE = Constantes.ID_PUESTO_CAE;
					}
					sustitutoSE.setIdPuesto(Constantes.ID_PUESTO_SE);
					sustitutoSE.setIdAreaResponsabilidad1e(sustitucionSE.getIdAreaResponsabilidad1e());
					sustitutoSE.setIdAreaResponsabilidad2e(sustitucionSE.getIdAreaResponsabilidad2e());
					sustitutoSE.setIdZonaResponsabilidad1e(sustitucionSE.getIdAZonaResponsabilidad1e());
					sustitutoSE.setIdZonaResponsabilidad2e(sustitucionSE.getIdZonaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario(),
							request.getIpUsuario());
				}

				asSustRescision.guardaSustitucion(
						sustitucionSE,
						sustitucionRescisionSE.getUsuario(),
						request.getIpUsuario());

				hlpCuentasSustituciones.eliminarCuentaSustitucion(
						sustituido,
						sustitucionRescisionSE.getUsuario());
				if (idPuestoSustitutoSE.equals(Constantes.ID_PUESTO_CAE)) {
					hlpCuentasSustituciones.modificarCuentaSustitucion(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario());
				} else {
					hlpCuentasSustituciones.crearCuentaSustitucion(
							sustitutoSE,
							sustitucionRescisionSE.getUsuario());
				}
				hlpCuentasSustituciones.crearCuentaSustitucion(
						sustitutoCAE,
						sustitucionRescisionSE.getUsuario());
				return true;
			} else if (sustitucionRescisionCAE != null) {

				updateAspiranteFoto(
						sustitucionRescisionCAE.getIdProcesoElectoral(),
						sustitucionRescisionCAE.getIdDetalleProceso(),
						sustitucionRescisionCAE.getIdParticipacion(),
						sustitucionRescisionCAE.getIdAspiranteSustituido(),
						request.getImgB64Sustituido(),
						request.getExtensionArchSustituido(),
						sustitucionRescisionCAE.getUsuario(),
						request.getIpUsuario());

				if (sustitucionRescisionCAE.getIdAspiranteSustituto() != null) {
					sustitutoCAE = obtenerAspirante(
							sustitucionRescisionCAE.getIdProcesoElectoral(),
							sustitucionRescisionCAE.getIdDetalleProceso(),
							sustitucionRescisionCAE.getIdParticipacion(),
							sustitucionRescisionCAE.getIdAspiranteSustituto());
					sustitutoCAE.setCorreoCtaCreada(sustitucionRescisionCAE.getCorreoCuenta());
					if (sustitucionRescisionCAE.getImgB64() != null) {
						sustitutoCAE.setUrlFoto(bsdCommons.almacenarFotoAspirante(
								sustitucionRescisionCAE.getImgB64(),
								sustitucionRescisionCAE.getExtensionArchivo(),
								sustitutoCAE.getIdProcesoElectoral(),
								sustitutoCAE.getIdDetalleProceso(),
								sustitutoCAE.getIdParticipacion(),
								sustitutoCAE.getIdAspirante()));
					}
				}

				sustitucionCAE = asSustRescision.obtenerSustitucionPendiente(
						sustitucionRescisionCAE.getIdProcesoElectoral(),
						sustitucionRescisionCAE.getIdDetalleProceso(),
						sustitucionRescisionCAE.getIdParticipacion(),
						sustitucionRescisionCAE.getIdAspiranteSustituido(),
						sustitucionRescisionCAE.getTipoCausaVacante());
				sustitucionCAE.setIdAspiranteSutituto(sustitutoCAE.getIdAspirante());
				sustitucionCAE.setIdPuestoSustituto(sustitutoCAE.getIdPuesto());
				sustitucionCAE.setFechaAlta(ApplicationUtil.convertStringADate(sustitucionRescisionCAE.getFechaAlta()));
				sustitucionCAE.setCorreoCtaCreadaSustituto(sustitucionRescisionCAE.getCorreoCuenta());
				sustitucionCAE.setCorreoCtaNotifSustituto(sustitutoCAE.getCorreoCtaNotificacion());
				sustitucionCAE.setFechaMovimiento(new Date());

				if (sustitucionRescisionCAE.getIdAspiranteSustituto() != null) {
					sustitutoCAE.setIdPuesto(Constantes.ID_PUESTO_CAE);
					sustitutoCAE.setIdZonaResponsabilidad1e(sustitucionCAE.getIdAZonaResponsabilidad1e());
					sustitutoCAE.setIdAreaResponsabilidad1e(sustitucionCAE.getIdAreaResponsabilidad1e());
					sustitutoCAE.setIdZonaResponsabilidad2e(sustitucionCAE.getIdZonaResponsabilidad2e());
					sustitutoCAE.setIdAreaResponsabilidad2e(sustitucionCAE.getIdAreaResponsabilidad2e());
					asSustRescision.guardaAspirante(
							sustitutoCAE,
							sustitucionRescisionCAE.getUsuario(),
							request.getIpUsuario());
				}

				asSustRescision.guardaSustitucion(
						sustitucionCAE,
						sustitucionRescisionCAE.getUsuario(),
						request.getIpUsuario());

				hlpCuentasSustituciones.eliminarCuentaSustitucion(
						sustituido,
						sustitucionRescisionCAE.getUsuario());

				hlpCuentasSustituciones.crearCuentaSustitucion(
						sustitutoCAE,
						sustitucionRescisionCAE.getUsuario());
				return true;
			}
		} catch (Exception e) {
			log.error("ERROR BSDSustRescisionImpl -guardarSustRescisionPendiente: ", e);
		}

		return false;
	}

	@Override
	public boolean modificarSustRescision(DTORequestModificarSustRescision request) {
		DTOSustitucionesSupycap sustitucionSE = new DTOSustitucionesSupycap();
		DTOSustitucionesSupycap sustitucionCAE = new DTOSustitucionesSupycap();
		try {

			if (request.getIdSustitucionSE() != null) {
				sustitucionSE = asSustRescision.obtenerSustitucion(
						request.getIdDetalleProceso(),
						request.getIdParticipacion(),
						request.getIdSustitucionSE())
						.orElseThrow();
				sustitucionSE.setTipoCausaVacante(request.getTipoCausaVacante());
				sustitucionSE.setIdCausaVacante(request.getIdCausaVacante());
				sustitucionSE.setObservaciones(request.getObservaciones());
				sustitucionSE.setFechaBaja(ApplicationUtil.convertStringADate(request.getFechaBaja()));
				sustitucionSE.setFechaAlta(request.getFechaAltaSupervisor() != null
						? ApplicationUtil.convertStringADate(request.getFechaAltaSupervisor())
						: null);
				asSustRescision.guardaSustitucion(
						sustitucionSE,
						request.getUsuario(),
						request.getIpUsuario());

				updateAspiranteFoto(
						sustitucionSE.getIdProcesoElectoral(),
						sustitucionSE.getId().getIdDetalleProceso(),
						sustitucionSE.getId().getIdParticipacion(),
						sustitucionSE.getIdAspiranteSutituido(),
						request.getImgB64Sustituido(),
						request.getExtensionArchivoSustituido(),
						request.getUsuario(),
						request.getIpUsuario());

				updateAspiranteFoto(
						sustitucionSE.getIdProcesoElectoral(),
						sustitucionSE.getId().getIdDetalleProceso(),
						sustitucionSE.getId().getIdParticipacion(),
						sustitucionSE.getIdAspiranteSutituto(),
						request.getImgB64SustitutoSE(),
						request.getExtensionArchivoSustitutoSE(),
						request.getUsuario(),
						request.getIpUsuario());

				if (request.getIdSustitucionCAE() != null) {
					sustitucionCAE = asSustRescision.obtenerSustitucion(
							request.getIdDetalleProceso(),
							request.getIdParticipacion(),
							request.getIdSustitucionCAE())
							.orElseThrow();
					sustitucionCAE
							.setFechaBaja(ApplicationUtil.convertStringADate(request.getFechaAltaSupervisor()));
					if (request.getFechaAltaCapacitador() != null) {
						sustitucionCAE
								.setFechaAlta(ApplicationUtil.convertStringADate(request.getFechaAltaCapacitador()));
					}
					asSustRescision.guardaSustitucion(
							sustitucionCAE,
							request.getUsuario(),
							request.getIpUsuario());

					updateAspiranteFoto(
							sustitucionCAE.getIdProcesoElectoral(),
							sustitucionCAE.getId().getIdDetalleProceso(),
							sustitucionCAE.getId().getIdParticipacion(),
							sustitucionCAE.getIdAspiranteSutituto(),
							request.getImgB64SustitutoCAE(),
							request.getExtensionArchivoSustitutoCAE(),
							request.getUsuario(),
							request.getIpUsuario());
				}
				return true;
			} else if (request.getIdSustitucionCAE() != null) {
				sustitucionCAE = asSustRescision.obtenerSustitucion(
						request.getIdDetalleProceso(),
						request.getIdParticipacion(),
						request.getIdSustitucionCAE())
						.orElseThrow();
				sustitucionCAE.setTipoCausaVacante(request.getTipoCausaVacante());
				sustitucionCAE.setIdCausaVacante(request.getIdCausaVacante());
				sustitucionCAE.setObservaciones(request.getObservaciones());
				sustitucionCAE.setFechaBaja(ApplicationUtil.convertStringADate(request.getFechaBaja()));
				if (request.getFechaAltaCapacitador() != null) {
					sustitucionCAE.setFechaAlta(ApplicationUtil.convertStringADate(request.getFechaAltaCapacitador()));
				}
				asSustRescision.guardaSustitucion(
						sustitucionCAE,
						request.getUsuario(),
						request.getIpUsuario());
				updateAspiranteFoto(
						sustitucionCAE.getIdProcesoElectoral(),
						sustitucionCAE.getId().getIdDetalleProceso(),
						sustitucionCAE.getId().getIdParticipacion(),
						sustitucionCAE.getIdAspiranteSutituido(),
						request.getImgB64Sustituido(),
						request.getExtensionArchivoSustituido(),
						request.getUsuario(),
						request.getIpUsuario());
				updateAspiranteFoto(
						sustitucionCAE.getIdProcesoElectoral(),
						sustitucionCAE.getId().getIdDetalleProceso(),
						sustitucionCAE.getId().getIdParticipacion(),
						sustitucionCAE.getIdAspiranteSutituto(),
						request.getImgB64SustitutoCAE(),
						request.getExtensionArchivoSustitutoCAE(),
						request.getUsuario(),
						request.getIpUsuario());
				return true;
			}
		} catch (Exception e) {
			log.error("ERROR BSDSustRescisionImpl -modificarSustRescision: ", e);
		}

		return false;
	}

	private void resetZOREAREAssigned(DTOAspirantes aspirante) {
		aspirante.setIdZonaResponsabilidad1e(null);
		aspirante.setIdAreaResponsabilidad1e(null);
		aspirante.setIdZonaResponsabilidad2e(null);
		aspirante.setIdAreaResponsabilidad2e(null);
	}

	private void updateAspiranteFoto(Integer proceso, Integer idDetalle, Integer idParticipacion,
			Integer idAspirante, String imagen, String extension, String usuario, String ipUsuario) {
		if (imagen == null)
			return;
		DTOAspirantes aspirante = obtenerAspirante(
				proceso,
				idDetalle,
				idParticipacion,
				idAspirante);
		aspirante.setUrlFoto(bsdCommons.almacenarFotoAspirante(
				imagen,
				extension,
				proceso,
				idDetalle,
				idParticipacion,
				idAspirante));
		asSustRescision.guardaAspirante(
				aspirante,
				usuario,
				ipUsuario);
	}

	private DTOAspirantes obtenerAspirante(Integer proceso, Integer idDetalle, Integer idParticipacion,
			Integer idAspirante) {
		return asSustRescision.buscarAspirante(new DTOAspirantesId(
				proceso,
				idDetalle,
				idParticipacion,
				idAspirante))
				.orElseThrow();
	}

	private DTOSustitucionesSupycap obtenerObjetoSustitucion(DTOSustRescision sustitucionRescision,
			DTOAspirantes sustituido, DTOAspirantes sustituto, Integer idSustitucion,
			String idRelacion) {
		DTOSustitucionesSupycapId sustitucionId = new DTOSustitucionesSupycapId(
				sustitucionRescision.getIdDetalleProceso(),
				sustitucionRescision.getIdParticipacion(),
				idSustitucion);

		DTOSustitucionesSupycap sustitucion = new DTOSustitucionesSupycap();
		sustitucion.setIdProcesoElectoral(sustitucionRescision.getIdProcesoElectoral());
		sustitucion.setId(sustitucionId);
		sustitucion.setIdRelacionSustituciones(idRelacion);

		sustitucion.setEtapa(2);
		sustitucion.setIdCausaVacante(sustitucionRescision.getIdCausaVacante());
		sustitucion.setTipoCausaVacante(sustitucionRescision.getTipoCausaVacante());
		sustitucion.setObservaciones(sustitucionRescision.getObservaciones());
		sustitucion.setFechaSustitucion(new Date());
		sustitucion.setFechaBaja(ApplicationUtil.convertStringADate(
				sustitucionRescision.getFechaBaja() != null ? sustitucionRescision.getFechaBaja()
						: sustitucionRescision.getFechaAlta()));

		sustitucion.setIdAspiranteSutituido(sustituido.getIdAspirante());
		sustitucion.setIdPuestoSustituido(sustituido.getIdPuesto());
		sustitucion.setCorreoCtaCreadaSustituido(sustituido.getCorreoCtaCreada());
		sustitucion.setCorreoCtaNotifSustituido(sustituido.getCorreoCtaNotificacion());
		sustitucion.setUidCuentaSustituido(sustituido.getUidCuenta());

		sustitucion.setIdAZonaResponsabilidad1e(sustituido.getIdZonaResponsabilidad1e());
		sustitucion.setIdAreaResponsabilidad1e(sustituido.getIdAreaResponsabilidad1e());
		sustitucion.setIdZonaResponsabilidad2e(sustituido.getIdZonaResponsabilidad2e());
		sustitucion.setIdAreaResponsabilidad2e(sustituido.getIdAreaResponsabilidad2e());

		if (sustituto.getId() != null) {
			sustitucion.setIdAspiranteSutituto(sustituto.getId().getIdAspirante());
			sustitucion.setIdPuestoSustituto(sustituto.getIdPuesto());
			sustitucion.setCorreoCtaCreadaSustituto(sustituto.getCorreoCtaCreada());
			sustitucion.setCorreoCtaNotifSustituto(sustituto.getCorreoCtaNotificacion());
			sustitucion.setUidCuentaSustituto(sustituto.getUidCuenta());
			sustitucion.setFechaAlta(ApplicationUtil.convertStringADate(sustitucionRescision.getFechaAlta()));
		}

		return sustitucion;
	}

	private Integer obtenerPuesto(Integer tipoCausa, Integer idCausa, Integer idPuestoPrevio) {

		if (Constantes.ID_PUESTO_SE.equals(idPuestoPrevio)
				&& Constantes.TIPO_CAUSA_RESCISION.equals(tipoCausa)) {
			return Constantes.ID_PUESTO_RESCISION_SE;
		}
		if (Constantes.ID_PUESTO_CAE.equals(idPuestoPrevio)
				&& Constantes.TIPO_CAUSA_RESCISION.equals(tipoCausa)) {
			return Constantes.ID_PUESTO_RESCISION_CAE;
		}
		if (Constantes.ID_PUESTO_SE.equals(idPuestoPrevio)
				&& Constantes.TIPO_CAUSA_TERMINACION.equals(tipoCausa)) {
			return Constantes.ID_PUESTO_RECONTRATACION_SE;
		}
		if (Constantes.ID_PUESTO_CAE.equals(idPuestoPrevio)
				&& Constantes.TIPO_CAUSA_TERMINACION.equals(tipoCausa)) {
			return Constantes.ID_PUESTO_RECONTRATACION_CAE;
		}
		if (Constantes.TIPO_OTRAS_CAUSAS.equals(tipoCausa)
				&& Constantes.ID_CAUSA_VACANTE_FALLECIMIENTO.equals(idCausa)) {
			return Constantes.ID_PUESTO_FALLECIMIENTO;
		}
		if (Constantes.TIPO_OTRAS_CAUSAS.equals(tipoCausa)
				&& Constantes.ID_CAUSA_VACANTE_PROMOCION.equals(idCausa)) {
			return Constantes.ID_PUESTO_CAE_PROMOCION;
		}
		if (Constantes.TIPO_OTRAS_CAUSAS.equals(tipoCausa)
				&& Constantes.ID_CAUSA_VACANTE_DECLINAR.equals(idCausa)) {
			return Constantes.ID_PUESTO_LISTA_RESERVA;
		}

		return 0;
	}

}
