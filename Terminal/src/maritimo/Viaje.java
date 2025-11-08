package maritimo;

import java.time.LocalDate;
import java.util.List;

public class Viaje {
	
	private CircuitoMaritimo circuito;
	private Buque buque;
	private LocalDate fechaInicio;
	
	public Viaje(CircuitoMaritimo circuito, Buque buque, LocalDate fechaInicio){
		this.circuito = circuito;
		this.setBuque(buque);
		this.setFechaInicio(fechaInicio);
	}
	//TODO
//	public LocalDate getFechaLlegada(TerminalPortuaria terminal) {
//		terminal.getFechaLlegada(this);
//	}
	
	public TerminalPortuaria getTerminalOrigen() {
	    return circuito.getTramos().get(0).getOrigen();
	}

	public TerminalPortuaria getTerminalDestino() {
		List<Tramo> tramos = circuito.getTramos();
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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
}
