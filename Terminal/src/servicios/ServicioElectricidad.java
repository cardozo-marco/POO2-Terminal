package servicios;

import ordenes.Orden;

public class ServicioElectricidad implements Servicio{
	private double precioPorKW;

	@Override
	public double calcularCosto(Orden orden) {
		return orden.cantidadDeDias() * this.precioPorKW;
	}

	public ServicioElectricidad(double precioPorKW) {
		this.precioPorKW = precioPorKW;
	}
	
}

