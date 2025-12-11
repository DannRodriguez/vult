package mx.ine.sustseycae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DTOModulo implements Serializable{

	private static final long serialVersionUID = -1045571072115753558L;
	
	private Integer idModulo;

	private String nombreModulo;
	
	private String urlModulo;

	private Integer idAccion;

	private String accionDescrip;

	private String tipoJunta;

	private String estatus;

	private Map<Integer, DTOAccionesModulo> accionesModuloMap;
	
	private List<DTOAccionesModulo> listUrlModulos;

	public DTOModulo() {
		super();
	}

	public DTOModulo(Integer idModulo, String nombreModulo, String urlModulo, Integer idAccion, String accionDescrip,
			String tipoJunta, String estatus, Map<Integer, DTOAccionesModulo> accionesModuloMap) {
		super();
		this.idModulo = idModulo;
		this.nombreModulo = nombreModulo;
		this.urlModulo = urlModulo;
		this.idAccion = idAccion;
		this.accionDescrip = accionDescrip;
		this.tipoJunta = tipoJunta;
		this.estatus = estatus;
		this.accionesModuloMap = accionesModuloMap;
	}



	public Integer getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(Integer idModulo) {
		this.idModulo = idModulo;
	}

	public String getNombreModulo() {
		return nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public String getUrlModulo() {
		return urlModulo;
	}

	public void setUrlModulo(String urlModulo) {
		this.urlModulo = urlModulo;
	}

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public String getAccionDescrip() {
		return accionDescrip;
	}

	public void setAccionDescrip(String accionDescrip) {
		this.accionDescrip = accionDescrip;
	}

	public String getTipoJunta() {
		return tipoJunta;
	}

	public void setTipoJunta(String tipoJunta) {
		this.tipoJunta = tipoJunta;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Map<Integer, DTOAccionesModulo> getAccionesModuloMap() {
		return accionesModuloMap;
	}

	public void setAccionesModuloMap(Map<Integer, DTOAccionesModulo> accionesModuloMap) {
		this.accionesModuloMap = accionesModuloMap;
	}
	
	public List<DTOAccionesModulo> getListUrlModulos() {
	    return accionesModuloMap != null && !accionesModuloMap.isEmpty() ? 
	    		new ArrayList<>(accionesModuloMap.values()) : Collections.emptyList();
	}
	
}
