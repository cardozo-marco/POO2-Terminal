package busqueda;

import java.util.List;

import maritimo.CircuitoMaritimo;
import maritimo.Terminal;

public interface CriterioDeBusqueda {
	CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, Terminal origen, Terminal destino);
}

