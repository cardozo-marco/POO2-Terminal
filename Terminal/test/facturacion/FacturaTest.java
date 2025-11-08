package facturacion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actores.Shipper;

public class FacturaTest {
	
	private Shipper cliente;
	private Factura factura;
	
	@BeforeEach
	public void setUp() {
		cliente = new Shipper("Juan Pérez", "juan@example.com");
		factura = new Factura(cliente);
	}
	
	// ========== TESTS CONSTRUCTOR ==========
	
	@Test
	public void testConstructor_ConCliente_CreaFacturaConCliente() {
		assertEquals(cliente, factura.getCliente());
	}
	
	@Test
	public void testConstructor_ConCliente_EstableceFechaActual() {
		LocalDate fechaEsperada = LocalDate.now();
		assertEquals(fechaEsperada, factura.getFecha());
	}
	
	@Test
	public void testConstructor_ConCliente_InicializaMontoTotalEnCero() {
		assertEquals(0.0, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testConstructor_ConCliente_InicializaListaVaciaDeItems() {
		assertTrue(factura.getItems().isEmpty());
		assertEquals(0, factura.getItems().size());
	}
	
	// ========== TESTS AGREGAR ITEM ==========
	
	@Test
	public void testAgregarItem_ConUnItem_AgregaItemALaLista() {
		factura.agregarItem("Servicio de almacenamiento", 1000.0);
		
		List<ItemFactura> items = factura.getItems();
		assertEquals(1, items.size());
		assertEquals("Servicio de almacenamiento", items.get(0).getDescripcion());
		assertEquals(1000.0, items.get(0).getMonto(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConUnItem_ActualizaMontoTotal() {
		factura.agregarItem("Servicio de almacenamiento", 1000.0);
		
		assertEquals(1000.0, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConMultiplesItems_AgregaTodosLosItems() {
		factura.agregarItem("Servicio de almacenamiento", 1000.0);
		factura.agregarItem("Servicio de electricidad", 500.0);
		factura.agregarItem("Servicio de lavado", 300.0);
		
		List<ItemFactura> items = factura.getItems();
		assertEquals(3, items.size());
		assertEquals("Servicio de almacenamiento", items.get(0).getDescripcion());
		assertEquals("Servicio de electricidad", items.get(1).getDescripcion());
		assertEquals("Servicio de lavado", items.get(2).getDescripcion());
	}
	
	@Test
	public void testAgregarItem_ConMultiplesItems_SumaMontoTotalCorrectamente() {
		factura.agregarItem("Servicio de almacenamiento", 1000.0);
		factura.agregarItem("Servicio de electricidad", 500.0);
		factura.agregarItem("Servicio de lavado", 300.0);
		
		assertEquals(1800.0, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConMontoDecimal_CalculaCorrectamente() {
		factura.agregarItem("Servicio", 123.45);
		factura.agregarItem("Otro servicio", 67.89);
		
		assertEquals(191.34, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConMontoCero_AgregaItemConMontoCero() {
		factura.agregarItem("Servicio gratuito", 0.0);
		
		assertEquals(0.0, factura.getMontoTotal(), 0.001);
		assertEquals(1, factura.getItems().size());
		assertEquals(0.0, factura.getItems().get(0).getMonto(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConMontoNegativo_AgregaMontoNegativo() {
		factura.agregarItem("Descuento", -100.0);
		
		assertEquals(-100.0, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testAgregarItem_ConDescripcionVacia_AgregaItemConDescripcionVacia() {
		factura.agregarItem("", 100.0);
		
		assertEquals("", factura.getItems().get(0).getDescripcion());
		assertEquals(100.0, factura.getMontoTotal(), 0.001);
	}
	
	// ========== TESTS GETTERS ==========
	
	@Test
	public void testGetCliente_RetornaClienteCorrecto() {
		assertEquals(cliente, factura.getCliente());
		assertEquals("Juan Pérez", factura.getCliente().getNombre());
		assertEquals("juan@example.com", factura.getCliente().getEmail());
	}
	
	@Test
	public void testGetFecha_RetornaFechaDeCreacion() {
		LocalDate fechaAntes = LocalDate.now();
		Factura nuevaFactura = new Factura(cliente);
		LocalDate fechaDespues = LocalDate.now();
		
		LocalDate fechaFactura = nuevaFactura.getFecha();
		assertTrue(fechaFactura.isEqual(fechaAntes) || 
				   fechaFactura.isEqual(fechaDespues) ||
				   (fechaFactura.isAfter(fechaAntes) && fechaFactura.isBefore(fechaDespues.plusDays(1))));
	}
	
	@Test
	public void testGetItems_RetornaListaModificable() {
		factura.agregarItem("Item 1", 100.0);
		
		List<ItemFactura> items = factura.getItems();
		items.add(new ItemFactura("Item 2", 200.0));
		
		assertEquals(2, factura.getItems().size());
	}
	
	@Test
	public void testGetMontoTotal_SinItems_RetornaCero() {
		assertEquals(0.0, factura.getMontoTotal(), 0.001);
	}
	
	@Test
	public void testGetMontoTotal_ConItems_RetornaSumaCorrecta() {
		factura.agregarItem("Item 1", 100.0);
		factura.agregarItem("Item 2", 200.0);
		factura.agregarItem("Item 3", 300.0);
		
		assertEquals(600.0, factura.getMontoTotal(), 0.001);
	}
	
	// ========== TESTS INTEGRACION ==========
	
	@Test
	public void testFacturaCompleta_ConVariosItems_CalculaTotalCorrecto() {
		factura.agregarItem("Almacenamiento por 30 días", 5000.0);
		factura.agregarItem("Servicio de electricidad", 1500.0);
		factura.agregarItem("Servicio de lavado", 800.0);
		factura.agregarItem("Descuento por volumen", -500.0);
		
		assertEquals(4, factura.getItems().size());
		assertEquals(6800.0, factura.getMontoTotal(), 0.001);
		assertEquals(cliente, factura.getCliente());
	}
}

