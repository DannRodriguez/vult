import React from "react";
import jspdf from "jspdf";
import "jspdf-autotable";
import logoINE from "../../img/logo_INE.png";
import * as etiquetas from "./etiquetas";
import * as infoSistema from "../constantes";
import * as roles from "../roles/roles";
import axios from "axios";
import apiClient from "../apiClient";

export const obtieneCatalogosReporte = async (idProceso, tipoReporte) => {
  const emptyData = {
    corte: "",
    listaReportes: [],
  };

  if (!tipoReporte) return emptyData;

  const catalogoFile =
    tipoReporte === etiquetas.FOLDER_CEDULAS
      ? etiquetas.FILE_CCEDULAS
      : etiquetas.FILE_CCLISTADOS;
  const catalogoNivelFile =
    tipoReporte === etiquetas.FOLDER_CEDULAS
      ? etiquetas.FILE_CCEDULAS_NIVEL
      : etiquetas.FILE_CCLISTADOS_NIVEL;

  const rutaFolder = `${etiquetas.RUTA_SERVIDOR}/${idProceso}/${etiquetas.RUTA_SERVIDOR_COMP}/${tipoReporte}`;
  const rutaCatalogos = `${rutaFolder}/${etiquetas.FOLDER_CATALOGOS}/${etiquetas.FOLDER_VOTO_NACIONAL}`;

  try {
    const corte = await (
      await fetch(`${rutaFolder}/${etiquetas.FILE_CORTE_JSON}`)
    ).json();
    const listaReportesJson = await (
      await fetch(`${rutaCatalogos}/${catalogoFile}`)
    ).json();
    const listaNivelesJson = await (
      await fetch(`${rutaCatalogos}/${catalogoNivelFile}`)
    ).json();
    const listaNiveles = listaNivelesJson.reduce((accumulator, nivel) => {
      accumulator[nivel.idCedula] = nivel.niveles.items.reduce((acc, obj) => {
        acc[obj.key] = obj;
        return acc;
      }, {});
      return accumulator;
    }, {});
    const listaReportes = listaReportesJson[0].items.reduce(
      (accumulator, cedula) => {
        accumulator[cedula.key] = {
          key: cedula.key,
          label: cedula.label,
          niveles: listaNiveles[cedula.key],
        };
        return accumulator;
      },
      {}
    );

    return {
      corte,
      listaReportes,
    };
  } catch (error) {
    console.log("Error al obtener los catálogos de reportes: ", error);
  }

  return emptyData;
};

export const obtieneDatosTabla = async (
  idProceso,
  idDetalle,
  tipoReporte,
  corte,
  reporteSeleccionado,
  nivelSeleccionado,
  idEstado,
  idDistrito,
  idMunicipio,
  restringido
) => {
  const rutaFolder = `${etiquetas.RUTA_SERVIDOR}/${idProceso}/${etiquetas.RUTA_SERVIDOR_COMP
    }/${tipoReporte}/${corte.corte}${restringido && tipoReporte === etiquetas.FOLDER_LISTADOS
      ? etiquetas.FOLDER_CORTE_RESTRINGIDO
      : ""
    }/${etiquetas.FOLDER_VOTO_NACIONAL}/${reporteSeleccionado}/`;
  let rutaFile = rutaFolder;

  if (tipoReporte === etiquetas.FOLDER_CEDULAS) {
    switch (nivelSeleccionado) {
      case etiquetas.NIVEL_PROCESO:
        rutaFile += etiquetas.FILE_OC;
        break;
      case etiquetas.NIVEL_DISTRITOS_TOTALES:
        rutaFile += etiquetas.FILE_OC300CT;
        break;
      case etiquetas.NIVEL_DISTRITOS_SIN_TOTALES:
        rutaFile += etiquetas.FILE_OC300ST;
        break;
      case etiquetas.NIVEL_ENTIDAD:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FILE_JL}`;
        break;
      case etiquetas.NIVEL_LOCAL_SECCION:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FILE_JL_DESGLOSE}`;
        break;
      case etiquetas.NIVEL_DISTRITO_CONV:
        if (!idEstado || !idDistrito) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO_DISTRITO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FILE_JL_DESGLOSE_CONV}`;
        break;
      case etiquetas.NIVEL_DISTRITO_SEDE:
        if (!idEstado || !idDistrito) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO_DISTRITO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FILE_JL_DESGLOSE_SEDE}`;
        break;
      case etiquetas.NIVEL_DISTRITO:
        if (!idEstado || !idDistrito) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO_DISTRITO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FOLDER_DISTRITO}${idDistrito}/${etiquetas.FILE_JDF}`;
        break;
      case etiquetas.NIVEL_LOCAL:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_LOCAL}/${etiquetas.FILE_OPLE}`;
        break;
      case etiquetas.NIVEL_LOCAL_DESGLOSE:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_LOCAL}/${etiquetas.FILE_JDL}`;
        break;
      case etiquetas.NIVEL_MUNICIPAL:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_MUNICIPAL}/${etiquetas.FILE_MUN}`;
        break;
      case etiquetas.NIVEL_MUNICIPAL_DESGLOSE:
        if (!idEstado) {
          return {
            msg: etiquetas.SELECCIONA_ESTADO,
            data: undefined,
          };
        }
        rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_MUNICIPAL}/${etiquetas.FILE_MUN_DESGLOSE}`;
        break;

      default:
        return {
          msg: etiquetas.ERROR_REPORTE_NO_ENCONTRADO,
          data: undefined,
        };
    }
  } else {
    if (!idEstado) {
      return {
        msg: etiquetas.SELECCIONA_ESTADO,
        data: undefined,
      };
    }
    rutaFile += `${idDetalle}/${etiquetas.FOLDER_ESTADO}${idEstado}/${etiquetas.FOLDER_FEDERAL}/${etiquetas.FILE_JL}`;
  }

  try {
    const data = (await (await fetch(rutaFile)).json())[0];

    if (
      (tipoReporte === etiquetas.FOLDER_LISTADOS && idDistrito) ||
      (tipoReporte === etiquetas.FOLDER_CEDULAS &&
        (nivelSeleccionado === etiquetas.NIVEL_LOCAL_DESGLOSE ||
          nivelSeleccionado === etiquetas.NIVEL_DISTRITO_CONV ||
          nivelSeleccionado === etiquetas.NIVEL_DISTRITO_SEDE))
    ) {
      data["datos"] = data["datos"].filter((fila) => {
        return fila["idDistrito"] == idDistrito;
      });
    }

    if (
      tipoReporte === etiquetas.FOLDER_CEDULAS &&
      nivelSeleccionado === etiquetas.NIVEL_MUNICIPAL_DESGLOSE
    ) {
      data["datos"] = data["datos"].filter((fila) => {
        return fila["idMunicipio"] == idMunicipio;
      });
    }

    return {
      msg: "",
      data,
    };
  } catch (error) {
    console.log(
      "Error al obtener la información del reporte: ",
      error,
      rutaFile
    );
    return {
      msg: etiquetas.ERROR_OBTENER_REPORTE,
      data: undefined,
    };
  }
};

export const obtieneEtiquetaNivel = (estado, distrito, municipio, nivel) => {
  const distritoLabel = `${distrito?.idDistrito}- ${distrito?.nombreDistrito}`;
  const municipioLabel = `${municipio.idMunicipio}- ${municipio.nombreMunicipio}`;

  switch (nivel.key) {
    case etiquetas.NIVEL_PROCESO:
    case etiquetas.NIVEL_DISTRITOS_TOTALES:
    case etiquetas.NIVEL_DISTRITOS_SIN_TOTALES:
      return nivel.label;
    case etiquetas.NIVEL_ENTIDAD:
    case etiquetas.NIVEL_LOCAL_SECCION:
    case etiquetas.NIVEL_LOCAL:
    case etiquetas.NIVEL_MUNICIPAL:
      return `${nivel.label} ${nivel.label.length > 0 ? "," : ""} ${estado.nombreEstado
        }`;
    case etiquetas.NIVEL_DISTRITO_CONV:
    case etiquetas.NIVEL_DISTRITO_SEDE:
    case etiquetas.NIVEL_DISTRITO:
      return `${nivel.label} ${nivel.label.length > 0 ? "," : ""} ${estado.nombreEstado
        } ${distritoLabel}`;
    case etiquetas.NIVEL_LOCAL_DESGLOSE:
      return `${etiquetas.NIVEL_LOCAL_DESGLOSE_TITLE}, ${estado.nombreEstado} ${distritoLabel}`;
    case etiquetas.NIVEL_MUNICIPAL_DESGLOSE:
      return `${etiquetas.NIVEL_MUNICIPAL_DESGLOSE_TITLE}, ${estado.nombreEstado} ${municipioLabel}`;
    default:
      return `${estado?.idEstado > 0 ? estado.nombreEstado : ""}${distrito?.idDistrito > 0 ? ", " + distritoLabel : ""
        }`;
  }
};

export const consultaRestringida = (grupo) => {
  return roles.ROLES_CONSULTA_RESTRINGIDA[grupo];
};

export const sortNumber = (key) => {
  return (a, b) => {
    const valA = parseFloat(a[key]) || 0;
    const valB = parseFloat(b[key]) || 0;
    return valA - valB;
  };
};

export const sortText = (key) => {
  return (a, b) => {
    const valA = a[key] || '';
    const valB = b[key] || '';
    return valA.toString().localeCompare(valB.toString());
  };
};

export const sortDate = (key) => {
  return (a, b) => {
    const dateA = parseStringToDate(a[key]);
    const dateB = parseStringToDate(b[key]);
    return dateA - dateB;
  };
};

export const parseStringToDate = (date) => {
  if (!date || date === '' || date === 'TOTAL') {
    return new Date(0);
  }

  const parts = date.split("/");
  try {
    return new Date(parts[2], parts[1] - 1, parts[0]);
  } catch (error) {
    console.log("Error al convertir string to date: ", error);
    return new Date(0);
  }
};

export const removeTimeFromMoment = (moment) => {
  try {
    return moment.format("DD/MM/YYYY");
  } catch (error) {
    console.log("Error al eliminar el tiempo de la fecha: ", error);
    return undefined;
  }
};

export const filterCombo = (key) => {
  return (value, record) => record[key].indexOf(value) === 0;
};

export const isRecordTotal = (record) => {
  return (
    record.descripcionDetalle === "TOTAL" ||
    record.idEstado === "TOTAL" ||
    record.idDistrito === "TOTAL" ||
    record.idMunicipio === "TOTAL" ||
    record.seccion === "TOTAL" ||
    record.numeroConvocatoria === "TOTAL" ||
    record.numeroSede === "TOTAL"
  );
};

export const parseHeaderFunctions = (
  header,
  datos,
  getColumnSearchPropsTxt,
  getColumnSearchPropsDate,
  handleLinkToNivelAddProps
) => {
  if (!header) return [];

  const processColumn = (column) => {
    let columnFunctions = {
      ...column,
    };

    if (column["sorter"]) {
      switch (column["sorter"]) {
        case "sortNumber":
          columnFunctions["sorter"] = sortNumber(columnFunctions["key"]);
          break;
        case "sortText":
          columnFunctions["sorter"] = sortText(columnFunctions["key"]);
          break;
        case "sortDate":
          columnFunctions["sorter"] = sortDate(columnFunctions["key"]);
          break;
        default:
          break;
      }
    }

    if (column["filterType"]) {
      switch (column["filterType"]) {
        case "filterCombo":
          columnFunctions["onFilter"] = filterCombo(columnFunctions["key"]);
          break;
        case "filterTxt":
          columnFunctions = {
            ...columnFunctions,
            ...getColumnSearchPropsTxt(columnFunctions["key"]),
          };
          break;
        case "filterDate":
          columnFunctions = {
            ...columnFunctions,
            ...getColumnSearchPropsDate(columnFunctions["key"]),
          };
          break;
        default:
          break;
      }
    }

    if (column["link"]) {
      columnFunctions = {
        ...columnFunctions,
        render: (text, record) => {
          if (isRecordTotal(record)) {
            return text;
          }
          return (
            <div
              style={{ cursor: "pointer", color: "#D4007F" }}
              onClick={() => handleLinkToNivelAddProps(record, column["link"])}
            >
              {text}
            </div>
          );
        },
      };
    }

    if (!column["render"] && !column["link"] && column["key"] !== 'descargar') {
      columnFunctions = {
        ...columnFunctions,
        render: (text) => {

          if (text === null || text === undefined || text === '' || text === false) {
            return "-";
          }

          if (typeof text === 'string') {

            if (text.trim() === '') {
              return "-";
            }
          }
          if (typeof text === 'number' && isNaN(text) || Array.isArray(text) && text.length === 0 || typeof text === 'object' && text !== null && Object.keys(text).length === 0) {
            return "-";
          }
          return text;
        }
      };
    }

    if (column.children && Array.isArray(column.children)) {
      columnFunctions.children = column.children.map(child => processColumn(child));
    }

    return columnFunctions;
  };

  return header.map(column => processColumn(column));
};

export const obtieneNombreArchivo = (reporteKey, nivelLabel) => {
  const date = new Date()
    .toLocaleString("es-MX", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
    })
    .replaceAll("/", "_");
  return `${reporteKey}${nivelLabel ? "_" + nivelLabel.replaceAll(" ", "_") : ""
    }_${date}`;
};

export const generaPDF = (
  proceso,
  estado,
  distrito,
  municipio,
  reporte,
  nivel,
  cotas,
  headerPDF,
  datos
) => {
  var doc = new jspdf("l", "mm", "legal");
  var totalPagesExp = "{total_pages_count_string}";
  var totalPages = 0;

  const headers = headerPDF.reduce(
    (acc, header) => {
      acc.keys[header["key"]] = true;
      acc.labels.push(header["label"]);
      return acc;
    },
    { keys: {}, labels: [] }
  );

  const dataTable = datos.reduce((acc, fila) => {
    const row = [];
    for (const [key, value] of Object.entries(fila)) {
      if (headers.keys[key]) {
        if (value === null || value === undefined || value === '' || value === false) {
          row.push("-");
        } else if (typeof value === 'string' && value.trim() === '') {
          row.push("-");
        } else if (typeof value === 'number' && isNaN(value)) {
          row.push("-");
        } else {
          row.push(value);
        }
      }
    }
    acc.push(row);
    return acc;
  }, []);

  const etiquetaNivel = obtieneEtiquetaNivel(
    estado,
    distrito,
    municipio,
    nivel
  );
  const fechaHora = `Fecha de impresión: ${new Date().toLocaleString("es-MX", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  })}`;

  var pageContent = function (data) {
    doc.setFontSize(10);
    doc.setTextColor(40);
    doc.addImage(logoINE, "PNG", data.settings.margin.left, 5, 45, 15);

    doc.setFont(undefined, "bold");
    doc.text(
      reporte.label,
      data.settings.margin.left + 160 - reporte.label.length,
      25
    );
    doc.text(
      etiquetaNivel,
      data.settings.margin.left + 155 - etiquetaNivel.length,
      30
    );

    doc.setFont(undefined, "normal");
    doc.text(infoSistema.DECEYEC, data.settings.margin.left + 242, 10);
    doc.text(infoSistema.DECE, data.settings.margin.left + 275, 15);
    doc.text(infoSistema.NOMBRE_SISTEMA, data.settings.margin.left + 216, 20);
    doc.text(fechaHora, data.settings.margin.left + 265, 35);

    doc.setFontSize(6);
    var str = `${etiquetas.PAGINA} ${data.pageCount}`;
    totalPages = data.pageCount + 1;
    if (typeof doc.putTotalPages === "function") {
      str = str + " de  " + totalPagesExp;
    }
    doc.setFontSize(10);
    doc.text(str, 320, 205);

    doc.setFontSize(8);
    doc.text("", data.settings.margin.left, doc.internal.pageSize.height - 10);
  };

  doc.autoTableSetDefaults({
    headerStyles: { fillColor: [219, 70, 163] },
  });

  doc.autoTable(headers.labels, dataTable, {
    headerStyles: {
      lineWidth: 0.3,
      lineColor: [255, 255, 255],
    },
    margin: { top: 40 },
    overflow: "linebreak",
    tableWidth: 330,
    styles: {
      cellPadding: 0.5,
      fontSize: 7,
      valign: 'top',
      halign: 'left'
    },
    columnStyles: {
      contenido_soporte: { cellWidth: 'auto' },
      responsables: { cellWidth: 'auto' }
    },
    addPageContent: pageContent,
  });

  const positionsX = [20, 130, 250];
  let indexCotasX = 0;
  let indexCotasY = 10;

  if (cotas && cotas.length > 0) {
    doc.addPage();
    doc.setFontSize(7);
    for (let i = 0; i < cotas.length; i++) {
      if (indexCotasX % 1 === 0) {
        indexCotasX = 0;
        indexCotasY += 3;
      }
      doc.text(cotas[i], positionsX[indexCotasX], indexCotasY);
      indexCotasX++;
    }
  }

  if (typeof doc.putTotalPages === "function") {
    doc.putTotalPages(totalPagesExp);
  }

  if (cotas && cotas.length > 0) {
    doc.setFontSize(10);
    doc.text(`${etiquetas.PAGINA} ${totalPages} de ${totalPages}`, 320, 205);
  }

  doc.save(`${obtieneNombreArchivo(reporte.key, nivel.label)}.pdf`);
};

export const generaTxt = (headerCSV, datos, reporteKey, nivelLabel) => {
  let textFile;
  const headers = headerCSV.reduce(
    (acc, header) => {
      acc.keys[header["key"]] = true;
      acc.labels += `${header["label"]}|`;
      return acc;
    },
    { keys: {}, labels: "" }
  );

  textFile = headers.labels;

  textFile += datos.reduce((acc, fila) => {
    for (const [key, value] of Object.entries(fila)) {
      if (headers.keys[key]) {
        let valorMostrar = value;
        if (value === null || value === undefined || value === '' || value === false) {
          valorMostrar = "-";
        } else if (typeof value === 'string' && value.trim() === '') {
          valorMostrar = "-";
        } else if (typeof value === 'number' && isNaN(value)) {
          valorMostrar = "-";
        }
        acc += `${valorMostrar}|`;
      }
    }
    acc += "\n";
    return acc;
  }, "\n");

  const element = document.createElement("a");
  const file = new Blob([textFile], { type: "text/plain;charset=utf-8" });
  element.href = URL.createObjectURL(file);
  element.download = `${obtieneNombreArchivo(reporteKey, nivelLabel)}.txt`;
  document.body.appendChild(element);
  element.click();
};

export function enviarBitacoraAccion(request) {
  return new Promise((resolve, reject) => {
    apiClient
      .post("enviarBitacoraAccion", request)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        console.error("ERROR -enviarBitacoraAccion:", error);
        reject(new Error(error));
      });
  });
}

export function enviarBitacoraAccionWS(request) {
  return new Promise((resolve, reject) => {
    axios
      .post(
        `${infoSistema.URL_HOST_BITACORA}/wsBitacora/ws/registraBitacora`,
        request,
        { headers: { "Content-Type": "application/json" } }
      )
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        console.error("ERROR -enviarBitacoraAccionWS:", error);
        reject(new Error(error));
      });
  });
}
