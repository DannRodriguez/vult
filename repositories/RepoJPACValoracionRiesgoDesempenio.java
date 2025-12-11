package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.ine.sustseycae.dto.db.DTOCValoracionRiesgoDesempenio;
import mx.ine.sustseycae.dto.db.DTOCValoracionRiesgoDesempenioId;
import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;

public interface RepoJPACValoracionRiesgoDesempenio
		extends JpaRepository<DTOCValoracionRiesgoDesempenio, DTOCValoracionRiesgoDesempenioId> {

	@Query(nativeQuery = true)
	public List<VOCValoracionRiesgo> getAllCValoracionRiesgo();

}
