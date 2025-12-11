import React from "react";
import { Col } from "antd";
import VObservaciones from "./VObservaciones";
import { FLUJO_CONSULTA } from "../../../utils/constantes.js";
import {
  SUB_OBSERVA,
  ETI_OPCIONAL,
} from "../../../utils/causaSustitucion/etiquetas.js";

const CObservaciones = ({
  tipoFlujo,
  observaciones,
  charCount,
  maxLength,
  setObservaciones,
}) => {
  return (
    <Col xs={20} md={16} xl={16}>
      <div>
        <span className="sub_titulos">{SUB_OBSERVA}</span>
        &nbsp;&nbsp;
        <span className="eti_opcional">{ETI_OPCIONAL}</span>
        {tipoFlujo == FLUJO_CONSULTA ? (
          <div className="sub_datos">
            <span>{observaciones ? observaciones : "Sin observaciones"}</span>
          </div>
        ) : (
          <VObservaciones
            observaciones={observaciones}
            charCount={charCount}
            maxLength={maxLength}
            setObservaciones={setObservaciones}
          />
        )}
      </div>
    </Col>
  );
};

export default CObservaciones;
