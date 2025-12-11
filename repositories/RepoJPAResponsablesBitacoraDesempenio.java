package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenioId;
import mx.ine.sustseycae.dto.vo.VOResponsableBitacoraDesempenio;

public interface RepoJPAResponsablesBitacoraDesempenio
		extends JpaRepository<DTOResponsablesBitacoraDesempenio, DTOResponsablesBitacoraDesempenioId> {

	@Query(nativeQuery = true)
	public List<VOResponsableBitacoraDesempenio> getResponsablesBitacoraDesempenio(
			@Param("idDetalleProceso") Integer idDetalleProceso,
			@Param("idParticipacion") Integer idParticipacion,
			@Param("idBitacora") Integer idBitacora);

	@Modifying
	@Query(value = """
			DELETE FROM SUPYCAP.RESPONSABLES_BITACORA_DESEMPENIO
			WHERE ID_DETALLE_PROCESO = :idDetalleProceso
			   AND ID_PARTICIPACION = :idParticipacion
			   AND ID_BITACORA_DESEMPENIO = :idBitacoraDesempenio""", nativeQuery = true)
	public void deleteResponsablesBitacoraDesempenio(
			@Param("idDetalleProceso") Integer idDetalleProceso,
			@Param("idParticipacion") Integer idParticipacion,
			@Param("idBitacoraDesempenio") Integer idBitacoraDesempenio);
}
