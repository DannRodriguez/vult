package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.ine.sustseycae.dto.db.DTOGruposDefaultSistema;
import mx.ine.sustseycae.dto.db.DTOGruposDefaultSistemaId;

public interface RepoJPAGruposDefaultSistema extends JpaRepository<DTOGruposDefaultSistema, DTOGruposDefaultSistemaId> {

	public List<DTOGruposDefaultSistema> findById_IdActor(Integer idActor);

}
