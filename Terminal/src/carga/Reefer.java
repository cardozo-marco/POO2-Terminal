package carga;

import reportes.*;
import servicios.*;

public class Reefer extends Container implements Visitable{
	private double consumoKWPorHora;
	private BLSimple carga;
	
	public Reefer(String id, double ancho, double largo, double altura, double pesoTotal, BLSimple carga, double consumoKWPorHora) {
		super(id, ancho, largo, altura, pesoTotal);
		this.carga = carga;
		this.consumoKWPorHora = consumoKWPorHora;
	}
	
	public double getConsumoKWPorHora() {
		return this.consumoKWPorHora;
	}
	
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
	public BLSimple getCarga() {
		return this.carga;
	}

	@Override
	public boolean aceptaServicio(Servicio servicio) {
		return servicio.getCompatibilidades().contains(Compatibilidad.REEFER);
	}

}
