import { ExclamationCircleOutlined, LoadingOutlined } from "@ant-design/icons";
import { Button, Upload, Modal, Popover, Flex, Spin } from "antd";
import React, { useState, useMemo, useEffect } from "react";
import icono_sin_foto from "../../img/icono_sin_foto.svg";
import icono_adjuntar from "../../img/adjuntar.svg";
import icono_trash from "../../img/icono_trash.svg";
import {
  MSJ_FORMATO_INVALIDO_FOTO_SECAE,
  MSJ_TAMANIO_INVALIDO_FOTO_SECAE,
  ETQ_ADJUNTAR,
  ETQ_CAMBIAR_FOTO,
  ETQ_SIN_FOTO,
  FLUJO_CONSULTA,
  FLUJO_CAPTURA,
  MAX_TAMANIO_FOTO,
  CODE_SUCCESS,
  ETQ_FOTO,
  MSJ_FOTO_NO_DISPONIBLE,
  MSJ_ERROR_FOTO,
  MSJ_BORRAR_FOTO,
} from "../../utils/constantes";
import { obtenerFotoAspirante } from "../../utils/busquedaSeCae/funciones";

//evita un warning, porque el componente
//pretende enviar la imagena a un repositorio/servicio
const dummyRequest = (options) => {
  const { onSuccess } = options;
  setTimeout(() => {
    onSuccess("ok");
  }, 0);
};

function LoadFoto({ tipoFlujo, urlFotoAspirante, onChangeFoto }) {
  const [imgB64, setImgB64] = useState();
  const [openModalError, setOpenModalError] = useState(false);
  const [msjError, setMsjError] = useState("");
  const [loading, setLoading] = useState(false);
  const [buscarFotoWs, setBuscarFotoWs] = useState(true);
  const custom_css_antd = `
                .ant-popover-content {
                    position: absolute !important;
                    margin-top: 65px;
                    margin-left: -25px;
                }
                .ant-popover-inner {
                    background-color: transparent !important;
                    box-shadow: none !important;
                }
            `;
  const [mensajeFoto, setMensajeFoto] = useState("");

  const noArrow = useMemo(() => {
    return false;
  });

  useEffect(() => {
    if (buscarFotoWs) {
      setBuscarFotoWs(false);
      if (urlFotoAspirante) {
        setLoading(true);
        let request = {
          urlFoto: urlFotoAspirante,
        };
        obtenerFotoAspirante(request)
          .then((data) => {
            if (data.code == CODE_SUCCESS) {
              setImgB64(data.data);
            } else setMensajeFoto(MSJ_FOTO_NO_DISPONIBLE);
            setLoading(false);
          })
          .catch((error) => {
            console.log(error);
            mostrarModalError(MSJ_ERROR_FOTO);
            setLoading(false);
          });
      } else {
        setMensajeFoto(ETQ_SIN_FOTO);
      }
    }
  });

  const handleChange = ({ fileList }) => {
    setLoading(true);
    try {
      if (validaImg(fileList[0])) {
        fileList.forEach(function (file, index) {
          let arrayExt = file.name.split(".");
          let ext = "." + arrayExt[arrayExt.length - 1];
          let reader = new FileReader();
          reader.onload = (e) => {
            setImgB64(e.target.result);
            if (onChangeFoto)
              onChangeFoto({
                imagenB64: e.target.result,
                extensionArchivo: ext,
              });

            setLoading(false);
          };
          reader.readAsDataURL(file.originFileObj);
        });
      } else setLoading(false);
    } catch (err) {
      consoler.log(err);
      setLoading(false);
    }
  };

  const validaImg = (file) => {
    if (!validaFormatoImagen(file.type)) {
      mostrarModalError(MSJ_FORMATO_INVALIDO_FOTO_SECAE);
      return false;
    } else if (file.size >= MAX_TAMANIO_FOTO) {
      mostrarModalError(MSJ_TAMANIO_INVALIDO_FOTO_SECAE);
      return false;
    } else return true;
  };

  const validaFormatoImagen = (tipoFormato) => {
    return (
      tipoFormato == "image/gif" ||
      tipoFormato == "image/png" ||
      tipoFormato == "image/jpg" ||
      tipoFormato == "image/jpeg" ||
      tipoFormato == "image/tiff"
    );
  };

  const mostrarModalError = (mssg) => {
    setMsjError(mssg);
    setOpenModalError(true);
  };
  const cerrarModalError = () => {
    setMsjError("");
    setOpenModalError(false);
  };
  const onDeleteFoto = () => {
    setImgB64("");
    onChangeFoto(null);
  };

  const opcDeleteImg = () => {
    return (
      <div>
        <Button className="pop-img-foto" onClick={onDeleteFoto}>
          <img src={icono_trash} alt={MSJ_BORRAR_FOTO} />
        </Button>
      </div>
    );
  };

  return (
    <div>
      <style>{custom_css_antd}</style>
      <div className="div-load-foto">
        <Flex align="center" gap="middle" style={{ width: "150px" }}>
          <Spin
            spinning={loading}
            indicator={<LoadingOutlined spin style={{ color: "#D7427F" }} />}
            size="large"
          >
            <Popover
              content={opcDeleteImg}
              arrow={noArrow}
              trigger="hover"
              open={imgB64 && tipoFlujo == FLUJO_CAPTURA ? null : false}
            >
              <img
                className="img-load-foto"
                src={imgB64 || icono_sin_foto}
                alt={ETQ_FOTO}
              />
            </Popover>
          </Spin>
        </Flex>
      </div>{" "}
      {(!imgB64 || imgB64.trim() === "") && (
        <div className="div-msj-foto">
          <span className="span-sin-foto">{mensajeFoto}</span>
        </div>
      )}
      <div id="divUploadFoto">
        {tipoFlujo != FLUJO_CONSULTA ? (
          <Upload
            customRequest={dummyRequest}
            onChange={handleChange}
            listType="picture"
            maxCount={1}
          >
            <Button className="button-load-foto">
              {imgB64 ? ETQ_CAMBIAR_FOTO : ETQ_ADJUNTAR}{" "}
              <img src={icono_adjuntar} alt={ETQ_ADJUNTAR} />
            </Button>
          </Upload>
        ) : (
          <>
            {imgB64 ? (
              <></>
            ) : (
              <div className="div-msj-foto">
                <span className="span-sin-foto"></span>
              </div>
            )}
          </>
        )}
      </div>
      <Modal
        title={null}
        className="modal-util-opc"
        open={openModalError}
        centered
        footer={null}
        onCancel={() => cerrarModalError()}
        maskClosable={false}
      >
        <div className="divCenterUtil">
          <ExclamationCircleOutlined className="icono_antd_warn" id="warning" />
        </div>
        <br />
        <div className="divCenterUtil">
          <p className="msj-modal-gen">{msjError}</p>
        </div>
      </Modal>
    </div>
  );
}

export default LoadFoto;
