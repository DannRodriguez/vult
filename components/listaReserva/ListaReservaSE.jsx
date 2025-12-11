import { Form, Table, Layout, Row, Radio, Col, Space, Tooltip } from "antd";
import { useRef, useState } from "react";
import FiltroListaReserva from "../commonComponents/filtroListaReserva/FiltroListaReserva";
import { useSelector } from "react-redux/es/hooks/useSelector";
import Template from "../interfaz/Template";
import * as B from "../../utils/listaReserva/etiquetas";
import * as etiqueta from "../../utils/listaReservaSE/etiquetas";
import { CODE_SUCCESS } from "../../utils/constantes.js";
import { DoubleRightOutlined } from "@ant-design/icons";
import icono_guardar from "../../img/icono_guardar.svg";
import icono_guardar_disabled from "../../img/icono_guardar_disabled.svg";
import AuthenticatedComponent from "../AuthenticatedComponent";
import { Loader } from "../interfaz/Loader.jsx";
import { BarInfo, BarError } from "../commonComponents/AccessoriesComponents";
import VCustomModal from "../commonComponents/VCustomModal.jsx";
import { apiClientPost } from "../../utils/apiClient";

const ListaReservaSE = () => {
  const formRef = useRef();

  const [filtroSeleccionado, setFiltroSeleccionado] = useState({
    idOpcion: B.ETQ_RADIO_OPC_TODOS,
    estatus: [B.ETQ_LR, B.ETQ_NA, B.ETQ_NL, B.ETQ_OP],
  });
  const [block, setBlock] = useState(false);
  const user = useSelector((store) => store.loginUser.currentUser);
  const proceso = useSelector((store) => store.menu.proceso);
  const idParticipacion = useSelector((store) => store.menu.idParticipacion);
  const [listaReserva, setListaReserva] = useState(null);
  const [msgNotData, setMsgNotData] = useState("");

  const [openModal, setOpenModal] = useState(false);
  const [tipoModal, setTipoModal] = useState(1);
  const [msjModal, setMsjModal] = useState("");

  function handleBuscar() {
    setBlock(true);
    let request = {
      idProceso: proceso.idProcesoElectoral,
      idDetalle: proceso.idDetalleProceso,
      idParticipacion: idParticipacion,
      idPuesto: 2,
      estatus:
        filtroSeleccionado.estatus != B.ETQ_TODOS
          ? [parseInt(filtroSeleccionado.estatus)]
          : [
              parseInt(B.ETQ_LR),
              parseInt(B.ETQ_NA),
              parseInt(B.ETQ_NL),
              parseInt(B.ETQ_OP),
            ],
      filtro: parseInt(filtroSeleccionado.idOpcion),
      idMunicipio: parseInt(
        filtroSeleccionado.idMunicipio ? filtroSeleccionado.idMunicipio : 0
      ),
      idLocalidad: parseInt(
        filtroSeleccionado.idLocalidad ? filtroSeleccionado.idLocalidad : 0
      ),
      idSede: parseInt(
        filtroSeleccionado.idSede ? filtroSeleccionado.idSede : 0
      ),
      seccion1: parseInt(
        filtroSeleccionado.idSeccion ? filtroSeleccionado.idSeccion : 0
      ),
      seccion2: parseInt(
        filtroSeleccionado.idSeccion2 ? filtroSeleccionado.idSeccion2 : 0
      ),
    };
    apiClientPost("obtenerListaReservaSe", request)
      .then((data) => {
        let code = data.code;
        if (code === CODE_SUCCESS) {
          if (data.data != null && data.data.length === 0) {
            setMsgNotData("200");
          }
          setListaReserva(data.data);
          setBlock(false);
        } else {
          setMsgNotData("500");
          setListaReserva([]);
          setBlock(false);
        }
      })
      .catch((error) => {
        setBlock(false);
        setMsgNotData("500");
        setListaReserva([]);
      });
  }

  const handleGuardar = (aspirante) => {
    setBlock(true);
    let request = {
      idProceso: proceso.idProcesoElectoral,
      idDetalle: aspirante.idDetalle,
      idParticipacion: aspirante.idParticipacion,
      idAspirante: aspirante.idAspirante,
      estatus: aspirante.estatusListaReserva,
      ip: user.ip,
      user: user.username,
    };
    apiClientPost("actualizarListaReservaSe", request)
      .then((data) => {
        let code = data.code;
        if (code == CODE_SUCCESS) {
          setBlock(false);
          mostrarModal("¡La actualización se guardó con éxito!", 1);
        } else {
          setBlock(false);
          mostrarModal("Error al actualizar.", 2);
        }
      })
      .catch((error) => {
        setBlock(false);
        mostrarModal("Error al actualizar.", 2);
      });
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

  const handleRadioChange = (value, record) => {
    const nuevaLista = listaReserva.map((item) =>
      item.idAspirante === record.idAspirante
        ? { ...item, estatusListaReserva: value }
        : item
    );
    setListaReserva(nuevaLista);
  };

  const header = [
    {
      title: etiqueta.ETQ_NO,
      dataIndex: "idObjeto",
      key: "idObjeto",
      width: "100px",
      align: "center",
      render(text, record) {
        return {
          props: {
            style: { background: record.cellColorBackground },
          },
          children: <div>{text}</div>,
        };
      },
    },
    {
      title: etiqueta.ETQ_SEDE,
      dataIndex: "sede",
      key: "sede",
      width: "300px",
      align: "left",
    },
    {
      title: etiqueta.ETQ_CAE,
      dataIndex: "nombreCae",
      key: "nombreCae",
      width: "300px",
      align: "left",
    },
    {
      title: etiqueta.ETQ_EVUALUACION,
      dataIndex: "evaluacionIntSe",
      key: "evaluacionIntSe",
      width: "180px",
      align: "center",
    },
    {
      title: etiqueta.ETQ_LISTA_RESERVA_RD,
      dataIndex: "estatusListaReserva",
      key: "estatusListaReserva",
      width: "150px",
      align: "center",
      render: (text, record) => (
        <Radio
          value={B.ETQ_LR}
          checked={text === B.ETQ_LR}
          onChange={(e) => handleRadioChange(e.target.value, record)}
        ></Radio>
      ),
    },
    {
      title: etiqueta.ETQ_NO_ACEPTO_RD,
      dataIndex: "estatusListaReserva",
      key: "estatusListaReserva",
      width: "150px",
      align: "center",
      render: (text, record) => (
        <Radio
          value={B.ETQ_NA}
          checked={text === B.ETQ_NA}
          onChange={(e) => handleRadioChange(e.target.value, record)}
        ></Radio>
      ),
    },
    {
      title: etiqueta.ETQ_NO_LOCALIZADA_RD,
      dataIndex: "estatusListaReserva",
      key: "estatusListaReserva",
      width: "150px",
      align: "center",
      render: (text, record) => (
        <Radio
          value={B.ETQ_NL}
          checked={text === B.ETQ_NL}
          onChange={(e) => handleRadioChange(e.target.value, record)}
        ></Radio>
      ),
    },
    {
      title: etiqueta.ETQ_ACCIONES,
      width: "100px",
      align: "center",
      render: (text, record) => (
        <Space size="middle">
          <Tooltip placement="bottom" title={"Guardar"}>
            {record.estatusListaReserva != record.estatusLista ? (
              <a
                key={1}
                style={{
                  display: "grid",
                  justifyContent: "center",
                  width: "20px",
                }}
                onClick={() => handleGuardar(record)}
              >
                <img src={icono_guardar} />
              </a>
            ) : (
              <img src={icono_guardar_disabled} />
            )}
          </Tooltip>
        </Space>
      ),
    },
  ];

  return (
    <AuthenticatedComponent>
      <Template>
        <Loader blocking={block} />
        <Form ref={formRef} onFinish={handleBuscar}>
          <FiltroListaReserva
            tipoLista={B.ETQ_TIPO_LISTA_SE}
            filtroSeleccionado={filtroSeleccionado}
            setFiltroSeleccionado={setFiltroSeleccionado}
            formRef={formRef}
            setLista={setListaReserva}
            setMsgNotData={setMsgNotData}
          />
        </Form>

        {listaReserva !== null && listaReserva.length > 0 ? (
          <Layout id="content-lista-reserva" style={{ marginBottom: "20px" }}>
            <Row style={{ marginBottom: "20px" }}>
              <span className="indicacion_rosa">
                {" "}
                <DoubleRightOutlined />
              </span>
              <span className="span-title-modulo">
                {etiqueta.ETQ_LISTA_RESERVA}
              </span>
            </Row>

            <Row className="lista-reserva-criterio" align="middle">
              <span style={{ background: "#FFCCFF", padding: "10px 10px" }} />
              &nbsp;&nbsp;{etiqueta.ETQ_ACOTACION_1}
            </Row>

            <Row className="lista-reserva-criterio" align="middle">
              <span style={{ background: "#CCFFFF", padding: "10px 10px" }} />
              &nbsp;&nbsp;{etiqueta.ETQ_ACOTACION_2}
            </Row>

            <Row className="lista-reserva-criterio" align="middle">
              <span style={{ background: "#CC99FF", padding: "10px 10px" }} />
              &nbsp;&nbsp;{etiqueta.ETQ_ACOTACION_3}
            </Row>

            <Row className="lista-reserva-criterio" align="middle">
              <span style={{ background: "#95DE6C", padding: "10px 10px" }} />
              &nbsp;&nbsp;{etiqueta.ETQ_ACOTACION_4}
            </Row>

            <Row>
              <Col xs={24} md={24} xl={24}>
                <Table
                  columns={header}
                  dataSource={listaReserva}
                  pagination={{
                    defaultPageSize: 10,
                    showQuickJumper: false,
                  }}
                  scroll={{ x: 1000 }}
                />
              </Col>
            </Row>
          </Layout>
        ) : (
          <>
            {msgNotData != "" ? (
              <Layout
                id="content-lista-reserva"
                style={{ marginBottom: "20px" }}
              >
                <Row style={{ marginBottom: "20px", marginTop: "30px" }}>
                  {msgNotData === "200" ? (
                    <BarInfo text={B.ETQ_REGISTROS_NO_ENCONT} />
                  ) : (
                    <BarError text={B.ETQ_INFO_NO_DISPONIBLE} />
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
  );
};

export default ListaReservaSE;
