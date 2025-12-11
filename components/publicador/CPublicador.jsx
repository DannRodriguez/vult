import React, { useEffect, useState, useRef, useCallback } from "react";
import { connect } from "react-redux";
import { createStructuredSelector } from "reselect";
import {
  ACCION_CONSULTA,
  BITACORA_ACCION_T_CEDULA,
  BITACORA_ACCION_T_LISTADO,
  ID_SISTEMA,
} from "../../utils/constantes";
import {
  obtieneCatalogosReporte,
  obtieneDatosTabla,
  consultaRestringida,
  enviarBitacoraAccion,
  enviarBitacoraAccionWS,
} from "../../utils/publicador/funciones";
import AuthenticatedComponent from "../AuthenticatedComponent";
import Template from "../interfaz/Template";
import VReporte from "./VReporte";
import VHeader from "./VHeader";
import * as etiquetas from "../../utils/publicador/etiquetas";
import { Button, Layout, Row, Divider, Modal } from "antd";
import "../../css/publicador.scss";
import { useSelector } from "react-redux/es/hooks/useSelector";
import * as constante from "../../utils/constantes";
import { Loader } from "../interfaz/Loader.jsx";

function CPublicador() {
  const isMounted = useRef(true);
  const user = useSelector((store) => store.loginUser.currentUser);
  const proceso = useSelector((store) => store.menu.proceso);
  const idParticipacion = useSelector((store) => store.menu.idParticipacion);
  const moduloSeleccionado = useSelector(
    (store) => store.menu.moduloSeleccionado
  );
  const estado = useSelector((store) => store.menu.estado);
  const distrito = useSelector((store) => store.menu.distrito);

  const [catalogosListados, setCatalogosListados] = useState(undefined);
  const [catalogosCedulas, setCatalogosCedulas] = useState(undefined);
  const [tipoReporte, setTipoReporte] = useState("");
  const [corte, setCorte] = useState("");
  const [listaReportes, setListaReportes] = useState([]);
  const [reporteSeleccionado, setReporteSeleccionado] = useState(undefined);
  const [nivelSeleccionado, setNivelSeleccionado] = useState(undefined);
  const [cargando, setCargando] = useState(false);
  const [vistasTemporales, setVistasTemporales] = useState([]);
  const [datosTabla, setDatosTabla] = useState(undefined);
  const [datosFiltrados, setDatosFiltrados] = useState(undefined);
  const [isOpenCotas, setIsOpenCotas] = useState(false);
  const [error, setError] = useState("");

  const [nivel, setNivel] = useState();

  const [municipioActual, setMunicipioActual] = useState({
    idMunicipio: 0,
    nombreMunicipio: "",
  });
  const [isVistaTemporal, setIsVistaTemporal] = useState(false);

  useEffect(() => {
    if (!proceso) {
      setCargando(false);
      setError(etiquetas.SELECCIONA_PROCESO);
      return;
    }
    setReporteSeleccionado(undefined);
    setDatosTabla(undefined);
    setNivelSeleccionado(undefined);
    setDatosFiltrados(undefined);
    const fetchData = async () => {
      const catalogosListadosData = await obtieneCatalogosReporte(
        proceso.idProcesoElectoral,
        etiquetas.FOLDER_LISTADOS
      );
      const catalogosCedulasData = await obtieneCatalogosReporte(
        proceso.idProcesoElectoral,
        etiquetas.FOLDER_CEDULAS
      );
      setCatalogosListados(catalogosListadosData);
      setCatalogosCedulas(catalogosCedulasData);
      seleccionaTipoReporte(catalogosListadosData, catalogosCedulasData);
    };
    fetchData();
  }, [moduloSeleccionado]);

  useEffect(() => {
    if (moduloSeleccionado) {
      seleccionaTipoReporte();
    }
    if (proceso || estado || distrito) {
      setCargando(true);
      obtieneDatosTabla1();
    }
  }, [proceso, estado, distrito]);

  const seleccionaTipoReporte = (catalogosListados, catalogosCedulas) => {
    let catalogosN;
    let tipoReporteN;

    if (constante.ID_MODULO_LISTADOS == moduloSeleccionado.idModulo) {
      catalogosN = catalogosListados;
      tipoReporteN = etiquetas.FOLDER_LISTADOS;
    } else {
      catalogosN = catalogosCedulas;
      tipoReporteN = etiquetas.FOLDER_CEDULAS;
    }
    setListaReportes(catalogosN?.listaReportes);
    setTipoReporte(tipoReporteN);
    setCorte(catalogosN?.corte);
  };

  const seleccionaReporte = (reporteSeleccionado) => {
    setDatosTabla(undefined);
    setDatosFiltrados(undefined);
    setReporteSeleccionado(reporteSeleccionado);
    setNivelSeleccionado(undefined);
    setCargando(true);
    obtieneDatosTabla1(reporteSeleccionado, undefined);
  };

  const seleccionaNivelReporte = (nivelSeleccionado) => {
    setNivelSeleccionado(nivelSeleccionado);
    setCargando(true);
    obtieneDatosTabla1(reporteSeleccionado, nivelSeleccionado);
  };

  const consumeBitacoraAccion = async (
    reporteSeleccionadoParam,
    nivelSeleccionadoParam
  ) => {
    try {
      const reporte = reporteSeleccionadoParam
        ? listaReportes[reporteSeleccionadoParam]
        : listaReportes[reporteSeleccionado];
      const nivel =
        reporteSeleccionado &&
          nivelSeleccionadoParam &&
          listaReportes[reporteSeleccionado].niveles[nivelSeleccionadoParam]
          ? listaReportes[reporteSeleccionado].niveles[nivelSeleccionadoParam]
          : { label: "", key: nivelSeleccionadoParam };

      if (
        tipoReporte === etiquetas.FOLDER_LISTADOS ||
        (tipoReporte === etiquetas.FOLDER_CEDULAS && nivel.idModulo)
      ) {
        const requestBitacora = {
          idProcesoElectoral: proceso.idProcesoElectoral,
          idDetalleProceso: proceso.idDetalleProceso,
          idSistema: ID_SISTEMA,
          nombreUsuarioConsulta: user.username,
          idEstadoUsuarioConsulta: user.idEstado,
          idDistritoUsuarioConsulta: user.idDistrito,
          rolUsuario: user.rolUsuario,
          idModulo: nivel.idModulo
            ? nivel.idModulo
            : reporte.niveles.nivelProceso.idModulo,
          nombreReporte: reporte.label,
          tipoReporte: tipoReporte === etiquetas.FOLDER_CEDULAS ? "C" : "L",
          nivelGeografico: nivel.key
            ? nivel.key
            : reporte.niveles.nivelProceso.key,
          tipoFormato: "H",
          idEstado: estado.idEstado,
          nombreEstado: estado.nombreEstado,
          idDistritoFederal: distrito.idDistrito,
          nombreDistritoFederal: distrito.nombreDistrito,
          idDistritoLocal: 0,
          nombreDistritoLocal: "",
          idMunicipio: 0,
          nombreMunicipio: "",
          idZonaResponsabilidad: 0,
          numeroZonaResponsabilidad: 0,
          idAreaResponsabilidad: 0,
          numeroAreaResponsabilidad: 0,
          votoMexResExt: "N",
        };
        enviarBitacoraAccionWS(requestBitacora);
      }
    } catch (error) {
      console.log("ocurrió un error en el consumo de la bitacora WS :" + error);
    }
  };

  const obtieneDatosTabla1 = async (reporteSeleccionado, nivelSeleccionado) => {
    if (!isMounted.current) return;

    if (!proceso) {
      setCargando(false);
      setError(etiquetas.SELECCIONA_PROCESO);
      return;
    }

    if (
      !reporteSeleccionado ||
      (!nivelSeleccionado && tipoReporte === etiquetas.FOLDER_CEDULAS)
    ) {
      setCargando(false);
      return;
    }

    if (tipoReporte === etiquetas.FOLDER_LISTADOS && !estado?.idEstado) {
      setCargando(false);
      setError(etiquetas.SELECCIONA_ESTADO);
      return;
    }

    let nivelActual = nivelSeleccionado;
    let idDetalleActual = proceso.idDetalleProceso;
    let idEstadoActual = estado.idEstado;
    let idDistritoActual = distrito != undefined ? distrito.idDistrito : 0;
    let idMunicipioActual = 0;
    if (vistasTemporales.length > 0) {
      const vistaActual = vistasTemporales[vistasTemporales.length - 1];
      nivelActual = vistaActual.nivel;
      idDetalleActual = vistaActual.proceso.idDetalleProceso;
      idEstadoActual = vistaActual.estado.idEstado;
      idDistritoActual = vistaActual.distrito.idDistrito;
      idMunicipioActual = vistaActual.municipio.idMunicipio;
    }

    const datosTabla = await obtieneDatosTabla(
      proceso.idProcesoElectoral,
      idDetalleActual,
      tipoReporte,
      corte,
      reporteSeleccionado,
      nivelActual,
      idEstadoActual,
      idDistritoActual,
      idMunicipioActual,
      consultaRestringida(user.rolUsuario)
    );
    if (datosTabla.msg) {
      setCargando(false);
      setError(datosTabla.msg);
    } else {
      setDatosTabla(datosTabla.data);
      setNivel(
        reporteSeleccionado &&
          nivelActual &&
          listaReportes[reporteSeleccionado].niveles[nivelActual]
          ? listaReportes[reporteSeleccionado].niveles[nivelActual]
          : { label: "", key: nivelActual }
      );
      setCargando(false);
    }
  };

  const handleChangeTabla = (pagination, filters, sorter, extra) => {
    setDatosFiltrados(extra.currentDataSource);
  };

  const handleChangeCotas = () => {
    setIsOpenCotas((prevIsOpenCotas) => !prevIsOpenCotas);
  };

  const handleLinkToNivel = (
    nivelActual,
    procesoActual,
    estadoActual,
    distritoActual,
    municipio
  ) => {
    const vistasTemporalesNew = vistasTemporales.slice();

    vistasTemporalesNew.push({
      nivel: nivelActual,
      proceso: procesoActual ? procesoActual : proceso,
      estado: estadoActual ? estadoActual : estado,
      distrito: distritoActual ? distritoActual : distrito,
      municipio,
    });

    setVistasTemporales(vistasTemporalesNew);
    setCargando(true);
    obtieneDatosTabla1();
  };

  const handleReturn = () => {
    const vistasTemporalesNew = vistasTemporales.slice();
    vistasTemporalesNew.pop();

    setVistasTemporales(vistasTemporalesNew);
    setCargando(true);
    obtieneDatosTabla1();
  };

  return (
    <AuthenticatedComponent>
      <Template>
        <Loader blocking={cargando} />
        <div className="publicador-container">
          <VHeader
            tipoReporte={tipoReporte}
            reporte={reporteSeleccionado ? listaReportes[reporteSeleccionado] : ""}
            nivelSeleccionado={nivelSeleccionado}
            corte={corte}
            listaReportes={listaReportes}
            seleccionaReporte={seleccionaReporte}
            seleccionaNivelReporte={seleccionaNivelReporte}
            datosTabla={datosTabla}
            isOpenCotas={isOpenCotas}
            handleChangeCotas={handleChangeCotas}
          />
          <div className="pc-module-data">
            <VReporte
              tipoReporte={tipoReporte}
              reporte={
                reporteSeleccionado ? listaReportes[reporteSeleccionado] : ""
              }
              nivel={nivel}
              proceso={proceso}
              estado={estado}
              distrito={distrito}
              municipio={municipioActual}
              error={error}
              datosTabla={datosTabla}
              datosFiltrados={datosFiltrados}
              isVistaTemporal={isVistaTemporal}
              handleChangeTabla={handleChangeTabla}
              handleLinkToNivel={handleLinkToNivel}
              handleReturn={handleReturn}
              isOpenCotas={isOpenCotas}
              handleChangeCotas={handleChangeCotas}
            />
          </div>
        </div>
      </Template>
    </AuthenticatedComponent>
  );
}

export default CPublicador;
