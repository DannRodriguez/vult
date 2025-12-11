import React from "react";
import { Tooltip } from "antd";
import VAcciones from "./VAcciones";
import * as etiquetas from "../../../utils/acciones/etiquetas";
import "../../../css/bandejaAcciones.scss";
import { ExclamationCircleFilled } from "@ant-design/icons";

const CBandejaAcciones = ({ acciones }) => {
  return (
    <Tooltip
      title={<VAcciones acciones={acciones} />}
      className="bandeja-acciones-columna"
      classNames={{ root: "bandeja-acciones-tooltip" }}
      placement="bottomLeft"
      color="white"
    >
      <span>{etiquetas.ACCIONES}</span>
      <ExclamationCircleFilled className="icono_acciones" />
    </Tooltip>
  );
};

export default CBandejaAcciones;
