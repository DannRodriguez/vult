package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.vo.VOCFrecuencias;

@SqlResultSetMapping(name = "VOCFrecuenciasMapping", classes = {
		@ConstructorResult(targetClass = VOCFrecuencias.class, columns = {
				@ColumnResult(name = "idFrecuencia", type = Integer.class),
				@ColumnResult(name = "descripcionFrecuencia", type = String.class),
		})
})

@NamedNativeQuery(name = "DTOCFrecuencias.getAllCFrecuencias", query = """
		SELECT ID_FRECUENCIA AS idFrecuencia,
		   DESCRIPCION_FRECUENCIA AS descripcionFrecuencia
		FROM SUPYCAP.C_FRECUENCIAS""", resultSetMapping = "VOCFrecuenciasMapping")

@Entity
@Table(name = "C_FRECUENCIAS", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCFrecuencias implements Serializable {

	private static final long serialVersionUID = 154635353098252832L;

	@EmbeddedId
	@AttributeOverride(name = "idFrecuencia", column = @Column(name = "ID_FRECUENCIA", nullable = false))
	private DTOCFrecuenciasId id;

	@Column(name = "DESCRIPCION_FRECUENCIA", length = 50)
	private String descripcionFrecuencia;

}
