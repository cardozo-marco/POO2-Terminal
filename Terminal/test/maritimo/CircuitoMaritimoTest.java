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
    void getTerminalesIntermediasConOrigenNoEncontrado_DevuelveListaVacia() {
        TerminalPortuaria origenInexistente = mock(TerminalPortuaria.class);
        List<TerminalPortuaria> intermedias = circuito.getTerminalesIntermedias(origenInexistente, destino);
        assertTrue(intermedias.isEmpty());
    }
    
    @Test
    void getTerminalesIntermediasConMultiplesIntermedias_DevuelveTodas() {
        TerminalPortuaria intermedia2 = mock(TerminalPortuaria.class);
        Tramo tramoExtra = mock(Tramo.class);
        
        when(tramo2.getDestino()).thenReturn(intermedia2);
        when(tramoExtra.getOrigen()).thenReturn(intermedia2);
        when(tramoExtra.getDestino()).thenReturn(destino);
        
        CircuitoMaritimo circuitoConMultiples = new CircuitoMaritimo(List.of(tramo1, tramo2, tramoExtra));
        List<TerminalPortuaria> intermedias = circuitoConMultiples.getTerminalesIntermedias(origen, destino);
        
        assertEquals(2, intermedias.size());
        assertTrue(intermedias.contains(intermedia));
        assertTrue(intermedias.contains(intermedia2));
    }
    
    // ========== TESTS CONTIENE TERMINALES ==========
    
    @Test
    void contieneTerminales_OrigenYDestinoExisten_RetornaTrue() {
        assertTrue(circuito.contieneTerminales(origen, destino));
    }
    
    @Test
    void contieneTerminales_OrigenYDestinoEnMismoTramo_RetornaTrue() {
        when(tramo1.getDestino()).thenReturn(destino);
        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1));
        assertTrue(circuitoDirecto.contieneTerminales(origen, destino));
    }
    
    @Test
    void contieneTerminales_OrigenNoExiste_RetornaFalse() {
        TerminalPortuaria origenInexistente = mock(TerminalPortuaria.class);
        assertFalse(circuito.contieneTerminales(origenInexistente, destino));
    }
    
    @Test
    void contieneTerminales_DestinoNoExiste_RetornaFalse() {
        TerminalPortuaria destinoInexistente = mock(TerminalPortuaria.class);
        assertFalse(circuito.contieneTerminales(origen, destinoInexistente));
    }
    
    @Test
    void contieneTerminales_OrigenEnTramoPosterior_RetornaTrue() {
        // Caso donde el origen no está en el primer tramo
        TerminalPortuaria otroOrigen = mock(TerminalPortuaria.class);
        when(tramo1.getOrigen()).thenReturn(otroOrigen);
        when(tramo2.getOrigen()).thenReturn(origen);
        
        assertTrue(circuito.contieneTerminales(origen, destino));
    }
    
    @Test
    void contieneTerminales_CircuitoVacio_RetornaFalse() {
        CircuitoMaritimo circuitoVacio = new CircuitoMaritimo(List.of());
        assertFalse(circuitoVacio.contieneTerminales(origen, destino));
    }
    
    @Test
    void contieneTerminales_DestinoDespuesDeOrigenPeroNoConectado_RetornaFalse() {
        // Caso donde el destino existe en el circuito pero no está conectado al origen
        // El destino está en un tramo que viene antes del origen
        // Nota: El método contieneTerminales no verifica conectividad, solo verifica
        // si el destino aparece después del origen. Para que retorne false, el destino
        // debe estar en un tramo que viene ANTES del origen o no existir.
        TerminalPortuaria otroOrigen = mock(TerminalPortuaria.class);
        TerminalPortuaria destinoDesconectado = mock(TerminalPortuaria.class);
        Tramo tramoAntes = mock(Tramo.class);
        
        // Tramo que viene antes del origen (no conectado)
        when(tramoAntes.getOrigen()).thenReturn(otroOrigen);
        when(tramoAntes.getDestino()).thenReturn(destinoDesconectado);
        
        // Tramo con el origen que buscamos
        when(tramo1.getOrigen()).thenReturn(origen);
        when(tramo1.getDestino()).thenReturn(intermedia);
        
        // El destino que buscamos está en tramoAntes, que viene ANTES del origen
        CircuitoMaritimo circuitoDesconectado = new CircuitoMaritimo(List.of(tramoAntes, tramo1, tramo2));
        assertFalse(circuitoDesconectado.contieneTerminales(origen, destinoDesconectado));
    }
    
    // ========== TESTS PRECIO ENTRE TERMINALES ==========
    
    @Test
    void precioEntreTerminales_OrigenYDestinoExisten_SumaCorrectamente() {
        double precio = circuito.precioEntreTerminales(origen, destino);
        assertEquals(300.0, precio); // tramo1 (100) + tramo2 (200)
        verify(tramo1).getPrecio();
        verify(tramo2).getPrecio();
    }
    
    @Test
    void precioEntreTerminales_OrigenYDestinoEnMismoTramo_RetornaPrecioDelTramo() {
        when(tramo1.getDestino()).thenReturn(destino);
        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1));
        double precio = circuitoDirecto.precioEntreTerminales(origen, destino);
        assertEquals(100.0, precio);
    }
    
    @Test
    void precioEntreTerminales_OrigenNoExiste_RetornaCero() {
        TerminalPortuaria origenInexistente = mock(TerminalPortuaria.class);
        double precio = circuito.precioEntreTerminales(origenInexistente, destino);
        assertEquals(0.0, precio);
    }
    
    @Test
    void precioEntreTerminales_DestinoNoExiste_RetornaSumaParcial() {
        TerminalPortuaria destinoInexistente = mock(TerminalPortuaria.class);
        double precio = circuito.precioEntreTerminales(origen, destinoInexistente);
        // Debe sumar todos los tramos desde el origen hasta el final
        assertEquals(350.0, precio); // tramo1 + tramo2 + tramo3
    }
    
    @Test
    void precioEntreTerminales_OrigenEnTramoPosterior_SumaDesdeEseTramo() {
        TerminalPortuaria otroOrigen = mock(TerminalPortuaria.class);
        when(tramo1.getOrigen()).thenReturn(otroOrigen);
        when(tramo2.getOrigen()).thenReturn(origen);
        
        double precio = circuito.precioEntreTerminales(origen, destino);
        assertEquals(200.0, precio); // Solo tramo2
    }
    
    // ========== TESTS TIEMPO ENTRE TERMINALES ==========
    
    @Test
    void tiempoEntreTerminales_OrigenYDestinoExisten_SumaCorrectamente() {
        double tiempo = circuito.tiempoEntreTerminales(origen, destino);
        assertEquals(30.0, tiempo); // tramo1 (10) + tramo2 (20)
        verify(tramo1).getTiempo();
        verify(tramo2).getTiempo();
    }
    
    @Test
    void tiempoEntreTerminales_OrigenYDestinoEnMismoTramo_RetornaTiempoDelTramo() {
        when(tramo1.getDestino()).thenReturn(destino);
        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1));
        double tiempo = circuitoDirecto.tiempoEntreTerminales(origen, destino);
        assertEquals(10.0, tiempo);
    }
    
    @Test
    void tiempoEntreTerminales_OrigenNoExiste_RetornaCero() {
        TerminalPortuaria origenInexistente = mock(TerminalPortuaria.class);
        double tiempo = circuito.tiempoEntreTerminales(origenInexistente, destino);
        assertEquals(0.0, tiempo);
    }
    
    @Test
    void tiempoEntreTerminales_DestinoNoExiste_RetornaSumaParcial() {
        TerminalPortuaria destinoInexistente = mock(TerminalPortuaria.class);
        double tiempo = circuito.tiempoEntreTerminales(origen, destinoInexistente);
        // Debe sumar todos los tramos desde el origen hasta el final
        assertEquals(35.0, tiempo); // tramo1 + tramo2 + tramo3
    }
    
    @Test
    void tiempoEntreTerminales_OrigenEnTramoPosterior_SumaDesdeEseTramo() {
        TerminalPortuaria otroOrigen = mock(TerminalPortuaria.class);
        when(tramo1.getOrigen()).thenReturn(otroOrigen);
        when(tramo2.getOrigen()).thenReturn(origen);
        
        double tiempo = circuito.tiempoEntreTerminales(origen, destino);
        assertEquals(20.0, tiempo); // Solo tramo2
    }
    
    // ========== TESTS CANTIDAD TERMINALES ENTRE ==========
    
    @Test
    void cantidadTerminalesEntre_ConTerminalIntermedia_RetornaUno() {
        int cantidad = circuito.cantidadTerminalesEntre(origen, destino);
        assertEquals(1, cantidad); // Solo intermedia
    }
    
    @Test
    void cantidadTerminalesEntre_SinTerminalesIntermedias_RetornaCero() {
        when(tramo1.getDestino()).thenReturn(destino);
        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1));
        int cantidad = circuitoDirecto.cantidadTerminalesEntre(origen, destino);
        assertEquals(0, cantidad);
    }
    
    @Test
    void cantidadTerminalesEntre_ConMultiplesTerminalesIntermedias_RetornaCantidadCorrecta() {
        TerminalPortuaria intermedia2 = mock(TerminalPortuaria.class);
        Tramo tramoExtra = mock(Tramo.class);
        
        when(tramo2.getDestino()).thenReturn(intermedia2);
        when(tramoExtra.getOrigen()).thenReturn(intermedia2);
        when(tramoExtra.getDestino()).thenReturn(destino);
        
        CircuitoMaritimo circuitoConMultiples = new CircuitoMaritimo(List.of(tramo1, tramo2, tramoExtra));
        int cantidad = circuitoConMultiples.cantidadTerminalesEntre(origen, destino);
        assertEquals(2, cantidad); // intermedia + intermedia2
    }
    
    @Test
    void cantidadTerminalesEntre_OrigenNoExiste_RetornaCero() {
        TerminalPortuaria origenInexistente = mock(TerminalPortuaria.class);
        int cantidad = circuito.cantidadTerminalesEntre(origenInexistente, destino);
        assertEquals(0, cantidad);
    }
    
    @Test
    void cantidadTerminalesEntre_DestinoNoExiste_RetornaCantidadHastaFinal() {
        TerminalPortuaria destinoInexistente = mock(TerminalPortuaria.class);
        int cantidad = circuito.cantidadTerminalesEntre(origen, destinoInexistente);
        // Cuenta todas las terminales desde origen hasta el final del circuito
        // tramo1: origen -> intermedia (cuenta intermedia = 1)
        // tramo2: intermedia -> destino (cuenta destino = 2)
        // tramo3: destino -> otra terminal (cuenta otra terminal = 3)
        assertEquals(3, cantidad);
    }
    
    @Test
    void cantidadTerminalesEntre_OrigenEnTramoPosterior_RetornaCantidadDesdeEseTramo() {
        TerminalPortuaria otroOrigen = mock(TerminalPortuaria.class);
        when(tramo1.getOrigen()).thenReturn(otroOrigen);
        when(tramo2.getOrigen()).thenReturn(origen);
        
        int cantidad = circuito.cantidadTerminalesEntre(origen, destino);
        // Desde origen (tramo2) hasta destino, no hay terminales intermedias
        // porque tramo2 va directo de origen a destino
        assertEquals(0, cantidad);
    }
    
    @Test
    void cantidadTerminalesEntre_OrigenYDestinoEnMismoTramo_RetornaCero() {
        when(tramo1.getDestino()).thenReturn(destino);
        CircuitoMaritimo circuitoDirecto = new CircuitoMaritimo(List.of(tramo1));
        int cantidad = circuitoDirecto.cantidadTerminalesEntre(origen, destino);
        assertEquals(0, cantidad);
    }

}
