import apiClient from "../apiClient";
import * as S from "../../utils/sustSEyCAE/etiquetas.js";
import moment from "moment";
const dateFormat = "DD/MM/YYYY";

export const obtenerInfoSustitucion = async (request) => {
  try {
    const response = await apiClient.post("obtenerInfoSustitucion", request);
    return response.data.data;
  } catch (error) {
    console.error("ERROR -obtenerInfoSustitucion: ", error);
    return "";
  }
};

export const guardarSustitucionSEyCAE = async (request) => {
  try {
    const response = await apiClient.post("guardarSustitucionSEyCAE", request, {
      headers: { "Content-Type": "multipart/form-data" },
    });
    return response.data;
  } catch (error) {
    console.error("ERROR -guardarSustitucionSEyCAE: ", error);
    return "";
  }
};

export const obtenerInformacionSustitucion = async (request) => {
  try {
    const response = await apiClient.post(
      "obtenerInformacionSustitucion",
      request
    );
    return response.data.data;
  } catch (error) {
    console.error("ERROR -obtenerInformacionSustitucion: ", error);
    return "";
  }
};

export function validaCausaSusitutcion(causaSustitucion) {
  let errors = [];
  if (
    causaSustitucion?.tipoCausaVacante === undefined ||
    causaSustitucion?.tipoCausaVacante === null
  ) {
    errors.push({ name: "radios", errors: [S.MSJ_SELEC_OPCION] });
  } else if (
    causaSustitucion?.idCausaVacante === undefined ||
    causaSustitucion?.idCausaVacante === null
  ) {
    if (causaSustitucion?.tipoCausaVacante == 1) {
      errors.push({ name: "select1", errors: [S.MSJ_SELEC_OPCION] });
    } else if (causaSustitucion?.tipoCausaVacante == 2) {
      errors.push({ name: "select2", errors: [S.MSJ_SELEC_OPCION] });
    } else if (causaSustitucion?.tipoCausaVacante == 3) {
      errors.push({ name: "select3", errors: [S.MSJ_SELEC_OPCION] });
    }
  }

  if (
    causaSustitucion?.fechaBaja === undefined ||
    causaSustitucion?.fechaBaja === null
  ) {
    errors.push({ name: "fechaCausa", errors: [S.MSJ_SELEC_FECHA] });
  }

  if (
    causaSustitucion?.observaciones !== null &&
    causaSustitucion?.observaciones !== undefined
  ) {
    let formatObservacion = new RegExp(/^[A-Za-z0-9ÁÉÍÓÚÑáéíóúñ#.\s]{0,500}$/);
    if (!formatObservacion.test(causaSustitucion.observaciones)) {
      errors.push({
        name: "observaciones",
        errors: [S.MSJ_FORMATO_INCORRECTO],
      });
    }
  }
  return errors;
}

export function validaBitacoraDesempenio(
  mostrarBitacora,
  expedienteDesempenio,
  evaluacionDesempenio
) {
  let errors = [];
  if (mostrarBitacora) {
    if (expedienteDesempenio !== undefined && expedienteDesempenio !== null) {
      if (
        (expedienteDesempenio.correo === undefined ||
          expedienteDesempenio.correo === "N") &&
        (expedienteDesempenio.citatorio === undefined ||
          expedienteDesempenio.citatorio === "N") &&
        (expedienteDesempenio.constancia === undefined ||
          expedienteDesempenio.constancia === "N")
      ) {
        errors.push({
          name: "checksDoctos",
          errors: [S.MSJ_INFO_REQ],
        });
      } else if (
        evaluacionDesempenio === undefined ||
        evaluacionDesempenio === null
      ) {
        errors.push({
          name: "selectFrecuenciaBit",
          errors: [S.MSJ_INFO_REQ],
        });
        errors.push({
          name: "ChecksResponablesBit",
          errors: [S.MSJ_INFO_REQ],
        });
      } else if (
        evaluacionDesempenio?.valoracionRiesgo === null ||
        evaluacionDesempenio?.valoracionRiesgo === undefined ||
        !evaluacionDesempenio?.valoracionRiesgo
      ) {
        errors.push({
          name: "selectFrecuenciaBit",
          errors: [S.MSJ_INFO_REQ],
        });
      } else if (
        evaluacionDesempenio?.responsables === null ||
        evaluacionDesempenio?.responsables === undefined ||
        evaluacionDesempenio?.responsables.length === 0
      ) {
        errors.push({
          name: "ChecksResponablesBit",
          errors: [S.MSJ_INFO_REQ],
        });
      }
    } else if (
      evaluacionDesempenio !== undefined &&
      evaluacionDesempenio !== null &&
      ((evaluacionDesempenio?.observaciones !== null &&
        evaluacionDesempenio?.observaciones !== undefined &&
        evaluacionDesempenio?.observaciones.trim().length > 0) ||
        (evaluacionDesempenio?.idFrecuencia !== null &&
          evaluacionDesempenio?.idFrecuencia !== undefined) ||
        (evaluacionDesempenio?.idImpacto !== null &&
          evaluacionDesempenio?.idImpacto !== undefined))
    ) {
      if (
        evaluacionDesempenio?.valoracionRiesgo === null ||
        evaluacionDesempenio?.valoracionRiesgo === undefined ||
        !evaluacionDesempenio?.valoracionRiesgo
      ) {
        errors.push({
          name: "selectFrecuenciaBit",
          errors: [S.MSJ_INFO_REQ],
        });
      } else if (
        evaluacionDesempenio?.responsables === null ||
        evaluacionDesempenio?.responsables === undefined ||
        evaluacionDesempenio?.responsables.length === 0
      ) {
        errors.push({
          name: "ChecksResponablesBit",
          errors: [S.MSJ_INFO_REQ],
        });
      }
    } else if (
      evaluacionDesempenio?.valoracionRiesgo !== null &&
      evaluacionDesempenio?.valoracionRiesgo !== undefined &&
      evaluacionDesempenio?.valoracionRiesgo &&
      (evaluacionDesempenio?.responsables === null ||
        evaluacionDesempenio?.responsables === undefined ||
        evaluacionDesempenio?.responsables.length === 0)
    ) {
      errors.push({
        name: "ChecksResponablesBit",
        errors: [S.MSJ_INFO_REQ],
      });
    } else if (
      evaluacionDesempenio?.responsables !== null &&
      evaluacionDesempenio?.responsables !== undefined &&
      evaluacionDesempenio?.responsables.length > 0 &&
      (evaluacionDesempenio?.valoracionRiesgo === null ||
        evaluacionDesempenio?.valoracionRiesgo === undefined ||
        !evaluacionDesempenio?.valoracionRiesgo)
    ) {
      errors.push({
        name: "selectFrecuenciaBit",
        errors: [S.MSJ_INFO_REQ],
      });
    }
  }
  return errors;
}

export function validarSustitutoUno(
  sustitutoSupervisor,
  dateSustitutoSupervisor,
  causaSustitucion
) {
  let errors = [];
  if (
    sustitutoSupervisor?.idAspirante !== undefined &&
    sustitutoSupervisor?.idAspirante !== null &&
    (dateSustitutoSupervisor === undefined || !dateSustitutoSupervisor)
  ) {
    errors.push({
      name: `fechaSustCapacitador-${sustitutoSupervisor?.folioPrincipal}`,
      errors: [S.MSJ_INFO_REQ],
    });
  }
  if (
    sustitutoSupervisor?.idAspirante !== undefined &&
    sustitutoSupervisor?.idAspirante !== null &&
    dateSustitutoSupervisor !== undefined &&
    dateSustitutoSupervisor !== null
  ) {
    //fecha de alta del sustituto del SE respecto de la fecha de baja del sustituido
    let dateBajaSustituido = moment(causaSustitucion?.fechaBaja, dateFormat)
      .toDate()
      .getTime();
    let dateAltaSustSE = moment(dateSustitutoSupervisor, dateFormat)
      .toDate()
      .getTime();
    if (dateAltaSustSE < dateBajaSustituido) {
      errors.push({
        name: `fechaSustCapacitador-${sustitutoSupervisor?.folioPrincipal}`,
        errors: [S.MSJ_FECHAS_SUT_SE_BAJA],
      });
    }
  }
  return errors;
}

export function validarSustitutoDos(
  sustitutoCapacitador,
  dateSustitutoCapacitador,
  causaSustitucion
) {
  let errors = [];
  if (
    sustitutoCapacitador?.idAspirante !== undefined &&
    sustitutoCapacitador?.idAspirante !== null &&
    (dateSustitutoCapacitador === undefined || !dateSustitutoCapacitador)
  ) {
    errors.push({
      name: `fechaSustCapacitador-${sustitutoCapacitador?.folioPrincipal}`,
      errors: [S.MSJ_INFO_REQ],
    });
  }

  if (
    sustitutoCapacitador?.idAspirante !== undefined &&
    sustitutoCapacitador?.idAspirante !== null &&
    dateSustitutoCapacitador !== undefined &&
    dateSustitutoCapacitador !== null
  ) {
    //fecha de alta del sustituto del CAE respecto de la fecha de baja del sustituido
    let dateBajaSustituido = moment(causaSustitucion?.fechaBaja, dateFormat)
      .toDate()
      .getTime();
    let dateAltaSustCAE = moment(dateSustitutoCapacitador, dateFormat)
      .toDate()
      .getTime();

    if (dateAltaSustCAE < dateBajaSustituido) {
      errors.push({
        name: `fechaSustCapacitador-${sustitutoCapacitador?.folioPrincipal}`,
        errors: [S.MSJ_FECHAS_SUT_CAE_BAJA],
      });
    }
  }

  return errors;
}

export function validarFechasSustitutos(
  sustitutoCapacitador,
  sustitutoSupervisor,
  dateSustitutoSupervisor,
  dateSustitutoCapacitador
) {
  let errors = [];

  if (
    sustitutoCapacitador?.idAspirante !== undefined &&
    sustitutoCapacitador?.idAspirante !== null &&
    sustitutoSupervisor?.idAspirante !== undefined &&
    sustitutoSupervisor?.idAspirante !== null &&
    dateSustitutoSupervisor !== undefined &&
    dateSustitutoSupervisor !== null &&
    dateSustitutoCapacitador !== undefined &&
    dateSustitutoCapacitador !== null
  ) {
    //Sutitución de un SE: sustitución de un CAE que sustituye a un SE
    let dateAltaSustSE = moment(dateSustitutoSupervisor, dateFormat)
      .toDate()
      .getTime();
    let dateAltaSustCAE = moment(dateSustitutoCapacitador, dateFormat)
      .toDate()
      .getTime();

    if (dateAltaSustCAE < dateAltaSustSE) {
      errors.push({
        name: `fechaSustCapacitador-${sustitutoCapacitador?.folioPrincipal}`,
        errors: [S.MSJ_FECHAS_SUT_SE_CAE],
      });
    }
  }
  return errors;
}
