import { useDispatch, useSelector } from "react-redux";
import { useRef, useEffect } from "react";
import { Form, Select } from "antd";
import {
  obtenerEstados,
  asignarEstado,
  obtenerProcesos,
  asignarProceso,
  obtenerDistritos,
  asignarDistrito,
  obtieneMenuLateral,
  obtieneMenuReportes,
  obtieneParticipacion,
} from "../../../redux/menu/menuReducer.js";
import * as etiquetas from "../../../utils/header/etiquetas.js";
import { ID_SISTEMA } from "../../../utils/constantes";
import { useNavigate } from "react-router-dom";

const componentsNames = {
  entidad: "entidad",
  proceso: "proceso",
  distrito: "distrito",
};

function CFiltrosHeader() {
  const formRef = useRef();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const user = useSelector((store) => store.loginUser.currentUser);
  const rolUsuario = useSelector((store) => store.menu.rolUsuario);
  const listaEstadosPagPrincipal = useSelector((store) => store.menu.estados);
  const estado = useSelector((store) => store.menu.estado);
  const listaProcesoElectPagPrincipal = useSelector(
    (store) => store.menu.procesos
  );
  const proceso = useSelector((store) => store.menu.proceso);
  const listaDistritoPagPrincipal = useSelector(
    (store) => store.menu.distritos
  );
  const distrito = useSelector((store) => store.menu.distrito);
  const modulo = useSelector((store) => store.menu.moduloSeleccionado);

  useEffect(() => {
    if (estado != null)
      formRef.current.setFieldsValue({
        [componentsNames.entidad]: parseInt(estado?.idEstado),
      });

    if (proceso != null)
      formRef.current.setFieldsValue({
        [componentsNames.proceso]: parseInt(proceso?.idDetalleProceso),
      });

    if (distrito != null)
      formRef.current.setFieldsValue({
        [componentsNames.distrito]: parseInt(distrito?.idDistrito),
      });

    if (
      (modulo === undefined ||
        modulo === null ||
        modulo.idMenu === null ||
        parseInt(modulo.idMenu) === 0) &&
      (rolUsuario === "OC" || rolUsuario === "JL")
    ) {
      navigate("/home");
    }
  }, []);

  useEffect(() => {
    if (rolUsuario != null && listaEstadosPagPrincipal === null) {
      dispatch(
        obtenerEstados({ rolUsuario: user.rolUsuario, tokenAuth: user.tknA })
      );
    }
  }, [listaEstadosPagPrincipal]);

  useEffect(() => {
    if (
      rolUsuario != null &&
      rolUsuario != "OC" &&
      listaEstadosPagPrincipal != null
    ) {
      handleOnSelectEstado(parseInt(user.idEstado));
    }
  }, [listaEstadosPagPrincipal]);

  useEffect(() => {
    if (rolUsuario != null && rolUsuario != "OC" && proceso === null) {
      if (
        listaProcesoElectPagPrincipal === undefined ||
        listaProcesoElectPagPrincipal === null
      ) {
        dispatch(
          obtenerProcesos({
            idEstado: parseInt(user.idEstado),
            ambitoUsuario: user.ambito,
            idDistrito:
              user.idDistrito === undefined || user.idDistrito === null
                ? 0
                : user.idDistrito,
            tokenAuth: user.tknA,
          })
        );
      } else if (listaProcesoElectPagPrincipal.length > 0) {
        selectProcesoUnico(parseInt(user.idEstado));
      }
    } else if (rolUsuario != null && rolUsuario === "OC" && proceso === null) {
      if (
        listaProcesoElectPagPrincipal != undefined &&
        listaProcesoElectPagPrincipal != null &&
        listaProcesoElectPagPrincipal.length == 1
      ) {
        selectProcesoUnico(parseInt(estado?.idEstado));
      }
    }
  }, [listaProcesoElectPagPrincipal]);

  useEffect(() => {
    if (
      rolUsuario != null &&
      rolUsuario === "JD" &&
      listaDistritoPagPrincipal != null &&
      distrito === null &&
      proceso != null
    ) {
      handleOnSelectDitrito(parseInt(user.idDistrito));
    }
  }, [listaDistritoPagPrincipal]);

  const selectProcesoUnico = (idestadoDef) => {
    dispatch(asignarProceso(listaProcesoElectPagPrincipal[0]));
    formRef.current.setFieldsValue({
      [componentsNames.proceso]: parseInt(
        listaProcesoElectPagPrincipal[0].idDetalleProceso
      ),
    });
    if (distrito === null) {
      let param = {
        request: {
          idEstado: parseInt(idestadoDef),
          idProceso: parseInt(
            listaProcesoElectPagPrincipal[0].idProcesoElectoral
          ),
          idDetalle: parseInt(
            listaProcesoElectPagPrincipal[0].idDetalleProceso
          ),
          idDistrito:
            user.idDistrito === undefined || user.idDistrito === null
              ? 0
              : user.idDistrito,
          idSistema: ID_SISTEMA,
        },
        tokenAuth: user.tknA,
      };
      dispatch(obtenerDistritos(param));

      param = {
        request: {
          idSistema: ID_SISTEMA,
          idProceso: parseInt(
            listaProcesoElectPagPrincipal[0].idProcesoElectoral
          ),
          idDetalle: parseInt(
            listaProcesoElectPagPrincipal[0].idDetalleProceso
          ),
          idEstado: 0,
          idDistrito: 0,
          idMunicipio: 0,
          grupoSistema: user.rolUsuario,
        },
        tokenAuth: user.tknA,
      };

      dispatch(obtieneMenuReportes(param));
    }
  };

  const handleOnSelectEstado = (estadoSelectOption) => {
    let estado_ = "";
    listaEstadosPagPrincipal.forEach((edo) => {
      if (parseInt(edo.idEstado) === estadoSelectOption) {
        estado_ = edo;
      }
    });
    dispatch(asignarEstado(estado_));

    if (rolUsuario != null && rolUsuario === "OC") {
      dispatch(
        obtenerProcesos({
          idEstado: parseInt(estado_.idEstado),
          ambitoUsuario: user.ambito,
          idDistrito:
            user.idDistrito === undefined || user.idDistrito === null
              ? 0
              : user.idDistrito,
          tokenAuth: user.tknA,
        })
      );
      dispatch(asignarDistrito(null));
      formRef.current.setFieldsValue({
        [componentsNames.proceso]: "Proceso electoral",
        [componentsNames.distrito]: etiquetas.MENU_FILTROS_DISTRITO_LABEL_0,
      });

      navigate("/home");
    }
  };

  const handleOnSelectProcesoElect = (procesoElectPagPrincipal) => {
    if (procesoElectPagPrincipal === 0) {
      dispatch(asignarDistrito(null));
      dispatch(asignarProceso(null));
      navigate("/home");
    } else {
      let proceso_ = "";
      listaProcesoElectPagPrincipal.forEach((p) => {
        if (parseInt(p.idDetalleProceso) === procesoElectPagPrincipal) {
          proceso_ = p;
        }
      });
      dispatch(asignarProceso(proceso_));

      if (rolUsuario != "JD") {
        let param = {
          request: {
            idEstado: parseInt(estado?.idEstado),
            idProceso: parseInt(proceso_.idProcesoElectoral),
            idDetalle: parseInt(proceso_.idDetalleProceso),
            idDistrito: 0,
            idSistema: ID_SISTEMA,
          },
          tokenAuth: user.tknA,
        };
        dispatch(obtenerDistritos(param));

        formRef.current.setFieldsValue({
          [componentsNames.distrito]: etiquetas.MENU_FILTROS_DISTRITO_LABEL_0,
        });

        param = {
          request: {
            idSistema: ID_SISTEMA,
            idProceso: parseInt(proceso_.idProcesoElectoral),
            idDetalle: parseInt(proceso_.idDetalleProceso),
            idEstado: 0,
            idDistrito: 0,
            idMunicipio: 0,
            grupoSistema: user.rolUsuario,
          },
          tokenAuth: user.tknA,
        };

        dispatch(obtieneMenuReportes(param));
      }

      if (
        modulo != undefined &&
        modulo != null &&
        parseInt(modulo.idMenu) != 0
      ) {
        navigate("/home");
      }
    }
  };

  const handleOnSelectDitrito = (distritoSelectOption) => {
    if (distritoSelectOption === 0) {
      dispatch(asignarDistrito(null));
      let param = {
        request: {
          idSistema: ID_SISTEMA,
          idProceso: parseInt(proceso.idProcesoElectoral),
          idDetalle: parseInt(proceso.idDetalleProceso),
          idEstado: 0,
          idDistrito: 0,
          idMunicipio: 0,
          grupoSistema: user.rolUsuario,
        },
        tokenAuth: user.tknA,
      };

      dispatch(obtieneMenuReportes(param));
    } else {
      let distrito_ = "";
      listaDistritoPagPrincipal.forEach((d) => {
        if (parseInt(d.idDistrito) === distritoSelectOption) {
          distrito_ = d;
        }
      });
      dispatch(asignarDistrito(distrito_));

      let param = {
        request: {
          idSistema: ID_SISTEMA,
          idProceso: parseInt(proceso?.idProcesoElectoral),
          idDetalle: parseInt(proceso?.idDetalleProceso),
          idEstado: parseInt(estado?.idEstado),
          idDistrito: parseInt(distritoSelectOption),
          idMunicipio: 0,
          grupoSistema: user.rolUsuario,
        },
        tokenAuth: user.tknA,
      };

      dispatch(obtieneMenuLateral(param))
        .unwrap()
        .then(() => {
          let paramPart = {
            request: {
              idProceso: parseInt(proceso?.idProcesoElectoral),
              idDetalle: parseInt(proceso?.idDetalleProceso),
              idEstado: parseInt(estado?.idEstado),
              idDistrito: parseInt(distrito_.idDistrito),
              ambitoUsuario: user.ambito,
              tipoCapturaSistema: proceso?.tipoCapturaSistema,
            },
            tokenAuth: user.tknA,
          };
          dispatch(obtieneParticipacion(paramPart));
        });
    }

    if (
      parseInt(modulo.idMenu) != 0 &&
      (rolUsuario === "OC" || rolUsuario === "JL")
    ) {
      navigate("/home");
    }
  };

  const obtienNombreDistrito = () => {
    return parseInt(distrito?.idDistrito) > 0
      ? distrito?.idDistrito + "-" + distrito?.nombreDistrito
      : distrito?.nombreDistrito + ":";
  };

  return (
    <Form ref={formRef}>
      <div className="select-header-geo">
        <div
          className={
            estado != null && (rolUsuario === "JD" || rolUsuario === "JL")
              ? "item-selected-geo"
              : "item-select-geo"
          }
        >
          <span className="span-select-geo">
            {estado != null && (rolUsuario === "JD" || rolUsuario === "JL")
              ? estado?.nombreEstado
              : etiquetas.MENU_FILTROS_ESTADO + ":"}
          </span>
          {rolUsuario !== "JD" && rolUsuario !== "JL" ? (
            <Form.Item name={componentsNames.entidad}>
              <Select onChange={handleOnSelectEstado} placeholder="TODOS">
                {listaEstadosPagPrincipal?.map((edo) => (
                  <Option key={edo?.idEstado} value={edo?.idEstado}>
                    {edo?.nombreEstado}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          ) : (
            ""
          )}
          <div className="div-separator-geo" />
        </div>

        <div
          className={
            rolUsuario === "JD" && distrito != null
              ? "item-select-geo-minus"
              : "item-select-geo"
          }
        >
          <span className="span-select-geo">
            {etiquetas.MENU_FILTROS_PROCESO_ELECTORAL + ":"}
          </span>

          <Form.Item name={componentsNames.proceso}>
            <Select onChange={handleOnSelectProcesoElect} placeholder="TODOS">
              {listaProcesoElectPagPrincipal?.map((procesoElect_) => (
                <Option
                  key={procesoElect_?.idDetalleProceso}
                  value={procesoElect_?.idDetalleProceso}
                >
                  {procesoElect_?.descripcionDetalle}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <div className="div-separator-geo" />
        </div>

        <div
          className={
            rolUsuario === "JD" && distrito != null
              ? "item-selected-geo"
              : "item-select-geo"
          }
        >
          <span className="span-select-geo">
            {rolUsuario === "JD" && distrito != null
              ? obtienNombreDistrito()
              : etiquetas.MENU_FILTROS_DISTRITO + ":"}
          </span>
          {rolUsuario !== "JD" ? (
            <Form.Item name={componentsNames.distrito}>
              <Select
                onChange={handleOnSelectDitrito}
                placeholder={etiquetas.MENU_FILTROS_DISTRITO_LABEL_0}
              >
                {listaDistritoPagPrincipal?.map((distrito_) => (
                  <Option
                    key={distrito_.idDistrito}
                    value={distrito_.idDistrito}
                  >
                    {`${
                      distrito_.idDistrito > 0 ? distrito_.idDistrito + "-" : ""
                    }${distrito_.nombreDistrito}`}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          ) : (
            ""
          )}
        </div>
      </div>
    </Form>
  );
}

export default CFiltrosHeader;
