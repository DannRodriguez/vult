import { Button, Form, Input, Spin, Modal } from "antd";
import { ExclamationCircleFilled } from "@ant-design/icons";
import { useDispatch, useSelector } from "react-redux";
import { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

import TemplateExterno from "../interfaz/TemplateExterno.jsx";

import {
  login,
  clearErrorMessage,
  forceLogout,
} from "../../redux/loginUser/loginUserReducer.js";
import { obtenerNivelUsr } from "../../redux/menu/menuReducer.js";
import { mensaje_codigo_205 } from "../../utils/login/etiquetas.js";
import * as constantes from "../../utils/constantes";

import graficoHome from "../../img/grafico_home.svg";
import iconoUsuario from "../../img/icono_usuario.svg";
import iconoPassword from "../../img/icono_password.svg";
import iconoSust from "../../img/Icono_sust.svg";

import "../../css/login.css";

const { confirm } = Modal;

function Login() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu);
  const errorLogin = useSelector((store) => store.loginUser.errorLogin);
  const loadingUser = useSelector(
    (store) => store.loginUser.isLoadingLoginUserReducer
  );

  const formLogin = useRef();

  const loginAction = (values) => {
    const dataLogin = {
      usuario: values.username,
      password: values.password,
      idSistema: constantes.ID_SISTEMA,
    };
    dispatch(login(dataLogin));
  };

  useEffect(() => {
    dispatch(clearErrorMessage());
  }, []);

  useEffect(() => {
    if (user != null) {
      if (menu.rolUsuario === null) dispatch(obtenerNivelUsr(user.rolUsuario));
      navigate("/home");
    }
  }, [user]);

  useEffect(() => {
    if (errorLogin != null) {
      setTimeout(() => {
        dispatch(clearErrorMessage());
      }, 10000);
    }
  }, [errorLogin]);

  const showCloseSesionConfirm = () => {
    confirm({
      title: "¿Está seguro de cerrar la sesión actualmente activa?",
      icon: <ExclamationCircleFilled />,
      content:
        "Se cerrará la sesión existente en otros navegadores o dispositivos. Una vez que el proceso se complete coloque de nuevo sus datos de usuario y contraseña.",
      okText: "Cerrar sesión",
      okType: "danger",
      cancelText: "Cancelar",
      onOk() {
        const data = {
          usuario: formLogin.current.getFieldValue("username"),
          password: formLogin.current.getFieldValue("password"),
          idSistema: constantes.ID_SISTEMA,
        };
        dispatch(forceLogout(data)).then(() => {
          formLogin.current.resetFields();
        });
      },
      onCancel() {
        console.log("Cancel");
      },
    });
  };

  return (
    <TemplateExterno>
      <div className="grid-login">
        <div className="imagen_sustituciones">
          <img style={{ marginLeft: "50px" }} src={graficoHome} alt="" />
        </div>

        <div
          className="formulario_login"
          style={{
            display: "grid",
            background: "white",
            marginRight: "80px",
            justifyContent: "center",
            boxShadow: "0px 3px 6px #00000029",
            alignContent: "center",
            justifyItems: "center",
            marginLeft: "50px",
          }}
        >
          <img src={iconoSust} alt="" />
          <span
            style={{
              fontSize: "29px",
              color: "#333333",
              fontFamily: "Roboto-Medium",
              marginTop: "10px",
              marginBottom: "20px",
            }}
          >
            {constantes.TITULO_HEADER_LOGIN}
          </span>
          <Form
            name="basic"
            labelCol={{
              span: 20,
            }}
            wrapperCol={{
              span: 20,
            }}
            style={{
              marginLeft: "30px",
              marginRight: "30px",
            }}
            initialValues={{
              remember: true,
            }}
            ref={formLogin}
            onFinish={loginAction}
            autoComplete="off"
          >
            <Form.Item
              name="username"
              rules={[
                {
                  required: true,
                  message: "Ingresar usuario",
                },
              ]}
            >
              <Input
                style={{ width: 302, marginBottom: "12px" }}
                placeholder="Usuario"
                prefix={<img src={iconoUsuario} alt="" />}
              />
            </Form.Item>

            <Form.Item
              name="password"
              rules={[
                {
                  required: true,
                  message: "Ingresar contraseña",
                },
              ]}
            >
              <Input.Password
                className="custom-password-input"
                placeholder="Contraseña"
                prefix={<img src={iconoPassword} alt="" />}
              />
            </Form.Item>

            {errorLogin != (null || "") ? (
              <label className="etiquetas_warnings_login">{errorLogin}</label>
            ) : (
              <></>
            )}
            {!loadingUser ? (
              <>
                {errorLogin === mensaje_codigo_205 ? (
                  <Button
                    onClick={showCloseSesionConfirm}
                    size="medium"
                    type="primary"
                    style={{ minWidth: 306 }}
                    danger
                  >
                    Cerrar sesión existente
                  </Button>
                ) : (
                  <Form.Item
                    wrapperCol={{
                      offset: 8,
                      span: 16,
                    }}
                  >
                    <Button
                      type="primary"
                      htmlType="submit"
                      style={{ fontFamily: "Roboto-Light" }}
                    >
                      {constantes.INCIA_SESION}
                    </Button>
                  </Form.Item>
                )}
              </>
            ) : (
              <div style={{ backgroundColor: "white" }}>
                <Spin style={{ color: "white" }} tip="Validando" size="large" />
              </div>
            )}
          </Form>
          <span style={{ color: "white" }}>
            Rev. {import.meta.env.VITE_APP_VERSION_COMMIT},{" "}
            {import.meta.env.VITE_APP_VERSION_DATE_TIME}{" "}
          </span>
        </div>
      </div>
    </TemplateExterno>
  );
}

export default Login;
