package terminal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actores.Camion;
import actores.Conductor;
import actores.EmpresaTransportista;
import actores.Shipper;
import busqueda.CriterioDeBusqueda;
import busqueda.CriterioMenorPrecio;
import busqueda.CriterioMenorTiempo;
import busqueda.FiltroDeBusqueda;
import busqueda.FiltroPuertoDestino;
import busqueda.FiltroFechaSalida;
import carga.Container;
import maritimo.Buque;
import maritimo.CircuitoMaritimo;
import maritimo.Naviera;
import maritimo.PosicionGPS;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;

public class TerminalGestionadaTest {
	
	private TerminalGestionada terminalGestionada;
	private TerminalPortuaria mockTerminal;
	private CriterioDeBusqueda mockCriterio;
	private Naviera mockNaviera1;
	private Naviera mockNaviera2;
	private Viaje mockViaje1;
	private Viaje mockViaje2;
	private Viaje mockViaje3;
	private CircuitoMaritimo mockCircuito1;
	private CircuitoMaritimo mockCircuito2;
	private TerminalPortuaria mockDestino;
	private Buque mockBuque;
	
	@BeforeEach
	public void setUp() {

		PosicionGPS posicion = new PosicionGPS(-34.6037, -58.3816);
		mockTerminal = new TerminalPortuaria("Terminal Buenos Aires", posicion);
		mockCriterio = mock(CriterioDeBusqueda.class);

		mockNaviera1 = new Naviera();
		mockNaviera2 = new Naviera();

		mockCircuito1 = new CircuitoMaritimo(new ArrayList<>());
		mockCircuito2 = new CircuitoMaritimo(new ArrayList<>());
		mockDestino = mock(TerminalPortuaria.class);

		PosicionGPS pos = new PosicionGPS(0, 0);

		CircuitoMaritimo dummyCircuito = new CircuitoMaritimo(new ArrayList<>());
		Viaje dummyViaje = new Viaje(dummyCircuito, null, LocalDateTime.now());
		Buque dummyBuque = new Buque(dummyViaje, pos,"dummy");
		mockViaje1 = new Viaje(dummyCircuito, dummyBuque, LocalDateTime.of(2025, 10, 15, 18, 10));
		mockViaje2 = new Viaje(dummyCircuito, dummyBuque, LocalDateTime.of(2025, 10, 20, 19, 10));
		mockViaje3 = new Viaje(dummyCircuito, dummyBuque, LocalDateTime.of(2025, 10, 25, 20, 10));
		mockBuque = new Buque(mockViaje1, pos,"test");
		
		terminalGestionada = new TerminalGestionada(mockTerminal, mockCriterio);
	}
	
	// ========== TESTS BUSQUEDA VIAJES (2) ==========
	
	@Test
	public void testBuscarViajes_SinNavierasRegistradas_RetornaListaVacia() {
		FiltroDeBusqueda mockFiltro = mock(FiltroDeBusqueda.class);
		
		List<Viaje> resultado = terminalGestionada.buscarViajes(mockFiltro);
		
		assertTrue(resultado.isEmpty());
		verify(mockFiltro, never()).cumple(any(Viaje.class));
	}
	
	@Test
	public void testBuscarViajes_ConUnaNaviera_RetornaViajesDeEsaNaviera() {
		FiltroDeBusqueda mockFiltro = mock(FiltroDeBusqueda.class);

		PosicionGPS posicion = new PosicionGPS(0, 0);
		Buque buque1 = new Buque(mockViaje1, posicion,"test1");
		Buque buque2 = new Buque(mockViaje2, posicion,"test2");
		mockNaviera1.addBuque(buque1);
		mockNaviera1.addBuque(buque2);
		
		when(mockFiltro.cumple(mockViaje1)).thenReturn(true);
		when(mockFiltro.cumple(mockViaje2)).thenReturn(true);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		List<Viaje> resultado = terminalGestionada.buscarViajes(mockFiltro);
		
		assertEquals(2, resultado.size());
		assertTrue(resultado.contains(mockViaje1));
		assertTrue(resultado.contains(mockViaje2));
	}
	
	@Test
	public void testBuscarViajes_ConMultiplesNavieras_RetornaViajesDeTodas() {
		FiltroDeBusqueda mockFiltro = mock(FiltroDeBusqueda.class);

		PosicionGPS posicion = new PosicionGPS(0, 0);
		Buque buque1 = new Buque(mockViaje1, posicion,"test1");
		mockNaviera1.addBuque(buque1);

		Buque buque2 = new Buque(mockViaje2, posicion,"test2");
		Buque buque3 = new Buque(mockViaje3, posicion,"test3");
		mockNaviera2.addBuque(buque2);
		mockNaviera2.addBuque(buque3);
		
		when(mockFiltro.cumple(mockViaje1)).thenReturn(true);
		when(mockFiltro.cumple(mockViaje2)).thenReturn(true);
		when(mockFiltro.cumple(mockViaje3)).thenReturn(true);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		terminalGestionada.registrarNaviera(mockNaviera2);
		List<Viaje> resultado = terminalGestionada.buscarViajes(mockFiltro);
		
		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(mockViaje1));
		assertTrue(resultado.contains(mockViaje2));
		assertTrue(resultado.contains(mockViaje3));
	}
	
	@Test
	public void testBuscarViajes_ConFiltro_FiltraCorrectamente() {
		FiltroDeBusqueda mockFiltro = mock(FiltroDeBusqueda.class);

		PosicionGPS posicion = new PosicionGPS(0, 0);
		Buque buque1 = new Buque(mockViaje1, posicion,"test1");
		Buque buque2 = new Buque(mockViaje2, posicion,"test2");
		Buque buque3 = new Buque(mockViaje3, posicion,"test3");
		mockNaviera1.addBuque(buque1);
		mockNaviera1.addBuque(buque2);
		mockNaviera1.addBuque(buque3);
		
		when(mockFiltro.cumple(mockViaje1)).thenReturn(true);
		when(mockFiltro.cumple(mockViaje2)).thenReturn(false);
		when(mockFiltro.cumple(mockViaje3)).thenReturn(true);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		List<Viaje> resultado = terminalGestionada.buscarViajes(mockFiltro);
		
		assertEquals(2, resultado.size());
		assertTrue(resultado.contains(mockViaje1));
		assertTrue(resultado.contains(mockViaje3));
		assertFalse(resultado.contains(mockViaje2));
		verify(mockFiltro).cumple(mockViaje1);
		verify(mockFiltro).cumple(mockViaje2);
		verify(mockFiltro).cumple(mockViaje3);
	}
	
	@Test
	public void testBuscarViajes_ConFiltroPuertoDestino_FiltraPorDestino() {

		TerminalPortuaria mockPuertoDestino = mock(TerminalPortuaria.class);
		FiltroPuertoDestino filtro = new FiltroPuertoDestino(mockPuertoDestino);
		

		PosicionGPS posicion = new PosicionGPS(0, 0);
		Buque buque1 = new Buque(mockViaje1, posicion,"test1");
		Buque buque2 = new Buque(mockViaje2, posicion,"test2");
		mockNaviera1.addBuque(buque1);
		mockNaviera1.addBuque(buque2);
		

		terminalGestionada.registrarNaviera(mockNaviera1);
		List<Viaje> resultado = terminalGestionada.buscarViajes(filtro);
		

		assertTrue(resultado.isEmpty());
	}
	
	@Test
	public void testBuscarViajes_ConFiltroFechaSalida_FiltraPorFecha() {
		LocalDate fechaBuscada = LocalDate.of(2025, 10, 15);
		FiltroFechaSalida filtro = new FiltroFechaSalida(fechaBuscada);
		

		PosicionGPS posicion = new PosicionGPS(0, 0);
		Viaje viajeConFechaBuscada = new Viaje(mockCircuito1, mockBuque, fechaBuscada.atStartOfDay());
		Viaje viajeConOtraFecha = new Viaje(mockCircuito1, mockBuque, LocalDateTime.of(2025, 10, 20,20,5));
		

		Buque buque1 = new Buque(viajeConFechaBuscada, posicion,"test1");
		Buque buque2 = new Buque(viajeConOtraFecha, posicion,"test2");
		mockNaviera1.addBuque(buque1);
		mockNaviera1.addBuque(buque2);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		List<Viaje> resultado = terminalGestionada.buscarViajes(filtro);
		
		assertEquals(1, resultado.size());
		assertTrue(resultado.contains(viajeConFechaBuscada));
		assertFalse(resultado.contains(viajeConOtraFecha));
	}
	
	// ========== TESTS BUSQUEDA CIRCUITOS (3) ==========
	
	@Test
	public void testMejorCircuitoHacia_ConUnaNaviera_UsaCriterioParaBuscar() {

		mockNaviera1.addCircuito(mockCircuito1);
		mockNaviera1.addCircuito(mockCircuito2);
		List<CircuitoMaritimo> circuitos = Arrays.asList(mockCircuito1, mockCircuito2);
		
		when(mockCriterio.buscarCircuitos(anyList(), eq(mockTerminal), eq(mockDestino)))
			.thenReturn(mockCircuito1);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		CircuitoMaritimo resultado = terminalGestionada.mejorCircuitoHacia(mockDestino);
		
		assertEquals(mockCircuito1, resultado);
		verify(mockCriterio).buscarCircuitos(circuitos, mockTerminal, mockDestino);
	}
	
	@Test
	public void testMejorCircuitoHacia_ConMultiplesNavieras_AgrupaTodosLosCircuitos() {

		mockNaviera1.addCircuito(mockCircuito1);
		mockNaviera2.addCircuito(mockCircuito2);
		
		when(mockCriterio.buscarCircuitos(anyList(), eq(mockTerminal), eq(mockDestino)))
			.thenReturn(mockCircuito2);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		terminalGestionada.registrarNaviera(mockNaviera2);
		CircuitoMaritimo resultado = terminalGestionada.mejorCircuitoHacia(mockDestino);
		
		assertEquals(mockCircuito2, resultado);
		verify(mockCriterio).buscarCircuitos(argThat(list -> list.size() == 2), eq(mockTerminal), eq(mockDestino));
	}
	
	@Test
	public void testMejorCircuitoHacia_SinCircuitosQueCoincidan_RetornaNull() {
		mockNaviera1.addCircuito(mockCircuito1);
		List<CircuitoMaritimo> circuitos = Arrays.asList(mockCircuito1);
		
		when(mockCriterio.buscarCircuitos(anyList(), eq(mockTerminal), eq(mockDestino)))
			.thenReturn(null);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		CircuitoMaritimo resultado = terminalGestionada.mejorCircuitoHacia(mockDestino);
		
		assertNull(resultado);
		verify(mockCriterio).buscarCircuitos(circuitos, mockTerminal, mockDestino);
	}
	
	@Test
	public void testMejorCircuitoHacia_ConCriterioMenorTiempo_UsaEseCriterio() {
		CriterioMenorTiempo criterioTiempo = new CriterioMenorTiempo();
		TerminalGestionada terminalConTiempo = new TerminalGestionada(mockTerminal, criterioTiempo);
		
		Naviera naviera = new Naviera();
		naviera.addCircuito(mockCircuito1);
		naviera.addCircuito(mockCircuito2);
		

		terminalConTiempo.registrarNaviera(naviera);
		CircuitoMaritimo resultado = terminalConTiempo.mejorCircuitoHacia(mockDestino);

		assertNull(resultado);
	}
	
	@Test
	public void testMejorCircuitoHacia_ConCriterioMenorPrecio_UsaEseCriterio() {
		CriterioMenorPrecio criterioPrecio = new CriterioMenorPrecio();
		TerminalGestionada terminalConPrecio = new TerminalGestionada(mockTerminal, criterioPrecio);
		
		Naviera naviera = new Naviera();
		naviera.addCircuito(mockCircuito1);
		naviera.addCircuito(mockCircuito2);

		terminalConPrecio.registrarNaviera(naviera);
		CircuitoMaritimo resultado = terminalConPrecio.mejorCircuitoHacia(mockDestino);
		assertNull(resultado);
	}
	
	@Test
	public void testSetCriterioDeBusqueda_CambiaElCriterio() {
		CriterioDeBusqueda nuevoCriterio = mock(CriterioDeBusqueda.class);
		mockNaviera1.addCircuito(mockCircuito1);
		List<CircuitoMaritimo> circuitos = Arrays.asList(mockCircuito1);
		
		when(nuevoCriterio.buscarCircuitos(anyList(), eq(mockTerminal), eq(mockDestino)))
			.thenReturn(mockCircuito1);
		
		terminalGestionada.registrarNaviera(mockNaviera1);
		terminalGestionada.setCriterioDeBusqueda(nuevoCriterio);
		CircuitoMaritimo resultado = terminalGestionada.mejorCircuitoHacia(mockDestino);
		
		assertEquals(mockCircuito1, resultado);
		verify(nuevoCriterio).buscarCircuitos(circuitos, mockTerminal, mockDestino);
		verify(mockCriterio, never()).buscarCircuitos(anyList(), any(), any());
	}
	

}