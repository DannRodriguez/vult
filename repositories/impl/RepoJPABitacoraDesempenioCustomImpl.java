package mx.ine.sustseycae.repositories.impl;

import java.math.BigDecimal;

import jakarta.persistence.Query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import mx.ine.sustseycae.repositories.RepoJPABitacoraDesempenioCustom;

public class RepoJPABitacoraDesempenioCustomImpl implements RepoJPABitacoraDesempenioCustom {

	@PersistenceContext(unitName = "sutSEyCAE-PU")
	private EntityManager entityManager;
	
	@Override
	public Integer getNextValSequenceIdBitacoraDesempeno(Integer idProcesoElectoral, Integer idDetalleProceso) {
		
		Query query = entityManager.createNativeQuery("SELECT SUPYCAP.S_BITACORA_DESEMPENO"+idProcesoElectoral+".NEXTVAL FROM DUAL");

		return ((BigDecimal) query.getSingleResult()).intValue();
	}

}
