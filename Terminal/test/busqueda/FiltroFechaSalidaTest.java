package busqueda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimo.Viaje;

public class FiltroFechaSalidaTest {
	
	private Viaje viaje;
	
	@BeforeEach
	public void setUp() {
		viaje = mock(Viaje.class);
	}
	
	@Test
	public void cumpleCuandoFechaCoincideTest() {
		LocalDate fecha = LocalDate.of(2025, 10, 15);
		LocalDateTime fechaInicio = LocalDateTime.of(2025, 10, 15, 10, 0);
		
		when(viaje.getFechaInicio()).thenReturn(fechaInicio);
		
		FiltroFechaSalida filtro = new FiltroFechaSalida(fecha);
		assertTrue(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoFechaNoCoincideTest() {
		LocalDate fechaFiltro = LocalDate.of(2025, 10, 15);
		LocalDateTime fechaInicio = LocalDateTime.of(2025, 10, 16, 10, 0);
		
		when(viaje.getFechaInicio()).thenReturn(fechaInicio);
		
		FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
		assertFalse(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoFechaEsDiferenteMesTest() {
		LocalDate fechaFiltro = LocalDate.of(2025, 10, 15);
		LocalDateTime fechaInicio = LocalDateTime.of(2025, 11, 15, 10, 0);
		
		when(viaje.getFechaInicio()).thenReturn(fechaInicio);
		
		FiltroFechaSalida filtro = new FiltroFechaSalida(fechaFiltro);
		assertFalse(filtro.cumple(viaje));
	}
}

