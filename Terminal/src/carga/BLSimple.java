package carga;

public class BLSimple implements BillOfLanding{
	private String producto;
	
	private double peso;
	
	@Override
	public double getPesoTotal() {
		return this.peso;
	}

	public BLSimple(String producto, double peso) {
		this.producto = producto;
		this.peso = peso;
	}
	
}
