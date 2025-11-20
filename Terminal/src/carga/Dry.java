package carga;

import reportes.*;
import servicios.*;

public class Dry extends Container implements Visitable{
	private BillOfLanding carga;
	
	public Dry(String id, double ancho, double largo, double altura, double pesoTotal, BillOfLanding carga) {
		super(id, ancho, largo, altura, pesoTotal);
		this.carga = carga;
	}

	public void accept(Visitor visitante) {
		visitante.visit(this);
	}	
	
	public BillOfLanding getCarga() {
		return this.carga;
	}
	
	@Override
	public boolean aceptaServicio(Servicio servicio) {
		return servicio.getCompatibilidades().contains(Compatibilidad.DRY);
	}
}
