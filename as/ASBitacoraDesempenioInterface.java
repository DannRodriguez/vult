package mx.ine.sustseycae.as;

import java.util.List;

import mx.ine.sustseycae.dto.DTOConsultaIntegrantes;
import mx.ine.sustseycae.dto.db.DTOBitacoraDesempenio;
import mx.ine.sustseycae.dto.db.DTOCEtiquetas;
import mx.ine.sustseycae.dto.db.DTOParticipacionGeografica;
import mx.ine.sustseycae.dto.db.DTOResponsablesBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOAspiranteBitacora;
import mx.ine.sustseycae.dto.vo.VOBitacoraDesempenio;
import mx.ine.sustseycae.dto.vo.VOCFrecuencias;
import mx.ine.sustseycae.dto.vo.VOCImpactos;
import mx.ine.sustseycae.dto.vo.VOCVocalesSust;
import mx.ine.sustseycae.dto.vo.VOCValoracionRiesgo;
import mx.ine.sustseycae.dto.vo.VOResponsableBitacoraDesempenio;

public interface ASBitacoraDesempenioInterface {

	/**
	 * Método para obtener el contenido completo del catalogo C_FRECUENCIAS
	 * 
	 * @return List<VOCFrecuencias>
	 */
	public List<VOCFrecuencias> obtenerCFrecuencias();

	/**
	 * Método para obtener el contenido completo del catalogo C_IMPACTOS
	 * 
	 * @return List<VOCImpactos>
	 */
	public List<VOCImpactos> obtenerCImpactos();

	/**
	 * Método para obtener el contenido completo del catalogo
	 * C_VALORACION_RIESGO_DESEMPENIO
	 * 
	 * @return List<VOCValoracionRiesgo>
	 */
	public List<VOCValoracionRiesgo> obtenerCValoracionRiesgo();

	/**
	 * Método pára obtener el registro de bitacora de desempenio de un aspirante
	 * 
	 * @param idDetalleProceso
	 * @param idParticipacion
	 * @param idAspirante
	 * 
	 * @returnVOBitacoraDesempenio
	 */
	public VOBitacoraDesempenio obtenerBitacoraDesempenioAspirante(Integer idDetalleProceso, Integer idParticipacion,
			Integer idAspirante);

	/**
	 * Método para obtener los responsables de una bitacora de desempenio
	 * 
	 * @param idDetalleProceso
	 * @param idParticipacion
	 * @param idBitacora
	 * 
	 * @return List<VOResponsableBitacoraDesempenio>
	 */
	public List<VOResponsableBitacoraDesempenio> obtenerResponsablesBitacoraDesempenio(Integer idDetalleProceso,
			Integer idParticipacion, Integer idBitacora);

	/**
	 * Método para obtener información complementaria
	 * de un funcionario SE/CAE o de un aspirante
	 * que fue seleccionado en el módulo de bitacora de desempenio
	 * 
	 * @param idDetalleProceso
	 * @param idParticipacion
	 * @param idAspirante
	 * 
	 * @return VOAspiranteBitacora
	 */
	public VOAspiranteBitacora obtenerAspiranteBitacora(Integer idDetalleProceso, Integer idParticipacion,
			Integer idAspirante);

	/**
	 * Obtiene los integrantes del ws de sesiones consejo
	 * 
	 * @param tipoIntegrante
	 * @param idEstado
	 * @param idDistritoFederal
	 * @param tipoPuesto
	 * @param estatus
	 * @param idProceso
	 * @param idDetalleProceso
	 * @param tipoOrdenamiento
	 * @param campoOrdenamiento
	 * 
	 * @return DTOConsultaIntegrantes con la lista de los Integrantes recuperados
	 */
	public DTOConsultaIntegrantes obtenerIntegrantesWsSesiones(List<Integer> tipoIntegrante, Integer idEstado,
			Integer idDistritoFederal, List<String> tipoPuesto, List<Integer> estatus, Integer idProceso,
			Integer idDetalleProceso, String tipoOrdenamiento, String campoOrdenamiento) throws Exception;

	/**
	 * @return DTOConsultaIntegrantes con la lista de los Integrantes recuperados
	 */
	public List<VOCVocalesSust> obtenerCatalogoVocalesSust(Integer idProceso,
			Integer idDetalleProceso);

	/**
	 * Mpetod para obtener participacion geografica
	 * 
	 * @param idProcesoElectoral
	 * @param idDetalleProceso
	 * @param idParticipacion
	 * 
	 * @return DTOParticipacionGeografica
	 */
	public DTOParticipacionGeografica obtenerParticipacionGeo(Integer idDetalleProceso, Integer idParticipacion);

	/**
	 * método para obtener el max registro de C_ETIQUETAS
	 * por proceso, detalleProceso e idEtiqueta
	 * 
	 * @param idProcesoElecoral
	 * @param idDetalleProceso
	 * @param idEtiqueta
	 * 
	 * @return DTOCEtiquetas
	 */
	public DTOCEtiquetas obtenerEtiquetaCEtiquetas(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idEtiqueta);

	/**
	 * Método para almecanar información de bitacora de desempeño
	 * 
	 * @param bitacoraDesempenio
	 * @param listResponsables
	 * @param idBitacora
	 */
	public void guardarBitacoraDesempenio(DTOBitacoraDesempenio bitacoraDesempenio,
			List<DTOResponsablesBitacoraDesempenio> listResponsables, Integer idBitacora);

	/**
	 * Método para borrar un registro de bitacora_desempenio y
	 * sus correspondientes registros de responsables_bitacora_desempenio
	 * 
	 * @param idDetalleProceso
	 * @param idParticipacion
	 * @param idBitacoraDesempenio
	 * @param idAspirante
	 * 
	 */
	public void eliminarBitacoraDesempenio(Integer idDetalleProceso, Integer idParticipacion,
			Integer idBitacoraDesempenio, Integer idAspirante);
}
