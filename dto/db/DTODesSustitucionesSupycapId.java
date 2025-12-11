package mx.ine.sustseycae.dto.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTODesSustitucionesSupycapId implements Serializable {
    @Serial
    private static final long serialVersionUID = 5954874776081259384L;

    @NotNull
    @Column(name = "ID_DETALLE_PROCESO", nullable = false, precision = 7, scale = 0)
    private Integer idDetalleProceso;

    @NotNull
    @Column(name = "ID_PARTICIPACION", nullable = false, precision = 9, scale = 0)
    private Integer idParticipacion;

    @NotNull
    @Column(name = "ID_DESHACER", nullable = false, precision = 5, scale = 0)
    private Integer idDeshacer;

}
