package mx.ine.sustseycae.bsd;

import mx.ine.sustseycae.dto.db.DTOAspirantes;

public interface BSDGestionCuentasInterface {

    public void adminCuentasSustitucion(DTOAspirantes aspiranteCrearCuenta, DTOAspirantes aspiranteModificarCuenta,
            DTOAspirantes aspiranteEliminarCuenta, String usuario);

    public DTOAspirantes obtenerAspirante(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAspirante);

}
