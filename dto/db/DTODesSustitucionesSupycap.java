package mx.ine.sustseycae.dto.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOConsultaDesSustitucionesSupycap;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@SqlResultSetMapping(name = "consultaDesSustituciones", classes = {
        @ConstructorResult(targetClass = VOConsultaDesSustitucionesSupycap.class, columns = {
                @ColumnResult(name = "id_sustitucion", type = Integer.class),
                @ColumnResult(name = "id_relacion_sustituciones", type = String.class),
                @ColumnResult(name = "id_causa", type = Integer.class),
                @ColumnResult(name = "tipo_causa", type = Integer.class),
                @ColumnResult(name = "id_aspirante_sustituido", type = Integer.class),
                @ColumnResult(name = "id_aspirante_sustituto", type = Integer.class),
                @ColumnResult(name = "puesto_sustituido", type = String.class),
                @ColumnResult(name = "nombre_sustituido", type = String.class),
                @ColumnResult(name = "causa", type = String.class),
                @ColumnResult(name = "fecha_baja", type = String.class),
                @ColumnResult(name = "fecha_alta", type = String.class),
                @ColumnResult(name = "fecha_sustitucion", type = String.class),
                @ColumnResult(name = "puesto_sustituto", type = String.class),
                @ColumnResult(name = "nombre_sustituto", type = String.class),
                @ColumnResult(name = "sustituciones_posteriores", type = Boolean.class),

        })
})

@NamedNativeQuery(name = "DTODesSustitucionesSupycap.consultaDesSustituciones", query = """
         SELECT ID_SUSTITUCION,
                       ID_RELACION_SUSTITUCIONES,
                       ID_CAUSA,
                       TIPO_CAUSA,
                       ID_ASPIRANTE_SUSTITUIDO,
                       ID_ASPIRANTE_SUSTITUTO,
                       PUESTO_SUSTITUIDO,
                       NOMBRE_SUSTITUIDO,
                       CAUSA,
                       FECHA_BAJA,
                       FECHA_ALTA,
                       FECHA_SUSTITUCION,
                       NOMBRE_SUSTITUTO,
                       PUESTO_SUSTITUTO,
                       SUSTITUCIONES_POSTERIORES
                FROM (
                    SELECT
                        SUS.ID_SUSTITUCION AS ID_SUSTITUCION,
                        SUS.ID_RELACION_SUSTITUCIONES AS ID_RELACION_SUSTITUCIONES,
                        SUS.ID_CAUSA_VACANTE AS ID_CAUSA,
                        SUS.TIPO_CAUSA_VACANTE AS TIPO_CAUSA,
                        SUS.ID_ASPIRANTE_SUSTITUIDO AS ID_ASPIRANTE_SUSTITUIDO,
                        SUS.ID_ASPIRANTE_SUSTITUTO AS ID_ASPIRANTE_SUSTITUTO,
                        pustituido.puesto AS PUESTO_SUSTITUIDO,
                        COALESCE(ASP.APELLIDO_PATERNO, '') || ' ' ||
                        COALESCE(ASP.APELLIDO_MATERNO, '') || ' ' ||
                        COALESCE(ASP.NOMBRE, '') AS NOMBRE_SUSTITUIDO,
                        CAU.DESCRIPCION AS CAUSA,
                        REPLACE (TO_CHAR(FECHA_BAJA, 'DD/MM/YYYY'), ' ', '') AS FECHA_BAJA,
                        REPLACE (TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY'), ' ', '') AS FECHA_ALTA,
                        REPLACE (TO_CHAR(FECHA_SUSTITUCION, 'DD/MM/YYYY'), ' ', '') AS FECHA_SUSTITUCION,
                        COALESCE(ASP_SUST.APELLIDO_PATERNO, '') || ' ' ||
                        COALESCE(ASP_SUST.APELLIDO_MATERNO, '') || ' ' ||
                        COALESCE(ASP_SUST.NOMBRE, '') AS NOMBRE_SUSTITUTO,
                        pustituto.puesto AS PUESTO_SUSTITUTO,

                        CASE
                        WHEN EXISTS (
                            SELECT 1
                            FROM SUPYCAP.SUSTITUCIONES_SUPYCAP S2
                            WHERE
                                S2.ID_PROCESO_ELECTORAL = SUS.ID_PROCESO_ELECTORAL
                                AND S2.ID_DETALLE_PROCESO = SUS.ID_DETALLE_PROCESO
                                AND S2.ID_PARTICIPACION = SUS.ID_PARTICIPACION
                                AND S2.FECHA_MOVIMIENTO > SUS.FECHA_MOVIMIENTO
                                AND (
                                    S2.ID_ASPIRANTE_SUSTITUIDO = SUS.ID_ASPIRANTE_SUSTITUIDO
                                    OR S2.ID_ASPIRANTE_SUSTITUTO = SUS.ID_ASPIRANTE_SUSTITUIDO
                                    OR S2.ID_ASPIRANTE_SUSTITUIDO = SUS.ID_ASPIRANTE_SUSTITUTO
                                    OR S2.ID_ASPIRANTE_SUSTITUTO = SUS.ID_ASPIRANTE_SUSTITUTO
                                )
                                AND S2.ID_RELACION_SUSTITUCIONES <> SUS.ID_RELACION_SUSTITUCIONES
                        ) THEN 1
                        ELSE 0
                    END AS SUSTITUCIONES_POSTERIORES,

                    ROW_NUMBER() OVER (PARTITION BY id_relacion_sustituciones ORDER BY fecha_movimiento, id_sustitucion) AS rn,
                    MAX(fecha_movimiento) OVER (PARTITION BY id_relacion_sustituciones) AS group_key

                    FROM SUPYCAP.SUSTITUCIONES_SUPYCAP SUS
                    LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP ON (
                        SUS.ID_ASPIRANTE_SUSTITUIDO = ASP.ID_ASPIRANTE
                        AND SUS.ID_PROCESO_ELECTORAL = ASP.ID_PROCESO_ELECTORAL
                        AND SUS.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
                        AND SUS.ID_PARTICIPACION = ASP.ID_PARTICIPACION
                    )
                    LEFT OUTER JOIN SUPYCAP.C_CAUSAS_VACANTE CAU ON (
                        SUS.ID_CAUSA_VACANTE = CAU.ID_CAUSA_VACANTE
                        AND SUS.TIPO_CAUSA_VACANTE = CAU.TIPO_CAUSA_VACANTE
                    )
                    LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituido ON (SUS.ID_PUESTO_SUSTITUIDO = pustituido.ID_PUESTO)
                    LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituto ON (SUS.ID_PUESTO_SUSTITUTO = pustituto.ID_PUESTO)
                    LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP_SUST ON (
                        SUS.ID_ASPIRANTE_SUSTITUTO = ASP_SUST.ID_ASPIRANTE
                        AND SUS.ID_PROCESO_ELECTORAL = ASP_SUST.ID_PROCESO_ELECTORAL
                        AND SUS.ID_DETALLE_PROCESO = ASP_SUST.ID_DETALLE_PROCESO
                        AND SUS.ID_PARTICIPACION = ASP_SUST.ID_PARTICIPACION
                    )
                    WHERE SUS.ID_PROCESO_ELECTORAL = :idProcesoElectoral
                      AND SUS.ID_DETALLE_PROCESO = :idDetalleProceso
                      AND SUS.ID_PARTICIPACION = :idParticipacion
                )
                ORDER BY group_key, rn, id_sustitucion
                """, resultSetMapping = "consultaDesSustituciones")

@NamedNativeQuery(name = "DTODesSustitucionesSupycap.consultaSustitucionesDeshechas", query = """
        SELECT DESUS.ID_SUSTITUCION AS ID_SUSTITUCION,
                DESUS.ID_RELACION_SUSTITUCIONES AS ID_RELACION_SUSTITUCIONES,
                DESUS.ID_CAUSA_VACANTE AS ID_CAUSA,
                DESUS.TIPO_CAUSA_VACANTE TIPO_CAUSA,
                DESUS.ID_ASPIRANTE_SUSTITUIDO AS ID_ASPIRANTE_SUSTITUIDO,
                DESUS.ID_ASPIRANTE_SUSTITUTO AS ID_ASPIRANTE_SUSTITUTO,
                pustituido.puesto AS PUESTO_SUSTITUIDO,
                COALESCE(ASP.APELLIDO_PATERNO, '') || ' ' ||
                COALESCE(ASP.APELLIDO_MATERNO, '') || ' ' ||
                COALESCE(ASP.NOMBRE, '') AS NOMBRE_SUSTITUIDO,
                CAU.DESCRIPCION AS CAUSA,
                REPLACE ((TO_CHAR (FECHA_BAJA, 'DD/MM/YYYY')), ' ', '') AS FECHA_BAJA,
                REPLACE ((TO_CHAR (FECHA_ALTA, 'DD/MM/YYYY')), ' ', '') AS FECHA_ALTA,
                REPLACE ((TO_CHAR (FECHA_SUSTITUCION, 'DD/MM/YYYY')), ' ', '') AS FECHA_SUSTITUCION,
                COALESCE(ASP_SUST.APELLIDO_PATERNO, '') || ' ' ||
                COALESCE(ASP_SUST.APELLIDO_MATERNO, '') || ' ' ||
                COALESCE(ASP_SUST.NOMBRE, '') AS NOMBRE_SUSTITUTO,
                pustituto.puesto AS PUESTO_SUSTITUTO, 0 AS SUSTITUCIONES_POSTERIORES
        FROM SUPYCAP.DES_SUSTITUCIONES_SUPYCAP DESUS
        LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP ON(DESUS.ID_ASPIRANTE_SUSTITUIDO = ASP.ID_ASPIRANTE
                                                AND DESUS.ID_PROCESO_ELECTORAL = ASP.ID_PROCESO_ELECTORAL
                                                AND DESUS.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
                                                AND DESUS.ID_PARTICIPACION = ASP.ID_PARTICIPACION)
        LEFT OUTER JOIN SUPYCAP.C_CAUSAS_VACANTE CAU ON(DESUS.ID_CAUSA_VACANTE = CAU.ID_CAUSA_VACANTE
                                                AND DESUS.TIPO_CAUSA_VACANTE = CAU.TIPO_CAUSA_VACANTE)
        LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP_SUST ON(DESUS.ID_ASPIRANTE_SUSTITUTO = ASP_SUST.ID_ASPIRANTE
                                                AND DESUS.ID_PROCESO_ELECTORAL = ASP_SUST.ID_PROCESO_ELECTORAL
                                                AND DESUS.ID_DETALLE_PROCESO = ASP_SUST.ID_DETALLE_PROCESO
                                                AND DESUS.ID_PARTICIPACION = ASP_SUST.ID_PARTICIPACION)
        LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituido ON (DESUS.id_puesto_sustituido = pustituido.id_puesto)
        LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituto ON (DESUS.id_puesto_sustituto = pustituto.id_puesto)
        WHERE DESUS.ID_PROCESO_ELECTORAL = :idProcesoElectoral
                AND DESUS.ID_DETALLE_PROCESO = :idDetalleProceso
                AND DESUS.ID_PARTICIPACION = :idParticipacion
        ORDER BY DESUS.ID_DESHACER""", resultSetMapping = "consultaDesSustituciones")

@NamedNativeQuery(name = "DTODesSustitucionesSupycap.consultaDesSustitucionesRelacionadas", query = """
        SELECT SUS.ID_SUSTITUCION AS ID_SUSTITUCION,
                SUS.ID_RELACION_SUSTITUCIONES AS ID_RELACION_SUSTITUCIONES,
                SUS.ID_CAUSA_VACANTE AS ID_CAUSA,
                SUS.TIPO_CAUSA_VACANTE AS TIPO_CAUSA,
                SUS.ID_ASPIRANTE_SUSTITUIDO AS ID_ASPIRANTE_SUSTITUIDO,
                SUS.ID_ASPIRANTE_SUSTITUTO AS ID_ASPIRANTE_SUSTITUTO,
                pustituido.puesto AS PUESTO_SUSTITUIDO,
                COALESCE(ASP.APELLIDO_PATERNO, '') || ' ' ||
                COALESCE(ASP.APELLIDO_MATERNO, '') || ' ' ||
                COALESCE(ASP.NOMBRE, '') AS NOMBRE_SUSTITUIDO,
                CAU.DESCRIPCION AS CAUSA,
                REPLACE ((TO_CHAR (FECHA_BAJA, 'DD/MM/YYYY')), ' ', '') AS FECHA_BAJA,
                REPLACE ((TO_CHAR (FECHA_ALTA, 'DD/MM/YYYY')),' ', '') AS FECHA_ALTA,
                REPLACE ((TO_CHAR (FECHA_SUSTITUCION, 'DD/MM/YYYY')),' ', '') AS FECHA_SUSTITUCION,
                COALESCE(ASP_SUST.APELLIDO_PATERNO, '') || ' ' ||
                COALESCE(ASP_SUST.APELLIDO_MATERNO, '') || ' ' ||
                COALESCE(ASP_SUST.NOMBRE, '') AS NOMBRE_SUSTITUTO,
                pustituto.puesto AS puesto_sustituto, 0 AS SUSTITUCIONES_POSTERIORES
        FROM SUPYCAP.SUSTITUCIONES_SUPYCAP SUS
        LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP ON(SUS.ID_ASPIRANTE_SUSTITUIDO = ASP.ID_ASPIRANTE
                                        AND SUS.ID_PROCESO_ELECTORAL = ASP.ID_PROCESO_ELECTORAL
                                        AND SUS.ID_DETALLE_PROCESO = ASP.ID_DETALLE_PROCESO
                                        AND SUS.ID_PARTICIPACION = ASP.ID_PARTICIPACION)
        LEFT OUTER JOIN SUPYCAP.C_CAUSAS_VACANTE CAU ON(SUS.ID_CAUSA_VACANTE = CAU.ID_CAUSA_VACANTE
                                                AND SUS.TIPO_CAUSA_VACANTE = CAU.TIPO_CAUSA_VACANTE)
        LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituido on (sus.id_puesto_sustituido = pustituido.id_puesto)
        LEFT OUTER JOIN SUPYCAP.C_PUESTOS pustituto on (sus.id_puesto_sustituto = pustituto.id_puesto)
        LEFT OUTER JOIN SUPYCAP.ASPIRANTES ASP_SUST ON(SUS.ID_ASPIRANTE_SUSTITUTO = ASP_SUST.ID_ASPIRANTE
                                                AND SUS.ID_PROCESO_ELECTORAL = ASP_SUST.ID_PROCESO_ELECTORAL
                                                AND SUS.ID_DETALLE_PROCESO = ASP_SUST.ID_DETALLE_PROCESO
                                                AND SUS.ID_PARTICIPACION = ASP_SUST.ID_PARTICIPACION)
        WHERE SUS.ID_PROCESO_ELECTORAL = :idProcesoElectoral
                AND SUS.ID_DETALLE_PROCESO = :idDetalleProceso
                AND SUS.ID_PARTICIPACION = :idParticipacion
                AND sus.id_relacion_sustituciones = :idRelacionSustituciones
        ORDER BY SUS.ID_SUSTITUCION""", resultSetMapping = "consultaDesSustituciones")

@NamedNativeQuery(name = "DTODesSustitucionesSupycap.insertarDeshacerSustitucion", query = """
            INSERT INTO SUPYCAP.DES_SUSTITUCIONES_SUPYCAP (
                ID_DETALLE_PROCESO,
                ID_PARTICIPACION,
                ID_DESHACER,
                ID_PROCESO_ELECTORAL,
                ID_SUSTITUCION,
                ID_RELACION_SUSTITUCIONES,
                ID_ASPIRANTE_SUSTITUIDO,
                ID_PUESTO_SUSTITUIDO,
                CORREO_CTA_CREADA_SUSTITUIDO,
                CORREO_CTA_NOTIF_SUSTITUIDO,
                ID_ASPIRANTE_SUSTITUTO,
                ID_PUESTO_SUSTITUTO,
                CORREO_CTA_CREADA_SUSTITUTO,
                CORREO_CTA_NOTIF_SUSTITUTO,
                ID_CAUSA_VACANTE,
                TIPO_CAUSA_VACANTE,
                FECHA_BAJA,
                FECHA_ALTA,
                FECHA_SUSTITUCION,
                ID_AREA_RESPONSABILIDAD_1E,
                ID_ZONA_RESPONSABILIDAD_1E,
                ID_AREA_RESPONSABILIDAD_2E,
                ID_ZONA_RESPONSABILIDAD_2E,
                UID_CUENTA_SUSTITUTO,
                UID_CUENTA_SUSTITUIDO,
                DECLINO_CARGO,
                ETAPA,
                IP_USUARIO,
                USUARIO,
                FECHA_HORA
            )
            SELECT
                S.ID_DETALLE_PROCESO,
                S.ID_PARTICIPACION,
                (
                    SELECT NVL(MAX(DS.ID_DESHACER), 0) + 1
                    FROM SUPYCAP.DES_SUSTITUCIONES_SUPYCAP DS
                    WHERE DS.ID_DETALLE_PROCESO = S.ID_DETALLE_PROCESO
                      AND DS.ID_PARTICIPACION = S.ID_PARTICIPACION
                ) AS ID_DESHACER,
                S.ID_PROCESO_ELECTORAL,
                S.ID_SUSTITUCION,
                S.ID_RELACION_SUSTITUCIONES,
                S.ID_ASPIRANTE_SUSTITUIDO,
                S.ID_PUESTO_SUSTITUIDO,
                S.CORREO_CTA_CREADA_SUSTITUIDO,
                S.CORREO_CTA_NOTIF_SUSTITUIDO,
                S.ID_ASPIRANTE_SUSTITUTO,
                S.ID_PUESTO_SUSTITUTO,
                S.CORREO_CTA_CREADA_SUSTITUTO,
                S.CORREO_CTA_NOTIF_SUSTITUTO,
                S.ID_CAUSA_VACANTE,
                S.TIPO_CAUSA_VACANTE,
                S.FECHA_BAJA,
                S.FECHA_ALTA,
                S.FECHA_SUSTITUCION,
                S.ID_AREA_RESPONSABILIDAD_1E,
                S.ID_ZONA_RESPONSABILIDAD_1E,
                S.ID_AREA_RESPONSABILIDAD_2E,
                S.ID_ZONA_RESPONSABILIDAD_2E,
                S.UID_CUENTA_SUSTITUTO,
                S.UID_CUENTA_SUSTITUIDO,
                S.DECLINO_CARGO,
                :etapa AS ETAPA,
                :ipUsuario AS IP_USUARIO,
                :usuario AS USUARIO,
                SYSDATE AS FECHA_HORA
            FROM SUPYCAP.SUSTITUCIONES_SUPYCAP S
            WHERE S.ID_SUSTITUCION = :idSustitucion
              AND S.ID_DETALLE_PROCESO = :idDetalleProceso
              AND S.ID_PARTICIPACION = :idParticipacion
        """)

@Entity
@Table(schema = "SUPYCAP", name = "DES_SUSTITUCIONES_SUPYCAP")
@Cacheable(true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTODesSustitucionesSupycap implements Serializable {

    @Serial
    private static final long serialVersionUID = 1084133946625558098L;

    @EmbeddedId
    private DTODesSustitucionesSupycapId id;

    @NotNull
    @Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
    private Integer idProcesoElectoral;

    @NotNull
    @Column(name = "ID_SUSTITUCION", nullable = false, precision = 5, scale = 0)
    private Integer idSustitucion;

    @NotNull
    @Column(name = "ID_RELACION_SUSTITUCIONES", nullable = false, precision = 36, scale = 0)
    private String idRelacionSustituciones;

    @Column(name = "ID_ASPIRANTE_SUSTITUIDO", nullable = true, precision = 6, scale = 0)
    private Integer idAspiranteSutituido;

    @Column(name = "ID_PUESTO_SUSTITUIDO", nullable = false, precision = 2, scale = 0)
    private Integer idPuestoSustituido;

    @Column(name = "CORREO_CTA_CREADA_SUSTITUIDO", nullable = true, precision = 60, scale = 0)
    private String correoCtaCreadaSustituido;

    @Column(name = "CORREO_CTA_NOTIF_SUSTITUIDO", nullable = true, precision = 60, scale = 0)
    private String correoCtaNotifSustituido;

    @Column(name = "ID_ASPIRANTE_SUSTITUTO", nullable = true, precision = 6, scale = 0)
    private Integer idAspiranteSutituto;

    @Column(name = "ID_PUESTO_SUSTITUTO", nullable = true, precision = 2, scale = 0)
    private Integer idPuestoSustituto;

    @Column(name = "CORREO_CTA_CREADA_SUSTITUTO", nullable = true, precision = 60, scale = 0)
    private String correoCtaCreadaSustituto;

    @Column(name = "CORREO_CTA_NOTIF_SUSTITUTO", nullable = true, precision = 60, scale = 0)
    private String correoCtaNotifSustituto;

    @Column(name = "ID_CAUSA_VACANTE", nullable = false, precision = 2, scale = 0)
    private Integer idCausaVacante;

    @Column(name = "TIPO_CAUSA_VACANTE", nullable = false, precision = 2, scale = 0)
    private Integer tipoCausaVacante;

    @Column(name = "FECHA_BAJA", nullable = true)
    private Date fechaBaja;

    @Column(name = "FECHA_ALTA", nullable = true)
    private Date fechaAlta;

    @Column(name = "FECHA_SUSTITUCION", nullable = true)
    private Date fechaSustitucion;

    @Column(name = "ID_AREA_RESPONSABILIDAD_1E", nullable = true, precision = 5, scale = 0)
    private Integer idAreaResponsabilidad1e;

    @Column(name = "ID_ZONA_RESPONSABILIDAD_1E", nullable = true, precision = 5, scale = 0)
    private Integer idZonaResponsabilidad1e;

    @Column(name = "ID_AREA_RESPONSABILIDAD_2E", nullable = true, precision = 5, scale = 0)
    private Integer idAreaResponsabilidad2e;

    @Column(name = "ID_ZONA_RESPONSABILIDAD_2E", nullable = true, precision = 5, scale = 0)
    private Integer idZonaResponsabilidad2e;

    @Column(name = "UID_CUENTA_SUSTITUTO", nullable = true, precision = 50, scale = 0)
    private String uidCuentaSustituto;

    @Column(name = "UID_CUENTA_SUSTITUIDO", nullable = true, precision = 50, scale = 0)
    private String uidCuentaSustituido;

    @Column(name = "DECLINO_CARGO", nullable = true, precision = 1, scale = 0)
    private Integer declinoCargo;

    @Column(name = "ETAPA", nullable = true, precision = 1, scale = 0)
    private Integer etapa;

    @Column(name = "IP_USUARIO", nullable = false, precision = 15, scale = 0)
    private String ipUsuario;

    @Column(name = "USUARIO", nullable = false, precision = 50, scale = 0)
    private String usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_HORA", nullable = false, precision = 5, scale = 0)
    private Date fechaHora;

    @Transient
    @Column(name = "SUSTITUCIONES_POSTERIORES")
    private Boolean existEnSustPosteriores;
}
