package busqueda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimo.CircuitoMaritimo;
import maritimo.TerminalPortuaria;

public class CriterioMenorTiempoTest {
	
	private CriterioMenorTiempo criterio;
	private TerminalPortuaria origen;
	private TerminalPortuaria destino;
	
	@BeforeEach
	public void setUp() {
		criterio = new CriterioMenorTiempo();
		origen = mock(TerminalPortuaria.class);
		destino = mock(TerminalPortuaria.class);
	}
	
	@Test
	public void buscarMejorCircuitoPorTiempoTest() {
		CircuitoMaritimo circuito1 = mock(CircuitoMaritimo.class);
		CircuitoMaritimo circuito2 = mock(CircuitoMaritimo.class);
		CircuitoMaritimo circuito3 = mock(CircuitoMaritimo.class);
		
		when(circuito1.contieneTerminales(origen, destino)).thenReturn(true);
		when(circuito2.contieneTerminales(origen, destino)).thenReturn(true);
		when(circuito3.contieneTerminales(origen, destino)).thenReturn(true);
		
		when(circuito1.tiempoEntreTerminales(origen, destino)).thenReturn(50.0);
		when(circuito2.tiempoEntreTerminales(origen, destino)).thenReturn(30.0);
		when(circuito3.tiempoEntreTerminales(origen, destino)).thenReturn(40.0);
		
		List<CircuitoMaritimo> circuitos = new ArrayList<>();
		circuitos.add(circuito1);
		circuitos.add(circuito2);
		circuitos.add(circuito3);
		
		CircuitoMaritimo resultado = criterio.buscarCircuitos(circuitos, origen, destino);
		assertEquals(circuito2, resultado);
	}
	
	@Test
	public void buscarSinCircuitosQueContenganTerminalesTest() {
		CircuitoMaritimo circuito = mock(CircuitoMaritimo.class);
		when(circuito.contieneTerminales(origen, destino)).thenReturn(false);
		
		List<CircuitoMaritimo> circuitos = new ArrayList<>();
		circuitos.add(circuito);
		
		CircuitoMaritimo resultado = criterio.buscarCircuitos(circuitos, origen, destino);
		assertNull(resultado);
	}
	
	@Test
	public void buscarConListaVaciaTest() {
		List<CircuitoMaritimo> circuitos = new ArrayList<>();
		CircuitoMaritimo resultado = criterio.buscarCircuitos(circuitos, origen, destino);
		assertNull(resultado);
	}
}

