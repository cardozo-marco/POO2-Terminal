package entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entidades.Conductor;

public class ConductorTest {
	
	@Test
	public void constructorYGettersTest() {
		Conductor conductor = new Conductor("Juan Perez", "12345678");
		assertEquals("Juan Perez", conductor.getNombre());
		assertEquals("12345678", conductor.getDni());
	}
	
	@Test
	public void diferentesConductoresTest() {
		Conductor conductor1 = new Conductor("Maria Garcia", "87654321");
		Conductor conductor2 = new Conductor("Carlos Lopez", "11223344");
		assertEquals("Maria Garcia", conductor1.getNombre());
		assertEquals("87654321", conductor1.getDni());
		assertEquals("Carlos Lopez", conductor2.getNombre());
		assertEquals("11223344", conductor2.getDni());
	}
}

