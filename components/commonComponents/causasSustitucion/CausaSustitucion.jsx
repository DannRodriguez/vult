import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux/es/hooks/useSelector";
import dayjs from "dayjs";
import { Layout, Row, Col, Tooltip } from "antd";
import {
  DoubleRightOutlined,
  ExclamationCircleFilled,
} from "@ant-design/icons";
import { CRadioGroupSelect } from "./CRadioGroupSelect.jsx";
import CObservaciones from "./CObservaciones.jsx";
import VFechaCausa from "./VFechaCausa.jsx";
import { Loader } from "../../interfaz/Loader.jsx";

import { apiClientPost } from "../../../utils/apiClient.js";

import * as etiquetas from "../../../utils/causaSustitucion/etiquetas.js";

import {
  CODE_SUCCESS,
  FLUJO_CONSULTA,
  FLUJO_MODIFICA,
  SUST_INCAPACIDAD,
  SUST_TERM_CONTRATO,
  SUST_RESC_CONTRATO,
} from "../../../utils/constantes.js";

import "../../../css/causaSustitucion.css";

function CausaSustitucion({
  moduloSust,
  tipoFlujo,
  causaSusti,
  setCausaSusti,
  formRef,
  idCausasNOMostrar,
}) {
  const maxLength = 500;

  const proceso = useSelector((store) => store.menu.proceso);

  const [charCount, setCharCount] = useState(0);
  const [obtenerCausas, setObtenerCausas] = useState(1);
  const [block, setBlock] = useState(false);
  const [listCausas, setListCausas] = useState([]);
  const [seleccion, setSeleccion] = useState(null);
  const [radioSeleccionado, setRadioSeleccionado] = useState(null);
  const [fechaInicioSustituciones, setFechaInicioSustituciones] =
    useState(null);
  const [fechaFinSustituciones, setFechaFinSustituciones] = useState(null);

  useEffect(() => {
    if (obtenerCausas) {
      setBlock(true);
      setObtenerCausas(0);
      obtenerFechas();
      if (moduloSust >= SUST_RESC_CONTRATO) {
        apiClientPost("obtenerListaCausasVacante", {})
          .then(({ code, data }) => {
            if (code == CODE_SUCCESS) {
              setListCausas(data);
            }
            setBlock(false);
          })
          .catch((error) => {
            setBlock(false);
          });
      } else {
        setDatosCausaSusti();
        setBlock(false);
      }
    }
  }, []);

  useEffect(() => {
    if (!causaSusti?.tipoCausaVacante) {
      setRadioSeleccionado(null);
      setSeleccion(null);
    }
  }, [causaSusti]);

  const obtenerFechas = () => {
    const request = {
      idProceso: proceso.idProcesoElectoral,
      idDetalle: proceso.idDetalleProceso,
    };
    apiClientPost("obtenerFechasSustituciones", request)
      .then((data) => {
        const code = data.code;
        if (code == CODE_SUCCESS) {
          setFechaInicioSustituciones(data.data.fechaInicioSustituciones);
          setFechaFinSustituciones(data.data.fechaFinSustituciones);
        }
      })
      .catch((error) => {});
  };

  const setDatosCausaSusti = () => {
    if (moduloSust == SUST_INCAPACIDAD) {
      setCausaSusti({
        ...causaSusti,
        idCausaVacante: 1,
        tipoCausaVacante: 4,
      });
    }
    if (moduloSust == SUST_TERM_CONTRATO) {
      setCausaSusti({
        ...causaSusti,
        idCausaVacante: 2,
        tipoCausaVacante: 4,
      });
    }
  };

  const setSelecRadio = (e) => {
    const newValue = e.target.value;
    setRadioSeleccionado(newValue);
    setSeleccion(null);
    setCausaSusti((prev) => ({
      ...prev,
      idCausaVacante: null,
      tipoCausaVacante: newValue,
      descripcionCausa: "",
    }));

    if (formRef?.current) {
      formRef.current.setFieldsValue({
        [`select${newValue}`]: undefined,
      });
    }
  };

  const setSeleccionMotivo = (value, object) => {
    setSeleccion(object);
    setCausaSusti({
      ...causaSusti,
      idCausaVacante: object.key,
      descripcionCausa: object.children,
    });
  };

  const setFecha = (date, dateString) => {
    setCausaSusti({
      ...causaSusti,
      fechaBaja: dateString,
    });
  };

  const setObservaciones = (e) => {
    setCharCount(e.target.value.length);
    setCausaSusti({
      ...causaSusti,
      observaciones: e.target.value,
    });
  };

  const disableDate = (current) => {
    const isFutureDate = current > dayjs().endOf("day");

    const isNotInRange =
      current.isBefore(dayjs(fechaInicioSustituciones, "DD/MM/YYYY")) ||
      current.isAfter(dayjs(fechaFinSustituciones, "DD/MM/YYYY"));

    const isBeforeSustitucionPrevia =
      current <= dayjs(causaSusti?.fechaSustIncapacidad, "DD/MM/YYYY");

    return isFutureDate || isNotInRange || isBeforeSustitucionPrevia;
  };

  return (
    <>
      <Loader blocking={block} />
      <Layout id="layout_container_causa_susitucion">
        <div>
          <span className="indicacion_rosa">
            {" "}
            <DoubleRightOutlined />{" "}
          </span>
          <span className="span_title_causa">{etiquetas.TITLE_COMPONENTE}</span>
        </div>

        {moduloSust >= SUST_RESC_CONTRATO ? (
          <div>
            {tipoFlujo == FLUJO_CONSULTA ? (
              <>
                <Row style={{ marginTop: "20px", marginBottom: "20px" }}>
                  <Col xs={20} md={8} xl={7}>
                    <div className="sub_titulos">
                      {causaSusti.tipoCausaVacante == 1
                        ? etiquetas.ETI_RESCISION
                        : causaSusti.tipoCausaVacante == 2
                        ? etiquetas.ETI_RENUNCIA
                        : etiquetas.ETI_OTRAS}
                    </div>
                    <div className="sub_datos">
                      <span>{causaSusti?.descripcionCausa}</span>
                    </div>
                    <div className="sub_titulos" style={{ marginTop: "32px" }}>
                      <span>{etiquetas.ETI_FECHA_BAJA}</span> <br />
                      <div className="sub_datos">
                        <span>{causaSusti?.fechaBaja}</span>
                      </div>
                    </div>
                  </Col>
                  <CObservaciones
                    tipoFlujo={tipoFlujo}
                    observaciones={causaSusti?.observaciones}
                  />
                </Row>
              </>
            ) : (
              <>
                <div className="sub_titulos" style={{ marginTop: "20px" }}>
                  <span className="asterisco">*</span>
                  {etiquetas.SUB_MOTIVO}
                </div>
                <div>
                  <CRadioGroupSelect
                    causas={listCausas}
                    seleccion={seleccion}
                    setSeleccionMotivo={setSeleccionMotivo}
                    setSelecRadio={setSelecRadio}
                    radioSeleccionado={
                      tipoFlujo == FLUJO_MODIFICA && causaSusti
                        ? causaSusti.tipoCausaVacante
                        : radioSeleccionado
                    }
                    formRef={formRef}
                    disabledRadios={tipoFlujo == FLUJO_MODIFICA && causaSusti}
                    idCausasNOMostrar={
                      idCausasNOMostrar ? idCausasNOMostrar : []
                    }
                  />
                </div>

                <Row style={{ marginTop: "20px", marginBottom: "20px" }}>
                  <Col xs={20} md={8} xl={7}>
                    <div className="sub_titulos">
                      <Col span={12}>
                        <span className="asterisco">*</span>
                        <span className="sub_titulos">
                          {etiquetas.ETI_FECHA_BAJA}
                        </span>
                      </Col>
                      <VFechaCausa
                        tipoFlujo={tipoFlujo}
                        disableDate={disableDate}
                        fechaBaja={causaSusti?.fechaBaja}
                        setFecha={setFecha}
                      />
                    </div>
                  </Col>
                  <CObservaciones
                    tipoFlujo={tipoFlujo}
                    observaciones={causaSusti?.observaciones}
                    charCount={charCount}
                    maxLength={maxLength}
                    setObservaciones={setObservaciones}
                  />
                </Row>
              </>
            )}
          </div>
        ) : (
          <div>
            <Row style={{ marginTop: "20px", marginBottom: "20px" }}>
              <Col xs={20} md={8} xl={7}>
                <div className="sub_titulos">{etiquetas.SUB_MOTIVO}</div>
                {moduloSust == SUST_INCAPACIDAD ? (
                  causaSusti.idCausaVacante == 1 ? (
                    <div className="sub_datos">{"Incapacidad"}</div>
                  ) : (
                    <div className="sub_datos">{"Promoción Temporal"}</div>
                  )
                ) : (
                  <div className="sub_datos">{"Término de contrato"}</div>
                )}
                <div className="sub_titulos" style={{ marginTop: "32px" }}>
                  <Col span={12}>
                    <span className="asterisco">
                      {tipoFlujo == FLUJO_CONSULTA ? "" : "*"}
                    </span>
                    <span className="sub_titulos">
                      {etiquetas.ETI_FECHA_BAJA}
                    </span>
                  </Col>
                  {tipoFlujo == FLUJO_CONSULTA ? (
                    <div className="sub_datos">
                      <span>{causaSusti?.fechaBaja}</span>
                    </div>
                  ) : causaSusti?.idCausaVacante == 3 ? (
                    <>
                      <div className="sub_datos">
                        <span>{causaSusti?.fechaBaja}</span> &nbsp;
                        <Tooltip
                          placement="rightBottom"
                          title={
                            <span>
                              Esta fecha sólo se puede modificar desde la
                              sustitución causante de la promoción temporal.
                            </span>
                          }
                        >
                          <ExclamationCircleFilled className="icono_acciones" />
                        </Tooltip>
                      </div>
                    </>
                  ) : (
                    <VFechaCausa
                      tipoFlujo={tipoFlujo}
                      disableDate={disableDate}
                      fechaBaja={causaSusti?.fechaBaja}
                      setFecha={setFecha}
                    />
                  )}
                </div>
              </Col>
              <CObservaciones
                tipoFlujo={tipoFlujo}
                observaciones={causaSusti?.observaciones}
                charCount={charCount}
                maxLength={maxLength}
                setObservaciones={setObservaciones}
              />
            </Row>
          </div>
        )}
      </Layout>
    </>
  );
}

export default CausaSustitucion;
