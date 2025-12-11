package mx.ine.sustseycae.dto.db;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "SUPYCAP", name = "CREACION_CUENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DTOCreacionCuentas implements Serializable {

	private static final long serialVersionUID = 9175533745085504833L;

	@Column(name = "ID_PROCESO_ELECTORAL", nullable = false, precision = 5, scale = 0)
	private Integer idProcesoElectoral;

	@EmbeddedId
	private DTOCreacionCuentasId id;

	@Column(name = "ID_SO", nullable = false, precision = 2, scale = 0)
	private Integer idSO;

	@Column(name = "ESTATUS_CUENTA", nullable = false, precision = 1, scale = 0)
	private Integer estatusCuenta;

	@Column(name = "ESTATUS_PERMISO", nullable = false, precision = 1, scale = 0)
	private Integer estatusPermiso;

	@Column(name = "CORREO_CUENTA_CREADA", nullable = true, length = 60)
	private String correoCuentaCreada;

	@Column(name = "TELEFONO_CUENTA_CREADA", nullable = true, length = 25)
	private String telefonoCuentaCreada;

	@Column(name = "CORREO_CUENTA_NOTIFICACION", nullable = true, length = 60)
	private String correoCuentaNotificacion;

	@Column(name = "UID_CUENTA", nullable = true, length = 50)
	private String uidCuenta;

	@Column(name = "CODIGO_ERROR", nullable = true, precision = 2, scale = 0)
	private Integer codigoError;

	@Column(name = "MENSAJE_ERROR", nullable = true, length = 250)
	private String mensajeError;

	@Column(name = "ID_ZORE_ARE", nullable = true, precision = 8, scale = 0)
	private Integer idZoreAre;

	@Column(name = "NUM_ZORE_ARE", nullable = true, precision = 3, scale = 0)
	private Integer numZoreAre;

	@Column(name = "ID_SISTEMA", nullable = false, precision = 4, scale = 0)
	private Integer idSistema;

	@Column(name = "USUARIO", nullable = false, length = 50)
	private String usuario;

	@Column(name = "IP_USUARIO", nullable = false, length = 15)
	private String ipUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA", nullable = false)
	private Date fechaHora;

}
