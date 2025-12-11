package mx.ine.sustseycae.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenioId;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;

public interface RepoJPABitacoraDesempenio
		extends JpaRepository<DTOBitacoraDesempenio, DTOBitacoraDesempenioId>, RepoJPABitacoraDesempenioCustom {

	@Modifying
	@Query(value = """
			DELETE FROM SUPYCAP.BITACORA_DESEMPENIO
			WHERE ID_DETALLE_PROCESO = :idDetalleProceso
			    AND ID_PARTICIPACION = :idParticipacion
			    AND ID_BITACORA_DESEMPENIO = :idBitacora
			    AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
	public void deleteBitacoraDesempenio(
			@Param("idDetalleProceso") Integer idDetalleProceso,
			@Param("idParticipacion") Integer idParticipacion,
			@Param("idBitacora") Integer idBitacora,
			@Param("idAspirante") Integer idAspirante);

	@Query(nativeQuery = true)
	public VOBitacoraDesempenio getBitacoraDesempenioAspirante(
			@Param("idDetalleProceso") Integer idDetalleProceso,
			@Param("idParticipacion") Integer idParticipacion,
			@Param("idAspirante") Integer idAspirante);

}
