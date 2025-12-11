import { useState, useEffect, useRef } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Layout, Row, Col, Form, Button, notification } from "antd";
import moment from "moment";
import AuthenticatedComponent from "../AuthenticatedComponent";
import Template from "../interfaz/Template";
import { Loader } from "../interfaz/Loader.jsx";
import { CMenuAcciones } from "../interfaz/navbar/CMenuAcciones.jsx";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
import Miga from "../commonComponents/Miga";
import HeaderModulo from "../commonComponents/HeaderModulo";
import {
  Requerido,
  HeaderSeccion,
  BarInfo,
  InfoData,
} from "../commonComponents/AccessoriesComponents";
import BusquedaSeCae from "../commonComponents/busquedaSeCae/BusquedaSeCae.jsx";
import LoadFoto from "../commonComponents/LoadFoto";
import VInfoAspirante from "../commonComponents/VInfoAspirante.jsx";
import CausaSustitucion from "../commonComponents/causasSustitucion/CausaSustitucion";
import ExpedienteDesempenio from "../commonComponents/bitacoraDesempenio/ExpedienteDesempenio.jsx";
import EvaluacionDesempenio from "../commonComponents/bitacoraDesempenio/EvaluacionDesempenio.jsx";
import BusquedaSustitutoSupervisor from "../commonComponents/busquedaSustitutos/BusquedaSustitutoSupervisor";
import BusquedaSustitutoCapacitador from "../commonComponents/busquedaSustitutos/BusquedaSustitutoCapacitador";
import { apiClientPost } from "../../utils/apiClient";
import { clearUser } from "../../redux/selectByFolioNombre/selectByFolioNombreReducer.js";
import {
  guardarSustitucionSEyCAE,
  obtenerInformacionSustitucion,
  obtenerInfoSustitucion,
  validaBitacoraDesempenio,
  validaCausaSusitutcion,
  validarSustitutoUno,
  validarSustitutoDos,
  validarFechasSustitutos,
} from "../../utils/sustSEyCAE/funciones.js";
import {
  ACCION_CAPTURA,
  ACCION_CONSULTA,
  ACCION_MODIFICA,
  SUST_SEyCAE,
  CODE_SUCCESS,
  ID_PUESTO_SE,
} from "../../utils/constantes";
import * as S from "../../utils/sustSEyCAE/etiquetas.js";
const dateFormat = "DD/MM/YYYY";

function SustSEyCAE() {
  let formRef = useRef();
  const dispatch = useDispatch();
  const funcionarioSECAERedux = useSelector(
    (store) => store.selectFolioNombre.sustituidoSeleccionado
  );
  const isLoadingSelectFolioNombre = useSelector(
    (store) => store.selectFolioNombre.isLoadingSelectFolioNombre
  );
  const [api, contextHolder] = notification.useNotification();
  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu);
  const menuAcciones = useSelector((store) => store.menu.menuAcciones);
  const [loading, setLoading] = useState(false);
  const [flujoActual, setFlujoActual] = useState(1);

  const [funcionarioAsp, setFuncionarioAsp] = useState(null);
  const [fotoSECAE, setFotoSECAE] = useState("");

  const [isSustitucionPendiente, setIsSustitucionPendiente] = useState(false);
  const [sustitucionPrevia, setSustitucionPrevia] = useState();

  const [causaSustitucion, setCausaSustitucion] = useState("");
  const [expedienteDesempenio, setExpedienteDesempenio] = useState();
  const [evaluacionDesempenio, setEvaluacionDesempenio] = useState();
  const [expediente, setExpediente] = useState();
  const [evaluacion, setEvaluacion] = useState();
  const [mostrarBitacora, setMostrarBitacora] = useState(false);
  const [idBitacoraDesempenio, setIdBitacoraDesempenio] = useState();
  const [tipoFlujoBitacora, setTipoFlujoBitacora] = useState();

  const [sustitutoSupervisor, setSustitutoSupervisor] = useState();
  const [dateSustitutoSupervisor, setDateSustitutoSupervisor] = useState();
  const [correoSustitutoSupervisor, setCorreoSustitutoSupervisor] = useState();
  const [fotoAspiranteSustSE, setFotoAspiranteSustSE] = useState("");

  const [sustitutoCapacitador, setSustitutoCapacitador] = useState();
  const [dateSustitutoCapacitador, setDateSustitutoCapacitador] = useState();
  const [correoSustitutoCapacitador, setCorreoSustitutoCapacitador] =
    useState();
  const [fotoAspiranteSustCAE, setFotoAspiranteSustCAE] = useState("");

  const [infoSustitutoSE, setInfosustitutoSE] = useState();
  const [infoSustitucionSE, setInfosustitucionSE] = useState();
  const [infoSustitutoCAE, setInfosustitutoCAE] = useState();
  const [infoSustitucionCAE, setInfosustitucionCAE] = useState();
  const [idSustitucionConMod, setIdSustitucionConMod] = useState(0);
  const [sinSustituto, setSinSustituto] = useState(false);
  const [msjSinSustituto, setMsjSinSustituto] = useState("");
  const [causa, setCausa] = useState("");

  const [openModal, setOpenModal] = useState(false);
  const [msjModal, setMsjModal] = useState("");
  const [tipoModal, setTipoModal] = useState("");

  let color_disabled_radio_css = `
                   .ant-radio-checked .ant-radio-inner {
                        border-color: #ED92C8 !important;
                        background-color: #ED92C8 !important;
                    }
                    .ant-radio-checked .ant-radio-inner:after{
                        background-color: #ffffff !important;
                    }`;

  useEffect(() => {
    if (!funcionarioSECAERedux && !isLoadingSelectFolioNombre) {
      formRef.current.setFieldsValue({ idSustituido: null });
      resetConstPrincipales(0);
    }
    if (isLoadingSelectFolioNombre !== true && funcionarioSECAERedux !== null) {
      resetConstPrincipales(1);
      let request = {
        idDetalleProceso: funcionarioSECAERedux.idDetalleProceso,
        idParticipacion: funcionarioSECAERedux.idParticipacion,
        idAspirante: funcionarioSECAERedux.idAspirante,
      };
      apiClientPost("obtenerAspiranteBitacora", request)
        .then((data) => {
          let code = data.code;
          if (code === CODE_SUCCESS) {
            let funcionario = data.data;
            setTipoFlujoBitacora(
              funcionario.idBitacoraDesempenio && flujoActual === ACCION_CAPTURA
                ? ACCION_MODIFICA
                : !funcionario.idBitacoraDesempenio &&
                  flujoActual === ACCION_MODIFICA
                ? ACCION_CAPTURA
                : flujoActual
            );
            setFuncionarioAsp(funcionario);
            setIdBitacoraDesempenio(funcionario.idBitacoraDesempenio);
            if (flujoActual !== ACCION_CAPTURA) obtenerInfoConsultaModifica();
          } else {
            mostrarModal(S.MSJ_ERROR_INFO_ASPIRANTE, 2);
            setTipoFlujoBitacora(ACCION_MODIFICA);
            if (flujoActual !== ACCION_CAPTURA) obtenerInfoConsultaModifica();
          }
        })
        .catch((error) => {
          console.log(error);
          mostrarModal(S.MSJ_ERROR_INFO_ASPIRANTE, 2);
        });
    } else {
      if (flujoActual && isLoadingSelectFolioNombre === true) {
        resetConstPrincipales(0);
      }
    }
  }, [funcionarioSECAERedux]);

  const obtenerInfoConsultaModifica = async () => {
    setMostrarBitacora(false);

    let request = {
      idProceso: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      idSustituido: funcionarioSECAERedux.idAspirante,
      idSustitucion: idSustitucionConMod,
    };
    let sustitucion = await obtenerInfoSustitucion(request);
    if (sustitucion !== "") {
      let request = {
        idProcesoElectoral: menu.proceso.idProcesoElectoral,
        idDetalleProceso: funcionarioSECAERedux.idDetalleProceso,
        idParticipacion: funcionarioSECAERedux.idParticipacion,
        idAspirante: funcionarioSECAERedux.idAspirante,
        tipoCausaVacante: sustitucion.tipoCausaVacante,
        idSustitucion: idSustitucionConMod,
      };
      let infoSustitucion = await obtenerInformacionSustitucion(request);
      if (infoSustitucion !== "") {
        let sustitucionSE = infoSustitucion.dtoSustitucionSE;
        let sustitucionCAE = infoSustitucion.dtoSustitucionCAE;
        let sustitutoSE = infoSustitucion.datosSustitutoSE;
        let sustitutoCAE = infoSustitucion.datosSustitutoCAE;

        if (sustitutoCAE) {
          let dataSustitucion = sustitucionCAE.dtoSustituciones;
          setInfosustitucionCAE(dataSustitucion);
          setInfosustitutoCAE(sustitutoCAE);
          setDateSustitutoCapacitador(dataSustitucion.strFechaAlta);

          formRef.current.setFieldsValue({
            [`fechaSustCapacitador-${sustitutoCAE.folioAspirante}`]: moment(
              dataSustitucion.strFechaAlta,
              dateFormat
            ),
          });
          formRef.current.setFieldsValue({
            radios: dataSustitucion.tipoCausaVacante,
          });
          formRef.current.setFieldsValue({
            [`select${dataSustitucion.tipoCausaVacante}`]:
              dataSustitucion.idCausaVacante.toString(),
          });
          formRef.current.setFieldsValue({
            observaciones: dataSustitucion.observaciones,
          });
          formRef.current.setFieldsValue({
            fechaCausa: moment(dataSustitucion.strFechaBaja, dateFormat),
          });

          if (
            dataSustitucion.idAspiranteSutituido ===
            sustitucion.idAspiranteSutituido
          ) {
            let objCausaSut = {
              fechaBaja: dataSustitucion.strFechaBaja,
              observaciones: dataSustitucion.observaciones,
              idCausaVacante: dataSustitucion.idCausaVacante,
              tipoCausaVacante: dataSustitucion.tipoCausaVacante,
            };
            setCausaSustitucion(objCausaSut);
            setCausa(objCausaSut);
            if (flujoActual === ACCION_CONSULTA) {
              objCausaSut.descripcionCausa =
                sustitucionCAE.descripcionCausaVacante;
            }
            onChangeCausaSutitucion(objCausaSut);
          }
        }

        if (sustitutoSE) {
          let dataSustitucion = sustitucionSE.dtoSustituciones;
          setInfosustitucionSE(dataSustitucion);
          setInfosustitutoSE(sustitutoSE);
          setDateSustitutoSupervisor(dataSustitucion.strFechaAlta);

          formRef.current.setFieldsValue({
            radios: null,
            select1: null,
            select2: null,
            select3: null,
            observaciones: null,
          });
          formRef.current.setFieldsValue({
            fechaSustSupervisor: moment(
              dataSustitucion.strFechaAlta,
              dateFormat
            ),
          });
          formRef.current.setFieldsValue({
            radios: dataSustitucion.tipoCausaVacante,
          });
          formRef.current.setFieldsValue({
            [`select${dataSustitucion.tipoCausaVacante}`]:
              dataSustitucion.idCausaVacante.toString(),
          });
          formRef.current.setFieldsValue({
            observaciones: dataSustitucion.observaciones,
          });
          formRef.current.setFieldsValue({
            fechaCausa: moment(dataSustitucion.strFechaBaja, dateFormat),
          });

          if (
            dataSustitucion.idAspiranteSutituido ===
            sustitucion.idAspiranteSutituido
          ) {
            let objCausaSut = {
              fechaBaja: dataSustitucion.strFechaBaja,
              observaciones: dataSustitucion.observaciones,
              idCausaVacante: dataSustitucion.idCausaVacante,
              tipoCausaVacante: dataSustitucion.tipoCausaVacante,
            };
            setCausaSustitucion(objCausaSut);
            setCausa(objCausaSut);

            if (flujoActual == ACCION_CONSULTA) {
              objCausaSut.descripcionCausa =
                sustitucionSE.descripcionCausaVacante;
            }

            onChangeCausaSutitucion(objCausaSut);
          }
        }

        if (!sustitutoCAE && !sustitutoSE) {
          let dataSustitucion;
          if (sustitucionCAE) {
            dataSustitucion = sustitucionCAE.dtoSustituciones;
            setInfosustitucionCAE(dataSustitucion);
          } else if (sustitucionSE) {
            dataSustitucion = sustitucionSE.dtoSustituciones;
            setInfosustitucionSE(dataSustitucion);
          }

          formRef.current.setFieldsValue({
            fechaSustSupervisor: moment(
              dataSustitucion.strFechaAlta,
              dateFormat
            ),
          });
          formRef.current.setFieldsValue({
            radios: dataSustitucion.tipoCausaVacante,
          });
          formRef.current.setFieldsValue({
            [`select${dataSustitucion.tipoCausaVacante}`]:
              dataSustitucion.idCausaVacante.toString(),
          });
          formRef.current.setFieldsValue({
            observaciones: dataSustitucion.observaciones,
          });
          formRef.current.setFieldsValue({
            fechaCausa: moment(dataSustitucion.strFechaBaja, dateFormat),
          });

          if (
            dataSustitucion.idAspiranteSutituido ===
            sustitucion.idAspiranteSutituido
          ) {
            let objCausaSut = {
              fechaBaja: dataSustitucion.strFechaBaja,
              observaciones: dataSustitucion.observaciones,
              idCausaVacante: dataSustitucion.idCausaVacante,
              tipoCausaVacante: dataSustitucion.tipoCausaVacante,
            };
            if (flujoActual == ACCION_CONSULTA) {
              objCausaSut.descripcionCausa =
                sustitucionSE?.descripcionCausaVacante ??
                sustitucionCAE?.descripcionCausaVacante;
            }
            setCausaSustitucion(objCausaSut);
            setCausa(objCausaSut);
            onChangeCausaSutitucion(objCausaSut);
          }
        }

        if (sustitucionCAE && !sustitutoCAE) {
          setMsjSinSustituto("Sin sustituto/a de la persona capacitadora");
          setSinSustituto(true);
        }
        if (sustitucionSE && !sustitutoSE) {
          setMsjSinSustituto("Sin sustituto/a de la persona supervisora");
          setSinSustituto(true);
        }

        setLoading(false);
      } else {
        setLoading(false);
        mostrarModal(S.MSJ_ERROR_INFO_ASPIRANTE, 2);
      }
    } else {
      setLoading(false);
      mostrarModal(S.MSJ_ERROR_OBTENER_SUST, 2);
    }
  };

  const selecTipoFujo = (tipoFujo) => {
    setFlujoActual(tipoFujo);
    if (!formRef) {
      formRef.current.setFieldsValue({ idSustituido: null });
      resetConstPrincipales(0);
      dispatch(clearUser());
    }
  };

  const resetConstPrincipales = (index) => {
    setSinSustituto(false);
    setMsjSinSustituto("");

    formRef.current.setFieldsValue({
      radios: null,
      select1: null,
      select2: null,
      select3: null,
      fechaCausa: null,
      observaciones: null,
      [`fechaSustCapacitador-${sustitutoSupervisor?.folioPrincipal}`]: null,
      [`fechaSustCapacitador-${sustitutoCapacitador?.folioPrincipal}`]: null,
    });
    if (!isSustitucionPendiente || index === 0) {
      setCausaSustitucion("");
      setCausa("");
      setMostrarBitacora(false);
    }

    setExpedienteDesempenio(undefined);
    setEvaluacionDesempenio(undefined);
    setExpediente(undefined);
    setEvaluacion(undefined);

    setSustitutoSupervisor(undefined);
    setDateSustitutoSupervisor(undefined);
    setCorreoSustitutoSupervisor();
    setFotoAspiranteSustSE();

    setSustitutoCapacitador(undefined);
    setDateSustitutoCapacitador();
    setCorreoSustitutoCapacitador(undefined);
    setFotoAspiranteSustCAE();

    setFuncionarioAsp(null);
    setIdBitacoraDesempenio(null);
    setFotoSECAE();

    setInfosustitucionSE();
    setInfosustitutoSE();

    setInfosustitucionCAE();
    setInfosustitutoCAE();
  };

  const validarSustitucionPendiente = async (option) => {
    let idSustitucionPend = 0;
    let idSustituidoPend = 0;
    setIdSustitucionConMod(0);
    if (option.value.includes(",")) {
      let idSustitucion = option.value.split(",")[0];
      setIdSustitucionConMod(idSustitucion);
      idSustitucionPend = idSustitucion;
      idSustituidoPend = option.value.split(",")[1];
    }

    setIsSustitucionPendiente(false);
    setSustitucionPrevia();

    if (flujoActual == ACCION_CAPTURA) {
      setMostrarBitacora(false);
      setSustitucionPrevia();

      if (option && option.pendiente) {
        setLoading(true);
        let request = {
          idProceso: menu.proceso.idProcesoElectoral,
          idDetalleProceso: menu.proceso.idDetalleProceso,
          idParticipacion: menu.idParticipacion,
          idSustituido: idSustituidoPend,
          idSustitucion: idSustitucionPend,
        };
        let sustitucion = await obtenerInfoSustitucion(request);
        if (sustitucion !== "") {
          let objCausaSut = {
            fechaBaja: sustitucion.fechaBajaString,
            observaciones: sustitucion.observaciones,
            idCausaVacante: sustitucion.idCausaVacante,
            tipoCausaVacante: sustitucion.tipoCausaVacante,
          };

          setSustitucionPrevia(sustitucion);
          setCausaSustitucion(objCausaSut);
          objCausaSut.descripcionCausa = sustitucion.causaString;
          onChangeCausaSutitucion(objCausaSut);
          setIsSustitucionPendiente(true);
        } else {
          mostrarModal(S.MSJ_ERROR_SUST_PREVIA, 2);
        }
        setLoading(false);
      } else {
        setCausaSustitucion("");
        setSustitucionPrevia();
        setIsSustitucionPendiente(false);
      }
    }
  };

  const isPuestoSE = (idPuesto) => {
    return idPuesto == S.ID_PUESTO_SE || idPuesto == S.ID_PUESTO_SE_TEMP;
  };

  const isPuestoCAE = (idPuesto) => {
    return idPuesto == S.ID_PUESTO_CAE || idPuesto == S.ID_PUESTO_CAE_TEMP;
  };

  const handleCargarImagen = (file) => {
    setFotoSECAE(file);
  };

  const onChangeSustitutoSupervisor = (dtoAspirante, correoElectronico) => {
    if (dtoAspirante) {
      formRef.current.setFieldsValue({
        [`fechaSustCapacitador-${dtoAspirante?.folioPrincipal}`]: null,
      });
    }
    setDateSustitutoSupervisor();
    setFotoAspiranteSustSE();
    setCorreoSustitutoSupervisor();

    setSustitutoSupervisor(dtoAspirante ?? null);

    if (dtoAspirante?.idPuesto == S.ID_PUESTO_LR) {
      formRef.current.setFieldsValue({
        correoElectronicoSustSupervisor: correoElectronico,
      });
      setCorreoSustitutoSupervisor(correoElectronico);
    }
  };

  const onChangeSustitutoCapacitador = (dtoAspirante, correoElectronico) => {
    if (dtoAspirante?.folioPrincipal ?? null) {
      formRef.current.setFieldsValue({
        [`fechaSustCapacitador-${dtoAspirante?.folioPrincipal}`]: null,
      });
    }
    setDateSustitutoCapacitador();
    setCorreoSustitutoCapacitador();
    setFotoAspiranteSustCAE();

    setSustitutoCapacitador(dtoAspirante ?? null);

    if (dtoAspirante?.idPuesto == S.ID_PUESTO_LR) {
      formRef.current.setFieldsValue({
        correoElectronicoSustCapacitador: correoElectronico,
      });
      setCorreoSustitutoCapacitador(correoElectronico);
    }
  };

  const mostrarModal = (msg, tipoVentanaModal) => {
    setMsjModal(msg);
    setOpenModal(true);
    setTipoModal(tipoVentanaModal);
  };

  const cerrarModal = () => {
    setMsjModal("");
    setOpenModal(false);
    if (tipoModal == 1) location.reload();
  };
  const mostrarAlerta = (titulo, mensaje) => {
    api.warning({
      message: `Alerta ${titulo}`,
      description: mensaje,
      placement: "top",
    });
  };

  const onChangeCausaSutitucion = (objCausa) => {
    if (objCausa && objCausa.tipoCausaVacante && objCausa.idCausaVacante) {
      if (
        objCausa.tipoCausaVacante == 3 &&
        (objCausa.idCausaVacante == 1 || objCausa.idCausaVacante == 2)
      ) {
        setMostrarBitacora(false);
        if (tipoFlujoBitacora === 1 && !isSustitucionPendiente) {
          setEvaluacionDesempenio(undefined);
          setExpedienteDesempenio(undefined);
        }
      } else {
        setMostrarBitacora(true);
      }
    } else {
      setMostrarBitacora(false);
    }
    setCausaSustitucion(objCausa);
  };

  const onGuardarSustitucion = async () => {
    setLoading(true);

    if (validaInfoSustitucion()) {
      let request = {};
      request = mapearDatosPrincipales(request);
      request = mapearSustituido(request);
      request = mapearCausaSustitucion(request);
      request = mapearExpediente(request);
      request = mapearSustitutoSE(request);
      request = mapearSustitutoCAE(request);
      if (flujoActual == ACCION_MODIFICA) {
        request = mapearDatosModifica(request);
      }

      const formData = new FormData();
      if (request?.expedienteDesempenio?.file) {
        let fileExp = request.expedienteDesempenio.file;
        formData.append(S.ATRIB_FIL_EXP, fileExp);
        delete request.expedienteDesempenio.file;
      }
      formData.append(
        S.ATRIB_REQ_SUST,
        new Blob([JSON.stringify(request)], { type: "application/json" })
      );

      let guardar = await guardarSustitucionSEyCAE(formData);
      if (guardar?.code == CODE_SUCCESS) {
        mostrarModal(
          flujoActual === ACCION_MODIFICA
            ? S.MSJ_EXITO_MODIFICAR
            : S.MSJ_EXITO_GUARDAR,
          1
        );
        setLoading(false);
      } else {
        console.log(guardar);
        mostrarModal(
          flujoActual === ACCION_MODIFICA
            ? S.MSJ_ERROR_MODIFICAR_SUSTITUCION
            : S.MSJ_ERROR_GUARDAR_SUSTITUCION,
          2
        );
        setLoading(false);
      }
    } else {
      setLoading(false);
    }
  };

  const mapearDatosPrincipales = (request) => {
    request.idProcesoElectoral = menu.proceso.idProcesoElectoral;
    request.idDetalleProceso = menu.proceso.idDetalleProceso;
    request.idParticipacion = menu.idParticipacion;
    if (isSustitucionPendiente) {
      request.idSustitucion = sustitucionPrevia?.idSustitucion ?? null;
      request.idRelacionSustitucion =
        sustitucionPrevia?.idRelacionSustituciones ?? null;
      request.sustitucionPrevia = sustitucionPrevia ?? null;
    }
    request.esPendiente = isSustitucionPendiente ? 1 : 0;
    request.tipoAccion = flujoActual;
    request.usuario = user.username;
    return request;
  };
  const mapearSustituido = (request) => {
    request.idSustituido = funcionarioAsp?.idAspirante;
    request.imagenB64Sustituido = fotoSECAE?.imagenB64 ?? null;
    request.extensionImagenSustituido = fotoSECAE?.extensionArchivo ?? null;
    return request;
  };
  const mapearCausaSustitucion = (request) => {
    request.tipoCausaVacante = causaSustitucion?.tipoCausaVacante;
    request.idCausaVacante = Number(causaSustitucion?.idCausaVacante);
    request.fechaBajaSustituido = causaSustitucion?.fechaBaja;
    request.observacionesSustituido = causaSustitucion?.observaciones ?? null;
    return request;
  };
  const mapearExpediente = (request) => {
    let existeBitacoraDesemp = evaluacionDesempenio?.valoracionRiesgo ?? false;
    request.existeInfoBitacora = existeBitacoraDesemp ? 1 : 0;

    if (existeBitacoraDesemp) {
      request.procesarSoloBitacora =
        isSustitucionPendiente && !sustitutoSupervisor && !sustitutoCapacitador
          ? 1
          : 0;
      request.idBitacoraDesempenio = idBitacoraDesempenio;
      request.tipoAccionBitacora = tipoFlujoBitacora;
      request.origenBitacora = 2;
      request.evaluacionDesempenio = evaluacionDesempenio;
      request.expedienteDesempenio = expedienteDesempenio;
    }
    return request;
  };

  const mapearSustitutoSE = (request) => {
    if (flujoActual == ACCION_MODIFICA) {
      request.idSustitutoSupervisor =
        infoSustitucionSE?.idAspiranteSutituto ?? null;
    } else {
      request.idSustitutoSupervisor =
        sustitutoSupervisor?.id?.idAspirante ?? null;
    }
    request.dateSustitutoSupervisor = dateSustitutoSupervisor ?? null;
    request.imagenB64SustitutoSupervisor =
      fotoAspiranteSustSE?.imagenB64 ?? null;
    request.extensionImagenSustitutoSupervisor =
      fotoAspiranteSustSE?.extensionArchivo ?? null;
    request.correoSustitutoSupervisor = correoSustitutoSupervisor ?? null;
    return request;
  };
  const mapearSustitutoCAE = (request) => {
    if (flujoActual == ACCION_MODIFICA) {
      request.idSustitutoCapacitador =
        infoSustitucionCAE?.idAspiranteSutituto ?? null;
    } else {
      request.idSustitutoCapacitador =
        sustitutoCapacitador?.id?.idAspirante ?? null;
    }
    request.dateSustitutoCapacitador = dateSustitutoCapacitador ?? null;
    request.imagenB64SustitutoCapacitador =
      fotoAspiranteSustCAE?.imagenB64 ?? null;
    request.extensionImagenSustitutoCapacitador =
      fotoAspiranteSustCAE?.extensionArchivo ?? null;
    request.correoSustitutoCapacitador = correoSustitutoCapacitador ?? null;
    return request;
  };

  const mapearDatosModifica = (request) => {
    if (infoSustitucionSE && infoSustitucionSE.id) {
      let dtoSustitucionSE = {
        idProcesoElectoral: infoSustitucionSE?.idProcesoElectoral,
        idDetalleProceso: infoSustitucionSE?.id?.idDetalleProceso,
        idParticipacion: infoSustitucionSE?.id?.idParticipacion,
        idSustitucion: infoSustitucionSE?.id?.idSustitucion,
        idAspiranteSutituido: infoSustitucionSE?.idAspiranteSutituido,
        idRelacionSustituciones: infoSustitucionSE?.idRelacionSustituciones,
      };
      request.sustitucionSE = dtoSustitucionSE;
    }

    if (infoSustitucionCAE && infoSustitucionCAE.id) {
      let dtoSustitucionCAE = {
        idProcesoElectoral: infoSustitucionCAE.idProcesoElectoral,
        idDetalleProceso: infoSustitucionCAE.id.idDetalleProceso,
        idParticipacion: infoSustitucionCAE.id.idParticipacion,
        idSustitucion: infoSustitucionCAE.id.idSustitucion,
        idAspiranteSutituido: infoSustitucionCAE.idAspiranteSutituido,
        idRelacionSustituciones: infoSustitucionCAE.idRelacionSustituciones,
      };
      request.sustitucionCAE = dtoSustitucionCAE;
    }
    return request;
  };

  const validaInfoSustitucion = () => {
    let formErrors = [];
    if (
      flujoActual === ACCION_CAPTURA &&
      isSustitucionPendiente &&
      !(
        sustitutoCapacitador?.id?.idAspirante ||
        sustitutoSupervisor?.id?.idAspirante
      )
    ) {
      let result = validarCambiosVacante();
      if (!result) {
        return false;
      }
    }
    if (flujoActual === ACCION_MODIFICA) {
      let result = validarCambiosModifica();
      if (!result) {
        return false;
      }
    }

    formErrors = validaCausaSusitutcion(causaSustitucion);
    if (formErrors.length > 0) {
      formRef.current.setFields(formErrors);
      scrollToElement("sustSeccionCausa");
      return false;
    }

    limpiarMensajesBitacora();
    formErrors = validaBitacoraDesempenio(
      mostrarBitacora,
      expedienteDesempenio,
      evaluacionDesempenio
    );
    if (formErrors.length > 0) {
      formRef.current.setFields(formErrors);
      scrollToElement("divExpedienteDesemp");
      return false;
    }

    formErrors = validarSustitutoUno(
      sustitutoSupervisor,
      dateSustitutoSupervisor,
      causaSustitucion
    );
    if (formErrors.length > 0) {
      formRef.current.setFields(formErrors);
      scrollToElement("sustSeccionBusquedaSE");
      return false;
    }

    formErrors = validarSustitutoDos(
      sustitutoCapacitador,
      dateSustitutoCapacitador,
      causaSustitucion
    );
    if (formErrors.length > 0) {
      formRef.current.setFields(formErrors);
      scrollToElement("sustSeccionBusquedaCAE");
      return false;
    }

    formErrors = validarFechasSustitutos(
      sustitutoCapacitador,
      sustitutoSupervisor,
      dateSustitutoSupervisor,
      dateSustitutoCapacitador
    );
    if (formErrors.length > 0) {
      formRef.current.setFields(formErrors);
      scrollToElement("sustSeccionBusquedaCAE");
      return false;
    }
    if (flujoActual === ACCION_CAPTURA) {
      if (sustitutoSupervisor?.idPuesto === S.ID_PUESTO_LR) {
        const value = formRef.current
          .getFieldValue("correoElectronicoSustSupervisor")
          ?.trim();
        const errores = formRef.current.getFieldError(
          "correoElectronicoSustSupervisor"
        );
        if (!value) {
          formRef.current.setFields([
            {
              name: "correoElectronicoSustSupervisor",
              errors: [S.MSJ_INFO_REQ],
            },
          ]);
          scrollToElement("correoElectronicoSustSupervisor");
          return false;
        }
        if (errores.length > 0) {
          scrollToElement("correoElectronicoSustSupervisor");
          return false;
        }
      }
      if (sustitutoCapacitador?.idPuesto === S.ID_PUESTO_LR) {
        const value = formRef.current
          .getFieldValue("correoElectronicoSustCapacitador")
          ?.trim();
        const errores = formRef.current.getFieldError(
          "correoElectronicoSustCapacitador"
        );
        if (!value) {
          formRef.current.setFields([
            {
              name: "correoElectronicoSustCapacitador",
              errors: [S.MSJ_INFO_REQ],
            },
          ]);
          scrollToElement("correoElectronicoSustCapacitador");
          return false;
        }
        if (errores.length > 0) {
          scrollToElement("correoElectronicoSustCapacitador");
          return false;
        }
      }
    }
    return true;
  };

  const limpiarMensajesBitacora = () => {
    formRef.current.setFields([{ name: "selectFrecuenciaBit", errors: [] }]);
    formRef.current.setFields([{ name: "ChecksResponablesBit", errors: [] }]);
  };

  const validarCambiosVacante = () => {
    try {
      if (mostrarBitacora) {
        if (
          tipoFlujoBitacora === ACCION_CAPTURA &&
          !expedienteDesempenio &&
          !evaluacionDesempenio &&
          !fotoAspiranteSustCAE &&
          !fotoAspiranteSustSE &&
          !fotoSECAE
        ) {
          setLoading(false);
          mostrarAlerta(S.MSJ_TITULO_SIN_SUST_EVA, S.MSJ_SELECCIONA_SUST_EVA);
          return false;
        } else if (
          tipoFlujoBitacora === ACCION_MODIFICA &&
          JSON.stringify(expedienteDesempenio)?.toLowerCase() ===
            JSON.stringify(expediente)?.toLowerCase() &&
          JSON.stringify(evaluacionDesempenio)?.toLowerCase() ===
            JSON.stringify(evaluacion)?.toLowerCase() &&
          !fotoAspiranteSustCAE &&
          !fotoAspiranteSustSE &&
          !fotoSECAE
        ) {
          setLoading(false);
          mostrarAlerta(S.MSJ_TITULO_SIN_SUST, S.MSJ_SELECCIONA_SUST);
          return false;
        }
      } else {
        setLoading(false);
        mostrarAlerta(S.MSJ_TITULO_SIN_SUST, S.MSJ_SELECCIONA_SUST);
        return false;
      }
    } catch (error) {
      console.log(error);
    }
    return true;
  };

  const validarCambiosModifica = () => {
    try {
      if (
        (dateSustitutoSupervisor ?? null) ===
          (infoSustitucionSE?.strFechaAlta ?? null) &&
        (infoSustitucionCAE?.strFechaAlta ?? null) ===
          (dateSustitutoCapacitador ?? null) &&
        (causaSustitucion?.fechaBaja ?? null) === (causa?.fechaBaja ?? null) &&
        (causaSustitucion?.observaciones?.toLowerCase() ?? null) ===
          (causa?.observaciones?.toLowerCase() ?? null) &&
        (causaSustitucion?.idCausaVacante ?? null) ==
          (causa?.idCausaVacante ?? null) &&
        (causaSustitucion?.tipoCausaVacante ?? null) ==
          (causa?.tipoCausaVacante ?? null) &&
        !fotoAspiranteSustCAE &&
        !fotoAspiranteSustSE &&
        !fotoSECAE
      ) {
        if (mostrarBitacora) {
          if (
            tipoFlujoBitacora === ACCION_CAPTURA &&
            !expedienteDesempenio &&
            !evaluacionDesempenio
          ) {
            setLoading(false);
            mostrarAlerta(S.MSJ_TITULO_SIN_CAMBIOS, S.MSJ_SIN_CAMBIOS);
            return false;
          } else if (
            tipoFlujoBitacora === ACCION_MODIFICA &&
            JSON.stringify(expedienteDesempenio)?.toLowerCase() ===
              JSON.stringify(expediente)?.toLowerCase() &&
            JSON.stringify(evaluacionDesempenio)?.toLowerCase() ===
              JSON.stringify(evaluacion)?.toLowerCase()
          ) {
            setLoading(false);
            mostrarAlerta(S.MSJ_TITULO_SIN_CAMBIOS, S.MSJ_SIN_CAMBIOS);
            return false;
          }
        } else {
          setLoading(false);
          mostrarAlerta(S.MSJ_TITULO_SIN_CAMBIOS, S.MSJ_SIN_CAMBIOS);
          return false;
        }
      }
    } catch (error) {
      console.log(error);
    }
    return true;
  };

  const scrollToElement = (idElement) => {
    document.getElementById(idElement).scrollIntoView({ behavior: "smooth" });
  };

  const obtenerTitleHeaderSeccion = () => {
    if (flujoActual === ACCION_CAPTURA) {
      if (
        (!isSustitucionPendiente &&
          isPuestoSE(funcionarioSECAERedux?.idPuesto)) ||
        (isSustitucionPendiente &&
          isPuestoSE(sustitucionPrevia?.idPuestoSustituido))
      ) {
        return S.ETQ_INFO_SE;
      } else if (
        (!isSustitucionPendiente &&
          isPuestoCAE(funcionarioSECAERedux?.idPuesto)) ||
        (isSustitucionPendiente &&
          isPuestoCAE(sustitucionPrevia?.idPuestoSustituido))
      ) {
        return S.ETQ_INFO_CAE;
      }
    } else if (isPuestoSE(infoSustitucionSE?.idPuestoSustituido)) {
      return S.ETQ_INFO_CONS_SE;
    } else if (isPuestoCAE(infoSustitucionCAE?.idPuestoSustituido)) {
      return S.ETQ_INFO_CONS_CAE;
    }
    return "";
  };

  const definirTipoFlujo = () => {
    if (flujoActual === ACCION_CAPTURA && !isSustitucionPendiente) {
      return ACCION_CAPTURA;
    } else if (flujoActual === ACCION_CAPTURA && isSustitucionPendiente) {
      return ACCION_CONSULTA;
    } else if (flujoActual === ACCION_CONSULTA) {
      return ACCION_CONSULTA;
    } else if (flujoActual === ACCION_MODIFICA) {
      return ACCION_MODIFICA;
    }
  };

  const listarCausasNoMostrar = () => {
    let idCausas = [2];
    if (
      flujoActual === ACCION_CAPTURA &&
      !isSustitucionPendiente &&
      isPuestoSE(funcionarioSECAERedux?.idPuesto)
    ) {
      return idCausas;
    } else if (
      flujoActual === ACCION_CAPTURA &&
      isSustitucionPendiente &&
      isPuestoSE(sustitucionPrevia?.idPuestoSustituido)
    ) {
      return idCausas;
    } else if (
      flujoActual != ACCION_CAPTURA &&
      infoSustitucionSE &&
      infoSustitucionSE?.idCausaVacante != 2
    ) {
      return idCausas;
    } else {
      return [];
    }
  };

  const existeSustitutoSe = () => {
    if (funcionarioAsp && funcionarioSECAERedux) {
      if (
        flujoActual === ACCION_CAPTURA &&
        !isSustitucionPendiente &&
        isPuestoSE(funcionarioSECAERedux.idPuesto)
      ) {
        return true;
      } else if (
        flujoActual === ACCION_CAPTURA &&
        isSustitucionPendiente &&
        isPuestoSE(sustitucionPrevia?.idPuestoSustituido)
      ) {
        return true;
      } else if (
        flujoActual != ACCION_CAPTURA &&
        infoSustitucionSE &&
        infoSustitutoSE
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  };

  const existeSustitutoCae = () => {
    if (funcionarioAsp && funcionarioSECAERedux) {
      if (
        flujoActual === ACCION_CAPTURA &&
        isPuestoCAE(funcionarioSECAERedux?.idPuesto)
      ) {
        return true;
      } else if (
        flujoActual === ACCION_CAPTURA &&
        sustitutoSupervisor &&
        isPuestoCAE(sustitutoSupervisor.idPuesto)
      ) {
        return true;
      } else if (
        flujoActual === ACCION_CAPTURA &&
        sustitucionPrevia &&
        isPuestoCAE(sustitucionPrevia.idPuestoSustituido)
      ) {
        return true;
      } else if (
        flujoActual != ACCION_CAPTURA &&
        infoSustitucionCAE &&
        infoSustitutoCAE
      ) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  };

  return (
    <AuthenticatedComponent>
      {contextHolder}
      <Template>
        {flujoActual == ACCION_MODIFICA ? (
          <style>{color_disabled_radio_css}</style>
        ) : (
          ""
        )}
        <div id="divContentSustSEyCAE" style={{ marginTop: "0px" }}>
          <Loader blocking={loading} />
          <CMenuAcciones
            state={flujoActual}
            setState={selecTipoFujo}
            menuAcciones={menuAcciones}
          />
          <Form layout="vertical" ref={formRef} id="form-sust-secae">
            <Layout id="content-sust-secae">
              <Miga />
              <HeaderModulo />
              <Requerido />
              <BusquedaSeCae
                key={flujoActual}
                moduloSust={SUST_SEyCAE}
                tipoFlujo={flujoActual}
                setIsPendiente={validarSustitucionPendiente}
              />
              {funcionarioSECAERedux != null ? (
                <>
                  {flujoActual == ACCION_CONSULTA ? (
                    ""
                  ) : (
                    <BarInfo text={S.ETQ_FORMATOS_FOTO} />
                  )}
                  <HeaderSeccion text={obtenerTitleHeaderSeccion()} />
                  <Row style={{ margin: "10px" }} id="infoSustituido">
                    <Col
                      xs={24}
                      md={8}
                      xl={5}
                      style={{ justifyItems: "center", padding: "3px" }}
                    >
                      <LoadFoto
                        key={funcionarioSECAERedux.claveElectorFuar}
                        urlFotoAspirante={funcionarioSECAERedux?.urlFoto}
                        tipoFlujo={flujoActual}
                        onChangeFoto={handleCargarImagen}
                      />
                    </Col>
                    <Col xs={24} md={16} xl={19}>
                      <Row>
                        <VInfoAspirante
                          folio={funcionarioSECAERedux.folio}
                          apellidoPaterno={
                            funcionarioSECAERedux.apellidoPaterno
                          }
                          apellidoMaterno={
                            funcionarioSECAERedux.apellidoMaterno
                          }
                          nombre={funcionarioSECAERedux.nombre}
                          nombreCargo={funcionarioSECAERedux.nombreCargo}
                          claveElectorFuar={
                            funcionarioSECAERedux.claveElectorFuar
                          }
                          isMostrarZORE={
                            funcionarioSECAERedux.numeroZonaResponsabilidad !=
                              undefined &&
                            (isSustitucionPendiente ||
                              (!isSustitucionPendiente &&
                                flujoActual == ACCION_CAPTURA))
                          }
                          numeroZonaResponsabilidad={
                            funcionarioSECAERedux.numeroZonaResponsabilidad
                          }
                          isMostrarARE={
                            funcionarioSECAERedux.numeroAreaResponsabilidad !=
                              undefined &&
                            (isSustitucionPendiente ||
                              (!isSustitucionPendiente &&
                                flujoActual == ACCION_CAPTURA))
                          }
                          numeroAreaResponsabilidad={
                            funcionarioSECAERedux.numeroAreaResponsabilidad
                          }
                          cargoAnterior={
                            flujoActual === ACCION_CAPTURA &&
                            funcionarioSECAERedux?.idPuesto === ID_PUESTO_SE
                              ? funcionarioSECAERedux?.cargoAnterior
                              : null
                          }
                        ></VInfoAspirante>
                      </Row>
                      <Row>
                        {!isSustitucionPendiente &&
                        flujoActual == ACCION_CAPTURA ? (
                          <InfoData
                            info={S.ETQ_PERIODO_CONTRATACION}
                            data={`${
                              funcionarioSECAERedux?.fechaInicioContratacion ??
                              ""
                            }  - ${
                              funcionarioSECAERedux?.fechaFinContratacion ?? ""
                            } `}
                          />
                        ) : (
                          ""
                        )}
                      </Row>
                    </Col>
                  </Row>
                </>
              ) : (
                <></>
              )}
            </Layout>

            <div id="sustSeccionCausa" />
            {funcionarioSECAERedux !== null ? (
              <CausaSustitucion
                moduloSust={SUST_SEyCAE}
                tipoFlujo={definirTipoFlujo()}
                causaSusti={causaSustitucion}
                setCausaSusti={onChangeCausaSutitucion}
                formRef={formRef}
                idCausasNOMostrar={listarCausasNoMostrar()}
                key={funcionarioSECAERedux.idAspirante + 1}
              />
            ) : (
              <></>
            )}

            {funcionarioAsp && funcionarioSECAERedux && mostrarBitacora ? (
              <>
                <ExpedienteDesempenio
                  key={funcionarioAsp.idAspirante + 2}
                  formRef={formRef}
                  onChangeExpediente={setExpedienteDesempenio}
                  tipoFlujo={tipoFlujoBitacora}
                  funcionarioSECAE={funcionarioAsp}
                  expedienteOriginal={setExpediente}
                />
                <EvaluacionDesempenio
                  key={funcionarioAsp.idAspirante + 3}
                  formRef={formRef}
                  onChangeEvaluacion={setEvaluacionDesempenio}
                  tipoFlujo={tipoFlujoBitacora}
                  funcionarioSECAE={funcionarioAsp}
                  isSust={true}
                  evaluacionOriginal={setEvaluacion}
                />
              </>
            ) : (
              <></>
            )}

            <div id="sustSeccionBusquedaSE" />
            {existeSustitutoSe() ? (
              <BusquedaSustitutoSupervisor
                tipoFlujo={flujoActual}
                sustitutoSupervisorSeleccionado={onChangeSustitutoSupervisor}
                onChangeDate={setDateSustitutoSupervisor}
                onChangeFoto={setFotoAspiranteSustSE}
                infoSustitutoSe={infoSustitutoSE}
                infoSustitucionSe={infoSustitucionSE}
                onChangeCorreoSupervisor={setCorreoSustitutoSupervisor}
              />
            ) : (
              <></>
            )}

            <div id="sustSeccionBusquedaCAE" />
            {existeSustitutoCae() ? (
              <BusquedaSustitutoCapacitador
                tipoFlujo={flujoActual}
                sustitutoCapacitadorSeleccionado={onChangeSustitutoCapacitador}
                onChangeDate={setDateSustitutoCapacitador}
                onChangeCorreo={setCorreoSustitutoCapacitador}
                onChangeFoto={setFotoAspiranteSustCAE}
                infoSustitutoCae={infoSustitutoCAE}
                infoSustitucionCae={infoSustitucionCAE}
              />
            ) : (
              <></>
            )}

            {sinSustituto ? (
              <>
                <br />
                <Layout id="content-sin-sust">
                  <span className="span-header-seccion">{msjSinSustituto}</span>
                </Layout>
              </>
            ) : (
              ""
            )}

            <div
              className="divCenterUtil"
              style={{ marginTop: "35px", marginBottom: "35px" }}
            >
              {funcionarioSECAERedux &&
              (flujoActual === ACCION_CAPTURA ||
                flujoActual === ACCION_MODIFICA) ? (
                <Button
                  onClick={() => onGuardarSustitucion()}
                  className="button-save-bit"
                >
                  {S.ETQ_GUARDAR}
                </Button>
              ) : (
                ""
              )}
            </div>
          </Form>
        </div>

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

export default SustSEyCAE;
