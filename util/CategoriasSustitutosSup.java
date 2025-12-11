package mx.ine.sustseycae.util;

public enum CategoriasSustitutosSup {
	caePromocionado {
		public String toString() {
			return "CAE Promocionados";
		}
	}, 
	caeConEvaluacionSE {
		public String toString() {
			return "CAE con evaluación de SE";
		}
	}, 
	caeSinEvaluacionSE {
		public String toString() {
			return "CAE sin evaluación de SE";
		}
	}, 
	caeDeclinaronEntrevistaSE {
		public String toString() {
			return "CAE que declinaron entrevista para SE";
		}
	},
	seRenunciaron {
		public String toString() {
			return "SE que renunciaron (recontratación)";
		}
	}, 
	seDeclinaron {
		public String toString() {
			return "SE que declinaron al contrato";
		}
	},
	aspConEvaluacionSE {
		public String toString() {
			return "Aspirantes con evaluación integral de SE";
		}
	}, 
	seRescisionContrato {
		public String toString() {
			return "SE con rescisión de contrato";
		}
	}, 
	caeRescisionContrato {
		public String toString() {
			return "CAE con rescisión de contrato";
		}
	}
}
