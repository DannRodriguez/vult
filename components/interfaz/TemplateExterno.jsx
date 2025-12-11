import React from "react";
import { ConfigProvider, Layout } from "antd";
import esES from "antd/es/locale/es_ES";

import BlockLoader from "./Loader";
import logoIne from "../../img/logoIne.svg";
import imgBandaTitulo from "../../img/icono_header_login.svg";
import { TITULO_HEADER_LOGIN } from "../../utils/constantes";

const { Header, Content, Footer } = Layout;

class TemplateExterno extends React.Component {
  render() {
    const bloquear = this.props.bloqueado || this.props.cargando;

    return (
      <ConfigProvider locale={esES}>
        <BlockLoader
          blocking={bloquear && this.props.bloquearTodo}
          id="template_externo_bloq"
          bloquearTodo={true}
        >
          <Layout className="layout template_externo">
            <Header className="header_style_externo_supycap_login">
              <div style={{ display: "grid", gridTemplateColumns: "1fr 15fr" }}>
                <img
                  style={{ width: 132.86, height: 60 }}
                  src={imgBandaTitulo}
                  alt=""
                />
                <div
                  style={{
                    color: "#FFFFFF",
                    fontSize: "45px",
                    marginLeft: "15px",
                    fontFamily: "Roboto-Regular",
                  }}
                >
                  {TITULO_HEADER_LOGIN}
                </div>
              </div>
            </Header>
            <Content
              className={
                this.props.contenidoExpandido
                  ? "contenido_expandido"
                  : undefined
              }
              style={{ backgroundColor: "#f1f2f6" }}
            >
              <BlockLoader blocking={bloquear && !this.props.bloquearTodo}>
                {this.props.children}
              </BlockLoader>
            </Content>
            <Footer
              className="footer_style_externo_supycap"
              style={{ marginTop: 15 }}
            >
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "1fr 2fr 2fr 2fr 2fr 2fr",
                  color: "white",
                  fontSize: "12px",
                  fontFamily: "Roboto-Light",
                }}
              >
                <div>
                  <img id="logoIne" src={logoIne} alt="" />
                </div>
                <div style={{ display: "grid", alignContent: "center" }}>
                  INE México 2026
                </div>
                <div
                  style={{
                    display: "grid",
                    alignContent: "center",
                    justifyContent: "center",
                  }}
                >
                  Unidad Técnica de Servicios de Informática{" "}
                </div>
                <div
                  style={{
                    display: "grid",
                    alignContent: "center",
                    justifyContent: "center",
                  }}
                >
                  <a
                    href="https://www.ine.mx/wp-content/uploads/2023/06/deceyec-aviso-privacidad-integral-SE-CAE.pdf"
                    target="_blank"
                    id="aviso_privacidad"
                  >
                    Aviso de privacidad integral
                  </a>
                </div>
                <div
                  style={{
                    display: "grid",
                    alignContent: "center",
                    justifyContent: "center",
                  }}
                >
                  <a
                    href="https://www.ine.mx/wp-content/uploads/2023/06/deceyec-aviso-privacidad-simplificado-SE-CAE.pdf"
                    target="_blank"
                    id="aviso_privacidad"
                  >
                    Aviso de privacidad simplificado
                  </a>{" "}
                </div>
                <div
                  style={{
                    display: "grid",
                    alignContent: "center",
                    justifyContent: "right",
                  }}
                >
                  Versión {import.meta.env.VITE_APP_VERSION_COMMIT}
                </div>
              </div>
            </Footer>
          </Layout>
        </BlockLoader>
      </ConfigProvider>
    );
  }
}

export default TemplateExterno;
