export const generarDatosSustitucion = (
  aspiranteSustituido,
  aspiranteSustituto,
  fechaAlta,
  fechaBaja,
  causaSustitucion,
  user,
  menu,
  imgB64,
  extensionArchivo,
  correoCuenta
) => {
  return {
    idProcesoElectoral: menu.proceso.idProcesoElectoral,
    idDetalleProceso: menu.proceso.idDetalleProceso,
    idParticipacion: menu.idParticipacion,
    idAspiranteSustituido: aspiranteSustituido.idAspirante,
    idCausaVacante: causaSustitucion.idCausaVacante,
    tipoCausaVacante: causaSustitucion.tipoCausaVacante,
    observaciones: causaSustitucion.observaciones
      ? causaSustitucion.observaciones
      : "",
    fechaBaja: fechaBaja,
    fechaAlta: fechaAlta,
    idAspiranteSustituto: aspiranteSustituto?.idAspirante,
    usuario: user.username,
    imgB64: imgB64,
    extensionArchivo: extensionArchivo,
    correoCuenta: correoCuenta,
  };
};

export const generarDatosSustitucionPendiente = (
  infoSustitucion,
  aspiranteSustituto,
  fechaAlta,
  causaSustitucion,
  user,
  imgB64,
  extensionArchivo,
  correoCuenta
) => {
  return {
    idProcesoElectoral: infoSustitucion.idProcesoElectoral,
    idDetalleProceso: infoSustitucion.id.idDetalleProceso,
    idParticipacion: infoSustitucion.id.idParticipacion,
    idSustitucion: infoSustitucion.id.idSustitucion,
    idAspiranteSustituido: infoSustitucion.idAspiranteSutituido,
    tipoCausaVacante: causaSustitucion.tipoCausaVacante,
    fechaAlta: fechaAlta,
    idAspiranteSustituto: aspiranteSustituto?.idAspirante,
    usuario: user.username,
    imgB64: imgB64,
    extensionArchivo: extensionArchivo,
    correoCuenta: correoCuenta,
  };
};
