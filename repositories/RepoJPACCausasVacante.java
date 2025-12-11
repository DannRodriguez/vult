package mx.ine.sustseycae.repositories;

import mx.ine.sustseycae.dto.db.DTOAspirante;
import mx.ine.sustseycae.dto.db.DTOCCausasVacante;
import mx.ine.sustseycae.dto.db.DTOCCausasVacanteId;
import mx.ine.sustseycae.dto.vo.VOCombo;
import mx.ine.sustseycae.dto.vo.VOComboPendientes;
import mx.ine.sustseycae.dto.vo.VOInfoSustituido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoJPACCausasVacante extends JpaRepository<DTOCCausasVacante, DTOCCausasVacanteId> {

    @Query(value = """
            WITH consulta AS
                    (SELECT ASP.ID_ASPIRANTE AS id
                    FROM SUPYCAP.ASPIRANTES ASP
                            JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON (ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                                    AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                                    AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                    WHERE SUST.ID_DETALLE_PROCESO= :idDetalle
                            AND SUST.ID_PARTICIPACION= :idParticipacion
                            AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
                            AND SUST.TIPO_CAUSA_VACANTE != 4
                            AND SUST.ID_PUESTO_SUSTITUIDO = 1
                            AND ASP.ID_PUESTO IN (3, 4, 6, 13)
                    UNION ALL
                    SELECT ASP.ID_ASPIRANTE AS id
                    FROM SUPYCAP.ASPIRANTES ASP
                            JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON (ASP.ID_DETALLE_PROCESO= SUST.ID_DETALLE_PROCESO
                                                                    AND ASP.ID_PARTICIPACION= SUST.ID_PARTICIPACION
                                                                    AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)

                    WHERE SUST.ID_DETALLE_PROCESO=:idDetalle
                            AND SUST.ID_PARTICIPACION=:idParticipacion
                            AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
                            AND SUST.TIPO_CAUSA_VACANTE != 4
                            AND SUST.ID_PUESTO_SUSTITUIDO = 2
                            AND ASP.ID_PUESTO IN (1, 3, 5, 9, 12, 13))
            SELECT DISTINCT(consulta.id)
            FROM consulta""", nativeQuery = true)
    public List<Integer> obtenerArrayPendientesSustSeCae(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion);

    @Query(value = """
            SELECT PUESTO
            FROM SUPYCAP.C_PUESTOS
            WHERE ID_PUESTO = :idPuesto""", nativeQuery = true)
    public String getNombreCargo(Integer idPuesto);

    @Query(nativeQuery = true)
    public List<DTOAspirante> getAspiranteInfo(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante);

    @Query(nativeQuery = true)
    public List<VOInfoSustituido> getDatosInfoSE(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante,
            @Param("etapa") Integer etapa,
            @Param("idZore") Integer idZore);

    @Query(nativeQuery = true)
    public List<VOInfoSustituido> getDatosInfoCAE(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaSupervisor(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("puestos") List<Integer> puestos,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaSupervisorCM(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("causasVacante") List<Integer> causasVacante,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaSupervisorTermContrato(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaCapacitador(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("puestos") List<Integer> puestos,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaCapacitadorCM(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("causasVacante") List<Integer> causasVacante,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenerListaCapacitadorTermContrato(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenListaCESustituidosRescision(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenListaSESustituidosRescision(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenListaCESustituidos(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOCombo> obtenListaSESustituidos(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("etapa") Integer etapa);

    @Query(nativeQuery = true)
    public List<VOComboPendientes> obtenerPendientesAdmin(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion);

    @Query(nativeQuery = true)
    public List<VOComboPendientes> obtenerPendientesSustSeCae(
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion);

    public DTOCCausasVacante findById_IdCausaVacanteAndIdTipoCausaVacante(Integer idCausaVacante,
            Integer tipoCausaVacante);
}
