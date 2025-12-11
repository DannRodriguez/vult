package mx.ine.sustseycae.util;

public enum CategoriasSustitutosCapa {
	listaReservaCae9a10 {
		public String toString() {
			return "Lista de reserva con evaluación integral 9.1-10";
		}
	}, 
	listaReservaCae8a9 {
		public String toString() {
			return "Lista de reserva con evaluación integral 8.1-9";
		}
	}, 
	listaReservaCae6a8 {
		public String toString() {
			return "Lista de reserva con evaluación integral 6.0-8";
		}
	}, 
	listaReservaCaeMenor6 {
		public String toString() {
			return "Lista de reserva con evaluación integral menor a 6";
		}
	},
	caeRenunciaron {
		public String toString() {
			return "CAE que renunciaron (recontratación)";
		}
	}, 
	caeDeclinaronContrato {
		public String toString() {
			return "CAE que declinaron al contrato";
		}
	},
	caeIncapacidad {
		public String toString() {
			return "CAE con incapacidad";
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
	caeRescisionContrato {
		public String toString() {
			return "CAE con rescisión de contrato";
		}
	},
	seRescisionContrato {
		public String toString() {
			return "SE con rescisión de contrato";
		}
	}
}
