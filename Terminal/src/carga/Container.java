package carga;

import java.util.*;

import reportes.*;
import servicios.Servicio;

public abstract class Container implements Visitable{
	protected String id;
	
	protected double ancho;
	
	protected double largo;
	
	protected double altura;
	
	protected double pesoTotal;
	
	public double getVolumen() {
		return this.altura * this.ancho * this.largo;
	};

	public Container(String id, double ancho, double largo, double altura, double pesoTotal) {
		this.id = id;
		this.ancho = ancho;
		this.largo = largo;
		this.altura = altura;
		this.pesoTotal = pesoTotal;
	}
	
	public String getId() {
		return this.id;
	}

	public abstract boolean aceptaServicio(Servicio servicio);
}

