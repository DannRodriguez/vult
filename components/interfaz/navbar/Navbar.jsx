import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import logoSistema from "../../../img/logo_sustseycae.png";
import IconoUsuario from "../../../img/icono_usuario.svg";
import IconoCerrarSesion from "../../../img/cerrar_sesion.svg";
import iconoAyuda from "../../../img/icono_ayuda.svg";
import { Layout } from "antd";
import { URL_CENTRO_AYUDA } from "../../../utils/constantes";
import { logout } from "../../../redux/loginUser/loginUserReducer";
import { Loader } from "../Loader";

const { Header } = Layout;

const Navbar = () => {
  const dispatch = useDispatch();
  const user = useSelector((store) => store.loginUser.currentUser);
  const [loading, setLoading] = useState(false);

  const buttonAction = () => {
    setLoading(true);
    dispatch(logout());
  };

  return (
    <nav>
      <Header className="header-sistema">
        <Loader blocking={loading} />
        <div className="content-nav-header">
          <div className="align-first-segment-nav">
            <div>
              <img src={logoSistema} alt="" width={"50%"} />
            </div>
          </div>

          <div className="align-second-segment-nav">
            <div className="div-segment-info-nav">
              <div className="div-info-nav">
                <a target="_blank" href={URL_CENTRO_AYUDA}>
                  <img
                    style={{ width: "25px", height: "25px" }}
                    src={iconoAyuda}
                    title="Centro de ayuda"
                    alt="Centro de ayuda"
                  />
                </a>
              </div>
            </div>

            <div className="div-segment-sesion-nav">
              <div className="div-user-nav">
                <img src={IconoUsuario} />
                <span className="span-user-nav">
                  {user ? user.username : ""}
                </span>
              </div>

              <div className="div-separator" />

              <div className="div-logout-nav">
                <div style={{ display: "flex" }}>
                  <div>
                    <img src={IconoCerrarSesion} onClick={buttonAction} />
                  </div>
                  <div style={{ paddingTop: "1px", minWidth: "110px" }}>
                    <a onClick={buttonAction}>
                      <div style={{ marginLeft: "8px" }}>
                        <span>Cerrar sesión </span>
                      </div>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Header>
    </nav>
  );
};

export default Navbar;
