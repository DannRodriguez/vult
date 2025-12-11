package mx.ine.sustseycae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DTOSubmenu implements Serializable{

	private static final long serialVersionUID = -780373244044068347L;

	private Integer idSubMenu;

	private String nombreSubMenu;
	
	private Map<Integer, DTOModulo> modulosMap;
	
	private List<DTOModulo> modulos;

	private List<DTOModulo> modulosList;
	
	public DTOSubmenu() {
		super();
	}
	
	public DTOSubmenu(Integer idSubMenu, String nombreSubMenu, Map<Integer, DTOModulo> modulosMap) {
		super();
		this.idSubMenu = idSubMenu;
		this.nombreSubMenu = nombreSubMenu;
		this.modulosMap = modulosMap;
	}



	public Integer getIdSubMenu() {
		return idSubMenu;
	}

	public void setIdSubMenu(Integer idSubMenu) {
		this.idSubMenu = idSubMenu;
	}

	public String getNombreSubMenu() {
		return nombreSubMenu;
	}

	public void setNombreSubMenu(String nombreSubMenu) {
		this.nombreSubMenu = nombreSubMenu;
	}

	public Map<Integer, DTOModulo> getModulosMap() {
		return modulosMap;
	}

	public void setModulosMap(Map<Integer, DTOModulo> modulosMap) {
		this.modulosMap = modulosMap;
	}
	
	public List<DTOModulo> getModulos() {
	    return modulosMap != null && !modulosMap.isEmpty() ? 
	    		new ArrayList<>(modulosMap.values()) : Collections.emptyList();
	}
	
	public List<DTOModulo> getModulosList() {
	    return getModulos();
	}
	
}
