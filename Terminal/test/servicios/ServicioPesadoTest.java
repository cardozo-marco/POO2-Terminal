package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioPesadoTest {
	public ServicioPesado pesado;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		pesado = new ServicioPesado(100.0);
		
		mockOrden = mock(OrdenDeImportacion.class);
	}
	
	@Test
	public void costoTest() {
		assertEquals(100.0, pesado.calcularCosto(mockOrden));
	}
}
