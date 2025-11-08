package maritimo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PosicionGPSTest {
	
	@Test
    void constructorInicializaLatitudYLongitud() {
        PosicionGPS pos = new PosicionGPS(-34.6, -58.4); 
        assertEquals(-34.6, pos.getLatitud());
        assertEquals(-58.4, pos.getLongitud());
    }

	@Test
    void distanciaHastaMismaPosicionEsCero() {
        PosicionGPS p1 = new PosicionGPS(0, 0);
        PosicionGPS p2 = new PosicionGPS(0, 0);

        double distancia = p1.distanciaHasta(p2);
        assertEquals(0.0, distancia);
    }
	
	@Test
    void distanciaHastaEntreDosPuntosEsPositiva() {
        PosicionGPS p1 = new PosicionGPS(0, 0);
        PosicionGPS p2 = new PosicionGPS(10, 10);

        double distancia = p1.distanciaHasta(p2);

        assertTrue(distancia > 0);
	}
	
	@Test
    void distanciaHastaEsSimetrica() {
		//la distancia de A a B == distancia de B a A
        PosicionGPS p1 = new PosicionGPS(5, 20);
        PosicionGPS p2 = new PosicionGPS(10, 30);

        double d1 = p1.distanciaHasta(p2);
        double d2 = p2.distanciaHasta(p1);

        assertEquals(d1, d2);
    }
}
