package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.AttributeOverride;
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
import mx.ine.sustseycae.dto.vo.VOCImpactos;

@SqlResultSetMapping(name = "VOCImpactosMapping", classes = {
		@ConstructorResult(targetClass = VOCImpactos.class, columns = {
				@ColumnResult(name = "idImpacto", type = Integer.class),
				@ColumnResult(name = "descripcionImpacto", type = String.class),
		})
})

@NamedNativeQuery(name = "DTOCImpactos.getAllCImpactos", query = """
		SELECT ID_IMPACTO AS idImpacto,
		   DESCRIPCION_IMPACTO AS descripcionImpacto
		FROM SUPYCAP.C_IMPACTOS""", resultSetMapping = "VOCImpactosMapping")

@Entity
@Table(name = "C_IMPACTOS", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCImpactos implements Serializable {

	private static final long serialVersionUID = 2471117775132883315L;

	@EmbeddedId
	@AttributeOverride(name = "idImpacto", column = @Column(name = "ID_IMPACTO", nullable = false))
	private DTOCImpactosId id;

	@Column(name = "DESCRIPCION_IMPACTO", length = 50)
	private String descripcionImpacto;

}
