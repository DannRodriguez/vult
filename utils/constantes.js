/*------------- Constantes GENERALES -------------*/
export const ID_SISTEMA = 772;
export const VERSION_APPLICACION = 1;
export const NOMBRE_SISTEMA =
  "Sustitución de Supervisores Electorales y Capacitadores Asistentes Electorales";
export const TITULO_HEADER_LOGIN = "Sustitución de SE y CAE";
export const UNICOM = "Unidad Técnica de Servicios de Informática";
export const INE_COPYRIGHT = "©INE México 2026";
export const DECEYEC =
  "Dirección Ejecutiva de Capacitación  y Educación Cívica";
export const DECE = "Dirección de Capacitacion Electoral";
export const INCIA_SESION = "Inicia sesión";
export const URL_CENTRO_AYUDA =
  "https://intranetanterior.ine.mx/tutoriales/sistemas/Electorales/2024/local/SustSEyCAE/index.html";

//Constante del HOST para los distintos ambientes
export const URL_HOST = import.meta.env.VITE_URL_HOST;
export const URL_HOST_BITACORA = import.meta.env.VITE_URL_HOST_BITACORA;

export const URL_WS_SUPYCAP_CENTRAL = "/sustseycae/ws/";

//Llave creada con el correo reclutaseycae@gmail.com
export const RECAPTCHA_KEY = "6Lf8c18dAAAAAODUY6ry1JGvr96Zeoq3S2y-uTlu"; // KEY PARA LOS AMBIENTES PRU,SEG,CAO,PROD

export const DEFAULT_LOCALE = "es-MX";
export const EN_LOCALE = "en";

export const ROLES_CAPTURA = [
  "ELECINE.CAU.OC",
  "ELECINE.UNICOM.OC",
  "ELECINE.ADMIN.OC",
  "ELECINE.CAPTURA.OC",
  "ELECINE.CAPTURA.JL",
  "ELECINE.CAPTURA.JD",
  "ELECINE.CAPTURA_VECEYEC.JD",
  "ELECINE.CAPTURA_VECEYEC.JL",
  "ELECINE.CAPTURA_VE.JL",
  "ELECINE.CAPTURA_VE.JD",
  "ELECINE.CAPTURA_CONSEJERO.JL",
  "ELECINE.CONSEJERO.JD",
  "ELECINE.CAPTURA_VS.JL",
  "ELECINE.CAPTURA_VERFE.JL",
  "ELECINE.CAPTURA_VOE.JL",
  "ELECINE.CAPTURA_VS.JD",
  "ELECINE.CAPTURA_VERFE.JD",
  "ELECINE.CAPTURA_VOE.JD",
];
export const ROLES_OC = [
  "ELECINE.CAU.OC",
  "ELECINE.UNICOM.OC",
  "ELECINE.ADMIN.OC",
  "ELECINE.CAPTURA.OC",
  "ELECINE.CONSULTA.OC",
  "ELECINE.CONSULTA_RESTRINGIDA.OC",
];
export const ROLES_JL = [
  "ELECINE.CAPTURA.JL",
  "ELECINE.CAPTURA_VECEYEC.JL",
  "ELECINE.CAPTURA_VE.JL",
  "ELECINE.CAPTURA_CONSEJERO.JL",
  "ELECINE.CAPTURA_VS.JL",
  "ELECINE.CAPTURA_VERFE.JL",
  "ELECINE.CAPTURA_VOE.JL",
];
//,'ELECINE.CONSULTA_EXT.REST.OC' solo para reportes

export const COMPONENTE_HOME = "home";

export const REGEX_FORMATO_CLAVE_ELECTOR_FUAR =
  /^([A-Z]{5,6}\d{8}[H|M]\d{3})$|^([0-9]{13})$/i;
export const REGEX_FORMATO_NOMBRES = /^[A-ZÁÉÍÓÚÜÑ\'\°\.\-\/\s]*$/i;
export const REGEX_FORMATO_RFC = /^[A-Z]{3,4}[0-9]{6}([A-Z0-9]{3})*$/i;
export const REGEX_FORMATO_CURP =
  /^[A-Z]{4}[0-9]{6}[H|M][A-Z]{5}[A-Z0-9][0-9]$/i;
export const REGEX_FORMATO_CORREO =
  /^[_A-Z0-9-\+]+(\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\.[A-Z0-9]+)*(\.[A-Z]{2,})$/i;
export const REGEX_FORMATO_TELEFONO = /^\d{8,10}$/i;
export const REGEX_FORMATO_NO_CARACTERES_ESPECIALES =
  /^[^\|\\\u00B4\"\<\>\@\&\=\(\)]*$/i;
export const REGEX_FORMATO_SOLO_DIGITOS = /^\d*$/i;
export const REGEX_FORMATO_SOLO_DIGITOS_12 = /[\d]{12}/i;
export const REGEX_FORMATO_SOLO_DIGITOS_15 = /[\d]{15}/i;

export const FORMATO_FECHA_COTRO_MOMENT = "DD/MM/YYYY";
export const FORMATO_HORA_MINUTOS_SEGUNDOS_MOMENT = "HH:mm:ss";
export const FORMATO_HORA_MINUTOS_MOMENT = "HH:mm";
export const FORMATO_FECHA_COTRO_ISO_8601 = "YYYY-MM-DD";
export const FORMATO_HORA_MINUTOS_ISO_8601 = "HH:mm";

export const TIPO_SEDES_EXAMEN = "E";

export const CODE_SUCCESS = 200;
export const MSSG_ASIGNACION_FINAL_REALIZADA =
  "Ya se generó la asignación final, si desea realizar un cambio en la asignación de cargos favor de comunicarse con DECEYEC.";
export const MSSG_ASIGNACION_FINAL_REALIZADA_LISTA =
  "Ya se generó la asignación final, únicamente puede asignar cargos de lista de reserva.";
export const MSSG_ASIGNACION_PRELIMINAR_REALIZADA =
  'Ya se han asignado todos los cargos. Para concluir con el proceso es necesario oprimir el botón "Asignación Final".';
export const CARGO_SE = "Supervisor/a Electoral";
export const CARGO_CAE = "Capacitador/a Asistente Electoral";
export const CARGO_LISTA_RESERVA = "Lista de reserva";

export const FLUJO_CAPTURA = 1;
export const FLUJO_CONSULTA = 2;
export const FLUJO_MODIFICA = 3;

export const ACCION_CAPTURA = 1;
export const ACCION_CONSULTA = 2;
export const ACCION_MODIFICA = 3;
export const ACCION_ELIMINA = 4;

export const NOMBRE_MODULO_14 = "Asignación de cargo";
export const NOMBRE_MODULO_17 = "Eliminación de cargos";

export const BITACORA_ACCION_T_MODULO = 1;
export const BITACORA_ACCION_T_CEDULA = 2;
export const BITACORA_ACCION_T_LISTADO = 3;

export const EXP_REG_TEXT_AREA = /^(?![\s]*$)[^|\"\\<>&=()]*$/;
export const EXP_REX_ONLY_TEXT =
  /^[A-zÀ-ú-z0-9 _ . , / ( )]*[A-zÀ-ú-z0-9][A-zÀ-ú-z0-9 _ . , / ( )]*$/;

//CONSTANTES PARA COMPONENTE BusquedaSeCae
export const SUST_INCAPACIDAD = 1;
export const SUST_TERM_CONTRATO = 2;
export const SUST_RESC_CONTRATO = 3;
export const SUST_SEyCAE = 4;

//CONSTANTES DE LOS MODULOS DE REPORTES
export const ID_MENU_REPORTES = 6;
export const ID_MODULO_LISTADOS = 300;
export const ID_MODULO_CEDULAS = 301;

//load foto
export const MAX_TAMANIO_FOTO = 52000;
export const MSJ_FORMATO_INVALIDO_FOTO_SECAE = "Formato inv\u00E1lido";
export const MSJ_TAMANIO_INVALIDO_FOTO_SECAE =
  "El tama\u00F1o m\u00E1ximo permitido es de 50KB";
export const MSJ_FOTO_NO_DISPONIBLE =
  "La foto no está disponible. Inténtalo más tarde.";
export const MSJ_ERROR_FOTO =
  "Ocurrió un error al cargar la foto de la persona.";
export const MSJ_BORRAR_FOTO = "Borrar foto.";
export const ETQ_ADJUNTAR = "Adjuntar";
export const ETQ_CAMBIAR_FOTO = "Cambiar foto";
export const ETQ_SIN_FOTO = "Sin foto";
export const ETQ_FOTO = "Foto";
export const TITULO_MODULO_CERRADO = "Módulo cerrado";
export const MSJ_MODULO_CERRADO =
  "Este módulo no se encuentra disponible en este momento.";

//CONSTANTES para descarga de expedientes
export const ETQ_LISTADOS = "Listados";
export const NUM_EXPEDIENTE = "SustSEyCAE-4";
export const EXP_NO_DISPONIBLE = "No hay expediente disponible.";
export const DESCARGAR = "Descargar";
export const ID_PUESTO_LISTA_RESERVA = 3;
export const ID_PUESTO_RECONTRATACION_SE = 6;
export const DECLINO_CARGO = 1;
export const ID_PUESTO_RESCISION_SE = 4;
export const ID_PUESTO_RESCISION_CAE = 12;
export const ID_PUESTO_PROMOCION = 5;
export const ID_PUESTO_SE = 1;
