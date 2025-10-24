package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioLavadoTest {
	public ServicioLavado lavado;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		lavado = new ServicioLavado(100.0, 200.0);
		
		mockOrden = mock(OrdenDeImportacion.class);
	}
	
	@Test
	public void costoMenorTest() {
		when(mockOrden.getVolumen()).thenReturn(50.0);
		assertEquals(100.0, lavado.calcularCosto(mockOrden));
	}
	
	@Test
	public void costoMayorTest() {
		when(mockOrden.getVolumen()).thenReturn(80.0);
		assertEquals(200.0, lavado.calcularCosto(mockOrden));
	}
}
