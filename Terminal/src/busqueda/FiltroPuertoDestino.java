package busqueda;

import maritimo.Terminal;
import maritimo.Viaje;

public class FiltroPuertoDestino implements FiltroDeBusqueda {
	private Terminal puertoDestino;
	
	public FiltroPuertoDestino(Terminal puerto) {
		this.puertoDestino = puerto;
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		Terminal destino = viaje.getPuertoDestino();
		if (destino == null) {
			return false;
		}
		return destino.equals(puertoDestino);
	}
}
