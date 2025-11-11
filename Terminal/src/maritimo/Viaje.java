package maritimo;

import java.time.LocalDateTime;
import java.util.List;

public class Viaje {
	
	private CircuitoMaritimo circuito;
	private Buque buque;
	private LocalDateTime fechaInicio;
	
	public Viaje(CircuitoMaritimo circuito, Buque buque, LocalDateTime fechaInicio){
		this.circuito = circuito;
		this.setBuque(buque);
		this.setFechaInicio(fechaInicio);
	}

	public LocalDateTime getFechaLlegada(TerminalPortuaria terminal) {
		List<Tramo> tramos = circuito.getTramos();

	    double horasAcumuladas = 0.0;

	    for (Tramo tramo : tramos) {
	        horasAcumuladas += tramo.getTiempo();
	        if (tramo.getDestino().equals(terminal)) {
	            break;
	        }
	    }

	    LocalDateTime fechaHoraInicio = this.fechaInicio;

	    // Sumamos las horas acumuladas (convertimos la parte decimal en minutos)
	    int horasEnteras = (int) horasAcumuladas;
	    int minutos = (int) ((horasAcumuladas - horasEnteras) * 60);
	    LocalDateTime fechaHoraLlegada = fechaHoraInicio.plusHours(horasEnteras).plusMinutes(minutos);

	    return fechaHoraLlegada;
	}
	
	public TerminalPortuaria getTerminalOrigen() {
		List<Tramo> tramos = circuito.getTramos();
		if (tramos == null || tramos.isEmpty()) {
			return null;
		}
	    return tramos.get(0).getOrigen();
	}

	public TerminalPortuaria getTerminalDestino() {
		List<Tramo> tramos = circuito.getTramos();
		if (tramos == null || tramos.isEmpty()) {
			return null;
		}
	    return tramos.get(tramos.size() - 1).getDestino();
	}

	public CircuitoMaritimo getCircuito() {
		return circuito;
	}

	public Buque getBuque() {
		return buque;
	}

	public void setBuque(Buque buque) {
		this.buque = buque;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	
}
