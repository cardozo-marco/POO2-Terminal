package entidades;

import java.util.ArrayList;
import java.util.List;

import facturacion.Factura;

public class Cliente {
	protected String nombre;
	protected String email;
	private List<String> notificacionesRecibidas;
	private List<Factura> facturasRecibidas;
	
	public Cliente(String nombre, String email) {
		this.nombre = nombre;
		this.email = email;
		this.notificacionesRecibidas = new ArrayList<>();
		this.facturasRecibidas = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	/**
	 * Notifica al cliente sobre la llegada del buque o cambios en el estado de su carga.
	 * En una implementación real, esto enviaría un email al cliente.
	 * Por ahora, guarda la notificación en una lista para poder ser consultada.
	 */
	public void notificarLlegadaBuque(String mensaje) {
		// Guardar la notificación recibida
		this.notificacionesRecibidas.add(mensaje);
		
	}
	
	/**
	 * Envía una factura por mail al cliente.
	 * En una implementación real, esto enviaría un email con la factura adjunta.
	 * Por ahora, guarda la factura en una lista para poder ser consultada.
	 */
	public void enviarFactura(Factura factura) {
		// Guardar la factura recibida
		this.facturasRecibidas.add(factura);
		
	}
	
	/**
	 * Obtiene la lista de notificaciones recibidas por el cliente.
	 * Útil para testing y verificación.
	 */
	public List<String> getNotificacionesRecibidas() {
		return new ArrayList<>(notificacionesRecibidas);
	}
	
	/**
	 * Obtiene la lista de facturas recibidas por el cliente.
	 * Útil para testing y verificación.
	 */
	public List<Factura> getFacturasRecibidas() {
		return new ArrayList<>(facturasRecibidas);
	}
}

