import React from "react";
import { Row, Col, Radio, Select, Form } from "antd";
const { Option } = Select;

const VRadioSelect = ({
  title,
  radioNumber,
  causas,
  radioSeleccionado,
  seleccion,
  setSeleccionMotivo,
}) => {
  return (
    <Row>
      <Col xs={15} md={15} xl={7}>
        <Radio
          className="sub_titulos"
          style={{ marginTop: "10px" }}
          value={radioNumber}
        >
          {title}
        </Radio>
      </Col>
      <Col xs={10} md={15} xl={17}>
        <Form.Item
          name={`select${radioNumber}`}
          rules={[
            {
              required: radioSeleccionado == radioNumber,
              message: "Selecciona una opción*",
            },
          ]}
        >
          <Select
            name={`select${radioNumber}`}
            className="container_select"
            placeholder="Selecciona"
            value={radioSeleccionado === radioNumber ? seleccion : undefined}
            onChange={setSeleccionMotivo}
            disabled={radioSeleccionado !== radioNumber}
          >
            <Option key="default" value="" disabled>
              --Seleccionar
            </Option>
            {causas.map(({ id, descripcion }) => (
              <Option key={id.idCausaVacante}>
                {`${id.idCausaVacante} ${descripcion}`}
              </Option>
            ))}
          </Select>
        </Form.Item>
      </Col>
    </Row>
  );
};

export default VRadioSelect;
