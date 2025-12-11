package mx.ine.sustseycae.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCParametrosId;

public interface RepoJPACParametros extends JpaRepository<DTOCParametros, DTOCParametrosId> {

    @Query(nativeQuery = true)
    BigDecimal obtieneParametrosRecursivamente(
            @Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idEstado") Integer idEstado,
            @Param("idDistrito") Integer idDistrito,
            @Param("idParametro") Integer idParametro);

    public List<DTOCParametros> findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParametroIn(
            Integer idProcesoElectoral, Integer idDetalleProceso, Set<Integer> idParametro);

    public List<DTOCParametros> findById_IdProcesoElectoralAndId_IdParametroIn(Integer idProcesoElectoral,
            Set<Integer> idParametro);

}
