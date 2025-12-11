import { Row, Col } from "antd";
import icono_boleta from "../../img/boleta.svg";
import pleca_1a_etapa from "../../img/pleca_1a_etapa.svg";
import pleca_2a_etapa from "../../img/pleca_2a_etapa.svg";
import { useSelector } from "react-redux/es/hooks/useSelector";

function HeaderModulo() {
  const modulo = useSelector((store) => store.menu.moduloSeleccionado);
  const etapaCapacitacion = useSelector(
    (store) => store.menu.etapaCapacitacion
  );

  return (
    <Row style={{ marginBottom: "22px" }}>
      <Col xs={24} md={24} xl={12}>
        <img className="align-boleta-header" src={icono_boleta} />
        <span>&ensp;</span>
        <span className="span-title-modulo">{modulo.nombreModulo}</span>
      </Col>

      <Col xs={24} md={24} xl={12} className="align-pleca-etapa">
        <img src={etapaCapacitacion == 2 ? pleca_2a_etapa : pleca_1a_etapa} />
      </Col>
    </Row>
  );
}

export default HeaderModulo;
