package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOSustitucionesSupycap;

@SqlResultSetMapping(name = "voSustitucionesSupycap", classes = {
		@ConstructorResult(targetClass = VOSustitucionesSupycap.class, columns = {
				@ColumnResult(name = "idDetalleProceso", type = Integer.class),
				@ColumnResult(name = "idParticipacion", type = Integer.class),
				@ColumnResult(name = "idSustitucion", type = Integer.class),
				@ColumnResult(name = "idProcesoElectoral", type = Integer.class),
				@ColumnResult(name = "idRelacionSustituciones", type = String.class),
				@ColumnResult(name = "idAspiranteSutituido", type = Integer.class),
				@ColumnResult(name = "idPuestoSustituido", type = Integer.class),
				@ColumnResult(name = "correoCtaCreadaSustituido", type = String.class),
				@ColumnResult(name = "correoCtaNotifSustituido", type = String.class),
				@ColumnResult(name = "idAspiranteSutituto", type = Integer.class),
				@ColumnResult(name = "idPuestoSustituto", type = Integer.class),
				@ColumnResult(name = "correoCtaCreadaSustituto", type = String.class),
				@ColumnResult(name = "correoCtaNotifSustituto", type = String.class),
				@ColumnResult(name = "idCausaVacante", type = Integer.class),
				@ColumnResult(name = "tipoCausaVacante", type = Integer.class),
				@ColumnResult(name = "fechaBaja", type = Date.class),
				@ColumnResult(name = "fechaAlta", type = Date.class),
				@ColumnResult(name = "fechaSustitucion", type = Date.class),
				@ColumnResult(name = "idAreaResponsabilidad1e", type = Integer.class),
				@ColumnResult(name = "idAZonaResponsabilidad1e", type = Integer.class),
				@ColumnResult(name = "idAreaResponsabilidad2e", type = Integer.class),
				@ColumnResult(name = "idZonaResponsabilidad2e", type = Integer.class),
				@ColumnResult(name = "observaciones", type = String.class),
				@ColumnResult(name = "uidCuentaSustituto", type = String.class),
				@ColumnResult(name = "uidCuentaSustituido", type = String.class),
				@ColumnResult(name = "declinoCargo", type = Integer.class),
				@ColumnResult(name = "etapa", type = Integer.class),
				@ColumnResult(name = "ipUsuario", type = String.class),
				@ColumnResult(name = "usuario", type = String.class),
				@ColumnResult(name = "fechaHora", type = Date.class),
				@ColumnResult(name = "fechaMovimiento", type = Date.class),

		})
})

@NamedNativeQuery(name = "DTOSustitucionesSupycap.obtenerInfoSustitucionById", query = """
		SELECT ID_DETALLE_PROCESO AS idDetalleProceso,
		    ID_PARTICIPACION AS idParticipacion,
		    ID_SUSTITUCION AS idSustitucion,
		    ID_PROCESO_ELECTORAL AS idProcesoElectoral,
		    ID_RELACION_SUSTITUCIONES AS idRelacionSustituciones,
		    ID_ASPIRANTE_SUSTITUIDO AS idAspiranteSutituido,
		    ID_PUESTO_SUSTITUIDO AS idPuestoSustituido,
		    CORREO_CTA_CREADA_SUSTITUIDO AS correoCtaCreadaSustituido,
		    CORREO_CTA_NOTIF_SUSTITUIDO AS correoCtaNotifSustituido,
		    ID_ASPIRANTE_SUSTITUTO AS idAspiranteSutituto,
		    ID_PUESTO_SUSTITUTO AS idPuestoSustituto,
		    CORREO_CTA_CREADA_SUSTITUTO AS correoCtaCreadaSustituto,
		    CORREO_CTA_NOTIF_SUSTITUTO AS correoCtaNotifSustituto,
		    ID_CAUSA_VACANTE AS idCausaVacante,
		    TIPO_CAUSA_VACANTE AS tipoCausaVacante,
		    FECHA_BAJA AS fechaBaja,
		    FECHA_ALTA AS fechaAlta,
		    FECHA_SUSTITUCION AS fechaSustitucion,
		    ID_AREA_RESPONSABILIDAD_1E AS idAreaResponsabilidad1e,
		    ID_ZONA_RESPONSABILIDAD_1E AS idAZonaResponsabilidad1e,
		    ID_AREA_RESPONSABILIDAD_2E AS idAreaResponsabilidad2e,
		    ID_ZONA_RESPONSABILIDAD_2E AS idZonaResponsabilidad2e,
		    OBSERVACIONES AS observaciones,
		    UID_CUENTA_SUSTITUTO AS uidCuentaSustituto,
		    UID_CUENTA_SUSTITUIDO AS uidCuentaSustituido,
		    DECLINO_CARGO AS declinoCargo,
		    ETAPA AS etapa,
		    IP_USUARIO AS ipUsuario,
		    USUARIO AS usuario,
		    FECHA_HORA AS fechaHora,
		    FECHA_MOVIMIENTO AS fechaMovimiento
		FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
		WHERE ID_PROCESO_ELECTORAL = :idProceso
		    AND ID_DETALLE_PROCESO = :idDetalle
		    AND ID_PARTICIPACION = :idParticipacion
		    AND ID_SUSTITUCION = :idSustitucion""", resultSetMapping = "voSustitucionesSupycap")

@NamedNativeQuery(name = "DTOSustitucionesSupycap.obtenerInfoSustitucionPorFiltro", query = """
		SELECT ID_DETALLE_PROCESO AS idDetalleProceso,
		    ID_PARTICIPACION AS idParticipacion,
		    ID_SUSTITUCION AS idSustitucion,
		    ID_PROCESO_ELECTORAL AS idProcesoElectoral,
		    ID_RELACION_SUSTITUCIONES AS idRelacionSustituciones,
		    ID_ASPIRANTE_SUSTITUIDO AS idAspiranteSutituido,
		    ID_PUESTO_SUSTITUIDO AS idPuestoSustituido,
		    CORREO_CTA_CREADA_SUSTITUIDO AS correoCtaCreadaSustituido,
		    CORREO_CTA_NOTIF_SUSTITUIDO AS correoCtaNotifSustituido,
		    ID_ASPIRANTE_SUSTITUTO AS idAspiranteSutituto,
		    ID_PUESTO_SUSTITUTO AS idPuestoSustituto,
		    CORREO_CTA_CREADA_SUSTITUTO AS correoCtaCreadaSustituto,
		    CORREO_CTA_NOTIF_SUSTITUTO AS correoCtaNotifSustituto,
		    ID_CAUSA_VACANTE AS idCausaVacante,
		    TIPO_CAUSA_VACANTE AS tipoCausaVacante,
		    FECHA_BAJA AS fechaBaja,
		    FECHA_ALTA AS fechaAlta,
		    FECHA_SUSTITUCION AS fechaSustitucion,
		    ID_AREA_RESPONSABILIDAD_1E AS idAreaResponsabilidad1e,
		    ID_ZONA_RESPONSABILIDAD_1E AS idAZonaResponsabilidad1e,
		    ID_AREA_RESPONSABILIDAD_2E AS idAreaResponsabilidad2e,
		    ID_ZONA_RESPONSABILIDAD_2E AS idZonaResponsabilidad2e,
		    OBSERVACIONES AS observaciones,
		    UID_CUENTA_SUSTITUTO AS uidCuentaSustituto,
		    UID_CUENTA_SUSTITUIDO AS uidCuentaSustituido,
		    DECLINO_CARGO AS declinoCargo,
		    ETAPA AS etapa,
		    IP_USUARIO AS ipUsuario,
		    USUARIO AS usuario,
		    FECHA_HORA AS fechaHora,
		    FECHA_MOVIMIENTO AS fechaMovimiento
		FROM SUPYCAP.SUSTITUCIONES_SUPYCAP
		WHERE ID_PROCESO_ELECTORAL = :idProceso
		    AND ID_DETALLE_PROCESO = :idDetalle
		    AND ID_PARTICIPACION = :idParticipacion
		    AND (:idAspiranteSustituido IS NULL OR ID_ASPIRANTE_SUSTITUIDO = :idAspiranteSustituido)
		    AND (:idAspiranteSustituto IS NULL OR ID_ASPIRANTE_SUSTITUTO = :idAspiranteSustituto)
		    AND (:idRelacion IS NULL OR ID_RELACION_SUSTITUCIONES = :idRelacion)
		ORDER BY ID_SUSTITUCION DESC""", resultSetMapping = "voSustitucionesSupycap")

@Entity
@Table(schema = "SUPYCAP", name = "SUSTITUCIONES_SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOSustitucionesSupycap implements Serializable {

	private static final long serialVersionUID = 79593035589790069L;

	@EmbeddedId
	private DTOSustitucionesSupycapId id;

	@NotNull
	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;

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
	private Integer idAZonaResponsabilidad1e;

	@Column(name = "ID_AREA_RESPONSABILIDAD_2E", nullable = true, precision = 5, scale = 0)
	private Integer idAreaResponsabilidad2e;

	@Column(name = "ID_ZONA_RESPONSABILIDAD_2E", nullable = true, precision = 5, scale = 0)
	private Integer idZonaResponsabilidad2e;

	@Column(name = "OBSERVACIONES", nullable = true, precision = 500, scale = 0)
	private String observaciones;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_MOVIMIENTO", nullable = false, precision = 5, scale = 0)
	private Date fechaMovimiento;

	@Transient
	private String strFechaAlta;

	@Transient
	private String strFechaBaja;

	public DTOSustitucionesSupycap(VOSustitucionesSupycap sustitucion) {
		super();
		this.id = new DTOSustitucionesSupycapId(sustitucion.getIdDetalleProceso(), sustitucion.getIdParticipacion(),
				sustitucion.getIdSustitucion());
		this.idProcesoElectoral = sustitucion.getIdProcesoElectoral();
		this.idRelacionSustituciones = sustitucion.getIdRelacionSustituciones();
		this.idAspiranteSutituido = sustitucion.getIdAspiranteSutituido();
		this.idPuestoSustituido = sustitucion.getIdPuestoSustituido();
		this.correoCtaCreadaSustituido = sustitucion.getCorreoCtaCreadaSustituido();
		this.correoCtaNotifSustituido = sustitucion.getCorreoCtaNotifSustituido();
		this.idAspiranteSutituto = sustitucion.getIdAspiranteSutituto();
		this.idPuestoSustituto = sustitucion.getIdPuestoSustituto();
		this.correoCtaCreadaSustituto = sustitucion.getCorreoCtaCreadaSustituto();
		this.correoCtaNotifSustituto = sustitucion.getCorreoCtaNotifSustituto();
		this.idCausaVacante = sustitucion.getIdCausaVacante();
		this.tipoCausaVacante = sustitucion.getTipoCausaVacante();
		this.fechaBaja = sustitucion.getFechaBaja();
		this.fechaAlta = sustitucion.getFechaAlta();
		this.fechaSustitucion = sustitucion.getFechaSustitucion();
		this.idAreaResponsabilidad1e = sustitucion.getIdAreaResponsabilidad1e();
		this.idAZonaResponsabilidad1e = sustitucion.getIdAZonaResponsabilidad1e();
		this.idAreaResponsabilidad2e = sustitucion.getIdAreaResponsabilidad2e();
		this.idZonaResponsabilidad2e = sustitucion.getIdZonaResponsabilidad2e();
		this.observaciones = sustitucion.getObservaciones();
		this.uidCuentaSustituto = sustitucion.getUidCuentaSustituto();
		this.uidCuentaSustituido = sustitucion.getUidCuentaSustituido();
		this.declinoCargo = sustitucion.getDeclinoCargo();
		this.etapa = sustitucion.getEtapa();
		this.ipUsuario = sustitucion.getIpUsuario();
		this.usuario = sustitucion.getUsuario();
		this.fechaHora = sustitucion.getFechaHora();
		this.fechaMovimiento = sustitucion.getFechaMovimiento();
	}

}
