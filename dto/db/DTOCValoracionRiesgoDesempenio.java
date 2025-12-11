package mx.ine.sustseycae.dto.db;

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
import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;

@SqlResultSetMapping(name = "VOCValoracionRiesgoMapping", classes = {
		@ConstructorResult(targetClass = VOCValoracionRiesgo.class, columns = {
				@ColumnResult(name = "idImpacto", type = Integer.class),
				@ColumnResult(name = "idFrecuencia", type = Integer.class),
				@ColumnResult(name = "idValoracionRiesgo", type = Integer.class),
				@ColumnResult(name = "descripcionRiesgo", type = String.class),
		})
})

@NamedNativeQuery(name = "DTOCValoracionRiesgoDesempenio.getAllCValoracionRiesgo", query = """
		SELECT ID_IMPACTO AS idImpacto,
		   ID_FRECUENCIA AS idFrecuencia,
		   ID_VALORACION_RIESGO AS idValoracionRiesgo,
		   DESCRIPCION_RIESGO AS descripcionRiesgo
		FROM SUPYCAP.C_VALORACION_RIESGO_DESEMPENIO""", resultSetMapping = "VOCValoracionRiesgoMapping")

@Entity
@Table(name = "C_VALORACION_RIESGO_DESEMPENIO", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCValoracionRiesgoDesempenio implements Serializable {

	private static final long serialVersionUID = 5142258486609933473L;

	@EmbeddedId
	private DTOCValoracionRiesgoDesempenioId id;

	@Column(name = "DESCRIPCION_RIESGO", length = 50)
	private String descripcionRiesgo;

}
