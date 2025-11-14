package entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import entidades.Camion;

public class CamionTest {
	
	@Test
	public void constructorYGettersTest() {
		Camion camion = new Camion("ABC123");
		assertEquals("ABC123", camion.getPatente());
	}
	
	@Test
	public void diferentesPatentesTest() {
		Camion camion1 = new Camion("XYZ789");
		Camion camion2 = new Camion("DEF456");
		assertEquals("XYZ789", camion1.getPatente());
		assertEquals("DEF456", camion2.getPatente());
	}
}

