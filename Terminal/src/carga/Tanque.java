package carga;

import reportes.*;

public class Tanque extends Container implements Visitable{
	public Tanque(String id, double ancho, double largo, double altura, double pesoTotal, BillOfLanding carga) {
		super(id, ancho, largo, altura, pesoTotal, carga);
	}
	
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
}
