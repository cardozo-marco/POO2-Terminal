package maritimo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class TramoTest {
	private TerminalPortuaria origenMock;
    private TerminalPortuaria destinoMock;
    private PosicionGPS origenPosMock;
    private PosicionGPS destinoPosMock;
    private Tramo tramo;

    @BeforeEach
    void setUp() {
        origenMock = mock(TerminalPortuaria.class);
        destinoMock = mock(TerminalPortuaria.class);
        origenPosMock = mock(PosicionGPS.class);
        destinoPosMock = mock(PosicionGPS.class);

        when(origenMock.getPosicion()).thenReturn(origenPosMock);
        when(destinoMock.getPosicion()).thenReturn(destinoPosMock);


        tramo = new Tramo(origenMock, destinoMock, 120.0, 50000.0);
    }

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        assertEquals(120.0, tramo.getTiempo());
        assertEquals(50000.0, tramo.getPrecio());
        assertSame(origenMock, tramo.getOrigen());
        assertSame(destinoMock, tramo.getDestino());

        assertNotNull(tramo.getOrigen());
        assertNotNull(tramo.getDestino());
    }
}

