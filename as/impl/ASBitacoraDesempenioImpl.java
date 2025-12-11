package mx.ine.sustseycae.as.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.ine.sustseycae.as.ASBitacoraDesempenioInterface;

import mx.ine.sustseycae.dto.DTOConsultaIntegrantes;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOCFrecuencias;
import mx.ine.sustseycae.dto.vo.VOCImpactos;
import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;
import mx.ine.sustseycae.dto.vo.VOResponsableBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOCVocalesSust;
import mx.ine.sustseycae.repositories.RepoJPAAspirantes;
import mx.ine.sustseycae.repositories.RepoJPABitacoraDesempenio;
import mx.ine.sustseycae.repositories.RepoJPACEtiquetas;
import mx.ine.sustseycae.repositories.RepoJPACFrecuencias;
import mx.ine.sustseycae.repositories.RepoJPACImpactos;
import mx.ine.sustseycae.repositories.RepoJPACValoracionRiesgoDesempenio;
import mx.ine.sustseycae.repositories.RepoJPAParticipacionGeografica;
import mx.ine.sustseycae.repositories.RepoJPAResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.repositories.RepoJPACVocalesSust;
import mx.ine.sustseycae.repositories.RepoSesionesIntegrantesInterface;

@Service("asBitacoraDesempenio")
@Scope("prototype")
public class ASBitacoraDesempenioImpl implements ASBitacoraDesempenioInterface {

    
	@Autowired
	private RepoJPACFrecuencias repoPACFrecuencias;

	@Autowired
	private RepoJPACImpactos repoJPACImpactos;

	@Autowired
	private RepoJPACEtiquetas repoJPACEtiquetas;

	@Autowired
	private RepoJPACValoracionRiesgoDesempenio repoJPACValoracionDesempenio;

	@Autowired
	private RepoJPABitacoraDesempenio repoJPABitacoraDesempenio;

	@Autowired
	private RepoJPAResponsablesBitacoraDesempenio repoJPAResponsablesBitacoraDesempenio;

	@Autowired
	private RepoJPAAspirantes repoJPAAspirantes;

	@Autowired
	private RepoJPAParticipacionGeografica repoJPAParticipacionGeografica;

	@Autowired
	private RepoJPACVocalesSust repoJPACVocalesSust;

	@Autowired
	@Qualifier("repoSesionesIntegrantes")
	private RepoSesionesIntegrantesInterface repoSesionesIntegrantes;

	@Autowired
	@Qualifier("hostWSIntegrantesSesiones")
	String hostWSIntegrantesSesiones;

    @Override
	public List<VOCFrecuencias> obtenerCFrecuencias() {

		return repoPACFrecuencias.getAllCFrecuencias();
	}

	@Override
	public List<VOCImpactos> obtenerCImpactos() {

		return repoJPACImpactos.getAllCImpactos();
	}

	@Override
	public List<VOCValoracionRiesgo> obtenerCValoracionRiesgo() {

		return repoJPACValoracionDesempenio.getAllCValoracionRiesgo();
	}

	@Override
	public VOBitacoraDesempenio obtenerBitacoraDesempenioAspirante(Integer idDetalleProceso, Integer idParticipacion,
			Integer idAspirante) {

		return repoJPABitacoraDesempenio.getBitacoraDesempenioAspirante(idDetalleProceso, idParticipacion, idAspirante);
	}

	@Override
	public List<VOResponsableBitacoraDesempenio> obtenerResponsablesBitacoraDesempenio(Integer idDetalleProceso,
			Integer idParticipacion, Integer idBitacora) {

		return repoJPAResponsablesBitacoraDesempenio.getResponsablesBitacoraDesempenio(idDetalleProceso,
				idParticipacion, idBitacora);
	}

	@Override
	public VOAspiranteBitacora obtenerAspiranteBitacora(Integer idDetalleProceso, Integer idParticipacion,
			Integer idAspirante) {

		return repoJPAAspirantes.getAspiranteBitacora(idDetalleProceso, idParticipacion, idAspirante);
	}

	@Override
	public DTOConsultaIntegrantes obtenerIntegrantesWsSesiones(List<Integer> tipoIntegrante, Integer idEstado,
			Integer idDistritoFederal, List<String> tipoPuesto, List<Integer> estatus, Integer idProceso,
			Integer idDetalleProceso, String tipoOrdenamiento, String campoOrdenamiento) throws Exception {

		return repoSesionesIntegrantes.obtenerIntegrantesWsSesiones(hostWSIntegrantesSesiones, tipoIntegrante, idEstado,
				idDistritoFederal, tipoPuesto,
				estatus, idProceso, idDetalleProceso,
				tipoOrdenamiento, campoOrdenamiento);
	}

	@Override
	public List<VOCVocalesSust> obtenerCatalogoVocalesSust(Integer idProceso,
			Integer idDetalleProceso) {
		return repoJPACVocalesSust.getAllVocalesSust(idProceso, idDetalleProceso);

	}

	@Override
	public DTOParticipacionGeografica obtenerParticipacionGeo(Integer idDetalleProceso, Integer idParticipacion) {
		return repoJPAParticipacionGeografica
				.findById_IdDetalleProcesoAndId_IdParticipacion(idDetalleProceso, idParticipacion);
	}

	@Override
	public DTOCEtiquetas obtenerEtiquetaCEtiquetas(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idEtiqueta) {

		DTOCEtiquetas etiqueta = repoJPACEtiquetas.findTopById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdEtiqueta(
				idProcesoElectoral, idDetalleProceso, idEtiqueta);

		if (etiqueta == null)
			etiqueta = repoJPACEtiquetas.findTopById_IdProcesoElectoralAndId_IdEtiqueta(idProcesoElectoral, idEtiqueta);

		return etiqueta;
	}

	@Override
	@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
			Exception.class })
	public void guardarBitacoraDesempenio(DTOBitacoraDesempenio bitacoraDesempenio,
			List<DTOResponsablesBitacoraDesempenio> listResponsables, Integer idBitacora) {

		if (idBitacora == null) { // es flujo captura
			Integer idBitacoraSeq = repoJPABitacoraDesempenio.getNextValSequenceIdBitacoraDesempeno(
					bitacoraDesempenio.getIdProcesoElectoral(),
					bitacoraDesempenio.getId().getIdDetalleProceso());

			bitacoraDesempenio.getId().setIdBitacoraDesempenio(idBitacoraSeq);
			bitacoraDesempenio.setFechaHora(new Date());
			listResponsables.forEach(r -> {
				r.getId().setIdBitacoraDesempenio(idBitacoraSeq);
				r.setFechaHora(new Date());
			});

		} else { // es flujo modificar

			bitacoraDesempenio.getId().setIdBitacoraDesempenio(idBitacora);
			bitacoraDesempenio.setFechaHora(new Date());

			if (listResponsables != null && !listResponsables.isEmpty()) {
				listResponsables.forEach(r -> {
					r.getId().setIdBitacoraDesempenio(idBitacora);
					r.setFechaHora(new Date());
				});
				repoJPAResponsablesBitacoraDesempenio.deleteResponsablesBitacoraDesempenio(
						bitacoraDesempenio.getId().getIdDetalleProceso(),
						bitacoraDesempenio.getId().getIdParticipacion(),
						idBitacora);
			}

		}

		repoJPABitacoraDesempenio.save(bitacoraDesempenio);
		if (listResponsables != null && !listResponsables.isEmpty())
			repoJPAResponsablesBitacoraDesempenio.saveAll(listResponsables);
	}

	@Override
	@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {
			Exception.class })
	public void eliminarBitacoraDesempenio(Integer idDetalleProceso, Integer idParticipacion,
			Integer idBitacoraDesempenio, Integer idAspirante) {

		repoJPAResponsablesBitacoraDesempenio.deleteResponsablesBitacoraDesempenio(idDetalleProceso, idParticipacion,
				idBitacoraDesempenio);
		repoJPABitacoraDesempenio.deleteBitacoraDesempenio(idDetalleProceso, idParticipacion, idBitacoraDesempenio,
				idAspirante);

	}

}
