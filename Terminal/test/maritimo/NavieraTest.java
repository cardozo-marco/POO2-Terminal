package maritimo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class NavieraTest {
	
	private Naviera naviera;
	
	@BeforeEach
	void setUp() {
		this.naviera = new Naviera();
	}
	
    @Test
    void agregaYObtieneCircuitos() {
    	CircuitoMaritimo circuitoMock = Mockito.mock(CircuitoMaritimo.class);;
		this.naviera.addCircuito(circuitoMock);
		
		assertTrue(this.naviera.getCircuitos().contains(circuitoMock));
    }

    @Test
    void agregaYObtieneBuques() {
    	Buque buqueMock = Mockito.mock(Buque.class);

        this.naviera.addBuque(buqueMock);

        assertTrue(this.naviera.getBuques().contains(buqueMock));
    }
}
