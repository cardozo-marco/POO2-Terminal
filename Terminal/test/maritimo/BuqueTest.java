package maritimo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class BuqueTest {

    private Buque buque;
    private Viaje viajeMock;
    private FaseBuque faseMock;
    private BuqueObserver observerMock;
    private PosicionGPS posicionInicial;

    @BeforeEach
    void setUp() {
        viajeMock = Mockito.mock(Viaje.class);
        faseMock = Mockito.mock(FaseBuque.class);
        observerMock = Mockito.mock(BuqueObserver.class);
        posicionInicial = new PosicionGPS(0, 0);
        buque = new Buque(viajeMock, posicionInicial,"test");
    }

    @Test
    void constructorInicializaCorrectamente() {
        assertNotNull(buque.getFaseActual());
        assertEquals(posicionInicial, buque.getPosicionActual());
        assertEquals(viajeMock, buque.getViajeAsignado());
        assertTrue(buque.getObservers().isEmpty());
        assertTrue(buque.getFaseActual() instanceof Outbound);
    }

    @Test
    void setFaseCambiaFaseYNotificaObservers() {
        buque.addObserver(observerMock);

        FaseBuque nuevaFase = new Inbound();
        buque.setFase(nuevaFase);

        assertEquals(nuevaFase, buque.getFaseActual());
        verify(observerMock, Mockito.times(1)).update(buque);
    }

    @Test
    void removeObserverEvitaNotificacion() {
        buque.addObserver(observerMock);
        buque.removeObserver(observerMock);

        buque.setFase(new Inbound());
        verify(observerMock, never()).update(buque);
    }

    @Test
    void actualizarPosicionDelegadaEnFaseActual() {
        PosicionGPS nuevaPosicion = new PosicionGPS(10, 10);
        buque.setFase(faseMock);

        buque.actualizarPosicion(nuevaPosicion);

        verify(faseMock).actualizarPosicion(buque, nuevaPosicion);
    }

    @Test
    void departDelegadaEnFaseActual() {
        buque.setFase(faseMock);

        buque.depart();

        verify(faseMock).depart(buque);
    }

    @Test
    void darOrdenDeTrabajoDelegadaEnFaseActual() {
        buque.setFase(faseMock);

        buque.darOrdenDeTrabajo();

        verify(faseMock).darOrdenDeTrabajo(buque);
    }
}
