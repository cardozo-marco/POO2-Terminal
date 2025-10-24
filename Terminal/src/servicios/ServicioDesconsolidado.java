package servicios;

import ordenes.Orden;

public class ServicioDesconsolidado implements Servicio{
	private double costoFijo;

	@Override
	public double calcularCosto(Orden orden) {
		return costoFijo;
	}

	public ServicioDesconsolidado(double costoFijo) {
		this.costoFijo = costoFijo;
	}
	
}
