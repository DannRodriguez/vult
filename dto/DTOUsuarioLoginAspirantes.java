package mx.ine.sustseycae.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * Clase que contiene los datos del usuario que se autentica.
 *
 * @author Jos&eacute; Carlos Ortega Romano
 * @copyright Direcci&oacute;n de sistemas - INE
 * @since 26/06/2017
 */

public class DTOUsuarioLoginAspirantes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8178882942229240172L;

	
	private String utilsP;
	private String username;
	
	/**
	 * Id Estado al que pertenece el usuario.
	 */
	private Integer idEstado;

	/**
	 * Id Estado asociado
	 */
	private Integer idEstadoAsoc;

	/**
	 * nombre del estado
	 */
	private String estado;
	
	/**
	 * nombre del distrito
	 */
	private String distrito;
	
	/**
	 * Nombre completo del usuario
	 */
	private String nombre;

	/**
	 * Id distrito al que pertenece el usuario.
	 */
	private Integer idDistrito;
	
	/**
	 * Rol que el usuario tiene dentro del sistema.
	 */
	@JsonIgnore
	private List<String> rolesUsuario;

	/**
	 * Id asociacion
	 */
	private Integer idAsociacion;

	/**
	 * Tipo asociacion (Que tipo de usurio es)
	 */
	private Integer tipoAsociacion;

	/**
	 * Id sistema
	 */
	private Integer idSistema;

	/**
	 * Ip del usuario
	 */
	private String ip;

	/**
	 * &Aacute;rea a la que pertenece el usuario
	 */
	private String areaAdscripcion;

	/**
	 * Puesto del usuario.
	 */
	private String puesto;

	/**
	 * Correo electr&oacute;nico.
	 */
	private String mail;
	
	/**
	 * idProcesoElectoral
	 */
	private Integer idProcesoElectoral;
	
	/**
	 * idDetalle
	 */
	private Integer IdDetalle;
	
	/**
	 * rol del usuario
	 */
	private String rolUsuario;
	
	/**
	 * version de la app movil que consume el servicio
	 */
	@JsonIgnore
	private String version;
	
	/**
	 * ambito del usuario
	 */
	private String ambito;
	
	/**
	 * tipo usuario
	 */
	private String tipoUsuario;
	
	/**
	 * Tratamiento
	 */
	private String tratamiento;
	
	
	/**
	 * Este campo corresponde al token enviado a los servicios para ejecutar la logica de negocio
	 */
	private String tknA;
	
	/**
	 * Este campo corresponde al token enviado al service de refresh
	 */
	private String tknR;
	
	/**
	 * Indica el tipo de token [A=0|R=1]
	 */
	@JsonIgnore
	private Integer tipoToken;
	
	private String nombreProceso;

	private String fechaFinCapsula;
	
	/**
	 * M&eacute;todo para obtener el campo idEstado
	 * @return idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}

	/**
	 * M&eacute;todo para establecer el campo idEstado
	 * 
	 * @param idEstado
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * M&eacute;todo para obtener el campo nombre 
	 * @return nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * M&eacute;todo para establecer el campo nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * M&eacute;todo para obtener el campo idDistrito
	 * @return idDistrito
	 */
	public Integer getIdDistrito() {
		return idDistrito;
	}

	/**
	 * M&eacute;todo para establecer el campo idDistrito
	 * @param idDistrito
	 */
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	/**
	 * M&eacute;todo para obtener el campo rolesUsuario
	 * @return rolesUsuario List<String>
	 */
	public List<String> getRolesUsuario() {
		return rolesUsuario;
	}

	/**
	 * M&eacute;todo para establecer el campo rolesUsuario
	 * @param rolesUsuario List<String>
	 */
	public void setRolesUsuario(List<String> rolesUsuario) {
		this.rolesUsuario = rolesUsuario;
	}

	/**
	 * M&eacute;todo para obtener el campo idAsociacion
	 * @return idAsociacion
	 */
	public Integer getIdAsociacion() {
		return idAsociacion;
	}

	/**
	 * M&eacute;todo para establecer el campo idAsociacion
	 * @param idAsociacion
	 */
	public void setIdAsociacion(Integer idAsociacion) {
		this.idAsociacion = idAsociacion;
	}

	/**
	 * M&eacute;todo para obtener el campo Tipo de asociacion (tipo del sujeto obligado)
	 * @return tipoAsociacion
	 */
	public Integer getTipoAsociacion() {
		return tipoAsociacion;
	}

	/**
	 *  M&eacute;todo para establecer el campo tipoAsociacion
	 * @param tipoAsociacion
	 */
	public void setTipoAsociacion(Integer tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
	}

	/**
	 * M&eacute;todo para obtener el campo idSistema
	 * @return idSistema
	 */
	public Integer getIdSistema() {
		return idSistema;
	}

	/**
	 * M&eacute;todo para establecer el campo idSistema
	 * @param idSistema
	 */
	public void setIdSistema(Integer idSistema) {
		this.idSistema = idSistema;
	}

	/**
	 * M&eacute;todo para obtener el campo ip del usuario
	 * @return ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * M&eacute;todo para establecer el campo ip
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * &Aacute;rea (departamento) a la que pertenece el usuario
	 * @return
	 */
	public String getAreaAdscripcion() {
		return areaAdscripcion;
	}

	/**
	 * M&eacute;todo para establecer el campo areaAdscripcion
	 * @param areaAdscripcion
	 */
	public void setAreaAdscripcion(String areaAdscripcion) {
		this.areaAdscripcion = areaAdscripcion;
	}

	/**
	 * M&eacute;todo para obtener el campo puesto
	 * @return puesto
	 */
	public String getPuesto() {
		return puesto;
	}

	/**
	 * M&eacute;todo para establecer el campo puesto
	 * @param puesto
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	/**
	 * M&eacute;todo para obtener el campo mail
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * M&eacute;todo para establecer el campo mail
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * M&eacute;todo para obtener el campo tknA
	 * @return tknA
	 */
	public String getTknA() {
		return tknA;
	}

	/**
	 * M&eacute;todo para establecer el campo tknA
	 * @param tknA
	 */
	public void setTknA(String tknA) {
		this.tknA = tknA;
	}

	
	/**
	 * M&eacute;todo para obtener el campo idProcesoElectoral
	 * @return idProcesoElectoral
	 */
	public Integer getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	/**
	 * M&eacute;todo para establecer el campo idProcesoElectoral
	 * @param idProcesoElectoral
	 */
	public void setIdProcesoElectoral(Integer idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	/**
	 * M&eacute;todo para obtener el campo IdDetalle
	 * @return IdDetalle
	 */
	public Integer getIdDetalle() {
		return IdDetalle;
	}

	/**
	 * M&eacute;todo para establecer el campo idDetalle
	 * @param idDetalle
	 */
	public void setIdDetalle(Integer idDetalle) {
		IdDetalle = idDetalle;
	}

	/**
	 * M&eacute;todo para obtener el campo estado
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * M&eacute;todo para establecer el campo estado
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * M&eacute;todo para obtener el campo distrito
	 * @return
	 */
	public String getDistrito() {
		return distrito;
	}

	/**
	 * M&eacute;todo para establecer el campo distrito
	 * @param distrito
	 */
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	/**
	 * M&eacute;todo para obtener el campo rolUsuario
	 * @return rolUsuario
	 */
	public String getRolUsuario() {
		return rolUsuario;
	}

	/**
	 * M&eacute;todo para establecer el campo rolUsuario
	 * @param rolUsuario
	 */
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}


	/**
	 * M&eacute;todo sobreescrito para obtener la clave del usuario
	 */
	@JsonIgnore
	public String getutilsP() {
		return utilsP;
	}

	public String getUsername() {
		return username;
	}

	public void setutilsP(String utilsP) {
		this.utilsP = utilsP;
	}

	/**
	 * M&eacute;todo para obtener el campo tknR
	 * @return tknR
	 */
	public String getTknR() {
		return tknR;
	}

	/**
	 * M&eacute;todo para establecer el campo tknR
	 * @param tknR
	 */
	public void setTknR(String tknR) {
		this.tknR = tknR;
	}

	/**
	 * M&eacute;todo para obtener el campo version de la app movil
	 * @return version de la app movil
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * M&eacute;todo para establecer el campo version de la app movil
	 * @param version de la app movil
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * M&eacute;todo para obtener el campo ambito
	 * @return ambito
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * M&eacute;todo para establecer el campo ambito
	 * @param ambito
	 */
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	/**
	 * M&eacute;todo para obtener el campo tipo de usuario
	 * @return tipo
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	/**
	 * M&eacute;todo para establecer el campo tipo de usuario
	 * @param tipo
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public Integer getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(Integer tipoToken) {
		this.tipoToken = tipoToken;
	}

	

	public Integer getIdEstadoAsoc() {
		return idEstadoAsoc;
	}

	public void setIdEstadoAsoc(Integer idEstadoAsoc) {
		this.idEstadoAsoc = idEstadoAsoc;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public String getFechaFinCapsula() {
		return fechaFinCapsula;
	}

	public void setFechaFinCapsula(String fechaFinCapsula) {
		this.fechaFinCapsula = fechaFinCapsula;
	}
	
}
