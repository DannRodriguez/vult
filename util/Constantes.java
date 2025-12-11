package mx.ine.sustseycae.util;

import mx.ine.sustseycae.dto.db.DTOAspirantes;

/**
 * @author Ricardo Rodr&iacute;guez de los Santos
 * @copyright Direcci&oacute;n de sistemas - INE
 * @since 21/12/2017
 */
public class Constantes {

    /**
     * Cadena representativa del nombre completo del usuario.
     */
    public static final String NOMBRE = "nombre";

    /**
     * Cadena respresentantiva del correo del usuario.
     */
    public static final String CORREO = "mail";

    /**
     * Cadena representativa de id estado.
     */
    public static final String ID_ESTADO = "idEstado";

    /**
     * Cadena representativa de id distrito.
     */
    public static final String ID_DISTRITO = "idDistrito";

    /**
     * Cadena representativa del rol del usuario.
     */
    public static final String ROL = "rol";

    /**
     * Cadena representativa del area de adscripci&oacute;n del usuario.
     */
    public static final String AREA_ADSCRIPCION = "area";

    /**
     * Cadena representativa del puesto del usuario.
     */
    public static final String PUESTO = "puesto";

    /**
     * Cadena vacia.
     */
    public static final String EMPTY = "";

    /**
     * clave del us
     */
    public static final String CLAVE_US = "claveUs";

    /**
     * Indica tipo de token
     */
    public static final String TIPO_TOKEN = "tipoToken";

    /**
     * UUID del token generado
     */
    public static final String UUIDJWT = "idjwt";

    public static final String USUARIO = "nameU";

    /**
     * Indica tipo de token: 0:authentication
     */
    public static final int TIPO_TOKEN_A = 0;

    /**
     * Indica tipo de token: 1:refresh
     */
    public static final int TIPO_TOKEN_R = 1;

    /**
     * Indica version de la app
     */
    public static final String VERSION_APP = "versionApp";

    /**
     * C&oacute;digo Personalizado HTTP 900
     */
    public static final Integer CODIGO_900_TKN_FIRMA_INCORRECTA = 900;

    /**
     * C&oacute;digo Personalizado HTTP 901
     */
    public static final Integer CODIGO_901_TKN_MALFORMADO = 901;

    /**
     * C&oacute;digo Personalizado HTTP 902
     */
    public static final Integer CODIGO_902_TKN_EXPIRADO = 902;

    /**
     * C&oacute;digo Personalizado HTTP 903
     */
    public static final Integer CODIGO_903_TKN_NO_SOPORTADO = 903;

    /**
     * C&oacute;digo Personalizado HTTP 904
     */
    public static final Integer CODIGO_904_TKN_NO_VALIDO = 904;

    /**
     * C&oacute;digo HTTP 400
     */
    public static final Integer CODIGO_400 = 400;

    /**
     * C&oacute;digo HTTP 500
     */
    public static final Integer CODIGO_500 = 500;

    /**
     * Código para indicar que el password o el usuario es incorrecto
     */
    public static final Integer CODIGO_USUARIO_PASSWORD_INCORRECTOS = 202;

    /**
     * Código para indicar que el usuario cuenta con una sesión activa
     */
    public static final Integer CODIGO_USUARIO_CON_SESION_ACTIVA = 205;

    /**
     * Código para el cierre de sesión, cuando no se encuentra la sesión del
     * usuario que quiere cerrar sesión
     */
    public static final Integer CODIGO_NO_EXISTE_SESION = 206;

    /**
     * Indica id_sistema
     */
    public static final Integer ID_SISTEMA = 772;

    /**
     * Indica El estatus del registro mov_bitacora_login (1 activo; 0 inactivo)
     */
    public static final Integer ESTATUS_ACTIVO_MOVBLOGIN = 1;

    public static final Integer ESTATUS_INACTIVO_MOVBLOGIN = 0;

    public static final Integer MODULO_ABIERTO = 1;

    /**
     * Indica valor del modulo que desea hacer la consulta
     */
    public static final Integer SUST_INCAPACIDAD = 1;
    public static final Integer SUST_TERM_CONTRATO = 2;
    public static final Integer SUST_RESC_CONTRATO = 3;
    public static final Integer SUST_SEyCAE = 4;
    public static final Integer BITACORA_DESEMP = 5;

    /**
     * Indica valor del tipo de consulta que se hará
     */
    public static final Integer FLUJO_CAPTURA = 1;
    public static final Integer FLUJO_CONSULTA = 2;
    public static final Integer FLUJO_MODIFICA = 3;

    // constantes creación de cuentas
    public static final Integer ID_PUESTO_SE = 1;
    public static final Integer ID_PUESTO_CAE = 2;
    public static final Integer ID_PUESTO_SE_TMP = 10;
    public static final Integer ID_PUESTO_CAE_TMP = 11;
    public static final Integer ID_PUESTO_REC_SE = 6;
    public static final Integer ID_PUESTO_REC_CAE = 9;
    public static final Integer ID_PUESTO_SE_RESC = 4;
    public static final Integer ID_PUESTO_CAE_RESC = 12;

    public static final Integer ID_PUESTO_CAE_PROMOCION = 5;

    // CONSTANTES SUST INCAPACIDAD
    public static final Integer ID_PUESTO_SE_INCAP = 7;
    public static final Integer ID_PUESTO_CAE_INCAP = 8;

    public static final Integer ESTATUS_CUENTA_SIN_CREAR = 1;
    public static final Integer ESTATUS_CUENTA_CREADA = 2;
    public static final Integer ESTATUS_CUENTA_PENDIENTE_MODIFICAR = 3;
    public static final Integer ESTATUS_CUENTA_PENDIENTE_ELIMINAR = 4;
    public static final Integer ESTATUS_CUENTA_ELIMINADA = 5;
    public static final Integer ESTATUS_PERMISOS_SIN_ASIGNAR = 1;
    public static final Integer ESTATUS_PERMISOS_ASIGNADOS = 2;

    public static final Integer ID_PARAM_ADMIN_CUENTAS_POLITICAS = 2;
    public static final Integer ID_PARAM_ADMIN_CUENTAS_CAMBIO_CONTRA = 3;
    public static final Integer ID_ETIQUETA_WS_CREACION_CTA = 10;

    public static final Integer INTENTOS_CREAR_CUENTA = 2;
    public static final Integer INTENTOS_ASIGNAR_PERMISOS = 1;
    public static final Integer VALOR_SERVICIO_AUS = 1;

    public static final Integer ID_PARAM_LDAP_CUENTAS = 30;
    public static final String ETIQUETA_USUARIO_SUST = "sustSEyCAE";
    public static final Integer ID_ETIQUETA_CARPETA_SUPYCAP = 92;
    public static final Integer CODIGO_ERROR_GENERAR_ACUSE = 20;

    /**
     * Etiquetas para generar acuse/comprobante de cuenta
     */
    public static final String ETIQUETA_ACUSE_PDF_TITULO = "Acuse de cuenta";
    public static final String ETIQUETA_ACUSE_TITULO = "Seguimiento SE y CAE";
    public static final String ETIQUETA_ACUSE_EMISION = "Emisi\u00f3n del acuse";
    public static final String ETIQUETA_ACUSE_FECHA = "Fecha";
    public static final String ETIQUETA_ACUSE_HORA = "Hora";
    public static final String ETIQUETA_ACUSE_SALUDO = "\u00a1Hola! Tienes una nueva cuenta. Con\u00f3cela.";
    public static final String ETIQUETA_ACUSE_SALUDO_NOTIFICA = "\u00a1Hola! Se ha creado la siguiente cuenta.";
    public static final String ETIQUETA_ACUSE_TITULAR = "Titular";
    public static final String ETIQUETA_ACUSE_NOMBRE_USUARIO = "Nombre de usuario";
    public static final String ETIQUETA_ACUSE_NOMBRE_CONTRASENA = "Contrase\u00f1a";
    public static final String ETIQUETA_ACUSE_CAMBIO_CONTRASENIA = "Por seguridad ingresa a la siguente direcci\u00f3n electr\u00f3nica para modificar tu contrase\u00f1a :";
    public static final String ETIQUETA_ACUSE_ADVERTENCIA = "La Unidad de Servicios de Inform\u00e1tica, as\u00ed como las dem\u00e1s \u00e1reas del Instituto,";
    public static final String ETIQUETA_ACUSE_ADVERTENCIA_DOS = "nunca te solicitar\u00e1n tu contrase\u00f1a por tel\u00e9fono o por correo electr\u00f3nico.";
    public static final String ETIQUETA_ACUSE_POLITICAS_USO = "Conoce las pol\u00edticas de uso de la cuenta externa de acceso";
    public static final String ETIQUETA_ACUSE_RATIFICO = "* Ratifico entender y acepto las pol\u00edticas de uso de la cuenta \u00fanica de acceso asignada.";
    public static final String ETIQUETA_ACUSE_SISTEMA = "Sistema";
    public static final String ETIQUETA_ACUSE_PERMISO = "Permiso";
    public static final String ETIQUETA_ACUSE_INDICACION_PERMISOS = "Te asignaron los siguientes permisos";
    public static final String ETIQUETA_ACUSE_INDICACION_PERMISOS_NOTIFICA = "Se le asignaron los siguientes permisos";
    public static final String ETIQUETA_ACUSE_CAMBIO_PERMISOS = "\u00a1Recuerda! Si requieres alg\u00fan cambio en los permisos asignados, puedes encontrar apoyo con el personal de la Junta Distrital Ejecutiva designada para la resoluci\u00f3n de tu caso.";
    public static final String MENSAJE_ADMIN_USUARIOS_ERROR_COMPROBANTE = "Sucedi\u00f3 un error al enviar, por correo electr\u00f3nico, el comprobante de la cuenta";

    public static final String ETIQUETA_CORREOCREACIONCTA_P1 = "Estimado Usuario,";
    public static final String ETIQUETA_CORREOCREACIONCTA_P2_NVA = "Se ha creado una cuenta a tu nombre a trav\u00e9s del Sistema de SUPyCAP del Instituto Nacional Electoral.";
    public static final String ETIQUETA_CORREOCREACIONCTA_P2_NOT = "Se ha creado una cuenta a trav\u00e9s del Sistema de SUPyCAP del Instituto Nacional Electoral.";
    public static final String ETIQUETA_CORREOCREACIONCTA_P3_NVA = "El detalle de tu nueva cuenta se encuentra en el archivo adjunto.";
    public static final String ETIQUETA_CORREOCREACIONCTA_P3_NOT = "El detalle de la misma se encuentra en el archivo adjunto.";
    public static final String ETIQUETA_CORREOCREACIONCTA_P5 = "Puedes validar el acceso de tu cuenta en la aplicaci\u00f3n validaUsuarioDeSEyCAE disponible en la tienda Play Store de Google.";
    public static final String ETIQUETA_CORREOCREACIONCTA_P4 = "Si tienes alguna duda, por favor comun\u00edcate al Centro de Atenci\u00f3n de Usuarios, CAU: Servicio en l\u00ednea cau@ine.mx"; // Tel\u00e9fonos:
    // IP
    // 348110
    // y
    // 01
    // (55)
    // 54838110.";

    public static final String ETIQUETA_CORREO_CUENTA_ASUNTO = "[SUPyCAP] Acuse de creaci\u00f3n de cuenta";
    public static final String ETIQUETA_CORREO_CUENTA_CONTENIDO = "\u00a1Hola! Te han creado una cuenta, consulta el archivo adjunto.";
    public static final String ETIQUETA_ADMINUSUARIOS_CORREO_PASS_ASUNTO = "Contrase\u00f1a restablecida - Comprobante";
    public static final String ETIQUETA_ADMINUSUARIOS_CORREO_PASS_CONTENIDO = "La contrase\u00f1a ha sido restablecida correctamente.";

    public static final String ETIQUETA_CARATULA_SE = "SE";
    public static final String ETIQUETA_CARATULA_CAE = "CAE";
    public static final String ETIQUETA_CARGOS_NOMBRE_COMPROBANTE = "_NVA_CUENTA.pdf";

    // Datos de correo
    public static final String APPLICATION_CORREO_CUENTA = "admin.cuentas@ine.mx";

    // Datos de correo de Excepciones
    public static final String CORREO_NOTIFICACION_ACUSE_CUENTA = "correosegseycae@gmail.com";

    public static final String PARA = "TO";
    public static final String CC = "CC";
    public static final String CCO = "BCC";
    public static final String MAIL_CAU = "cau@ine.mx";

    public static final String IMAGEN_SEGUIMIENTO = "supycapv11/resources/imgSUPyCAP/seguimiento.png";
    public static final String IMAGEN_FOOTER = "supycapv11/resources/imgSUPyCAP/footer.png";

    // FECHAS de C_FECHAS
    /*
     * 18 Inicio Periodo de contratación de SE 19 Fin Periodo de contratación de SE
     * 20 Inicio Periodo de contratación de CAE 21 Fin Periodo de contratación de
     * CAE
     */
    public static final Integer F_INI_CONTRATACION_SE = 18;
    public static final Integer F_FIN_CONTRATACION_SE = 19;
    public static final Integer F_INI_CONTRATACION_CAE = 20;
    public static final Integer F_FIN_CONTRATACION_CAE = 21;

    public static final Integer RESPONSE_CODE_200 = 200;
    public static final Integer RESPONSE_CODE_400 = 400;
    public static final Integer RESPONSE_CODE_404 = 404;
    public static final Integer RESPONSE_CODE_500 = 500;

    public static final String ESTATUS_EXITO = "\u00C9xito";
    public static final String ESTATUS_ADVERTENCIA = "Advertencia";
    public static final String ESTATUS_ERROR = "Error";

    public static final String MSG_VALOR_NULL = "Par\u00e1metro de entrada nulo o incorrecto";
    public static final String MSG_EXITO = "El servicio se ejecut\u00f3 correctamente";
    public static final String MSG_ERROR_WS = "Error al ejecutar el servicio";
    public static final String CAUSA_ERROR_WS = "Sucedi\u00f3 una excepci\u00f3n interna en el servidor al momento de ejecutar el servicio web.";
    public static final String CAUSA_ERROR_CAU = "Ocurri\u00f3 un error. Comunicate al CAU.";

    public static final String MSG_EXITO_SUST = "\u00a1El registro se realiz\u00f3 con \u00C9xito!";
    public static final String MSG_EXITO_SUST_NO_BITACORA = "\u00a1El registro de la sustitución se realiz\u00f3 con \u00C9xito!     La bitacora de desempeño no se logr\u00f3 almacenar.";
    public static final String MSG_EXITO_SUST_BITACORA = "\u00a1La bit\u00e1cora de desempe\u00f1o se registr\u00f3 con \u00C9xito!";

    public static final String MSG_EXITO_GUARDAR_BITACORA = "La bit\u00E1cora se guard\u00f3 con \u00e9xito.";
    public static final String MSG_EXITO_GUARDAR_BITACORA_ERROR = "La bit\u00E1cora se guard\u00f3 con \u00e9xito. El soporte documental no se pudo almacenar.";
    public static final String MSG_ERROR_INFO_BITACORA = "ControllerBitacoraDesempenioImpl.obtenerExpedienteDesemp - ocurri\u00f3 un error al obtener la informaci\u00f3n de la bit\u00e1cora - ";
    public static final String MSG_ERROR_GUARDAR_BITACORA = "ControllerBitacoraDesempenioImpl.guardarBitacoraDesemp - ocurri\u00f3 un error al guardar bitácora -  ";
    public static final String MSG_ERROR_GUARDAR_ELLIM_ARCHIVO_BITACORA = "BSDBitacoraDesempenoImpl - guardarBitacora: error al eliminar el documento : ";
    public static final String MSG_ERROR_ELIMINAR_BITACORA = "ControllerBitacoraDesempenioImpl.eliminarBitacoraDesemp - ocurri\u00f3 un error al eliminar bitácora - ";
    public static final String MSG_ERROR_ACCION_BITACORA = "El valor del tipo acci\u00f3n es incorrecto";
    public static final String MSG_ERROR_ORIGEN_BITACORA = "El valor del origen de bit\u00e1cora es incorrecto";
    public static final String MSG_ERROR_ID_BITACORA = "El valor del id bit\u00e1cora de desempeño es incorrecto";
    public static final String MSG_ERROR_FALTA_INFO_BITACORA = "Falta información de la evaluación de desempeño";
    public static final String MSG_ERROR_RUTA_ARCHIVO_BITACORA = "No se encontró el archivo en la ruta especificada.";
    public static final String MSG_ERROR_LEER_ARCHIVO_BITACORA = "BSDBitacoraDesempenioImpl - obtenerBase64Expediente : Error al leer el archivo: ";
    public static final String MSG_ERROR_GUARDAR_ARCHIVO_BITACORA = "BSDBitacoraDesempenoImpl - guardarBitacora:: error al guardar el soporte documental- ";

    public static final String MSG_ERROR_DESHACER_ZORES_E1 = "Existe un intercambio entre ZOREs en la etapa 1.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_ARES_E1 = "Existe un intercambio entre AREs en la etapa 1.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_ZORES_E2 = "Existe un intercambio entre ZOREs en la etapa 2.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_ARES_E2 = "Existe un intercambio entre AREs en la etapa 2.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_ZORE_CAMBIO = "Existe un intercambio entre ZOREs.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_ARE_CAMBIO = "Existe un intercambio entre AREs.\nPara continuar, debes eliminar el intercambio desde el sistema de Seguimiento de SE y CAE.";
    public static final String MSG_ERROR_DESHACER_NO_PERMITIDO = "Movimiento no permitido debido a que se perder\u00eda el puesto que ocupa el sustituido: ";
    public static final String MSG_ERROR_DESHACER_SIN_RELACION = "No se ha encontrado sustituci\u00f3n con ese id relación.";
    public static final String MSG_ERROR_DESHACER_MAS_RELACIONES = "Existen m\u00e1s de dos sustituciones relacionadas.";

    public static final Integer ORIGEN_MODULO_BITACORA = 1;
    public static final Integer ORIGEN_MODULO_SUST = 2;

    public static final Integer ID_ETIQUETA_CARPETA_FOTOS = 2;
    public static final String CARPETA_WEB_GLUSTER = "web";
    public static final String CARPETA_EXPEDIENTE_GLUSTER = "expedientes_desempenio";

    public static final Integer TIPO_CAUSA_VACANTE_OTRAS_CAUSAS = 3;

    /**
     * causas sustitucion sustSEyCAE
     */
    public static final DTOAspirantes ASPIRANTE_SIN_INFORMACION = null;
    public static final Integer TIPO_CAUSA_RESCISION = 1;
    public static final Integer TIPO_CAUSA_TERMINACION = 2;
    public static final Integer TIPO_OTRAS_CAUSAS = 3;

    public static final Integer ID_CAUSA_VACANTE_FALLECIMIENTO = 1;
    public static final Integer ID_CAUSA_VACANTE_PROMOCION = 2;
    public static final Integer ID_CAUSA_VACANTE_DECLINAR = 3;

    public static final Integer ID_PUESTO_LISTA_RESERVA = 3;
    public static final Integer ID_PUESTO_RESCISION_SE = 4;
    public static final Integer ID_PUESTO_RESCISION_CAE = 12;
    public static final Integer ID_PUESTO_RECONTRATACION_SE = 6;
    public static final Integer ID_PUESTO_RECONTRATACION_CAE = 9;
    public static final Integer ID_PUESTO_PROMOCION = 5;
    public static final Integer ID_PUESTO_FALLECIMIENTO = 13;
    public static final Integer ID_FECHA_INCIO_2DA_ETAPA = 24;

    public static final Integer ID_FECHA_INICIO_SUSTITUCIONES = 37;
    public static final Integer ID_FECHA_FIN_SUSTITUCIONES = 36;

    public static final Integer DECLINO_CARGO = 1;

    /**
     * Etiquetas y parametros que se ocupan para consultar el servicio web de
     * ADMIN DECEYEC
     */
    public static final Integer ID_PARAMETRO_USAR_WS_ADMIN_DECEYEC = 50;
    public static final Integer ID_ETIQUETA_HOST_WS_ADMIN_DECEYEC = 95;
    public static final Integer ID_ETIQUETA_HEADER_WS_ADMIN_DECEYEC = 96;

    /**
     * Constantes que se ocupan para consultar el servicio web de ADMIN DECEYEC
     */
    public static final String WSPARAM_GEOGRAFICO = "/wsParam-geografico";
    public static final String WSPARAM_MENU = "/wsParam-menu";
    public static final String WSPARAM_ESTATUS = "/wsParam-estatus";

    public static final String API_GEOGRAFICO = "/api/geografico";
    public static final String API_MENU = "/api/menu";
    public static final String API_MODULO = "/api/modulo";

    public static final String API_GEOGRAFICO_ESTADOS_CON_PROCESO_VIG = "/estadosConProcesosVigentes";
    public static final String API_GEOGRAFICO_PROCESOS = "/procesos";
    public static final String API_GEOGRAFICO_DISTRITOS_FEDERALES = "/distritosFederales";
    public static final String API_MENU_LATERAL = "/menuLateral";
    public static final String API_MENU_ACCIONES = "/menuAcciones";
    public static final String API_MODULO_ESTATUS = "/estatus";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_APP_JSON = "application/json";

    public static final String MSG_REQUEST_SISTEMA_NOT_NULL = "El sistema no puede ser nulo";
    public static final String MSG_REQUEST_SISTEMA_MIN = "El sistema debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_SISTEMA_MAX = "El sistema no debe de ser mayor a 5 caracteres";

    public static final String MSG_REQUEST_PROCESO_NOT_NULL = "El proceso electoral no puede ser nulo";
    public static final String MSG_REQUEST_PROCESO_MIN = "El proceso electoral debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_PROCESO_MAX = "El proceso electoral no debe de ser mayor a 5 caracteres";

    public static final String MSG_REQUEST_DETALLE_NOT_NULL = "El detalle no puede ser nulo";
    public static final String MSG_REQUEST_DETALLE_MIN = "El detalle debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_DETALLE_MAX = "El detalle no debe de ser mayor a 7 caracteres";

    public static final String MSG_REQUEST_ESTADO_NOT_NULL = "El estado no puede ser nulo";
    public static final String MSG_REQUEST_ESTADO_MIN = "El estado debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_ESTADO_MAX = "El estado no debe de ser mayor a 2 caracteres";

    public static final String MSG_REQUEST_AMBITO_PATTERN = "El ámbito del usuario puede contener sólo puede ser L-local o F-federal";
    public static final String MSG_REQUEST_VIGENCIA_PATTERN = "La vigencia sólo puede ser S-Vigente o N-No vigente o A-Ambos";

    public static final String MSG_REQUEST_GRUPO_NOT_NULL = "El grupo no puede ser null";
    public static final String MSG_REQUEST_GRUPO_PATTERN = "El grupo sistema puede contener sólo letras, números, puntos y guiones bajos";

    public static final String MSG_REQUEST_PARTICIPACION_NOT_NULL = "La participación geográfica no puede ser nula";
    public static final String MSG_REQUEST_PARTICIPACION_MIN = "La participación geográfica debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_PARTICIPACION_MAX = "La participación geográfica no debe de ser mayor a 3 caracteres";

    public static final String MSG_REQUEST_ASPIRANTE_MIN = "El identificador del aspirante debe de ser mayor o igual a cero";
    public static final String MSG_REQUEST_DESC_MAX = "La descripción no puede exceder los 50 caracteres";

    /**
     * Constantes para definir los JNDI para ocupar el jar de parametrización
     */
    public static final String JNDI_PARAM_SUSTSUPYCAP = "paramSUSTSUPYCAPjndi";
    public static final String JNDI_MENU_SUSTSUPYCAP = "menuSUSTSUPYCAPjndi";

    /**
     * Etiquetas y parametros que se ocupan para el envió de notificaciones de
     * la creación de cuentas
     */
    public static final Integer ID_PARAMETRO_ENVIAR_CORREO = 51;
    public static final Integer ID_PARAMETRO_USAR_CORREO_INSTITUCIONAL = 52;
    public static final Integer ID_ETIQUETA_CORREO_INSTITUCIONAL = 98;

    /**
     * Constructor privado
     *
     * @author Jos&eacute; Carlos Ortega Romano
     * @since 27/10/2017
     */
    private Constantes() {
        // Constructor privado utilizado para evitar la creaci&oacute;n de instancias.
    }
}
