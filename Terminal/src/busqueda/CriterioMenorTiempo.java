package busqueda;

import java.util.List;


import maritimo.CircuitoMaritimo;
import maritimo.TerminalPortuaria;

public class CriterioMenorTiempo implements CriterioDeBusqueda {
	
	public CriterioMenorTiempo() {
	}
	
	@Override
	public CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, TerminalPortuaria origen, TerminalPortuaria destino) {
		CircuitoMaritimo mejorCircuito = null;
		double menorTiempo = Double.MAX_VALUE;
		
		for (CircuitoMaritimo circuito : circuitos) {
			if (circuito.contieneTerminales(origen, destino)) {
				double tiempo = circuito.tiempoEntreTerminales(origen, destino);
				if (tiempo < menorTiempo) {
					menorTiempo = tiempo;
					mejorCircuito = circuito;
				}
			}
		}
		
		return mejorCircuito;
	}
}

