package mx.ine.sustseycae.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VOConsultaDesSustitucionesSupycap {

    private Integer id_sustitucion;

    private String id_relacion_sustituciones;

    private Integer id_causa;

    private Integer tipo_causa;

    private Integer id_aspirante_sustituido;

    private Integer id_aspirante_sustituto;

    private String puesto_sustituido;

    private String nombre_sustituido;

    private String causa;

    private String fecha_baja;

    private String fecha_alta;

    private String fecha_sustitucion;

    private String puesto_sustituto;

    private String nombre_sustituto;

    private Boolean existEnSustPosteriores;

}
