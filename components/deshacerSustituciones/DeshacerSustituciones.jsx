import { useState, useEffect } from "react";
import { useSelector } from "react-redux";

import AuthenticatedComponent from "../AuthenticatedComponent";
import Template from "../interfaz/Template";
import * as Constantes from "../../utils/constantes";
import { Layout, Table, Button } from "antd";
import Miga from "../commonComponents/Miga";
import HeaderModulo from "../commonComponents/HeaderModulo";
import { Loader } from "../interfaz/Loader";
import { useNavigate } from "react-router-dom";
import { CMenuAcciones } from "../interfaz/navbar/CMenuAcciones.jsx";
import { apiClientPost } from "../../utils/apiClient";
import * as B from "../../utils/deshacerSustituciones/etiquetas";
import VCustomModal from "../commonComponents/VCustomModal.jsx";

export default function DeshacerSustituciones() {
  const [vistaActual, setVistaActual] = useState(Constantes.FLUJO_CAPTURA);
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState([]);
  const [dataConsulta, setDataConsulta] = useState([]);

  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  const navigate = useNavigate();
  const menuAcciones = useSelector((store) => store.menu.menuAcciones);

  let noFila = 1;

  let noFilaConsulta = 1;

  const menu = useSelector((store) => store.menu);

  const user = useSelector((store) => store.loginUser.currentUser);

  useEffect(() => {
    let data = {
      idProcesoElectoral: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
    };
    setLoading(true);

    apiClientPost("consultaDeshacerSustitucion", data)
      .then((data) => {
        if (data.code == Constantes.CODE_SUCCESS) {
          setData(data.data);
        } else {
          mostrarModal(data.message, 2);
        }
        setLoading(false);
      })
      .catch((error) => {
        console.error(B.ETQ_ERROR_CONSULTAR_SUSTITUCIONES, error);
        mostrarModal(error, 2);
        setLoading(false);
      });
  }, []);

  useEffect(() => {
    noFilaConsulta = 1;
    if (vistaActual == Constantes.FLUJO_CONSULTA) {
      let data = {
        idProcesoElectoral: menu.proceso.idProcesoElectoral,
        idDetalleProceso: menu.proceso.idDetalleProceso,
        idParticipacion: menu.idParticipacion,
      };
      setLoading(true);
      apiClientPost("consultaSustitucionesDeshechas", data)
        .then((data) => {
          if (data.code == Constantes.CODE_SUCCESS) {
            setDataConsulta(data.data);
          } else {
            mostrarModal(data.message, 2);
          }
          setLoading(false);
        })
        .catch((error) => {
          console.error(B.ETQ_ERROR_CONSULTAR_SUSTITUCIONES_DESECHAS, error);
          mostrarModal(error, 2);
          setLoading(false);
        });
    }
  }, [vistaActual]);

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

  function mandaDeshacerSustitucion(sustitucionADeshacer) {
    let data = {
      idProcesoElectoral: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      user: user.username,
      sustitucionADeshacer,
    };
    setLoading(true);
    apiClientPost("deshacerSustitucion", data)
      .then((data) => {
        if (data.code == Constantes.CODE_SUCCESS) {
          mostrarModal(B.ETQ_EXITO_GUARDAR_DESHACER, 1);
        } else {
          mostrarModal(data.causa?.trim() || data.message, 2);
        }
        setLoading(false);
      })
      .catch((error) => {
        console.error(B.ETQ_ERROR_DESHACER_SUSTITUCION, error);
        mostrarModal(error, 2);
        setLoading(false);
      });
  }

  const sharedOnCell = (record, index) => {
    const list = vistaActual == Constantes.FLUJO_CONSULTA ? dataConsulta : data;
    const curr = list[index];
    const next = list[index + 1];
    const prev = list[index - 1];

    if (!curr) {
      return {};
    }

    if (
      next &&
      curr.id_relacion_sustituciones === next.id_relacion_sustituciones
    ) {
      return { rowSpan: 2 };
    }

    if (
      prev &&
      curr.id_relacion_sustituciones === prev.id_relacion_sustituciones
    ) {
      return { rowSpan: 0 };
    }

    return {};
  };

  const devuelveNumero = (index) => {
    const isConsulta = vistaActual == Constantes.FLUJO_CONSULTA;
    const list = isConsulta ? dataConsulta : data;
    let counter = isConsulta ? noFilaConsulta : noFila;

    const mismoIdSust = (i, j) =>
      list[i] &&
      list[j] &&
      list[i].id_relacion_sustituciones === list[j].id_relacion_sustituciones;

    if (mismoIdSust(index, index + 1)) {
      const result = counter;
      if (isConsulta) noFilaConsulta = counter + 1;
      else noFila = counter + 1;
      return result;
    }

    if (mismoIdSust(index, index - 1)) {
      return counter;
    }

    const result = counter;
    if (isConsulta) noFilaConsulta = counter + 1;
    else noFila = counter + 1;
    return result;
  };

  const baseColumns = [
    {
      title: B.ETQ_COLUMNA_NO,
      dataIndex: B.ETQ_COLUMNA_NO_ID,
      key: B.ETQ_COLUMNA_NO_ID,
      onCell: sharedOnCell,
      render: (text, record, index) => <>{devuelveNumero(index)}</>,
    },
    {
      title: B.ETQ_COLUMNA_CARGO,
      dataIndex: B.ETQ_COLUMNA_CARGO_ID,
      key: B.ETQ_COLUMNA_CARGO_KEY,
    },
    {
      title: B.ETQ_COLUMNA_NOMBRE_SUSTITUIDO,
      dataIndex: B.ETQ_COLUMNA_NOMBRE_SUSTITUIDO_ID,
      key: B.ETQ_COLUMNA_NOMBRE_SUSTITUIDO_ID,
    },
    {
      title: B.ETQ_COLUMNA_CAUSA,
      dataIndex: B.ETQ_COLUMNA_CAUSA_ID,
      key: B.ETQ_COLUMNA_CAUSA_ID,
    },
    {
      title: B.ETQ_COLUMNA_FECHA_BAJA,
      dataIndex: B.ETQ_COLUMNA_FECHA_BAJA_ID,
      key: B.ETQ_COLUMNA_FECHA_BAJA_ID,
    },
    {
      title: B.ETQ_COLUMNA_NOMBRE_SUSTITUTO,
      dataIndex: B.ETQ_COLUMNA_NOMBRE_SUSTITUTO_ID,
      key: B.ETQ_COLUMNA_NOMBRE_SUSTITUTO_ID,
      render: (text, record) => (
        <>
          {record.id_aspirante_sustituto
            ? record.nombre_sustituto
            : B.ETQ_COLUMNA_SIN_ASIGNAR}
        </>
      ),
    },
    {
      title: B.ETQ_COLUMNA_FECHA_ALTA,
      dataIndex: B.ETQ_COLUMNA_FECHA_ALTA_ID,
      key: B.ETQ_COLUMNA_FECHA_ALTA_ID,
      render: (text, record) => (
        <>
          {record.id_aspirante_sustituto ? record.fecha_alta : B.ETQ_COLUMNA_NA}
        </>
      ),
    },
    {
      title: B.ETQ_COLUMNA_PUESTO,
      dataIndex: B.ETQ_COLUMNA_PUESTO_ID,
      key: B.ETQ_COLUMNA_PUESTO_ID,
      render: (text, record) => (
        <>
          {record.id_aspirante_sustituto
            ? record.puesto_sustituto
            : B.ETQ_COLUMNA_NA}
        </>
      ),
    },
  ];

  const columns =
    vistaActual === Constantes.FLUJO_CAPTURA
      ? [
          ...baseColumns,
          {
            title: B.ETQ_COLUMNA_RELACION,
            dataIndex: B.ETQ_COLUMNA_RELACION_ID,
            key: B.ETQ_COLUMNA_RELACION_ID,
            render: (text, record) => (
              <Button
                disabled={record?.existEnSustPosteriores}
                className={
                  record?.existEnSustPosteriores
                    ? "btnDeshDisabled"
                    : "btnDeshacer"
                }
                onClick={() => mandaDeshacerSustitucion(record)}
              >
                Deshacer
              </Button>
            ),
            onCell: sharedOnCell,
          },
        ]
      : baseColumns;

  function actualizaDatos(pagination) {
    if (pagination?.current == 1) {
      noFila = 1;
      noFilaConsulta = 1;
    }
  }

  const getDataConsulta =
    vistaActual === Constantes.FLUJO_CAPTURA ? data : dataConsulta;

  const vistaRenderizada = () => (
    <Layout id="content-bitacora">
      <Miga />
      <br />
      <HeaderModulo />
      <br />
      <Table
        key={vistaActual}
        className="tableDesSust"
        columns={columns}
        pagination={false}
        dataSource={getDataConsulta}
        bordered
        onChange={actualizaDatos}
      />
    </Layout>
  );

  return (
    <AuthenticatedComponent>
      <Loader blocking={loading} />
      <Template>
        <CMenuAcciones
          state={vistaActual}
          setState={setVistaActual}
          menuAcciones={menuAcciones}
        />
        {vistaRenderizada()}

        <VCustomModal
          title={null}
          mensaje={msjModal?.split("\n")?.map((linea, idx) => (
            <p key={idx} style={{ margin: 0 }}>
              {linea}
            </p>
          ))}
          footer={null}
          open={openModal}
          tipoModal={tipoModal}
          cerrarModal={cerrarModal}
        />
      </Template>
    </AuthenticatedComponent>
  );
}
