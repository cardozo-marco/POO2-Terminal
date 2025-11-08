package carga;

import reportes.*;

public class Reefer extends Container implements Visitable{
	private double consumoKWPorHora;
	
	public Reefer(String id, double ancho, double largo, double altura, double pesoTotal, BillOfLanding carga) {
		super(id, ancho, largo, altura, pesoTotal, carga);
	}
	
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
}

