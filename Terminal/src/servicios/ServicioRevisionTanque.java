package servicios;

import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;

public class ServicioRevisionTanque implements Servicio{
	private double costoPorRevision;
	
	private Set<Compatibilidad> compatibilidades = EnumSet.of(Compatibilidad.TANQUE);

	@Override
	public double calcularCosto(Orden orden) {
		return this.costoPorRevision;
	}

	public ServicioRevisionTanque(double costoPorRevision) {
		this.costoPorRevision = costoPorRevision;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibilidades;
	}
}
