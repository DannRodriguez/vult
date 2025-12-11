package mx.ine.sustseycae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DTOMenu implements Serializable{

	private static final long serialVersionUID = -7022153502513095644L;

	private Integer idMenu;

	private String nombreMenu;
	
	private Map<Integer, DTOSubmenu> submenusMap;

	private List<DTOSubmenu> subMenus;
	
	public DTOMenu() {
		super();
	}

	public DTOMenu(Integer idMenu, String nombreMenu, Map<Integer, DTOSubmenu> submenusMap) {
		super();
		this.idMenu = idMenu;
		this.nombreMenu = nombreMenu;
		this.submenusMap = submenusMap;
	}

	public Integer getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	public String getNombreMenu() {
		return nombreMenu;
	}

	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}

	public Map<Integer, DTOSubmenu> getSubmenusMap() {
		return submenusMap;
	}

	public void setSubmenusMap(Map<Integer, DTOSubmenu> submenusMap) {
		this.submenusMap = submenusMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<DTOSubmenu> getSubMenus() {
	    return submenusMap != null && !submenusMap.isEmpty() ? 
	    		new ArrayList<>(submenusMap.values()) : Collections.emptyList();
	}
}
