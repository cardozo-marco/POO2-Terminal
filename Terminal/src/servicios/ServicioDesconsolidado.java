package servicios;

import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;

public class ServicioDesconsolidado implements Servicio{
	private double costoFijo;
	
	private Set<Compatibilidad> compatibilidades = EnumSet.of(Compatibilidad.DRY);

	@Override
	public double calcularCosto(Orden orden) {
		return costoFijo;
	}

	public ServicioDesconsolidado(double costoFijo) {
		this.costoFijo = costoFijo;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibilidades;
	}
}
