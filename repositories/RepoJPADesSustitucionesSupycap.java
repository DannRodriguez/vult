package mx.ine.sustseycae.repositories;

import mx.ine.sustseycae.dto.db.DTODesSustitucionesSupycap;
import mx.ine.sustseycae.dto.db.DTODesSustitucionesSupycapId;
import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RepoJPADesSustitucionesSupycap
                extends JpaRepository<DTODesSustitucionesSupycap, DTODesSustitucionesSupycapId> {

        @Query(value = """
                        SELECT MAX(ID_DESHACER) + 1
                        FROM SUPYCAP.DES_SUSTITUCIONES_SUPYCAP
                        WHERE ID_DETALLE_PROCESO = :idDetalle
                            AND ID_PARTICIPACION = :idParticipacion""", nativeQuery = true)
        public Optional<Integer> encontrarMaxId(
                        @Param("idDetalle") Integer idDetalle,
                        @Param("idParticipacion") Integer idParticipacion);

        @Query(nativeQuery = true)
        public List<VOConsultaDesSustitucionesSupycap> consultaDesSustituciones(
                        @Param("idProcesoElectoral") Integer idProcesoElectoral,
                        @Param("idDetalleProceso") Integer idDetalleProceso,
                        @Param("idParticipacion") Integer idParticipacion);

        @Query(nativeQuery = true)
        public List<VOConsultaDesSustitucionesSupycap> consultaSustitucionesDeshechas(
                        @Param("idProcesoElectoral") Integer idProcesoElectoral,
                        @Param("idDetalleProceso") Integer idDetalleProceso,
                        @Param("idParticipacion") Integer idParticipacion);

        @Query(nativeQuery = true)
        public List<VOConsultaDesSustitucionesSupycap> consultaDesSustitucionesRelacionadas(
                        @Param("idProcesoElectoral") Integer idProcesoElectoral,
                        @Param("idDetalleProceso") Integer idDetalleProceso,
                        @Param("idParticipacion") Integer idParticipacion,
                        @Param("idRelacionSustituciones") String idRelacionSustituciones);

        @Modifying
        @Transactional
        @Query(nativeQuery = true)
        public void insertarDeshacerSustitucion(
                        @Param("idDetalleProceso") Integer idDetalleProceso,
                        @Param("idParticipacion") Integer idParticipacion,
                        @Param("idSustitucion") Integer idSustitucion,
                        @Param("etapa") Integer etapa,
                        @Param("ipUsuario") String ipUsuario,
                        @Param("usuario") String usuario);

}
