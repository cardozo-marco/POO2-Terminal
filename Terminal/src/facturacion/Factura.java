package facturacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

public class Factura {
	private Cliente cliente;
	private LocalDate fecha;
	private double montoTotal;
	private List<ItemFactura> items;
	
	public Factura(Cliente cliente) {
		this.cliente = cliente;
		this.fecha = LocalDate.now();
		this.montoTotal = 0.0;
		this.items = new ArrayList<>();
	}
	
	public void agregarItem(String descripcion, double monto) {
		ItemFactura item = new ItemFactura(descripcion, monto);
		this.items.add(item);
		this.montoTotal += monto;
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}
	
	public double getMontoTotal() {
		return montoTotal;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
}

