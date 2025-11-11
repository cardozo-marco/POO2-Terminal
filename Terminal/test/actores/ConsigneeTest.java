package actores;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConsigneeTest {
	
	@Test
	public void constructorHeredaDeClienteTest() {
		Consignee consignee = new Consignee("Import Company", "import@example.com");
		assertEquals("Import Company", consignee.getNombre());
		assertEquals("import@example.com", consignee.getEmail());
	}
	
	@Test
	public void puedeRecibirNotificacionesTest() {
		Consignee consignee = new Consignee("Test Consignee", "test@test.com");
		consignee.notificarLlegadaBuque("Mensaje de prueba");
		assertEquals(1, consignee.getNotificacionesRecibidas().size());
	}
}

