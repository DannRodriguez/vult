import { Row, Segmented } from "antd";

export function CMenuAcciones({ state, setState, menuAcciones }) {
  const acciones = () => {
    let options = [];
    if (menuAcciones != null && menuAcciones != undefined) {
      menuAcciones.forEach((element) => {
        let option = {
          label: element.accionDescrip,
          value: element.idAccion,
        };
        options.push(option);
      });
      const found = menuAcciones.find((element) => element.idAccion == state);
      if (!found) {
        setState(menuAcciones[0].idAccion);
      }
    }
    if (options != null && Object.keys(options).length === 0) {
      options.push({});
    }
    return options;
  };
  return (
    <Row
      justify="end"
      style={{ marginTop: "20px", marginLeft: "50px", marginRight: "50px" }}
    >
      <Segmented
        options={acciones()}
        value={state}
        onChange={(value) => setState(value)}
      />
    </Row>
  );
}
