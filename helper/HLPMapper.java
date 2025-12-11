package mx.ine.sustseycae.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.ine.sustseycae.dto.DTOAccionesModulo;
import mx.ine.sustseycae.dto.DTOMenu;
import mx.ine.sustseycae.dto.DTOModulo;
import mx.ine.sustseycae.dto.DTOSubmenu;
import mx.ine.sustseycae.dto.vo.VOMenu;

public class HLPMapper {
	private HLPMapper() {
		throw new IllegalStateException("HLPMapper is an utility class");
	}
	
	public static List<DTOMenu> mapperMenuLateral(List<VOMenu> menus, boolean isAcciones) {
		Map<Integer, DTOMenu> menuMap = new TreeMap<>();
		
		for(VOMenu menu : menus) {
			Integer idMenu = HLPEncoder.encode(menu.getIdMenu());
			Integer idSubmenu = HLPEncoder.encode(menu.getIdSubmenu());
			Integer idModulo = HLPEncoder.encode(menu.getIdModulo());
			
			menuMap.computeIfAbsent(idMenu, 
									k -> new DTOMenu(k, HLPEncoder.encode(menu.getNombreMenu()), new TreeMap<>()));
			Map<Integer, DTOSubmenu> submenus = menuMap.get(idMenu).getSubmenusMap();
			submenus.computeIfAbsent(idSubmenu, 
									k -> new DTOSubmenu(k, HLPEncoder.encode(menu.getNombreSubmenu()), new TreeMap<>()));
			Map<Integer, DTOModulo> modulos = submenus.get(idSubmenu).getModulosMap();
			modulos.computeIfAbsent(idModulo, 
									k -> new DTOModulo(k, 
											HLPEncoder.encode(menu.getNombreModulo()), 
											HLPEncoder.encode(menu.getUrlModulo()),
											HLPEncoder.encode(menu.getIdAccion()),
											HLPEncoder.encode(menu.getAccionDescrip()),
											HLPEncoder.encode(menu.getTipoJunta()),
											HLPEncoder.encode(menu.getEstatus()),
											new TreeMap<>()));
			
			if(isAcciones) {
				Map<Integer, DTOAccionesModulo> acciones = modulos.get(idModulo).getAccionesModuloMap();
				acciones.computeIfAbsent(HLPEncoder.encode(menu.getIdAccion()), 
										k -> new DTOAccionesModulo(HLPEncoder.encode(menu.getUrlModulo()),
																k,
																HLPEncoder.encode(menu.getAccionDescrip()),
																HLPEncoder.encode(menu.getTipoJunta()),
																HLPEncoder.encode(menu.getEstatus())));
			}
			
		}
		
		return new ArrayList<>(menuMap.values());
	}
}
