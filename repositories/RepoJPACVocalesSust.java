package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOCVocalesSust;
import mx.ine.sustseycae.dto.db.DTOCVocalesSustId;
import mx.ine.sustseycae.dto.vo.VOCVocalesSust;

public interface RepoJPACVocalesSust
		extends JpaRepository<DTOCVocalesSust, DTOCVocalesSustId> {

	@Query(nativeQuery = true)
	public List<VOCVocalesSust> getAllVocalesSust(
			@Param("idProcesoElectoral") Integer idProcesoElectoral,
			@Param("idDetalleProceso") Integer idDetalleProceso);

}
