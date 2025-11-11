package busqueda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class FiltroPuertoDestinoTest {
	
	private TerminalPortuaria puerto1;
	private TerminalPortuaria puerto2;
	private Viaje viaje;
	
	@BeforeEach
	public void setUp() {
		puerto1 = mock(TerminalPortuaria.class);
		puerto2 = mock(TerminalPortuaria.class);
		viaje = mock(Viaje.class);
	}
	
	@Test
	public void cumpleCuandoDestinoCoincideTest() {
		when(viaje.getTerminalDestino()).thenReturn(puerto1);
		
		FiltroPuertoDestino filtro = new FiltroPuertoDestino(puerto1);
		assertTrue(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoDestinoNoCoincideTest() {
		when(viaje.getTerminalDestino()).thenReturn(puerto1);
		
		FiltroPuertoDestino filtro = new FiltroPuertoDestino(puerto2);
		assertFalse(filtro.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoDestinoEsNullTest() {
		when(viaje.getTerminalDestino()).thenReturn(null);
		
		FiltroPuertoDestino filtro = new FiltroPuertoDestino(puerto1);
		assertFalse(filtro.cumple(viaje));
	}
}

