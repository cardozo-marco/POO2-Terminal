package actores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ShipperTest {
	
	@Test
	public void constructorHeredaDeClienteTest() {
		Shipper shipper = new Shipper("Export Company", "export@example.com");
		assertEquals("Export Company", shipper.getNombre());
		assertEquals("export@example.com", shipper.getEmail());
	}
	
	@Test
	public void puedeRecibirNotificacionesTest() {
		Shipper shipper = new Shipper("Test Shipper", "test@test.com");
		shipper.notificarLlegadaBuque("Mensaje de prueba");
		assertEquals(1, shipper.getNotificacionesRecibidas().size());
	}
}

