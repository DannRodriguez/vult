import apiClient from "../apiClient";

export function llenaCombo({ pendientesPorCubrir, supervisor, capacitador }) {
  let pendPorCubrir = {
    label: <span className="tituloDivisorCombo">Pendientes por cubrir</span>,
    title: "pendientesPorCubrir",
    options: creaOptions(pendientesPorCubrir, "pendientesPorCubrir"),
  };

  let sup = {
    label: <span className="tituloDivisorCombo">Supervisores/as</span>,
    title: "supervisor",
    options: creaOptions(supervisor, "supervisor"),
  };

  let cap = {
    label: <span className="tituloDivisorCombo">Capacitadores/as</span>,
    title: "capacitador",
    options: creaOptions(capacitador, "capacitador"),
  };

  let options = [];

  if (pendientesPorCubrir.length > 0) {
    options.push(pendPorCubrir);
  }
  options.push(sup);
  options.push(cap);

  return options;
}

function creaOptions(valorConvertir, tipoCombo) {
  let options = [];
  if (valorConvertir != null || valorConvertir != undefined) {
    valorConvertir.forEach((element) => {
      let option = {};
      if (tipoCombo == "pendientesPorCubrir") {
        option = {
          label: (
            <>
              {`${element.value}`} &nbsp;{" "}
              <span className="textoRosa">{element.causa}</span>
            </>
          ),
          busqueda: `${element.value}`,
          value: element.id,
          pendiente: true,
        };
      } else {
        option = {
          label: `${element.value}`,
          busqueda: `${element.value}`,
          value: element.id,
        };
      }

      options.push(option);
    });
  }
  return options;
}

export function obtenerFotoAspirante(request) {
  return new Promise((resolve, reject) => {
    apiClient
      .post("obtenerFotoAsp", request)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        console.error("ERROR -obtenerFotoAspirante: ", error);
        reject(new Error(error));
      });
  });
}
