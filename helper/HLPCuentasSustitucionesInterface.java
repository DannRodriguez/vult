package mx.ine.sustseycae.helper;

import mx.ine.sustseycae.dto.db.DTOAspirantes;

public interface HLPCuentasSustitucionesInterface {

    /**
     * Método para gestionar los proceso posibles relacionados a
     * creación/eliminación de cuentas en una sustitución.
     */
    public void adminCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta, DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario);

    /**
     * Método para crear una cuenta a un aspirante.
     */
    public void crearCuentaSustitucion(DTOAspirantes aspiranteCrearCuenta, String usuario);

    /**
     * Método para modificar los permisos de una cuenta SE/CAE .
     */
    public void modificarCuentaSustitucion(DTOAspirantes aspiranteModificarCuenta, String usuario);

    /**
     * Método para crear una cuenta a un aspirante y eliminar una cuenta de
     * SE/CAE.
     */
    public void eliminarCuentaSustitucion(DTOAspirantes aspiranteEliminarCuenta, String usuario);

    /**
     * Método para crear una cuenta a un aspirante y eliminar una cuenta de
     * SE/CAE.
     */
    public void crearYeliminarCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario);

    /**
     * Método para modificar los permisos de una cuenta SE/CAE y eliminar otra
     * cuenta de SE/CAE.
     */
    public void modificarYeliminarCuentasSustitucion(DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario);

    /**
     * Método para gestionar los proceso posibles relacionados a
     * creación/eliminación de cuentas en una sustitución.
     */
    public void crearYmodificarCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta,
            DTOAspirantes aspiranteModificarCuenta, String usuario);

}
