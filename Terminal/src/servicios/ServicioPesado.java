package servicios;

import ordenes.Orden;

public class ServicioPesado implements Servicio{
	private double costoFijo;

	@Override
	public double calcularCosto(Orden orden) {
		return this.costoFijo;
	}

	public ServicioPesado(double costoFijo) {
		this.costoFijo = costoFijo;
	}
	
}
