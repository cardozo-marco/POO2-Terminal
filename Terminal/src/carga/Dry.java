package carga;

import reportes.*;

public class Dry extends Container implements Visitable{
	public Dry(String id, double ancho, double largo, double altura, double pesoTotal, BillOfLanding carga) {
		super(id, ancho, largo, altura, pesoTotal, carga);
	}

	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
}
