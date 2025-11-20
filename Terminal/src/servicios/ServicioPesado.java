package servicios;

import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;

public class ServicioPesado implements Servicio{
	private double costoFijo;
	
	private Set<Compatibilidad> compatibilidades = EnumSet.of(Compatibilidad.DRY, Compatibilidad.REEFER, Compatibilidad.TANQUE);

	@Override
	public double calcularCosto(Orden orden) {
		return this.costoFijo;
	}

	public ServicioPesado(double costoFijo) {
		this.costoFijo = costoFijo;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibilidades;
	}
}
