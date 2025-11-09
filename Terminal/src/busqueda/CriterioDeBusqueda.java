package busqueda;

import java.util.List;


import maritimo.CircuitoMaritimo;
import maritimo.TerminalPortuaria;

public interface CriterioDeBusqueda {
	CircuitoMaritimo buscarCircuitos(List<CircuitoMaritimo> circuitos, TerminalPortuaria origen, TerminalPortuaria destino);
}

