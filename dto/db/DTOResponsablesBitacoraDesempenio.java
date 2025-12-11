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
import mx.ine.sustseycae.dto.vo.VOResponsableBitacoraDesempenio;

@SqlResultSetMapping(name = "VOResponsableBitacoraDesempenioMapping", classes = {
		@ConstructorResult(targetClass = VOResponsableBitacoraDesempenio.class, columns = {
				@ColumnResult(name = "idDetalleProceso", type = Integer.class),
				@ColumnResult(name = "idParticipacion", type = Integer.class),
				@ColumnResult(name = "idBitacoraDesempenio", type = Integer.class),
				@ColumnResult(name = "idResponsableBitacora", type = Integer.class),
				@ColumnResult(name = "idPuestoFuncionario", type = Integer.class),
				@ColumnResult(name = "tipoPuesto", type = String.class),
				@ColumnResult(name = "inicialesPuesto", type = String.class),
				@ColumnResult(name = "nombre", type = String.class),
				@ColumnResult(name = "apellidoPaterno", type = String.class),
				@ColumnResult(name = "apellidoMaterno", type = String.class),
				@ColumnResult(name = "tratamiento", type = String.class),
				@ColumnResult(name = "idPuesto", type = Integer.class),
		})
})

@NamedNativeQuery(name = "DTOResponsablesBitacoraDesempenio.getResponsablesBitacoraDesempenio", query = """
		SELECT ID_DETALLE_PROCESO AS idDetalleProceso,
		   ID_PARTICIPACION AS idParticipacion,
		   ID_BITACORA_DESEMPENIO AS idBitacoraDesempenio,
		   ID_RESPONSABLE_BITACORA AS idResponsableBitacora,
		   ID_PUESTO_FUNCIONARIO AS idPuestoFuncionario,
		   TIPO_PUESTO AS tipoPuesto,
		   INICIALES_PUESTO AS inicialesPuesto,
		   NOMBRE AS nombre,
		   APELLIDO_PATERNO AS  apellidoPaterno,
		   APELLIDO_MATERNO AS apellidoMaterno,
		   TRATAMIENTO AS tratamiento,
		   ID_PUESTO AS idPuesto
		FROM SUPYCAP.RESPONSABLES_BITACORA_DESEMPENIO
		WHERE ID_DETALLE_PROCESO = :idDetalleProceso
		   AND ID_PARTICIPACION = :idParticipacion
		   AND ID_BITACORA_DESEMPENIO = :idBitacora""", resultSetMapping = "VOResponsableBitacoraDesempenioMapping")

@Entity
@Table(name = "RESPONSABLES_BITACORA_DESEMPENIO", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOResponsablesBitacoraDesempenio implements Serializable {

	private static final long serialVersionUID = 2738588531826468206L;

	@EmbeddedId
	private DTOResponsablesBitacoraDesempenioId id;

	@Column(name = "ID_PUESTO_FUNCIONARIO", nullable = false, precision = 10, scale = 0)
	private Integer idPuestoFuncionario;

	@Column(name = "TIPO_PUESTO", nullable = false, length = 1)
	private String tipoPuesto;

	@Column(name = "INICIALES_PUESTO", nullable = false, length = 50)
	private String inicialesPuesto;

	@Column(name = "NOMBRE", nullable = false, length = 50)
	private String nombre;

	@Column(name = "APELLIDO_PATERNO", length = 40)
	private String apellidoPaterno;

	@Column(name = "APELLIDO_MATERNO", length = 40)
	private String apellidoMaterno;

	@Column(name = "TRATAMIENTO", length = 1)
	private String tratamiento;

	@Column(name = "ID_PUESTO", nullable = false, precision = 3, scale = 0)
	private Integer idPuesto;

	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;

	@Column(name = "IP_USUARIO", nullable = false, length = 15)
	private String ipUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

}
