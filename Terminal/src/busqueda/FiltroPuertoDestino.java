package busqueda;

import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class FiltroPuertoDestino implements FiltroDeBusqueda {
	private TerminalPortuaria puertoDestino;
	
	public FiltroPuertoDestino(TerminalPortuaria puerto) {
		this.puertoDestino = puerto;
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		TerminalPortuaria destino = viaje.getTerminalDestino();
		if (destino == null) {
			return false;
		}
		return destino.equals(puertoDestino);
	}
}
