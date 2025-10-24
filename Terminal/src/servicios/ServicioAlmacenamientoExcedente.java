package servicios;

import ordenes.Orden;

public class ServicioAlmacenamientoExcedente implements Servicio{
	private double costoPorDia;

	@Override
	public double calcularCosto(Orden orden) {
		return orden.cantidadDeDias() * this.costoPorDia;
	}

	public ServicioAlmacenamientoExcedente(double costoPorDia) {
		this.costoPorDia = costoPorDia;
	}
	
}
