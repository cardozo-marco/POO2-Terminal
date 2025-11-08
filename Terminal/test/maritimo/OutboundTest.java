package maritimo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutboundTest {

    private Outbound outbound;
    private Buque buqueMock;
    private Viaje viajeMock;
    private TerminalPortuaria terminalDestinoMock;
    private PosicionGPS posicionBuqueMock;
    private PosicionGPS posicionDestinoMock;

    @BeforeEach
    void setUp() {
        outbound = new Outbound();

        buqueMock = mock(Buque.class);
        viajeMock = mock(Viaje.class);
        terminalDestinoMock = mock(TerminalPortuaria.class);
        posicionBuqueMock = mock(PosicionGPS.class);
        posicionDestinoMock = mock(PosicionGPS.class);

        when(buqueMock.getViajeAsignado()).thenReturn(viajeMock);
        when(viajeMock.getTerminalDestino()).thenReturn(terminalDestinoMock);
        when(terminalDestinoMock.getPosicion()).thenReturn(posicionDestinoMock);
        when(buqueMock.getPosicionActual()).thenReturn(posicionBuqueMock);
    }

    @Test
    void noCambiaAFaseInboundSiDistanciaMayorA50() {
        when(posicionBuqueMock.distanciaHasta(posicionDestinoMock)).thenReturn(120.0);

        outbound.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(buqueMock).setPosicionActual(posicionBuqueMock);
        verify(buqueMock, never()).setFase(any(Inbound.class));
    }

    @Test
    void cambiaAFaseInboundSiDistanciaMenorA50() {
        when(posicionBuqueMock.distanciaHasta(posicionDestinoMock)).thenReturn(30.0);

        outbound.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(buqueMock).setPosicionActual(posicionBuqueMock);
        verify(buqueMock).setFase(any(Inbound.class));
    }

    @Test
    void calculaDistanciaCorrectamenteAlActualizar() {
        when(posicionBuqueMock.distanciaHasta(posicionDestinoMock)).thenReturn(40.0);

        outbound.actualizarPosicion(buqueMock, posicionBuqueMock);

        verify(posicionBuqueMock).distanciaHasta(posicionDestinoMock);
    }

}
