package maritimo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircuitoMaritimoTest {

	private Tramo tramo1;
    private Tramo tramo2;
    private Tramo tramo3;

    private TerminalPortuaria origen;
    private TerminalPortuaria intermedia;
    private TerminalPortuaria destino;

    private CircuitoMaritimo circuito;

    @BeforeEach
    void setUp() {
        tramo1 = mock(Tramo.class);
        tramo2 = mock(Tramo.class);
        tramo3 = mock(Tramo.class);

        origen = mock(TerminalPortuaria.class);
        intermedia = mock(TerminalPortuaria.class);
        destino = mock(TerminalPortuaria.class);

        when(tramo1.getOrigen()).thenReturn(origen);
        when(tramo1.getDestino()).thenReturn(intermedia);

        when(tramo2.getOrigen()).thenReturn(intermedia);
        when(tramo2.getDestino()).thenReturn(destino);

        when(tramo3.getOrigen()).thenReturn(destino);
        when(tramo3.getDestino()).thenReturn(mock(TerminalPortuaria.class)); // tramo final “falso”

        when(tramo1.getTiempo()).thenReturn(10.0);
        when(tramo2.getTiempo()).thenReturn(20.0);
        when(tramo3.getTiempo()).thenReturn(5.0);

        when(tramo1.getPrecio()).thenReturn(100.0);
        when(tramo2.getPrecio()).thenReturn(200.0);
        when(tramo3.getPrecio()).thenReturn(50.0);

        circuito = new CircuitoMaritimo(List.of(tramo1, tramo2, tramo3));
    }

    @Test
    void getTramosDevuelveListaOriginal() {
        List<Tramo> tramos = circuito.getTramos();
        assertEquals(3, tramos.size());
        assertTrue(tramos.contains(tramo1));
    }

    @Test
    void getTiempoTotalSumaCorrectamente() {
        double total = circuito.getTiempoTotal();
        assertEquals(35.0, total);
        verify(tramo1).getTiempo();
        verify(tramo2).getTiempo();
        verify(tramo3).getTiempo();
    }

    @Test
    void getPrecioTotalSumaCorrectamente() {
        double total = circuito.getPrecioTotal();
        assertEquals(350.0, total);
        verify(tramo1).getPrecio();
        verify(tramo2).getPrecio();
        verify(tramo3).getPrecio();
    }

    @Test
    void getTerminalesIntermediasDevuelveSoloLasQueEstanEntreOrigenYDestino() {
        List<TerminalPortuaria> intermedias = circuito.getTerminalesIntermedias(origen, destino);

        assertEquals(1, intermedias.size());
        assertTrue(intermedias.contains(intermedia));
    }

    @Test
    void getTerminalesIntermediasSinIntermediasDevuelveListaVacia() {
        when(tramo1.getDestino()).thenReturn(destino);

        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1, tramo2));

        List<TerminalPortuaria> intermedias =
                circuitoDirecto.getTerminalesIntermedias(origen, destino);

        assertTrue(intermedias.isEmpty());
    }
    
    @Test
    void cantidadTerminalesEntreCuentaCorrectamente() {
        int cantidad = circuito.cantidadTerminalesEntre(origen, destino);
        assertEquals(1, cantidad);
    }
    
    @Test
    void precioEntreTerminalesSumaCorrectamente() {
        double precio = circuito.precioEntreTerminales(origen, destino);
        assertEquals(300.0, precio);
    }

    @Test
    void tiempoEntreTerminalesSumaCorrectamente() {
        double tiempo = circuito.tiempoEntreTerminales(origen, destino);
        assertEquals(30.0, tiempo);
    }

}
