package mx.ine.sustseycae.as;

import java.util.List;
import java.util.Set;

import mx.ine.sustseycae.dto.DTOCPermisosCta;
import mx.ine.sustseycae.dto.DTORespuestaWSAdmin;
import mx.ine.sustseycae.dto.db.DTOAspirantes;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOCParametros;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentas;
import mx.ine.sustseycae.dto.db.DTOCreacionCuentasId;
import mx.ine.sustseycae.dto.db.DTOGruposDefaultSistema;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;

public interface ASGestionCuentasInterface {

    public DTORespuestaWSAdmin.Usuario crearCuenta(DTOAspirantes aspirante, DTOCreacionCuentas cuentaPendiente);

    public boolean eliminarCuenta(DTOCreacionCuentas cuentaPendiente, String usuario, String ipUsuario,
            Integer ldapCuentas);

    public DTORespuestaWSAdmin.Usuario obtenerUsuarioPorMail(Integer grupoLdap, String mail);

    public DTOCreacionCuentas obtenerCreacionCtaAspirante(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante);

    public String obtenerDescripcionParametroCtasSeCae(Integer idParametro);

    public List<DTOCParametros> obtenerCParametros(Integer idProcesoElectoral, Integer idDetalleProceso,
            Set<Integer> idParametros);

    public DTOParticipacionGeografica obtenerParticipacionGeo(Integer idDetalleProceso, Integer idParticipacion);

    public Integer obtenerNumeroARE(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idAre);

    public Integer obtenerNumeroZORE(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idParticipacion,
            Integer idZore);

    public void guardarOActalizarCreacionCuenta(DTOCreacionCuentas creacionCuenta);

    public String creaEstructuraCorreoSistema(Integer idProceso, Integer idDetalle, Integer idParticipacion,
            Integer idAspirante, Integer idPuesto);

    public void actualizarAspiranteUidServicoUsado(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante, String uidCuenta, Integer serviocioUsado,
            String usuario, String ipUsuario);

    public List<DTOGruposDefaultSistema> obtenerPermisosCuenta(Integer idActor);

    public List<DTOCPermisosCta> asignarPermisosCuenta(List<DTOGruposDefaultSistema> permisos, String uid,
            String ipEjecucion, String usuario);

    public DTOCEtiquetas obtenerEtiquetaPorIdEtiquetaProceso(Integer idProcesoElectoral, Integer idEtiqueta);

    public void eliminarRegistroCreacionCuenta(DTOCreacionCuentasId IdCreacionCuenta);

    public DTOAspirantes obtenerAspirante(Integer idProcesoElectoral, Integer idDetalleProceso,
            Integer idParticipacion, Integer idAspirante);

}
