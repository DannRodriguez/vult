import axios from "axios";
import { store } from "../redux/store";
import { notification } from "antd";
import { refreshToken } from "../redux/loginUser/loginUserReducer";
import { URL_HOST, URL_WS_SUPYCAP_CENTRAL } from "./constantes";

const alertExpiredSession = () => {
  notification["warning"]({
    message: "Su sesión no es válida.",
    description:
      "Su sesión ha vencido o ha sido cerrada desde otro navegador/dispositivo.",
  });
};

const apiClient = axios.create({
  baseURL: URL_HOST + URL_WS_SUPYCAP_CENTRAL,
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (request) => {
    const accessToken = store.getState().loginUser.currentUser?.tknA;
    if (accessToken) {
      request.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    return request;
  },
  (error) => {
    return Promise.reject(new Error(error));
  }
);

apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response?.status == 904) {
      alertExpiredSession();
      store.dispatch({ type: "RESET_STATE" });
      return Promise.reject(new Error(error));
    }

    if (
      (error.response?.status === 401 || error.response?.status === 902) &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true;
      try {
        await store.dispatch(refreshToken()).unwrap();
        const newAccessToken = store.getState().loginUser.currentUser?.tknA;
        apiClient.defaults.headers.common[
          "Authorization"
        ] = `Bearer ${newAccessToken}`;
        return apiClient(originalRequest);
      } catch (refreshError) {
        console.log("Token refresh failed: ", refreshError);
        alertExpiredSession();
        store.dispatch({ type: "RESET_STATE" });
        return Promise.reject(new Error(refreshError));
      }
    }

    return Promise.reject(new Error(error));
  }
);

export function apiClientPost(url, request) {
  return new Promise((resolve, reject) => {
    apiClient
      .post(url, request)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        console.error(`ERROR -${url}(${request}): `, error);
        reject(new Error(error));
      });
  });
}

export default apiClient;
