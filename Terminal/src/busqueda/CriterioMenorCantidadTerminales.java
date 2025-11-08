package busqueda;

import java.util.List;

import maritimo.CircuitoMaritimo;
import maritimo.Terminal;

public class CriterioMenorCantidadTerminales implements CriterioDeBusqueda {
	
	public CriterioMenorCantidadTerminales() {
	}
	
	@Override
	public CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, Terminal origen, Terminal destino) {
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

