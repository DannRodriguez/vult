package mx.ine.sustseycae.repositories;

import java.util.List;

import mx.ine.sustseycae.dto.DTOConsultaIntegrantes;

public interface RepoSesionesIntegrantesInterface {

	public DTOConsultaIntegrantes obtenerIntegrantesWsSesiones(String hostWSSesiones, List<Integer> tipoIntegrante,
			Integer idEstado, Integer idDistritoFederal, List<String> tipoPuesto, List<Integer> estatus,
			Integer idProceso, Integer idDetalleProceso, String tipoOrdenamiento, String campoOrdenamiento)
			throws Exception;

}
