import { Row, Col } from "antd";
import { useSelector } from "react-redux/es/hooks/useSelector";

function Miga() {
  const modulo = useSelector((store) => store.menu.moduloSeleccionado);

  return (
    <Row style={{ marginBottom: "25px" }}>
      <Col xs={24} md={24} xl={24}>
        <span className="span-miga-light">Inicio </span>
        <span className="span-miga-light">&ensp;/&ensp;</span>
        <span className="span-miga-medium">{modulo.nombreMigas}</span>
      </Col>
    </Row>
  );
}

export default Miga;
