package mx.ine.sustseycae.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentasId;

public interface RepoJPACreacionCuentas extends JpaRepository<DTOCreacionCuentas, DTOCreacionCuentasId> {

    @Query(value = """
                        SELECT NUMERO_AREA_RESPONSABILIDAD
                        FROM SUPYCAP.AREAS_RESPONSABILIDAD
                        WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral
                        AND ID_DETALLE_PROCESO = :idDetalleProceso
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_AREA_RESPONSABILIDAD = :idAre""", nativeQuery = true)
    public Integer getNumeroAre(@Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion, @Param("idAre") Integer idAre);

    @Query(value = """
                        SELECT NUMERO_ZONA_RESPONSABILIDAD
                        FROM SUPYCAP.ZONAS_RESPONSABILIDAD
                        WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral
                        AND ID_DETALLE_PROCESO =:idDetalleProceso
                        AND ID_PARTICIPACION = :idParticipacion
                        AND id_zona_responsabilidad = :idZore""", nativeQuery = true)
    public Integer getNumeroZore(@Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion, @Param("idZore") Integer idZore);

    @Modifying
    @Query(value = """
                        UPDATE SUPYCAP.ASPIRANTES
                        SET UID_CUENTA = :uidCuenta,
                        SERVICIO_USADO_CTA = :servicio,
                        USUARIO = :usuario,
                        IP_USUARIO =:ipUsuario,
                        FECHA_HORA =:fecha,
                        ID_SISTEMA_ACTUALIZA =:idSistema
                        WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral
                        AND ID_DETALLE_PROCESO = :idDetalleProceso
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
    public void updateUidCuentaAndServicioUsadoAspirante(@Param("uidCuenta") String uidCuenta,
            @Param("servicio") Integer servicio, @Param("usuario") String usuario,
            @Param("ipUsuario") String ipUsuario, @Param("fecha") Date fecha,
            @Param("idSistema") Integer idSistema, @Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion, @Param("idAspirante") Integer idAspirante);

    public DTOCreacionCuentas findByIdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
            Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idApirante);

}
