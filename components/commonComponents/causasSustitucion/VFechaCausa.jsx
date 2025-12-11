import React from "react";
import dayjs from "dayjs";
import { Form, DatePicker } from "antd";

import { ACCION_MODIFICA } from "../../../utils/constantes.js";
import { FECHA_PLACEHOLDER } from "../../../utils/causaSustitucion/etiquetas";

const VFechaCausa = ({ tipoFlujo, disableDate, fechaBaja, setFecha }) => {
  return (
    <Form.Item
      name="fechaCausa"
      rules={[
        {
          required: !fechaBaja,
          message: "Selecciona una fecha*",
        },
      ]}
    >
      <DatePicker
        className="date_picker"
        placeholder={FECHA_PLACEHOLDER}
        format="DD/MM/YYYY"
        onChange={setFecha}
        initialValues={
          tipoFlujo == ACCION_MODIFICA
            ? fechaBaja
              ? dayjs(fechaBaja, "DD/MM/YYYY")
              : null
            : ""
        }
        disabledDate={disableDate}
        inputReadOnly
      />
    </Form.Item>
  );
};

export default VFechaCausa;
