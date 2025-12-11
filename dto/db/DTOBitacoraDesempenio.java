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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
@SqlResultSetMapping(
		name = "VOBitacoraDesempenioMapping", 
		classes = {
		@ConstructorResult(targetClass = VOBitacoraDesempenio.class, 
		columns = {
				@ColumnResult(name = "idProcesoElectoral", type = Integer.class),
				@ColumnResult(name = "idDetalleProceso", type = Integer.class),
				@ColumnResult(name = "idParticipacion", type = Integer.class),
				@ColumnResult(name = "idBitacoraDesempenio", type = Integer.class),
				@ColumnResult(name = "idAspirante", type = Integer.class),
				@ColumnResult(name = "devolvioPrendas", type = String.class),
				@ColumnResult(name = "rutaDocumentos", type = String.class),
				@ColumnResult(name = "documentoCorreo", type = String.class),
				@ColumnResult(name = "documentoCitatorio", type = String.class),
				@ColumnResult(name = "documentoConstancia", type = String.class),
				@ColumnResult(name = "idImpacto", type = Integer.class),
				@ColumnResult(name = "idFrecuencia", type = Integer.class),
				@ColumnResult(name = "idValoracionRiesgo", type = Integer.class),
				@ColumnResult(name = "observaciones", type = String.class),
				@ColumnResult(name = "nombreDocumento", type = String.class),

		})
})

@NamedNativeQuery(
		name = "DTOBitacoraDesempenio.getBitacoraDesempenioAspirante", 
		query = """
		SELECT ID_PROCESO_ELECTORAL AS idProcesoElectoral,
		   ID_DETALLE_PROCESO AS idDetalleProceso,
		   ID_PARTICIPACION AS idParticipacion,
		   id_bitacora_desempenio  AS idBitacoraDesempenio,
		   ID_ASPIRANTE AS idAspirante,
		   DEVOLVIO_PRENDAS AS devolvioPrendas,
		   RUTA_DOCUMENTOS AS rutaDocumentos,
		   DOCUMENTO_CORREO AS documentoCorreo,
		   DOCUMENTO_CITATORIO  AS documentoCitatorio,
		   DOCUMENTO_CONSTANCIA  AS documentoConstancia,
		   ID_IMPACTO AS idImpacto,
		   ID_FRECUENCIA AS idFrecuencia,
		   ID_VALORACION_RIESGO AS idValoracionRiesgo,
		   OBSERVACIONES AS observaciones,
		   NOMBRE_DOCUMENTO AS nombreDocumento
		FROM SUPYCAP.BITACORA_DESEMPENIO
		WHERE ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_PARTICIPACION = :idParticipacion
		AND ID_ASPIRANTE = :idAspirante""", 
		resultSetMapping = "VOBitacoraDesempenioMapping")

@Entity
@Table(name = "BITACORA_DESEMPENIO", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOBitacoraDesempenio implements Serializable {

	private static final long serialVersionUID = 7215006772739569342L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;

	@EmbeddedId
	private DTOBitacoraDesempenioId id;

	@Column(name = "ID_ASPIRANTE", nullable = false, precision = 6, scale = 0)
	private Integer idAspirante;

	@Column(name = "DEVOLVIO_PRENDAS", nullable = false, length = 1)
	private String devolvioPrendas;

	@Column(name = "RUTA_DOCUMENTOS", length = 300)
	private String rutaDocumentos;

	@Column(name = "DOCUMENTO_CORREO", length = 1)
	private String documentoCorreo;

	@Column(name = "DOCUMENTO_CITATORIO", length = 1)
	private String documentoCitatorio;

	@Column(name = "DOCUMENTO_CONSTANCIA", length = 1)
	private String documentoConstancia;

	@Column(name = "ID_IMPACTO", nullable = false, precision = 2, scale = 0)
	private Integer idImpacto;

	@Column(name = "ID_FRECUENCIA", nullable = false, precision = 2, scale = 0)
	private Integer idFrecuencia;

	@Column(name = "ID_VALORACION_RIESGO", nullable = false, precision = 2, scale = 0)
	private Integer idValoracionRiesgo;

	@Column(name = "OBSERVACIONES", length = 500)
	private String observaciones;

	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;

	@Column(name = "IP_USUARIO", nullable = false, length = 15)
	private String ipUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date  fechaHora;
	 
	@Column(name = "NOMBRE_DOCUMENTO", length = 100)
	private String nombreDocumento;

	@Column(name = "ORIGEN_BITACORA", nullable = false, precision = 1, scale = 0)
	private Integer origenBitacora;

}
