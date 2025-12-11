package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.ine.sustseycae.dto.db.DTOCFrecuencias;
import mx.ine.sustseycae.dto.db.DTOCFrecuenciasId;
import mx.ine.sustseycae.dto.vo.VOCFrecuencias;

public interface RepoJPACFrecuencias extends JpaRepository<DTOCFrecuencias, DTOCFrecuenciasId> {

	@Query(nativeQuery = true)
	public List<VOCFrecuencias> getAllCFrecuencias();

}
