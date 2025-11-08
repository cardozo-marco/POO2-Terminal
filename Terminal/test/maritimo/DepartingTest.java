package maritimo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartingTest {

	private Departing departing;
    private Buque buqueMock;
    private Viaje viajeMock;
    private TerminalPortuaria terminalOrigenMock;
    private PosicionGPS posicionBuqueMock;
    private PosicionGPS posicionOrigenMock;

    @BeforeEach
    void setUp() {
        departing = new Departing();

        buqueMock = mock(Buque.class);
        viajeMock = mock(Viaje.class);
        terminalOrigenMock = mock(TerminalPortuaria.class);
        posicionBuqueMock = mock(PosicionGPS.class);
        posicionOrigenMock = mock(PosicionGPS.class);

        when(buqueMock.getViajeAsignado()).thenReturn(viajeMock);
        when(buqueMock.getPosicionActual()).thenReturn(posicionBuqueMock);
        when(viajeMock.getTerminalOrigen()).thenReturn(terminalOrigenMock);
        when(terminalOrigenMock.getPosicion()).thenReturn(posicionOrigenMock);
    }

    @Test
    void noCambiaAFueraDePuertoSiDistanciaMenorA1Km() {

        when(posicionBuqueMock.distanciaHasta(posicionOrigenMock)).thenReturn(0.5);

        departing.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(buqueMock, never()).setFase(any(Outbound.class));
    }

    @Test
    void cambiaAFueraDePuertoSiDistanciaMayorA1Km() {

        when(posicionBuqueMock.distanciaHasta(posicionOrigenMock)).thenReturn(5.0);

        departing.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(buqueMock, times(1)).setFase(any(Outbound.class));
    }

}
