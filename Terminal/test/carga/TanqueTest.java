package carga;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reportes.Visitor;

public class TanqueTest {
	
	private Tanque tanque;
	private BillOfLanding carga;
	
	@BeforeEach
	public void setUp() {
		carga = new BLSimple("liquido", 8000);
		tanque = new Tanque("TANK001", 2.4, 6.0, 2.6, 15000, carga);
	}
	
	@Test
	public void constructorInicializaCorrectamenteTest() {
		assertEquals("TANK001", tanque.getId());
		// Verificamos que el volumen se calcula correctamente (indirectamente verifica dimensiones)
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, tanque.getVolumen());
	}
	
	@Test
	public void getVolumenCalculaCorrectamenteTest() {
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, tanque.getVolumen());
	}
	
	@Test
	public void getIdRetornaIdCorrectoTest() {
		assertEquals("TANK001", tanque.getId());
	}
	
	@Test
	public void acceptLlamaVisitConTanqueTest() {
		Visitor visitor = mock(Visitor.class);
		tanque.accept(visitor);
		verify(visitor).visit(tanque);
	}
	
	@Test
	public void diferentesTanqueConDiferentesDimensionesTest() {
		Tanque tanque2 = new Tanque("TANK002", 3.0, 6.0, 2.6, 18000, carga);
		assertEquals("TANK002", tanque2.getId());
		assertEquals(3.0 * 6.0 * 2.6, tanque2.getVolumen());
	}
	
	@Test
	public void getVolumenConDimensionesCeroTest() {
		Tanque tanqueCero = new Tanque("TANK000", 0, 0, 0, 0, carga);
		assertEquals(0.0, tanqueCero.getVolumen());
	}
}

