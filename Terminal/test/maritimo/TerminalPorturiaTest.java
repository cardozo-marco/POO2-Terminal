package maritimo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TerminalPorturiaTest {

	private PosicionGPS posicion;
	private TerminalPortuaria terminal;

	@BeforeEach
    void setUp() {
        posicion = new PosicionGPS(-34.6, -58.4);
        terminal = new TerminalPortuaria("Buenos Aires", posicion);
    }

	@Test
	void constructorYGetterFuncionanCorrectamente() {
	    assertEquals(posicion, terminal.getPosicion());
	    assertSame(posicion, terminal.getPosicion());
	}
}

