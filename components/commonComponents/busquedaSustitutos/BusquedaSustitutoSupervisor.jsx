import { Form, Input, TreeSelect, Row, Col } from "antd";
import * as etiquetas from "./../../../utils/busquedaSustituto/etiquetas";
import * as etiquetasSust from "./../../../utils/sustSEyCAE/etiquetas";
import * as funciones from "./../../../utils/busquedaSustituto/funciones";
import { apiClientPost } from "../../../utils/apiClient";
import { useEffect, useState } from "react";
import { CaretDownOutlined } from "@ant-design/icons";
import { tablaCapacitador, tablaSupervisor } from "./TablaDatosSustitutos";
import LoadFoto from "../LoadFoto";
import { useSelector } from "react-redux";
import dayjs from "dayjs";
const ROL_ADMIN_OC = "SCE.ADMIN.OC";
const TXT_RESCISION = "rescisión de contrato";
import { ACCION_CAPTURA } from "../../../utils/constantes";

function BusquedaSustitutoSupervisor(props) {
  const [sustitutosSupervisores, setSustitutosSupervisores] = useState([]);
  const [dtoSustitutoSupervisor, setDtoSustitutoSupervisor] = useState({});
  const [datosSustituSupervisor, setDatosSustituSupervisor] = useState({});
  const [fechaInicioSustitucion, setFechaInicioSustitucion] =
    useState(undefined);
  const [fechaFinSustitucion, setFechaFinSustitucion] = useState(undefined);
  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu);
  const [value, setValue] = useState();

  useEffect(() => {
    getListaSustitutosPersSup();
    setValue(undefined);
    setDtoSustitutoSupervisor({});
    setDatosSustituSupervisor({});

    return () => {
      props.suplenteSupervisorSeleccionado({});
      props.onChangeDate("");
    };
  }, [props.infoSustitutoSe]);

  const getListaSustitutosPersSup = async () => {
    let request = {
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idEstado: menu.estado.idEstado,
      idParticipacion: menu.idParticipacion,
    };
    apiClientPost("obtenerListaSustitutosSupervisores", request)
      .then((data) => {
        let sustSupervisores = data.data;
        //los usuario que no son admin, no deben ver categorias de rescisión
        let categoriasSustitutos_NO_ADMIN;
        if (
          sustSupervisores &&
          sustSupervisores.categoriasSustitutos &&
          user &&
          user.rolUsuario != ROL_ADMIN_OC
        ) {
          categoriasSustitutos_NO_ADMIN =
            sustSupervisores.categoriasSustitutos.filter(
              (categoria) =>
                !categoria.title.toLowerCase().includes(TXT_RESCISION)
            );
        }
        setSustitutosSupervisores(
          categoriasSustitutos_NO_ADMIN
            ? categoriasSustitutos_NO_ADMIN
            : sustSupervisores.categoriasSustitutos
        );
        setFechaInicioSustitucion(
          dayjs(sustSupervisores.fechaInicioRegistroSustituciones)
        );
        setFechaFinSustitucion(
          dayjs(sustSupervisores.fechaFinRegistroSustituciones)
        );
      })
      .catch((error) => {
        console.error("ERROR -getListaSustitutosPersSup: ", error);
      });
  };

  const onChange = (newValue) => {
    setValue(newValue);
    if (newValue) {
      findAspirante(newValue);
    } else {
      setDtoSustitutoSupervisor({});
      setDatosSustituSupervisor({});
      const { sustitutoSupervisorSeleccionado } = props;
      sustitutoSupervisorSeleccionado(null, null);
    }
  };

  const findAspirante = async (idAspirante) => {
    let request = {
      idProcesoElectoral: menu.proceso.idProcesoElectoral,
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
      idAspirante: idAspirante,
    };
    let aspirante = await funciones.getAspiranteSustituto(request);
    setDtoSustitutoSupervisor(aspirante.dtoAspirante);
    setDatosSustituSupervisor(aspirante.datosAspirante);
    const { sustitutoSupervisorSeleccionado } = props;
    sustitutoSupervisorSeleccionado(
      aspirante.dtoAspirante,
      aspirante.datosAspirante.correoElectronico
    );
  };

  const onChangeDate = (date) => {
    const { onChangeDate } = props;
    onChangeDate(date);
  };

  const onChangeFoto = (imagenB64) => {
    const { onChangeFoto } = props;
    onChangeFoto(imagenB64);
  };

  const onChangeCorreoSupervisor = (correo) => {
    const { onChangeCorreoSupervisor } = props;
    onChangeCorreoSupervisor(correo.target.value);
  };

  return (
    <div className="contenedor_sustitutos">
      <Row>
        <Col xs={24} md={24} xl={24} className="fuente_titulo_box_sustitutos">
          {etiquetas.titulo_persona_supervisora}
        </Col>
      </Row>
      <Row>
        <Col xs={24} md={24} xl={24}>
          {(props.tipoFlujo === ACCION_CAPTURA ||
            props.tipoFlujo === undefined) && (
            <div className="fuente_header_treeselect">
              {etiquetas.nombre_numero_folio}{" "}
              <span style={{ color: "#CCCCCC" }}>{etiquetas.opcional}</span>
            </div>
          )}
        </Col>
      </Row>
      <Row>
        <Col xs={24} md={12} xl={8}>
          {(props.tipoFlujo === ACCION_CAPTURA ||
            props.tipoFlujo === undefined) && (
            <TreeSelect
              filterTreeNode={(inputValue, treeNode) =>
                treeNode.title.toLowerCase().includes(inputValue.toLowerCase())
              }
              style={{
                maxHeight: 400,
                overflow: "auto",
                width: "100%",
              }}
              value={value}
              treeData={sustitutosSupervisores}
              placeholder="Seleccione una opción"
              treeDefaultExpandAll={false}
              onChange={onChange}
              showSearch
              treeLine
              allowClear
              switcherIcon={<CaretDownOutlined style={{ color: "#D5007F" }} />}
              popupMatchSelectWidth={false}
            />
          )}
        </Col>
      </Row>
      <Row className="contenedor_info_sustituto">
        {(datosSustituSupervisor.cargoActual !== undefined &&
          props.tipoFlujo === ACCION_CAPTURA) ||
        props.tipoFlujo !== ACCION_CAPTURA ? (
          <Col
            xs={24}
            md={8}
            xl={5}
            style={{ justifyItems: "center", padding: "3px" }}
          >
            <LoadFoto
              key={
                dtoSustitutoSupervisor?.idAspirante ||
                props.infoSustitutoSe?.idAspirante ||
                dtoSustitutoSupervisor?.urlFoto ||
                props.infoSustitutoSe?.urlFoto
              }
              tipoFlujo={props.tipoFlujo}
              urlFotoAspirante={
                dtoSustitutoSupervisor.urlFoto !== undefined
                  ? dtoSustitutoSupervisor.urlFoto
                  : props.infoSustitutoSe !== null &&
                    props.infoSustitutoSe !== undefined
                  ? props.infoSustitutoSe.urlFoto
                  : null
              }
              onChangeFoto={onChangeFoto}
            />
          </Col>
        ) : (
          <></>
        )}
        {datosSustituSupervisor.cargoActual !== undefined &&
          (props.tipoFlujo === ACCION_CAPTURA ||
            props.tipoFlujo === undefined) &&
          tablaCapacitador(
            datosSustituSupervisor,
            onChangeDate,
            props.tipoFlujo,
            {},
            fechaInicioSustitucion,
            fechaFinSustitucion,
            props.fechaSustitucionAnterior
          )}
        {props.tipoFlujo !== ACCION_CAPTURA &&
          props.tipoFlujo !== undefined &&
          tablaSupervisor(
            props.infoSustitutoSe !== undefined ? props.infoSustitutoSe : {},
            onChangeDate,
            props.tipoFlujo,
            props.infoSustitucionSe,
            fechaInicioSustitucion,
            fechaFinSustitucion,
            props.fechaSustitucionAnterior
          )}
      </Row>
      <Row className="contenedor_info_sustituto">
        {datosSustituSupervisor.cargoActual !== undefined &&
          datosSustituSupervisor.idPuesto === 3 && (
            <Col xs={12} md={8} xl={5}>
              <span className="fuente_datos_aspirante_header">
                <span style={{ color: "#D5007F" }}>* </span>
                {etiquetas.correo_cuenta}
              </span>
              <br />
              <div>
                <Form.Item
                  name="correoElectronicoSustSupervisor"
                  rules={[
                    {
                      required: true,
                      message: etiquetasSust.MSJ_INFO_REQ,
                    },
                    {
                      type: "email",
                      message: etiquetas.msg_correo_invalido,
                    },
                  ]}
                >
                  <Input
                    onChange={onChangeCorreoSupervisor}
                    placeholder="correo.correo@correo.mx"
                  />
                </Form.Item>
              </div>
            </Col>
          )}
      </Row>
    </div>
  );
}

BusquedaSustitutoSupervisor.defaultProps = {
  suplenteSupervisorSeleccionado: (suplenteSupervisor) =>
    console.log("valor defautl, suplenteSupervisor: ", suplenteSupervisor), // Función predeterminada
};

export default BusquedaSustitutoSupervisor;
