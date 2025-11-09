package busqueda;

import java.time.LocalDate;


import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class FiltroFechaLlegada implements FiltroDeBusqueda {
	private LocalDate fecha;
	private TerminalPortuaria puertoDestino;
	
	public FiltroFechaLlegada(LocalDate fecha) {
		this.fecha = fecha;
		this.puertoDestino = null; 
	}
	
	public void setPuertoDestino(TerminalPortuaria puerto) {
		this.puertoDestino = puerto;
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		if (puertoDestino != null) {
			LocalDate fechaLlegada = viaje.getFechaLlegada((TerminalPortuaria) puertoDestino).toLocalDate();
			return fechaLlegada.equals(fecha);
		}
		
		TerminalPortuaria destino = viaje.getTerminalDestino();
		if (destino != null) {
			LocalDate fechaLlegada = viaje.getFechaLlegada((TerminalPortuaria) destino).toLocalDate();
			return fechaLlegada.equals(fecha);
		}
		return false;
	}
}

