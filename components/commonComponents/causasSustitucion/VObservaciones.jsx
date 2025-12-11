import React from "react";
import { Form, Input } from "antd";

const { TextArea } = Input;

const VObservaciones = ({
  observaciones,
  charCount,
  maxLength,
  setObservaciones,
}) => {
  return (
    <>
      <Form.Item
        name="observaciones"
        initialValue={observaciones}
        rules={[
          {
            pattern: new RegExp(/^[A-Za-z0-9ÁÉÍÓÚÑáéíóúñ#.\s]{0,500}$/),
            message: "Formato incorrecto",
          },
        ]}
      >
        <TextArea
          rows={5}
          maxLength={maxLength}
          onChange={setObservaciones}
          showCount={false}
        />
      </Form.Item>
      <span
        style={{
          fontStyle: "italic",
          color: maxLength - charCount <= 10 ? "red" : "#B6B6B6",
          fontSize: "14px",
          marginTop: "20px",
        }}
      >
        {`${maxLength - charCount} Caracteres restantes`}
      </span>
    </>
  );
};

export default VObservaciones;
