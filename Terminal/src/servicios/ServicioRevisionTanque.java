package servicios;

import ordenes.Orden;

public class ServicioRevisionTanque implements Servicio{
	private double costoPorRevision;

	@Override
	public double calcularCosto(Orden orden) {
		return this.costoPorRevision;
	}

	public ServicioRevisionTanque(double costoPorRevision) {
		this.costoPorRevision = costoPorRevision;
	}
	
}
