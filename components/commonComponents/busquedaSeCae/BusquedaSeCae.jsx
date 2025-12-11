import React, { useEffect, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Col, Row, Form, Select } from "antd";

import { apiClientPost } from "../../../utils/apiClient";
import {
  selecciona,
  clearUser,
} from "../../../redux/selectByFolioNombre/selectByFolioNombreReducer";
import { llenaCombo } from "../../../utils/busquedaSeCae/funciones";

function BusquedaSeCae({ moduloSust, tipoFlujo, setIsPendiente }) {
  const dispatch = useDispatch();

  const isFirstRender = useRef(true);

  const [options, setOptions] = useState([]);

  const menu = useSelector((store) => store.menu);

  useEffect(() => {
    iniciaCarga();
    return () => {
      dispatch(clearUser());
    };
  }, []);

  useEffect(() => {
    if (isFirstRender.current) {
      isFirstRender.current = false;
    } else {
      iniciaCarga();
    }
    return () => {
      dispatch(clearUser());
    };
  }, [tipoFlujo]);

  function iniciaCarga() {
    dispatch(clearUser());
    const request = {
      idProceso: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      moduloSust,
      tipoFlujo,
    };
    apiClientPost("obtenerListaSustituido", request)
      .then((data) => {
        setOptions(llenaCombo(data.data));
      })
      .catch((error) => {});
  }

  function seleccionaAspirante(aspirante) {
    const request = {
      idProceso: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      moduloSust: moduloSust,
      idAspirante: aspirante.includes(",")
        ? aspirante.split(",")[1]
        : aspirante,
    };
    dispatch(selecciona(request));
  }

  return (
    <>
      <Row>
        <Col span={12}>
          <span style={{ color: "#D5007F" }}>*</span>
          <span
            style={{
              color: "#333333",
              fontSize: 16,
              fontFamily: "Roboto-Medium",
            }}
          >
            Nombre o número de folio:
          </span>
        </Col>
      </Row>
      <Row>
        <Col span={16} xs={24} md={12} xl={8}>
          <Form.Item
            label=""
            name="idSustituido"
            rules={[
              {
                required: true,
                message: "Selecciona la persona a sustituir.",
              },
            ]}
          >
            <Select
              style={{
                width: "100%",
              }}
              onChange={(value, option) => {
                if (value != undefined) {
                  setIsPendiente(option);
                  return seleccionaAspirante(value);
                }
                return null;
              }}
              options={options}
              showSearch
              placeholder="Búsqueda por folio o nombre"
              filterOption={(input, option) =>
                (option?.busqueda ?? "")
                  .toLowerCase()
                  .includes(input.toLowerCase())
              }
              allowClear={true}
              onClear={() => dispatch(clearUser())}
            />
          </Form.Item>
        </Col>
        <Col
          span={8}
          style={{
            width: 500,
          }}
        ></Col>
      </Row>
      <br />
    </>
  );
}

export default BusquedaSeCae;
