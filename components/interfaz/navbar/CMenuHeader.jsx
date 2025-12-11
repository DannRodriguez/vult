import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Menu } from "antd";
import {
  asignarModulo,
  obtieneMenuAcciones,
} from "../../../redux/menu/menuReducer.js";
import barlat from "../../../img/bar-lat-thin.png";
import icon_reportes from "../../../img/reportes.svg";
import { useNavigate } from "react-router-dom";
import { ID_SISTEMA } from "../../../utils/constantes";
import { clearUser } from "../../../redux/selectByFolioNombre/selectByFolioNombreReducer";

const { SubMenu } = Menu;
const MENU_REPORTES = "Reportes";

function CMenuHeader() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu.opcionesMenu);
  const modulo = useSelector((store) => store.menu.moduloSeleccionado);
  const menuAcciones = useSelector((store) => store.menu.menuAcciones);
  const estado = useSelector((store) => store.menu.estado);
  const proceso = useSelector((store) => store.menu.proceso);
  const distrito = useSelector((store) => store.menu.distrito);
  const [current, setCurrent] = useState("");

  useEffect(() => {
    if (modulo?.idMenu && modulo?.idSubMenu) {
      setCurrent(`${modulo.idMenu}-${modulo.idSubMenu}`);
    } else {
      setCurrent("");
    }
  }, [modulo]);

  const handleClick = (e) => {
    const menuSeleccionado = e.key.split("-");
    const idMenu = parseInt(menuSeleccionado[0]);
    const idSubMenu = parseInt(menuSeleccionado[1]);
    const modulo = Object.values(menu[idMenu].subMenus[idSubMenu].modulos)[0];
    const nombreModulo = menu[idMenu].subMenus[idSubMenu].nombreSubMenu;
    const nombreMigas =
      menu[idMenu].nombreMenu +
      " - " +
      menu[idMenu].subMenus[idSubMenu].nombreSubMenu;

    setCurrent(`${idMenu}-${idSubMenu}`);

    let param = {
      request: {
        idSistema: ID_SISTEMA,
        idProceso: parseInt(proceso?.idProcesoElectoral),
        idDetalle: parseInt(proceso?.idDetalleProceso),
        idEstado: parseInt(estado?.idEstado),
        idDistrito: parseInt(distrito?.idDistrito),
        idMunicipio: 0,
        grupoSistema: user.rolUsuario,
        idModulo: parseInt(modulo?.idModulo),
      },
      tokenAuth: user.tknA,
    };
    dispatch(obtieneMenuAcciones(param));
    dispatch(clearUser());
    dispatch(
      asignarModulo({
        idMenu,
        idSubMenu,
        idModulo: parseInt(modulo.idModulo),
        nombreModulo: nombreModulo,
        nombreMigas: nombreMigas,
      })
    );
  };
  useEffect(() => {
    if (modulo?.idMenu === 6) {
      const moduloSeleccionado = Object.values(
        menu[modulo?.idMenu].subMenus[modulo?.idSubMenu].modulos
      )[0];
      navigate(moduloSeleccionado.urlModulo);
    } else {
      if (
        menuAcciones != null &&
        menuAcciones !== undefined &&
        menuAcciones.length === 0
      ) {
        if (modulo?.idMenu && modulo?.idSubMenu) {
          navigate("/moduloCerrado");
        }
      } else if (
        menuAcciones != null &&
        menuAcciones !== undefined &&
        menuAcciones.length > 0
      ) {
        if (modulo?.idMenu && modulo?.idSubMenu) {
          const moduloSeleccionado = Object.values(
            menu[modulo?.idMenu].subMenus[modulo?.idSubMenu].modulos
          )[0];
          navigate(moduloSeleccionado.urlModulo);
        }
      }
    }
  }, [menuAcciones]);

  return (
    <div className="menu-header-container">
      {menu ? (
        <Menu
          id="menuHeader"
          key={"menu"}
          onClick={handleClick}
          selectedKeys={[current]}
          mode="horizontal"
          disabledOverflow={true}
        >
          {Object.entries(menu).map(([keyMenu, valueMenu]) => {
            return (
              <>
                <SubMenu
                  key={keyMenu}
                  className={"border-submenu"}
                  style={{
                    paddingLeft:
                      valueMenu.nombreMenu == MENU_REPORTES ? "0px" : "",
                    float: valueMenu.nombreMenu == MENU_REPORTES ? "Right" : "",
                  }}
                  icon={
                    <>
                      <img
                        style={{
                          display:
                            valueMenu.nombreMenu != MENU_REPORTES ? "none" : "",
                          marginRight: "20px",
                        }}
                        src={barlat}
                      />
                      <img
                        style={{
                          display:
                            valueMenu.nombreMenu != MENU_REPORTES ? "none" : "",
                          marginRight: "10px",
                          marginTop: "-5px",
                        }}
                        src={icon_reportes}
                      />
                    </>
                  }
                  title={
                    <>
                      <span className="span-menu-title">
                        {valueMenu.nombreMenu}
                      </span>
                    </>
                  }
                >
                  {valueMenu.subMenus
                    ? Object.entries(valueMenu.subMenus).map(
                        ([keySubMenu, valueSubMenu]) => {
                          return (
                            <React.Fragment key={keySubMenu}>
                              <Menu.Item
                                key={`${keyMenu}-${keySubMenu}`}
                                className="span-submenu-title"
                              >
                                {valueSubMenu.nombreSubMenu}
                              </Menu.Item>
                              <Menu.Divider />
                            </React.Fragment>
                          );
                        }
                      )
                    : ""}
                </SubMenu>
                <img
                  style={{
                    display: valueMenu.nombreMenu == "Reportes" ? "none" : "",
                  }}
                  src={barlat}
                />
              </>
            );
          })}
        </Menu>
      ) : (
        ""
      )}
    </div>
  );
}

export default CMenuHeader;
