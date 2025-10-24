package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioDesconsolidadoTest {
	public ServicioDesconsolidado servicio;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		servicio = new ServicioDesconsolidado(100.0);
		
		mockOrden = mock(OrdenDeImportacion.class);
	}
	
	@Test
	public void costoTotalTest() {
		assertEquals(100.0, servicio.calcularCosto(mockOrden));
	}
}
