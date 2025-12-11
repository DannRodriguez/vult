import React from "react";
import { Radio, Space, Form } from "antd";
import VRadioSelect from "./VRadioSelect.jsx";
import {
  ETI_RESCISION,
  ETI_RENUNCIA,
  ETI_OTRAS,
} from "../../../utils/causaSustitucion/etiquetas.js";

export function CRadioGroupSelect({
  causas,
  seleccion,
  setSeleccionMotivo,
  setSelecRadio,
  radioSeleccionado,
  formRef,
  disabledRadios,
  idCausasNOMostrar,
}) {
  const onChangeRadio = (e) => {
    if (formRef) {
      formRef.current.setFields([
        { name: "select1", errors: [], value: undefined },
      ]);
      formRef.current.setFields([
        { name: "select2", errors: [], value: undefined },
      ]);
      formRef.current.setFields([
        { name: "select3", errors: [], value: undefined },
      ]);
    }
    setSelecRadio(e);
  };

  const listRadioSelect = [
    {
      title: ETI_RESCISION,
      radioNumber: 1,
      causas: causas.filter(({ id }) => id.tipoCausaVacante === 1),
    },
    {
      title: ETI_RENUNCIA,
      radioNumber: 2,
      causas: causas.filter(({ id }) => id.tipoCausaVacante === 2),
    },
    {
      title: ETI_OTRAS,
      radioNumber: 3,
      causas: causas.filter(
        ({ id }) =>
          id.tipoCausaVacante === 3 &&
          !idCausasNOMostrar.find((x) => x === id.idCausaVacante)
      ),
    },
  ];

  return (
    <Form.Item
      name="radios"
      rules={[{ required: true, message: "Selecciona una opción*" }]}
    >
      <Radio.Group
        name="radios"
        style={{ display: "flex", flexDirection: "column", gap: "16px" }}
        onChange={onChangeRadio}
        disabled={disabledRadios}
      >
        <Space direction="vertical">
          {listRadioSelect.map(({ title, radioNumber, causas }) => (
            <VRadioSelect
              key={radioNumber}
              title={title}
              radioNumber={radioNumber}
              causas={causas}
              radioSeleccionado={radioSeleccionado}
              seleccion={seleccion}
              setSeleccionMotivo={setSeleccionMotivo}
            />
          ))}
        </Space>
      </Radio.Group>
    </Form.Item>
  );
}
