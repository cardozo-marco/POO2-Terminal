package busqueda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class FiltroFechaLlegadaTest {
	
	private Viaje viaje;
	private TerminalPortuaria puerto1;
	private TerminalPortuaria puerto2;
	
	@BeforeEach
	public void setUp() {
		viaje = mock(Viaje.class);
		puerto1 = mock(TerminalPortuaria.class);
		puerto2 = mock(TerminalPortuaria.class);
	}
	
	@Test
	public void cumpleConPuertoDestinoSetTest() {
		LocalDate fecha = LocalDate.of(2025, 10, 20);
		LocalDateTime fechaLlegada = LocalDateTime.of(2025, 10, 20, 14, 0);
		
		FiltroFechaLlegada filtro = new FiltroFechaLlegada(fecha);
		filtro.setPuertoDestino(puerto1);
		
		when(viaje.getFechaLlegada(puerto1)).thenReturn(fechaLlegada);
		
		assertTrue(filtro.cumple(viaje));
	}
	
	@Test
	public void cumpleSinPuertoDestinoSetUsaTerminalDestinoTest() {
		LocalDate fecha = LocalDate.of(2025, 10, 20);
		LocalDateTime fechaLlegada = LocalDateTime.of(2025, 10, 20, 14, 0);
		
		FiltroFechaLlegada filtro = new FiltroFechaLlegada(fecha);
		
		when(viaje.getTerminalDestino()).thenReturn(puerto1);
		when(viaje.getFechaLlegada(puerto1)).thenReturn(fechaLlegada);
		
		assertTrue(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoFechaNoCoincideTest() {
		LocalDate fechaFiltro = LocalDate.of(2025, 10, 20);
		LocalDateTime fechaLlegada = LocalDateTime.of(2025, 10, 21, 14, 0);
		
		FiltroFechaLlegada filtro = new FiltroFechaLlegada(fechaFiltro);
		filtro.setPuertoDestino(puerto1);
		
		when(viaje.getFechaLlegada(puerto1)).thenReturn(fechaLlegada);
		
		assertFalse(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoTerminalDestinoEsNullTest() {
		LocalDate fecha = LocalDate.of(2025, 10, 20);
		
		FiltroFechaLlegada filtro = new FiltroFechaLlegada(fecha);
		
		when(viaje.getTerminalDestino()).thenReturn(null);
		
		assertFalse(filtro.cumple(viaje));
	}
	
	@Test
	public void setPuertoDestinoTest() {
		LocalDate fecha = LocalDate.of(2025, 10, 20);
		LocalDateTime fechaLlegada = LocalDateTime.of(2025, 10, 20, 14, 0);
		
		FiltroFechaLlegada filtro = new FiltroFechaLlegada(fecha);
		
		when(viaje.getFechaLlegada(puerto1)).thenReturn(fechaLlegada);
		when(viaje.getFechaLlegada(puerto2)).thenReturn(LocalDateTime.of(2025, 10, 21, 14, 0));
		
		filtro.setPuertoDestino(puerto1);
		assertTrue(filtro.cumple(viaje));
		
		filtro.setPuertoDestino(puerto2);
		assertFalse(filtro.cumple(viaje));
	}
}

