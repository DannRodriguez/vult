import apiClient from "../apiClient";

export function obtenerListaCausasVacante(request) {
  return new Promise((resolve, reject) => {
    apiClient
      .post("obtenerListaCausasVacante", request)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        console.error("ERROR -obtenerListaCausasVacante: ", error);
        reject(new Error(error));
      });
  });
}
