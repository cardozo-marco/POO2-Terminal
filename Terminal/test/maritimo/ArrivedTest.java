package maritimo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrivedTest {

	private Arrived arrived;
    private Buque buqueMock;

    @BeforeEach
    void setUp() {
        arrived = new Arrived();
        buqueMock = mock(Buque.class);
    }

    @Test
    void darOrdenDeTrabajoCambiaFaseABuque() {
        arrived.darOrdenDeTrabajo(buqueMock);

        verify(buqueMock, times(1)).setFase(any(Working.class));
    }

}
