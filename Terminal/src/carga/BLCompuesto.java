package carga;

import java.util.*;

public class BLCompuesto implements BillOfLanding{
	private List<BillOfLanding> hijos = new ArrayList<>();
	
	public void agregarProducto(BillOfLanding producto) {
		hijos.add(producto);
	}
	
	@Override
	public double getPesoTotal() {
		double pesoTotal = 0;
		for(BillOfLanding hijo : hijos) {
			pesoTotal = pesoTotal + hijo.getPesoTotal();
		}
		return pesoTotal;
	}
}
