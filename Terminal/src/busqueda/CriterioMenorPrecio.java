package busqueda;

import java.util.List;

import maritimo.CircuitoMaritimo;
import maritimo.Terminal;

public class CriterioMenorPrecio implements CriterioDeBusqueda {
	
	public CriterioMenorPrecio() {
	}
	
	@Override
	public CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, Terminal origen, Terminal destino) {
		CircuitoMaritimo mejorCircuito = null;
		double menorPrecio = Double.MAX_VALUE;
		
		for (CircuitoMaritimo circuito : circuitos) {
			if (circuito.contieneTerminales(origen, destino)) {
				double precio = circuito.precioEntreTerminales(origen, destino);
				if (precio < menorPrecio) {
					menorPrecio = precio;
					mejorCircuito = circuito;
				}
			}
		}
		
		return mejorCircuito;
	}
}

