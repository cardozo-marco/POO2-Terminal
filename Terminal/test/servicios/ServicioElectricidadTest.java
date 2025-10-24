package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioElectricidadTest {
	public ServicioElectricidad electricidad;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		electricidad = new ServicioElectricidad(10.0);
		
		mockOrden = mock(OrdenDeImportacion.class);
		
		when(mockOrden.cantidadDeDias()).thenReturn(10.0);
	}
	
	@Test
	public void costoTotalTest() {
		assertEquals(100.0, electricidad.calcularCosto(mockOrden));
	}
}
