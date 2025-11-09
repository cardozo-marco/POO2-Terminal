package busqueda;

import java.util.List;


import maritimo.CircuitoMaritimo;
import maritimo.TerminalPortuaria;

public class CriterioMenorCantidadTerminales implements CriterioDeBusqueda {
	
	public CriterioMenorCantidadTerminales() {
	}
	
	@Override
	public CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, TerminalPortuaria origen, TerminalPortuaria destino) {
		CircuitoMaritimo mejorCircuito = null;
		int menorCantidad = Integer.MAX_VALUE;
		
		for (CircuitoMaritimo circuito : circuitos) {
			if (circuito.contieneTerminales(origen, destino)) {
				int cantidad = circuito.cantidadTerminalesEntre(origen, destino);
				if (cantidad < menorCantidad) {
					menorCantidad = cantidad;
					mejorCircuito = circuito;
				}
			}
		}
		
		return mejorCircuito;
	}
}

