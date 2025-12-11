package mx.ine.sustseycae.repositoriesgeografico;

import mx.ine.sustseycae.dto.db.DTOGeografico;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.ine.sustseycae.dto.dbgeografico.DTOEstados;
import mx.ine.sustseycae.dto.dbgeografico.DTOEstadosId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoJPAEstados extends JpaRepository<DTOEstados, DTOEstadosId>{

	
	public DTOEstados findById_IdEstadoAndId_IdCorte(Integer idEstado, Integer idCorte);

	@Query(nativeQuery = true)
	public DTOGeografico getNombreEstado(
			@Param("idEstado") Integer idEstado
	);

	@Query(nativeQuery = true)
	public DTOGeografico getNombreDistrito(
			@Param("idEstado") Integer idEstado,
			@Param("idDistrito") Integer idDistrito
	);
}
