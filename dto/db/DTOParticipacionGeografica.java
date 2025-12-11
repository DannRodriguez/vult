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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOCombo;

@SqlResultSetMapping(name = "comboGeografico", classes = {
    @ConstructorResult(targetClass = VOCombo.class, columns = {
        @ColumnResult(name = "id", type = String.class),
        @ColumnResult(name = "value", type = String.class)})})

@NamedNativeQuery(name = "DTOParticipacionGeografica.getParticipacion", query = """
		SELECT ID_PARTICIPACION AS idParticipacion
		FROM SUPYCAP.PARTICIPACION_GEOGRAFICA
		WHERE ID_PROCESO_ELECTORAL = :idProceso
		    AND ID_DETALLE_PROCESO = :idDetalle
		    AND ID_ESTADO = :idEstado
		    AND ID_DISTRITO = :idDistrito
		    AND AMBITO_PARTICIPACION = :ambito
		    AND (ID_MUNICIPIO IS NULL
		        OR ID_MUNICIPIO = 0)
		    AND (ID_REGIDURIA IS NULL
		        OR ID_REGIDURIA = 0)
		    AND (ID_SECCION_SEDE IS NULL
		        OR ID_SECCION_SEDE = 0)
		    AND (ID_LOCALIDAD IS NULL
		        OR ID_LOCALIDAD = 0)
		    AND (ID_COMUNIDAD IS NULL
		        OR ID_COMUNIDAD = 0)""")

@NamedNativeQuery(name = "DTOParticipacionGeografica.getMunicipios", query = """
		SELECT MUN.ID_MUNICIPIO AS id,
		    MUN.NOMBRE_MUNICIPIO AS value
		FROM GEOGRAFICOINE.SECCIONES SEC
		    JOIN GEOGRAFICOINE.MUNICIPIOS MUN ON (SEC.ID_ESTADO = MUN.ID_ESTADO
		                                        AND SEC.ID_MUNICIPIO = MUN.ID_MUNICIPIO
		                                        AND SEC.ID_CORTE = MUN.ID_CORTE)
		WHERE SEC.ID_ESTADO = :idEstado
		    AND SEC.ID_DISTRITO_FEDERAL = :idDistrito
		    AND SEC.ID_CORTE = :idCorte
		GROUP BY MUN.ID_MUNICIPIO,
		    MUN.NOMBRE_MUNICIPIO
		ORDER BY MUN.ID_MUNICIPIO ASC""", resultSetMapping = "comboGeografico")

@NamedNativeQuery(name = "DTOParticipacionGeografica.getLocalidadesPorMunicipio", query = """
		SELECT DISTINCT L.ID_LOCALIDAD AS id,
		    L.NOMBRE_LOCALIDAD AS value
		FROM GEOGRAFICOINE.LOCALIDADES L
		    JOIN SUPYCAP.SEDES SE ON (L.ID_LOCALIDAD = SE.ID_LOCALIDAD)
		WHERE L.ID_ESTADO = :idEstado
		    AND L.ID_MUNICIPIO = :idMunicipio
		    AND L.ID_DISTRITO_FEDERAL = :idDistrito
		    AND L.ID_CORTE = :idCorte
		    AND SE.ID_PROCESO_ELECTORAL = :idProceso
		    AND SE.ID_DETALLE_PROCESO = :idDetalle
		    AND SE.ID_PARTICIPACION = :idParticipacion
		ORDER BY L.NOMBRE_LOCALIDAD""", resultSetMapping = "comboGeografico")

@NamedNativeQuery(name = "DTOParticipacionGeografica.getSedes", query = """
		SELECT ID_SEDE AS id,
		    NUMERO_SEDE || ' - ' || LUGAR AS value
		FROM SUPYCAP.SEDES
		WHERE ID_PROCESO_ELECTORAL = :idProceso
		    AND ID_DETALLE_PROCESO = :idDetalle
		    AND ID_PARTICIPACION= :idParticipacion
		    AND TIPO_SEDE LIKE 'R'
		ORDER BY NUMERO_SEDE""", resultSetMapping = "comboGeografico")

@NamedNativeQuery(name = "DTOParticipacionGeografica.getSecciones", query = """
		SELECT DISTINCT A.SECCION AS id,
		    A.SECCION AS value
		FROM SUPYCAP.AREAS_SECCIONES A
		    JOIN SUPYCAP.SEDES S ON (A.ID_PROCESO_ELECTORAL = S.ID_PROCESO_ELECTORAL
		        AND A.ID_DETALLE_PROCESO = S.ID_DETALLE_PROCESO
		        AND A.ID_PARTICIPACION = S.ID_PARTICIPACION
		        AND A.SECCION = S.SECCION)
		    JOIN SUPYCAP.ASPIRANTES ASP ON (ASP.ID_DETALLE_PROCESO = S.ID_DETALLE_PROCESO
		        AND ASP.ID_PARTICIPACION = S.ID_PARTICIPACION
		        AND ASP.ID_SEDE_RECLUTAMIENTO = S.ID_SEDE)
		WHERE A.ID_PROCESO_ELECTORAL = :idProceso
		    AND A.ID_DETALLE_PROCESO = :idDetalle
		    AND A.ID_PARTICIPACION = :idParticipacion
		ORDER BY A.SECCION ASC""", resultSetMapping = "comboGeografico")

@Entity
@Table(schema = "SUPYCAP", name = "PARTICIPACION_GEOGRAFICA")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOParticipacionGeografica implements Serializable {

    private static final long serialVersionUID = -432256615395386492L;

    @EmbeddedId
    private DTOParticipacionGeograficaId id;

    @NotNull
    @Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
    private Integer idProcesoElectoral;

    @Column(name = "ID_ESTADO")
    private Integer idEstado;

    @Column(name = "ID_DISTRITO", precision = 2, scale = 0)
    private Integer idDistrito;

    @Column(name = "ID_MUNICIPIO", precision = 3, scale = 0)
    private Integer idMunicipio;

    @Column(name = "ID_REGIDURIA", precision = 3, scale = 0)
    private Integer idRegiduria;

    @Column(name = "ID_SECCION_SEDE", precision = 4, scale = 0)
    private Integer idSeccionSede;

    @Column(name = "ID_LOCALIDAD", precision = 4, scale = 0)
    private Integer idLocalidad;

    @Column(name = "ID_COMUNIDAD", precision = 3, scale = 0)
    private Integer idComunidad;

    @Column(name = "AMBITO_PARTICIPACION", nullable = true, length = 1)
    private String ambitoParticipacion;

    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_HORA", nullable = false)
    private Date fechaHora;

}
