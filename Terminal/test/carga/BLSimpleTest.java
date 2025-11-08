package carga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class BLSimpleTest {
	public BillOfLanding simple;
	
	@BeforeEach
	public void setUp() {
		simple = new BLSimple("latas", 8000);
	}
	
	@Test
	public void obtenerPesoTest() {
		assertEquals(8000, simple.getPesoTotal());
	}
}
