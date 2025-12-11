import React, { useEffect, useState } from "react";
import { Layout, Row, Col } from "antd";
import { DoubleRightOutlined } from "@ant-design/icons";

import LoadFoto from "../commonComponents/LoadFoto.jsx";
import VInfoAspirante from "../commonComponents/VInfoAspirante.jsx";

import * as etiquetas from "../../utils/causaSustitucion/etiquetas.js";

function Sustitutos({
  sustitucion,
  sustituto,
  vistaActual,
  setImagenB64,
  setExtensionImagen,
  setIdAspiranteImagen,
}) {
  const [cargo, setCargo] = useState("");

  useEffect(() => {
    if (sustituto?.idPuesto) {
      handleCargo();
      setIdAspiranteImagen(sustituto?.idAspirante);
    }
  }, [sustituto?.idPuesto]);

  const handleCargo = () => {
    switch (sustituto.idPuesto) {
      case 1:
        return setCargo(
          sustitucion.idPuestoSustituto == 10
            ? etiquetas.INFO_SUSTITUTO_SUPERVISOR
            : etiquetas.INFO_SUPERVISOR_INCAPACITADO
        );
      case 2:
        return setCargo(
          sustitucion.idPuestoSustituto == 8
            ? etiquetas.INFO_CAPACITADOR_INCAPACITADO
            : etiquetas.INFO_SUSTITUTO_SUPERVISOR
        );
      case 3:
      case 11:
        return setCargo(etiquetas.INFO_SUSTITUTO_CAPACITADOR);
      case 6:
      case 7:
        return setCargo(etiquetas.INFO_SUPERVISOR_INCAPACITADO);
      case 8:
      case 9:
        return setCargo(etiquetas.INFO_CAPACITADOR_INCAPACITADO);
      case 10:
        return setCargo(etiquetas.INFO_SUSTITUTO_SUPERVISOR);
    }
  };

  return (
    <Layout id="layout_container_causa_susitucion">
      <div>
        <span className="indicacion_rosa">
          {" "}
          <DoubleRightOutlined />{" "}
        </span>
        <span className="span_title_causa">{cargo}</span>
      </div>

      <Row style={{ marginTop: "20px" }}>
        <Col xs={20} md={15} xl={7}>
          <div style={{ justifyItems: "center" }}>
            <LoadFoto
              key={sustituto?.claveElectorFuar}
              tipoFlujo={vistaActual}
              urlFotoAspirante={sustituto?.urlFoto}
              onChangeFoto={({ imagenB64, extensionArchivo }) => {
                setImagenB64(imagenB64);
                setExtensionImagen(extensionArchivo);
              }}
            />
          </div>
        </Col>
        <Col xs={20} md={15} xl={17}>
          <Row>
            <VInfoAspirante
              folio={sustituto?.folio}
              apellidoPaterno={sustituto?.apellidoPaterno}
              apellidoMaterno={sustituto?.apellidoMaterno}
              nombre={sustituto?.nombre}
              nombreCargo={sustituto?.nombreCargo}
              claveElectorFuar={sustituto?.claveElectorFuar}
              isMostrarZORE={sustitucion.idPuestoSustituido == 1}
              numeroZonaResponsabilidad={sustituto?.numeroZonaResponsabilidad}
              isMostrarARE={true}
              numeroAreaResponsabilidad={sustituto?.numeroAreaResponsabilidad}
            />
          </Row>
        </Col>
      </Row>
    </Layout>
  );
}

export default Sustitutos;
