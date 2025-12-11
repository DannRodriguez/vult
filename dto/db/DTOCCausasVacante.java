package mx.ine.sustseycae.dto.db;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOCombo;
import mx.ine.sustseycae.dto.vo.VOComboPendientes;
import mx.ine.sustseycae.dto.vo.VOInfoSustituido;

@SqlResultSetMapping(name = "combo", classes = {
    @ConstructorResult(targetClass = VOCombo.class, columns = {
        @ColumnResult(name = "id", type = String.class),
        @ColumnResult(name = "value", type = String.class),})
})
@SqlResultSetMapping(name = "comboPendientes", classes = {
    @ConstructorResult(targetClass = VOComboPendientes.class, columns = {
        @ColumnResult(name = "id", type = String.class),
        @ColumnResult(name = "value", type = String.class),
        @ColumnResult(name = "causa", type = String.class),})
})
@SqlResultSetMapping(name = "aspirante", classes = {
    @ConstructorResult(targetClass = DTOAspirante.class, columns = {
        @ColumnResult(name = "idDetalleProceso", type = Integer.class),
        @ColumnResult(name = "idParticipacion", type = Integer.class),
        @ColumnResult(name = "idAspirante", type = Integer.class),
        @ColumnResult(name = "folio", type = Integer.class),
        @ColumnResult(name = "claveElectorFuar", type = String.class),
        @ColumnResult(name = "apellidoPaterno", type = String.class),
        @ColumnResult(name = "apellidoMaterno", type = String.class),
        @ColumnResult(name = "nombre", type = String.class),
        @ColumnResult(name = "urlFoto", type = String.class),
        @ColumnResult(name = "uidCuenta", type = String.class),
        @ColumnResult(name = "correoCtaCreada", type = String.class),
        @ColumnResult(name = "correoCtaNotificacion", type = String.class),
        @ColumnResult(name = "idPuesto", type = Integer.class),
        @ColumnResult(name = "declinoCargo", type = Integer.class),
        @ColumnResult(name = "idZonaResponsabilidad", type = Integer.class),
        @ColumnResult(name = "idAreaResponsabilidad", type = String.class),
        @ColumnResult(name = "idZonaResponsabilidad2e", type = Integer.class),
        @ColumnResult(name = "idAreaResponsabilidad2e", type = String.class),
        @ColumnResult(name = "idEstadoAsp", type = Integer.class),
        @ColumnResult(name = "participoProceso", type = String.class),
        @ColumnResult(name = "cualProceso", type = String.class),
        @ColumnResult(name = "participoSE", type = String.class),
        @ColumnResult(name = "participoCAE", type = String.class),
        @ColumnResult(name = "participoOtroEspecifique", type = String.class),
        @ColumnResult(name = "fecha", type = String.class),
        @ColumnResult(name = "observaciones", type = String.class),
        @ColumnResult(name = "cargoAnterior", type = String.class)
    })
})
@SqlResultSetMapping(name = "infoSustituido", classes = {
    @ConstructorResult(targetClass = VOInfoSustituido.class, columns = {
        @ColumnResult(name = "idAreaResponsabilidad", type = String.class),
        @ColumnResult(name = "idZonaResponsabilidad", type = Integer.class)

    })
})

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaSupervisor", query = """
        WITH supervisores AS
                (SELECT ID_DETALLE_PROCESO,
                      ID_PARTICIPACION,
                      ID_ASPIRANTE AS id,
                      APELLIDO_PATERNO
                          || ' ' || APELLIDO_MATERNO
                          || ' ' || NOMBRE AS nombre,
                      FOLIO,
                      ID_ZONA_RESPONSABILIDAD_1E,
                      ID_ZONA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES
                WHERE ID_DETALLE_PROCESO = :idDetalle
                     AND ID_PARTICIPACION = :idParticipacion
                     AND ID_PUESTO IN (:puestos)
                     AND ESTATUS = 'A'
                     AND ID_ASPIRANTE NOT IN
                       (SELECT ID_ASPIRANTE_SUSTITUIDO
                        FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
                        WHERE ID_DETALLE_PROCESO = :idDetalle
                          AND ID_PARTICIPACION = :idParticipacion
                          AND ID_ASPIRANTE_SUSTITUIDO IS NOT NULL
                          AND ID_ASPIRANTE_SUSTITUTO IS NULL))
        SELECT id,
              folio
                ||' - '|| nombre AS value
        FROM
            (SELECT s1.id,
                   s1.folio,
                   s1.nombre
            FROM supervisores s1
                JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_E1 ON(s1.ID_DETALLE_PROCESO = ZORE_E1.ID_DETALLE_PROCESO
                                                            AND s1.ID_PARTICIPACION = ZORE_E1.ID_PARTICIPACION
                                                            AND s1.ID_ZONA_RESPONSABILIDAD_1E = ZORE_E1.ID_ZONA_RESPONSABILIDAD)
            WHERE :etapa = 1
            UNION ALL
            SELECT s2.id,
                   s2.folio,
                   s2.nombre
            FROM supervisores s2
                JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_E2 ON(s2.ID_DETALLE_PROCESO = ZORE_E2.ID_DETALLE_PROCESO
                                                            AND s2.ID_PARTICIPACION = ZORE_E2.ID_PARTICIPACION
                                                            AND s2.ID_ZONA_RESPONSABILIDAD_2E = ZORE_E2.ID_ZONA_RESPONSABILIDAD)
            WHERE :etapa = 2)
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaSupervisorCM", query = """
        WITH supervisores AS
                (SELECT ASP.ID_ASPIRANTE AS id,
                      ASP.APELLIDO_PATERNO
                          || ' ' || ASP.APELLIDO_MATERNO
                          || ' ' || ASP.NOMBRE AS nombre,
                      ASP.FOLIO,
                      SUST.ID_SUSTITUCION,
                      SUST.ID_ZONA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES ASP
                    JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                            AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                            AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                     AND ASP.ID_PARTICIPACION = :idParticipacion
                     AND SUST.ID_PUESTO_SUSTITUIDO IN (1, 10)
                         AND SUST.TIPO_CAUSA_VACANTE = 4
                         AND SUST.ID_CAUSA_VACANTE in (:causasVacante))
        SELECT id_sustitucion
                    ||','|| id AS id,
                folio
                    ||' - '|| nombre AS value
        FROM
            (SELECT id_sustitucion,
                   id,
                   folio,
                   nombre
            FROM supervisores
            WHERE (:etapa = 1
                    OR (:etapa = 2 AND ID_ZONA_RESPONSABILIDAD_2E IS NOT NULL)))
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaSupervisorTermContrato", query = """
    SELECT ID_ASPIRANTE id, FOLIO ||' - '|| nombre AS value
    FROM (
        SELECT A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION, A.ID_ASPIRANTE, A.nombre, A.FOLIO,
        A.ID_ZONA_RESPONSABILIDAD_1E, A.ID_ZONA_RESPONSABILIDAD_2E, A.ID_SUSTITUCION,
        ROW_NUMBER()
        OVER ( PARTITION BY A.ID_ASPIRANTE ORDER BY A.ID_ASPIRANTE, A.ID_SUSTITUCION DESC) AS row_num
        FROM (
            SELECT
            A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION,
            A.ID_ASPIRANTE AS ID_ASPIRANTE,
            A.APELLIDO_PATERNO || ' ' || A.APELLIDO_MATERNO || ' ' || A.NOMBRE AS nombre,
            A.FOLIO,
            A.ID_ZONA_RESPONSABILIDAD_1E,
            A.ID_ZONA_RESPONSABILIDAD_2E,
            SUST_E1.ID_SUSTITUCION
            FROM SUPYCAP.ASPIRANTES A
            INNER JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST_E1
            ON ( A.ID_DETALLE_PROCESO = SUST_E1.ID_DETALLE_PROCESO
            AND A.ID_PARTICIPACION = SUST_E1.ID_PARTICIPACION
            AND A.ID_ASPIRANTE = SUST_E1.ID_ASPIRANTE_SUSTITUTO
            AND A.ID_ZONA_RESPONSABILIDAD_1E = SUST_E1.ID_ZONA_RESPONSABILIDAD_1E
            AND ID_PUESTO = 10 AND A.ID_ZONA_RESPONSABILIDAD_1E IS NOT NULL
            AND SUST_E1.TIPO_CAUSA_VACANTE = 4 )
            WHERE A.ID_DETALLE_PROCESO = :idDetalle
            AND A.ID_PARTICIPACION = :idParticipacion AND :etapa = 1
            
            UNION ALL
            
            SELECT
            A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION,
            A.ID_ASPIRANTE AS ID_ASPIRANTE,
            A.APELLIDO_PATERNO || ' ' || A.APELLIDO_MATERNO || ' ' || A.NOMBRE AS nombre,
            A.FOLIO,
            A.ID_ZONA_RESPONSABILIDAD_1E,
            A.ID_ZONA_RESPONSABILIDAD_2E,
            SUST.ID_SUSTITUCION
            FROM SUPYCAP.ASPIRANTES A
            INNER JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST
            ON ( A.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
            AND A.ID_PARTICIPACION = SUST.ID_PARTICIPACION
            AND A.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUTO
            AND A.ID_ZONA_RESPONSABILIDAD_2E = SUST.ID_ZONA_RESPONSABILIDAD_2E
            AND ID_PUESTO = 10 AND A.ID_ZONA_RESPONSABILIDAD_2E IS NOT NULL
            AND SUST.TIPO_CAUSA_VACANTE = 4 )
            WHERE A.ID_DETALLE_PROCESO = :idDetalle
            AND A.ID_PARTICIPACION = :idParticipacion AND :etapa = 2
        ) A
        ORDER BY A.ID_SUSTITUCION DESC
    ) WHERE row_num =1
    ORDER BY nombre""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaCapacitador", query = """
        WITH capacitadores AS
                (SELECT ID_DETALLE_PROCESO,
                      ID_PARTICIPACION,
                      ID_ASPIRANTE AS id,
                      APELLIDO_PATERNO
                          || ' ' || APELLIDO_MATERNO
                          || ' ' || NOMBRE AS nombre,
                      FOLIO,
                      ID_AREA_RESPONSABILIDAD_1E,
                      ID_AREA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES
                WHERE ID_DETALLE_PROCESO = :idDetalle
                     AND ID_PARTICIPACION = :idParticipacion
                     AND ID_PUESTO IN (:puestos)
                     AND ESTATUS = 'A')
        SELECT id,
              folio
                ||' - '|| nombre AS value
        FROM
            (SELECT c1.id,
                   c1.folio,
                   c1.nombre
            FROM capacitadores c1
                JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_E1 ON(c1.ID_DETALLE_PROCESO = ARE_E1.ID_DETALLE_PROCESO
                                                            AND c1.ID_PARTICIPACION = ARE_E1.ID_PARTICIPACION
                                                            AND c1.ID_AREA_RESPONSABILIDAD_1E = ARE_E1.ID_AREA_RESPONSABILIDAD
                                                            AND ARE_E1.ETAPA_1 = 0)
            WHERE :etapa = 1
            UNION ALL
            SELECT c2.id,
                   c2.folio,
                   c2.nombre
            FROM capacitadores c2
                JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_E2 ON(c2.ID_DETALLE_PROCESO = ARE_E2.ID_DETALLE_PROCESO
                                                            AND c2.ID_PARTICIPACION = ARE_E2.ID_PARTICIPACION
                                                            AND c2.ID_AREA_RESPONSABILIDAD_2E = ARE_E2.ID_AREA_RESPONSABILIDAD
                                                            AND ARE_E2.ETAPA_2 = 1)
            WHERE :etapa = 2)
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaCapacitadorCM", query = """
        WITH capacitadores AS
                   (SELECT ASP.ID_ASPIRANTE AS id,
                         ASP.APELLIDO_PATERNO
                             || ' ' || ASP.APELLIDO_MATERNO
                             || ' ' || ASP.NOMBRE AS nombre,
                         ASP.FOLIO,
                         SUST.ID_SUSTITUCION,
                         SUST.ID_AREA_RESPONSABILIDAD_1E,
                         SUST.ID_AREA_RESPONSABILIDAD_2E
                   FROM SUPYCAP.ASPIRANTES ASP
                       JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                               AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                               AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                   WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                        AND ASP.ID_PARTICIPACION = :idParticipacion
                        AND SUST.ID_PUESTO_SUSTITUIDO IN (2, 11)
                            AND SUST.TIPO_CAUSA_VACANTE = 4
                            AND SUST.ID_CAUSA_VACANTE in (:causasVacante))
           SELECT id_sustitucion
                       ||','|| id AS id,
                   folio
                       ||' - '|| nombre AS value
           FROM
               (SELECT id_sustitucion,
                      id,
                      folio,
                      nombre
               FROM capacitadores
               WHERE ((:etapa = 1 AND ID_AREA_RESPONSABILIDAD_1E IS NOT NULL)
                       OR (:etapa = 2 AND ID_AREA_RESPONSABILIDAD_2E IS NOT NULL)))
           ORDER BY nombre,
               folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerListaCapacitadorTermContrato", query = """
    SELECT ID_ASPIRANTE id, FOLIO ||' - '|| nombre AS value
    FROM (
        SELECT A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION, A.ID_ASPIRANTE, A.nombre, A.FOLIO,
        A.ID_AREA_RESPONSABILIDAD_1E, A.ID_AREA_RESPONSABILIDAD_2E, A.ID_SUSTITUCION,
        ROW_NUMBER()
        OVER ( PARTITION BY A.ID_ASPIRANTE ORDER BY A.ID_ASPIRANTE, A.ID_SUSTITUCION DESC) AS row_num
        FROM (
            SELECT A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION, A.ID_ASPIRANTE,
            A.APELLIDO_PATERNO || ' ' || A.APELLIDO_MATERNO || ' ' || A.NOMBRE AS nombre,
            A.FOLIO,
            A.ID_AREA_RESPONSABILIDAD_1E,
            A.ID_AREA_RESPONSABILIDAD_2E,
            SUST.ID_SUSTITUCION
            FROM SUPYCAP.ASPIRANTES A
            JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST
            ON(A.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
            AND A.ID_PARTICIPACION = SUST.ID_PARTICIPACION
            AND A.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUTO
            AND A.ID_AREA_RESPONSABILIDAD_1E = SUST.ID_AREA_RESPONSABILIDAD_1E
            AND SUST.TIPO_CAUSA_VACANTE = 4
            AND A.ID_PUESTO = 11  AND A.ID_AREA_RESPONSABILIDAD_1E IS NOT NULL)
            WHERE :etapa = 1 AND A.ID_DETALLE_PROCESO = :idDetalle
            AND A.ID_PARTICIPACION = :idParticipacion
            
            UNION ALL
            
            SELECT A.ID_DETALLE_PROCESO, A.ID_PARTICIPACION, A.ID_ASPIRANTE,
            A.APELLIDO_PATERNO || ' ' || A.APELLIDO_MATERNO || ' ' || A.NOMBRE AS nombre,
            A.FOLIO,
            A.ID_AREA_RESPONSABILIDAD_1E,
            A.ID_AREA_RESPONSABILIDAD_2E,
            SUST.ID_SUSTITUCION
            FROM SUPYCAP.ASPIRANTES A
            JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST
            ON(A.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
            AND A.ID_PARTICIPACION = SUST.ID_PARTICIPACION
            AND A.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUTO
            AND A.ID_AREA_RESPONSABILIDAD_2E = SUST.ID_AREA_RESPONSABILIDAD_2E
            AND SUST.TIPO_CAUSA_VACANTE = 4
            AND A.ID_PUESTO = 11  AND A.ID_AREA_RESPONSABILIDAD_2E IS NOT NULL)
            WHERE :etapa = 2 AND A.ID_DETALLE_PROCESO = :idDetalle
            AND A.ID_PARTICIPACION = :idParticipacion
        ) A
        ORDER BY A.ID_SUSTITUCION DESC
    ) WHERE row_num =1
    ORDER BY nombre""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenListaCESustituidosRescision", query = """
        WITH capacitadores AS
                (SELECT ASP.ID_ASPIRANTE AS id,
                      ASP.APELLIDO_PATERNO
                          || ' ' || ASP.APELLIDO_MATERNO
                          || ' ' || ASP.NOMBRE AS nombre,
                      ASP.FOLIO,
                      SUST.ID_SUSTITUCION,
                      SUST.ID_AREA_RESPONSABILIDAD_1E,
                      SUST.ID_AREA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES ASP
                    JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                            AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                            AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                     AND ASP.ID_PARTICIPACION = :idParticipacion
                     AND ASP.ID_PUESTO IN (12, 9, 13, 5)
                     AND SUST.TIPO_CAUSA_VACANTE != 4)
        SELECT id_sustitucion
                    ||','|| id AS id,
                folio
                    ||' - '|| nombre AS value
        FROM
            (SELECT id_sustitucion,
                   id,
                   folio,
                   nombre
            FROM capacitadores
            WHERE ((:etapa = 1 AND ID_AREA_RESPONSABILIDAD_1E IS NOT NULL)
                    OR (:etapa = 2 AND ID_AREA_RESPONSABILIDAD_2E IS NOT NULL)))
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenListaSESustituidosRescision", query = """
        WITH supervisores AS
                (SELECT ASP.ID_ASPIRANTE AS id,
                      ASP.APELLIDO_PATERNO
                          || ' ' || ASP.APELLIDO_MATERNO
                          || ' ' || ASP.NOMBRE AS nombre,
                      ASP.FOLIO,
                      SUST.ID_SUSTITUCION,
                      SUST.ID_ZONA_RESPONSABILIDAD_1E,
                      SUST.ID_ZONA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES ASP
                    JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                            AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                            AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                     AND ASP.ID_PARTICIPACION = :idParticipacion
                     AND ASP.ID_PUESTO IN (4, 6, 13)
                     AND SUST.TIPO_CAUSA_VACANTE != 4)
        SELECT id_sustitucion
                    ||','|| id AS id,
                folio
                    ||' - '|| nombre AS value
        FROM
            (SELECT id_sustitucion,
                   id,
                   folio,
                   nombre
            FROM supervisores
            WHERE ((:etapa = 1 AND ID_ZONA_RESPONSABILIDAD_1E IS NOT NULL)
                    OR (:etapa = 2 AND ID_ZONA_RESPONSABILIDAD_2E IS NOT NULL)))
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenListaCESustituidos", query = """
        WITH capacitadores AS
                (SELECT ASP.ID_ASPIRANTE AS id,
                      ASP.APELLIDO_PATERNO
                          || ' ' || ASP.APELLIDO_MATERNO
                          || ' ' || ASP.NOMBRE AS nombre,
                      ASP.FOLIO,
                      SUST.ID_SUSTITUCION,
                      SUST.ID_AREA_RESPONSABILIDAD_1E,
                      SUST.ID_AREA_RESPONSABILIDAD_2E
                FROM SUPYCAP.ASPIRANTES ASP
                    JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                            AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                            AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                     AND ASP.ID_PARTICIPACION = :idParticipacion
                     AND SUST.ID_PUESTO_SUSTITUIDO = 2
                     AND SUST.TIPO_CAUSA_VACANTE != 4)
        SELECT id_sustitucion
                    ||','|| id AS id,
                folio
                    ||' - '|| nombre AS value
        FROM
            (SELECT id_sustitucion,
                   id,
                   folio,
                   nombre
            FROM capacitadores
            WHERE ((:etapa = 1 AND ID_AREA_RESPONSABILIDAD_1E IS NOT NULL)
                    OR (:etapa = 2 AND ID_AREA_RESPONSABILIDAD_2E IS NOT NULL)))
        ORDER BY nombre,
            folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenListaSESustituidos", query = """
        WITH supervisores AS
                  (SELECT ASP.ID_ASPIRANTE AS id,
                        ASP.APELLIDO_PATERNO
                            || ' ' || ASP.APELLIDO_MATERNO
                            || ' ' || ASP.NOMBRE AS nombre,
                        ASP.FOLIO,
                        SUST.ID_SUSTITUCION,
                        SUST.ID_ZONA_RESPONSABILIDAD_1E,
                        SUST.ID_ZONA_RESPONSABILIDAD_2E
                  FROM SUPYCAP.ASPIRANTES ASP
                      JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                              AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                              AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                  WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
                       AND ASP.ID_PARTICIPACION = :idParticipacion
                       AND SUST.ID_PUESTO_SUSTITUIDO = 1
                       AND SUST.TIPO_CAUSA_VACANTE != 4)
          SELECT id_sustitucion
                      ||','|| id AS id,
                  folio
                      ||' - '|| nombre AS value
          FROM
              (SELECT id_sustitucion,
                     id,
                     folio,
                     nombre
              FROM supervisores
              WHERE ((:etapa = 1 AND ID_ZONA_RESPONSABILIDAD_1E IS NOT NULL)
                      OR (:etapa = 2 AND ID_ZONA_RESPONSABILIDAD_2E IS NOT NULL)))
          ORDER BY nombre,
              folio""", resultSetMapping = "combo")

@NamedNativeQuery(name = "DTOCCausasVacante.getAspiranteInfo", query = """
        SELECT  ASP.ID_DETALLE_PROCESO AS idDetalleProceso,
                ASP.ID_PARTICIPACION AS idParticipacion,
                ASP.ID_ASPIRANTE AS idAspirante,
                ASP.FOLIO AS folio,
                ASP.CLAVE_ELECTOR_FUAR AS claveElectorFuar,
                ASP.APELLIDO_PATERNO AS apellidoPaterno,
                ASP.APELLIDO_MATERNO AS apellidoMaterno,
                ASP.NOMBRE AS nombre,
                ASP.URL_FOTO AS urlFoto,
                ASP.UID_CUENTA AS uidCuenta,
                ASP.CORREO_CUENTA_CREADA AS correoCtaCreada,
                ASP.CORREO_CUENTA_NOTIFICACION AS correoCtaNotificacion,
                ASP.ID_PUESTO AS idPuesto,
                ASP.DECLINO_CARGO AS declinoCargo,
                ASP.ID_ZONA_RESPONSABILIDAD_1E AS idZonaResponsabilidad,
                ASP.ID_AREA_RESPONSABILIDAD_1E AS idAreaResponsabilidad,
                ASP.ID_ZONA_RESPONSABILIDAD_2E AS idZonaResponsabilidad2e,
                ASP.ID_AREA_RESPONSABILIDAD_2E AS idAreaResponsabilidad2e,
                EC.ID_ESTADO_DOM AS idEstadoAsp,
                EC.PARTICIPO_PROCESO as participoProceso,
                EC.CUAL_PROCESO as cualProceso,
                EC.PARTICIPO_SE as participoSE,
                EC.PARTICIPO_CAE as participoCAE,
                EC.PARTICIPO_OTRO_ESPECIFIQUE as participoOtroEspecifique,
                SUST.FECHA_ALTA AS fecha,
                SUST.OBSERVACIONES AS observaciones,
                CASE WHEN SUST.ID_PUESTO_SUSTITUTO IS NULL THEN ''
                    WHEN SUST.ID_PUESTO_SUSTITUTO = 1 THEN 'Supervisor Electoral'
                    WHEN SUST.ID_PUESTO_SUSTITUTO = 2 THEN 'Capacitador Asistente Electoral'
                    WHEN SUST.ID_PUESTO_SUSTITUTO <> 1
                        AND SUST.ID_PUESTO_SUSTITUTO <> 2
                        AND SUST.ID_PUESTO_SUSTITUTO <> 13 THEN SUST.PUESTO
                    ELSE 'Otro' END cargoAnterior
        FROM SUPYCAP.ASPIRANTES ASP
                JOIN SUPYCAP.EVALUACION_CURRICULAR EC ON (ASP.ID_DETALLE_PROCESO = EC.ID_DETALLE_PROCESO
                                                  AND ASP.ID_PARTICIPACION = EC.ID_PARTICIPACION
                                                  AND ASP.ID_ASPIRANTE = EC.ID_ASPIRANTE)
               LEFT JOIN (SELECT SUST.ID_DETALLE_PROCESO,
                            SUST.ID_PARTICIPACION,
                            SUST.ID_ASPIRANTE_SUSTITUTO,
                            SUST.ID_PUESTO_SUSTITUTO,
                            SUST.FECHA_ALTA,
                            SUST.OBSERVACIONES,
                            CP.PUESTO
                   FROM SUPYCAP.SUSTITUCIONES_SUPYCAP SUST
                        LEFT JOIN SUPYCAP.C_PUESTOS CP ON (SUST.ID_PUESTO_SUSTITUTO = CP.ID_PUESTO)
                   WHERE SUST.ID_DETALLE_PROCESO = :idDetalle
                     AND SUST.ID_PARTICIPACION = :idParticipacion
                     AND SUST.ID_SUSTITUCION = (
                           SELECT MAX(ID_SUSTITUCION) AS ID_SUSTITUCION
                           FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
                           WHERE ID_PUESTO_SUSTITUTO IN (2, 5)
                             AND ID_DETALLE_PROCESO = :idDetalle
                             AND ID_PARTICIPACION = :idParticipacion
                             AND ID_ASPIRANTE_SUSTITUTO = :idAspirante
                           GROUP BY ID_DETALLE_PROCESO,
                                    ID_PARTICIPACION,
                                    ID_ASPIRANTE_SUSTITUTO)) SUST ON (ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                                  AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                                  AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUTO)
        WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
          AND ASP.ID_PARTICIPACION = :idParticipacion
          AND ASP.ID_ASPIRANTE = :idAspirante""", resultSetMapping = "aspirante")

@NamedNativeQuery(name = "DTOCCausasVacante.getDatosInfoSE", query = """
        SELECT (SELECT LISTAGG (NUMERO_AREA_RESPONSABILIDAD, ', ')
                                    WITHIN GROUP (ORDER BY NUMERO_AREA_RESPONSABILIDAD) AS idAreaResponsabilidad
                             FROM (SELECT NUMERO_AREA_RESPONSABILIDAD
                                   FROM SUPYCAP.AREAS_RESPONSABILIDAD
                                   WHERE ID_DETALLE_PROCESO = :idDetalle
                                       AND ID_PARTICIPACION = :idParticipacion
                                       AND ID_ZONA_RESPONSABILIDAD = :idZore)) AS idAreaResponsabilidad,
            ZORE.NUMERO_ZONA_RESPONSABILIDAD AS idZonaResponsabilidad
        FROM SUPYCAP.ASPIRANTES ASP
            JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE ON(ASP.ID_DETALLE_PROCESO = ZORE.ID_DETALLE_PROCESO
                                                AND ASP.ID_PARTICIPACION = ZORE.ID_PARTICIPACION
                                                AND ASP.ID_ZONA_RESPONSABILIDAD_1E = ZORE.ID_ZONA_RESPONSABILIDAD)

        WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
             AND ASP.ID_PARTICIPACION = :idParticipacion
             AND ASP.ID_ASPIRANTE = :idAspirante
             AND :etapa = 1
        UNION ALL
        SELECT DISTINCT (SELECT LISTAGG(NUMERO_AREA_RESPONSABILIDAD, ', ')
                                WITHIN GROUP(ORDER BY NUMERO_AREA_RESPONSABILIDAD) AS idAreaResponsabilidad
                        FROM (SELECT NUMERO_AREA_RESPONSABILIDAD
                             FROM SUPYCAP.AREAS_RESPONSABILIDAD
                             WHERE ID_DETALLE_PROCESO = :idDetalle
                               AND ID_PARTICIPACION = :idParticipacion
                               AND ID_ZONA_RESPONSABILIDAD = :idZore
                               AND ETAPA_2 = 1)) AS idAreaResponsabilidad,
            ZORE.NUMERO_ZONA_RESPONSABILIDAD AS idZonaResponsabilidad
        FROM SUPYCAP.ASPIRANTES ASP
            JOIN SUPYCAP.AREAS_RESPONSABILIDAD AR ON(ASP.ID_DETALLE_PROCESO = AR.ID_DETALLE_PROCESO
                                                     AND ASP.ID_PARTICIPACION = AR.ID_PARTICIPACION
                                                     AND ASP.ID_ZONA_RESPONSABILIDAD_2E = AR.ID_ZONA_RESPONSABILIDAD
                                                     AND AR.ETAPA_2 = 1)
            JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE ON (ZORE.ID_DETALLE_PROCESO = AR.ID_DETALLE_PROCESO
                                                   AND ZORE.ID_PARTICIPACION = AR.ID_PARTICIPACION
                                                   AND ZORE.ID_ZONA_RESPONSABILIDAD = AR.ID_ZONA_RESPONSABILIDAD
                                                   AND AR.ETAPA_2 = 1)
        WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
         AND ASP.ID_PARTICIPACION = :idParticipacion
         AND ASP.ID_ASPIRANTE = :idAspirante
         AND :etapa = 2""", resultSetMapping = "infoSustituido")

@NamedNativeQuery(name = "DTOCCausasVacante.getDatosInfoCAE", query = """
        WITH tiposVoto AS (SELECT ID_AREA_RESPONSABILIDAD,
                                ' - ' || LISTAGG(SIGLAS, ', ')
                                                WITHIN GROUP(ORDER BY ID_TIPO_VOTO) AS TIPO_VOTO
                           FROM (SELECT DISTINCT ARES.ID_AREA_RESPONSABILIDAD,
                                             T.SIGLAS,
                                             T.ID_TIPO_VOTO
                                 FROM SUPYCAP.AREAS_RESPONSABILIDAD ARES
                                     JOIN SUPYCAP.AREAS_SECCIONES ASEC ON(ARES.ID_DETALLE_PROCESO = ASEC.ID_DETALLE_PROCESO
                                                                          AND ARES.ID_PARTICIPACION = ASEC.ID_PARTICIPACION
                                                                          AND ARES.ID_AREA_RESPONSABILIDAD = ASEC.ID_AREA_RESPONSABILIDAD)
                                     JOIN SUPYCAP.C_TIPOS_VOTO T ON (T.ID_TIPO_VOTO = ASEC.ID_TIPO_VOTO)
                                 WHERE ARES.ID_DETALLE_PROCESO = :idDetalle
                                   AND ARES.ID_PARTICIPACION = :idParticipacion
                                   AND T.ID_TIPO_VOTO != 0)
                          GROUP BY ID_AREA_RESPONSABILIDAD)
        SELECT  AR.NUMERO_AREA_RESPONSABILIDAD
                    || TV.TIPO_VOTO AS idAreaResponsabilidad,
             (SELECT ZORE.NUMERO_ZONA_RESPONSABILIDAD
              FROM SUPYCAP.AREAS_RESPONSABILIDAD ARES
                    JOIN SUPYCAP.ASPIRANTES ASP ON(ARES.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
                                                   AND ARES.ID_PARTICIPACION = ASP.ID_PARTICIPACION
                                                   AND ARES.ID_AREA_RESPONSABILIDAD = ASP.ID_AREA_RESPONSABILIDAD_1E)
                    JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE ON(ARES.ID_DETALLE_PROCESO = ZORE.ID_DETALLE_PROCESO
                                                   AND ARES.ID_PARTICIPACION = ZORE.ID_PARTICIPACION
                                                   AND ARES.ID_ZONA_RESPONSABILIDAD = ZORE.ID_ZONA_RESPONSABILIDAD)
              WHERE ARES.ID_DETALLE_PROCESO = :idDetalle
                    AND ARES.ID_PARTICIPACION = :idParticipacion
                    AND ASP.ID_ASPIRANTE = :idAspirante) AS idZonaResponsabilidad
        FROM SUPYCAP.ASPIRANTES ASP
            JOIN SUPYCAP.AREAS_RESPONSABILIDAD AR ON(ASP.ID_DETALLE_PROCESO = AR.ID_DETALLE_PROCESO
                                                   AND ASP.ID_PARTICIPACION = AR.ID_PARTICIPACION
                                                   AND ASP.ID_AREA_RESPONSABILIDAD_1E = AR.ID_AREA_RESPONSABILIDAD
                                                   AND AR.ETAPA_1 = 0)
            LEFT JOIN tiposVoto TV ON (AR.ID_AREA_RESPONSABILIDAD = TV.ID_AREA_RESPONSABILIDAD)
        WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
            AND ASP.ID_PARTICIPACION = :idParticipacion
            AND ASP.ID_ASPIRANTE = :idAspirante
            AND :etapa = 1
        UNION ALL
        SELECT AR.NUMERO_AREA_RESPONSABILIDAD
                    || TV.TIPO_VOTO AS idAreaResponsabilidad,
             (SELECT ZORE.NUMERO_ZONA_RESPONSABILIDAD
              FROM SUPYCAP.AREAS_RESPONSABILIDAD ARES
               JOIN SUPYCAP.ASPIRANTES ASP ON(ARES.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
                                               AND ARES.ID_PARTICIPACION = ASP.ID_PARTICIPACION
                                               AND ARES.ID_AREA_RESPONSABILIDAD = ASP.ID_AREA_RESPONSABILIDAD_2E
                                               AND ARES.ETAPA_2 = 1)
               JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE ON(ARES.ID_DETALLE_PROCESO = ZORE.ID_DETALLE_PROCESO
                                               AND ARES.ID_PARTICIPACION = ZORE.ID_PARTICIPACION
                                               AND ARES.ID_ZONA_RESPONSABILIDAD = ZORE.ID_ZONA_RESPONSABILIDAD
                                               AND ARES.ETAPA_2 = 1)
              WHERE ARES.ID_DETALLE_PROCESO = :idDetalle
                AND ARES.ID_PARTICIPACION = :idParticipacion
                AND ASP.ID_ASPIRANTE = :idAspirante) AS idZonaResponsabilidad
        FROM SUPYCAP.ASPIRANTES ASP
            JOIN SUPYCAP.AREAS_RESPONSABILIDAD AR ON(ASP.ID_DETALLE_PROCESO = AR.ID_DETALLE_PROCESO
                                                     AND ASP.ID_PARTICIPACION = AR.ID_PARTICIPACION
                                                     AND ASP.ID_AREA_RESPONSABILIDAD_2E = AR.ID_AREA_RESPONSABILIDAD
                                                     AND AR.ETAPA_2 = 1)
            LEFT JOIN tiposVoto TV ON (AR.ID_AREA_RESPONSABILIDAD = TV.ID_AREA_RESPONSABILIDAD)
        WHERE ASP.ID_DETALLE_PROCESO = :idDetalle
             AND ASP.ID_PARTICIPACION = :idParticipacion
             AND ASP.ID_ASPIRANTE = :idAspirante
             AND :etapa = 2""", resultSetMapping = "infoSustituido")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerPendientesAdmin", query = """
        SELECT id_sustitucion
                    || ',' || id AS id,
                value AS value,
                'Vacante por ' || causa
                    || ' en ' || zona AS causa
        FROM
          (SELECT ASP.ID_ASPIRANTE AS id,
                  ASP.FOLIO
                        || ' - ' || ASP.APELLIDO_PATERNO
                        || ' ' || ASP.APELLIDO_MATERNO
                        || ' ' || ASP.NOMBRE AS value,
                  SUST.ID_SUSTITUCION,
                  CV.DESCRIPCION AS causa,
                  CASE WHEN SUST.ID_AREA_RESPONSABILIDAD_2E IS NULL
                        THEN 'ARE '|| ZORE_1E.NUMERO_ZONA_RESPONSABILIDAD
                        ELSE 'ARE '|| ZORE_2E.NUMERO_ZONA_RESPONSABILIDAD
                  END zona
           FROM SUPYCAP.ASPIRANTES ASP
                JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                     AND ASP.ID_PARTICIPACION= SUST.ID_PARTICIPACION
                                                     AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                JOIN SUPYCAP.C_CAUSAS_VACANTE CV ON (SUST.ID_CAUSA_VACANTE = CV.ID_CAUSA_VACANTE
                                                     AND SUST.TIPO_CAUSA_VACANTE = CV.TIPO_CAUSA_VACANTE)
                LEFT JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_1E ON (SUST.ID_DETALLE_PROCESO = ZORE_1E.ID_DETALLE_PROCESO
                                                     AND SUST.ID_PARTICIPACION = ZORE_1E.ID_PARTICIPACION
                                                     AND SUST.ID_ZONA_RESPONSABILIDAD_1E = ZORE_1E.ID_ZONA_RESPONSABILIDAD)
                LEFT JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_2E ON (SUST.ID_DETALLE_PROCESO = ZORE_2E.ID_DETALLE_PROCESO
                                                     AND SUST.ID_PARTICIPACION = ZORE_2E.ID_PARTICIPACION
                                                     AND SUST.ID_ZONA_RESPONSABILIDAD_2E = ZORE_2E.ID_ZONA_RESPONSABILIDAD)
           WHERE SUST.ID_DETALLE_PROCESO = :idDetalle
             AND SUST.ID_PARTICIPACION = :idParticipacion
             AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
             AND SUST.TIPO_CAUSA_VACANTE IN (1, 4)
             AND ASP.ID_PUESTO IN (10, 8)
           UNION ALL
           SELECT ASP.ID_ASPIRANTE AS id,
                    ASP.FOLIO
                        || ' - ' || ASP.APELLIDO_PATERNO
                        || ' ' || ASP.APELLIDO_MATERNO
                        || ' ' || ASP.NOMBRE AS value,
                    SUST.ID_SUSTITUCION,
                    CV.DESCRIPCION AS causa,
                    CASE WHEN SUST.ID_ZONA_RESPONSABILIDAD_2E IS NULL
                        THEN 'ZORE '|| ARE_1E.NUMERO_AREA_RESPONSABILIDAD
                        ELSE 'ZORE '|| ARE_2E.NUMERO_AREA_RESPONSABILIDAD
                    END zona
           FROM SUPYCAP.ASPIRANTES ASP
                JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO= SUST.ID_DETALLE_PROCESO
                                                        AND ASP.ID_PARTICIPACION= SUST.ID_PARTICIPACION
                                                        AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                JOIN SUPYCAP.C_CAUSAS_VACANTE CV ON (CV.ID_CAUSA_VACANTE = SUST.ID_CAUSA_VACANTE
                                                      AND CV.TIPO_CAUSA_VACANTE = SUST.TIPO_CAUSA_VACANTE)
                LEFT JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_1E ON (SUST.ID_DETALLE_PROCESO = ARE_1E.ID_DETALLE_PROCESO
                                                        AND SUST.ID_PARTICIPACION = ARE_1E.ID_PARTICIPACION
                                                        AND SUST.ID_AREA_RESPONSABILIDAD_1E = ARE_1E.ID_AREA_RESPONSABILIDAD)
                LEFT JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_2E ON (SUST.ID_DETALLE_PROCESO = ARE_2E.ID_DETALLE_PROCESO
                                                        AND SUST.ID_PARTICIPACION = ARE_2E.ID_PARTICIPACION
                                                        AND SUST.ID_AREA_RESPONSABILIDAD_2E = ARE_2E.ID_AREA_RESPONSABILIDAD)
           WHERE SUST.ID_DETALLE_PROCESO = :idDetalle
             AND SUST.ID_PARTICIPACION = :idParticipacion
             AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
             AND SUST.TIPO_CAUSA_VACANTE IN (1, 4)
             AND ASP.ID_PUESTO = 7)
        ORDER BY value ASC""", resultSetMapping = "comboPendientes")

@NamedNativeQuery(name = "DTOCCausasVacante.obtenerPendientesSustSeCae", query = """
        SELECT id_sustitucion
                    || ',' || id AS id,
                value,
                'Vacante por ' || causa
                    || ' en ' || zona AS causa
        FROM (SELECT ASP.ID_ASPIRANTE AS id,
                  ASP.FOLIO
                        || ' - ' || ASP.APELLIDO_PATERNO
                        || ' ' || ASP.APELLIDO_MATERNO
                        || ' ' || ASP.NOMBRE AS value,
                  SUST.ID_SUSTITUCION,
                  CV.DESCRIPCION AS causa,
                  CASE WHEN SUST.ID_ZONA_RESPONSABILIDAD_2E IS NULL
                        THEN 'ZORE '||ZORE_1E.NUMERO_ZONA_RESPONSABILIDAD
                        ELSE 'ZORE '||ZORE_2E.NUMERO_ZONA_RESPONSABILIDAD END zona
            FROM SUPYCAP.ASPIRANTES ASP
                JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                    AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                    AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                JOIN SUPYCAP.C_CAUSAS_VACANTE CV ON (SUST.ID_CAUSA_VACANTE = CV.ID_CAUSA_VACANTE
                                                    AND SUST.TIPO_CAUSA_VACANTE = CV.TIPO_CAUSA_VACANTE)
                LEFT JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_1E ON (SUST.ID_DETALLE_PROCESO = ZORE_1E.ID_DETALLE_PROCESO
                                                    AND SUST.ID_PARTICIPACION = ZORE_1E.ID_PARTICIPACION
                                                    AND SUST.ID_ZONA_RESPONSABILIDAD_1E = ZORE_1E.ID_ZONA_RESPONSABILIDAD)
                LEFT JOIN SUPYCAP.ZONAS_RESPONSABILIDAD ZORE_2E ON (SUST.ID_DETALLE_PROCESO = ZORE_2E.ID_DETALLE_PROCESO
                                                    AND SUST.ID_PARTICIPACION = ZORE_2E.ID_PARTICIPACION
                                                    AND SUST.ID_ZONA_RESPONSABILIDAD_2E = ZORE_2E.ID_ZONA_RESPONSABILIDAD)
            WHERE SUST.ID_DETALLE_PROCESO = :idDetalle
              AND SUST.ID_PARTICIPACION = :idParticipacion
              AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
              AND SUST.TIPO_CAUSA_VACANTE != 4
              AND SUST.ID_PUESTO_SUSTITUIDO = 1
              AND ASP.ID_PUESTO IN (3, 4, 6, 13)
            UNION ALL
            SELECT ASP.ID_ASPIRANTE AS id,
                  ASP.FOLIO
                        || ' - ' || ASP.APELLIDO_PATERNO
                        || ' ' || ASP.APELLIDO_MATERNO
                        || ' ' || ASP.NOMBRE AS value,
                  SUST.ID_SUSTITUCION,
                  CV.DESCRIPCION AS causa,
                  CASE WHEN SUST.ID_AREA_RESPONSABILIDAD_2E IS NULL
                        THEN 'ARE '||ARE_1E.NUMERO_AREA_RESPONSABILIDAD
                        ELSE 'ARE '||ARE_2E.NUMERO_AREA_RESPONSABILIDAD END zona
            FROM SUPYCAP.ASPIRANTES ASP
                JOIN SUPYCAP.SUSTITUCIONES_SUPYCAP SUST ON(ASP.ID_DETALLE_PROCESO = SUST.ID_DETALLE_PROCESO
                                                    AND ASP.ID_PARTICIPACION = SUST.ID_PARTICIPACION
                                                    AND ASP.ID_ASPIRANTE = SUST.ID_ASPIRANTE_SUSTITUIDO)
                JOIN SUPYCAP.C_CAUSAS_VACANTE CV ON (SUST.ID_CAUSA_VACANTE = CV.ID_CAUSA_VACANTE
                                                    AND SUST.TIPO_CAUSA_VACANTE = CV.TIPO_CAUSA_VACANTE)
                LEFT JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_1E ON (SUST.ID_DETALLE_PROCESO = ARE_1E.ID_DETALLE_PROCESO
                                                    AND SUST.ID_PARTICIPACION = ARE_1E.ID_PARTICIPACION
                                                    AND SUST.ID_AREA_RESPONSABILIDAD_1E = ARE_1E.ID_AREA_RESPONSABILIDAD)
                LEFT JOIN SUPYCAP.AREAS_RESPONSABILIDAD ARE_2E ON (SUST.ID_DETALLE_PROCESO = ARE_2E.ID_DETALLE_PROCESO
                                                    AND SUST.ID_PARTICIPACION = ARE_2E.ID_PARTICIPACION
                                                    AND SUST.ID_AREA_RESPONSABILIDAD_2E = ARE_2E.ID_AREA_RESPONSABILIDAD)
            WHERE SUST.ID_DETALLE_PROCESO = :idDetalle
              AND SUST.ID_PARTICIPACION = :idParticipacion
              AND SUST.ID_ASPIRANTE_SUSTITUTO IS NULL
              AND SUST.TIPO_CAUSA_VACANTE != 4
              AND SUST.ID_PUESTO_SUSTITUIDO = 2
              AND ASP.ID_PUESTO IN (1, 3, 5, 9, 12, 13))
        ORDER BY value ASC""", resultSetMapping = "comboPendientes")

@Entity
@Table(schema = "SUPYCAP", name = "C_CAUSAS_VACANTE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCCausasVacante implements Serializable {

    @Serial
    private static final long serialVersionUID = 7088329901411762532L;

    @EmbeddedId
    private DTOCCausasVacanteId id;

    @Column(name = "DESCRIPCION", nullable = false, precision = 150, scale = 0)
    private String descripcion;

}
