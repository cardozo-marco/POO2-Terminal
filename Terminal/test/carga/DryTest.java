package carga;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reportes.Visitor;

public class DryTest {
	
	private Dry dry;
	private BillOfLanding carga;
	
	@BeforeEach
	public void setUp() {
		carga = new BLSimple("producto", 5000);
		dry = new Dry("DRY001", 2.4, 6.0, 2.6, 10000, carga);
	}
	
	@Test
	public void constructorInicializaCorrectamenteTest() {
		assertEquals("DRY001", dry.getId());
		// Verificamos que el volumen se calcula correctamente (indirectamente verifica dimensiones)
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, dry.getVolumen());
	}
	
	@Test
	public void getVolumenCalculaCorrectamenteTest() {
		double volumenEsperado = 2.4 * 6.0 * 2.6;
		assertEquals(volumenEsperado, dry.getVolumen());
	}
	
	@Test
	public void getIdRetornaIdCorrectoTest() {
		assertEquals("DRY001", dry.getId());
	}
	
	@Test
	public void acceptLlamaVisitConDryTest() {
		Visitor visitor = mock(Visitor.class);
		dry.accept(visitor);
		verify(visitor).visit(dry);
	}
	
	@Test
	public void diferentesDryConDiferentesDimensionesTest() {
		Dry dry2 = new Dry("DRY002", 3.0, 6.0, 2.6, 12000, carga);
		assertEquals("DRY002", dry2.getId());
		assertEquals(3.0 * 6.0 * 2.6, dry2.getVolumen());
	}
	
	@Test
	public void getVolumenConDimensionesCeroTest() {
		Dry dryCero = new Dry("DRY000", 0, 0, 0, 0, carga);
		assertEquals(0.0, dryCero.getVolumen());
	}
}

