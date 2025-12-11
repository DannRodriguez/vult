import { useState, useRef } from "react";
import {
  Form,
  Layout,
  Col,
  Row,
  Select,
  Table,
  Input,
  Tooltip,
  notification,
} from "antd";
import Template from "../interfaz/Template";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { CODE_SUCCESS } from "../../utils/constantes.js";
import { BarInfo, BarError } from "../commonComponents/AccessoriesComponents";
import FiltroListaReserva from "../commonComponents/filtroListaReserva/FiltroListaReserva";
import * as etiquetas from "../../utils/listaReserva/etiquetas";
import { DoubleRightOutlined } from "@ant-design/icons";
import "../../css/listaReservaCAE.css";
import { apiClientPost } from "../../utils/apiClient";
import { Loader } from "../interfaz/Loader.jsx";
import CBandejaAcciones from "../commonComponents/acciones/CBandejaAcciones.jsx";
import * as acciones from "../../utils/acciones/etiquetas";
import icono_modificar from "../../img/Guardar.svg";
import icono_modificar_gris from "../../img/Guardar_gris.svg";
import AuthenticatedComponent from "../AuthenticatedComponent";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
const { TextArea } = Input;

const opciones = [
  { label: "Sí", value: "S" },
  { label: "No", value: "N" },
];

const opEstatus = [
  { label: etiquetas.ETQ_LISTA_RESEVA, value: etiquetas.ETQ_LR },
  { label: etiquetas.ETQ_NO_ACEPTO, value: etiquetas.ETQ_NA },
  { label: etiquetas.ETQ_NO_LOCALIZADA, value: etiquetas.ETQ_NL },
];

const ListaReservaCAE = () => {
  const formRef = useRef();
  const proceso = useSelector((store) => store.menu.proceso);
  const idParticipacion = useSelector((store) => store.menu.idParticipacion);
  const [api, contextHolder] = notification.useNotification();
  const [filtroSeleccionado, setFiltroSeleccionado] = useState({});
  const [lista, setLista] = useState([]);
  const [block, setBlock] = useState(false);
  const [msgNotData, setMsgNotData] = useState("");

  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  const handleBuscar = () => {
    setBlock(true);
    let request = {
      idProceso: proceso.idProcesoElectoral,
      idDetalle: proceso.idDetalleProceso,
      idParticipacion: idParticipacion,
      idPuesto: 3,
      estatus: parseInt(
        filtroSeleccionado.estatus == null ? 4 : filtroSeleccionado.estatus
      ),
      filtro: parseInt(filtroSeleccionado.idOpcion),
      idMunicipio: parseInt(
        filtroSeleccionado.idMunicipio ? filtroSeleccionado.idMunicipio : 0
      ),
      idLocalidad: parseInt(
        filtroSeleccionado.idLocalidad ? filtroSeleccionado.idLocalidad : 0
      ),
      idSedeReclutamiento: parseInt(
        filtroSeleccionado.idSede ? filtroSeleccionado.idSede : 0
      ),
      seccion1: parseInt(
        filtroSeleccionado.idSeccion ? filtroSeleccionado.idSeccion : 0
      ),
      seccion2: parseInt(
        filtroSeleccionado.idSeccion2 ? filtroSeleccionado.idSeccion2 : 0
      ),
    };
    apiClientPost("obtenerListaReservaCAE", request)
      .then((data) => {
        let code = data.code;
        if (code === CODE_SUCCESS) {
          if (data.data != null && data.data.length === 0) {
            setMsgNotData("200");
          }
          setLista(data.data);
          setBlock(false);
        } else {
          setMsgNotData("500");
          setLista([]);
          setBlock(false);
        }
      })
      .catch((error) => {
        setMsgNotData("500");
        setLista([]);
        setBlock(false);
      });
  };

  const generarColumnas = () => {
    const columns = [
      {
        title: etiquetas.ETQ_NO,
        key: "num",
        dataIndex: "num",
        width: "20px",
        align: "center",
      },
      {
        title: etiquetas.ETQ_SEDE_REC,
        key: "lugarSede",
        dataIndex: "lugarSede",
        width: "300px",
        align: "center",
      },
      {
        title: etiquetas.ETQ_NOMBRE_ASPIRANTE,
        key: "nombreAspirante",
        dataIndex: "nombreAspirante",
        width: "300px",
        align: "center",
      },
      {
        title: etiquetas.ETQ_EVA_INTEGRAL_CAE,
        key: "evaluacionInteCAE",
        dataIndex: "evaluacionInteCAE",
        width: "210px",
        align: "center",
      },
      {
        title: etiquetas.ETQ_REPRE_PP,
        key: "representantePP",
        dataIndex: "representantePP",
        width: "200px",
        align: "center",
        render: (text, record) => (
          <Select
            value={text}
            style={{ width: "100%" }}
            onChange={(value) => handleSelectReprePP(value, record)}
            options={opciones}
          />
        ),
      },
      {
        title: etiquetas.ETQ_MILITANTE_PP,
        key: "militantePP",
        dataIndex: "militantePP",
        width: "200px",
        align: "center",
        render: (text, record) => (
          <Select
            value={text}
            style={{ width: "100%" }}
            onChange={(value) => handleSelectMiliPP(value, record)}
            options={opciones}
          />
        ),
      },
      {
        title: etiquetas.ETQ_ESTATUS,
        key: "estatusListaReserva",
        dataIndex: "estatusListaReserva",
        width: "200px",
        align: "center",
        render: (text, record) => (
          <Select
            value={text}
            style={{ width: "100%" }}
            onChange={(value) => handleSelectEstatus(value, record)}
            options={opEstatus}
          />
        ),
      },
      {
        title: etiquetas.ETQ_OBSERVACIONES,
        key: "observaciones",
        dataIndex: "observaciones",
        width: "200px",
        align: "center",
        render: (text, record) => {
          return campoObservaciones(text, record);
        },
      },
      {
        title: <CBandejaAcciones acciones={[acciones.ACCION_MODIFICA]} />,
        key: "action",
        width: "100px",
        align: "center",
        render: (aspirante) => (
          <div style={{ display: "grid", justifyContent: "center" }}>
            <Tooltip placement="bottom" title={"Guardar"}>
              <a
                key={1}
                style={{
                  display: "grid",
                  justifyContent: "center",
                  width: "20px",
                }}
                disabled={aspirante.regModificado == null ? true : false}
                onClick={() => handleGuardarCambios(aspirante)}
              >
                <img
                  style={{ marginTop: "6px" }}
                  src={
                    aspirante.regModificado == null
                      ? icono_modificar_gris
                      : icono_modificar
                  }
                />
              </a>
            </Tooltip>
          </div>
        ),
      },
    ];
    return columns;
  };

  const campoObservaciones = (text, record) => {
    const maxLength = 150;
    return (
      <div style={{ position: "relative" }}>
        <TextArea
          value={text != null ? text : ""}
          onChange={(e) => {
            const sanitizedValue = e.target.value
              .replace(/[^a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]/g, "")
              .slice(0, maxLength);
            handleTextAreaChange(sanitizedValue, record);
          }}
          rows={2}
          placeholder="Escribe aquí"
          style={{ width: "100%" }}
          disabled={record.estatusListaReserva === 0}
        />
        <span
          className="span-observaciones"
          style={{ color: text?.length == 150 ? "red" : "#999" }}
        >
          {`${text?.length || 0}/${maxLength}`} caracteres
        </span>
      </div>
    );
  };

  const handleSelectReprePP = (value, record) => {
    const nuevaLista = lista.map((item) =>
      item.idAspirante === record.idAspirante
        ? { ...item, representantePP: value, regModificado: true }
        : item
    );
    setLista(nuevaLista);
  };

  const handleSelectMiliPP = (value, record) => {
    const nuevaLista = lista.map((item) =>
      item.idAspirante === record.idAspirante
        ? { ...item, militantePP: value, regModificado: true }
        : item
    );
    setLista(nuevaLista);
  };

  const handleSelectEstatus = (value, record) => {
    const nuevaLista = lista.map((item) =>
      item.idAspirante === record.idAspirante
        ? {
            ...item,
            estatusListaReserva: value,
            observaciones: value === 0 ? "" : record.observaciones,
            regModificado: true,
          }
        : item
    );
    setLista(nuevaLista);
  };

  const handleTextAreaChange = (value, record) => {
    const nuevaLista = lista.map((item) =>
      item.idAspirante === record.idAspirante
        ? { ...item, observaciones: value, regModificado: true }
        : item
    );
    setLista(nuevaLista);
  };

  const handleGuardarCambios = async (aspirante) => {
    if (aspirante.regModificado != null) {
      setBlock(true);
      try {
        apiClientPost("guardarCambiosListaReservaCAE", aspirante)
          .then((data) => {
            let code = data.code;
            if (code == CODE_SUCCESS) {
              mostrarModal("¡La actualización se guardó con éxito!", 1);
            } else {
              mostrarModal("Error al actualizar.", 2);
            }
          })
          .catch((error) => {
            mostrarModal("Error al actualizar.", 2);
          });
      } catch (error) {
        console.log("Ocurrió un error al guardar los cambios,", error);
        mostrarModal("Error al actualizar.", 2);
      } finally {
        setBlock(false);
      }
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
    if (tipoModal === 1) handleBuscar();
  };

  return (
    <>
      <AuthenticatedComponent>
        <Template>
          <Loader blocking={block} />

          <Form ref={formRef} onFinish={handleBuscar}>
            <FiltroListaReserva
              tipoLista={etiquetas.ETQ_TIPO_LISTA_CAE}
              filtroSeleccionado={filtroSeleccionado}
              setFiltroSeleccionado={setFiltroSeleccionado}
              formRef={formRef}
              setLista={setLista}
              setMsgNotData={setMsgNotData}
            />
          </Form>
          {lista.length > 0 ? (
            <>
              {contextHolder}
              <Layout
                id="content-lista-reserva"
                style={{ marginBottom: "20px" }}
              >
                <Row style={{ marginBottom: "20px" }}>
                  <span className="indicacion_rosa">
                    {" "}
                    <DoubleRightOutlined />
                  </span>
                  <span className="span-title-modulo">
                    {"Lista de reserva"}
                  </span>
                </Row>
                <BarInfo text={etiquetas.ETQ_INFO_CAE} />
                <Row>
                  <Col xs={24} md={24} xl={24}>
                    <Table
                      columns={generarColumnas()}
                      dataSource={lista ? lista : {}}
                      pagination={true}
                      scroll={{ x: 1000 }}
                    />
                  </Col>
                </Row>
              </Layout>
            </>
          ) : (
            <>
              {msgNotData != "" ? (
                <Layout
                  id="content-lista-reserva"
                  style={{ marginBottom: "20px" }}
                >
                  <Row style={{ marginBottom: "20px", marginTop: "30px" }}>
                    {msgNotData === "200" ? (
                      <BarInfo text={etiquetas.ETQ_REGISTROS_NO_ENCONT} />
                    ) : (
                      <BarError text={etiquetas.ETQ_INFO_NO_DISPONIBLE} />
                    )}
                  </Row>
                </Layout>
              ) : (
                ""
              )}
            </>
          )}
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
    </>
  );
};

export default ListaReservaCAE;
