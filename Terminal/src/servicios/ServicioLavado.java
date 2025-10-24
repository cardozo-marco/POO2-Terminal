package servicios;

import ordenes.Orden;

public class ServicioLavado implements Servicio{
	private double costoVolMenor;
	
	private double costoVolMayor;
	
	private double limiteVolumen = 70.0;

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
	
}


