package mx.ine.sustseycae.dto.dbgeografico;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.ine.sustseycae.dto.db.DTOGeografico;

@SqlResultSetMapping(name = "geografico", classes = {
		@ConstructorResult(targetClass = DTOGeografico.class, columns = {
				@ColumnResult(name = "nombreEdoDto", type = String.class),
				@ColumnResult(name = "identificador", type = Integer.class)
		})
})

@NamedNativeQuery(name = "DTOEstados.getNombreEstado", query = "SELECT edo.nombre_estado AS nombreEdoDto,\n" +
		"       edo.id_estado AS identificador\n" +
		"FROM GEOGRAFICOINE.estados edo\n" +
		"WHERE edo.ID_ESTADO = :idEstado\n" +
		"  AND edo.ID_CORTE =\n" +
		"    (SELECT MAX(id_corte) id_corte\n" +
		"     FROM geograficoine.cortes)", resultSetMapping = "geografico")

@NamedNativeQuery(name = "DTOEstados.getNombreDistrito", query = "SELECT dto.CABECERA_DISTRITAL_FEDERAL AS nombreEdoDto,\n"
		+
		"       dto.id_distrito_federal AS identificador\n" +
		"FROM GEOGRAFICOINE.DISTRITOS_FEDERALES dto\n" +
		"WHERE dto.ID_ESTADO = :idEstado\n" +
		"  AND dto.ID_DISTRITO_FEDERAL = :idDistrito\n" +
		"  AND dto.ID_CORTE =\n" +
		"    (SELECT MAX(id_corte) id_corte\n" +
		"     FROM geograficoine.cortes)", resultSetMapping = "geografico")

@Entity
@Table(name = "ESTADOS", schema = "GEOGRAFICOINE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOEstados implements Serializable {

	@Serial
	private static final long serialVersionUID = -2665390481021106348L;

	@EmbeddedId
	private DTOEstadosId id;

	@Column(name = "NOMBRE_ESTADO", unique = true, nullable = false, precision = 50, scale = 0)
	private String nombreEstado;

	@Column(name = "ABREVIATURA", unique = true, nullable = false, precision = 8, scale = 0)
	private String abreviatura;

	@Column(name = "ID_CIRCUNSCRIPCION", unique = true, nullable = true, precision = 1, scale = 0)
	private Integer idCircunscripcion;

	@Column(name = "CIRCUNSCRIPCION", unique = true, nullable = true, precision = 3, scale = 0)
	private String circunscripcion;

}