package mx.ine.sustseycae.repositoriesadmin;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.ine.sustseycae.dto.dbadmin.DTOGruposSistemas;
import mx.ine.sustseycae.dto.dbadmin.DTOGruposSistemasId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoJPAGruposSistema extends JpaRepository<DTOGruposSistemas, DTOGruposSistemasId> {

	@Query(nativeQuery = true)
	public List<String> getGrupoSistemas(@Param("idSistema") Integer idSistema);

}
