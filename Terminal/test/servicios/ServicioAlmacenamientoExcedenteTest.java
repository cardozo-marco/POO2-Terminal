package servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

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
	
	@Test
	public void calcularCosto_OrdenImportacion_SinExcedente() {
		OrdenDeImportacion mockOrdenImportacion = mock(OrdenDeImportacion.class);
		LocalDateTime fechaLlegada = LocalDateTime.now().minusHours(12); // Menos de 24 horas
		
		when(mockOrdenImportacion.esOrdenDeImportacion()).thenReturn(true);
		when(mockOrdenImportacion.getFechaLlegadaCarga()).thenReturn(fechaLlegada);
		
		assertEquals(0.0, almacenamiento.calcularCosto(mockOrdenImportacion));
	}
	
	@Test
	public void calcularCosto_OrdenImportacion_ConExcedente() {
		OrdenDeImportacion mockOrdenImportacion = mock(OrdenDeImportacion.class);
		LocalDateTime fechaLlegada = LocalDateTime.now().minusHours(48); // 48 horas - 24 tolerancia = 24 horas excedentes = 1 día
		
		when(mockOrdenImportacion.esOrdenDeImportacion()).thenReturn(true);
		when(mockOrdenImportacion.getFechaLlegadaCarga()).thenReturn(fechaLlegada);
		
		assertEquals(10.0, almacenamiento.calcularCosto(mockOrdenImportacion)); // 1 día * 10.0
	}
	
	@Test
	public void calcularCosto_OrdenImportacion_ConHorasParciales() {
		OrdenDeImportacion mockOrdenImportacion = mock(OrdenDeImportacion.class);
		LocalDateTime fechaLlegada = LocalDateTime.now().minusHours(50); // 50 horas - 24 tolerancia = 26 horas excedentes = 2 días
		
		when(mockOrdenImportacion.esOrdenDeImportacion()).thenReturn(true);
		when(mockOrdenImportacion.getFechaLlegadaCarga()).thenReturn(fechaLlegada);
		
		assertEquals(20.0, almacenamiento.calcularCosto(mockOrdenImportacion)); // 2 días * 10.0
	}
	
	@Test
	public void calcularCosto_OrdenImportacion_Exactamente24Horas() {
		OrdenDeImportacion mockOrdenImportacion = mock(OrdenDeImportacion.class);
		LocalDateTime fechaLlegada = LocalDateTime.now().minusHours(24); // Exactamente 24 horas
		
		when(mockOrdenImportacion.esOrdenDeImportacion()).thenReturn(true);
		when(mockOrdenImportacion.getFechaLlegadaCarga()).thenReturn(fechaLlegada);
		
		assertEquals(0.0, almacenamiento.calcularCosto(mockOrdenImportacion));
	}
	
	@Test
	public void calcularCosto_OrdenImportacion_MultiplesDias() {
		OrdenDeImportacion mockOrdenImportacion = mock(OrdenDeImportacion.class);
		LocalDateTime fechaLlegada = LocalDateTime.now().minusHours(72); // 3 días = 72 horas - 24 tolerancia = 48 horas excedentes = 2 días
		
		when(mockOrdenImportacion.esOrdenDeImportacion()).thenReturn(true);
		when(mockOrdenImportacion.getFechaLlegadaCarga()).thenReturn(fechaLlegada);
		
		assertEquals(20.0, almacenamiento.calcularCosto(mockOrdenImportacion)); // 2 días * 10.0
	}
}

