package maritimo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkingTest {

	    Working working;
	    Buque buqueMock;

	    @BeforeEach
	    void setUp() {
	        working = new Working();
	        buqueMock = mock(Buque.class);
	    }

	    @Test
	    void departCambiaAFaseDeparting() {
	        working.depart(buqueMock);

	        verify(buqueMock, times(1)).setFase(any(Departing.class));
	    }

}
