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
import mx.ine.sustseycae.dto.vo.VOCVocalesSust;

@SqlResultSetMapping(name = "VOCVocalesSustMapping", classes = {
        @ConstructorResult(targetClass = VOCVocalesSust.class, columns = {
                @ColumnResult(name = "idResponsableBitacora", type = Integer.class),
                @ColumnResult(name = "tipoPuesto", type = String.class),
                @ColumnResult(name = "idPuesto", type = Integer.class),
                @ColumnResult(name = "puesto", type = String.class),
        })
})
@NamedNativeQuery(name = "DTOCVocalesSust.getAllVocalesSust", query = """
                  SELECT ID_RESPONSABLE_BITACORA AS idResponsableBitacora,
                         TIPO_PUESTO AS tipoPuesto,
                         ID_PUESTO AS idPuesto,
                         PUESTO AS puesto
                  FROM SUPYCAP.C_VOCALES_SUST
                  WHERE ID_PROCESO_ELECTORAL = :idProcesoElectoral AND ID_DETALLE_PROCESO = :idDetalleProceso
                  ORDER BY ID_PUESTO
        """, resultSetMapping = "VOCVocalesSustMapping")
@Entity
@Table(name = "C_VOCALES_SUST", schema = "SUPYCAP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCVocalesSust implements Serializable {

    private static final long serialVersionUID = 1L;

    
    @EmbeddedId
    private DTOCVocalesSustId id;

    @Column(name = "TIPO_PUESTO", nullable = false)
    private Integer tipoPuesto;

    @Column(name = "ID_PUESTO", nullable = false, precision = 1, scale = 0)
    private Integer idPuesto;

    @Column(name = "PUESTO", length = 50, nullable = false)
    private String puesto;
}
