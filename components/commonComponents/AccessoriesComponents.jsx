import { Row, Col } from "antd";
import { DoubleRightOutlined, InfoCircleOutlined } from "@ant-design/icons";

import icono_info from "../../img/icono_informativo.svg";
import icono_adver from "../../img/Icono_adver.svg";

export function Requerido() {
  return (
    <Row style={{ marginBottom: "22px" }}>
      <Col xs={24} md={24} xl={24}>
        <div className="bar-requerido">
          <span className="span-requerido"> Los campos con ( </span>
          <span className="star-requerido" />
          <span className="span-requerido">) son requeridos. </span>
        </div>
      </Col>
    </Row>
  );
}

export function HeaderSeccion({ text }) {
  return (
    <div style={{ marginBottom: "26px" }}>
      <DoubleRightOutlined className="icon-header-seccion" />{" "}
      <span className="span-header-seccion">{text}</span>
    </div>
  );
}

export function BarInfo({ text }) {
  return (
    <div className="div-bar-info">
      <div>
        <img src={icono_info} alt="" />
      </div>
      <div className="div-span-bar-info">
        <span className="span-bar-info">{text}</span>
      </div>
    </div>
  );
}

export function BarWarn({ text }) {
  return (
    <div className="div-bar-warn">
      <div>
        <img src={icono_adver} alt="" />
      </div>
      <div className="div-span-bar-info">
        <span className="span-bar-warn">{text}</span>
      </div>
    </div>
  );
}

export function InfoData({ info, data }) {
  return (
    <Col xs={24} md={12} xl={8} style={{ paddingRight: "20px" }}>
      <div>
        <div>
          <span className="span-info-gen">{info}</span>
        </div>
        <div>
          <span className="span-data-gen">{data}</span>
        </div>
        <br />
      </div>
    </Col>
  );
}

export function BarError({ text }) {
  return (
    <div className="div-bar-error">
      <div>
        <span className="span-bar-error" style={{ fontSize: "20px" }}>
          <InfoCircleOutlined />
        </span>
      </div>
      <div className="div-span-bar-error">
        <span className="span-bar-error">{text}</span>
      </div>
    </div>
  );
}
