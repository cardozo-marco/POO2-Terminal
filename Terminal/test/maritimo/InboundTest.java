package maritimo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InboundTest {

    private Inbound inbound;
    private Buque buqueMock;
    private Viaje viajeMock;
    private TerminalPortuaria terminalDestinoMock;
    private PosicionGPS posicionBuqueMock;
    private PosicionGPS posicionDestinoMock;
	private PosicionGPS otraPosicionMock;

    @BeforeEach
    void setUp() {
        inbound = new Inbound();
        buqueMock = mock(Buque.class);
        viajeMock = mock(Viaje.class);
        terminalDestinoMock = mock(TerminalPortuaria.class);
        posicionBuqueMock = mock(PosicionGPS.class);
        posicionDestinoMock = mock(PosicionGPS.class);

        when(buqueMock.getViajeAsignado()).thenReturn(viajeMock);
        when(buqueMock.getPosicionActual()).thenReturn(posicionBuqueMock);
        when(viajeMock.getTerminalDestino()).thenReturn(terminalDestinoMock);
        when(terminalDestinoMock.getPosicion()).thenReturn(posicionDestinoMock);
        when(buqueMock.getPosicionActual()).thenReturn(posicionBuqueMock);
    }

    @Test
    void cambiaAFaseArrivedSiDistanciaEsCero() {
        when(posicionBuqueMock.distanciaHasta(posicionDestinoMock)).thenReturn(0.0);

        inbound.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(buqueMock).setPosicionActual(posicionBuqueMock);
        verify(buqueMock).setFase(any(Arrived.class));
    }

    @Test
    void noCambiaDeFaseSiDistanciaMayorACero() {
        when(posicionBuqueMock.distanciaHasta(posicionDestinoMock)).thenReturn(25.0);

        inbound.actualizarPosicion(buqueMock, otraPosicionMock);

        verify(buqueMock).setPosicionActual(otraPosicionMock);
        verify(buqueMock, never()).setFase(any(Arrived.class));
    }
}
