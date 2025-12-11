package mx.ine.sustseycae.repositories;

import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTOSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepoJPASustitucionesSupycap extends JpaRepository<DTOSustitucionesSupycap, DTOSustitucionesSupycapId> {

        @Query(value = """
                        SELECT MAX(ID_SUSTITUCION) + 1
                        FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
                        WHERE ID_DETALLE_PROCESO = :idDetalle
                            AND ID_PARTICIPACION = :idParticipacion""", nativeQuery = true)
        public Optional<Integer> encontrarMaxId(
                        @Param("idDetalle") Integer idDetalle,
                        @Param("idParticipacion") Integer idParticipacion);

        @Query(value = """
                        SELECT COUNT(ID_SUSTITUCION) AS TOTAL
                        FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
                        WHERE ID_PROCESO_ELECTORAL = :idProceso
                            AND ID_DETALLE_PROCESO = :idDetalle
                            AND ID_PARTICIPACION = :idParticipacion
                            AND ID_RELACION_SUSTITUCIONES = :idRelacion""", nativeQuery = true)
        public Integer countSustitucionesRelacion(@Param("idProceso") Integer idProceso,
                        @Param("idDetalle") Integer idDetalle,
                        @Param("idParticipacion") Integer idParticipacion,
                        @Param("idRelacion") String idRelacion);

        @Query(nativeQuery = true)
        public VOSustitucionesSupycap obtenerInfoSustitucionById(
                        @Param("idProceso") Integer idProceso,
                        @Param("idDetalle") Integer idDetalle,
                        @Param("idParticipacion") Integer idParticipacion,
                        @Param("idSustitucion") Integer idSustitucion);

        @Query(nativeQuery = true)
        public List<VOSustitucionesSupycap> obtenerInfoSustitucionPorFiltro(
                        @Param("idProceso") Integer idProceso,
                        @Param("idDetalle") Integer idDetalle,
                        @Param("idParticipacion") Integer idParticipacion,
                        @Param("idAspiranteSustituido") Integer idAspiranteSustituido,
                        @Param("idAspiranteSustituto") Integer idAspiranteSustituto,
                        @Param("idRelacion") String idRelacion);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndTipoCausaVacante(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSutituido,
                        Integer tipoCausaVacante);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucionAndIdAspiranteSutituidoAndIdPuestoSustituido(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idSustitucion,
                        Integer idAspiranteSutituido,
                        Integer idPuestoSustituido);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndIdPuestoSustituido(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSutituido,
                        Integer idPuestoSustituido);

        public List<DTOSustitucionesSupycap> findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoOrderById_IdSustitucionDesc(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSutituido);

        public List<DTOSustitucionesSupycap> findById_IdDetalleProcesoAndId_IdParticipacionAndIdRelacionSustituciones(
                        Integer idDetalleProceso, Integer idParticipacion, String idRelacionSustituciones);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndIdRelacionSustituciones(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSustituido,
                        String idRelacionSustituciones);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndIdAspiranteSutituidoAndTipoCausaVacanteAndIdCausaVacante(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idAspiranteSutituido,
                        Integer tipoCausaVacante,
                        Integer idCausaVacante);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucion(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idSustitucion);

        public DTOSustitucionesSupycap findById_IdDetalleProcesoAndId_IdParticipacionAndId_IdSustitucionAndIdRelacionSustituciones(
                        Integer idDetalleProceso, Integer idParticipacion, Integer idSustitucion,
                        String idRelacionSustituciones);

}
