package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "C_PARAMETROS_CTAS_SE_CAE", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCParametrosCtasSeCae implements Serializable {

	private static final long serialVersionUID = 1689973847152554412L;

	@EmbeddedId
	private DTOCParametrosCtasSeCaeId id;

	@NotNull
	@Column(name = "DESCRIPCION_PARAMETRO", nullable = false, length = 50)
	private String descripcionParametro;

	@NotNull
	@Column(name = "DESCRIPCION_VALORES", nullable = false, length = 100)
	private String descripcionValores;

	@NotNull
	@Column(name = "VALOR_PARAMETRO", nullable = false, precision = 5, scale = 0)
	private Integer valorParametro;

}
