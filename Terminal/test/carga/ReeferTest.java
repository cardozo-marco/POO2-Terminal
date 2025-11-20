package carga;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reportes.Visitor;

public class ReeferTest {
	
	private Reefer reefer;
	private BLSimple carga;
	
	@BeforeEach
	public void setUp() {
		carga = new BLSimple("producto refrigerado", 6000);
		reefer = new Reefer("REF001", 2.4, 6.0, 2.6, 12000, carga, 100);
	}

	@Test
	public void constructorInicializaCorrectamenteTest() {
		assertEquals("REF001", reefer.getId());
		// Verificamos que el volumen se calcula correctamente (indirectamente verifica dimensiones)
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, reefer.getVolumen());
	}
	
	@Test
	public void getVolumenCalculaCorrectamenteTest() {
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, reefer.getVolumen());
	}
	
	@Test
	public void getIdRetornaIdCorrectoTest() {
		assertEquals("REF001", reefer.getId());
	}
	
	@Test
	public void acceptLlamaVisitConReeferTest() {
		Visitor visitor = mock(Visitor.class);
		reefer.accept(visitor);
		verify(visitor).visit(reefer);
	}
	
	@Test
	public void diferentesReeferConDiferentesDimensionesTest() {
		Reefer reefer2 = new Reefer("REF002", 3.0, 6.0, 2.6, 15000, carga, 100);
		assertEquals("REF002", reefer2.getId());
		assertEquals(3.0 * 6.0 * 2.6, reefer2.getVolumen());
	}
	
	@Test
	public void getVolumenConDimensionesCeroTest() {
		Reefer reeferCero = new Reefer("REF000", 0, 0, 0, 0, carga, 100);
		assertEquals(0.0, reeferCero.getVolumen());
	}
	
	@Test 
	public void getConsumo() {
		assertEquals(100, reefer.getConsumoKWPorHora());
	}
}

