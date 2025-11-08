package busqueda;

import java.time.LocalDate;

import maritimo.Terminal;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class FiltroFechaLlegada implements FiltroDeBusqueda {
	private LocalDate fecha;
	private Terminal puertoDestino;
	
	public FiltroFechaLlegada(LocalDate fecha) {
		this.fecha = fecha;
		this.puertoDestino = null; 
	}
	
	public void setPuertoDestino(Terminal puerto) {
		this.puertoDestino = puerto;
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		if (puertoDestino != null) {
			LocalDate fechaLlegada = viaje.getFechaLlegadaA((TerminalPortuaria) puertoDestino).toLocalDate();
			return fechaLlegada.equals(fecha);
		}
		
		Terminal destino = viaje.getPuertoDestino();
		if (destino != null) {
			LocalDate fechaLlegada = viaje.getFechaLlegadaA((TerminalPortuaria) destino).toLocalDate();
			return fechaLlegada.equals(fecha);
		}
		return false;
	}
}

