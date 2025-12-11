package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mx.ine.sustseycae.dto.db.DTOCImpactos;
import mx.ine.sustseycae.dto.db.DTOCImpactosId;
import mx.ine.sustseycae.dto.vo.VOCImpactos;

public interface RepoJPACImpactos extends JpaRepository<DTOCImpactos, DTOCImpactosId> {

	@Query(nativeQuery = true)
	public List<VOCImpactos> getAllCImpactos();

}
