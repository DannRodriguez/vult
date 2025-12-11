import React, { useEffect, useState, useRef } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import {
  Layout,
  Skeleton,
  Row,
  Col,
  Form,
  Button,
  notification,
  DatePicker,
} from "antd";

import Template from "../interfaz/Template";
import AuthenticatedComponent from "../AuthenticatedComponent";
import { Loader } from "../interfaz/Loader";
import { CMenuAcciones } from "../interfaz/navbar/CMenuAcciones.jsx";
import BusquedaSeCae from "../commonComponents/busquedaSeCae/BusquedaSeCae";
import BusquedaSustitutoSupervisor from "../commonComponents/busquedaSustitutos/BusquedaSustitutoSupervisor";
import BusquedaSustitutoCapacitador from "../commonComponents/busquedaSustitutos/BusquedaSustitutoCapacitador";
import CausaSustitucion from "../commonComponents/causasSustitucion/CausaSustitucion";
import Miga from "../commonComponents/Miga";
import HeaderModulo from "../commonComponents/HeaderModulo";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
import {
  HeaderSeccion,
  BarInfo,
  InfoData,
  Requerido,
} from "../commonComponents/AccessoriesComponents";
import LoadFoto from "../commonComponents/LoadFoto";
import VInfoAspirante from "../commonComponents/VInfoAspirante";
import { apiClientPost } from "../../utils/apiClient.js";
import {
  ACCION_CONSULTA,
  FLUJO_CAPTURA,
  FLUJO_CONSULTA,
  FLUJO_MODIFICA,
  SUST_INCAPACIDAD,
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

export default function SustIncapacidad() {
  const navigate = useNavigate();

  const [api, contextHolder] = notification.useNotification();

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

  const [loading, setLoading] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  const [vistaActual, setVistaActual] = useState(FLUJO_CAPTURA);
  const [isPendiente, setIsPendiente] = useState(false);
  const [idSustitucionSeleccionada, setIdSustitucionSeleccionada] = useState(0);
  const [causaSusti, setCausaSusti] = useState("");
  const [muestraConsulta, setMuestraConsulta] = useState(false);

  const [suplenteSupervisor, setSuplenteSupervisor] = useState("");
  const [dateSuplenteSupervisor, setDateSuplenteSupervisor] = useState("");

  const [suplenteCapacitador, setSuplenteCapacitador] = useState("");
  const [dateSuplenteCapacitador, setDateSuplenteCapacitador] = useState("");
  const [cuentaSuplenteCapacitador, setCuentaSuplenteCapacitador] =
    useState("");

  const [imagenB64Sustituido, setImagenB64Sustituido] = useState(undefined);
  const [extensionArchivoSustituido, setExtensionArchivoSustituido] =
    useState(undefined);

  const [imagenB64SustitutoSE, setImagenB64SustitutoSE] = useState(undefined);
  const [extensionArchivoSustitutoSE, setExtensionArchivoSustitutoSE] =
    useState(undefined);

  const [imagenB64SustitutoCAE, setImagenB64SustitutoCAE] = useState(undefined);
  const [extensionArchivoSustitutoCAE, setExtensionArchivoSustitutoCAE] =
    useState(undefined);

  const [consultaPrimeraSust, setConsultaPrimeraSust] = useState(undefined);
  const [consultaSustSupervisor, setConsultaSustSupervisor] =
    useState(undefined);
  const [consultaSegundaSust, setConsultaSegundaSust] = useState(undefined);
  const [consultaSustCapacitador, setConsultaSustCapacitador] =
    useState(undefined);

  const [fechaInicioSustitucion, setFechaInicioSustitucion] =
    useState(undefined);
  const [fechaFinSustitucion, setFechaFinSustitucion] = useState(undefined);

  useEffect(() => {
    setSuplenteSupervisor("");
    setDateSuplenteSupervisor("");
    setSuplenteCapacitador("");
    setDateSuplenteCapacitador("");
    setCuentaSuplenteCapacitador("");

    setImagenB64Sustituido(undefined);
    setExtensionArchivoSustituido(undefined);

    setImagenB64SustitutoSE(undefined);
    setExtensionArchivoSustitutoSE(undefined);

    setImagenB64SustitutoCAE(undefined);
    setExtensionArchivoSustitutoCAE(undefined);

    const request = {
      idProceso: menu.proceso.idProcesoElectoral,
      idDetalle: menu.proceso.idDetalleProceso,
    };

    apiClientPost("obtenerFechasSustituciones", request)
      .then(({ code, data }) => {
        if (code == CODE_SUCCESS) {
          setFechaInicioSustitucion(data.fechaInicioSustituciones);
          setFechaFinSustitucion(data.fechaFinSustituciones);
        }
      })
      .catch((error) => {});

    if (!isPendiente) {
      setCausaSusti({
        idCausaVacante: 1,
        tipoCausaVacante: 4,
        observaciones: "",
      });
      formRef.current.setFieldsValue({
        observaciones: "",
        fechaCausa: undefined,
      });
    }

    if (sustituidoSeleccionado) {
      if (vistaActual == FLUJO_CONSULTA || vistaActual == FLUJO_MODIFICA) {
        const request = {
          idProceso: menu.proceso.idProcesoElectoral,
          idDetalleProceso: menu.proceso.idDetalleProceso,
          idParticipacion: menu.idParticipacion,
          idSustitucion: idSustitucionSeleccionada,
          idSustituido: sustituidoSeleccionado.idAspirante,
        };
        apiClientPost("consultaSustitucionesRelacion", request)
          .then(({ code, data }) => {
            if (code == CODE_SUCCESS) {
              setConsultaSustSupervisor(data.sustitutoUno ?? undefined);
              setConsultaSustCapacitador(data.sustitutoDos ?? undefined);
              setConsultaPrimeraSust(data.primeraSustitucion ?? undefined);
              setConsultaSegundaSust(data.segundaSustitucion ?? undefined);

              const valorEsSustitucionCausante =
                data.esSustitucionCausante ?? false;

              setCausaSusti({
                fechaBaja: data.primeraSustitucion.fechaBajaString,
                observaciones: data.primeraSustitucion.observaciones,
                idCausaVacante: valorEsSustitucionCausante ? 1 : 3,
                tipoCausaVacante: 4,
              });

              if (vistaActual == FLUJO_MODIFICA) {
                formRef.current.setFieldsValue({
                  fechaCausa: moment(
                    data.primeraSustitucion.fechaBajaString,
                    "DD/MM/YYYY"
                  ),
                });

                formRef.current.setFieldsValue({
                  [`fechaSustCapacitador-${data.sustitutoUno?.folio ?? ""}`]:
                    moment(data.primeraSustitucion.fechaBajaString),
                });
                setDateSuplenteSupervisor(
                  moment(data.primeraSustitucion?.fechaAlta).format(
                    "DD/MM/YYYY"
                  )
                );

                formRef.current.setFieldsValue({
                  [`fechaSustCapacitador-${data.sustitutoDos?.folio ?? ""}`]:
                    moment(data.primeraSustitucion.fechaBajaString),
                });
                setDateSuplenteCapacitador(
                  moment(data.segundaSustitucion?.fechaAlta).format(
                    "DD/MM/YYYY"
                  )
                );
              }
            } else {
              mostrarModal("Error al obtener la sustitución.", 2);
            }
            setLoading(false);
          })
          .catch((error) => {
            mostrarModal("Error al obtener la sustitución.", 2);
            setLoading(false);
          });
      }
    } else {
      setCausaSusti({
        idCausaVacante: 1,
        tipoCausaVacante: 4,
        observaciones: "",
      });
      formRef.current.setFieldsValue({
        observaciones: "",
        fechaCausa: undefined,
      });
      if (!sustituidoSeleccionado && !isLoadingSelectFolioNombre) {
        form.resetFields();
      }
      setMuestraConsulta(false);
    }
  }, [sustituidoSeleccionado]);

  useEffect(() => {
    form.resetFields();
    setMuestraConsulta(false);
    setConsultaSustSupervisor(undefined);
    setConsultaSustCapacitador(undefined);
    setCausaSusti({
      idCausaVacante: 1,
      tipoCausaVacante: 4,
      observaciones: "",
    });
    form.setFieldsValue({ observaciones: "" });
    setImagenB64Sustituido(undefined);
    setExtensionArchivoSustituido(undefined);
  }, [vistaActual]);

  useEffect(() => {
    if (causaSusti?.observaciones) {
      form.setFieldsValue({
        observaciones: causaSusti.observaciones,
        fechaCausa: causaSusti.fechaBaja
          ? moment(causaSusti.fechaBaja, "DD/MM/YYYY")
          : null,
      });
    }
  }, [causaSusti, form]);

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

  const openNotification = (titulo, mensaje) => {
    api.warning({
      message: `Alerta ${titulo}`,
      description: mensaje,
      placement: "top",
    });
  };

  function validaciones() {
    let esValido = true;

    if (isPendiente) {
      if (
        !(
          suplenteSupervisor?.id?.idAspirante ||
          suplenteCapacitador?.id?.idAspirante
        )
      ) {
        openNotification(
          "Selecciona un sustituto.",
          "Debes seleccionar a un sustituto de la persona pendiente."
        );
        esValido = false;
      } else {
        if (
          (suplenteSupervisor?.id?.idAspirante ?? 0) != 0 &&
          (suplenteCapacitador?.id?.idAspirante ?? 0) != 0
        ) {
          let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
          let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
          let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
          if (altaSE < bajaSustituido) {
            form.setFields([
              {
                name: [
                  `fechaSustCapacitador-${suplenteSupervisor.folioPrincipal}`,
                ],
                errors: ["La fecha no puede ser anterior a la del sustituido."],
              },
            ]);
            esValido = false;
          }
          if (altaCAE < bajaSustituido) {
            form.setFields([
              {
                name: [
                  `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
                ],
                errors: ["La fecha no puede ser anterior a la del sustituido."],
              },
            ]);
            esValido = false;
          } else {
            if (altaCAE < altaSE) {
              form.setFields([
                {
                  name: [
                    `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
                  ],
                  errors: [
                    "La fecha no puede ser anterior a la del sustituto del SE.",
                  ],
                },
              ]);
              esValido = false;
            }
          }
        } else if ((suplenteSupervisor?.id?.idAspirante ?? 0) != 0) {
          let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
          let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
          if (altaSE < bajaSustituido) {
            form.setFields([
              {
                name: [
                  `fechaSustCapacitador-${suplenteSupervisor.folioPrincipal}`,
                ],
                errors: ["La fecha no puede ser anterior a la del sustituido."],
              },
            ]);
            esValido = false;
          }
        } else if ((suplenteCapacitador?.id?.idAspirante ?? 0) != 0) {
          let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
          let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
          if (altaCAE < bajaSustituido) {
            form.setFields([
              {
                name: [
                  `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
                ],
                errors: ["La fecha no puede ser anterior a la del sustituido."],
              },
            ]);
            esValido = false;
          }
        }
      }
    } else {
      if (
        (suplenteSupervisor?.id?.idAspirante ?? 0) != 0 &&
        (suplenteCapacitador?.id?.idAspirante ?? 0) != 0
      ) {
        let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
        let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
        let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
        if (altaSE < bajaSustituido) {
          form.setFields([
            {
              name: [
                `fechaSustCapacitador-${suplenteSupervisor.folioPrincipal}`,
              ],
              errors: ["La fecha no puede ser anterior a la del sustituido."],
            },
          ]);
          esValido = false;
        }
        if (altaCAE < bajaSustituido) {
          form.setFields([
            {
              name: [
                `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
              ],
              errors: ["La fecha no puede ser anterior a la del sustituido."],
            },
          ]);
          esValido = false;
        } else {
          if (altaCAE < altaSE) {
            form.setFields([
              {
                name: [
                  `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
                ],
                errors: [
                  "La fecha no puede ser anterior a la del sustituto del SE.",
                ],
              },
            ]);
            esValido = false;
          }
        }
      } else if ((suplenteSupervisor?.id?.idAspirante ?? 0) != 0) {
        let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
        let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
        if (altaSE < bajaSustituido) {
          form.setFields([
            {
              name: [
                `fechaSustCapacitador-${suplenteSupervisor.folioPrincipal}`,
              ],
              errors: ["La fecha no puede ser anterior a la del sustituido."],
            },
          ]);
          esValido = false;
        }
      } else if ((suplenteCapacitador?.id?.idAspirante ?? 0) != 0) {
        let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
        let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
        if (altaCAE < bajaSustituido) {
          form.setFields([
            {
              name: [
                `fechaSustCapacitador-${suplenteCapacitador.folioPrincipal}`,
              ],
              errors: ["La fecha no puede ser anterior a la del sustituido."],
            },
          ]);
          esValido = false;
        }
      }
    }
    return esValido;
  }

  function validacionesModifica() {
    let esValido = true;

    if (
      (consultaSustSupervisor?.idAspirante ?? 0) != 0 &&
      (consultaSustCapacitador?.idAspirante ?? 0) != 0
    ) {
      let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
      let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
      let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
      if (altaSE < bajaSustituido) {
        form.setFields([
          {
            name: [`fechaSustCapacitador-${consultaSustSupervisor.folio}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
        esValido = false;
      }
      if (altaCAE < bajaSustituido) {
        form.setFields([
          {
            name: [`fechaSustCapacitador-${consultaSustCapacitador.folio}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
        esValido = false;
      } else {
        if (altaCAE < altaSE) {
          form.setFields([
            {
              name: [`fechaSustCapacitador-${consultaSustCapacitador.folio}`],
              errors: [
                "La fecha no puede ser anterior a la del sustituto del SE.",
              ],
            },
          ]);
          esValido = false;
        }
      }

      let fechaBajaOriginalSust = moment(consultaPrimeraSust.fechaBaja).format(
        "DD/MM/YYYY"
      );
      let fechaAltaOriginalSust = moment(consultaPrimeraSust.fechaAlta).format(
        "DD/MM/YYYY"
      );
      let fechaAltaOriginalSustDos = moment(
        consultaSegundaSust.fechaAlta
      ).format("DD/MM/YYYY");
      if (
        fechaBajaOriginalSust == causaSusti.fechaBaja &&
        fechaAltaOriginalSust == dateSuplenteSupervisor &&
        consultaPrimeraSust.observaciones == causaSusti.observaciones &&
        dateSuplenteCapacitador == fechaAltaOriginalSustDos &&
        !imagenB64Sustituido &&
        !imagenB64SustitutoSE &&
        !imagenB64SustitutoCAE
      ) {
        esValido = false;
        openNotification(
          "Sin cambios detectados.",
          "No se ha detectado algún cambio realizado."
        );
        return esValido;
      }
    } else if ((consultaSustSupervisor?.idAspirante ?? 0) != 0) {
      let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
      let altaSE = moment(dateSuplenteSupervisor, "DD/MM/YYYY");
      if (altaSE < bajaSustituido) {
        form.setFields([
          {
            name: [`fechaSustCapacitador-${consultaSustSupervisor.folio}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
        esValido = false;
      }

      let fechaBajaOriginalSust = moment(consultaPrimeraSust.fechaBaja).format(
        "DD/MM/YYYY"
      );
      let fechaAltaOriginalSust = moment(consultaPrimeraSust.fechaAlta).format(
        "DD/MM/YYYY"
      );
      if (
        fechaBajaOriginalSust == causaSusti.fechaBaja &&
        fechaAltaOriginalSust == dateSuplenteSupervisor &&
        consultaPrimeraSust.observaciones == causaSusti.observaciones &&
        !imagenB64Sustituido &&
        !imagenB64SustitutoSE
      ) {
        esValido = false;
        openNotification(
          "Sin cambios detectados.",
          "No se ha detectado algún cambio realizado."
        );
        return esValido;
      }
    } else if ((consultaSustCapacitador?.idAspirante ?? 0) != 0) {
      let bajaSustituido = moment(causaSusti.fechaBaja, "DD/MM/YYYY");
      let altaCAE = moment(dateSuplenteCapacitador, "DD/MM/YYYY");
      if (altaCAE < bajaSustituido) {
        form.setFields([
          {
            name: [`fechaSustCapacitador-${consultaSustCapacitador.folio}`],
            errors: ["La fecha no puede ser anterior a la del sustituido."],
          },
        ]);
        esValido = false;
      }

      let fechaBajaOriginalSust = moment(consultaPrimeraSust.fechaBaja).format(
        "DD/MM/YYYY"
      );
      let fechaAltaOriginalSust = moment(consultaPrimeraSust.fechaAlta).format(
        "DD/MM/YYYY"
      );
      if (
        fechaBajaOriginalSust == causaSusti.fechaBaja &&
        fechaAltaOriginalSust == dateSuplenteCapacitador &&
        consultaPrimeraSust.observaciones == causaSusti.observaciones &&
        !imagenB64Sustituido &&
        !imagenB64SustitutoSE
      ) {
        esValido = false;
        openNotification(
          "Sin cambios detectados.",
          "No se ha detectado algún cambio realizado."
        );
        return esValido;
      }
    } else {
      let fechaBajaOriginalSust = moment(consultaPrimeraSust.fechaBaja).format(
        "DD/MM/YYYY"
      );
      if (
        fechaBajaOriginalSust == causaSusti.fechaBaja &&
        consultaPrimeraSust.observaciones == causaSusti.observaciones &&
        !imagenB64Sustituido
      ) {
        esValido = false;
        openNotification(
          "Sin cambios detectados.",
          "No se ha detectado algún cambio realizado."
        );
        return esValido;
      }
    }

    return esValido;
  }

  function mandaForm(values) {
    let idAspiranteSustituido;
    let idSustitucion = 0;
    if (values.idSustituido.includes(",")) {
      idSustitucion = values.idSustituido.split(",")[0];
      idAspiranteSustituido = values.idSustituido.split(",")[1];
    } else {
      idAspiranteSustituido = values.idSustituido;
    }
    if (vistaActual == FLUJO_CAPTURA) {
      if (validaciones()) {
        const request = {
          idProceso: menu.proceso.idProcesoElectoral,
          idDetalleProceso: menu.proceso.idDetalleProceso,
          idParticipacion: menu.idParticipacion,
          tipoFlujo: vistaActual,
          esPendiente: isPendiente,
          idSustitucion,
          idCausaVacante: causaSusti.idCausaVacante,
          tipoCausaVacante: causaSusti.tipoCausaVacante,
          idSustituido: idAspiranteSustituido,
          fechaBajaSustituido: causaSusti.fechaBaja,
          observacionesBajaSustituido: causaSusti.observaciones ?? "",
          imagenB64Sustituido,
          extensionArchivoSustituido,
          idSustitutoSupervisor: suplenteSupervisor?.id?.idAspirante ?? 0,
          dateSuplenteSupervisor: dateSuplenteSupervisor,
          imagenB64SustitutoSE,
          extensionArchivoSustitutoSE,
          idSustituidoCapacitador: suplenteCapacitador?.id?.idAspirante ?? 0,
          dateSuplenteCapacitador: dateSuplenteCapacitador,
          imagenB64SustitutoCAE,
          extensionArchivoSustitutoCAE,
          cuentaSuplenteCapacitador: cuentaSuplenteCapacitador,
          usuario: user.username,
        };

        setLoading(true);
        apiClientPost("guardarSustIncapacidad", request)
          .then(({ code }) => {
            if (code == CODE_SUCCESS) {
              mostrarModal(MSJ_EXITO_GUARDAR, 1);
            } else {
              mostrarModal(MSJ_ERROR_GUARDAR, 2);
            }
            setLoading(false);
          })
          .catch((error) => {
            mostrarModal(MSJ_ERROR_GUARDAR, 2);
            setLoading(false);
          });
      }
    } else if (vistaActual == FLUJO_MODIFICA) {
      if (validacionesModifica()) {
        const request = {
          idProceso: menu.proceso.idProcesoElectoral,
          idDetalleProceso: menu.proceso.idDetalleProceso,
          idParticipacion: menu.idParticipacion,
          tipoFlujo: vistaActual,
          idSustitucion: idSustitucionSeleccionada,
          idSustituidoPrimeraSust:
            consultaPrimeraSust?.idAspiranteSutituido ?? 0,
          fechaBajaSustituido: causaSusti.fechaBaja,
          observacionesSustituido: causaSusti.observaciones,
          imagenB64Sustituido,
          extensionArchivoSustituido,
          fechaAltaSustitutoPrimeraSust:
            consultaPrimeraSust?.idAspiranteSutituto
              ? dateSuplenteSupervisor
              : "",
          imagenB64SustitutoSE,
          extensionArchivoSustitutoSE,
          idSustituidoSegundaSust:
            consultaSegundaSust?.idAspiranteSutituido ?? 0,
          fechaAltaSustitutoSegundaSust:
            consultaSegundaSust?.idAspiranteSutituto
              ? dateSuplenteCapacitador
              : "",
          imagenB64SustitutoCAE,
          extensionArchivoSustitutoCAE,
          usuario: user.username,
        };

        setLoading(true);
        apiClientPost("modificarSustIncapacidad", request)
          .then(({ code }) => {
            if (code == CODE_SUCCESS) {
              mostrarModal(MSJ_EXITO_MODIFICAR, 1);
            } else {
              mostrarModal(MSJ_ERROR_MODIFICAR, 2);
            }
            setLoading(false);
          })
          .catch((error) => {
            mostrarModal(MSJ_ERROR_MODIFICAR, 2);
            setLoading(false);
          });
      }
    }
  }

  function validaIsPendiente(option) {
    let idAspirante;
    let idSustitucion = 0;
    if (option.value.includes(",")) {
      idSustitucion = option.value.split(",")[0];
      setIdSustitucionSeleccionada(idSustitucion);
      idAspirante = option.value.split(",")[1];
    } else {
      idAspirante = option.value;
      setIdSustitucionSeleccionada(0);
    }
    if (option?.pendiente) {
      const request = {
        idProceso: menu.proceso.idProcesoElectoral,
        idDetalleProceso: menu.proceso.idDetalleProceso,
        idParticipacion: menu.idParticipacion,
        idSustituido: idAspirante,
        idSustitucion,
      };

      setLoading(true);
      apiClientPost("obtenerInfoSustitucion", request)
        .then(({ code, data }) => {
          if (code == CODE_SUCCESS) {
            setMuestraConsulta(true);
            setCausaSusti({
              fechaBaja: data.fechaBajaString,
              observaciones: data.observaciones,
              idCausaVacante: 1,
              tipoCausaVacante: 4,
            });
            setIsPendiente(true);
          } else {
            mostrarModal("Error al obtener la sustitución.", 2);
          }
          setLoading(false);
        })
        .catch((error) => {
          mostrarModal("Error al obtener la sustitución.", 2);
          setLoading(false);
        });
    } else {
      setIsPendiente(false);
      setMuestraConsulta(false);
    }
  }

  const disabledDate = (current) => {
    const fechaFin = moment(fechaFinSustitucion, "DD-MM-YYYY");
    const fechaEnd = current.isAfter(fechaFin) ? fechaFin : moment();
    return (
      current &&
      (current.isBefore(moment(fechaInicioSustitucion, "DD-MM-YYYY"), "day") ||
        current.isAfter(fechaEnd, "day"))
    );
  };

  const onChangeSustitutoSupervisor = (
    suplenteSupervisor,
    correoElectronico
  ) => {
    setDateSuplenteSupervisor();
    setImagenB64SustitutoSE();
    setExtensionArchivoSustitutoSE();
    formRef.current.setFieldsValue({
      [`fechaSustCapacitador-${suplenteSupervisor?.folioPrincipal}`]: null,
    });

    setSuplenteSupervisor(suplenteSupervisor);
    form.setFieldValue("correoElectronicoSustSupervisor", correoElectronico);
    setCuentaSuplenteCapacitador(correoElectronico);
  };

  const onChangeSustitutoCapacitador = (
    suplenteCapacitador,
    correoElectronico
  ) => {
    setDateSuplenteCapacitador();
    setImagenB64SustitutoCAE();
    setExtensionArchivoSustitutoCAE();
    if (suplenteCapacitador?.folioPrincipal ?? null) {
      formRef.current.setFieldsValue({
        [`fechaSustCapacitador-${suplenteCapacitador?.folioPrincipal}`]: null,
      });
    }

    setSuplenteCapacitador(suplenteCapacitador);
    form.setFieldValue("correoElectronicoSustCapacitador", correoElectronico);
    setCuentaSuplenteCapacitador(correoElectronico);
  };

  function vistaRenderizada() {
    switch (vistaActual) {
      case FLUJO_CAPTURA:
        return (
          <>
            <Layout id="content-bitacora">
              <Miga />
              <HeaderModulo />
              <Requerido />
              <BusquedaSeCae
                moduloSust={SUST_INCAPACIDAD}
                tipoFlujo={vistaActual}
                setIsPendiente={validaIsPendiente}
              />

              {sustituidoSeleccionado ? (
                <>
                  <BarInfo text={FORMATOS_PERMITIDOS_FOTOGRAFIA} />

                  <HeaderSeccion
                    text={`Información del ${
                      sustituidoSeleccionado.nombreCargo ?? "ciudadano"
                    } a sustituir`}
                  />

                  <Row justify="space-around" align="middle">
                    <Col xs={0} md={0} xl={2} />
                    <Col xs={24} md={6} xl={4}>
                      {!isLoadingSelectFolioNombre ? (
                        <LoadFoto
                          tipoFlujo={
                            isPendiente ? FLUJO_CONSULTA : FLUJO_CAPTURA
                          }
                          urlFotoAspirante={sustituidoSeleccionado.urlFoto}
                          onChangeFoto={({ imagenB64, extensionArchivo }) => {
                            setImagenB64Sustituido(imagenB64);
                            setExtensionArchivoSustituido(extensionArchivo);
                          }}
                        />
                      ) : (
                        <Skeleton.Image />
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
                            cargoAnterior={
                              sustituidoSeleccionado?.idPuesto === ID_PUESTO_SE
                                ? sustituidoSeleccionado?.cargoAnterior
                                : null
                            }
                          />
                        )}
                      </Row>
                      <Row>
                        {!isPendiente && vistaActual === FLUJO_CAPTURA ? (
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
              ) : (
                <></>
              )}
            </Layout>
            {sustituidoSeleccionado ? (
              <>
                <CausaSustitucion
                  moduloSust={SUST_INCAPACIDAD}
                  tipoFlujo={muestraConsulta ? ACCION_CONSULTA : FLUJO_CAPTURA}
                  causaSusti={causaSusti}
                  setCausaSusti={setCausaSusti}
                />

                {sustituidoSeleccionado.idPuesto != undefined &&
                (sustituidoSeleccionado.idPuesto == 1 ||
                  sustituidoSeleccionado.idPuesto == 7) &&
                !isLoadingSelectFolioNombre ? (
                  <BusquedaSustitutoSupervisor
                    disabled={true}
                    tipoFlujo={vistaActual}
                    onChangeDate={(date) => setDateSuplenteSupervisor(date)}
                    onChangeCorreoSupervisor={(correo) =>
                      setCuentaSuplenteCapacitador(correo)
                    }
                    sustitutoSupervisorSeleccionado={
                      onChangeSustitutoSupervisor
                    }
                    onChangeFoto={({ imagenB64, extensionArchivo }) => {
                      setImagenB64SustitutoSE(imagenB64);
                      setExtensionArchivoSustitutoSE(extensionArchivo);
                    }}
                  />
                ) : (
                  <></>
                )}

                {(suplenteSupervisor?.idPuesto != undefined &&
                  suplenteSupervisor?.idPuesto == 2 &&
                  !isLoadingSelectFolioNombre) ||
                (sustituidoSeleccionado.idPuesto != undefined &&
                  (sustituidoSeleccionado.idPuesto == 2 ||
                    sustituidoSeleccionado.idPuesto == 10 ||
                    sustituidoSeleccionado.idPuesto == 8) &&
                  !isLoadingSelectFolioNombre) ? (
                  <BusquedaSustitutoCapacitador
                    tipoFlujo={vistaActual}
                    onChangeDate={(date) => setDateSuplenteCapacitador(date)}
                    sustitutoCapacitadorSeleccionado={
                      onChangeSustitutoCapacitador
                    }
                    onChangeCorreo={(correo) =>
                      setCuentaSuplenteCapacitador(correo)
                    }
                    onChangeFoto={({ imagenB64, extensionArchivo }) => {
                      setImagenB64SustitutoCAE(imagenB64);
                      setExtensionArchivoSustitutoCAE(extensionArchivo);
                    }}
                  />
                ) : (
                  <></>
                )}
              </>
            ) : (
              <></>
            )}
          </>
        );

      case FLUJO_CONSULTA:
        return (
          <>
            <Layout id="content-bitacora">
              <Miga />
              <HeaderModulo />
              <Requerido />
              <BusquedaSeCae
                moduloSust={SUST_INCAPACIDAD}
                tipoFlujo={vistaActual}
                setIsPendiente={validaIsPendiente}
              />

              {sustituidoSeleccionado ? (
                <>
                  <BarInfo text={FORMATOS_PERMITIDOS_FOTOGRAFIA} />

                  <HeaderSeccion
                    text={`Información del ${
                      consultaPrimeraSust?.idPuestoSustituido == 1
                        ? "Supervisor"
                        : "Capacitador"
                    } a sustituir`}
                  />

                  <Row justify="space-around" align="middle">
                    <Col xs={0} md={0} xl={2} />
                    <Col xs={24} md={6} xl={4}>
                      {!isLoadingSelectFolioNombre ? (
                        <LoadFoto
                          tipoFlujo={FLUJO_CONSULTA}
                          urlFotoAspirante={sustituidoSeleccionado.urlFoto}
                        />
                      ) : (
                        <Skeleton.Image />
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
              ) : (
                <></>
              )}
            </Layout>

            {sustituidoSeleccionado ? (
              <>
                <CausaSustitucion
                  moduloSust={SUST_INCAPACIDAD}
                  tipoFlujo={FLUJO_CONSULTA}
                  causaSusti={causaSusti}
                  setCausaSusti={setCausaSusti}
                />

                {consultaSustSupervisor ? (
                  <Layout id="layout_container_causa_susitucion">
                    <HeaderSeccion
                      text={
                        consultaPrimeraSust?.idPuestoSustituido == 1
                          ? `Sustituto/a de la persona supervisora`
                          : `Sustituto/a de la persona capacitadora`
                      }
                    />
                    <Row justify="space-around" align="middle">
                      <Col xs={0} md={0} xl={2} />
                      <Col xs={24} md={6} xl={4}>
                        {!isLoadingSelectFolioNombre ? (
                          <LoadFoto
                            key={consultaSustSupervisor.claveElectorFuar}
                            urlFotoAspirante={consultaSustSupervisor.urlFoto}
                            tipoFlujo={FLUJO_CONSULTA}
                          />
                        ) : (
                          <Skeleton.Image />
                        )}
                      </Col>
                      <Col xs={24} md={18} xl={18}>
                        <Row>
                          <>
                            <VInfoAspirante
                              folio={consultaSustSupervisor.folio}
                              apellidoPaterno={
                                consultaSustSupervisor.apellidoPaterno
                              }
                              apellidoMaterno={
                                consultaSustSupervisor.apellidoMaterno
                              }
                              nombre={consultaSustSupervisor.nombre}
                              nombreCargo={consultaSustSupervisor.nombreCargo}
                              claveElectorFuar={
                                consultaSustSupervisor.claveElectorFuar
                              }
                              isMostrarZORE={true}
                              numeroZonaResponsabilidad={
                                consultaSustSupervisor.numeroZonaResponsabilidad
                              }
                              isMostrarARE={true}
                              numeroAreaResponsabilidad={
                                consultaSustSupervisor.numeroAreaResponsabilidad
                              }
                            />
                            <InfoData
                              info={"Fecha de alta"}
                              data={`${
                                moment(consultaPrimeraSust.fechaAlta).format(
                                  "DD/MM/YYYY"
                                ) ?? ""
                              }`}
                            />
                          </>
                        </Row>
                      </Col>
                    </Row>
                  </Layout>
                ) : (
                  <></>
                )}

                {consultaSustCapacitador ? (
                  <Layout id="layout_container_causa_susitucion">
                    <HeaderSeccion
                      text={`Sustituto/a de la persona capacitadora`}
                    />
                    <Row justify="space-around" align="middle">
                      <Col xs={0} md={0} xl={2}></Col>
                      <Col xs={24} md={6} xl={4}>
                        {!isLoadingSelectFolioNombre ? (
                          <LoadFoto
                            key={consultaSustCapacitador.claveElectorFuar}
                            urlFotoAspirante={consultaSustCapacitador.urlFoto}
                            tipoFlujo={FLUJO_CONSULTA}
                            onChangeFoto={() => console.log("Carga")}
                          />
                        ) : (
                          <Skeleton.Image />
                        )}

                        <br />
                      </Col>
                      <Col xs={24} md={18} xl={18}>
                        <Row>
                          <>
                            <VInfoAspirante
                              folio={consultaSustCapacitador.folio}
                              apellidoPaterno={
                                consultaSustCapacitador.apellidoPaterno
                              }
                              apellidoMaterno={
                                consultaSustCapacitador.apellidoMaterno
                              }
                              nombre={consultaSustCapacitador.nombre}
                              nombreCargo={consultaSustCapacitador.nombreCargo}
                              claveElectorFuar={
                                consultaSustCapacitador.claveElectorFuar
                              }
                              isMostrarZORE={true}
                              numeroZonaResponsabilidad={
                                consultaSustCapacitador.numeroZonaResponsabilidad
                              }
                              isMostrarARE={true}
                              numeroAreaResponsabilidad={
                                consultaSustCapacitador.numeroAreaResponsabilidad
                              }
                            />
                            <InfoData
                              info={"Fecha de alta"}
                              data={`${
                                moment(consultaSegundaSust.fechaAlta).format(
                                  "DD/MM/YYYY"
                                ) ?? ""
                              }`}
                            />
                          </>
                        </Row>
                      </Col>
                    </Row>
                  </Layout>
                ) : (
                  <></>
                )}
              </>
            ) : (
              <></>
            )}
          </>
        );
      default:
        return (
          <>
            <Layout id="content-bitacora">
              <Miga />
              <HeaderModulo />
              <Requerido />
              <BusquedaSeCae
                moduloSust={SUST_INCAPACIDAD}
                tipoFlujo={vistaActual}
                setIsPendiente={validaIsPendiente}
              />
              {sustituidoSeleccionado ? (
                <>
                  <BarInfo text={FORMATOS_PERMITIDOS_FOTOGRAFIA} />

                  <HeaderSeccion
                    text={`Información del ${
                      consultaPrimeraSust?.idPuestoSustituido == 1
                        ? "Supervisor"
                        : "Capacitador"
                    } a sustituir`}
                  />

                  <Row justify="space-around" align="middle">
                    <Col xs={0} md={0} xl={2}></Col>
                    <Col xs={24} md={6} xl={4}>
                      {!isLoadingSelectFolioNombre ? (
                        <LoadFoto
                          tipoFlujo={FLUJO_MODIFICA}
                          urlFotoAspirante={sustituidoSeleccionado.urlFoto}
                          onChangeFoto={({ imagenB64, extensionArchivo }) => {
                            setImagenB64Sustituido(imagenB64);
                            setExtensionArchivoSustituido(extensionArchivo);
                          }}
                        />
                      ) : (
                        <Skeleton.Image />
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
              ) : (
                <></>
              )}
            </Layout>

            {sustituidoSeleccionado ? (
              <>
                <CausaSustitucion
                  moduloSust={SUST_INCAPACIDAD}
                  tipoFlujo={FLUJO_MODIFICA}
                  causaSusti={causaSusti}
                  setCausaSusti={setCausaSusti}
                />

                {consultaSustSupervisor ? (
                  <Layout id="layout_container_causa_susitucion">
                    <HeaderSeccion
                      text={
                        consultaPrimeraSust?.idPuestoSustituido == 1
                          ? `Sustituto/a de la persona supervisora`
                          : `Sustituto/a de la persona capacitadora`
                      }
                    />
                    <Row justify="space-around" align="middle">
                      <Col xs={0} md={0} xl={2} />
                      <Col xs={24} md={6} xl={4}>
                        <LoadFoto
                          key={consultaSustSupervisor.claveElectorFuar}
                          urlFotoAspirante={consultaSustSupervisor.urlFoto}
                          tipoFlujo={FLUJO_MODIFICA}
                          onChangeFoto={({ imagenB64, extensionArchivo }) => {
                            setImagenB64SustitutoSE(imagenB64);
                            setExtensionArchivoSustitutoSE(extensionArchivo);
                          }}
                        />
                      </Col>
                      <Col xs={24} md={18} xl={18}>
                        <Row>
                          <>
                            <VInfoAspirante
                              folio={consultaSustSupervisor.folio}
                              apellidoPaterno={
                                consultaSustSupervisor.apellidoPaterno
                              }
                              apellidoMaterno={
                                consultaSustSupervisor.apellidoMaterno
                              }
                              nombre={consultaSustSupervisor.nombre}
                              nombreCargo={consultaSustSupervisor.nombreCargo}
                              claveElectorFuar={
                                consultaSustSupervisor.claveElectorFuar
                              }
                              isMostrarZORE={true}
                              numeroZonaResponsabilidad={
                                consultaSustSupervisor.numeroZonaResponsabilidad
                              }
                              isMostrarARE={true}
                              numeroAreaResponsabilidad={
                                consultaSustSupervisor.numeroAreaResponsabilidad
                              }
                            />
                            <InfoData
                              info={
                                <>
                                  <span style={{ color: "#D5007F" }}>*</span>
                                  Fecha de alta
                                </>
                              }
                              data={
                                <Form.Item
                                  layout="vertical"
                                  label={""}
                                  name={`fechaSustCapacitador-${
                                    consultaSustSupervisor.folio ?? ""
                                  }`}
                                  rules={[
                                    {
                                      required: true,
                                      message: "Ingresar datos",
                                    },
                                  ]}
                                >
                                  <DatePicker
                                    disabledDate={(current) =>
                                      disabledDate(current)
                                    }
                                    className="calendar"
                                    placeholder={"dd/mm/aaaa"}
                                    defaultValue={
                                      consultaPrimeraSust.fechaAlta
                                        ? moment(consultaPrimeraSust.fechaAlta)
                                        : undefined
                                    }
                                    onChange={(date, dateString) => {
                                      setDateSuplenteSupervisor(dateString);
                                    }}
                                    format="DD/MM/YYYY"
                                  />
                                  <span style={{ display: "none" }}>
                                    {consultaPrimeraSust.fechaAlta
                                      ? `${consultaPrimeraSust.fechaAlta}`
                                      : ""}
                                  </span>
                                </Form.Item>
                              }
                            />
                          </>
                        </Row>
                      </Col>
                    </Row>
                  </Layout>
                ) : (
                  <></>
                )}

                {consultaSustCapacitador ? (
                  <Layout id="layout_container_causa_susitucion">
                    <HeaderSeccion
                      text={`Sustituto/a de la persona capacitadora`}
                    />
                    <Row justify="space-around" align="middle">
                      <Col xs={0} md={0} xl={2} />
                      <Col xs={24} md={6} xl={4}>
                        <LoadFoto
                          key={consultaSustCapacitador.claveElectorFuar}
                          urlFotoAspirante={consultaSustCapacitador.urlFoto}
                          tipoFlujo={FLUJO_MODIFICA}
                          onChangeFoto={({ imagenB64, extensionArchivo }) => {
                            setImagenB64SustitutoCAE(imagenB64);
                            setExtensionArchivoSustitutoCAE(extensionArchivo);
                          }}
                        />
                      </Col>
                      <Col xs={24} md={18} xl={18}>
                        <Row>
                          <>
                            <VInfoAspirante
                              folio={consultaSustCapacitador.folio}
                              apellidoPaterno={
                                consultaSustCapacitador.apellidoPaterno
                              }
                              apellidoMaterno={
                                consultaSustCapacitador.apellidoMaterno
                              }
                              nombre={consultaSustCapacitador.nombre}
                              nombreCargo={consultaSustCapacitador.nombreCargo}
                              claveElectorFuar={
                                consultaSustCapacitador.claveElectorFuar
                              }
                              isMostrarZORE={true}
                              numeroZonaResponsabilidad={
                                consultaSustCapacitador.numeroZonaResponsabilidad
                              }
                              isMostrarARE={true}
                              numeroAreaResponsabilidad={
                                consultaSustCapacitador.numeroAreaResponsabilidad
                              }
                            />
                            <InfoData
                              info={
                                <>
                                  <span style={{ color: "#D5007F" }}>*</span>
                                  Fecha de alta
                                </>
                              }
                              data={
                                <Form.Item
                                  layout="vertical"
                                  label={""}
                                  name={`fechaSustCapacitador-${
                                    consultaSustCapacitador.folio ?? ""
                                  }`}
                                  rules={[
                                    {
                                      required: true,
                                      message: "Ingresar datos",
                                    },
                                  ]}
                                >
                                  <DatePicker
                                    disabledDate={(current) =>
                                      disabledDate(current)
                                    }
                                    className="calendar"
                                    placeholder={"dd/mm/aaaa"}
                                    defaultValue={
                                      consultaSegundaSust.fechaAlta
                                        ? moment(consultaSegundaSust.fechaAlta)
                                        : undefined
                                    }
                                    onChange={(date, dateString) =>
                                      setDateSuplenteCapacitador(dateString)
                                    }
                                    format="DD/MM/YYYY"
                                  />
                                  <span style={{ display: "none" }}>
                                    {consultaSegundaSust.fechaAlta
                                      ? `${consultaSegundaSust.fechaAlta}`
                                      : ""}
                                  </span>
                                </Form.Item>
                              }
                            />
                          </>
                        </Row>
                      </Col>
                    </Row>
                  </Layout>
                ) : (
                  <></>
                )}
              </>
            ) : (
              <></>
            )}
          </>
        );
    }
  }

  return (
    <AuthenticatedComponent>
      {contextHolder}
      <Loader blocking={loading} />
      <Template>
        <CMenuAcciones
          state={vistaActual}
          setState={setVistaActual}
          menuAcciones={menuAcciones}
        />
        <Form
          form={form}
          ref={formRef}
          onFinish={mandaForm}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              e.preventDefault();
            }
          }}
        >
          {vistaRenderizada()}

          {vistaActual == FLUJO_CONSULTA || !sustituidoSeleccionado ? (
            <></>
          ) : (
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
