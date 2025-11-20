package carga;

import reportes.*;
import servicios.*;

public class Tanque extends Container implements Visitable{
	public BLSimple carga;
	
	public Tanque(String id, double ancho, double largo, double altura, double pesoTotal, BLSimple carga) {
		super(id, ancho, largo, altura, pesoTotal);
		this.carga = carga;
	}
	
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
	public BLSimple getCarga() {
		return this.carga;
	}

	@Override
	public boolean aceptaServicio(Servicio servicio) {
		return servicio.getCompatibilidades().contains(Compatibilidad.TANQUE);
	}

}
