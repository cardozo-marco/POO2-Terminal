package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioRevisionTanqueTest {
	public ServicioRevisionTanque revision;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		revision = new ServicioRevisionTanque(100.0);
		
		mockOrden = mock(OrdenDeImportacion.class);
	}
	
	@Test
	public void costoTest() {
		assertEquals(100.0, revision.calcularCosto(mockOrden));
	}
}
