package facturacion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ItemFacturaTest {
	
	// ========== TESTS CONSTRUCTOR ==========
	
	@Test
	public void testConstructor_ConDescripcionYMonto_CreaItemCorrectamente() {
		ItemFactura item = new ItemFactura("Servicio de almacenamiento", 1000.0);
		
		assertEquals("Servicio de almacenamiento", item.getDescripcion());
		assertEquals(1000.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testConstructor_ConDescripcionVacia_CreaItemConDescripcionVacia() {
		ItemFactura item = new ItemFactura("", 500.0);
		
		assertEquals("", item.getDescripcion());
		assertEquals(500.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testConstructor_ConMontoCero_CreaItemConMontoCero() {
		ItemFactura item = new ItemFactura("Servicio gratuito", 0.0);
		
		assertEquals("Servicio gratuito", item.getDescripcion());
		assertEquals(0.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testConstructor_ConMontoNegativo_CreaItemConMontoNegativo() {
		ItemFactura item = new ItemFactura("Descuento", -100.0);
		
		assertEquals("Descuento", item.getDescripcion());
		assertEquals(-100.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testConstructor_ConMontoDecimal_CreaItemConMontoDecimal() {
		ItemFactura item = new ItemFactura("Servicio", 123.45);
		
		assertEquals("Servicio", item.getDescripcion());
		assertEquals(123.45, item.getMonto(), 0.001);
	}
	
	@Test
	public void testConstructor_ConMontoGrande_CreaItemConMontoGrande() {
		ItemFactura item = new ItemFactura("Servicio premium", 999999.99);
		
		assertEquals("Servicio premium", item.getDescripcion());
		assertEquals(999999.99, item.getMonto(), 0.001);
	}
	
	// ========== TESTS GETTERS ==========
	
	@Test
	public void testGetDescripcion_RetornaDescripcionCorrecta() {
		ItemFactura item = new ItemFactura("Servicio de almacenamiento", 1000.0);
		
		assertEquals("Servicio de almacenamiento", item.getDescripcion());
	}
	
	@Test
	public void testGetDescripcion_ConDescripcionLarga_RetornaDescripcionCompleta() {
		String descripcionLarga = "Servicio de almacenamiento de contenedores refrigerados por período extendido";
		ItemFactura item = new ItemFactura(descripcionLarga, 5000.0);
		
		assertEquals(descripcionLarga, item.getDescripcion());
	}
	
	@Test
	public void testGetMonto_RetornaMontoCorrecto() {
		ItemFactura item = new ItemFactura("Servicio", 1500.0);
		
		assertEquals(1500.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testGetMonto_ConMontoDecimal_RetornaMontoConPrecision() {
		ItemFactura item = new ItemFactura("Servicio", 123.456789);
		
		assertEquals(123.456789, item.getMonto(), 0.000001);
	}
	
	// ========== TESTS INMUTABILIDAD ==========
	
	@Test
	public void testItemFactura_EsInmutable_NoPermiteModificacion() {
		ItemFactura item = new ItemFactura("Servicio", 1000.0);
		
		// Los getters retornan valores, pero no hay setters
		// Esto verifica que el objeto es inmutable después de la creación
		String descripcion = item.getDescripcion();
		double monto = item.getMonto();
		
		// Verificar que los valores no cambian
		assertEquals("Servicio", descripcion);
		assertEquals(1000.0, monto, 0.001);
		assertEquals("Servicio", item.getDescripcion());
		assertEquals(1000.0, item.getMonto(), 0.001);
	}
	
	// ========== TESTS CASOS ESPECIALES ==========
	
	@Test
	public void testItemFactura_ConCaracteresEspecialesEnDescripcion_AlmacenaCorrectamente() {
		ItemFactura item = new ItemFactura("Servicio #123 - Contenedor (Refrigerado)", 2000.0);
		
		assertEquals("Servicio #123 - Contenedor (Refrigerado)", item.getDescripcion());
		assertEquals(2000.0, item.getMonto(), 0.001);
	}
	
	@Test
	public void testItemFactura_ConMontoMuyPequeno_AlmacenaCorrectamente() {
		ItemFactura item = new ItemFactura("Servicio mínimo", 0.01);
		
		assertEquals(0.01, item.getMonto(), 0.001);
	}
	
	@Test
	public void testItemFactura_MultiplesInstancias_SonIndependientes() {
		ItemFactura item1 = new ItemFactura("Servicio 1", 100.0);
		ItemFactura item2 = new ItemFactura("Servicio 2", 200.0);
		ItemFactura item3 = new ItemFactura("Servicio 3", 300.0);
		
		assertEquals("Servicio 1", item1.getDescripcion());
		assertEquals(100.0, item1.getMonto(), 0.001);
		
		assertEquals("Servicio 2", item2.getDescripcion());
		assertEquals(200.0, item2.getMonto(), 0.001);
		
		assertEquals("Servicio 3", item3.getDescripcion());
		assertEquals(300.0, item3.getMonto(), 0.001);
	}
}

