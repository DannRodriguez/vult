import { Layout, Row, Col, Select, Input, Checkbox, Form } from "antd";
import VCustomModal from "../../commonComponents/VCustomModal.jsx";
import { HeaderSeccion } from "../../commonComponents/AccessoriesComponents";
import { useState, useEffect, Fragment } from "react";
import * as B from "../../../utils/bitacoraDesempenio/etiquetas";
import { CaretDownOutlined } from "@ant-design/icons";
import {
  FLUJO_CAPTURA,
  FLUJO_MODIFICA,
  FLUJO_CONSULTA,
  CODE_SUCCESS,
} from "../../../utils/constantes.js";
import { apiClientPost } from "../../../utils/apiClient";
const { TextArea } = Input;
const VR_ALTO = "ALTO";
const VR_MEDIO = "MEDIO";
const VR_BAJO = "BAJO";

function EvaluacionDesempenio({
  onChangeEvaluacion,
  tipoFlujo,
  funcionarioSECAE,
  formRef,
  isSust,
  evaluacionOriginal,
}) {
  const [openModalErrorEval, setOpenModalErrorEval] = useState(false);
  const [msjErrorEval, setMsjErrorEval] = useState();
  const [loading, setLoading] = useState(false);
  const [maxLength, setMaxLength] = useState(500);
  const [charCount, setCharCount] = useState(0);
  const [descRiesgo, setDescRiesgo] = useState();
  const [colorRiesgo, setColorRiesgo] = useState();
  const [valoracionRiesgo, setValoracionRiesgo] = useState();
  const [observaciones, setObservaciones] = useState();
  const [cValoracionesRiesgos, setCValoracionesRiesgos] = useState();
  const [optionsFrecuencias, setOptionsFrecuencias] = useState();
  const [idFrecuenciaSelected, setIdFrecuenciaSelected] = useState();
  const [optionsImpactos, setOptionsImpactos] = useState();
  const [idImpactoSelected, setIdImpactoSelected] = useState();
  const [integrantesWS, setIntegrantesWS] = useState();
  const [responsablesChecks, setResponsablesChecks] = useState();
  const [responsablesChecked, setResponsablesChecked] = useState();
  const [responsablesSelected, setResponsablesSelected] = useState();
  const [obtenerEvaluacion, setobtenerEvaluacion] = useState(true);
  const [evaluacionDesem, setEvaluacionDesemp] = useState();
  const [responsablesPrev, setResponsablesPrev] = useState();
  const [sinEvaluacionBitacora, setSinEvaluacionBitacora] = useState(false);

  const custom_css_antd_eval = `
                #layEvalBit .ant-form-item-control-input{
                            display: none;
                }
            `;

  useEffect(() => {
    if (funcionarioSECAE && obtenerEvaluacion) {
      setobtenerEvaluacion(false);
      setLoading(true);
      setSinEvaluacionBitacora(false);

      let request = {
        idDetalleProceso: funcionarioSECAE?.idDetalleProceso,
        idParticipacion: funcionarioSECAE?.idParticipacion,
        idAspirante: funcionarioSECAE?.idAspirante,
      };

      apiClientPost("obtenerEvaluacionDesemp", request)
        .then((data) => {
          if (data.code === CODE_SUCCESS) {
            let evaluacionDesemp = data.data;

            if (evaluacionDesemp) {
              if (
                !evaluacionDesemp.bitacoraDesempenio &&
                tipoFlujo == FLUJO_CONSULTA
              )
                setSinEvaluacionBitacora(true);
              else {
                try {
                  loadEvaluacionDesemp(evaluacionDesemp);

                  if (
                    !evaluacionDesemp.integrantesWS ||
                    evaluacionDesemp.integrantesWS.length === 0
                  )
                    mostrarModalErrorEval(B.MSJ_ERROR_INTEGRANTESESIONES_WS);
                } catch (e) {
                  console.log(e);
                  mostrarModalErrorEval(B.MSJ_ERROR_EVALUACION);
                }
              }
            } else mostrarModalErrorEval(B.MSJ_ERROR_EVALUACION_ASPIRANTE);
          } else mostrarModalErrorEval(B.MSJ_ERROR_EVALUACION_ASPIRANTE);

          setLoading(false);
        })
        .catch((error) => {
          console.log(error);
          mostrarModalErrorEval(B.MSJ_ERROR_EVALUACION_ASPIRANTE);
          setLoading(false);
        });
    }
  });

  const loadEvaluacionDesemp = (evaluacionDesemp) => {
    setCValoracionesRiesgos(evaluacionDesemp.catValoracionesRiesgo);
    let catFrecuencias = evaluacionDesemp.catFrecuencias.map((f) => {
      let itemF = { value: f.idFrecuencia, label: f.descripcionFrecuencia };
      return itemF;
    });

    let catImpactos = evaluacionDesemp.catImpactos.map((i) => {
      let itemI = { value: i.idImpacto, label: i.descripcionImpacto };
      return itemI;
    });
    setOptionsFrecuencias(catFrecuencias);
    setOptionsImpactos(catImpactos);

    let integrantesWebServ = evaluacionDesemp.integrantesWS ?? [];
    setIntegrantesWS(integrantesWebServ);

    let integrantesOptions = integrantesWebServ.map((i) => {
      let nombreIntegrante = [
        i.gradoAcademico || "",
        i.nombreIntegrante || "",
        i.primerApellidoIntegrante || "",
        i.segundoApellidoIntegrante || "",
      ]
        .join(" ")
        .trim();
      let itemI = {
        label:
          (nombreIntegrante.trim() ? nombreIntegrante + " - " : "") +
          i.iniciales,
        value: i.idPuesto,
        iniciales: i.iniciales,
        nombreIntegrante: i.nombreIntegrante,
        primerApellidoIntegrante: i.primerApellidoIntegrante,
        segundoApellidoIntegrante: i.segundoApellidoIntegrante,
        gradoAcademico: i.gradoAcademico,
      };

      return itemI;
    });

    setResponsablesChecks(integrantesOptions);

    if (
      (tipoFlujo == FLUJO_MODIFICA || tipoFlujo == FLUJO_CONSULTA) &&
      evaluacionDesemp.bitacoraDesempenio &&
      evaluacionDesemp.bitacoraDesempenio.idBitacoraDesempenio
    ) {
      let bitacoraDesempenio = evaluacionDesemp.bitacoraDesempenio;

      setIdFrecuenciaSelected(bitacoraDesempenio.idFrecuencia);
      setIdImpactoSelected(bitacoraDesempenio.idImpacto);
      let riesgo = evaluacionDesemp.catValoracionesRiesgo.find(
        (r) =>
          r.idFrecuencia == bitacoraDesempenio.idFrecuencia &&
          r.idImpacto == bitacoraDesempenio.idImpacto
      );
      loadValoracionRiesgo(riesgo);
      setObservacionesDesemp({
        target: { value: bitacoraDesempenio.observaciones },
      });

      let idsRespSelected = [];
      if (
        evaluacionDesemp.responsablesBitacora &&
        evaluacionDesemp.responsablesBitacora.length > 0
      ) {
        for (const resp of evaluacionDesemp.responsablesBitacora) {
          idsRespSelected.push(resp.idPuesto);
        }
        setResponsablesChecked(idsRespSelected);

        const responsablesPrevTemp = integrantesOptions
          .filter((opt) => idsRespSelected.includes(opt.value))
          .map((opt) => {
            const respBD = evaluacionDesemp.responsablesBitacora.find(
              (r) => r.idPuesto === opt.value
            );

            return {
              ...respBD,
              iniciales: opt.iniciales,
              gradoAcademico: opt.gradoAcademico,
              nombreIntegrante: opt.nombreIntegrante,
              primerApellidoIntegrante: opt.primerApellidoIntegrante,
              segundoApellidoIntegrante: opt.segundoApellidoIntegrante,
            };
          });

        setResponsablesPrev(responsablesPrevTemp);
      }
      let responsablesSelec = [];
      for (const id of idsRespSelected) {
        let resp = integrantesWebServ.find(
          (integrante) => integrante.idPuesto == id
        );
        if (resp) responsablesSelec.push(resp);
      }
      setResponsablesSelected(responsablesSelec);
      let evaluacion = {
        valoracionRiesgo: riesgo,
        observaciones: bitacoraDesempenio.observaciones,
        responsables: responsablesSelec,
        cambioResponsables: true,
      };
      setEvaluacionDesemp(evaluacion);
      onChangeEvaluacion(evaluacion);
      if (evaluacionOriginal) evaluacionOriginal(evaluacion);
    }
  };

  const onSelecImpacto = (idImpacto) => {
    setIdImpactoSelected(idImpacto);

    if (idFrecuenciaSelected) {
      let riesgo = cValoracionesRiesgos.find(
        (r) =>
          r.idFrecuencia == idFrecuenciaSelected && r.idImpacto == idImpacto
      );

      let evaluacion = { ...evaluacionDesem, valoracionRiesgo: riesgo };
      setEvaluacionDesemp(evaluacion);
      onChangeEvaluacion(evaluacion);

      loadValoracionRiesgo(riesgo);
    } else {
      let evaluacion = { ...evaluacionDesem, idImpacto: idImpacto };
      setEvaluacionDesemp(evaluacion);
      onChangeEvaluacion(evaluacion);
    }

    formRef.current.setFields([{ name: "selectImpactoBit", errors: [""] }]);
  };

  const onSelecFrecuencia = (idFrecuencia) => {
    setIdFrecuenciaSelected(idFrecuencia);

    if (idImpactoSelected) {
      let riesgo = cValoracionesRiesgos.find(
        (r) =>
          r.idFrecuencia == idFrecuencia && r.idImpacto == idImpactoSelected
      );

      let evaluacion = { ...evaluacionDesem, valoracionRiesgo: riesgo };
      setEvaluacionDesemp(evaluacion);
      onChangeEvaluacion(evaluacion);

      loadValoracionRiesgo(riesgo);
    } else {
      let evaluacion = { ...evaluacionDesem, idFrecuencia: idFrecuencia };
      setEvaluacionDesemp(evaluacion);
      onChangeEvaluacion(evaluacion);
    }

    formRef.current.setFields([{ name: "selectFrecuenciaBit", errors: [""] }]);
  };

  const loadValoracionRiesgo = (riesgo) => {
    setValoracionRiesgo(riesgo);
    const descripRiesgo = riesgo?.descripcionRiesgo;
    setDescRiesgo(descripRiesgo);

    const coloresRiesgo = {
      [VR_ALTO]: "#FF0000",
      [VR_MEDIO]: "#F7F75C",
      [VR_BAJO]: "#60BC56",
    };

    setColorRiesgo(coloresRiesgo[descripRiesgo] || "#FFFFFF");
  };

  const setObservacionesDesemp = (e) => {
    const observacion = e.target.value
      ? e.target.value.trimStart()
      : e.target.value;
    actualizarObservaciones(observacion);
  };

  const handleBlurObservaciones = (e) => {
    const observacion = e.target.value.trim().replace(/\s+/g, " ");
    actualizarObservaciones(observacion);
  };

  const actualizarObservaciones = (observacion) => {
    setObservaciones(observacion);

    const evaluacion = { ...evaluacionDesem, observaciones: observacion };
    setEvaluacionDesemp(evaluacion);
    onChangeEvaluacion(evaluacion);

    setCharCount(observacion ? observacion.length : 0);
  };

  const onChangeCheckResponsables = (list) => {
    let responsablesSelec = [];
    for (const id of list) {
      let resp = integrantesWS.find((integrante) => integrante.idPuesto == id);
      responsablesSelec.push(resp);
    }
    setResponsablesSelected(responsablesSelec);

    let evaluacion = {
      ...evaluacionDesem,
      responsables: responsablesSelec,
      cambioResponsables: true,
    };
    setEvaluacionDesemp(evaluacion);
    onChangeEvaluacion(evaluacion);

    setResponsablesChecked(list);

    formRef.current.setFields([{ name: "ChecksResponablesBit", errors: [""] }]);
  };

  const mostrarModalErrorEval = (msg) => {
    setMsjErrorEval(msg);
    setOpenModalErrorEval(true);
  };

  const cerrarModalErrorEval = () => {
    setMsjErrorEval("");
    setOpenModalErrorEval(false);
  };

  const onClearFrecuencia = () => {
    let evaluacion = { ...evaluacionDesem, valoracionRiesgo: null };
    setIdFrecuenciaSelected(null);

    setValoracionRiesgo({});
    setDescRiesgo(null);

    setEvaluacionDesemp(evaluacion);
    onChangeEvaluacion(evaluacion);
  };

  const onClearImpacto = () => {
    let evaluacion = { ...evaluacionDesem, valoracionRiesgo: null };
    setIdImpactoSelected(null);
    setEvaluacionDesemp(evaluacion);
    onChangeEvaluacion(evaluacion);
  };

  return (
    <Layout className="content-comp-bitacora" id="layEvalBit">
      <style>{custom_css_antd_eval}</style>
      <HeaderSeccion text={B.ETQ_EVALUAICON_DESEMP} />
      {tipoFlujo == FLUJO_CONSULTA && sinEvaluacionBitacora ? (
        <div className="divCenterUtil">
          <span className="span-content-docto">
            {B.MSJ_SIN_EVA_DESEMP_BITACORA}
          </span>
        </div>
      ) : (
        <>
          <Row id="rowEvaluacionImpFec">
            <Col xs={8} md={8} xl={8} className="Col-header-eval">
              <div className="divCenterUtil">
                <span>{B.ETQ_FRECUENCIA}</span>
              </div>
            </Col>
            <Col xs={8} md={8} xl={8} className="Col-header-eval">
              <div className="divCenterUtil">
                {" "}
                <span>{B.ETQ_IMPACTO}</span>
              </div>
            </Col>
            <Col xs={8} md={8} xl={8} className="Col-header-eval">
              <div className="divCenterUtil">
                {" "}
                <span>{B.ETQ_RIESGO}</span>
              </div>
            </Col>
          </Row>
          <Row className="row-content-eval">
            <Col xs={8} md={8} xl={8} className="col-content-eval">
              {tipoFlujo == FLUJO_CONSULTA ? (
                <span className="span-freim">
                  {
                    optionsFrecuencias?.find(
                      (f) => f.value == idFrecuenciaSelected
                    )?.label
                  }
                </span>
              ) : (
                <>
                  <Select
                    name="selectFrecuenciaBit"
                    className="select-eval-desem"
                    suffixIcon={
                      <CaretDownOutlined className="icon-select-gen" />
                    }
                    value={idFrecuenciaSelected}
                    options={optionsFrecuencias}
                    allowClear={isSust && tipoFlujo == FLUJO_CAPTURA}
                    onClear={onClearFrecuencia}
                    onChange={onSelecFrecuencia}
                  />

                  <Form.Item
                    name={"selectFrecuenciaBit"}
                    rules={[{ required: false, message: "" }]}
                  ></Form.Item>
                </>
              )}
            </Col>
            <Col xs={8} md={8} xl={8} className="col-content-eval">
              {tipoFlujo == FLUJO_CONSULTA ? (
                <span className="span-freim">
                  {
                    optionsImpactos?.find((i) => i.value == idImpactoSelected)
                      ?.label
                  }
                </span>
              ) : (
                <>
                  <Select
                    name="selectImpactoBit"
                    className="select-eval-desem"
                    suffixIcon={
                      <CaretDownOutlined className="icon-select-gen" />
                    }
                    value={idImpactoSelected}
                    options={optionsImpactos}
                    allowClear={isSust && tipoFlujo == FLUJO_CAPTURA}
                    onClear={onClearImpacto}
                    onChange={onSelecImpacto}
                  />

                  <Form.Item
                    name={"selectImpactoBit"}
                    rules={[{ required: false, message: "" }]}
                  ></Form.Item>
                </>
              )}
            </Col>
            <Col xs={8} md={8} xl={8} className="col-content-eval">
              <div className="divCenterUtil">
                <div style={{ display: "flex", justifyContent: "center" }}>
                  <div
                    className="div-color-eval"
                    style={{ backgroundColor: colorRiesgo }}
                  />
                  &emsp;<span className="div-span-riesgo">{descRiesgo}</span>
                </div>
              </div>
            </Col>
          </Row>

          <br />
          <div>
            <span className="span-medium-gen1">{B.ETQ_OBSERVACIONES}</span>
            {tipoFlujo != FLUJO_CONSULTA ? (
              <span className="span-medium-gen1-opac">{B.ETQ_OPCIONAL}</span>
            ) : (
              ""
            )}

            <div style={{ maxWidth: "900px", marginTop: "10px" }}>
              {tipoFlujo == FLUJO_CONSULTA ? (
                <span className="area-obs-eval">{observaciones}</span>
              ) : (
                <>
                  <TextArea
                    maxLength={maxLength}
                    style={{ marginTop: "12px" }}
                    onChange={setObservacionesDesemp}
                    onBlur={handleBlurObservaciones}
                    value={observaciones}
                    autoSize={{ minRows: 4, maxRows: 6 }}
                    showCount={false}
                    className="area-obs-eval"
                  />
                  <span
                    style={{
                      fontStyle: "italic",
                      color: maxLength - charCount <= 10 ? "red" : "#B6B6B6",
                      fontSize: "14px",
                      marginTop: "20px",
                    }}
                  >
                    {`${maxLength - charCount} ` + B.ETQ_CARACTERES_REST}
                  </span>
                </>
              )}
            </div>
          </div>

          <br />
          <div id="cheks-resp-eval">
            <span className="span-medium-gen1">{B.ETQ_RESPONSABLES}</span>
            <br />
            {tipoFlujo == FLUJO_CONSULTA ? (
              <>
                <div className="separador-resp" />
                {responsablesPrev?.map((r) => {
                  let nombreIntegrante = [
                    r.gradoAcademico || "",
                    r.nombreIntegrante || "",
                    r.primerApellidoIntegrante || "",
                    r.segundoApellidoIntegrante || "",
                  ]
                    .join(" ")
                    .trim();
                  return (
                    <Fragment key={r.idResponsableBitacora}>
                      <span className="span-cons-resp">
                        {(nombreIntegrante.trim()
                          ? nombreIntegrante + " - "
                          : "") + r.iniciales}
                      </span>
                      <br />
                    </Fragment>
                  );
                })}
              </>
            ) : (
              <>
                <Checkbox.Group
                  style={{ marginTop: "10px" }}
                  options={responsablesChecks}
                  value={responsablesChecked}
                  onChange={onChangeCheckResponsables}
                />
                <Form.Item
                  name={"ChecksResponablesBit"}
                  rules={[{ required: true, message: "" }]}
                ></Form.Item>
              </>
            )}
          </div>
          <br />

          <VCustomModal
            open={openModalErrorEval}
            tipoModal={0}
            mensaje={msjErrorEval}
            footer={null}
            cerrarModal={cerrarModalErrorEval}
          />
        </>
      )}
    </Layout>
  );
}

export default EvaluacionDesempenio;
