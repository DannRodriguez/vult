import { Form, Input, Row, TreeSelect, Col } from "antd";
import * as etiquetas from "./../../../utils/busquedaSustituto/etiquetas";
import * as etiquetasSust from "./../../../utils/sustSEyCAE/etiquetas";
import * as funciones from "./../../../utils/busquedaSustituto/funciones";
import { apiClientPost } from "../../../utils/apiClient";
import { useEffect, useState } from "react";
import { CaretDownOutlined } from "@ant-design/icons";
import { tablaCapacitador } from "./TablaDatosSustitutos";
import LoadFoto from "../LoadFoto";
import { useSelector } from "react-redux";
import dayjs from "dayjs";
import { ACCION_CAPTURA } from "../../../utils/constantes";
const ROL_ADMIN_OC = "SCE.ADMIN.OC";
const TXT_RESCISION = "rescisión de contrato";
const TXT_INCAPACIDAD = "cae con incapacidad";

function BusquedaSustitutoCapacitador(props) {
  const [sustitutosCapacitadores, setSustitutosCapacitadores] = useState([]);
  const [dtoSustitutoCapacitador, setDtoSustitutoCapacitador] = useState({});
  const [datosSustituCapacitador, setDatosSustituCapacitador] = useState({});
  const [fechaInicioSustitucion, setFechaInicioSustitucion] =
    useState(undefined);
  const [fechaFinSustitucion, setFechaFinSustitucion] = useState(undefined);
  const user = useSelector((store) => store.loginUser.currentUser);
  const menu = useSelector((store) => store.menu);
  const [value, setValue] = useState();

  useEffect(() => {
    getListaSustitutosPersCap();
    setDtoSustitutoCapacitador({});
    setDatosSustituCapacitador({});

    return () => {
      props.onChangeDate("");
      props.sustitutoCapacitadorSeleccionado({});
      props.onChangeCorreo("");
    };
  }, []);

  const getListaSustitutosPersCap = async () => {
    let request = {
      idDetalleProceso: menu.proceso.idDetalleProceso,
      idParticipacion: menu.idParticipacion,
    };
    apiClientPost("obtenerListaSustitutosCapacitadores", request)
      .then((data) => {
        let sustCapacitadores = data.data;

        //los usuario que no son admin, no deben ver categorias de rescisión
        let categoriasSustitutos_NO_ADMIN;
        if (
          sustCapacitadores &&
          sustCapacitadores.categoriasSustitutos &&
          user &&
          user.rolUsuario != ROL_ADMIN_OC
        ) {
          categoriasSustitutos_NO_ADMIN =
            sustCapacitadores.categoriasSustitutos.filter(
              (categoria) =>
                !categoria.title.toLowerCase().includes(TXT_RESCISION)
            );
          categoriasSustitutos_NO_ADMIN = categoriasSustitutos_NO_ADMIN.filter(
            (categoria) =>
              !categoria.title.toLowerCase().includes(TXT_INCAPACIDAD)
          );
        }

        setSustitutosCapacitadores(
          categoriasSustitutos_NO_ADMIN
            ? categoriasSustitutos_NO_ADMIN
            : sustCapacitadores.categoriasSustitutos
        );
        setFechaInicioSustitucion(
          dayjs(sustCapacitadores.fechaInicioRegistroSustituciones)
        );
        setFechaFinSustitucion(
          dayjs(sustCapacitadores.fechaFinRegistroSustituciones)
        );
      })
      .catch((error) => {
        console.error("ERROR -getListaSustitutosPersCap: ", error);
      });
  };

  const onChange = (newValue) => {
    setValue(newValue);
    if (newValue) {
      findAspirante(newValue);
    } else {
      setDtoSustitutoCapacitador();
      setDatosSustituCapacitador();
      const { sustitutoCapacitadorSeleccionado } = props;
      sustitutoCapacitadorSeleccionado(null, null);
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
    setDtoSustitutoCapacitador(aspirante.dtoAspirante);
    setDatosSustituCapacitador(aspirante.datosAspirante);
    const { sustitutoCapacitadorSeleccionado } = props;
    sustitutoCapacitadorSeleccionado(
      aspirante.dtoAspirante,
      aspirante.datosAspirante.correoElectronico
    );
  };

  const onChangeDate = (date) => {
    const { onChangeDate } = props;
    onChangeDate(date);
  };

  const onChangeCorreo = (correo) => {
    const { onChangeCorreo } = props;
    onChangeCorreo(correo.target.value);
  };

  const onChangeFoto = (imagenB64) => {
    const { onChangeFoto } = props;
    onChangeFoto(imagenB64);
  };

  const obtenerFechaMayor = () => {
    if (
      props.fechaSustitucionAnterior2 !== undefined &&
      props.fechaSustitucionAnterior2 !== null &&
      props.fechaSustitucionAnterior2 !== ""
    ) {
      return props.fechaSustitucionAnterior2;
    } else {
      return props.fechaSustitucionAnterior1;
    }
  };

  return (
    <div className="contenedor_sustitutos">
      <Row>
        <Col xs={24} md={24} xl={24} className="fuente_titulo_box_sustitutos">
          {etiquetas.titulo_persona_capacitadora}
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
              value={value}
              style={{
                maxHeight: 400,
                overflow: "auto",
                width: "100%",
              }}
              treeData={sustitutosCapacitadores}
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
        <Col xs={24} md={12} xl={16}></Col>
      </Row>

      <Row className="contenedor_info_sustituto">
        {(datosSustituCapacitador?.cargoActual !== undefined &&
          props.tipoFlujo === ACCION_CAPTURA) ||
        (props.infoSustitutoCae !== null &&
          props.tipoFlujo !== ACCION_CAPTURA) ? (
          <Col
            xs={24}
            md={8}
            xl={5}
            style={{ justifyItems: "center", padding: "3px" }}
          >
            <LoadFoto
              key={
                dtoSustitutoCapacitador?.idAspirante ||
                props.infoSustitutoCae?.idAspirante ||
                dtoSustitutoCapacitador?.urlFoto ||
                props.infoSustitutoCae?.urlFoto
              }
              imgBase64Prev={""}
              onChangeFoto={onChangeFoto}
              tipoFlujo={props.tipoFlujo}
              urlFotoAspirante={
                dtoSustitutoCapacitador?.urlFoto !== undefined
                  ? dtoSustitutoCapacitador?.urlFoto
                  : props.infoSustitutoCae !== null &&
                    props.infoSustitutoCae !== undefined
                  ? props.infoSustitutoCae.urlFoto
                  : null
              }
            />
          </Col>
        ) : (
          <></>
        )}
        {datosSustituCapacitador?.cargoActual !== undefined &&
          (props.tipoFlujo === ACCION_CAPTURA ||
            props.tipoFlujo === undefined) &&
          tablaCapacitador(
            datosSustituCapacitador,
            onChangeDate,
            props.tipoFlujo,
            {},
            fechaInicioSustitucion,
            fechaFinSustitucion,
            obtenerFechaMayor()
          )}
        {props.tipoFlujo !== ACCION_CAPTURA &&
          props.tipoFlujo !== undefined &&
          tablaCapacitador(
            props.infoSustitutoCae !== null ? props.infoSustitutoCae : {},
            onChangeDate,
            props.tipoFlujo,
            props.infoSustitucionCae,
            fechaInicioSustitucion,
            fechaFinSustitucion,
            obtenerFechaMayor()
          )}
      </Row>
      <Row className="contenedor_info_sustituto">
        {datosSustituCapacitador?.cargoActual !== undefined &&
          datosSustituCapacitador?.idPuesto === 3 && (
            <Col xs={12} md={8} xl={5}>
              <span className="fuente_datos_aspirante_header">
                <span style={{ color: "#D5007F" }}>* </span>
                {etiquetas.correo_cuenta}
              </span>
              <br />
              <div>
                <Form.Item
                  name="correoElectronicoSustCapacitador"
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
                    onChange={onChangeCorreo}
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

BusquedaSustitutoCapacitador.defaultProps = {
  sustitutoCapacitadorSeleccionado: (suplenteCapacitador) =>
    console.log("valor defautl, suplenteCapacitador: ", suplenteCapacitador), // Función predeterminada
};

export default BusquedaSustitutoCapacitador;
