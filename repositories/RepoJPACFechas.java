package mx.ine.sustseycae.repositories;

import mx.ine.sustseycae.dto.db.DTOCFechas;
import mx.ine.sustseycae.dto.db.DTOCFechasId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepoJPACFechas extends JpaRepository<DTOCFechas, DTOCFechasId> {

	@Query(value = """
			SELECT CASE WHEN TRUNC(SYSDATE) < fecha THEN 1 -- Si la fecha es antes de hoy
			            WHEN TRUNC(SYSDATE) >= fecha THEN 2 -- Si hoy es igual o mayor a la fecha
			            END AS resultado
			FROM SUPYCAP.C_FECHAS
			WHERE ID_FECHA = :idFecha
			      AND ID_DETALLE_PROCESO = :idDetalleProceso""", nativeQuery = true)
	public Integer obtenerFecha(
			@Param("idFecha") Integer idFecha,
			@Param("idDetalleProceso") Integer idDetalleProceso);

	@Query(value = """
			SELECT TO_CHAR(fecha, 'DD-MM-YYYY') AS fecha
			FROM SUPYCAP.C_FECHAS
			WHERE ID_PROCESO_ELECTORAL = :idProceso
			    AND ID_DETALLE_PROCESO = :idDetalle
			    AND ID_FECHA = :idFecha""", nativeQuery = true)
	public String obtenerFechaSustitucion(
			@Param("idProceso") Integer idProceso,
			@Param("idDetalle") Integer idDetalle,
			@Param("idFecha") Integer idFecha);

	public DTOCFechas findById_IdFechaAndId_IdDetalleProcesoAndId_IdEstadoAndId_IdDistrito(Integer idFecha,
			Integer idDetalleProceso, Integer idEstado, Integer idDistrito);

	public DTOCFechas findById_IdFechaAndId_IdDetalleProceso(Integer idFecha, Integer idDetalleProceso);

	public DTOCFechas findById_idProcesoElectoralAndId_IdDetalleProcesoAndId_IdFecha(Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idFecha);

}
