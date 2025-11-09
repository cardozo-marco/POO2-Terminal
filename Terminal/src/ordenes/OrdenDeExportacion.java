package ordenes;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import carga.*;
import paraPruebas.*;
import reportes.*;

public class OrdenDeExportacion extends Orden implements Visitable{
	private LocalDateTime turnoAsignado;
	
	public OrdenDeExportacion(Shipper shipper, Container carga, Viaje viaje, Camion camion, Conductor conductor,
			TerminalPortuaria terminal) {
		super(shipper, carga, viaje, camion, conductor, terminal);
	}
	
	public LocalDateTime getTurnoAsignado() {
		return this.turnoAsignado;
	}
	
	public void setTurnoAsignado(LocalDateTime turno) {
		this.turnoAsignado = turno; 
	}

	@Override
	public double cantidadDeDias() {
		LocalDate fechaTurno = turnoAsignado.toLocalDate();
		long cantidadDias = ChronoUnit.DAYS.between(fecha, fechaTurno);
		return cantidadDias;
	}

	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
}


