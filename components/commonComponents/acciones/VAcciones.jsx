import VAccion from "./VAccion";
import * as etiquetas from "../../../utils/acciones/etiquetas";

const VAcciones = ({ acciones }) => {
  return (
    <div className="bandeja-acciones-container">
      <span className="bac-info">{etiquetas.BANDEJA_ACCIONES_INFO}</span>
      <div className="bac-acciones">
        {acciones.map((accion) => (
          <VAccion key={accion} accion={etiquetas.ACCIONES_DATA[accion]} />
        ))}
      </div>
    </div>
  );
};

export default VAcciones;
