package maritimo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViajeTest {

    private CircuitoMaritimo circuito;
    private Buque buqueMock;
    private TerminalPortuaria origen;
    private TerminalPortuaria intermedio;
    private TerminalPortuaria destino;
    private LocalDate fechaInicio;
    private Viaje viaje;

    @BeforeEach
    void setUp() {
        origen = new TerminalPortuaria("Buenos Aires", new PosicionGPS(0, 0));
        intermedio = new TerminalPortuaria("Montevideo", new PosicionGPS(100, 50));
        destino = new TerminalPortuaria("Valparaiso", new PosicionGPS(200, 100));

        Tramo tramo1 = new Tramo(origen, intermedio, 500, 5);
        Tramo tramo2 = new Tramo(intermedio, destino, 800, 8);

        circuito = new CircuitoMaritimo(List.of(tramo1, tramo2));
        buqueMock = new Buque(null, new PosicionGPS(0, 0));
        fechaInicio = LocalDate.of(2025, 11, 5);

        viaje = new Viaje(circuito, buqueMock, fechaInicio);
    }

    @Test
    void constructorYGettersFuncionanCorrectamente() {
        assertEquals(circuito, viaje.getCircuito());
        assertEquals(buqueMock, viaje.getBuque());
        assertEquals(fechaInicio, viaje.getFechaInicio());
        assertEquals(origen, viaje.getTerminalOrigen());
        assertEquals(destino, viaje.getTerminalDestino());
    }
}
