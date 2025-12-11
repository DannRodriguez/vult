package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NamedNativeQuery(name = "DTOCParametros.obtieneParametrosRecursivamente", query = """
		SELECT VALOR_PARAMETRO AS parametro
		FROM(
		SELECT VALOR_PARAMETRO, 1 AS PRIORIDAD
		FROM SUPYCAP.C_PARAMETROS WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = :idEstado AND ID_DISTRITO = :idDistrito AND TIPO_JUNTA = 'GE'
		AND ID_PARAMETRO = :idParametro
		UNION
		SELECT VALOR_PARAMETRO, 2 AS PRIORIDAD
		FROM SUPYCAP.C_PARAMETROS WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = :idEstado AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_PARAMETRO = :idParametro
		UNION
		SELECT VALOR_PARAMETRO, 3 AS PRIORIDAD
		FROM SUPYCAP.C_PARAMETROS WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = 0 AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_PARAMETRO = :idParametro
		UNION
		SELECT VALOR_PARAMETRO, 4 AS PRIORIDAD
		FROM SUPYCAP.C_PARAMETROS
		WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = 0
		AND ID_ESTADO = 0 AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_PARAMETRO = :idParametro
		UNION
		SELECT VALOR_PARAMETRO, 5 AS PRIORIDAD
		FROM SUPYCAP.C_PARAMETROS
		WHERE ID_PROCESO_ELECTORAL = 0 AND ID_DETALLE_PROCESO = 0
		AND ID_ESTADO = 0 AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_PARAMETRO = :idParametro
		ORDER BY PRIORIDAD ASC )
		WHERE ROWNUM = 1""")

@Entity
@Table(name = "C_PARAMETROS", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCParametros implements Serializable {

	private static final long serialVersionUID = -6832483503054169902L;

	@EmbeddedId
	private DTOCParametrosId id;

	@NotNull
	@Column(name = "VALOR_PARAMETRO", nullable = false, precision = 5, scale = 0)
	private Integer valorParametro;

}
