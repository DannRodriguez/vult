package mx.ine.sustseycae.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOListaReservaCAE;
import mx.ine.sustseycae.dto.vo.VOObjectsListaReservaSe;
import mx.ine.sustseycae.models.responses.ModelResponseDatosAspirante;
import mx.ine.sustseycae.models.responses.ModelResponseSustitutos;

@SqlResultSetMapping(name = "sustitutos", classes = {
    @ConstructorResult(targetClass = ModelResponseSustitutos.class, columns = {
        @ColumnResult(name = "idDetalleProceso", type = Integer.class),
        @ColumnResult(name = "idParticipacion", type = Integer.class),
        @ColumnResult(name = "idAspirante", type = Integer.class),
        @ColumnResult(name = "folio", type = Integer.class),
        @ColumnResult(name = "title", type = String.class),
        @ColumnResult(name = "value", type = Integer.class),
        @ColumnResult(name = "key", type = Integer.class),
        @ColumnResult(name = "categoria", type = Integer.class),})})

@SqlResultSetMapping(name = "datosAspirante", classes = {
    @ConstructorResult(targetClass = ModelResponseDatosAspirante.class, columns = {
        @ColumnResult(name = "folioAspirante", type = Integer.class),
        @ColumnResult(name = "nombreAspirante", type = String.class),
        @ColumnResult(name = "claveElectorFuar", type = String.class),
        @ColumnResult(name = "idPuesto", type = Integer.class),
        @ColumnResult(name = "cargoActual", type = String.class),
        @ColumnResult(name = "idZore", type = Integer.class),
        @ColumnResult(name = "numZore", type = Integer.class),
        @ColumnResult(name = "idAre", type = Integer.class),
        @ColumnResult(name = "numAre", type = Integer.class),
        @ColumnResult(name = "ares", type = String.class),
        @ColumnResult(name = "urlFoto", type = String.class),
        @ColumnResult(name = "correoElectronico", type = String.class),})})

@SqlResultSetMapping(name = "VOAspiranteBitacoraMapping", classes = {
    @ConstructorResult(targetClass = VOAspiranteBitacora.class, columns = {
        @ColumnResult(name = "idProcesoElectoral", type = Integer.class),
        @ColumnResult(name = "idDetalleProceso", type = Integer.class),
        @ColumnResult(name = "idParticipacion", type = Integer.class),
        @ColumnResult(name = "idAspirante", type = Integer.class),
        @ColumnResult(name = "folio", type = Integer.class),
        @ColumnResult(name = "nombreCompleto", type = String.class),
        @ColumnResult(name = "claveElector", type = String.class),
        @ColumnResult(name = "idPuesto", type = Integer.class),
        @ColumnResult(name = "puesto", type = String.class),
        @ColumnResult(name = "evaluacionIntegralSE", type = String.class),
        @ColumnResult(name = "evaluacionIntegralCAE", type = String.class),
        @ColumnResult(name = "presentaEntrevistaSE", type = String.class),
        @ColumnResult(name = "declinoEntrevistaSE", type = String.class),
        @ColumnResult(name = "declinoEntrevistaCAE", type = String.class),
        @ColumnResult(name = "participoProceso", type = String.class),
        @ColumnResult(name = "cualProceso", type = String.class),
        @ColumnResult(name = "participoSE", type = String.class),
        @ColumnResult(name = "participoCAE", type = String.class),
        @ColumnResult(name = "participoOtro", type = String.class),
        @ColumnResult(name = "idBitacoraDesempenio", type = Integer.class),
        @ColumnResult(name = "devolvioPrendas", type = Integer.class),
        @ColumnResult(name = "fechaSustAlta", type = String.class),})})

@SqlResultSetMapping(name = "listaReservaSe", classes = {
    @ConstructorResult(targetClass = VOObjectsListaReservaSe.class, columns = {
        @ColumnResult(name = "idObjeto", type = Integer.class),
        @ColumnResult(name = "idDetalle", type = Integer.class),
        @ColumnResult(name = "idParticipacion", type = Integer.class),
        @ColumnResult(name = "sedeReclutamiento", type = Integer.class),
        @ColumnResult(name = "numSede", type = Integer.class),
        @ColumnResult(name = "lugarSede", type = String.class),
        @ColumnResult(name = "idAspirante", type = Integer.class),
        @ColumnResult(name = "nombreCae", type = String.class),
        @ColumnResult(name = "puesto", type = Integer.class),
        @ColumnResult(name = "evaluacionIntSe", type = String.class),
        @ColumnResult(name = "evaluacionIntSeAux", type = String.class),
        @ColumnResult(name = "evaluacionIntCae", type = String.class),
        @ColumnResult(name = "evaluacionIntCaeAux", type = String.class),
        @ColumnResult(name = "domicilio", type = String.class),
        @ColumnResult(name = "telefono", type = String.class),
        @ColumnResult(name = "celular", type = String.class),
        @ColumnResult(name = "correo", type = String.class),
        @ColumnResult(name = "escolaridad", type = String.class),
        @ColumnResult(name = "carrera", type = String.class),
        @ColumnResult(name = "declinaEntrevistaSe", type = String.class),
        @ColumnResult(name = "estatusListaReserva", type = Integer.class),
        @ColumnResult(name = "imparteCapacit", type = String.class),
        @ColumnResult(name = "experienciaGrupos", type = String.class),
        @ColumnResult(name = "disponibleTiempo", type = String.class),
        @ColumnResult(name = "tieneEvaluacion", type = Integer.class),
        @ColumnResult(name = "declinaEntSe", type = Integer.class),
        @ColumnResult(name = "participoProceso", type = String.class),
        @ColumnResult(name = "numCriterioOrden", type = Integer.class),
        @ColumnResult(name = "cellColorBackground", type = String.class),
        @ColumnResult(name = "estatusLista", type = Integer.class),
        @ColumnResult(name = "sede", type = String.class)})})

@SqlResultSetMapping(name = "listaReservaCAE", classes = {
    @ConstructorResult(targetClass = VOListaReservaCAE.class, columns = {
        @ColumnResult(name = "num", type = Integer.class),
        @ColumnResult(name = "idProceso", type = Integer.class),
        @ColumnResult(name = "idDetalle", type = Integer.class),
        @ColumnResult(name = "idParticipacion", type = Integer.class),
        @ColumnResult(name = "idSedeReclutamiento", type = Integer.class),
        @ColumnResult(name = "numSede", type = Integer.class),
        @ColumnResult(name = "lugarSede", type = String.class),
        @ColumnResult(name = "idAspirante", type = Integer.class),
        @ColumnResult(name = "nombreAspirante", type = String.class),
        @ColumnResult(name = "puesto", type = Integer.class),
        @ColumnResult(name = "evaluacionInteCAE", type = String.class),
        @ColumnResult(name = "representantePP", type = String.class),
        @ColumnResult(name = "militantePP", type = String.class),
        @ColumnResult(name = "estatusListaReserva", type = Integer.class),
        @ColumnResult(name = "observaciones", type = String.class),})})

@NamedNativeQuery(name = "DTOAspirantes.obtenerListaSustitutosSupervisores", query = """
		WITH CTE AS (
			SELECT
			        idDetalleProceso,
			        idParticipacion,
			        idAspirante,
			        folio,
			        title,
			        value,
			        key,
			        categoria,
			        ROW_NUMBER() OVER (
			            PARTITION BY
			                idDetalleProceso, idParticipacion, idAspirante, folio, title, value, key
			            ORDER BY
			                CATEGORIA ASC
			        ) AS rn,
			        evaluacionIntegralSe
			    FROM
			(
			  (SELECT
			  ASP.ID_DETALLE_PROCESO idDetalleProceso,
			  ASP.ID_PARTICIPACION idParticipacion,
			  ASP.ID_ASPIRANTE idAspirante,
			  ASP.FOLIO folio,
			  ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			  ASP.ID_ASPIRANTE AS value,
			  ASP.ID_ASPIRANTE AS key,
			  '1' AS categoria,
			  EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			  FROM ASPIRANTES ASP
			  JOIN EVALUACION_INTEGRAL EI
			  ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			  AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			  AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			  WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			  AND ASP.ID_PARTICIPACION=:idParticipacion
			  AND ASP.ID_PUESTO = 5
			  AND EI.EVALUACION_INTEGRAL_SE IS NOT NULL
			  AND EI.DECLINO_ENTREVISTA_SE = 'N'
			  AND ASP.ESTATUS_LISTA_RESERVA = 0)
			  UNION
			  (SELECT
			    A2.idDetalleProceso,
			    A2.idParticipacion,
			    A2.idAspirante,
			    A2.folio,
			    A2.title,
			    A2.value,
			    A2.key,
			    A2.categoria,
			    A2.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '2' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ID_PUESTO = 2
			        AND EI.EVALUACION_INTEGRAL_SE IS NOT NULL
			        AND EI.DECLINO_ENTREVISTA_SE = 'N'
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A2
			    )
			  UNION
			  (SELECT
			    A3.idDetalleProceso,
			    A3.idParticipacion,
			    A3.idAspirante,
			    A3.folio,
			    A3.title,
			    A3.value,
			    A3.key,
			    A3.categoria,
			    A3.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '3' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ID_PUESTO = 2
			        AND EI.EVALUACION_INTEGRAL_SE IS NULL
			        AND EI.DECLINO_ENTREVISTA_SE = 'N'
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A3
			    )
			   UNION
			   (SELECT
			    A4.idDetalleProceso,
			    A4.idParticipacion,
			    A4.idAspirante,
			    A4.folio,
			    A4.title,
			    A4.value,
			    A4.key,
			    A4.categoria,
			    A4.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '4' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ID_PUESTO = 2
			        AND EI.DECLINO_ENTREVISTA_SE = 'S'
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A4
			    )
			    UNION
			    (SELECT
			    A5.idDetalleProceso,
			    A5.idParticipacion,
			    A5.idAspirante,
			    A5.folio,
			    A5.title,
			    A5.value,
			    A5.key,
			    A5.categoria,
			    A5.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '5' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND EI.DECLINO_ENTREVISTA_SE = 'N'
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        AND ASP.ID_PUESTO = 6
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A5
			    )
			    UNION
			    (SELECT
			    A6.idDetalleProceso,
			    A6.idParticipacion,
			    A6.idAspirante,
			    A6.folio,
			    A6.title,
			    A6.value,
			    A6.key,
			    A6.categoria,
			    A6.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '6' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        AND ASP.DECLINO_CARGO = 1
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A6
			    )
			    UNION
			    (SELECT
			    A7.idDetalleProceso,
			    A7.idParticipacion,
			    A7.idAspirante,
			    A7.folio,
			    A7.title,
			    A7.value,
			    A7.key,
			    A7.categoria,
			    A7.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '7' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ID_PUESTO = 3
			        AND EI.DECLINO_ENTREVISTA_SE = 'N'
			        AND EI.EVALUACION_INTEGRAL_SE IS NOT NULL
			        AND ASP.DECLINO_CARGO IS NULL
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        AND EI.DECLINO_ENTREVISTA_CAE = 'N'
			        ORDER BY EI.EVALUACION_INTEGRAL_SE DESC
			      ) A7
			    )
			    UNION
			    (SELECT
			    A8.idDetalleProceso,
			    A8.idParticipacion,
			    A8.idAspirante,
			    A8.folio,
			    A8.title,
			    A8.value,
			    A8.key,
			    A8.categoria,
			    A8.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '8' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        AND ASP.ID_PUESTO = 4
			      ) A8
			    )
			    UNION
			    (SELECT
			    A9.idDetalleProceso,
			    A9.idParticipacion,
			    A9.idAspirante,
			    A9.folio,
			    A9.title,
			    A9.value,
			    A9.key,
			    A9.categoria,
			    A9.evaluacionIntegralSe
			  FROM
			    (   SELECT
			        ASP.ID_DETALLE_PROCESO idDetalleProceso,
			        ASP.ID_PARTICIPACION idParticipacion,
			        ASP.ID_ASPIRANTE idAspirante,
			        ASP.FOLIO folio,
			        ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			        ASP.ID_ASPIRANTE AS value,
			        ASP.ID_ASPIRANTE AS key,
			        '9' AS categoria,
			        EI.EVALUACION_INTEGRAL_SE evaluacionIntegralSe
			        FROM ASPIRANTES ASP
			        JOIN EVALUACION_INTEGRAL EI
			        ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			        AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			        AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			        WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			        AND ASP.ID_PARTICIPACION=:idParticipacion
			        AND ASP.ESTATUS_LISTA_RESERVA = 0
			        AND ASP.ID_PUESTO = 12
			      ) A9
			    )
			  )
			)
			SELECT
			    idDetalleProceso,
			    idParticipacion,
			    idAspirante,
			    folio,
			    title,
			    value,
			    key,
			    categoria
			FROM
			    CTE
			WHERE
			    rn = 1
			ORDER BY categoria, evaluacionIntegralSe DESC""", resultSetMapping = "sustitutos")

@NamedNativeQuery(name = "DTOAspirantes.obtenerListaSustitutosCapacitadores", query = """
		WITH CTE AS (
			      SELECT
			              idDetalleProceso,
			              idParticipacion,
			              idAspirante,
			              folio,
			              title,
			              value,
			              key,
			              categoria,
			              ROW_NUMBER() OVER (
			                  PARTITION BY
			                      idDetalleProceso, idParticipacion, idAspirante, folio, title, value, key
			                  ORDER BY
			                      CATEGORIA ASC
			              ) AS rn,
			              evaluacionIntegralCae
			          FROM
			      (
			        (
			        SELECT
			          A1.idDetalleProceso,
			          A1.idParticipacion,
			          A1.idAspirante,
			          A1.folio,
			          A1.title,
			          A1.value,
			          A1.key,
			          A1.categoria,
			          A1.evaluacionIntegralCae
			          FROM
			            (SELECT
			            ASP.ID_DETALLE_PROCESO idDetalleProceso,
			            ASP.ID_PARTICIPACION idParticipacion,
			            ASP.ID_ASPIRANTE idAspirante,
			            ASP.FOLIO folio,
			            ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			            ASP.ID_ASPIRANTE AS value,
			            ASP.ID_ASPIRANTE AS key,
			            1 AS categoria,
			            EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			            FROM ASPIRANTES ASP
			            JOIN EVALUACION_INTEGRAL EI
			            ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			            AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			            AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			            WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			            AND ASP.ID_PARTICIPACION=:idParticipacion
			            AND ASP.ID_PUESTO = 3
			            AND EI.EVALUACION_INTEGRAL_CAE > 9
			            AND ASP.DECLINO_CARGO IS NULL
			            AND ASP.ESTATUS_LISTA_RESERVA = 0
			            AND EI.DECLINO_ENTREVISTA_CAE = 'N'
			            ORDER BY EI.EVALUACION_INTEGRAL_CAE DESC) A1
			        )
			        UNION
			        (SELECT
			          A2.idDetalleProceso,
			          A2.idParticipacion,
			          A2.idAspirante,
			          A2.folio,
			          A2.title,
			          A2.value,
			          A2.key,
			          A2.categoria,
			          A2.evaluacionIntegralCae
			          FROM
			            (SELECT
			            ASP.ID_DETALLE_PROCESO idDetalleProceso,
			            ASP.ID_PARTICIPACION idParticipacion,
			            ASP.ID_ASPIRANTE idAspirante,
			            ASP.FOLIO folio,
			            ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			            ASP.ID_ASPIRANTE AS value,
			            ASP.ID_ASPIRANTE AS key,
			            2 AS categoria,
			            EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			            FROM ASPIRANTES ASP
			            JOIN EVALUACION_INTEGRAL EI
			            ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			            AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			            AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			            WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			            AND ASP.ID_PARTICIPACION=:idParticipacion
			            AND ASP.ID_PUESTO = 3
			            AND (EI.EVALUACION_INTEGRAL_CAE > 8 AND EI.EVALUACION_INTEGRAL_CAE <= 9)
			            AND ASP.DECLINO_CARGO IS NULL
			            AND ASP.ESTATUS_LISTA_RESERVA = 0
			            AND EI.DECLINO_ENTREVISTA_CAE = 'N'
			            ORDER BY EI.EVALUACION_INTEGRAL_CAE DESC) A2
			        )
			        UNION
			        (SELECT
			          A3.idDetalleProceso,
			          A3.idParticipacion,
			          A3.idAspirante,
			          A3.folio,
			          A3.title,
			          A3.value,
			          A3.key,
			          A3.categoria,
			          A3.evaluacionIntegralCae
			          FROM
			            (SELECT
			            ASP.ID_DETALLE_PROCESO idDetalleProceso,
			            ASP.ID_PARTICIPACION idParticipacion,
			            ASP.ID_ASPIRANTE idAspirante,
			            ASP.FOLIO folio,
			            ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			            ASP.ID_ASPIRANTE AS value,
			            ASP.ID_ASPIRANTE AS key,
			            3 AS categoria,
			            EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			            FROM ASPIRANTES ASP
			            JOIN EVALUACION_INTEGRAL EI
			            ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			            AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			            AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			            WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			            AND ASP.ID_PARTICIPACION=:idParticipacion
			            AND ASP.ID_PUESTO = 3
			            AND (EI.EVALUACION_INTEGRAL_CAE >= 6 AND EI.EVALUACION_INTEGRAL_CAE <= 8)
			            AND ASP.DECLINO_CARGO IS NULL
			            AND ASP.ESTATUS_LISTA_RESERVA = 0
			            AND EI.DECLINO_ENTREVISTA_CAE = 'N'
			            ORDER BY EI.EVALUACION_INTEGRAL_CAE DESC) A3
			            )
			        UNION
			        (SELECT
			          A4.idDetalleProceso,
			          A4.idParticipacion,
			          A4.idAspirante,
			          A4.folio,
			          A4.title,
			          A4.value,
			          A4.key,
			          A4.categoria,
			          A4.evaluacionIntegralCae
			          FROM
			            (SELECT
			            ASP.ID_DETALLE_PROCESO idDetalleProceso,
			            ASP.ID_PARTICIPACION idParticipacion,
			            ASP.ID_ASPIRANTE idAspirante,
			            ASP.FOLIO folio,
			            ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			            ASP.ID_ASPIRANTE AS value,
			            ASP.ID_ASPIRANTE AS key,
			            4 AS categoria,
			            EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			            FROM ASPIRANTES ASP
			            JOIN EVALUACION_INTEGRAL EI
			            ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			            AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			            AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			            WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			            AND ASP.ID_PARTICIPACION=:idParticipacion
			            AND ASP.ID_PUESTO = 3
			            AND (EI.EVALUACION_INTEGRAL_CAE < 6)
			            AND ASP.DECLINO_CARGO IS NULL
			            AND ASP.ESTATUS_LISTA_RESERVA = 0
			            AND EI.DECLINO_ENTREVISTA_CAE = 'N'
			            ORDER BY EI.EVALUACION_INTEGRAL_CAE DESC) A4
			        )
			        UNION
			        (SELECT
			          A5.idDetalleProceso,
			          A5.idParticipacion,
			          A5.idAspirante,
			          A5.folio,
			          A5.title,
			          A5.value,
			          A5.key,
			          A5.categoria,
			          A5.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              5 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.ID_PUESTO = 9
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A5
			          )
			        UNION
			        (SELECT
			          A6.idDetalleProceso,
			          A6.idParticipacion,
			          A6.idAspirante,
			          A6.folio,
			          A6.title,
			          A6.value,
			          A6.key,
			          A6.categoria,
			          A6.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              6 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.DECLINO_CARGO = 2
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A6
			          )
			         UNION
			         (SELECT
			          A7.idDetalleProceso,
			          A7.idParticipacion,
			          A7.idAspirante,
			          A7.folio,
			          A7.title,
			          A7.value,
			          A7.key,
			          A7.categoria,
			          A7.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              7 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.ID_PUESTO = 8
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A7
			          )
			          UNION
			          (SELECT
			          A8.idDetalleProceso,
			          A8.idParticipacion,
			          A8.idAspirante,
			          A8.folio,
			          A8.title,
			          A8.value,
			          A8.key,
			          A8.categoria,
			          A8.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              8 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.ID_PUESTO = 6
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A8
			          )
			          UNION
			          (SELECT
			          A9.idDetalleProceso,
			          A9.idParticipacion,
			          A9.idAspirante,
			          A9.folio,
			          A9.title,
			          A9.value,
			          A9.key,
			          A9.categoria,
			          A9.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              9 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.DECLINO_CARGO = 1
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A9
			          )
			          UNION
			          (SELECT
			          A10.idDetalleProceso,
			          A10.idParticipacion,
			          A10.idAspirante,
			          A10.folio,
			          A10.title,
			          A10.value,
			          A10.key,
			          A10.categoria,
			          A10.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              10 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.ID_PUESTO = 12
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A10
			          )
			          UNION
			          (SELECT
			          A11.idDetalleProceso,
			          A11.idParticipacion,
			          A11.idAspirante,
			          A11.folio,
			          A11.title,
			          A11.value,
			          A11.key,
			          A11.categoria,
			          A11.evaluacionIntegralCae
			        FROM
			          (   SELECT
			              ASP.ID_DETALLE_PROCESO idDetalleProceso,
			              ASP.ID_PARTICIPACION idParticipacion,
			              ASP.ID_ASPIRANTE idAspirante,
			              ASP.FOLIO folio,
			              ASP.FOLIO || ' - ' || NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO, '') || ' ' || NVL(ASP.NOMBRE,'') AS title,
			              ASP.ID_ASPIRANTE AS value,
			              ASP.ID_ASPIRANTE AS key,
			              11 AS categoria,
			              EI.EVALUACION_INTEGRAL_CAE evaluacionIntegralCae
			              FROM ASPIRANTES ASP
			              JOIN EVALUACION_INTEGRAL EI
			              ON EI.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			              AND EI.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			              AND EI.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			              WHERE ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			              AND ASP.ID_PARTICIPACION=:idParticipacion
			              AND ASP.ID_PUESTO = 4
			              AND ASP.ESTATUS_LISTA_RESERVA = 0
			            ) A11
			          )
			        )
			      )
			      SELECT
			          idDetalleProceso,
			          idParticipacion,
			          idAspirante,
			          folio,
			          title,
			          value,
			          key,
			          categoria
			      FROM
			          CTE
			      WHERE
			          rn = 1
			      ORDER BY categoria, evaluacionIntegralCae DESC""", resultSetMapping = "sustitutos")

@NamedNativeQuery(name = "DTOAspirantes.obtenerDatosAspirante", query = """
		SELECT
			ASP.FOLIO AS folioAspirante,
			NVL(ASP.APELLIDO_PATERNO,'') || ' ' || NVL(ASP.APELLIDO_MATERNO,'') || ' ' || NVL(ASP.NOMBRE,'') AS nombreAspirante,
			ASP.CLAVE_ELECTOR_FUAR claveElectorFuar,
			ASP.ID_PUESTO AS idPuesto,
			CP.PUESTO AS cargoActual,
			ZR.ID_ZONA_RESPONSABILIDAD AS idZore,
			ZR.NUMERO_ZONA_RESPONSABILIDAD AS numZore,
			AR.ID_AREA_RESPONSABILIDAD AS idAre,
			AR.NUMERO_AREA_RESPONSABILIDAD AS numARE,
			A1.numeroAreaResponsabilidad AS ares,
			ASP.URL_FOTO AS urlFoto,
			EC.CORREO_ELECTRONICO correoElectronico
			FROM ASPIRANTES ASP
			LEFT JOIN ZONAS_RESPONSABILIDAD ZR
			ON ASP.ID_DETALLE_PROCESO = ZR.ID_DETALLE_PROCESO
			AND ASP.ID_PARTICIPACION = ZR.ID_PARTICIPACION
			AND ASP.ID_ZONA_RESPONSABILIDAD_1E = ZR.ID_ZONA_RESPONSABILIDAD
			LEFT JOIN AREAS_RESPONSABILIDAD AR
			ON ASP.ID_DETALLE_PROCESO = AR.ID_DETALLE_PROCESO
			AND ASP.ID_PARTICIPACION = AR.ID_PARTICIPACION
			AND ASP.ID_AREA_RESPONSABILIDAD_1E = AR.ID_AREA_RESPONSABILIDAD
			LEFT JOIN C_PUESTOS CP
			ON ASP.ID_PUESTO = CP.ID_PUESTO
			LEFT JOIN EVALUACION_CURRICULAR EC
			ON EC.ID_PROCESO_ELECTORAL = ASP.ID_PROCESO_ELECTORAL
			AND EC.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
			AND EC.ID_PARTICIPACION = ASP.ID_PARTICIPACION
			AND EC.ID_ASPIRANTE = ASP.ID_ASPIRANTE
			LEFT JOIN
			  (SELECT
			    idDetalleProceso,
			    idParticipacion,
			    idZonaResponsabilidad,
			    LISTAGG(numeroAreaResponsabilidad, ', ') WITHIN GROUP (ORDER BY numeroAreaResponsabilidad) AS numeroAreaResponsabilidad
			    FROM
			    (SELECT
			    AR.ID_DETALLE_PROCESO AS idDetalleProceso,
			    AR.ID_PARTICIPACION AS idParticipacion,
			    AR.ID_ZONA_RESPONSABILIDAD AS idZonaResponsabilidad,
			    AR.NUMERO_AREA_RESPONSABILIDAD AS numeroAreaResponsabilidad FROM AREAS_RESPONSABILIDAD AR
			    WHERE AR.ID_DETALLE_PROCESO=:idDetalleProceso
			    AND AR.ID_PARTICIPACION=:idParticipacion)
			    GROUP BY IDDETALLEPROCESO, IDPARTICIPACION, IDZONARESPONSABILIDAD) A1
			ON ASP.ID_DETALLE_PROCESO = A1.idDetalleProceso
			AND ASP.ID_PARTICIPACION = A1.idParticipacion
			AND ZR.ID_ZONA_RESPONSABILIDAD = A1.idZonaResponsabilidad
			WHERE ASP.ID_PROCESO_ELECTORAL=:idProcesoElectoral
			AND ASP.ID_DETALLE_PROCESO=:idDetalleProceso
			AND ASP.ID_PARTICIPACION=:idParticipacion
			AND ASP.ID_ASPIRANTE=:idAspirante""", resultSetMapping = "datosAspirante")

@NamedNativeQuery(name = "DTOAspirantes.getAspiranteBitacora", query = """
		select
			asp.id_proceso_electoral as idProcesoElectoral,
			asp.id_detalle_proceso as idDetalleProceso,
			asp.id_participacion as idParticipacion,
			asp.id_aspirante as idAspirante,
			asp.folio as folio,
			asp.nombre ||' '|| asp.apellido_paterno ||' '||asp.apellido_materno as nombreCompleto,
			asp.clave_elector_fuar as claveElector,
			asp.id_puesto as idPuesto,
			cp.puesto as puesto,
			to_char(evi.evaluacion_integral_se) as evaluacionIntegralSE,
			to_char(evi.evaluacion_integral_cae) as evaluacionIntegralCAE,
			evi.presenta_entrevista_se as presentaEntrevistaSE,
			evi.declino_entrevista_se as declinoEntrevistaSE,
			evi.declino_entrevista_cae as declinoEntrevistaCAE,
			case  when evc.participo_proceso ='N' THEN 'No'
			            when evc.participo_proceso ='S' THEN 'Si'
			            else 'No' end as participoProceso,
			evc.cual_proceso as cualProceso,
			case  when evc.participo_se ='N' THEN NULL
			            when evc.participo_se ='S' THEN 'SE'
			            else 'No' end as participoSE,
			case  when evc.participo_cae ='N' THEN NULL
			            when evc.participo_cae ='S' THEN 'CAE'
			            else 'No' end as participoCAE,
			evc.participo_otro_especifique as participoOtro,
			bit.id_bitacora_desempenio as idBitacoraDesempenio,
			case  when bit.devolvio_prendas ='N' THEN 0
			            when bit.devolvio_prendas ='S' THEN 1
			            else NULL end as devolvioPrendas,
			 to_char(sust.fecha_alta, 'DD-MM-YYYY') AS fechaSustAlta
			from aspirantes asp
			left join evaluacion_integral evi
			    on  asp.id_proceso_electoral = evi.id_proceso_electoral
			        and asp.id_detalle_proceso = evi.id_detalle_proceso
			        and asp.id_participacion = evi.id_participacion
			        and asp.id_aspirante = evi.id_aspirante
			left join evaluacion_curricular evc
			    on  asp.id_proceso_electoral = evc.id_proceso_electoral
			        and asp.id_detalle_proceso = evc.id_detalle_proceso
			        and asp.id_participacion = evc.id_participacion
			        and asp.id_aspirante = evc.id_aspirante
			left join sustituciones_supycap   sust ON asp.id_proceso_electoral = sust.id_proceso_electoral
			                                            AND asp.id_detalle_proceso = sust.id_detalle_proceso
			                                            AND asp.id_participacion = sust.id_participacion
			                                            AND asp.id_aspirante = sust.id_aspirante_sustituto
			                                            AND sust.id_sustitucion = (
			        SELECT
			            sustid.id_sustitucion
			        FROM
			            sustituciones_supycap sustid
			        WHERE
			            sustid.id_detalle_proceso = :idDetalleProceso
			            AND sustid.id_participacion = :idParticipacion
			            AND sustid.id_aspirante_sustituto = :idAspirante
			        ORDER BY
			            sustid.id_sustitucion DESC
			        FETCH FIRST 1 ROWS ONLY)
			left join c_puestos cp  on asp.id_puesto = cp.id_puesto
			left join bitacora_desempenio bit
			    on bit.id_detalle_proceso = asp.id_detalle_proceso
			        and bit.id_participacion = asp.id_participacion
			        and bit.id_aspirante = asp.id_aspirante
			where asp.id_detalle_proceso =:idDetalleProceso
			        and asp.id_participacion =:idParticipacion
			        and asp.id_aspirante =:idAspirante""", resultSetMapping = "VOAspiranteBitacoraMapping")

@NamedNativeQuery(name = "DTOAspirantes.obtenerListaReservaSe", query = """
		SELECT ROWNUM AS idObjeto,
		T2.idDetalle,
		T2.idParticipacion,
		T2.sedeReclutamiento,
		T2.numSede,
		T2.lugarSede,
		T2.idAspirante,
		T2.nombreCae,
		T2.puesto,
		CASE WHEN T2.NumCriterioOrden =3 THEN 'Declin\u00f3 entrevista para SE' ELSE T2. evaluacionIntSe END evaluacionIntSe,
		T2.evaluacionIntSeAux,
		T2.evaluacionIntCae,
		T2.evaluacionIntCaeAux,
		T2.domicilio,
		T2.telefono,
		T2.celular,
		T2.correo,
		T2.escolaridad,
		T2.carrera,
		T2.declinaEntrevistaSe,
		T2.estatusListaReserva,
		T2.imparteCapacit,
		T2.experienciaGrupos,
		T2.disponibleTiempo,
		T2.tieneEvaluacion,
		T2.declinaEntSe,
		T2.participoProceso,
		T2.NumCriterioOrden,
		CASE WHEN T2.NumCriterioOrden =1 THEN '#FFCCFF'
		WHEN T2.NumCriterioOrden =2 THEN '#CCFFFF'
		WHEN T2.NumCriterioOrden =3 THEN '#CC99FF'
		WHEN T2.NumCriterioOrden =5 THEN '#95DE6C' ELSE '' END as CellColorBackground,
		T2.estatusListaReserva AS estatusLista,
		T2.numSede ||' - ' || T2.lugarSede AS sede
		FROM (
			SELECT T1.idDetalle, T1.idParticipacion,
			T1.sedeReclutamiento, T1.numSede, T1.lugarSede,
			T1.idAspirante, T1.nombreCae, T1.puesto,
			T1.evaluacionIntSe, T1.evaluacionIntSeAux, T1.evaluacionIntCae, T1.evaluacionIntCaeAux,
			T1.domicilio, T1.telefono, T1.celular, T1.correo, T1.escolaridad, T1.carrera,
			T1.declinaEntrevistaSe, T1.estatusListaReserva, T1.imparteCapacit, T1.experienciaGrupos,
			T1.disponibleTiempo, T1.tieneEvaluacion, T1.declinaEntSe, T1.participoProceso,
			CASE WHEN NVL(T1.tieneEvaluacion, 0) =1 AND NVL(T1.declinaEntSe, 0) = 0 THEN 1
			WHEN NVL(T1.tieneEvaluacion, 0) = 2 AND NVL(T1.declinaEntSe, 0) = 0 THEN 2
			WHEN (NVL(T1.tieneEvaluacion, 0) = 1 OR NVL(T1.tieneEvaluacion, 0) = 2) AND NVL(T1.declinaEntSe, 0) = 1 THEN 3
			WHEN NVL(T1.tieneEvaluacion, 0) = 3 AND NVL(T1.declinaEntSe, 0) = 2 THEN 5
			ELSE 0 END AS NumCriterioOrden
			FROM (
				(
				SELECT a.id_detalle_proceso AS idDetalle, a.id_participacion AS idParticipacion,
				a.id_sede_reclutamiento AS sedeReclutamiento,
				se.numero_sede AS numSede,
				se.lugar AS lugarSede,
				a.id_aspirante AS idAspirante,
				( CASE WHEN a.apellido_paterno IS NULL THEN '' WHEN a.apellido_paterno IS NOT NULL THEN a.apellido_paterno END ) || ' ' ||
				( CASE WHEN a.apellido_materno IS NULL THEN '' WHEN a.apellido_materno IS NOT NULL THEN a.apellido_materno END ) || ' ' ||
				( CASE WHEN a.nombre IS NULL THEN '' WHEN a.nombre IS NOT NULL THEN a.nombre END ) AS nombreCae,
				a.id_puesto AS puesto,
				( CASE WHEN ei.evaluacion_integral_se >= 10 THEN TO_CHAR(ei.evaluacion_integral_se,'990.000')
				ELSE TO_CHAR(ei.evaluacion_integral_se,'990.000') END ) AS evaluacionIntSe,
				TO_CHAR(ei.evaluacion_integral_se,'990.000') AS evaluacionIntSeAux,
				( CASE WHEN ei.evaluacion_integral_se >= 10 THEN TO_CHAR(ei.evaluacion_integral_cae,'990.000')
				ELSE TO_CHAR(ei.evaluacion_integral_cae,'990.000') END ) AS evaluacionIntCae,
				TO_CHAR(ei.evaluacion_integral_cae,'990.000') AS evaluacionIntCaeAux,
				ec.calle_numero || ' ' || ec.colonia || ' ' || ec.cp AS domicilio,
				ec.telefono AS telefono,
				ec.celular AS celular,
				ec.correo_electronico AS correo,
				ce.descripcion_escolaridad AS escolaridad,
				NVL(ec.carrera,'S/I') carrera,
				ei.declino_entrevista_se AS declinaEntrevistaSe,
				a.estatus_lista_reserva AS estatusListaReserva,
				ec.impartir_capacitacion AS imparteCapacit,
				ec.experiencia_manejo_grupos_1 AS experienciaGrupos,
				ec.diponibilidad_tiempo_1 AS disponibleTiempo,
				( CASE WHEN ei.evaluacion_integral_se IS NOT NULL THEN '1' ELSE '2' END ) AS tieneEvaluacion,
				( CASE WHEN ei.declino_entrevista_se = 'S' THEN '1' ELSE '0' END ) AS declinaEntSe,
				ec.participo_proceso participoProceso
				FROM SUPYCAP.aspirantes a
				INNER JOIN SUPYCAP.sedes se ON se.id_proceso_electoral = a.id_proceso_electoral AND se.id_detalle_proceso = a.id_detalle_proceso
				AND se.id_participacion = a.id_participacion AND se.id_sede = a.id_sede_reclutamiento
				INNER JOIN SUPYCAP.evaluacion_integral ei ON ei.id_proceso_electoral = a.id_proceso_electoral AND ei.id_detalle_proceso = a.id_detalle_proceso
				AND ei.id_participacion = a.id_participacion AND ei.id_aspirante = a.id_aspirante
				INNER JOIN SUPYCAP.evaluacion_curricular ec ON ec.id_proceso_electoral = a.id_proceso_electoral AND ec.id_detalle_proceso = a.id_detalle_proceso
				AND ec.id_participacion = a.id_participacion AND ec.id_aspirante = a.id_aspirante
				INNER JOIN SUPYCAP.c_escolaridades ce ON ce.id_escolaridad = a.id_escolaridad
				WHERE a.id_proceso_electoral = :idProceso AND a.id_detalle_proceso = :idDetalle AND a.id_participacion = :idParticipacion
				AND ((a.id_puesto = :idPuesto AND ei.evaluacion_integral_se IS NOT NULL AND ei.declino_entrevista_se = 'N'
				AND a.estatus_lista_reserva IN (:estatus) AND (a.id_area_responsabilidad_1e IS NOT NULL OR a.id_area_responsabilidad_2e IS NOT NULL))
				OR (a.id_puesto = :idPuesto AND ei.evaluacion_integral_se IS NULL AND ei.evaluacion_integral_cae IS NOT NULL
				AND a.estatus_lista_reserva IN (:estatus) AND (a.id_area_responsabilidad_1e IS NOT NULL OR a.id_area_responsabilidad_2e IS NOT NULL))
				OR (a.id_puesto = :idPuesto AND ei.declino_entrevista_se = 'S' AND ei.evaluacion_integral_cae IS NOT NULL
				AND a.estatus_lista_reserva IN (:estatus) AND (a.id_area_responsabilidad_1e IS NOT NULL OR a.id_area_responsabilidad_2e IS NOT NULL)))
				AND (:filtro!=2 OR (se.id_municipio_dom = :idMunicipio AND (:idLocalidad <= 0 OR SE.ID_LOCALIDAD = :idLocalidad)))
				AND (:filtro != 3 OR a.id_sede_reclutamiento = :idSede )
				AND (:filtro != 4 OR se.seccion BETWEEN :seccion1 and :seccion2)
				)
			UNION
				(
				SELECT a.id_detalle_proceso AS idDetalle,
				a.id_participacion AS idParticipacion,
				a.id_sede_reclutamiento AS sedeReclutamiento,
				se.numero_sede AS numSede,
				se.lugar AS lugarSede,
				a.id_aspirante AS idAspirante,
				( CASE WHEN a.apellido_paterno IS NULL THEN '' WHEN a.apellido_paterno IS NOT NULL THEN a.apellido_paterno END ) || ' ' ||
				( CASE WHEN a.apellido_materno IS NULL THEN '' WHEN a.apellido_materno IS NOT NULL THEN a.apellido_materno END ) || ' ' ||
				( CASE WHEN a.nombre IS NULL THEN '' WHEN a.nombre IS NOT NULL THEN a.nombre END ) AS nombreCae,
				a.id_puesto AS puesto,
				( CASE WHEN ei.evaluacion_integral_se >= 10 THEN TO_CHAR(ei.evaluacion_integral_se,'990.000')
				ELSE TO_CHAR(ei.evaluacion_integral_se,'990.000') END ) AS evaluacionIntSe,
				TO_CHAR(ei.evaluacion_integral_se,'990.000') AS evaluacionIntSeAux,
				'' evaluacionIntCae,
				'' evaluacionIntCaeAux,
				ec.calle_numero || ' ' || ec.colonia || ' ' || ec.cp AS domicilio,
				ec.telefono AS telefono,
				ec.celular AS celular,
				ec.correo_electronico AS correo,
				ce.descripcion_escolaridad AS escolaridad,
				NVL(ec.carrera,'S/I') AS carrera,
				'' AS declinaEntrevistaSe,
				a.estatus_lista_reserva AS estatusListaReserva,
				ec.impartir_capacitacion AS imparteCapacit,
				ec.experiencia_manejo_grupos_1 AS experienciaGrupos,
				ec.diponibilidad_tiempo_1 AS disponibleTiempo,
				'3' AS tienEevaluacion,
				'2' AS declinaEntSe,
				ec.participo_proceso participoProceso
				FROM SUPYCAP.aspirantes a
				INNER JOIN SUPYCAP.sedes se ON se.id_proceso_electoral = a.id_proceso_electoral AND se.id_detalle_proceso = a.id_detalle_proceso
				AND se.id_participacion = a.id_participacion AND se.id_sede = a.id_sede_reclutamiento
				INNER JOIN SUPYCAP.evaluacion_integral ei ON ei.id_proceso_electoral = a.id_proceso_electoral AND ei.id_detalle_proceso = a.id_detalle_proceso
				AND ei.id_participacion = a.id_participacion AND ei.id_aspirante = a.id_aspirante
				INNER JOIN SUPYCAP.evaluacion_curricular ec ON ec.id_proceso_electoral = a.id_proceso_electoral AND ec.id_detalle_proceso = a.id_detalle_proceso
				AND ec.id_participacion = a.id_participacion AND ec.id_aspirante = a.id_aspirante
				INNER JOIN SUPYCAP.c_escolaridades ce ON ce.id_escolaridad = a.id_escolaridad
				WHERE a.id_proceso_electoral = :idProceso AND a.id_detalle_proceso = :idDetalle AND a.id_participacion = :idParticipacion
				AND a.id_puesto = 3 AND ei.declino_entrevista_se = 'N' AND ei.evaluacion_integral_se IS NOT NULL
				AND a.estatus_lista_reserva IN (:estatus)
				AND (:filtro!=2 OR (se.id_municipio_dom = :idMunicipio AND (:idLocalidad <= 0 OR SE.ID_LOCALIDAD = :idLocalidad)))
				AND (:filtro != 3 OR a.id_sede_reclutamiento = :idSede )
				AND (:filtro != 4 OR se.seccion BETWEEN :seccion1 and :seccion2)
				) ORDER BY declinaEntSE ASC, tieneEvaluacion ASC, evaluacionIntSeAux DESC, evaluacionIntCaeAux DESC, participoProceso DESC,
				imparteCapacit DESC, experienciaGrupos DESC, disponibleTiempo DESC
			)T1 ORDER BY declinaEntSE ASC, tieneEvaluacion ASC, evaluacionIntSeAux DESC, evaluacionIntCaeAux DESC, participoProceso DESC,
			imparteCapacit DESC, experienciaGrupos DESC, disponibleTiempo DESC
		)T2""", resultSetMapping = "listaReservaSe")

@NamedNativeQuery(name = "DTOAspirantes.obtenerListaReservaCAE", query = """
		SELECT ROWNUM AS num,
		idProceso,
		idDetalle,
		idParticipacion,
		idSedeReclutamiento,
		numSede,
		lugarSede,
		idAspirante,
		nombreAspirante,
		puesto,
		evaluacionInteCAE,
		representantePP,
		militantePP,
		estatusListaReserva,
		observaciones
		FROM (
			SELECT A.ID_PROCESO_ELECTORAL AS idProceso,
			A.ID_DETALLE_PROCESO AS idDetalle,
			A.ID_PARTICIPACION AS idParticipacion,
			A.ID_SEDE_RECLUTAMIENTO AS idSedeReclutamiento,
			SE.NUMERO_SEDE AS numSede,
			SE.NUMERO_SEDE || ' - ' || SE.LUGAR AS lugarSede,
			A.ID_ASPIRANTE AS idAspirante,
			( CASE WHEN A.APELLIDO_PATERNO IS NULL THEN '' WHEN A.APELLIDO_PATERNO IS NOT NULL THEN A.APELLIDO_PATERNO END ) || ' ' ||
			( CASE WHEN A.APELLIDO_MATERNO IS NULL THEN '' WHEN A.APELLIDO_MATERNO IS NOT NULL THEN A.APELLIDO_MATERNO END ) || ' ' ||
			( CASE WHEN A.NOMBRE IS NULL THEN '' WHEN A.NOMBRE IS NOT NULL THEN A.NOMBRE END ) AS nombreAspirante,
			A.ID_PUESTO AS puesto,
			( CASE WHEN EI.EVALUACION_INTEGRAL_CAE >= 10 THEN TO_CHAR(EI.EVALUACION_INTEGRAL_CAE, '00.990')
			ELSE TO_CHAR(EI.EVALUACION_INTEGRAL_CAE, '0.990') END ) AS evaluacionInteCAE,
			EC.REPRESENTANTE_PARTIDO_1 AS representantePP,
			EC.MILITANTE_PARTIDO_1 AS militantePP,
			A.ESTATUS_LISTA_RESERVA AS estatusListaReserva,
			A.OBS_DECLINACION_LISTA_RESERVA AS observaciones
			FROM SUPYCAP.ASPIRANTES A
			INNER JOIN SUPYCAP.SEDES SE ON SE.ID_PROCESO_ELECTORAL = A.ID_PROCESO_ELECTORAL
			AND SE.ID_DETALLE_PROCESO = A.ID_DETALLE_PROCESO AND SE.ID_PARTICIPACION = A.ID_PARTICIPACION
			AND SE.ID_SEDE = A.ID_SEDE_RECLUTAMIENTO
			INNER JOIN SUPYCAP.EVALUACION_INTEGRAL EI ON EI.ID_PROCESO_ELECTORAL = A.ID_PROCESO_ELECTORAL
			AND EI.ID_DETALLE_PROCESO = A.ID_DETALLE_PROCESO AND EI.ID_PARTICIPACION = A.ID_PARTICIPACION
			AND EI.ID_ASPIRANTE = A.ID_ASPIRANTE
			INNER JOIN SUPYCAP.EVALUACION_CURRICULAR EC ON EC.ID_PROCESO_ELECTORAL = A.ID_PROCESO_ELECTORAL
			AND EC.ID_DETALLE_PROCESO = A.ID_DETALLE_PROCESO AND EC.ID_PARTICIPACION = A.ID_PARTICIPACION
			AND EC.ID_ASPIRANTE = A.ID_ASPIRANTE
			INNER JOIN SUPYCAP.C_ESCOLARIDADES CE ON ( CE.ID_ESCOLARIDAD = A.ID_ESCOLARIDAD )
			WHERE A.ID_PROCESO_ELECTORAL = :idProceso AND A.ID_DETALLE_PROCESO = :idDetalle AND A.ID_PARTICIPACION = :idParticipacion
			AND A.ID_PUESTO = :idPuesto AND EI.EVALUACION_INTEGRAL_CAE IS NOT NULL
			AND ((:estatus != 0  OR A.ESTATUS_LISTA_RESERVA = 0)
			AND (:estatus != 1 OR A.ESTATUS_LISTA_RESERVA = 1)
			AND (:estatus != 2 OR A.ESTATUS_LISTA_RESERVA = 2)
			AND (:estatus != 3 OR A.ESTATUS_LISTA_RESERVA =3))
			AND ( (:filtro != 2 OR (SE.ID_MUNICIPIO_DOM = :idMunicipio
			AND (:idLocalidad <= 0 OR SE.ID_LOCALIDAD = :idLocalidad)) )
			AND (:filtro != 3 OR A.ID_SEDE_RECLUTAMIENTO = :idSede )
			AND (:filtro != 4 OR SE.SECCION BETWEEN :seccion1 and :seccion2))
			ORDER BY EI.EVALUACION_INTEGRAL_CAE DESC, EC.PARTICIPO_PROCESO DESC, EC.IMPARTIR_CAPACITACION DESC,
			EI.CALIF_EXAMEN DESC, EI.PROMEDIO_ENTREVISTA_CAE DESC
		) T1""", resultSetMapping = "listaReservaCAE")

@Entity
@Table(schema = "SUPYCAP", name = "ASPIRANTES")
public class DTOAspirantes implements Serializable {

    private static final long serialVersionUID = 5085300296494098342L;

    @EmbeddedId
    private DTOAspirantesId id;

    @Column(name = "FOLIO", nullable = false, precision = 5, scale = 0)
    private Integer folioPrincipal;

    @Column(name = "ID_CONVOCATORIA", nullable = false, precision = 5, scale = 0)
    private Integer idConvocatoria;

    @Column(name = "ID_SEDE_RECLUTAMIENTO", nullable = false, precision = 5, scale = 0)
    private Integer idSedeReclutamiento;

    @Column(name = "CLAVE_ELECTOR_FUAR", nullable = false, length = 18)
    private String claveElectorFuar;

    @Column(name = "APELLIDO_PATERNO", length = 40)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 40)
    private String apellidoMaterno;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "SECCION_DOM", nullable = false, precision = 4, scale = 0)
    private Integer seccionDom;

    @Column(name = "ID_ESCOLARIDAD", nullable = false, precision = 2, scale = 0)
    private Integer idEscolaridad;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_SOLICITUD", nullable = false)
    private Date fechaSolicitud;

    @Column(name = "ID_PUESTO", precision = 2, scale = 0)
    private Integer idPuesto;

    @Column(name = "ID_ZONA_RESPONSABILIDAD_1E", precision = 8, scale = 0)
    private Integer idZonaResponsabilidad1e;

    @Column(name = "ID_AREA_RESPONSABILIDAD_1E", precision = 8, scale = 0)
    private Integer idAreaResponsabilidad1e;

    @Column(name = "ID_ZONA_RESPONSABILIDAD_2E", precision = 2, scale = 0)
    private Integer idZonaResponsabilidad2e;

    @Column(name = "ID_AREA_RESPONSABILIDAD_2E", precision = 3, scale = 0)
    private Integer idAreaResponsabilidad2e;

    @Column(name = "ESTATUS_LISTA_RESERVA", precision = 1, scale = 0)
    private Integer estatusListaReserva;

    @Column(name = "DECLINO_CARGO", precision = 1, scale = 0)
    private Integer declinoCargo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_DECLINACION")
    private Date fechaDeclinacion;

    @Column(name = "JUSTIFICACION_DECLINACION", length = 1500)
    private String justificacionDeclinacion;

    @Column(name = "OBS_DECLINACION_LISTA_RESERVA", length = 150)
    private String obsDeclinacionListaReserva;

    @Column(name = "URL_FOTO", length = 500)
    private String urlFoto;

    @Column(name = "ID_PLATICA", precision = 5, scale = 0)
    private Integer idPlatica;

    @Column(name = "ESTATUS", nullable = false, length = 1)
    private String estatus;

    @Column(name = "HABLA_LENGUA_INDIGENA", nullable = false, length = 1)
    private String hablaLenguaIndigena;

    @Column(name = "CUAL_LENGUA_INDIGENA", length = 50)
    private String cualLenguaIndigena;

    @Column(name = "EXISTE_INTERCAMBIO_1E", length = 1)
    private String existeIntercambioE1;

    @Column(name = "EXISTE_INTERCAMBIO_2E", length = 1)
    private String existeIntercambioE2;

    @Column(name = "EXISTE_SUSTITUCION", length = 1)
    private String existeSustitucion;

    @Column(name = "UID_CUENTA", length = 50)
    private String uidCuenta;

    @Column(name = "AVANCE_INSA1", precision = 1, scale = 0)
    private Integer avanceInsa1;

    /*
	 * @Column(name = "ID_TIPO_VOTO", nullable = false)
	 * private Integer idTipoVoto;
     */
    @Column(name = "CORREO_CUENTA_CREADA", length = 60)
    private String correoCtaCreada;

    @Column(name = "CORREO_CUENTA_NOTIFICACION", length = 60)
    private String correoCtaNotificacion;

    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Column(name = "IP_USUARIO", length = 15)
    private String ipUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_HORA", nullable = false)
    private Date fechaHora;

    @Column(name = "TIPO_CONTRATACION", length = 1)
    private String tipoContratacion;

    @Column(name = "TELEFONO_CUENTA_CREADA", length = 25)
    private String telefonoCtaCreada;

    @Column(name = "SERVICIO_USADO_CTA", nullable = true, precision = 1, scale = 0)
    private Integer servicioUsadoCta;

    @Column(name = "ID_SISTEMA_ACTUALIZA", precision = 3)
    private Integer idSistemaActualiza;

    @Column(name = "ORIGEN_REGISTRO", nullable = true, precision = 2, scale = 0)
    private Integer origenRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_PLATICA_EN_LINEA", nullable = true)
    private Date fechaPlaticaEnLinea;

    @Column(name = "ESTATUS_REGISTRO", nullable = true, precision = 2, scale = 0)
    private Integer estatusRegistro;

    @Column(name = "B_OBSERVACION", nullable = true, precision = 1, scale = 0)
    private Integer bObservcion;

    @Column(name = "B_ACUDIR_JDE", nullable = true, precision = 1, scale = 0)
    private Integer bAcudirJDE;

    @Column(name = "B_PLATICA", nullable = true, precision = 1, scale = 0)
    private Integer bPlatica;

    @Column(name = "B_VALIDA_CORREO", nullable = true, precision = 1, scale = 0)
    private Integer bValidaCorreo;

    @Column(name = "ID_SEDE_PROGRAMADA", nullable = true, precision = 5, scale = 0)
    private Integer idSedeProgramada;

    @Column(name = "FECHA_PROGRAMADA", nullable = true)
    private Date fechaProgramada;

    @Column(name = "HORA_PROGRAMADA", length = 5)
    private String horaProgramada;

    @Column(name = "FOLIO_ACCESO", length = 14)
    private String folioAcceso;

    @Column(name = "DATOS_PERSONALES", nullable = true, precision = 1, scale = 0)
    private Integer datosPersonales;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CAPSULA", nullable = true)
    private Date fechaCapsula;

    @Column(name = "FECHA_CREACION_CUENTA_MOVIL", nullable = true)
    private Date fechaCreacionCuentaMovil;

    @Column(name = "B_OTRO_CORTE", nullable = true, precision = 1, scale = 0)
    private Integer bOtroCorte;

    @Transient
    private String nombreMunicipio;

    public DTOAspirantes() {
        super();
    }

    public DTOAspirantes(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspirante, Integer folioPrincipal, Integer idConvocatoria, Integer idSedeReclutamiento,
            String claveElectorFuar, String apellidoPaterno, String apellidoMaterno, String nombre, Integer seccionDom,
            Integer idEscolaridad, Date fechaSolicitud, Integer idPuesto, Integer idZonaResponsabilidad1e,
            Integer idAreaResponsabilidad1e, Integer idZonaResponsabilidad2e, Integer idAreaResponsabilidad2e,
            Integer estatusListaReserva, Integer declinoCargo, Date fechaDeclinacion, String justificacionDeclinacion,
            String obsDeclinacionListaReserva, String urlFoto, Integer idPlatica, String estatus,
            String hablaLenguaIndigena, String cualLenguaIndigena, String existeIntercambioE1,
            String existeIntercambioE2, String existeSustitucion, String uidCuenta, Integer avanceInsa1,
            /* Integer idTipoVoto, */ String correoCtaCreada, String correoCtaNotificacion, String usuario,
            String ipUsuario, Date fechaHora, String tipoContratacion, String telefonoCtaCreada,
            Integer servicioUsadoCta, Integer idSistemaActualiza, Integer origenRegistro, Date fechaPlaticaEnLinea,
            Integer estatusRegistro, Integer bObservcion, Integer bAcudirJDE, Integer bPlatica, Integer bValidaCorreo,
            Integer idSedeProgramada, Date fechaProgramada, String horaProgramada, String folioAcceso,
            Integer datosPersonales, Date fechaCapsula, Date fechaCreacionCuentaMovil, Integer bOtroCorte) {
        super();
        this.folioPrincipal = folioPrincipal;
        this.idConvocatoria = idConvocatoria;
        this.idSedeReclutamiento = idSedeReclutamiento;
        this.claveElectorFuar = claveElectorFuar;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombre = nombre;
        this.seccionDom = seccionDom;
        this.idEscolaridad = idEscolaridad;
        this.fechaSolicitud = fechaSolicitud;
        this.idPuesto = idPuesto;
        this.idZonaResponsabilidad1e = idZonaResponsabilidad1e;
        this.idAreaResponsabilidad1e = idAreaResponsabilidad1e;
        this.idZonaResponsabilidad2e = idZonaResponsabilidad2e;
        this.idAreaResponsabilidad2e = idAreaResponsabilidad2e;
        this.estatusListaReserva = estatusListaReserva;
        this.declinoCargo = declinoCargo;
        this.fechaDeclinacion = fechaDeclinacion;
        this.justificacionDeclinacion = justificacionDeclinacion;
        this.obsDeclinacionListaReserva = obsDeclinacionListaReserva;
        this.urlFoto = urlFoto;
        this.idPlatica = idPlatica;
        this.estatus = estatus;
        this.hablaLenguaIndigena = hablaLenguaIndigena;
        this.cualLenguaIndigena = cualLenguaIndigena;
        this.existeIntercambioE1 = existeIntercambioE1;
        this.existeIntercambioE2 = existeIntercambioE2;
        this.existeSustitucion = existeSustitucion;
        this.uidCuenta = uidCuenta;
        this.avanceInsa1 = avanceInsa1;
        // this.idTipoVoto = idTipoVoto;
        this.correoCtaCreada = correoCtaCreada;
        this.correoCtaNotificacion = correoCtaNotificacion;
        this.usuario = usuario;
        this.ipUsuario = ipUsuario;
        this.fechaHora = fechaHora;
        this.tipoContratacion = tipoContratacion;
        this.telefonoCtaCreada = telefonoCtaCreada;
        this.servicioUsadoCta = servicioUsadoCta;
        this.idSistemaActualiza = idSistemaActualiza;
        this.origenRegistro = origenRegistro;
        this.fechaPlaticaEnLinea = fechaPlaticaEnLinea;
        this.estatusRegistro = estatusRegistro;
        this.bObservcion = bObservcion;
        this.bAcudirJDE = bAcudirJDE;
        this.bPlatica = bPlatica;
        this.bValidaCorreo = bValidaCorreo;
        this.idSedeProgramada = idSedeProgramada;
        this.fechaProgramada = fechaProgramada;
        this.horaProgramada = horaProgramada;
        this.folioAcceso = folioAcceso;
        this.datosPersonales = datosPersonales;
        this.fechaCapsula = fechaCapsula;
        this.fechaCreacionCuentaMovil = fechaCreacionCuentaMovil;
        this.bOtroCorte = bOtroCorte;
        id = new DTOAspirantesId(idProcesoElectoral, idDetalleProceso, idParticipacion, idAspirante);
    }

    public DTOAspirantes(DTOAspirantes asp) {
        super();
        this.folioPrincipal = asp.getFolioPrincipal();
        this.idConvocatoria = asp.getIdConvocatoria();
        this.idSedeReclutamiento = asp.getIdSedeReclutamiento();
        this.claveElectorFuar = asp.getClaveElectorFuar();
        this.apellidoPaterno = asp.getApellidoPaterno();
        this.apellidoMaterno = asp.getApellidoMaterno();
        this.nombre = asp.getNombre();
        this.seccionDom = asp.getSeccionDom();
        this.idEscolaridad = asp.getIdEscolaridad();
        this.fechaSolicitud = asp.getFechaSolicitud();
        this.idPuesto = asp.getIdPuesto();
        this.idZonaResponsabilidad1e = asp.getIdZonaResponsabilidad1e();
        this.idAreaResponsabilidad1e = asp.getIdAreaResponsabilidad1e();
        this.idZonaResponsabilidad2e = asp.getIdZonaResponsabilidad2e();
        this.idAreaResponsabilidad2e = asp.getIdAreaResponsabilidad2e();
        this.estatusListaReserva = asp.getEstatusListaReserva();
        this.declinoCargo = asp.getDeclinoCargo();
        this.fechaDeclinacion = asp.getFechaDeclinacion();
        this.justificacionDeclinacion = asp.getJustificacionDeclinacion();
        this.obsDeclinacionListaReserva = asp.getObsDeclinacionListaReserva();
        this.urlFoto = asp.getUrlFoto();
        this.idPlatica = asp.getIdPlatica();
        this.estatus = asp.getEstatus();
        this.hablaLenguaIndigena = asp.getHablaLenguaIndigena();
        this.cualLenguaIndigena = asp.getCualLenguaIndigena();
        this.existeIntercambioE1 = asp.getExisteIntercambioE1();
        this.existeIntercambioE2 = asp.getExisteIntercambioE2();
        this.existeSustitucion = asp.getExisteSustitucion();
        this.uidCuenta = asp.getUidCuenta();
        this.avanceInsa1 = asp.getAvanceInsa1();
        this.correoCtaCreada = asp.getCorreoCtaCreada();
        this.correoCtaNotificacion = asp.getCorreoCtaNotificacion();
        this.usuario = asp.getUsuario();
        this.ipUsuario = asp.getIpUsuario();
        this.fechaHora = asp.getFechaHora();
        this.tipoContratacion = asp.getTipoContratacion();
        this.telefonoCtaCreada = asp.getTelefonoCtaCreada();
        this.servicioUsadoCta = asp.getServicioUsadoCta();
        this.idSistemaActualiza = asp.getIdSistemaActualiza();
        this.origenRegistro = asp.getOrigenRegistro();
        this.fechaPlaticaEnLinea = asp.getFechaPlaticaEnLinea();
        this.estatusRegistro = asp.getEstatusRegistro();
        this.bObservcion = asp.getbObservcion();
        this.bAcudirJDE = asp.getbAcudirJDE();
        this.bPlatica = asp.getbPlatica();
        this.bValidaCorreo = asp.getbValidaCorreo();
        this.idSedeProgramada = asp.getIdSedeProgramada();
        this.fechaProgramada = asp.getFechaProgramada();
        this.horaProgramada = asp.getHoraProgramada();
        this.folioAcceso = asp.getFolioAcceso();
        this.datosPersonales = asp.getDatosPersonales();
        this.fechaCapsula = asp.getFechaCapsula();
        this.fechaCreacionCuentaMovil = asp.getFechaCreacionCuentaMovil();
        this.bOtroCorte = asp.getbOtroCorte();
        id = new DTOAspirantesId(asp.getId().getIdProcesoElectoral(), asp.getId().getIdDetalleProceso(),
                asp.getId().getIdParticipacion(), asp.getId().getIdAspirante());
    }

    public DTOAspirantesId getId() {
        return id;
    }

    public void setId(DTOAspirantesId id) {
        this.id = id;
    }

    public Integer getIdProcesoElectoral() {
        return id.getIdProcesoElectoral();
    }

    public void setIdProcesoElectoral(Integer idProcesoElectoral) {
        id.setIdProcesoElectoral(idProcesoElectoral);
    }

    public Integer getIdDetalleProceso() {
        return id.getIdDetalleProceso();
    }

    public void setIdDetalleProceso(Integer idDetalleProceso) {
        id.setIdDetalleProceso(idDetalleProceso);
    }

    public Integer getIdParticipacion() {
        return id.getIdParticipacion();
    }

    public void setIdParticipacion(Integer idParticipacion) {
        id.setIdParticipacion(idParticipacion);
    }

    public Integer getIdAspirante() {
        return id.getIdAspirante();
    }

    public void setIdAspirante(Integer idAspirante) {
        id.setIdAspirante(idAspirante);
    }

    public Integer getFolioPrincipal() {
        return folioPrincipal;
    }

    public void setFolioPrincipal(Integer folioPrincipal) {
        this.folioPrincipal = folioPrincipal;
    }

    public Integer getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Integer idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public Integer getIdSedeReclutamiento() {
        return idSedeReclutamiento;
    }

    public void setIdSedeReclutamiento(Integer idSedeReclutamiento) {
        this.idSedeReclutamiento = idSedeReclutamiento;
    }

    public String getClaveElectorFuar() {
        return claveElectorFuar;
    }

    public void setClaveElectorFuar(String claveElectorFuar) {
        this.claveElectorFuar = claveElectorFuar;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSeccionDom() {
        return seccionDom;
    }

    public void setSeccionDom(Integer seccionDom) {
        this.seccionDom = seccionDom;
    }

    public Integer getIdEscolaridad() {
        return idEscolaridad;
    }

    public void setIdEscolaridad(Integer idEscolaridad) {
        this.idEscolaridad = idEscolaridad;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public Integer getIdZonaResponsabilidad1e() {
        return idZonaResponsabilidad1e;
    }

    public void setIdZonaResponsabilidad1e(Integer idZonaResponsabilidad1e) {
        this.idZonaResponsabilidad1e = idZonaResponsabilidad1e;
    }

    public Integer getIdAreaResponsabilidad1e() {
        return idAreaResponsabilidad1e;
    }

    public void setIdAreaResponsabilidad1e(Integer idAreaResponsabilidad1e) {
        this.idAreaResponsabilidad1e = idAreaResponsabilidad1e;
    }

    public Integer getIdZonaResponsabilidad2e() {
        return idZonaResponsabilidad2e;
    }

    public void setIdZonaResponsabilidad2e(Integer idZonaResponsabilidad2e) {
        this.idZonaResponsabilidad2e = idZonaResponsabilidad2e;
    }

    public Integer getIdAreaResponsabilidad2e() {
        return idAreaResponsabilidad2e;
    }

    public void setIdAreaResponsabilidad2e(Integer idAreaResponsabilidad2e) {
        this.idAreaResponsabilidad2e = idAreaResponsabilidad2e;
    }

    public Integer getEstatusListaReserva() {
        return estatusListaReserva;
    }

    public void setEstatusListaReserva(Integer estatusListaReserva) {
        this.estatusListaReserva = estatusListaReserva;
    }

    public Integer getDeclinoCargo() {
        return declinoCargo;
    }

    public void setDeclinoCargo(Integer declinoCargo) {
        this.declinoCargo = declinoCargo;
    }

    public Date getFechaDeclinacion() {
        return fechaDeclinacion;
    }

    public void setFechaDeclinacion(Date fechaDeclinacion) {
        this.fechaDeclinacion = fechaDeclinacion;
    }

    public String getJustificacionDeclinacion() {
        return justificacionDeclinacion;
    }

    public void setJustificacionDeclinacion(String justificacionDeclinacion) {
        this.justificacionDeclinacion = justificacionDeclinacion;
    }

    public String getObsDeclinacionListaReserva() {
        return obsDeclinacionListaReserva;
    }

    public void setObsDeclinacionListaReserva(String obsDeclinacionListaReserva) {
        this.obsDeclinacionListaReserva = obsDeclinacionListaReserva;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Integer getIdPlatica() {
        return idPlatica;
    }

    public void setIdPlatica(Integer idPlatica) {
        this.idPlatica = idPlatica;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getHablaLenguaIndigena() {
        return hablaLenguaIndigena;
    }

    public void setHablaLenguaIndigena(String hablaLenguaIndigena) {
        this.hablaLenguaIndigena = hablaLenguaIndigena;
    }

    public String getCualLenguaIndigena() {
        return cualLenguaIndigena;
    }

    public void setCualLenguaIndigena(String cualLenguaIndigena) {
        this.cualLenguaIndigena = cualLenguaIndigena;
    }

    public String getExisteIntercambioE1() {
        return existeIntercambioE1;
    }

    public void setExisteIntercambioE1(String existeIntercambioE1) {
        this.existeIntercambioE1 = existeIntercambioE1;
    }

    public String getExisteIntercambioE2() {
        return existeIntercambioE2;
    }

    public void setExisteIntercambioE2(String existeIntercambioE2) {
        this.existeIntercambioE2 = existeIntercambioE2;
    }

    public String getExisteSustitucion() {
        return existeSustitucion;
    }

    public void setExisteSustitucion(String existeSustitucion) {
        this.existeSustitucion = existeSustitucion;
    }

    public String getUidCuenta() {
        return uidCuenta;
    }

    public void setUidCuenta(String uidCuenta) {
        this.uidCuenta = uidCuenta;
    }

    public Integer getAvanceInsa1() {
        return avanceInsa1;
    }

    public void setAvanceInsa1(Integer avanceInsa1) {
        this.avanceInsa1 = avanceInsa1;
    }

    /**
     * @return the idTipoVoto
     */
    /*
	 * public Integer getIdTipoVoto() {
	 * return idTipoVoto;
	 * }
     */
    /**
     * @param idTipoVoto the idTipoVoto to set
     */
    /*
	 * public void setIdTipoVoto(Integer idTipoVoto) {
	 * this.idTipoVoto = idTipoVoto;
	 * }
     */
    public String getCorreoCtaCreada() {
        return correoCtaCreada;
    }

    public void setCorreoCtaCreada(String correoCtaCreada) {
        this.correoCtaCreada = correoCtaCreada;
    }

    public String getCorreoCtaNotificacion() {
        return correoCtaNotificacion;
    }

    public void setCorreoCtaNotificacion(String correoCtaNotificacion) {
        this.correoCtaNotificacion = correoCtaNotificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoContratacion() {
        return tipoContratacion;
    }

    public void setTipoContratacion(String tipoContratacion) {
        this.tipoContratacion = tipoContratacion;
    }

    public String getTelefonoCtaCreada() {
        return telefonoCtaCreada;
    }

    public void setTelefonoCtaCreada(String telefonoCtaCreada) {
        this.telefonoCtaCreada = telefonoCtaCreada;
    }

    public Integer getServicioUsadoCta() {
        return servicioUsadoCta;
    }

    public void setServicioUsadoCta(Integer servicioUsadoCta) {
        this.servicioUsadoCta = servicioUsadoCta;
    }

    public Integer getIdSistemaActualiza() {
        return idSistemaActualiza;
    }

    public void setIdSistemaActualiza(Integer idSistemaActualiza) {
        this.idSistemaActualiza = idSistemaActualiza;
    }

    public Integer getOrigenRegistro() {
        return origenRegistro;
    }

    public void setOrigenRegistro(Integer origenRegistro) {
        this.origenRegistro = origenRegistro;
    }

    public Date getFechaPlaticaEnLinea() {
        return fechaPlaticaEnLinea;
    }

    public void setFechaPlaticaEnLinea(Date fechaPlaticaEnLinea) {
        this.fechaPlaticaEnLinea = fechaPlaticaEnLinea;
    }

    public Integer getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(Integer estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }

    public Integer getbObservcion() {
        return bObservcion;
    }

    public void setbObservcion(Integer bObservcion) {
        this.bObservcion = bObservcion;
    }

    public Integer getbAcudirJDE() {
        return bAcudirJDE;
    }

    public void setbAcudirJDE(Integer bAcudirJDE) {
        this.bAcudirJDE = bAcudirJDE;
    }

    public Integer getbPlatica() {
        return bPlatica;
    }

    public void setbPlatica(Integer bPlatica) {
        this.bPlatica = bPlatica;
    }

    public Integer getbValidaCorreo() {
        return bValidaCorreo;
    }

    public void setbValidaCorreo(Integer bValidaCorreo) {
        this.bValidaCorreo = bValidaCorreo;
    }

    public Integer getIdSedeProgramada() {
        return idSedeProgramada;
    }

    public void setIdSedeProgramada(Integer idSedeProgramada) {
        this.idSedeProgramada = idSedeProgramada;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getHoraProgramada() {
        return horaProgramada;
    }

    public void setHoraProgramada(String horaProgramada) {
        this.horaProgramada = horaProgramada;
    }

    public String getFolioAcceso() {
        return folioAcceso;
    }

    public void setFolioAcceso(String folioAcceso) {
        this.folioAcceso = folioAcceso;
    }

    public Integer getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(Integer datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    public Date getFechaCapsula() {
        return fechaCapsula;
    }

    public void setFechaCapsula(Date fechaCapsula) {
        this.fechaCapsula = fechaCapsula;
    }

    public Date getFechaCreacionCuentaMovil() {
        return fechaCreacionCuentaMovil;
    }

    public void setFechaCreacionCuentaMovil(Date fechaCreacionCuentaMovil) {
        this.fechaCreacionCuentaMovil = fechaCreacionCuentaMovil;
    }

    public Integer getbOtroCorte() {
        return bOtroCorte;
    }

    public void setbOtroCorte(Integer bOtroCorte) {
        this.bOtroCorte = bOtroCorte;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getIdToString() {

        return "DTOAspirantesId:{ idProcesoElectoral: " + id.getIdProcesoElectoral()
                + ", idDetalleProceso: " + id.getIdDetalleProceso()
                + ", idParticipacion: " + id.getIdParticipacion()
                + ", idAspirante: " + id.getIdAspirante() + "}";
    }

}
