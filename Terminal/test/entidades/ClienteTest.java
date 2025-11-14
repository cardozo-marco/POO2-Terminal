package entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import facturacion.Factura;

public class ClienteTest {
	
	private Cliente cliente;
	
	@BeforeEach
	public void setUp() {
		cliente = new Cliente("Test Company", "test@example.com");
	}
	
	@Test
	public void constructorYGettersTest() {
		assertEquals("Test Company", cliente.getNombre());
		assertEquals("test@example.com", cliente.getEmail());
	}
	
	@Test
	public void notificarLlegadaBuqueTest() {
		assertTrue(cliente.getNotificacionesRecibidas().isEmpty());
		
		cliente.notificarLlegadaBuque("Buque llegó al puerto");
		cliente.notificarLlegadaBuque("Carga lista para retiro");
		
		assertEquals(2, cliente.getNotificacionesRecibidas().size());
		assertTrue(cliente.getNotificacionesRecibidas().contains("Buque llegó al puerto"));
		assertTrue(cliente.getNotificacionesRecibidas().contains("Carga lista para retiro"));
	}
	
	@Test
	public void enviarFacturaTest() {
		assertTrue(cliente.getFacturasRecibidas().isEmpty());
		
		Factura factura1 = new Factura(cliente);
		Factura factura2 = new Factura(cliente);
		
		cliente.enviarFactura(factura1);
		cliente.enviarFactura(factura2);
		
		assertEquals(2, cliente.getFacturasRecibidas().size());
		assertTrue(cliente.getFacturasRecibidas().contains(factura1));
		assertTrue(cliente.getFacturasRecibidas().contains(factura2));
	}
	
	@Test
	public void getNotificacionesDevuelveCopiaTest() {
		cliente.notificarLlegadaBuque("Notificación 1");
		var lista1 = cliente.getNotificacionesRecibidas();
		var lista2 = cliente.getNotificacionesRecibidas();
		
		assertNotSame(lista1, lista2);
		assertEquals(lista1, lista2);
	}
	
	@Test
	public void getFacturasDevuelveCopiaTest() {
		Factura factura = new Factura(cliente);
		cliente.enviarFactura(factura);
		var lista1 = cliente.getFacturasRecibidas();
		var lista2 = cliente.getFacturasRecibidas();
		
		assertNotSame(lista1, lista2);
		assertEquals(lista1, lista2);
	}
}

