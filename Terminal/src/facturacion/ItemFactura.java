package facturacion;

public class ItemFactura {
	private String descripcion;
	private double monto;
	
	public ItemFactura(String descripcion, double monto) {
		this.descripcion = descripcion;
		this.monto = monto;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public double getMonto() {
		return monto;
	}
}

