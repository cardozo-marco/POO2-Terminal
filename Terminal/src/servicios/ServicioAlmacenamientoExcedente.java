package servicios;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;
import ordenes.OrdenDeImportacion;

public class ServicioAlmacenamientoExcedente implements Servicio{
	private double costoPorDia;
	
	private Set<Compatibilidad> compatibilidades = EnumSet.of(Compatibilidad.DRY, Compatibilidad.REEFER, Compatibilidad.TANQUE);

	@Override
	public double calcularCosto(Orden orden) {
		// Para órdenes de importación, calcular días excedentes desde la fecha de llegada
		// (más allá de las 24 horas de tolerancia)
		if (orden.esOrdenDeImportacion()) {
			OrdenDeImportacion ordenImportacion = (OrdenDeImportacion) orden;
			LocalDateTime fechaLlegada = ordenImportacion.getFechaLlegadaCarga();
			LocalDateTime ahora = LocalDateTime.now();
			
			// Calcular horas desde la llegada
			long horasDiferencia = ChronoUnit.HOURS.between(fechaLlegada, ahora);
			
			// Si hay más de 24 horas, calcular días excedentes
			if (horasDiferencia > 24) {
				long horasExcedentes = horasDiferencia - 24;
				// Calcular días excedentes (redondeando hacia arriba)
				long diasExcedentes = horasExcedentes / 24;
				if (horasExcedentes % 24 > 0) {
					diasExcedentes++; // Si hay horas adicionales, cuenta como un día más
				}
				return diasExcedentes * this.costoPorDia;
			}
			return 0.0; // No hay días excedentes
		}
		
		// Para otras órdenes, usar cantidadDeDias() como antes
		return orden.cantidadDeDias() * this.costoPorDia;
	}

	public ServicioAlmacenamientoExcedente(double costoPorDia) {
		this.costoPorDia = costoPorDia;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibilidades;
	}
}
