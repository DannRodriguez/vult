import apiClient from "../apiClient";

export const getAspiranteSustituto = async (request) => {
  try {
    const response = await apiClient.post("obtenerAspiranteSustituto", request);
    return response.data.data;
  } catch (error) {
    console.error("ERROR -getAspiranteSustituto: ", error);
    return "";
  }
};
