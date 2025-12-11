import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import moment from "moment";
import dayjs from "dayjs";

import { Layout, Button, Col, Form, Row, Skeleton } from "antd";

import AuthenticatedComponent from "../AuthenticatedComponent";
import Template from "../interfaz/Template";
import { Loader } from "../interfaz/Loader";
import { CMenuAcciones } from "../interfaz/navbar/CMenuAcciones.jsx";
import Miga from "../commonComponents/Miga";
import HeaderModulo from "../commonComponents/HeaderModulo";
import {
  BarInfo,
  HeaderSeccion,
  Requerido,
  InfoData,
} from "../commonComponents/AccessoriesComponents";
import BusquedaSeCae from "../commonComponents/busquedaSeCae/BusquedaSeCae";
import BusquedaSustitutoCapacitador from "../../components/commonComponents/busquedaSustitutos/BusquedaSustitutoCapacitador";
import BusquedaSustitutoSupervisor from "../../components/commonComponents/busquedaSustitutos/BusquedaSustitutoSupervisor";
import CausaSustitucion from "../commonComponents/causasSustitucion/CausaSustitucion";
import LoadFoto from "../commonComponents/LoadFoto";
import VInfoAspirante from "../commonComponents/VInfoAspirante";
import VCustomModal from "../commonComponents/VCustomModal.jsx";

import { apiClientPost } from "../../utils/apiClient.js";
import {
  generarDatosSustitucion,
  generarDatosSustitucionPendiente,
} from "../../utils/sustRescision/funciones";

import {
  ACCION_CAPTURA,
  ACCION_CONSULTA,
  ACCION_MODIFICA,
  FLUJO_CAPTURA,
  FLUJO_CONSULTA,
  SUST_RESC_CONTRATO,
  CODE_SUCCESS,
  ID_PUESTO_SE,
} from "../../utils/constantes";
import {
  FORMATOS_PERMITIDOS_FOTOGRAFIA,
  MSJ_EXITO_GUARDAR,
  MSJ_EXITO_MODIFICAR,
  MSJ_ERROR_GUARDAR,
  MSJ_ERROR_MODIFICAR,
} from "../../utils/causaSustitucion/etiquetas.js";
import * as S from "../../utils/sustSEyCAE/etiquetas.js";

function SustRescisionContrato() {
  const formRef = useRef();
  const [form] = Form.useForm();

  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu);
  const menuAcciones = useSelector((store) => store.menu.menuAcciones);

  const sustituidoSeleccionado = useSelector(
    (store) => store.selectFolioNombre.sustituidoSeleccionado
  );
  const isLoadingSelectFolioNombre = useSelector(
    (store) => store.selectFolioNombre.isLoadingSelectFolioNombre
  );

  const navigate = useNavigate();

  const [vistaActual, setVistaActual] = useState(1);

  const [idSustitucion, setIdSustitucion] = useState(0);
  const [seSustituto, setSeSustituto] = useState({});
  const [caeSustituto, setCaeSustituto] = useState({});

  const [sustitutoConsultaSe, setSustitutoConsultaSe] = useState({});
  const [sustitutoConsultaCae, setSustitutoConsultaCae] = useState({});

  const [infoSustitucionSe, setInfoSustitucionSe] = useState({});
  const [infoSustitucionCae, setInfoSustitucionCae] = useState({});

  const [fechaAltaCapacitador, setFechaAltaCapacitador] = useState(null);
  const [fechaAltaSupervisor, setFechaAltaSupervisor] = useState(null);

  const [causaSustitucion, setCausaSustitucion] = useState({});
  const [pendiente, setPendiente] = useState(false);

  const [imgB64Sustituido, setImgB64Sustituido] = useState();
  const [extensionArchSustituido, setextensionArchSustituido] = useState();

  const [imgB64SustSupervisor, setImgB64SustSupervisor] = useState();
  const [extensionArchSustSupervisor, setextensionArchSustSupervisor] =
    useState();

  const [imgB64SustCapacitador, setImgB64SustCapacitador] = useState();
  const [extensionArchSustCapacitador, setextensionArchSustCapacitador] =
    useState();

  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  const [loading, setLoading] = useState(false);

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
    if (sustituidoSeleccionado !== null && vistaActual !== FLUJO_CAPTURA) {
      const request = {
        idProcesoElectoral: menu.proceso.idProcesoElectoral,
        idDetalleProceso: menu.proceso.idDetalleProceso,
        idParticipacion: menu.idParticipacion,
        idAspirante: sustituidoSeleccionado.idAspirante,
        tipoCausaVacante: 1,
        idSustitucion,
      };
      obtenerInfoSustitucion(request);
    }
    if (!pendiente) resetFormSustitucion();
  }, [sustituidoSeleccionado]);

  const onFinish = () => {
    if (
      !pendiente &&
      sustituidoSeleccionado.idPuesto === 1 &&
      causaSustitucion.tipoCausaVacante === 3 &&
      causaSustitucion.idCausaVacante === "2"
    ) {
      formRef.current.setFields([
        {
          name: [`select3`],
          errors: [
            "La opción de Promoción no es válida para Supervisor Electoral.",
          ],
        },
      ]);
    } else {
      switch (vistaActual) {
        case ACCION_CAPTURA: {
          capturarSustitucion();
          break;
        }
        case ACCION_MODIFICA: {
          modificarSustitucion();
          break;
        }
      }
    }
  };

  const resetFormSustitucion = () => {
    setSeSustituto(undefined);
    setCaeSustituto(undefined);
    setSustitutoConsultaSe({});
    setSustitutoConsultaCae({});
    setInfoSustitucionSe({});
    setInfoSustitucionCae({});
    setFechaAltaCapacitador("");
    setFechaAltaSupervisor("");
    setImgB64Sustituido(undefined);
    setextensionArchSustituido(undefined);
    setImgB64SustSupervisor(undefined);
    setextensionArchSustSupervisor(undefined);
    setImgB64SustCapacitador(undefined);
    setextensionArchSustCapacitador(undefined);
    setCausaSustitucion((prev) => ({
      ...prev,
      idCausaVacante: null,
      tipoCausaVacante: null,
      descripcionCausa: "",
      fechaBaja: null,
      observaciones: "",
    }));

    formRef.current.setFieldsValue({
      radios: undefined,
      select1: undefined,
      select2: undefined,
      select3: undefined,
      fechaCausa: null,
      observaciones: "",
      correoElectronicoSustSupervisor: undefined,
      correoElectronicoSustCapacitador: undefined,
      fechaSustSupervisor: null,
    });
  };

  const obtenerRequestSustitucionSE = async () => {
    setLoading(true);
    let request = {};
    if (caeSustituto?.idAspirante) {
      request = {
        datosSustitucionSe: generarDatosSustitucion(
          sustituidoSeleccionado,
          seSustituto,
          fechaAltaSupervisor,
          causaSustitucion.fechaBaja ? causaSustitucion.fechaBaja : new Date(),
          causaSustitucion,
          user,
          menu,
          imgB64SustSupervisor,
          extensionArchSustSupervisor,
          formRef.current.getFieldValue("correoElectronicoSustSupervisor")
        ),
        datosSustitucionCae: generarDatosSustitucion(
          seSustituto,
          caeSustituto,
          fechaAltaCapacitador,
          fechaAltaSupervisor,
          causaSustitucion,
          user,
          menu,
          imgB64SustCapacitador,
          extensionArchSustCapacitador,
          formRef.current.getFieldValue("correoElectronicoSustCapacitador")
        ),
        imgB64Sustituido,
        extensionArchSustituido,
      };
    } else {
      request = {
        datosSustitucionSe: generarDatosSustitucion(
          sustituidoSeleccionado,
          seSustituto,
          fechaAltaSupervisor,
          causaSustitucion.fechaBaja ? causaSustitucion.fechaBaja : new Date(),
          causaSustitucion,
          user,
          menu,
          imgB64SustSupervisor,
          extensionArchSustSupervisor,
          formRef.current.getFieldValue("correoElectronicoSustSupervisor")
        ),
        imgB64Sustituido,
        extensionArchSustituido,
      };
    }
    const response = await apiClientPost("guardarSustRescision", request);

    setLoading(false);

    if (response && response.code === CODE_SUCCESS && response.data) {
      resetFormSustitucion();
      setMsjModal(MSJ_EXITO_GUARDAR);
      setTipoModal(1);
    } else {
      setMsjModal(MSJ_ERROR_GUARDAR);
      setTipoModal(2);
    }

    setOpenModal(true);
  };

  const obtenerRequestSustitucionCAE = async () => {
    setLoading(true);
    const request = {
      datosSustitucionCae: generarDatosSustitucion(
        sustituidoSeleccionado,
        caeSustituto,
        fechaAltaCapacitador,
        causaSustitucion.fechaBaja ? causaSustitucion.fechaBaja : new Date(),
        causaSustitucion,
        user,
        menu,
        imgB64SustCapacitador,
        extensionArchSustCapacitador,
        formRef.current.getFieldValue("correoElectronicoSustCapacitador")
      ),
      imgB64Sustituido,
      extensionArchSustituido,
    };

    const response = await apiClientPost("guardarSustRescision", request);

    setLoading(false);

    if (response && response.code === CODE_SUCCESS && response.data) {
      resetFormSustitucion();
      setMsjModal(MSJ_EXITO_GUARDAR);
      setTipoModal(1);
    } else {
      setMsjModal(MSJ_ERROR_GUARDAR);
      setTipoModal(2);
    }

    setOpenModal(true);
  };

  const obtenerRequestSustitucionSEPendiente = async () => {
    setLoading(true);
    let request = {};
    if (caeSustituto?.idAspirante) {
      request = {
        datosSustitucionSe: generarDatosSustitucionPendiente(
          infoSustitucionSe,
          seSustituto,
          fechaAltaSupervisor,
          causaSustitucion,
          user,
          imgB64SustSupervisor,
          extensionArchSustSupervisor,
          formRef.current.getFieldValue("correoElectronicoSustSupervisor")
        ),
        datosSustitucionCae: generarDatosSustitucion(
          seSustituto,
          caeSustituto,
          fechaAltaCapacitador,
          fechaAltaSupervisor,
          causaSustitucion,
          user,
          menu,
          imgB64SustCapacitador,
          extensionArchSustCapacitador,
          formRef.current.getFieldValue("correoElectronicoSustCapacitador")
        ),
        imgB64Sustituido,
        extensionArchSustituido,
      };
    } else {
      request = {
        datosSustitucionSe: generarDatosSustitucionPendiente(
          infoSustitucionSe,
          seSustituto,
          fechaAltaSupervisor,
          causaSustitucion,
          user,
          imgB64SustSupervisor,
          extensionArchSustSupervisor,
          formRef.current.getFieldValue("correoElectronicoSustSupervisor")
        ),
        imgB64Sustituido,
        extensionArchSustituido,
      };
    }
    const response = await apiClientPost(
      "guardarSustRescisionPendiente",
      request
    );

    setLoading(false);

    if (response && response.code === CODE_SUCCESS && response.data) {
      resetFormSustitucion();
      setMsjModal(MSJ_EXITO_GUARDAR);
      setTipoModal(1);
    } else {
      setMsjModal(MSJ_ERROR_GUARDAR);
      setTipoModal(2);
    }
    setOpenModal(true);
  };

  const obtenerRequestSustitucionCAEPendiente = async () => {
    setLoading(true);
    let request = {};
    if (caeSustituto.idAspirante !== undefined) {
      request = {
        datosSustitucionCae: generarDatosSustitucionPendiente(
          infoSustitucionCae,
          caeSustituto,
          fechaAltaCapacitador,
          causaSustitucion,
          user,
          imgB64SustCapacitador,
          extensionArchSustCapacitador,
          formRef.current.getFieldValue("correoElectronicoSustCapacitador")
        ),
        imgB64Sustituido,
        extensionArchSustituido,
      };
    }
    const response = await apiClientPost(
      "guardarSustRescisionPendiente",
      request
    );
    setLoading(false);
    if (response && response.code === CODE_SUCCESS && response.data) {
      resetFormSustitucion();
      setMsjModal(MSJ_EXITO_GUARDAR);
      setTipoModal(1);
    } else {
      setMsjModal(MSJ_ERROR_GUARDAR);
      setTipoModal(2);
    }
    setOpenModal(true);
  };

  const sustitucionSE = () => {
    if (seSustituto?.folioPrincipal && caeSustituto?.folioPrincipal) {
      if (compararFechas(causaSustitucion.fechaBaja, fechaAltaSupervisor)) {
        formRef.current.setFields([
          {
            name: [`fechaSustCapacitador-${seSustituto.folioPrincipal}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
      } else if (compararFechas(fechaAltaSupervisor, fechaAltaCapacitador)) {
        formRef.current.setFields([
          {
            name: [`fechaSustCapacitador-${caeSustituto.folioPrincipal}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
      } else if (pendiente) {
        obtenerRequestSustitucionSEPendiente();
      } else {
        obtenerRequestSustitucionSE();
      }
    }
    if (seSustituto?.folioPrincipal && !caeSustituto?.folioPrincipal) {
      if (compararFechas(causaSustitucion.fechaBaja, fechaAltaSupervisor)) {
        formRef.current.setFields([
          {
            name: [`fechaSustCapacitador-${seSustituto.folioPrincipal}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
      } else if (pendiente) {
        obtenerRequestSustitucionSEPendiente();
      } else {
        obtenerRequestSustitucionSE();
      }
    }
    if (!seSustituto?.folioPrincipal) {
      if (pendiente) {
        obtenerRequestSustitucionSEPendiente();
      } else {
        obtenerRequestSustitucionSE();
      }
    }
  };

  const sustitucionCAE = () => {
    if (caeSustituto?.folioPrincipal) {
      if (compararFechas(causaSustitucion.fechaBaja, fechaAltaCapacitador)) {
        formRef.current.setFields([
          {
            name: [`fechaSustCapacitador-${caeSustituto.folioPrincipal}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
      } else if (pendiente) {
        obtenerRequestSustitucionCAEPendiente();
      } else {
        obtenerRequestSustitucionCAE();
      }
    } else if (pendiente) {
      obtenerRequestSustitucionCAEPendiente();
    } else {
      obtenerRequestSustitucionCAE();
    }
  };

  const capturarSustitucion = () => {
    const idPuesto = !pendiente
      ? sustituidoSeleccionado.idPuesto
      : infoSustitucionSe.idPuestoSustituido
      ? infoSustitucionSe.idPuestoSustituido
      : infoSustitucionCae.idPuestoSustituido;

    switch (idPuesto) {
      case 1:
      case 4: // SE RESCISION
      case 6: // SE RECONTRATACIÓN
      case 7: // SE INCAPACITADO
      case 10: {
        // SE TEMPORAL
        sustitucionSE();
        break;
      }
      case 2: // CAE
      case 8: // CAE RECONTRATACIÓN
      case 9: // CAE INCAPACITADO
      case 11: // CAE TEMPORAL
      case 12: {
        // CAE RESCISION
        sustitucionCAE();
        break;
      }
    }
  };

  const modificarSustitucion = async () => {
    let modificar = true;

    if (compararFechas(causaSustitucion.fechaBaja, fechaAltaSupervisor)) {
      formRef.current.setFields([
        {
          name: [`fechaSustSupervisor`],
          errors: ["La fecha no puede ser anterior a la del sustituido."],
        },
      ]);
      modificar = false;
    }

    const isFechaInvalida2 = fechaAltaSupervisor
      ? compararFechas(fechaAltaSupervisor, fechaAltaCapacitador)
      : compararFechas(causaSustitucion.fechaBaja, fechaAltaCapacitador);

    if (isFechaInvalida2) {
      formRef.current.setFields([
        {
          name: [`fechaSustCapacitador-${sustitutoConsultaCae.folioAspirante}`],
          errors: ["La fecha no puede ser anterior a la del sustituido."],
        },
      ]);
      modificar = false;
    }

    if (!modificar) {
      return;
    }

    setLoading(true);

    const request = {
      idProcesoElectoral: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      idSustitucionSE: infoSustitucionSe?.id?.idSustitucion,
      idSustitucionCAE: infoSustitucionCae?.id?.idSustitucion,
      tipoCausaVacante: causaSustitucion.tipoCausaVacante,
      idCausaVacante: causaSustitucion.idCausaVacante,
      fechaBaja: formatoFecha(causaSustitucion.fechaBaja),
      observaciones: causaSustitucion.observaciones,
      fechaAltaSupervisor: formatoFecha(fechaAltaSupervisor),
      fechaAltaCapacitador: formatoFecha(fechaAltaCapacitador),

      imgB64Sustituido: imgB64Sustituido,
      extensionArchivoSustituido: extensionArchSustituido,
      imgB64SustitutoSE: imgB64SustSupervisor,
      extensionArchivoSustitutoSE: extensionArchSustSupervisor,
      imgB64SustitutoCAE: imgB64SustCapacitador,
      extensionArchivoSustitutoCAE: extensionArchSustCapacitador,

      usuario: user.username,
    };

    const response = await apiClientPost("modificarSustRescision", request);

    setLoading(false);

    if (response.code === CODE_SUCCESS && response.data) {
      resetFormSustitucion();
      setMsjModal(MSJ_EXITO_MODIFICAR);
      setTipoModal(1);
    } else {
      setMsjModal(MSJ_ERROR_MODIFICAR);
      setTipoModal(2);
    }
    setOpenModal(true);
  };

  const obtenerInfoSustitucion = async (request) => {
    const { data } = await apiClientPost(
      "obtenerInformacionSustitucion",
      request
    );
    setInfoSustitucionSe(
      data.dtoSustitucionSE ? data.dtoSustitucionSE.dtoSustituciones : {}
    );
    setSustitutoConsultaSe(data.datosSustitutoSE ? data.datosSustitutoSE : {});
    setInfoSustitucionCae(
      data.dtoSustitucionCAE ? data.dtoSustitucionCAE.dtoSustituciones : {}
    );
    setSustitutoConsultaCae(
      data.datosSustitutoCAE ? data.datosSustitutoCAE : {}
    );
    if (data.dtoSustitucionSE) {
      setCausaSustitucion({
        fechaBaja: convertirDateAString(
          data.dtoSustitucionSE.dtoSustituciones.fechaBaja
        ),
        observaciones: data.dtoSustitucionSE.dtoSustituciones.observaciones,
        idCausaVacante: data.dtoSustitucionSE.dtoSustituciones.idCausaVacante,
        tipoCausaVacante:
          data.dtoSustitucionSE.dtoSustituciones.tipoCausaVacante,
        descripcionCausa: data.dtoSustitucionSE.descripcionCausaVacante,
      });

      setFechaAltaSupervisor(
        convertirDateAString(validarFechaNoNulo(data.dtoSustitucionSE))
      );
      setFechaAltaCapacitador(
        convertirDateAString(validarFechaNoNulo(data.dtoSustitucionCAE))
      );

      formRef.current.setFieldValue(
        "radios",
        data.dtoSustitucionSE.dtoSustituciones.tipoCausaVacante
      );
      formRef.current.setFieldValue(
        "fechaCausa",
        obtenerMomentFecha(data.dtoSustitucionSE.dtoSustituciones.strFechaBaja)
      );
      formRef.current.setFieldValue(
        "fechaSustSupervisor",
        obtenerMomentFecha(
          data.dtoSustitucionSE?.dtoSustituciones?.strFechaAlta
        )
      );
      formRef.current.setFieldValue(
        `fechaSustCapacitador-${getIdAspirante(data.datosSustitutoCAE)}`,
        obtenerMomentFecha(
          data.dtoSustitucionCAE?.dtoSustituciones?.strFechaAlta
        )
      );
      if (data.dtoSustitucionSE.dtoSustituciones.tipoCausaVacante === 1) {
        formRef.current.setFieldValue(
          "select1",
          data.dtoSustitucionSE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      if (data.dtoSustitucionSE.dtoSustituciones.tipoCausaVacante === 2) {
        formRef.current.setFieldValue(
          "select2",
          data.dtoSustitucionSE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      if (data.dtoSustitucionSE.dtoSustituciones.tipoCausaVacante === 3) {
        formRef.current.setFieldValue(
          "select3",
          data.dtoSustitucionSE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      formRef.current.setFieldValue(
        "observaciones",
        data.dtoSustitucionSE.dtoSustituciones.observaciones
      );
    } else {
      setCausaSustitucion({
        fechaBaja: convertirDateAString(
          data.dtoSustitucionCAE.dtoSustituciones.fechaBaja
        ),
        observaciones: data.dtoSustitucionCAE.dtoSustituciones.observaciones,
        idCausaVacante: data.dtoSustitucionCAE.dtoSustituciones.idCausaVacante,
        tipoCausaVacante:
          data.dtoSustitucionCAE.dtoSustituciones.tipoCausaVacante,
        descripcionCausa: data.dtoSustitucionCAE.descripcionCausaVacante,
      });
      setFechaAltaCapacitador(
        convertirDateAString(validarFechaNoNulo(data.dtoSustitucionCAE))
      );
      formRef.current.setFieldValue(
        "radios",
        data.dtoSustitucionCAE.dtoSustituciones.tipoCausaVacante
      );
      formRef.current.setFieldValue(
        "fechaCausa",
        obtenerMomentFecha(data.dtoSustitucionCAE.dtoSustituciones.strFechaBaja)
      );
      formRef.current.setFieldValue(
        "fechaSustSupervisor",
        obtenerMomentFecha(data.dtoSustitucionCAE.dtoSustituciones.strFechaAlta)
      );
      formRef.current.setFieldValue(
        `fechaSustCapacitador-${getIdAspirante(data.datosSustitutoCAE)}`,
        obtenerMomentFecha(data.dtoSustitucionCAE.dtoSustituciones.strFechaAlta)
      );
      if (data.dtoSustitucionCAE.dtoSustituciones.tipoCausaVacante === 1) {
        formRef.current.setFieldValue(
          "select1",
          data.dtoSustitucionCAE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      if (data.dtoSustitucionCAE.dtoSustituciones.tipoCausaVacante === 2) {
        formRef.current.setFieldValue(
          "select2",
          data.dtoSustitucionCAE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      if (data.dtoSustitucionCAE.dtoSustituciones.tipoCausaVacante === 3) {
        formRef.current.setFieldValue(
          "select3",
          data.dtoSustitucionCAE.dtoSustituciones.idCausaVacante.toString()
        );
      }
      formRef.current.setFieldValue(
        "observaciones",
        data.dtoSustitucionCAE.dtoSustituciones.observaciones
      );
    }
  };

  const validarMostrarBusquedaSupervisor = () => {
    return (
      (sustituidoSeleccionado.idPuesto === 1 &&
        vistaActual === FLUJO_CAPTURA &&
        !pendiente) ||
      (infoSustitucionSe?.idPuestoSustituido === 1 &&
        vistaActual === FLUJO_CAPTURA &&
        pendiente) ||
      (infoSustitucionSe?.idAspiranteSutituto && vistaActual !== FLUJO_CAPTURA)
    );
  };

  const validarMostrarBusquedaCapacitador = () => {
    return (
      (sustituidoSeleccionado.idPuesto === 2 &&
        vistaActual === FLUJO_CAPTURA &&
        !pendiente) ||
      (infoSustitucionCae?.idPuestoSustituido === 2 &&
        vistaActual === FLUJO_CAPTURA &&
        pendiente) ||
      (infoSustitucionCae?.idAspiranteSutituto &&
        vistaActual !== FLUJO_CAPTURA) ||
      (seSustituto?.idPuesto === 2 && vistaActual === FLUJO_CAPTURA)
    );
  };

  const validaIsPendiente = async (option) => {
    let idAspirante;
    if (option.value.includes(",")) {
      setIdSustitucion(option.value.split(",")[0]);
      idAspirante = option.value.split(",")[1];
    } else {
      idAspirante = option.value;
    }
    setPendiente(option?.pendiente ? option.pendiente : false);
    if (option.pendiente) {
      const request = {
        idDetalleProceso: menu.proceso.idDetalleProceso,
        idParticipacion: menu.idParticipacion,
        idAspirante,
      };
      const {
        data: { dtoSustituciones, descripcionCausaVacante },
      } = await apiClientPost("obtenerSustitucionPendiente", request);
      setCausaSustitucion({
        fechaBaja: convertirDateAString(dtoSustituciones.fechaBaja),
        observaciones: dtoSustituciones.observaciones,
        idCausaVacante: dtoSustituciones.idCausaVacante,
        tipoCausaVacante: dtoSustituciones.tipoCausaVacante,
        descripcionCausa: descripcionCausaVacante,
      });

      if (dtoSustituciones.idPuestoSustituido === 1) {
        setInfoSustitucionSe(dtoSustituciones);
      } else if (dtoSustituciones.idPuestoSustituido === 2) {
        setInfoSustitucionCae(dtoSustituciones);
      }
    }
  };

  const getIdAspirante = (aspirante) => {
    return aspirante?.folioAspirante;
  };

  const sustitutoSupervisorSeleccionado = (
    sustitutoSupervisor,
    correoElectronico
  ) => {
    setImgB64SustSupervisor();
    setextensionArchSustSupervisor();
    setFechaAltaSupervisor();
    formRef.current.setFieldsValue({
      [`fechaSustCapacitador-${sustitutoSupervisor?.folioPrincipal}`]: null,
    });

    setSeSustituto(sustitutoSupervisor);
    formRef.current.setFieldValue(
      "correoElectronicoSustSupervisor",
      correoElectronico
    );
  };

  const sustitutoCapacitadorSeleccionado = (
    sustitutoCapacitador,
    correoElectronico
  ) => {
    setImgB64SustCapacitador();
    setextensionArchSustCapacitador();
    setFechaAltaCapacitador();
    if (sustitutoCapacitador?.folioPrincipal ?? null) {
      formRef.current.setFieldsValue({
        [`fechaSustCapacitador-${sustitutoCapacitador?.folioPrincipal}`]: null,
      });
    }

    setCaeSustituto(sustitutoCapacitador);
    formRef.current.setFieldValue(
      "correoElectronicoSustCapacitador",
      correoElectronico
    );
  };

  const definirTipoFlujo = () => {
    if (vistaActual === FLUJO_CAPTURA) {
      return pendiente ? FLUJO_CONSULTA : FLUJO_CAPTURA;
    } else {
      return vistaActual;
    }
  };

  const cerrarModal = () => {
    setOpenModal(false);
    setMsjModal("");
    if (tipoModal === 1) {
      navigate(0);
    }
  };

  const convertirDateAString = (date) => {
    if (date) {
      const fecha = new Date(date);
      return `${moment(date).format("DD")}/${
        fecha.getMonth() + 1
      }/${fecha.getFullYear()}`;
    }
  };

  const compararFechas = (primerFecha, segundaFecha) => {
    return (
      dayjs(primerFecha, "DD/MM/YYYY").toDate().getTime() >
      dayjs(segundaFecha, "DD/MM/YYYY").toDate().getTime()
    );
  };

  const formatoFecha = (fecha) => {
    if (!fecha) return undefined;
    const fechaArry = fecha.split("/");
    const dia = fechaArry[0].length === 2 ? fechaArry[0] : `0${fechaArry[0]}`;
    const mes = fechaArry[1].length === 2 ? fechaArry[1] : `0${fechaArry[1]}`;
    return `${dia}/${mes}/${fechaArry[2]}`;
  };

  const obtenerMomentFecha = (fecha) => {
    if (!fecha) return "";
    return moment(fecha, "DD/MM/YYYY");
  };

  const validarFechaNoNulo = (dto) => {
    return dto?.dtoSustituciones?.fechaAlta;
  };

  return (
    <AuthenticatedComponent>
      <Loader blocking={loading} />
      <Template>
        <Form
          form={form}
          ref={formRef}
          onFinish={onFinish}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              e.preventDefault();
            }
          }}
        >
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
              moduloSust={SUST_RESC_CONTRATO}
              tipoFlujo={vistaActual}
              setIsPendiente={validaIsPendiente}
            />
            {sustituidoSeleccionado && (
              <>
                <BarInfo text={FORMATOS_PERMITIDOS_FOTOGRAFIA} />

                <HeaderSeccion
                  text={`Información del ${
                    sustituidoSeleccionado?.nombreCargo ?? "ciudadano"
                  } a sustituir`}
                />

                <Row justify="space-around" align="middle">
                  <Col xs={0} md={0} xl={2} />
                  <Col xs={24} md={6} xl={4}>
                    {isLoadingSelectFolioNombre ? (
                      <Skeleton.Image />
                    ) : (
                      <LoadFoto
                        key={sustituidoSeleccionado.folio}
                        imgBase64Prev={""}
                        urlFotoAspirante={sustituidoSeleccionado.urlFoto}
                        tipoFlujo={FLUJO_CAPTURA}
                        onChangeFoto={({ imagenB64, extensionArchivo }) => {
                          setImgB64Sustituido(imagenB64);
                          setextensionArchSustituido(extensionArchivo);
                        }}
                      />
                    )}
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
                          cargoAnterior={
                            vistaActual === ACCION_CAPTURA &&
                            sustituidoSeleccionado?.idPuesto === ID_PUESTO_SE
                              ? sustituidoSeleccionado?.cargoAnterior
                              : null
                          }
                        />
                      )}
                    </Row>
                    <Row>
                      {!pendiente && vistaActual === ACCION_CAPTURA ? (
                        <InfoData
                          info={S.ETQ_PERIODO_CONTRATACION}
                          data={`${
                            sustituidoSeleccionado?.fechaInicioContratacion ??
                            ""
                          } - ${
                            sustituidoSeleccionado?.fechaFinContratacion ?? ""
                          }`}
                        />
                      ) : null}
                    </Row>
                  </Col>
                </Row>
              </>
            )}
          </Layout>
          {sustituidoSeleccionado && (
            <>
              <CausaSustitucion
                moduloSust={SUST_RESC_CONTRATO}
                tipoFlujo={definirTipoFlujo()}
                causaSusti={causaSustitucion}
                setCausaSusti={setCausaSustitucion}
                formRef={formRef}
              />
              {validarMostrarBusquedaSupervisor() && (
                <BusquedaSustitutoSupervisor
                  fechaSustitucionAnterior={causaSustitucion.fechaBaja}
                  tipoFlujo={vistaActual}
                  infoSustitutoSe={sustitutoConsultaSe}
                  infoSustitucionSe={infoSustitucionSe}
                  onChangeDate={(date) => setFechaAltaSupervisor(date)}
                  onChangeFoto={({ imagenB64, extensionArchivo }) => {
                    setImgB64SustSupervisor(imagenB64);
                    setextensionArchSustSupervisor(extensionArchivo);
                  }}
                  sustitutoSupervisorSeleccionado={
                    sustitutoSupervisorSeleccionado
                  }
                />
              )}
              {validarMostrarBusquedaCapacitador() && (
                <BusquedaSustitutoCapacitador
                  fechaSustitucionAnterior1={causaSustitucion.fechaBaja}
                  fechaSustitucionAnterior2={fechaAltaSupervisor}
                  tipoFlujo={vistaActual}
                  infoSustitutoCae={sustitutoConsultaCae}
                  infoSustitucionCae={infoSustitucionCae}
                  onChangeDate={(date) => setFechaAltaCapacitador(date)}
                  onChangeFoto={({ imagenB64, extensionArchivo }) => {
                    setImgB64SustCapacitador(imagenB64);
                    setextensionArchSustCapacitador(extensionArchivo);
                  }}
                  suplenteCapacitadorSeleccionado={
                    sustitutoSupervisorSeleccionado
                  }
                  sustitutoCapacitadorSeleccionado={
                    sustitutoCapacitadorSeleccionado
                  }
                  onChangeCorreo={(correo) => console.log(correo)}
                />
              )}
              {vistaActual !== ACCION_CONSULTA && (
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

export default SustRescisionContrato;
