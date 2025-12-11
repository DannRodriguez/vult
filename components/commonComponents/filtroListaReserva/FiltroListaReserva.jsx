import { useEffect, useState } from "react";
import { Layout, Row, Col, Form, Radio, Select, Button, Space } from "antd";
import { BarInfo } from "../../commonComponents/AccessoriesComponents";
import { apiClientPost } from "../../../utils/apiClient";
import * as B from "../../../utils/listaReserva/etiquetas";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { CODE_SUCCESS } from "../../../utils/constantes.js";
import icono_boleta from "../../../img/boleta.svg";
import "../../../css/listaReservaCAE.css";
import PropTypes from "prop-types";
const { Option } = Select;

const FiltroListaReserva = ({
  tipoLista,
  filtroSeleccionado,
  setFiltroSeleccionado,
  formRef,
  setLista,
  setMsgNotData,
}) => {
  const estado = useSelector((store) => store.menu.estado);
  const proceso = useSelector((store) => store.menu.proceso);
  const distrito = useSelector((store) => store.menu.distrito);
  const idParticipacion = useSelector((store) => store.menu.idParticipacion);

  const [selectedOption, setSelectedOption] = useState(B.ETQ_RADIO_OPC_TODOS);
  const [municipios, setMunicipios] = useState(null);
  const [localidades, setLocalidades] = useState(null);
  const [sedes, setSedes] = useState(null);
  const [secciones, setSecciones] = useState(null);
  const [secciones2, setSecciones2] = useState(null);
  const [seleccion, setSeleccion] = useState(undefined);
  const [seleccion2, setSeleccion2] = useState(null);
  const [estatus, setEstatus] = useState(null);

  useEffect(() => {
    handleRadioChange(1);
  }, []);

  const handleRadioChange = (value) => {
    if (formRef) {
      formRef.current.setFields([
        { name: "municipio", errors: [], value: null },
      ]);
      formRef.current.setFields([
        { name: "localidad", errors: [], value: null },
      ]);
      formRef.current.setFields([{ name: "sede", errors: [], value: null }]);
      formRef.current.setFields([
        { name: "seccion1", errors: [], value: null },
      ]);
      formRef.current.setFields([
        { name: "seccion2", errors: [], value: null },
      ]);
      formRef.current.setFields([{ name: "estatus", errors: [], value: null }]);
    }

    setSelectedOption(value);

    setSeleccion(null);
    setSeleccion2(null);
    setFiltroSeleccionado({
      idOpcion: value,
      estatus: B.ETQ_TODOS,
    });

    setLista([]);
    setMsgNotData("");

    let request = {};

    switch (value) {
      case B.ETQ_RADIO_OPC_TODOS:
        break;
      case B.ETQ_RADIO_OPC_MUNICIPIO:
        request = {
          idEstado: parseInt(estado.idEstado),
          idDistrito: parseInt(distrito.idDistrito),
          idCorte: parseInt(proceso.corte),
        };
        apiClientPost("obtenerMunicipios", request)
          .then((data) => {
            let code = data.code;
            if (code == CODE_SUCCESS) {
              setMunicipios(data.data);
            }
          })
          .catch((error) => {});
        break;
      case B.ETQ_RADIO_OPC_SEDE:
        request = {
          idProceso: parseInt(proceso.idProcesoElectoral),
          idDetalle: parseInt(proceso.idDetalleProceso),
          idParticipacion: parseInt(idParticipacion),
        };
        apiClientPost("obtenerSedes", request)
          .then((data) => {
            let code = data.code;
            if (code == CODE_SUCCESS) {
              setSedes(data.data);
            }
          })
          .catch((error) => {});
        break;
      case B.ETQ_RADIO_OPC_SECCION:
        request = {
          idProceso: parseInt(proceso.idProcesoElectoral),
          idDetalle: parseInt(proceso.idDetalleProceso),
          idParticipacion: parseInt(idParticipacion),
        };
        apiClientPost("obtenerSecciones", request)
          .then((data) => {
            let code = data.code;
            if (code == CODE_SUCCESS) {
              setSecciones(data.data);
            }
          })
          .catch((error) => {});
        break;
    }
  };

  const handleMunicipioChange = (value, object) => {
    setSeleccion(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      idMunicipio: object.key,
      idLocalidad: null,
    });

    let request = {
      idEstado: parseInt(estado.idEstado),
      idMunicipio: parseInt(object.key),
      idDistrito: parseInt(distrito.idDistrito),
      idCorte: parseInt(proceso.corte),
      idProceso: parseInt(proceso.idProcesoElectoral),
      idDetalle: parseInt(proceso.idDetalleProceso),
      idParticipacion: parseInt(idParticipacion),
    };
    apiClientPost("obtenerLocalidadesPorMunicipio", request)
      .then((data) => {
        let code = data.code;
        if (code == CODE_SUCCESS) {
          setLocalidades(data.data);
        }
      })
      .catch((error) => {});
    formRef.current.setFieldsValue({ localidad: null });
  };

  const handleLocalidadChange = (value, object) => {
    setSeleccion2(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      idLocalidad: object.key,
    });
  };

  const handleSedeChange = (value, object) => {
    setSeleccion(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      idSede: object.key,
    });
  };

  const handleSeccionChange = (value, object) => {
    setSeleccion(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      idSeccion: object.key,
      idSeccion2: null,
    });

    if (filtroSeleccionado.idSeccion2 != null) {
      if (filtroSeleccionado.idSeccion2 > filtroSeleccionado.idSeccion) {
        setFiltroSeleccionado({
          ...filtroSeleccionado,
          idSeccion2: null,
        });
      }
    }

    let filtroSecciones2 = [];
    secciones.forEach((seccion_) => {
      if (seccion_.id >= object.key) {
        filtroSecciones2.push(seccion_);
      }
    });

    setSecciones2(filtroSecciones2);
    formRef.current.setFieldsValue({ seccion2: null });
  };

  const handleSeccion2Change = (value, object) => {
    setSeleccion2(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      idSeccion2: object.key,
    });
  };

  const handleEstatusChange = (value, object) => {
    setEstatus(object);
    setFiltroSeleccionado({
      ...filtroSeleccionado,
      estatus: object.key === null ? B.ETQ_TODOS : object.key,
    });
  };

  return (
    <Form.Item name="radios">
      <Layout id="content-lista-reserva">
        <Row style={{ marginBottom: "20px" }}>
          <Col xs={24} md={24} xl={24}>
            <span className="span-miga-light">Inicio </span>
            <span className="span-miga-light">&ensp;/&ensp;</span>
            <span className="span-miga-medium">
              {tipoLista == B.ETQ_TIPO_LISTA_SE
                ? B.ETQ_MIGA_RUTA_SE
                : B.ETQ_MIGA_RUTA_CAE}
            </span>
          </Col>
        </Row>

        <Row style={{ marginBottom: "20px" }}>
          <Col xs={24} md={24} xl={12}>
            <img className="align-boleta-header" src={icono_boleta} />
            <span>&ensp;</span>
            <span className="span-title-modulo">
              {tipoLista == B.ETQ_TIPO_LISTA_SE
                ? B.ETQ_TITULO_SE
                : B.ETQ_TITULO_CAE}
            </span>
          </Col>
        </Row>

        <BarInfo text={B.ETQ_BAR_INFO} />

        <Radio.Group
          name="radios"
          className="radio-group"
          onChange={(e) => handleRadioChange(e.target.value)}
          value={selectedOption}
        >
          <Space direction="vertical">
            {/* TODOS */}
            <Row className="row">
              <Col xs={24} md={24} xl={24}>
                <Radio
                  className={"sub_titulos_radios"}
                  value={B.ETQ_RADIO_OPC_TODOS}
                >
                  {B.ETQ_RADIO_TODOS}
                </Radio>
              </Col>
            </Row>

            {/* MUNICIPIO */}
            <Row className="row">
              <Col xs={24} md={5} xl={5}>
                <Radio
                  className={"sub_titulos_radios"}
                  style={{ marginTop: "10px" }}
                  value={B.ETQ_RADIO_OPC_MUNICIPIO}
                >
                  {B.ETQ_RADIO_MUNICIPIO}
                </Radio>
              </Col>
              <Col xs={20} md={8} xl={7}>
                <Form.Item
                  name="municipio"
                  rules={[
                    {
                      required:
                        filtroSeleccionado.idOpcion ===
                        B.ETQ_RADIO_OPC_MUNICIPIO,
                      message: B.DATO_REQUERIDO,
                    },
                  ]}
                  className="form-item"
                >
                  <Select
                    name="municipio"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    disabled={
                      filtroSeleccionado.idOpcion !== B.ETQ_RADIO_OPC_MUNICIPIO
                    }
                    onChange={handleMunicipioChange}
                    value={
                      filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_MUNICIPIO
                        ? seleccion
                        : undefined
                    }
                  >
                    {municipios &&
                      municipios.map((municipio) => (
                        <Option key={municipio.id} value={municipio.id}>
                          {municipio.value}
                        </Option>
                      ))}
                  </Select>
                </Form.Item>
              </Col>
              <Col xs={24} md={2} xl={2} className="col-item">
                <span
                  className={"sub_titulos_radios"}
                  style={{ fontSize: "14px" }}
                >
                  {B.ETQ_LABEL_LOCALIDAD}
                </span>
              </Col>
              <Col xs={20} md={8} xl={7}>
                <Form.Item name="localidad" className="form-item">
                  <Select
                    name="localidad"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    disabled={
                      filtroSeleccionado.idMunicipio === undefined ||
                      filtroSeleccionado.idMunicipio === null
                    }
                    onChange={handleLocalidadChange}
                    value={
                      filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_MUNICIPIO
                        ? seleccion2
                        : undefined
                    }
                  >
                    {localidades &&
                      localidades.map((localidad) => (
                        <Option key={localidad.id} value={localidad.id}>
                          {localidad.value}
                        </Option>
                      ))}
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* SEDE DE RECLUTAMIENTO */}
            <Row className="row">
              <Col xs={24} md={5} xl={5}>
                <Radio
                  className={"sub_titulos_radios"}
                  style={{ marginTop: "10px" }}
                  value={B.ETQ_RADIO_OPC_SEDE}
                >
                  {B.ETQ_RADIO_SEDE}
                </Radio>
              </Col>
              <Col xs={20} md={8} xl={7} style={{ width: "5px" }}>
                <Form.Item
                  name="sede"
                  rules={[
                    {
                      required:
                        filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_SEDE,
                      message: B.DATO_REQUERIDO,
                    },
                  ]}
                  className="form-item"
                >
                  <Select
                    name="sede"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    disabled={
                      filtroSeleccionado.idOpcion !== B.ETQ_RADIO_OPC_SEDE
                    }
                    onChange={handleSedeChange}
                    value={
                      filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_SEDE
                        ? seleccion
                        : undefined
                    }
                  >
                    {sedes &&
                      sedes.map((sede) => (
                        <Option key={sede.id} value={sede.id}>
                          {sede.value}
                        </Option>
                      ))}
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* SECCIÓN */}
            <Row className="row">
              <Col xs={24} md={5} xl={5}>
                <Radio
                  className={"sub_titulos_radios"}
                  style={{ marginTop: "10px" }}
                  value={B.ETQ_RADIO_OPC_SECCION}
                >
                  {B.ETQ_RADIO_SECCION}
                </Radio>
              </Col>
              <Col xs={20} md={8} xl={7}>
                <Form.Item
                  name="seccion1"
                  rules={[
                    {
                      required:
                        filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_SECCION,
                      message: B.DATO_REQUERIDO,
                    },
                  ]}
                  className="form-item"
                >
                  <Select
                    name="seccion1"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    disabled={
                      filtroSeleccionado.idOpcion !== B.ETQ_RADIO_OPC_SECCION
                    }
                    onChange={handleSeccionChange}
                    value={
                      filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_SECCION
                        ? seleccion
                        : undefined
                    }
                  >
                    {secciones &&
                      secciones.map((seccion) => (
                        <Option key={seccion.id} value={seccion.id}>
                          {seccion.value}
                        </Option>
                      ))}
                  </Select>
                </Form.Item>
              </Col>
              <Col xs={24} md={2} xl={2} className="col-item">
                <span
                  className={"sub_titulos_radios"}
                  style={{ fontSize: "14px" }}
                >
                  {B.ETQ_LABEL_A_LA_SECCION}
                </span>
              </Col>
              <Col xs={20} md={8} xl={7}>
                <Form.Item
                  name="seccion2"
                  rules={[
                    {
                      required:
                        filtroSeleccionado.idSeccion !== undefined &&
                        filtroSeleccionado.idSeccion !== null,
                      message: B.DATO_REQUERIDO,
                    },
                  ]}
                  className="form-item"
                >
                  <Select
                    name="seccion2"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    disabled={
                      filtroSeleccionado.idSeccion === undefined ||
                      filtroSeleccionado.idSeccion === null
                    }
                    onChange={handleSeccion2Change}
                    value={
                      filtroSeleccionado.idOpcion === B.ETQ_RADIO_OPC_SECCION
                        ? seleccion2
                        : undefined
                    }
                  >
                    {secciones2 &&
                      secciones2.map((seccion) => (
                        <Option key={seccion.id} value={seccion.id}>
                          {seccion.value}
                        </Option>
                      ))}
                  </Select>
                </Form.Item>
              </Col>
            </Row>

            {/* ESTATUS */}
            <Row className="row">
              <Col xs={24} md={5} xl={5} className="col-item-status">
                <span
                  className={"sub_titulos_radios"}
                  style={{ fontSize: "14px" }}
                >
                  {B.ETQ_RADIO_ESTATUS}
                </span>
              </Col>
              <Col xs={20} md={8} xl={7}>
                <Form.Item name="estatus" className="form-item">
                  <Select
                    name="estatus"
                    placeholder={B.ETQ_SELECT_SELECCIONA}
                    className="container_select_filtros"
                    onChange={handleEstatusChange}
                  >
                    <Option key={B.ETQ_TODOS} value={B.ETQ_TODOS}>
                      {B.ETQ_RADIO_TODOS}
                    </Option>
                    <Option key={B.ETQ_LR} value={B.ETQ_LR}>
                      {B.ETQ_LISTA_RESEVA}
                    </Option>
                    <Option key={B.ETQ_NA} value={B.ETQ_NA}>
                      {B.ETQ_NO_ACEPTO}
                    </Option>
                    <Option key={B.ETQ_NL} value={B.ETQ_NL}>
                      {B.ETQ_NO_LOCALIZADA}
                    </Option>
                  </Select>
                </Form.Item>
              </Col>
            </Row>
          </Space>
        </Radio.Group>
      </Layout>
      {/* Botón ACEPTAR */}
      <Row className="row" style={{ marginTop: "20px" }}>
        <Col xs={24} md={24} xl={24} style={{ textAlign: "center" }}>
          <Button className="accept-button" type="primary" htmlType="submit">
            {B.ETQ_BOTON_ACEPTAR}
          </Button>
        </Col>
      </Row>
    </Form.Item>
  );
};
FiltroListaReserva.PropTypes = {
  filtroSeleccionado: PropTypes.array,
};
export default FiltroListaReserva;
