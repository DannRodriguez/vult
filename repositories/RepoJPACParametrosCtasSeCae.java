package mx.ine.sustseycae.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOCParametrosCtasSeCae;
import mx.ine.sustseycae.dto.db.DTOCParametrosCtasSeCaeId;

public interface RepoJPACParametrosCtasSeCae extends JpaRepository<DTOCParametrosCtasSeCae, DTOCParametrosCtasSeCaeId> {

	@Query(value = """
			SELECT DESCRIPCION_VALORES
			FROM SUPYCAP.C_PARAMETROS_CTAS_SE_CAE
			WHERE ID_PARAMETRO = :parametro""", nativeQuery = true)
	public String obtenerEtiquetaAcuse(@Param("parametro") Integer idParametro);

	public List<DTOCParametrosCtasSeCae> findById_IdParametroIn(Set<Integer> idParametros);

}
