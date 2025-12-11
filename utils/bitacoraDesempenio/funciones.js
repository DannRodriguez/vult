import apiClient from "../apiClient";
import { CODE_SUCCESS } from '../../utils/constantes';
import { apiClientPost } from '../../utils/apiClient';

export function guardarBitacoraDesemp(request) {
  return new Promise((resolve, reject) => {
    apiClient
      .post("guardarBitacoraDesemp", request, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      })
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        console.error("ERROR -guardarBitacoraDesemp: ", error);
        reject(new Error(error));
      });
  });
}

export function validarFormatoExpediente(tipoFormato, nombreArchivo) {
  return (
    formatosPermitidos.has(tipoFormato) ||
    extensionesPermitidas.some((ext) =>
      nombreArchivo.toLowerCase().endsWith(ext)
    )
  );
}
const formatosPermitidos = new Set([
  "image/gif",
  "image/png",
  "image/jpeg",
  "application/zip",
  "application/vnd.rar",
  "application/pdf",
  "application/msword",
  "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
  "application/vnd.ms-excel",
  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  "application/vnd.ms-powerpoint",
  "application/vnd.openxmlformats-officedocument.presentationml.presentation",
]);

const extensionesPermitidas = [".msg", ".rar", ".zip"];

export function getDimensionArchivo(fileSize) {
  let dimensionArchivo = "";

  if (fileSize < 1048576) {
    //menor a 1 MB
    let size = fileSize / 1024;
    size = Number.parseFloat(size).toFixed(2);
    dimensionArchivo = " - " + size + " KB";
  } else {
    //más de 1 MB
    let size = fileSize / 1024 / 1024;
    size = Number.parseFloat(size).toFixed(2);
    dimensionArchivo = " - " + size + " MB";
  }

  return dimensionArchivo;
}

export async function descargaExpediente(request, nombreArchivo) {
  try {

    const data = await apiClientPost("obtenerExpedienteB64", request);

    if (data.code !== CODE_SUCCESS) {
      return false;
    }

    let expedienteB64 = data.data.body;

    if (!expedienteB64) {
      return false;
    }
    const linkSource = expedienteB64;
    const downloadLink = document.createElement("a");
    const fileName = nombreArchivo;
    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
    return true;

  } catch (error) {

    console.error("Error al descargar expediente:", error);
    return false;
  }
}
