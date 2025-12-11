import { Layout, Button, Upload, Checkbox, Form, Spin } from "antd";
import {
  HeaderSeccion,
  BarInfo,
} from "../../commonComponents/AccessoriesComponents";
import * as B from "../../../utils/bitacoraDesempenio/etiquetas";
import React, { useState, useEffect } from "react";
import icono_adjuntar from "../../../img/adjuntar_pink.svg";
import icono_docto from "../../../img/documento.svg";
import icono_eliminar from "../../../img/eliminar.svg";
import icono_descargar from "../../../img/descargar.svg";
import {
  validarFormatoExpediente,
  getDimensionArchivo,
} from "../../../utils/bitacoraDesempenio/funciones.js";
import {
  FLUJO_CAPTURA,
  FLUJO_MODIFICA,
  FLUJO_CONSULTA,
  CODE_SUCCESS,
} from "../../../utils/constantes.js";
import logoLoader from "../../../img/loader.gif";
import { apiClientPost } from "../../../utils/apiClient";
import { descargaExpediente } from '../../../utils/bitacoraDesempenio/funciones';

//evita un warning, porque el componente
//pretende enviar la imagena a un repositorio/servicio
const dummyRequest = (options) => {
  const { onSuccess } = options;
  setTimeout(() => {
    onSuccess("ok");
  }, 0);
};

function ExpedienteDesempenio({
  onChangeExpediente,
  tipoFlujo,
  formRef,
  funcionarioSECAE,
  expedienteOriginal,
}) {
  const [documento, setDocumento] = useState(false);
  const [documentoPrevio, setDocumentoPrevio] = useState(false);
  const [loading, setLoading] = useState(false);
  const [nombreDocto, setNombreDocto] = useState();
  const [dimensionDocto, setDimensionDocto] = useState();
  const [msjErrorEx, setMsjErrorEx] = useState("");
  const [checkCorreo, setCheckCorreo] = useState(false);
  const [checkcitatorio, setCheckcitatorio] = useState(false);
  const [checkConstancia, setCheckConstancia] = useState(false);
  const [disabledChecks, setDisabledChecks] = useState(true);
  const [dataExpediente, setDataExpediente] = useState();
  const [obtenerExpediente, setObtenerExpediente] = useState(true);

  useEffect(() => {
    if (
      (tipoFlujo === FLUJO_CONSULTA || tipoFlujo === FLUJO_MODIFICA) &&
      funcionarioSECAE &&
      obtenerExpediente
    ) {
      const fetchExpediente = async () => {
        try {
          setObtenerExpediente(false);
          setLoading(true);
          setMsjErrorEx(null);

          const request = {
            idDetalleProceso: funcionarioSECAE.idDetalleProceso,
            idParticipacion: funcionarioSECAE.idParticipacion,
            idAspirante: funcionarioSECAE.idAspirante,
          };

          const data = await apiClientPost("obtenerExpedienteDesemp", request);

          if (data.code === CODE_SUCCESS) {
            actualizarEstadoExpediente(data.data);
          } else {
            setMsjErrorEx(B.MSJ_ERROR_EXPEDIENTE_ASPIRANTE);
          }
        } catch (error) {
          console.error(error);
          setMsjErrorEx(B.MSJ_ERROR_EXPEDIENTE_ASPIRANTE);
        } finally {
          setLoading(false);
        }
      };

      fetchExpediente();
    }
  }, [tipoFlujo, funcionarioSECAE, obtenerExpediente]);

  const actualizarEstadoExpediente = (bitacoraDesemp) => {
    if (!bitacoraDesemp?.rutaDocumentos) return;

    setDocumentoPrevio(true);
    setDocumento(true);
    setDisabledChecks(tipoFlujo === FLUJO_CONSULTA);
    setDimensionDocto(getDimensionArchivo(bitacoraDesemp.dimensionArchivo));
    setNombreDocto(bitacoraDesemp.nombreDocumento);
    setCheckCorreo(bitacoraDesemp.documentoCorreo === "S");
    setCheckcitatorio(bitacoraDesemp.documentoCitatorio === "S");
    setCheckConstancia(bitacoraDesemp.documentoConstancia === "S");

    const dataExpediente = {
      correo: bitacoraDesemp.documentoCorreo,
      citatorio: bitacoraDesemp.documentoCitatorio,
      constancia: bitacoraDesemp.documentoConstancia,
    };
    setDataExpediente(dataExpediente);
    onChangeExpediente(dataExpediente);
    if (expedienteOriginal) expedienteOriginal(dataExpediente);
  };

  const whenUpFile = (file) => {
    setLoading(true);

    const esValido = validaDoc(file);
    const { name } = file;
    const ext = name.includes(".") ? `.${name.split(".").pop()}` : "";

    const dataExpediente = esValido
      ? {
        file,
        nombreDocto: name,
        extensionArchivo: ext,
        correo: toSN(checkCorreo),
        citatorio: toSN(checkcitatorio),
        constancia: toSN(checkConstancia),
      }
      : {
        file,
        nombreDocto: name,
        correo: "N",
        citatorio: "N",
        constancia: "N",
      };

    setDataExpediente(dataExpediente);
    onChangeExpediente(dataExpediente);
    setDocumento(esValido);
    setDisabledChecks(!esValido);
    setLoading(false);
  };

  const toSN = (value) => (value ? "S" : "N");

  const validaDoc = (file) => {
    if (!validarFormatoExpediente(file.type, file.name)) {
      onDeleteDocto();
      setMsjErrorEx(B.MSJ_FORMATO_INVALIDO_EXP_SECAE);
      return false;
    } else if (!(file.size <= 20971520)) {
      onDeleteDocto();
      setMsjErrorEx(B.MSJ_TAMANIO_INVALIDO_EXP_SECAE);
      return false;
    } else {
      //formato y dimensión correcto
      setNombreDocto(file.name);
      setDimensionDocto(getDimensionArchivo(file.size));
      setMsjErrorEx(null);
      return true;
    }
  };

  const onDeleteDocto = () => {
    setNombreDocto(null);
    setDimensionDocto(null);
    setCheckCorreo(false);
    setCheckcitatorio(false);
    setCheckConstancia(false);
    setDisabledChecks(true);
    setDocumento(false);
    setMsjErrorEx(null);

    let dataExpediente = null;
    if (documentoPrevio) dataExpediente = { eliminarExpediente: 1 };

    setDataExpediente(dataExpediente);
    onChangeExpediente(dataExpediente);
    formRef.current.setFields([{ name: "checksDoctos", errors: [""] }]);
  };

  const onChangeCheckCorreo = (e) => {
    let check = e.target.checked;

    if (onChangeExpediente) {
      let infoExpediente = { ...dataExpediente, correo: check ? "S" : "N" };
      setDataExpediente(infoExpediente);
      onChangeExpediente(infoExpediente);
    }

    formRef.current.setFields([{ name: "checksDoctos", errors: [""] }]);
    setCheckCorreo(check);
  };

  const onChangeCheckCitatorio = (e) => {
    let check = e.target.checked;

    if (onChangeExpediente) {
      let infoExpediente = { ...dataExpediente, citatorio: check ? "S" : "N" };
      setDataExpediente(infoExpediente);
      onChangeExpediente(infoExpediente);
    }

    formRef.current.setFields([{ name: "checksDoctos", errors: [""] }]);
    setCheckcitatorio(check);
  };

  const onChangeCheckConstancia = (e) => {
    let check = e.target.checked;

    if (onChangeExpediente) {
      let infoExpediente = { ...dataExpediente, constancia: check ? "S" : "N" };
      setDataExpediente(infoExpediente);
      onChangeExpediente(infoExpediente);
    }

    formRef.current.setFields([{ name: "checksDoctos", errors: [""] }]);
    setCheckConstancia(check);
  };

  const onDescargaExpediente = async () => {
    setLoading(true);
    setMsjErrorEx(null);

    const request = {
      idDetalleProceso: funcionarioSECAE?.idDetalleProceso,
      idParticipacion: funcionarioSECAE?.idParticipacion,
      idAspirante: funcionarioSECAE?.idAspirante,
    };

    try {
      const resultado = await descargaExpediente(request, nombreDocto);
      if (!resultado) {
        setMsjErrorEx(B.MSJ_ERROR_DESCARGA_DOCUMENTO_ASPIRANTE);
      }
    } catch (error) {
      console.log(error);
      setMsjErrorEx(B.MSJ_ERROR_EXPEDIENTE_ASPIRANTE);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout className="content-comp-bitacora">
      {/*<Loader blocking={loading}/>*/}
      <Spin
        spinning={loading}
        indicator={
          <img src={logoLoader} style={{ width: "150px", height: "150px" }} />
        }
      >
        <HeaderSeccion text={B.ETQ_EXPEDIENTE_DESEM} />
        {tipoFlujo == FLUJO_CONSULTA ? (
          ""
        ) : (
          <BarInfo text={B.ETQ_FORMATOS_EXP} />
        )}
        <div className="div-msj-expediente">
          {msjErrorEx && <span className="span-error">{msjErrorEx}</span>}

          {!documento && tipoFlujo == FLUJO_CONSULTA && (
            <span className="span-content-docto">
              {B.MSJ_SIN_SOPORTE_DOC_BITACORA}
            </span>
          )}
        </div>
        <div id="divExpedienteDesemp" className="divCenterUtil">
          {nombreDocto && (
            <div className="div-nombre-docto">
              <div>
                <img src={icono_docto} />
              </div>
              <div className="div-span-nombre-docto">
                <span className="span-nombre-docto">
                  {nombreDocto + (dimensionDocto || "")}
                </span>
              </div>
            </div>
          )}
          {documento &&
            (tipoFlujo == FLUJO_CAPTURA || tipoFlujo == FLUJO_MODIFICA) ? (
            <Button
              className="button-elim-exp"
              style={{ marginRight: "20px" }}
              onClick={() => onDeleteDocto()}
            >
              {B.ETQ_ELIMINAR_EXP} <img src={icono_eliminar} />
            </Button>
          ) : (
            <></>
          )}

          {documento && tipoFlujo == FLUJO_CONSULTA ? (
            <Button
              className="button_descarga-exp"
              onClick={() => onDescargaExpediente()}
            >
              {B.DESCARGAR} <img src={icono_descargar} />
            </Button>
          ) : (
            <></>
          )}

          {tipoFlujo == FLUJO_CAPTURA || tipoFlujo == FLUJO_MODIFICA ? (
            <Upload
              customRequest={dummyRequest}
              beforeUpload={whenUpFile}
              listType="picture"
              maxCount={1}
            >
              <Button className="button-load-exp">
                {documento ? B.CAMBIAR_DOC : B.CARGAR_DOC}{" "}
                <img src={icono_adjuntar} />
              </Button>
            </Upload>
          ) : (
            <></>
          )}
          {!documento && tipoFlujo == FLUJO_CONSULTA ? (
            <></>
          ) : (
            <>
              <div style={{ marginTop: "30px", marginBottom: "20px" }}>
                <span className="span-content-docto">{B.ETQ_TIPOS_DOC}</span>
              </div>

              <div id="divChecksDoctos">
                <Form.Item
                  name={"checksDoctos"}
                  rules={[{ required: false, message: "" }]}
                  validateTrigger={false}
                >
                  <div>
                    <Checkbox
                      checked={checkCorreo}
                      disabled={!documento || tipoFlujo == FLUJO_CONSULTA}
                      className="span-check-docto"
                      onChange={(e) => onChangeCheckCorreo(e)}
                    >
                      {B.ETQ_CORREO}
                    </Checkbox>
                    &emsp;
                    <Checkbox
                      checked={checkcitatorio}
                      disabled={!documento || tipoFlujo == FLUJO_CONSULTA}
                      className="span-check-docto"
                      onChange={(e) => onChangeCheckCitatorio(e)}
                    >
                      {B.ETQ_CITATORIO}
                    </Checkbox>
                    &emsp;
                    <Checkbox
                      checked={checkConstancia}
                      disabled={!documento || tipoFlujo == FLUJO_CONSULTA}
                      className="span-check-docto"
                      onChange={(e) => onChangeCheckConstancia(e)}
                    >
                      {B.ETQ_CONSTANCIA}
                    </Checkbox>
                  </div>
                </Form.Item>
              </div>
            </>
          )}
        </div>
      </Spin>
    </Layout>
  );
}

export default ExpedienteDesempenio;
