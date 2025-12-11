package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NamedNativeQuery(name = "DTOCEtiquetas.obtieneEtiquetasRecursivamente", query = """
		SELECT ETIQUETA AS etiqueta FROM( SELECT ETIQUETA, 1 AS PRIORIDAD
		FROM SUPYCAP.C_ETIQUETAS
		WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = :idEstado AND ID_DISTRITO = :idDistrito AND TIPO_JUNTA = 'GE'
		AND ID_ETIQUETA = :idEtiqueta
		UNION
		SELECT ETIQUETA, 2 AS PRIORIDAD
		FROM SUPYCAP.C_ETIQUETAS
		WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = :idEstado AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_ETIQUETA = :idEtiqueta
		UNION
		SELECT ETIQUETA, 3 AS PRIORIDAD
		FROM SUPYCAP.C_ETIQUETAS
		WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
		AND ID_ESTADO = 0 AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_ETIQUETA = :idEtiqueta
		UNION
		SELECT ETIQUETA, 4 AS PRIORIDAD
		FROM SUPYCAP.C_ETIQUETAS
		WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = 0
		AND ID_ESTADO = 0 AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_ETIQUETA = :idEtiqueta
		UNION
		SELECT ETIQUETA, 5 AS PRIORIDAD
		FROM SUPYCAP.C_ETIQUETAS
		WHERE ID_PROCESO_ELECTORAL = 0 AND ID_DETALLE_PROCESO = 0 AND ID_ESTADO = 0
		AND ID_DISTRITO = 0 AND TIPO_JUNTA = 'GE'
		AND ID_ETIQUETA = :idEtiqueta
		ORDER BY PRIORIDAD ASC )
		WHERE ROWNUM = 1""")

@Entity
@Table(schema = "SUPYCAP", name = "C_ETIQUETAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCEtiquetas implements Serializable {

	private static final long serialVersionUID = 6642737515517770599L;

	@EmbeddedId
	private DTOCEtiquetasId id;

	@Column(name = "CVE_ETIQUETA", length = 30, nullable = false)
	private String cveEtiqueta;

	@Column(name = "ETIQUETA", length = 250, nullable = true)
	private String etiqueta;

}
