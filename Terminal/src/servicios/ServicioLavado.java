package servicios;

import java.util.EnumSet;
import java.util.Set;

import ordenes.Orden;

public class ServicioLavado implements Servicio{
	private double costoVolMenor;
	
	private double costoVolMayor;
	
	private double limiteVolumen = 70.0;
	
	private Set<Compatibilidad> compatibiliades = EnumSet.of(Compatibilidad.DRY, Compatibilidad.REEFER, Compatibilidad.TANQUE);

	@Override
	public double calcularCosto(Orden orden) {
		if(orden.getVolumen() > this.limiteVolumen) {
			return this.costoVolMayor;
		}
		else {
			return this.costoVolMenor;
		}
	}

	public ServicioLavado(double costoVolMenor, double costoVolMayor) {
		this.costoVolMenor = costoVolMenor;
		this.costoVolMayor = costoVolMayor;
	}

	@Override
	public Set<Compatibilidad> getCompatibilidades() {
		return this.compatibiliades;
	}
}
