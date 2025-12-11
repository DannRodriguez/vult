import { DatePicker, Form, Row, Col } from "antd";
import moment from "moment";
import {
  ACCION_CAPTURA,
  ACCION_CONSULTA,
  ACCION_MODIFICA,
} from "../../../utils/constantes";
import * as etiquetas from "./../../../utils/busquedaSustituto/etiquetas";

export const tablaCapacitador = (
  datosSustituto,
  onChageDate_,
  tipoFlujo,
  infoSustitucion,
  fechaInicio,
  fechaFin,
  fechaSustitucionAnterior
) => {
  const onChangeDate = (date, dateString) => {
    onChageDate_(dateString);
  };
  const getFecha = (date) => {
    let fecha = new Date(date);
    return `${fecha.getDate()}/${month(
      fecha.getMonth()
    )}/${fecha.getFullYear()}`;
  };
  const month = (mes) => {
    let meses = [
      "Enero",
      "Febrero",
      "Marzo",
      "Abril",
      "Mayo",
      "Junio",
      "Julio",
      "Agosto",
      "Septiembre",
      "Octubre",
      "Noviembre",
      "Diciembre",
    ];
    return meses.at(mes);
  };
  const disabledDate = (current) => {
    let fecha = moment();
    let fechaEnd = current.isAfter(fechaFin) ? fechaFin : fecha;
    return (
      current &&
      (current.isBefore(fechaInicio, "day") || current.isAfter(fechaEnd, "day"))
    );
  };
  const validateDate = (_, value) => {
    let fechaStr = "";
    if (
      fechaSustitucionAnterior !== null &&
      fechaSustitucionAnterior !== undefined
    ) {
      let fechaArry = fechaSustitucionAnterior.split("/");
      let mes =
        parseInt(fechaArry[1], 10) < 10 ? `0${fechaArry[1]}` : fechaArry[1];
      fechaStr = `${fechaArry[0]}/${mes}/${fechaArry[2]}`;
    }
    let fechaSustAnteriorMoment = moment(fechaStr, "DD/MM/YYYY");
    if (!value) {
      return Promise.reject(new Error("Por favor selecciona una fecha."));
    }
    if (value.isBefore(fechaSustAnteriorMoment, "day")) {
      return Promise.reject(
        new Error("La fecha de alta no puede ser anterior a la fecha de baja.")
      );
    }
    return Promise.resolve();
  };
  return (
    <Col xs={24} md={16} xl={19}>
      <Row>
        <Col xs={24} md={6} xl={6} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.folio}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.folioAspirante}
          </span>
        </Col>
        <Col xs={24} md={18} xl={10} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.nombre_aspirante}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.nombreAspirante}
          </span>
        </Col>
        <Col xs={24} md={24} xl={8} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.clave_elector}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.claveElectorFuar}
          </span>
        </Col>
      </Row>
      <Row>
        <Col xs={24} md={6} xl={6} style={{ padding: "5px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.cargo_actual}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.cargoActual}
          </span>
        </Col>
        <Col xs={24} md={18} xl={18} style={{ padding: "5px 5px 10px 0px" }}>
          {datosSustituto?.idPuesto === 2 ? (
            <div>
              <span className="fuente_datos_aspirante_header">
                {etiquetas.are}
              </span>
              <br />
              <span className="fuente_datos_aspirante_contenido">
                {datosSustituto?.numAre}
              </span>
            </div>
          ) : (
            <div />
          )}
        </Col>
      </Row>
      <Row>
        <Col xs={12} md={8} xl={5} style={{ padding: "5px 5px 10px 0px" }}>
          {(tipoFlujo === ACCION_CAPTURA ||
            tipoFlujo === ACCION_MODIFICA ||
            tipoFlujo === undefined) && (
            <Form.Item
              layout="vertical"
              label={
                <div className="fuente_datos_aspirante_header">
                  {etiquetas.fecha_alta}:
                </div>
              }
              name={`fechaSustCapacitador-${datosSustituto?.folioAspirante}`}
              rules={[
                { required: true, message: "Ingresar datos" },
                { validator: validateDate },
              ]}
            >
              <DatePicker
                style={{ width: "100%" }}
                disabledDate={(current) => disabledDate(current)}
                className="calendar"
                placeholder={"dd/mm/aaaa"}
                onChange={onChangeDate}
                format="DD/MM/YYYY"
              />
            </Form.Item>
          )}
          {tipoFlujo === ACCION_CONSULTA && (
            <div>
              <span className="fuente_datos_aspirante_header">
                {etiquetas.fecha_alta}:
              </span>
              <br />
              <span className="fuente_datos_aspirante_contenido">
                {infoSustitucion !== null && infoSustitucion !== undefined
                  ? infoSustitucion.fechaAlta !== null &&
                    infoSustitucion.fechaAlta !== undefined
                    ? infoSustitucion.strFechaAlta
                      ? infoSustitucion.strFechaAlta
                      : getFecha(infoSustitucion.fechaAlta)
                    : ""
                  : ""}
              </span>
            </div>
          )}
        </Col>
      </Row>
    </Col>
  );
};

export const tablaSupervisor = (
  datosSustituto,
  onChangeDate_,
  tipoFlujo,
  infoSustitucion,
  fechaInicio,
  fechaFin,
  fechaSustitucionAnterior
) => {
  const onChangeDate = (date, dateString) => {
    onChangeDate_(dateString);
  };
  const getFecha = (date) => {
    let fecha = new Date(date);
    return `${fecha.getDate()}/${month(
      fecha.getMonth()
    )}/${fecha.getFullYear()}`;
  };
  const month = (mes) => {
    let meses = [
      "Enero",
      "Febrero",
      "Marzo",
      "Abril",
      "Mayo",
      "Junio",
      "Julio",
      "Agosto",
      "Septiembre",
      "Octubre",
      "Noviembre",
      "Diciembre",
    ];
    return meses.at(mes);
  };
  const disabledDate = (current) => {
    let fecha = moment();
    let fechaEnd = current.isAfter(fechaFin) ? fechaFin : fecha;
    return (
      current &&
      (current.isBefore(fechaInicio, "day") || current.isAfter(fechaEnd, "day"))
    );
  };
  const validateDate = (_, value) => {
    let fechaSustAnteriorMoment = moment(
      fechaSustitucionAnterior,
      "DD/MM/YYYY"
    );
    if (!value) {
      return Promise.reject(new Error("Por favor selecciona una fecha."));
    }
    if (value.isBefore(fechaSustAnteriorMoment, "day")) {
      return Promise.reject(
        new Error("La fecha de alta no puede ser anterior a la fecha de baja.")
      );
    }
    return Promise.resolve();
  };
  return (
    <Col xs={24} md={16} xl={19}>
      <Row>
        <Col xs={24} md={6} xl={6} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.folio}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto.folioAspirante}
          </span>
        </Col>
        <Col xs={24} md={18} xl={10} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.nombre_aspirante}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto.nombreAspirante}
          </span>
        </Col>
        <Col xs={24} md={24} xl={8} style={{ padding: "0px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.clave_elector}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.claveElectorFuar}
          </span>
        </Col>
      </Row>
      <Row>
        <Col xs={24} md={6} xl={6} style={{ padding: "5px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.cargo_actual}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.cargoActual}
          </span>
        </Col>
        <Col xs={24} md={6} xl={10} style={{ padding: "5px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">
            {etiquetas.zore}
          </span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.numZore}
          </span>
        </Col>
        <Col xs={24} md={12} xl={8} style={{ padding: "5px 5px 10px 0px" }}>
          <span className="fuente_datos_aspirante_header">{etiquetas.are}</span>
          <br />
          <span className="fuente_datos_aspirante_contenido">
            {datosSustituto?.ares}
          </span>
        </Col>
      </Row>
      <Row>
        <Col xs={12} md={8} xl={5} style={{ padding: "5px 5px 10px 0px" }}>
          {(tipoFlujo === ACCION_CAPTURA ||
            tipoFlujo === ACCION_MODIFICA ||
            tipoFlujo === undefined) && (
            <Form.Item
              layout="vertical"
              label={
                <span className="fuente_datos_aspirante_header">
                  {etiquetas.fecha_alta}:
                </span>
              }
              name="fechaSustSupervisor"
              rules={[
                { required: true, message: "Ingresar datos" },
                { validator: validateDate },
              ]}
            >
              <DatePicker
                style={{ width: "100%" }}
                disabledDate={(current) => disabledDate(current)}
                className="calendar"
                placeholder={"dd/mm/aaaa"}
                onChange={onChangeDate}
                format="DD/MM/YYYY"
              />
            </Form.Item>
          )}
          {tipoFlujo === ACCION_CONSULTA && (
            <span>
              <span className="fuente_datos_aspirante_header">
                {etiquetas.fecha_alta}:
              </span>
              <br />
              <span className="fuente_datos_aspirante_contenido">
                {infoSustitucion !== null && infoSustitucion !== undefined
                  ? infoSustitucion.fechaAlta !== null &&
                    infoSustitucion.fechaAlta !== undefined
                    ? infoSustitucion.strFechaAlta
                      ? infoSustitucion.strFechaAlta
                      : getFecha(infoSustitucion.fechaAlta)
                    : ""
                  : ""}
              </span>
            </span>
          )}
        </Col>
      </Row>
    </Col>
  );
};
