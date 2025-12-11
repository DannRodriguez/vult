package mx.ine.sustseycae.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOCEtiquetasId;

@Repository("repoJPACEtiquetas")
public interface RepoJPACEtiquetas extends JpaRepository<DTOCEtiquetas, DTOCEtiquetasId> {

    @Query(value = """
			SELECT MAX(ETIQUETA)
			FROM SUPYCAP.C_ETIQUETAS
			WHERE ID_ETIQUETA = :idEtiqueta""", nativeQuery = true)
    public String obtenerUrlWsAus(@Param("idEtiqueta") Integer idEtiqueta);

    public DTOCEtiquetas findTopById_IdProcesoElectoralAndId_IdEtiqueta(Integer idProcesoElectoral, Integer idEtiqueta);

    public DTOCEtiquetas findTopById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdEtiqueta(
            Integer idProcesoElectoral, Integer idDetalleProceso, Integer idEtiqueta);

    @Query(nativeQuery = true)
    String obtieneEtiquetasRecursivamente(@Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso, @Param("idEstado") Integer idEstado,
            @Param("idDistrito") Integer idDistrito, @Param("idEtiqueta") Integer idEtiqueta);

}
