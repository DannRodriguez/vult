import React from "react";
import { Col } from "antd";
import { InfoData } from "./AccessoriesComponents.jsx";

import * as etiquetas from "../../utils/causaSustitucion/etiquetas.js";

const VInfoAspirante = ({
  folio,
  apellidoPaterno,
  apellidoMaterno,
  nombre,
  nombreCargo,
  claveElectorFuar,
  isMostrarZORE,
  numeroZonaResponsabilidad,
  isMostrarARE,
  numeroAreaResponsabilidad,
  cargoAnterior,
}) => {
  return (
    <>
      <InfoData info={etiquetas.FOLIO} data={`${folio ?? ""}`} />
      <InfoData
        info={etiquetas.NOMBRE}
        data={`${apellidoPaterno ?? ""} ${apellidoMaterno ?? ""} ${
          nombre ?? ""
        }`}
      />
      <Col xs={0} md={0} xl={8} />
      <InfoData info={etiquetas.CARGO_ACTUAL} data={`${nombreCargo ?? ""}`} />
      <InfoData
        info={etiquetas.CLAVE_ELECTOR}
        data={`${claveElectorFuar ?? ""}`}
      />
      {cargoAnterior ? (
        <>
          <Col xs={0} md={0} xl={8} />
          <InfoData
            info={etiquetas.CARGO_ANTERIOR}
            data={`${cargoAnterior ?? ""}`}
          />
          <Col xs={0} md={0} xl={8} />
        </>
      ) : (
        <></>
      )}
      <Col xs={0} md={0} xl={8} />
      {isMostrarZORE ? (
        <InfoData
          info={etiquetas.ZORE}
          data={`${numeroZonaResponsabilidad ?? ""}`}
        />
      ) : (
        <></>
      )}
      {isMostrarARE ? (
        <InfoData
          info={etiquetas.ARE}
          data={`${numeroAreaResponsabilidad ?? ""}`}
        />
      ) : (
        <></>
      )}

      <Col xs={0} md={0} xl={8} />
    </>
  );
};

export default VInfoAspirante;
