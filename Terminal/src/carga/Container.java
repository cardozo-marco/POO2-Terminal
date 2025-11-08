package carga;

import reportes.*;

public abstract class Container implements Visitable{
	protected String id;
	
	protected double ancho;
	
	protected double largo;
	
	protected double altura;
	
	protected double pesoTotal;
	
	protected BillOfLanding carga;
	
	public double getVolumen() {
		return this.altura * this.ancho * this.largo;
	};

	public Container(String id, double ancho, double largo, double altura, double pesoTotal, BillOfLanding carga) {
		this.id = id;
		this.ancho = ancho;
		this.largo = largo;
		this.altura = altura;
		this.pesoTotal = pesoTotal;
		this.carga = carga;
	}
	
	public String getId() {
		return this.id;
	}
	
}
