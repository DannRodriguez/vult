package mx.ine.sustseycae.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOAspirantesId;
import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.dto.vo.VOObjectsListaReservaSe;
import mx.ine.sustseycae.models.responses.ModelResponseDatosAspirante;
import mx.ine.sustseycae.models.responses.ModelResponseSustitutos;

public interface RepoJPAAspirantes extends JpaRepository<DTOAspirantes, DTOAspirantesId> {

    public interface idAspiranteProjection {
        Integer getIdParticipacion();
        Integer getIdAspirante();
    }

    @Query(nativeQuery = true)
    List<ModelResponseDatosAspirante> obtenerDatosAspirante(
            @Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante);

    @Query(nativeQuery = true)
    public VOAspiranteBitacora getAspiranteBitacora(
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante);

    @Query(nativeQuery = true)
    List<ModelResponseSustitutos> obtenerListaSustitutosSupervisores(
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion);

    @Query(nativeQuery = true)
    List<ModelResponseSustitutos> obtenerListaSustitutosCapacitadores(
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion);

    @Query(nativeQuery = true)
    List<VOObjectsListaReservaSe> obtenerListaReservaSe(
            @Param("idProceso") Integer idProceso,
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idPuesto") Integer idPuesto,
            @Param("estatus") ArrayList<Integer> estatus,
            @Param("filtro") Integer filtro,
            @Param("idMunicipio") Integer idMunicipio,
            @Param("idLocalidad") Integer idLocalidad,
            @Param("idSede") Integer idSede,
            @Param("seccion1") Integer seccion1,
            @Param("seccion2") Integer seccion2);

    @Query(nativeQuery = true)
    List<VOListaReservaCAE> obtenerListaReservaCAE(
            @Param("idProceso") Integer idProceso,
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idPuesto") Integer idPuesto,
            @Param("estatus") Integer estatus,
            @Param("filtro") Integer filtro,
            @Param("idMunicipio") Integer idMunicipio,
            @Param("idLocalidad") Integer idLocalidad,
            @Param("idSede") Integer idSede,
            @Param("seccion1") Integer seccion1,
            @Param("seccion2") Integer seccion2);

    @Modifying
    @Query(value = """
                    UPDATE SUPYCAP.ASPIRANTES
                    SET URL_FOTO = :urlFoto
                    WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral
                        AND ID_DETALLE_PROCESO = :idDetalleProceso
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
    public void updateURLFotoAspirante(
            @Param("urlFoto") String urlFoto,
            @Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante);

    @Modifying
    @Query(value = """
                    UPDATE SUPYCAP.ASPIRANTES
                    SET ESTATUS_LISTA_RESERVA = :estatusListaReserva,
                        OBS_DECLINACION_LISTA_RESERVA = :observaciones
                    WHERE ID_PROCESO_ELECTORAL = :idProceso
                        AND ID_DETALLE_PROCESO = :idDetalle
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
    public void updateListaReservaCAEAspirantes(
            @Param("idProceso") Integer idProceso,
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante,
            @Param("estatusListaReserva") Integer estatusListaReserva,
            @Param("observaciones") String observaciones);

    @Modifying
    @Query(value = """
                    UPDATE SUPYCAP.EVALUACION_CURRICULAR
                    SET REPRESENTANTE_PARTIDO_1 = :representantePP,
                        MILITANTE_PARTIDO_1 = :militantePP
                    WHERE ID_PROCESO_ELECTORAL = :idProceso
                        AND ID_DETALLE_PROCESO = :idDetalle
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
    public void updateListaReservaCAEEvaCurricular(
            @Param("idProceso") Integer idProceso,
            @Param("idDetalle") Integer idDetalle,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante,
            @Param("representantePP") String representantePP,
            @Param("militantePP") String militantePP);

    @Modifying
    @Query(value = """
                    UPDATE SUPYCAP.ASPIRANTES
                    SET ID_PUESTO = :idPuesto,
                        DECLINO_CARGO = :declinoCargo
                    WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral
                        AND ID_DETALLE_PROCESO = :idDetalleProceso
                        AND ID_PARTICIPACION = :idParticipacion
                        AND ID_ASPIRANTE = :idAspirante""", nativeQuery = true)
    public void updateIdPuestoDeclinoCargoAspirante(
            @Param("idProcesoElectoral") Integer idProcesoElectoral,
            @Param("idDetalleProceso") Integer idDetalleProceso,
            @Param("idParticipacion") Integer idParticipacion,
            @Param("idAspirante") Integer idAspirante,
            @Param("idPuesto") Integer idPuesto,
            @Param("declinoCargo") Integer declinoCargo);

    public DTOAspirantes findById_IdProcesoElectoralAndId_IdDetalleProcesoAndId_IdParticipacionAndId_IdAspirante(
            Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion, Integer idApirante);

}