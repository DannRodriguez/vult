package mx.ine.sustseycae.dto.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(schema = "SUPYCAP", name = "C_FECHAS")
@Cacheable(true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCFechas implements Serializable {

	private static final long serialVersionUID = 7397397589955529518L;

	@EmbeddedId
	private DTOCFechasId id;

	@Column(name = "FECHA", nullable = false)
	private LocalDate fecha;

}
