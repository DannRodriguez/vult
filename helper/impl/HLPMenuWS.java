package mx.ine.sustseycae.helper.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.ine.parametrizacion.model.dto.DTOAccionModulo;
import mx.ine.parametrizacion.model.dto.DTOMenu;
import mx.ine.parametrizacion.model.dto.DTOModulo;
import mx.ine.parametrizacion.model.dto.DTOSubmenu;

public class HLPMenuWS implements Serializable {

    public static final String ID_SUB_MENU = "idSubMenu";
    public static final String NOMBRE_SUB_MENU = "nombreSubMenu";
    public static final String ID_MODULO = "idModulo";
    public static final String URL_MODULO = "urlModulo";
    public static final String NOMBRE_MODULO = "nombreModulo";
    public static final String ID_ACCION = "idAccion";
    public static final String ACCION_DESCRIP = "accionDescrip";
    public static final String TIPO_JUNTA = "tipoJunta";
    public static final String ESTATUS = "estatus";
    public static final String ID_MENU = "idMenu";
    public static final String NOMBRE_MENU = "nombreMenu";

    private HLPMenuWS() {
        throw new IllegalStateException("HLPMenuWS is an utility class");
    }

    public static List<DTOMenu> mapperMenuLateralWS(String response) throws JacksonException {

        List<DTOMenu> lista = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);

        JsonNode data = jsonNode.get("data");

        if (data != null && data.isArray()) {
            data.forEach(node -> {
                Map<Integer, DTOSubmenu> treeMapSub = new TreeMap<>();
                DTOMenu dtoMenu = new DTOMenu(
                        node.get(ID_MENU) == null ? 0 : node.get(ID_MENU).intValue(),
                        node.get(NOMBRE_MENU) == null ? "" : node.get(NOMBRE_MENU).textValue(),
                        treeMapSub);

                Map<Integer, DTOSubmenu> submenus = dtoMenu.getSubmenusMap();
                JsonNode nodeSubMenu = node.get("submenusMap");

                if (nodeSubMenu != null && nodeSubMenu.isArray()) {
                    nodeSubMenu.forEach(nodeSub -> {
                        Map<Integer, DTOModulo> treeMapModulos = new TreeMap<>();
                        DTOSubmenu dtoSubmenu = new DTOSubmenu(
                                nodeSub.get(ID_SUB_MENU) == null ? 0 : nodeSub.get(ID_SUB_MENU).intValue(),
                                nodeSub.get(NOMBRE_SUB_MENU) == null ? "" : nodeSub.get(NOMBRE_SUB_MENU).textValue(),
                                treeMapModulos);

                        Map<Integer, DTOModulo> modulos = dtoSubmenu.getModulosMap();
                        JsonNode nodeModulo = nodeSub.get("modulosMap");

                        if (nodeModulo != null && nodeModulo.isArray()) {
                            nodeModulo.forEach(nodeMod -> {
                                Map<Integer, DTOAccionModulo> treeMapAcciones = new TreeMap<>();
                                DTOModulo dtoModulo = new DTOModulo(
                                        nodeMod.get(ID_MODULO) == null ? 0 : nodeMod.get(ID_MODULO).intValue(),
                                        nodeMod.get(NOMBRE_MODULO) == null ? ""
                                        : nodeMod.get(NOMBRE_MODULO).textValue(),
                                        nodeMod.get(URL_MODULO) == null ? "" : nodeMod.get(URL_MODULO).textValue(),
                                        nodeMod.get(ID_ACCION) == null ? 0 : nodeMod.get(ID_ACCION).intValue(),
                                        nodeMod.get(ACCION_DESCRIP) == null ? ""
                                        : nodeMod.get(ACCION_DESCRIP).textValue(),
                                        nodeMod.get(TIPO_JUNTA) == null ? "" : nodeMod.get(TIPO_JUNTA).textValue(),
                                        nodeMod.get(ESTATUS) == null ? "" : nodeMod.get(ESTATUS).textValue(),
                                        treeMapAcciones);

                                JsonNode nodeAcciones = nodeMod.get("listUrlModulos");
                                if (nodeAcciones != null && nodeAcciones.isArray() && !nodeAcciones.isEmpty()) {
                                    Map<Integer, DTOAccionModulo> acciones = dtoModulo.getAccionesModuloMap();

                                    nodeAcciones.forEach(nodeAccion -> {
                                        DTOAccionModulo dtoAccion = new DTOAccionModulo(
                                                nodeAccion.get(URL_MODULO) == null ? null
                                                : nodeAccion.get(URL_MODULO).textValue(),
                                                nodeAccion.get(ID_ACCION) == null ? null
                                                : nodeAccion.get(ID_ACCION).intValue(),
                                                nodeAccion.get(ACCION_DESCRIP) == null ? null
                                                : nodeAccion.get(ACCION_DESCRIP).textValue(),
                                                nodeAccion.get(TIPO_JUNTA) == null ? null
                                                : nodeAccion.get(TIPO_JUNTA).textValue(),
                                                nodeAccion.get(ESTATUS) == null ? null
                                                : nodeAccion.get(ESTATUS).textValue());

                                        acciones.put(
                                                nodeAccion.get(ID_ACCION) == null ? 0
                                                : nodeAccion.get(ID_ACCION).intValue(),
                                                dtoAccion);
                                    });
                                }
                                modulos.put(
                                        nodeMod.get(ID_MODULO) == null ? 0 : nodeMod.get(ID_MODULO).intValue(),
                                        dtoModulo);
                            });
                        }
                        submenus.put(
                                nodeSub.get(ID_SUB_MENU) == null ? 0 : nodeSub.get(ID_SUB_MENU).intValue(),
                                dtoSubmenu);
                    });
                }
                lista.add(dtoMenu);
            });
        }
        return lista;
    }

    public static List<DTOAccionModulo> mapperMenuAcciones(Integer idModulo, List<DTOMenu> menu) {
        List<DTOAccionModulo> acciones = new ArrayList<>();
        if (menu != null && !menu.isEmpty()) {
            Iterator iterator = menu.iterator();
            while (iterator.hasNext()) {
                DTOMenu dtoMenu = (DTOMenu) iterator.next();
                if (!dtoMenu.getSubMenus().isEmpty()) {

                    List<DTOModulo> modulos = dtoMenu.getSubMenus().stream()
                            .map(x -> x.getModulos())
                            .flatMap(y -> y.stream()).collect(Collectors.toList());

                    if (modulos != null && !modulos.isEmpty()) {
                        List<DTOAccionModulo> listAcciones = modulos.stream()
                                .filter(x -> x.getIdModulo().equals(idModulo))
                                .map(x -> x.getListUrlModulos())
                                .flatMap(y -> y.stream())
                                .collect(Collectors.toList());

                        if (listAcciones != null && !listAcciones.isEmpty()) {
                            acciones = listAcciones.stream().filter(
                                    accion -> (accion.getIdAccion().equals(2) && (accion.getEstatus().trim().equals("A")
                                    || accion.getEstatus().trim().equals("C")))
                                    || (!accion.getIdAccion().equals(2) && accion.getEstatus().trim().equals("A")))
                                    .collect(Collectors.toList());

                            break;
                        }
                    }
                }
            }
        }
        return acciones;
    }
}
