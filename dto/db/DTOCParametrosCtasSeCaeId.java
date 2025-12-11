package mx.ine.sustseycae.dto.db;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCParametrosCtasSeCaeId implements Serializable {

	private static final long serialVersionUID = 1689973847152554412L;

	@NotNull
	@Column(name = "ID_PARAMETRO", nullable = false, precision = 3, scale = 0)
	private Integer idParametro;

}
