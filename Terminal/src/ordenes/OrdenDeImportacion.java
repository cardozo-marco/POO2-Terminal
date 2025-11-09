package ordenes;

import java.time.*;
import java.time.temporal.ChronoUnit;

import actores.Camion;
import actores.Consignee;
import actores.Conductor;
import carga.*;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;
import reportes.*;

public class OrdenDeImportacion extends Orden implements Visitable{
	private LocalDateTime fechaLlegadaCarga;

	public OrdenDeImportacion(Consignee consignee, Container carga, Viaje viaje, Camion camion, Conductor conductor,
			TerminalPortuaria terminal) {
		super(consignee, carga, viaje, camion, conductor, terminal);
		this.fechaLlegadaCarga = viaje.getFechaLlegada(terminal);
	}
	
	public LocalDateTime getFechaLlegadaCarga() {
		return this.fechaLlegadaCarga;
	}

	@Override
	public double cantidadDeDias() {
		LocalDate fechaLlegada = this.fechaLlegadaCarga.toLocalDate();
		long cantidadDias = ChronoUnit.DAYS.between(fecha, fechaLlegada);
		return Math.abs(cantidadDias);
	}

	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
	@Override
	public boolean esOrdenDeImportacion() {
		return true;
	}
}


