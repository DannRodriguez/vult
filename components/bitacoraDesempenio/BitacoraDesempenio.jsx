import React, { useState, useEffect } from "react";
import { Layout, Row, Col, Form, Radio, Button, Spin } from "antd";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
import Miga from "../commonComponents/Miga";
import HeaderModulo from "../commonComponents/HeaderModulo";
import {
  Requerido,
  HeaderSeccion,
  BarInfo,
  InfoData,
  BarWarn,
} from "../commonComponents/AccessoriesComponents";
import LoadFoto from "../commonComponents/LoadFoto";
import {
  FLUJO_CAPTURA,
  FLUJO_CONSULTA,
  FLUJO_MODIFICA,
  CODE_SUCCESS,
  ETQ_FOTO,
} from "../../utils/constantes";
import AuthenticatedComponent from "../AuthenticatedComponent";
import * as B from "../../utils/bitacoraDesempenio/etiquetas";
import BusquedaSeCae from "../commonComponents/busquedaSeCae/BusquedaSeCae.jsx";
import { useSelector } from "react-redux";
import { guardarBitacoraDesemp } from "../../utils/bitacoraDesempenio/funciones.js";
import ExpedienteDesempenio from "../commonComponents/bitacoraDesempenio/ExpedienteDesempenio.jsx";
import EvaluacionDesempenio from "../commonComponents/bitacoraDesempenio/EvaluacionDesempenio.jsx";
import Template from "../interfaz/Template";
import logoLoader from "../../img/loader.gif";
import { apiClientPost } from "../../utils/apiClient";

const componentsNames = {
  radiosPrendas: "radiosPrendas",
};
const ORIGEN_MODULO_BITACORA = 1;

function BitacoraDesempenio() {
  let formRef = React.createRef();

  const user = useSelector((store) => store.loginUser.currentUser);
  const funcionarioSECAERedux = useSelector(
    (store) => store.selectFolioNombre.sustituidoSeleccionado
  );
  const [fotoAspirante, setFotoAspirante] = useState("");
  const [funcionarioSECAE, setFuncionarioSECAE] = useState(null);
  const [devolvioPrendas, setDevolvioPrendas] = useState();
  const [devolvioPrendasCons, setDevolvioPrendasCons] = useState();
  const [loading, setLoading] = useState(false);
  const [mounted, setMounted] = useState(false);

  const [openModalError, setOpenModalError] = useState(false);
  const [openModalElim, setOpenModalElim] = useState(false);
  const [openModalExito, setOpenModalExito] = useState(false);
  const [msjError, setMsjError] = useState("");
  const [msjQuest, setMsjQuest] = useState("");
  const [msjExito, setMsjExito] = useState("");
  const [cargoOtroProceso, setCargoOtroProceso] = useState();
  const [expedienteDesempenio, setExpedienteDesempenio] = useState();
  const [evaluacionDesempenio, setEvaluacionDesempenio] = useState();
  const [tipoFlujo, setTipoFlujo] = useState();
  const [estiloSpinComplemento, setEstiloSpinComplemento] = useState("");

  let custom_css_antd_spin = `
                #divContentBitacora{
                    .ant-spin-dot {
                    left: 45% !important; ${estiloSpinComplemento}
                 }
                }
            `;

  useEffect(() => {
    if (mounted) {
      if (funcionarioSECAERedux) {
        setLoading(true);
        setExpedienteDesempenio(null);
        setEvaluacionDesempenio(null);
        let request = {
          idDetalleProceso: funcionarioSECAERedux.idDetalleProceso,
          idParticipacion: funcionarioSECAERedux.idParticipacion,
          idAspirante: funcionarioSECAERedux.idAspirante,
        };
        //obtener información complementaria del aspirante para el módulo de bitacora de desempeño
        apiClientPost("obtenerAspiranteBitacora", request)
          .then((data) => {
            if (data.code === CODE_SUCCESS) {
              let funcionario = data.data;

              setTipoFlujo(
                funcionario.idBitacoraDesempenio
                  ? FLUJO_CONSULTA
                  : FLUJO_CAPTURA
              );

              let cargoOtro = funcionario.participoSE
                ? funcionario.participoSE
                : funcionario.participoCAE
                ? funcionario.participoCAE
                : funcionario.participoOtro
                ? funcionario.participoOtro
                : "";
              setCargoOtroProceso(cargoOtro);

              if (
                funcionario.devolvioPrendas == 1 ||
                funcionario.devolvioPrendas == 0
              ) {
                setDevolvioPrendas(funcionario.devolvioPrendas);
                setDevolvioPrendasCons(
                  funcionario.devolvioPrendas == 1 ? "Si" : "No"
                );
              } else setDevolvioPrendasCons("Sin respuesta");

              setLoading(false);
              setFuncionarioSECAE(funcionario);
            } else {
              setLoading(false);
              mostrarModalError(B.MSJ_ERROR_INFO_ASPIRANTE);
            }
          })
          .catch((error) => {
            console.log(error);
            mostrarModalError(B.MSJ_ERROR_INFO_ASPIRANTE);
            setLoading(false);
          });
      }
    } else setMounted(true);
  }, [funcionarioSECAERedux]);

  const handleCargarImagen = (file) => {
    setFotoAspirante(file);
  };

  const isPuestoSE = (idPuesto) => {
    return idPuesto == B.ID_PUESTO_SE || idPuesto == B.ID_PUESTO_SE_TEMP;
  };

  const mostrarModalError = (msg) => {
    setMsjError(msg);
    setOpenModalError(true);
  };

  const cerrarModalError = () => {
    setMsjError("");
    setOpenModalError(false);
  };

  const onGuardarBitacora = () => {
    setEstiloSpinComplemento(" margin-top: 50%;");
    setLoading(true);

    if (validarDatosBitacora()) {
      let devolvioPrend = null;
      if (devolvioPrendas != null && devolvioPrendas != undefined)
        devolvioPrend = devolvioPrendas ? "S" : "N";

      const expedienteCopy = Object.assign({}, expedienteDesempenio);
      let request = {
        idProcesoElectoral: funcionarioSECAE.idProcesoElectoral,
        idDetalleProceso: funcionarioSECAE.idDetalleProceso,
        idParticipacion: funcionarioSECAE.idParticipacion,
        idAspirante: funcionarioSECAE.idAspirante,

        idBitacoraDesempenio: funcionarioSECAE.idBitacoraDesempenio,
        origenBitacora: ORIGEN_MODULO_BITACORA,
        tipoAccion: tipoFlujo,
        docFotoB64: fotoAspirante?.imagenB64,
        extensionFoto: fotoAspirante?.extensionArchivo,
        devolvioPrendas: devolvioPrend,
        expedienteDesempenio: expedienteCopy,
        evaluacionDesempenio: evaluacionDesempenio,
        usuario: user.username,
      };

      const formData = new FormData();
      if (request.expedienteDesempenio && request.expedienteDesempenio.file) {
        let fileExp = request.expedienteDesempenio.file;
        formData.append(B.ATRIB_FIL_EXP, fileExp);
        delete request.expedienteDesempenio.file;
      }
      formData.append(
        B.ATRIB_REQ_BIT,
        new Blob([JSON.stringify(request)], { type: "application/json" })
      );

      guardarBitacoraDesemp(formData)
        .then((data) => {
          if (data.code === CODE_SUCCESS) {
            setMsjExito(B.MSJ_EXITO_GUARDAR_BITACORA);
            setOpenModalExito(true);
            setLoading(false);
          } else {
            console.log(data);
            mostrarModalError(B.MSJ_ERROR_GUARDAR_BITACORA);
            setLoading(false);
          }
        })
        .catch((error) => {
          console.log(error);
          mostrarModalError(B.MSJ_ERROR_GUARDAR_BITACORA);
          setLoading(false);
        });
    } else {
      setEstiloSpinComplemento("");
      setLoading(false);
    }
  };

  const validarDatosBitacora = () => {
    let expedienteValido = true;
    let evaluacionValida = true;
    let elementId = "";

    if (evaluacionDesempenio) {
      const sinResponsables =
        !Array.isArray(evaluacionDesempenio.responsables) ||
        evaluacionDesempenio.responsables.length === 0;

      const esNuevaEvaluacion =
        evaluacionDesempenio.cambioResponsables === undefined;

      if (
        sinResponsables &&
        (evaluacionDesempenio.cambioResponsables || esNuevaEvaluacion)
      ) {
        evaluacionValida = false;
        formRef.current.setFields([
          { name: "ChecksResponablesBit", errors: [B.ETQ_REQUERIDO] },
        ]);
      }

      if (!evaluacionDesempenio.valoracionRiesgo) {
        evaluacionValida = false;
        elementId = "rowEvaluacionImpFec";

        if (!evaluacionDesempenio.idFrecuencia)
          formRef.current.setFields([
            { name: "selectFrecuenciaBit", errors: [B.ETQ_REQUERIDO] },
          ]);

        if (!evaluacionDesempenio.idImpacto)
          formRef.current.setFields([
            { name: "selectImpactoBit", errors: [B.ETQ_REQUERIDO] },
          ]);
      }
    } else {
      evaluacionValida = false;
      formRef.current.setFields([
        { name: "selectFrecuenciaBit", errors: [B.ETQ_REQUERIDO] },
        { name: "selectImpactoBit", errors: [B.ETQ_REQUERIDO] },
        { name: "ChecksResponablesBit", errors: [B.ETQ_REQUERIDO] },
      ]);

      elementId = "rowEvaluacionImpFec";
    }

    if (expedienteDesempenio) {
      if (
        expedienteDesempenio.correo == "N" &&
        expedienteDesempenio.citatorio == "N" &&
        expedienteDesempenio.constancia == "N"
      ) {
        expedienteValido = false;
        formRef.current.setFields([
          { name: "checksDoctos", errors: [B.ETQ_REQUERIDO] },
        ]);
        elementId = "divExpedienteDesemp";
      }
    } else expedienteValido = true;

    if (elementId) scrollToElement(elementId);

    return expedienteValido && evaluacionValida;
  };

  const scrollToElement = (idElement) => {
    document.getElementById(idElement).scrollIntoView({ behavior: "smooth" });
  };

  const onOpenEliminarBitacora = () => {
    setMsjQuest(B.MSJ_ELIMINAR_BIT);
    setOpenModalElim(true);
  };

  const onEliminarBitacora = () => {
    setEstiloSpinComplemento(" margin-top: 50%;");
    setLoading(true);
    setOpenModalElim(false);

    let request = {
      idProcesoElectoral: funcionarioSECAE.idProcesoElectoral,
      idDetalleProceso: funcionarioSECAE.idDetalleProceso,
      idParticipacion: funcionarioSECAE.idParticipacion,
      idBitacoraDesempenio: funcionarioSECAE.idBitacoraDesempenio,
      idAspirante: funcionarioSECAE.idAspirante,
    };

    apiClientPost("eliminarBitacoraDesemp", request)
      .then((data) => {
        if (data.code === CODE_SUCCESS) {
          setMsjExito(B.MSJ_EXITO_ELIM_BITACORA);
          setLoading(false);
          setOpenModalExito(true);
        } else {
          setLoading(false);
          mostrarModalError(B.MSJ_ERROR_ELIM_BITACORA);
        }
        setEstiloSpinComplemento("");
      })
      .catch((error) => {
        console.log(error);
        mostrarModalError(B.MSJ_ERROR_ELIM_BITACORA);
        setEstiloSpinComplemento("");
        setLoading(false);
      });
  };

  return (
    <AuthenticatedComponent>
      <Template>
        <style>{custom_css_antd_spin}</style>
        <div id="divContentBitacora" style={{ marginTop: "20px" }}>
          <Spin
            spinning={loading}
            indicator={
              <img
                src={logoLoader}
                alt={ETQ_FOTO}
                style={{ width: "200px", height: "200px" }}
              />
            }
          >
            <Form layout="vertical" ref={formRef} id="form-bitacora">
              <Layout id="content-bitacora">
                <Miga />
                <HeaderModulo />
                <Requerido />
                <BusquedaSeCae
                  moduloSust={4}
                  tipoFlujo={FLUJO_CAPTURA}
                  setIsPendiente={() => false}
                />

                {funcionarioSECAE && funcionarioSECAERedux ? (
                  <>
                    {tipoFlujo == FLUJO_CONSULTA ? (
                      <BarWarn text={B.ETQ_BITACORA_PREVIA} />
                    ) : (
                      <BarInfo text={B.ETQ_FORMATOS_FOTO} />
                    )}

                    <HeaderSeccion
                      text={
                        isPuestoSE(funcionarioSECAE.idPuesto)
                          ? B.ETQ_INFO_SE
                          : B.ETQ_INFO_CAE
                      }
                    />
                    <Row id="info-funcionario">
                      <Col xs={0} md={0} xl={2} />

                      <Col xs={24} md={6} xl={4}>
                        <LoadFoto
                          key={funcionarioSECAE.claveElector}
                          urlFotoAspirante={funcionarioSECAERedux?.urlFoto}
                          tipoFlujo={tipoFlujo}
                          onChangeFoto={handleCargarImagen}
                        />
                        <br />
                      </Col>

                      <Col xs={24} md={18} xl={18}>
                        <Row>
                          <InfoData
                            info={B.ETQ_FOLIO}
                            data={funcionarioSECAE.folio}
                          />
                          <InfoData
                            info={B.ETQ_NOMBRE}
                            data={funcionarioSECAE.nombreCompleto}
                          />
                          <InfoData
                            info={B.ETQ_CLAVE_ELEC}
                            data={funcionarioSECAE.claveElector}
                          />
                          <InfoData
                            info={B.ETQ_CARGO}
                            data={funcionarioSECAE.puesto}
                          />
                          <InfoData
                            info={
                              isPuestoSE(funcionarioSECAE.idPuesto)
                                ? B.ETQ_EVAL_SE
                                : B.ETQ_EVAL_CAE
                            }
                            data={
                              isPuestoSE(funcionarioSECAE.idPuesto)
                                ? funcionarioSECAE.evaluacionIntegralSE
                                : funcionarioSECAE.evaluacionIntegralCAE
                            }
                          />
                          <InfoData
                            info={B.ETQ_FECHA_CONTRATACION}
                            data={
                              funcionarioSECAE.fechaSustAlta
                                ? funcionarioSECAE.fechaSustAlta
                                : funcionarioSECAERedux?.fechaInicioContratacion
                            }
                          />
                          <InfoData
                            info={B.ETQ_HA_PARTICIPADO}
                            data={funcionarioSECAE.participoProceso}
                          />
                          <InfoData
                            info={B.ETQ_CUAL}
                            data={funcionarioSECAE.cualProceso}
                          />
                          <InfoData
                            info={B.ETQ_CARGO_OTRO_PROCESO}
                            data={cargoOtroProceso}
                          />

                          <Col
                            xs={24}
                            md={12}
                            xl={8}
                            style={{ paddingRight: "20px" }}
                          >
                            <div>
                              <div>
                                <span className="span-info-gen">
                                  {B.ETQ_DEVOLVIO_PRENDAS}
                                </span>
                              </div>
                              {tipoFlujo == FLUJO_CONSULTA ? (
                                <div>
                                  <span className="span-data-gen">
                                    {devolvioPrendasCons}
                                  </span>
                                </div>
                              ) : (
                                <div>
                                  <Form.Item
                                    name={componentsNames.radiosPrendas}
                                    rules={[{ required: false, message: "" }]}
                                  >
                                    <Radio.Group
                                      disabled={false}
                                      value={devolvioPrendas}
                                      onChange={(e) =>
                                        setDevolvioPrendas(e.target.value)
                                      }
                                      name={componentsNames.radiosPrendas}
                                    >
                                      <Radio
                                        className="span-data-gen"
                                        value={1}
                                      >
                                        Sí
                                      </Radio>
                                      &emsp;&emsp;
                                      <Radio
                                        className="span-data-gen"
                                        value={0}
                                      >
                                        No
                                      </Radio>
                                    </Radio.Group>
                                  </Form.Item>
                                </div>
                              )}
                              <br />
                            </div>
                          </Col>
                        </Row>
                      </Col>
                    </Row>
                  </>
                ) : (
                  <></>
                )}
              </Layout>

              {funcionarioSECAE && funcionarioSECAERedux ? (
                <ExpedienteDesempenio
                  key={funcionarioSECAE.idAspirante}
                  formRef={formRef}
                  onChangeExpediente={setExpedienteDesempenio}
                  tipoFlujo={tipoFlujo}
                  funcionarioSECAE={funcionarioSECAE}
                />
              ) : (
                <></>
              )}

              {funcionarioSECAE && funcionarioSECAERedux ? (
                <EvaluacionDesempenio
                  key={funcionarioSECAE.claveElector}
                  formRef={formRef}
                  onChangeEvaluacion={setEvaluacionDesempenio}
                  tipoFlujo={tipoFlujo}
                  funcionarioSECAE={funcionarioSECAE}
                />
              ) : (
                <></>
              )}

              <div
                className="divCenterUtil"
                style={{ marginTop: "35px", marginBottom: "35px" }}
              >
                {funcionarioSECAERedux && tipoFlujo == FLUJO_CAPTURA ? (
                  <Button
                    onClick={() => onGuardarBitacora()}
                    className="button-save-bit"
                  >
                    {B.ETQ_GUARDAR}
                  </Button>
                ) : (
                  ""
                )}
                {funcionarioSECAERedux && tipoFlujo == FLUJO_CONSULTA ? (
                  <>
                    <Button
                      onClick={() => onOpenEliminarBitacora()}
                      className="button-delete-bit"
                    >
                      {B.ETQ_ELIMINAR}
                    </Button>
                    <Button
                      onClick={() => setTipoFlujo(FLUJO_MODIFICA)}
                      className="button-save-bit"
                    >
                      {B.ETQ_MODIFICAR}
                    </Button>
                  </>
                ) : (
                  ""
                )}
                {funcionarioSECAERedux && tipoFlujo == FLUJO_MODIFICA ? (
                  <Button
                    onClick={() => onGuardarBitacora()}
                    className="button-save-bit"
                  >
                    {B.ETQ_GUARDAR_CAM}
                  </Button>
                ) : (
                  ""
                )}
              </div>
            </Form>
          </Spin>
        </div>

        <VCustomModal
          title={null}
          open={openModalError}
          tipoModal={0}
          mensaje={msjError}
          cerrarModal={cerrarModalError}
          footer={null}
        />

        <VCustomModal
          open={openModalElim}
          tipoModal={0}
          mensaje={msjQuest}
          cerrarModal={() => setOpenModalElim(false)}
          footer={
            <div className="divCenterUtil">
              <Button
                onClick={() => setOpenModalElim(false)}
                className="button-delete-bit"
              >
                {B.ETQ_CANCELAR}
              </Button>
              <Button
                onClick={() => onEliminarBitacora()}
                className="button-save-bit"
              >
                {B.ETQ_ACEPTAR}
              </Button>
            </div>
          }
        />

        <VCustomModal
          title={null}
          open={openModalExito}
          tipoModal={1}
          mensaje={msjExito}
          footer={null}
          cerrarModal={() => {
            setOpenModalExito(false);
            location.reload();
          }}
        />
      </Template>
    </AuthenticatedComponent>
  );
}

export default BitacoraDesempenio;
