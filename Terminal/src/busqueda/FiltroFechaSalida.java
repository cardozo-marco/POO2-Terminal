package busqueda;

import java.time.LocalDate;

import maritimo.Viaje;

public class FiltroFechaSalida implements FiltroDeBusqueda {
	private LocalDate fecha;
	
	public FiltroFechaSalida(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		LocalDate fechaSalida = viaje.getFechaSalida();
		return fechaSalida.equals(fecha);
	}
}

