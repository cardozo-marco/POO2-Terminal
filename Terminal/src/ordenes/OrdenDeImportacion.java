package ordenes;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import carga.*;
import paraPruebas.*;
import reportes.*;

public class OrdenDeImportacion extends Orden implements Visitable{
	private LocalDateTime fechaLlegadaCarga;

	public OrdenDeImportacion(Consignee consignee, Container carga, Viaje viaje, Camion camion, Conductor conductor,
			TerminalPortuaria terminal) {
		super(consignee, carga, viaje, camion, conductor, terminal);
		this.fechaLlegadaCarga = viaje.getFechaLlegadaA(terminal);
	}
	
	public LocalDateTime getFechaLlegadaCarga() {
		return this.fechaLlegadaCarga;
	}

	@Override
	public double cantidadDeDias() {
		LocalDate fechaLlegada = this.fechaLlegadaCarga.toLocalDate();
		long cantidadDias = ChronoUnit.DAYS.between(fecha, fechaLlegada);
		return cantidadDias;
	}

	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
}


