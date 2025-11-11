package actores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import facturacion.Factura;

public class ClienteTest {
	
	private Shipper shipper;
	
	@BeforeEach
	public void setUp() {
		shipper = new Shipper("Test Company", "test@example.com");
	}
	
	@Test
	public void constructorYGettersTest() {
		assertEquals("Test Company", shipper.getNombre());
		assertEquals("test@example.com", shipper.getEmail());
	}
	
	@Test
	public void notificarLlegadaBuqueTest() {
		assertTrue(shipper.getNotificacionesRecibidas().isEmpty());
		
		shipper.notificarLlegadaBuque("Buque llegó al puerto");
		shipper.notificarLlegadaBuque("Carga lista para retiro");
		
		assertEquals(2, shipper.getNotificacionesRecibidas().size());
		assertTrue(shipper.getNotificacionesRecibidas().contains("Buque llegó al puerto"));
		assertTrue(shipper.getNotificacionesRecibidas().contains("Carga lista para retiro"));
	}
	
	@Test
	public void enviarFacturaTest() {
		assertTrue(shipper.getFacturasRecibidas().isEmpty());
		
		Factura factura1 = new Factura(shipper);
		Factura factura2 = new Factura(shipper);
		
		shipper.enviarFactura(factura1);
		shipper.enviarFactura(factura2);
		
		assertEquals(2, shipper.getFacturasRecibidas().size());
		assertTrue(shipper.getFacturasRecibidas().contains(factura1));
		assertTrue(shipper.getFacturasRecibidas().contains(factura2));
	}
	
	@Test
	public void getNotificacionesDevuelveCopiaTest() {
		shipper.notificarLlegadaBuque("Notificación 1");
		var lista1 = shipper.getNotificacionesRecibidas();
		var lista2 = shipper.getNotificacionesRecibidas();
		
		assertNotSame(lista1, lista2);
		assertEquals(lista1, lista2);
	}
	
	@Test
	public void getFacturasDevuelveCopiaTest() {
		Factura factura = new Factura(shipper);
		shipper.enviarFactura(factura);
		var lista1 = shipper.getFacturasRecibidas();
		var lista2 = shipper.getFacturasRecibidas();
		
		assertNotSame(lista1, lista2);
		assertEquals(lista1, lista2);
	}
}

