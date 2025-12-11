package mx.ine.sustseycae.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeograficaId;
import mx.ine.sustseycae.dto.vo.VOCombo;

public interface RepoJPAParticipacionGeografica
        extends JpaRepository<DTOParticipacionGeografica, DTOParticipacionGeograficaId> {

    @Query(value = """
			SELECT NOMBRE_ESTADO
			FROM GEOGRAFICOINE.ESTADOS
			WHERE ID_ESTADO = :idEstado
			   AND ID_CORTE = :corte """, nativeQuery = true)
    public String getNombreEstado(@Param("idEstado") Integer idEstado, @Param("corte") Integer corte);

    @Query(value = """
			SELECT CABECERA_DISTRITAL_FEDERAL
			FROM GEOGRAFICOINE.DISTRITOS_FEDERALES
			WHERE ID_ESTADO = :idEstado
			   AND ID_DISTRITO_FEDERAL = :idDistrito
			   AND ID_CORTE =:corte""", nativeQuery = true)
    public String getNombreDistrito(@Param("idEstado") Integer idEstado, @Param("idDistrito") Integer idDistrito,
            @Param("corte") Integer corte);

    @Query(nativeQuery = true)
    public Integer getParticipacion(@Param("idProceso") Integer idProceso, @Param("idDetalle") Integer idDetalle,
            @Param("idEstado") Integer idEstado, @Param("idDistrito") Integer idDistrito,
            @Param("ambito") String ambito);

    @Query(nativeQuery = true)
    public List<VOCombo> getMunicipios(@Param("idEstado") Integer idEstado, @Param("idDistrito") Integer idDistrito,
            @Param("idCorte") Integer idCorte);

    @Query(nativeQuery = true)
    public List<VOCombo> getLocalidadesPorMunicipio(@Param("idEstado") Integer idEstado,
            @Param("idMunicipio") Integer idMunicipio, @Param("idDistrito") Integer idDistrito,
            @Param("idCorte") Integer idCorte, @Param("idProceso") Integer idProceso,
            @Param("idDetalle") Integer idDetalle, @Param("idParticipacion") Integer idParticipacion);

    @Query(nativeQuery = true)
    public List<VOCombo> getSedes(@Param("idProceso") Integer idProceso, @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion);

    @Query(nativeQuery = true)
    public List<VOCombo> getSecciones(@Param("idProceso") Integer idProceso, @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion);

    public DTOParticipacionGeografica findById_IdDetalleProcesoAndId_IdParticipacion(Integer idDetalleProceso,
            Integer idParticipacion);
}
