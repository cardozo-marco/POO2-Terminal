package servicios;

import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;

public class ServicioElectricidad implements Servicio{
	private double precioPorKW;
	
	private Set<Compatibilidad> compatibilidades = EnumSet.of(Compatibilidad.REEFER);

	@Override
	public double calcularCosto(Orden orden) {
		return orden.cantidadDeDias() * this.precioPorKW;
	}

	public ServicioElectricidad(double precioPorKW) {
		this.precioPorKW = precioPorKW;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibilidades;
	}
}