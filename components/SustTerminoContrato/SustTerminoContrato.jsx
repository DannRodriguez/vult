import React, { useState, useEffect, useRef } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import { Button, Layout, Form, notification, Skeleton, Row, Col } from "antd";

import AuthenticatedComponent from "../AuthenticatedComponent";
import { Loader } from "../interfaz/Loader.jsx";
import Template from "../../components/interfaz/Template";
import HeaderModulo from "../commonComponents/HeaderModulo";
import { CMenuAcciones } from "../interfaz/navbar/CMenuAcciones.jsx";
import Miga from "../commonComponents/Miga";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
import {
  Requerido,
  BarInfo,
  HeaderSeccion,
} from "../commonComponents/AccessoriesComponents";
import BusquedaSeCae from "../commonComponents/busquedaSeCae/BusquedaSeCae.jsx";
import CausaSustitucion from "../commonComponents/causasSustitucion/CausaSustitucion";
import Sustitutos from "./Sustitutos.jsx";
import LoadFoto from "../commonComponents/LoadFoto.jsx";
import VInfoAspirante from "../commonComponents/VInfoAspirante.jsx";

import { apiClientPost } from "../../utils/apiClient.js";

import {
  ACCION_CAPTURA,
  ACCION_CONSULTA,
  SUST_TERM_CONTRATO,
  CODE_SUCCESS,
  ID_PUESTO_LISTA_RESERVA,
  ID_PUESTO_RECONTRATACION_SE,
  DECLINO_CARGO,
  ID_PUESTO_RESCISION_SE,
  ID_PUESTO_RESCISION_CAE,
  ID_PUESTO_PROMOCION,
} from "../../utils/constantes.js";

import {
  FORMATOS_PERMITIDOS_FOTOGRAFIA,
  MSJ_EXITO_GUARDAR,
  MSJ_EXITO_MODIFICAR,
  MSJ_ERROR_GUARDAR,
  MSJ_ERROR_MODIFICAR,
} from "../../utils/causaSustitucion/etiquetas.js";

function SustTerminoContrato() {
  const formRef = useRef();
  const [form] = Form.useForm();

  useEffect(() => {
    formRef.current = form;
  }, [form]);
  const sustituidoSeleccionado = useSelector(
    (store) => store.selectFolioNombre.sustituidoSeleccionado
  );
  const isLoadingSelectFolioNombre = useSelector(
    (store) => store.selectFolioNombre.isLoadingSelectFolioNombre
  );

  const user = useSelector((store) => store.loginUser.currentUser);
  const proceso = useSelector((store) => store.menu.proceso);
  const idParticipacion = useSelector((store) => store.menu.idParticipacion);
  const menuAcciones = useSelector((store) => store.menu.menuAcciones);
  const navigate = useNavigate();

  const [api, contextHolder] = notification.useNotification();

  const [idSustitucionSeleccionada, setIdSustitucionSeleccionada] = useState(0);
  const [causaSusti, setCausaSusti] = useState();
  const [vistaActual, setVistaActual] = useState(ACCION_CAPTURA);
  const [block, setBlock] = useState(false);

  const [sustitucionSE, setSustitucionSE] = useState();
  const [sustitucionCAE, setSustitucionCAE] = useState();

  const [seIncapacitado, setSeIncapacitado] = useState();
  const [seTemporal, setSeTemporal] = useState();
  const [caeIncapacitado, setCaeIncapacitado] = useState();
  const [caeTemporal, setCaeTemporal] = useState();

  const [imagenB641, setImagenB641] = useState(null);
  const [extensionImagen1, setExtensionImagen1] = useState(null);

  const [imagenB642, setImagenB642] = useState(null);
  const [extensionImagen2, setExtensionImagen2] = useState(null);
  const [idAspiranteImagen2, setIdAspiranteImagen2] = useState(null);

  const [imagenB643, setImagenB643] = useState(null);
  const [extensionImagen3, setExtensionImagen3] = useState(null);
  const [idAspiranteImagen3, setIdAspiranteImagen3] = useState(null);

  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  useEffect(() => {
    formRef.current.setFieldsValue({
      idSustituido: undefined,
    });
    resetFormSustitucion();
  }, [vistaActual]);

  useEffect(() => {
    if (!sustituidoSeleccionado && !isLoadingSelectFolioNombre) {
      formRef.current.setFieldsValue({ idSustituido: null });
    }
    resetFormSustitucion();
    if (sustituidoSeleccionado != null) obtenerSustitucion();
  }, [sustituidoSeleccionado]);

  const resetFormSustitucion = () => {
    setSustitucionSE({});
    setSustitucionCAE({});

    setSeIncapacitado({});
    setSeTemporal({});
    setCaeIncapacitado({});
    setCaeTemporal({});

    setImagenB641(undefined);
    setExtensionImagen1(undefined);
    setImagenB642(undefined);
    setExtensionImagen2(undefined);
    setIdAspiranteImagen2(undefined);
    setImagenB643(undefined);
    setExtensionImagen3(undefined);
    setIdAspiranteImagen3(undefined);

    setCausaSusti((prev) => ({
      ...prev,
      fechaBaja: null,
      observaciones: "",
    }));

    formRef.current.setFieldsValue({
      fechaCausa: null,
      observaciones: "",
    });
  };

  const actualizarCausasSusti = ({
    fechaBaja,
    fechaBajaString,
    observaciones,
  }) => {
    const fechaBajaVal = vistaActual == ACCION_CAPTURA ? "" : fechaBajaString;
    const observacionesVal = vistaActual == ACCION_CAPTURA ? "" : observaciones;
    const fechaSustIncapacidadVal =
      vistaActual == ACCION_CAPTURA
        ? fechaBajaString
        : convertirDateAString(fechaBaja);

    setCausaSusti((prev) => ({
      ...prev,
      fechaBaja: fechaBajaVal,
      observaciones: observacionesVal,
      fechaSustIncapacidad: fechaSustIncapacidadVal,
    }));

    formRef.current.setFieldsValue({
      fechaCausa: obtenerMomentFecha(fechaBajaVal),
      observaciones: observacionesVal,
    });
  };

  const obtenerSustitucion = () => {
    setBlock(true);
    const request = {
      idAspirante: sustituidoSeleccionado?.idAspirante,
      idProceso: proceso.idProcesoElectoral,
      idDetalleProceso: proceso.idDetalleProceso,
      idParticipacion: idParticipacion,
      tipoFlujo: vistaActual,
      idSustitucion: idSustitucionSeleccionada,
    };
    apiClientPost("obtenerSustitutosSustTermino", request)
      .then(({ code, data }) => {
        if (code == CODE_SUCCESS) {
          setBlock(false);
          setSustitucionSE(data.sustitucionSE);
          setSustitucionCAE(data.sustitucionCAE);
          setSeIncapacitado(data.seIncapacitado);
          setSeTemporal(data.seTemporal);
          setCaeIncapacitado(data.caeIncapacitado);
          setCaeTemporal(data.caeTemporal);

          if (data.sustitucionSE != null) {
            actualizarCausasSusti(data.sustitucionSE);
          } else if (data.sustitucionCAE != null) {
            actualizarCausasSusti(data.sustitucionCAE);
          }
          if (vistaActual === ACCION_CAPTURA) {
            if (
              data?.sustitucionSE != null &&
              data?.caeTemporal == null &&
              data?.seTemporal != null &&
              validarOrigenSustituto(data?.sustitucionSE)
            ) {
              openNotificationSustIncompleta();
            }
          }
        } else {
          setBlock(false);
          mostrarModal("Error al obtener los sustitutos.", 2);
        }
      })
      .catch((error) => {
        mostrarModal("Error al obtener los sustitutos.", 2);
      });
  };

  const validarOrigenSustituto = (sustitucion) => {
    if (vistaActual === ACCION_CAPTURA) {
      if (
        sustitucion?.idPuestoSustituto === ID_PUESTO_RECONTRATACION_SE ||
        sustitucion?.declinoCargo === DECLINO_CARGO ||
        sustitucion?.idPuestoSustituto === ID_PUESTO_LISTA_RESERVA ||
        sustitucion?.idPuestoSustituto === ID_PUESTO_RESCISION_SE ||
        sustitucion?.idPuestoSustituto === ID_PUESTO_RESCISION_CAE ||
        sustitucion?.idPuestoSustituto === ID_PUESTO_PROMOCION
      ) {
        return false;
      }
    } else {
      if (
        sustitucion?.idPuesto === ID_PUESTO_RECONTRATACION_SE ||
        sustitucion?.declinoCargo === DECLINO_CARGO ||
        sustitucion?.idPuesto === ID_PUESTO_LISTA_RESERVA ||
        sustitucion?.idPuesto === ID_PUESTO_RESCISION_SE ||
        sustitucion?.idPuesto === ID_PUESTO_RESCISION_CAE ||
        sustitucion?.idPuesto === ID_PUESTO_PROMOCION
      ) {
        return false;
      }
    }

    return true;
  };

  const handleGuardar = () => {
    setBlock(true);

    const request = {
      sustitucionSE: sustitucionSE,
      sustitucionCAE: sustitucionCAE,

      seIncapacitado: seIncapacitado,
      seTemporal: seTemporal,

      caeIncapacitado: caeIncapacitado,
      caeTemporal: caeTemporal,

      fechaBaja: causaSusti?.fechaBaja,
      observaciones: causaSusti?.observaciones,
      usuario: user.username,

      imagenB641: imagenB641,
      extensionImagen1: extensionImagen1,
      idAspiranteImagen1: sustituidoSeleccionado?.idAspirante,

      imagenB642: imagenB642,
      extensionImagen2: extensionImagen2,
      idAspiranteImagen2: idAspiranteImagen2,

      imagenB643: imagenB643,
      extensionImagen3: extensionImagen3,
      idAspiranteImagen3: idAspiranteImagen3,

      tipoFlujo: vistaActual,
    };

    apiClientPost("guardarSustitucionTerminoContrato", request)
      .then((data) => {
        if (data.code == CODE_SUCCESS && data.data) {
          setBlock(false);
          mostrarModal(
            vistaActual === ACCION_CAPTURA
              ? MSJ_EXITO_GUARDAR
              : MSJ_EXITO_MODIFICAR,
            1
          );
        } else {
          setBlock(false);
          mostrarModal(
            vistaActual === ACCION_CAPTURA
              ? MSJ_ERROR_GUARDAR
              : MSJ_ERROR_MODIFICAR,
            2
          );
        }
      })
      .catch((error) => {
        mostrarModal(
          vistaActual === ACCION_CAPTURA
            ? MSJ_ERROR_GUARDAR
            : MSJ_ERROR_MODIFICAR,
          2
        );
      });
  };

  const openNotificationSustIncompleta = () => {
    api.open({
      message: "La sustitución está incompleta.",
      description: "Regresa a Sustitución por incapacidad para completarla.",
      type: "error",
    });
  };

  const mostrarModal = (msg, tipoVentanaModal) => {
    setMsjModal(msg);
    setOpenModal(true);
    setTipoModal(tipoVentanaModal);
  };

  const cerrarModal = () => {
    setMsjModal("");
    setOpenModal(false);
    if (tipoModal == 1) navigate(0);
  };

  const validaIsPendiente = (option) => {
    if (option.value.includes(",")) {
      setIdSustitucionSeleccionada(option.value.split(",")[0]);
    } else {
      setIdSustitucionSeleccionada(0);
    }
  };

  const convertirDateAString = (date) => {
    if (date) {
      const fecha = new Date(date);
      fecha.setDate(fecha.getDate() - 1);
      return `${moment(fecha).format("DD")}/${
        fecha.getMonth() + 1
      }/${fecha.getFullYear()}`;
    }
  };

  const obtenerMomentFecha = (fecha) => {
    if (!fecha) return "";
    return moment(fecha, "DD/MM/YYYY");
  };

  return (
    <AuthenticatedComponent>
      {contextHolder}
      <Template>
        <Form form={form} ref={formRef} onFinish={handleGuardar}>
          <Loader blocking={block} />
          <CMenuAcciones
            state={vistaActual}
            setState={setVistaActual}
            menuAcciones={menuAcciones}
          />
          <Layout id="content-bitacora">
            <Miga />
            <HeaderModulo />
            <Requerido />
            <BusquedaSeCae
              moduloSust={SUST_TERM_CONTRATO}
              tipoFlujo={vistaActual}
              setIsPendiente={validaIsPendiente}
            />
            {sustituidoSeleccionado && (
              <>
                <BarInfo text={FORMATOS_PERMITIDOS_FOTOGRAFIA} />

                <HeaderSeccion
                  text={`Información del ${
                    sustituidoSeleccionado.nombreCargo ?? "ciudadano"
                  }  ${vistaActual == ACCION_CAPTURA ? "a sustituir" : ""}`}
                />

                <Row justify="space-around" align="middle">
                  <Col xs={0} md={0} xl={2} />
                  <Col xs={24} md={6} xl={4}>
                    {isLoadingSelectFolioNombre ? (
                      <Skeleton.Image />
                    ) : (
                      <LoadFoto
                        key={sustituidoSeleccionado.idAspirante}
                        tipoFlujo={vistaActual}
                        urlFotoAspirante={sustituidoSeleccionado.urlFoto}
                        onChangeFoto={({ imagenB64, extensionArchivo }) => {
                          setImagenB641(imagenB64);
                          setExtensionImagen1(extensionArchivo);
                        }}
                      />
                    )}

                    <br />
                  </Col>
                  <Col xs={24} md={18} xl={18}>
                    <Row>
                      {isLoadingSelectFolioNombre ? (
                        <Skeleton
                          paragraph={{
                            rows: 2,
                          }}
                        />
                      ) : (
                        <VInfoAspirante
                          folio={sustituidoSeleccionado.folio}
                          apellidoPaterno={
                            sustituidoSeleccionado.apellidoPaterno
                          }
                          apellidoMaterno={
                            sustituidoSeleccionado.apellidoMaterno
                          }
                          nombre={sustituidoSeleccionado.nombre}
                          nombreCargo={sustituidoSeleccionado.nombreCargo}
                          claveElectorFuar={
                            sustituidoSeleccionado.claveElectorFuar
                          }
                          isMostrarZORE={true}
                          numeroZonaResponsabilidad={
                            sustituidoSeleccionado.numeroZonaResponsabilidad
                          }
                          isMostrarARE={true}
                          numeroAreaResponsabilidad={
                            sustituidoSeleccionado.numeroAreaResponsabilidad
                          }
                        />
                      )}
                    </Row>
                  </Col>
                </Row>
              </>
            )}
          </Layout>
          {sustituidoSeleccionado && (
            <>
              <CausaSustitucion
                moduloSust={SUST_TERM_CONTRATO}
                tipoFlujo={vistaActual}
                causaSusti={causaSusti}
                setCausaSusti={setCausaSusti}
                formRef={formRef}
              />

              {sustitucionSE != null && seIncapacitado != null && (
                <Sustitutos
                  sustitucion={sustitucionSE}
                  sustituto={seIncapacitado}
                  vistaActual={vistaActual}
                  setImagenB64={setImagenB642}
                  setExtensionImagen={setExtensionImagen2}
                  setIdAspiranteImagen={setIdAspiranteImagen2}
                />
              )}

              {sustitucionCAE != null && caeTemporal != null && (
                <Sustitutos
                  sustitucion={sustitucionCAE}
                  sustituto={
                    sustituidoSeleccionado.idPuesto == 1
                      ? caeTemporal
                      : sustituidoSeleccionado.idPuesto == 2 &&
                        caeIncapacitado == null
                      ? caeTemporal
                      : sustituidoSeleccionado.idPuesto == 10
                      ? caeTemporal
                      : seTemporal != null
                      ? seTemporal
                      : caeIncapacitado
                  }
                  vistaActual={vistaActual}
                  setImagenB64={setImagenB643}
                  setExtensionImagen={setExtensionImagen3}
                  setIdAspiranteImagen={setIdAspiranteImagen3}
                />
              )}

              {vistaActual != ACCION_CONSULTA &&
                !(
                  seIncapacitado != null &&
                  validarOrigenSustituto(
                    vistaActual === ACCION_CAPTURA ? sustitucionSE : seTemporal
                  ) &&
                  caeTemporal == null
                ) && (
                  <div
                    style={{
                      marginTop: "20px",
                      textAlign: "center",
                      marginBottom: "20px",
                    }}
                  >
                    <Button type="primary" htmlType="submit">
                      Guardar
                    </Button>
                  </div>
                )}
            </>
          )}
        </Form>

        <VCustomModal
          title={null}
          mensaje={msjModal}
          footer={null}
          open={openModal}
          tipoModal={tipoModal}
          cerrarModal={cerrarModal}
        />
      </Template>
    </AuthenticatedComponent>
  );
}

export default SustTerminoContrato;
