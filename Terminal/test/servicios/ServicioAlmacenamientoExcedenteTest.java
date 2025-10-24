package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import ordenes.*;

public class ServicioAlmacenamientoExcedenteTest {
	public ServicioAlmacenamientoExcedente almacenamiento;
	
	public Orden mockOrden;
	
	@BeforeEach
	public void setUp() {
		mockOrden = mock(OrdenDeExportacion.class);
		
		when(mockOrden.cantidadDeDias()).thenReturn(7.0);
		
		almacenamiento = new ServicioAlmacenamientoExcedente(10.0);
	}
	
	@Test
	public void calcularCosto() {
		assertEquals(70.0, almacenamiento.calcularCosto(mockOrden));
	}
}

