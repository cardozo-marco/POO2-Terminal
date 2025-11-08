package carga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class BLCompuestoTest {
	public BLCompuesto compuesto;
	
	public BLSimple latas;
	public BLSimple cajas;
	public BLSimple sillas;
	
	@BeforeEach
	public void setUp() {
		compuesto = new BLCompuesto();
		
		latas = new BLSimple("latas", 2000);
		cajas = new BLSimple("cajas", 4000);
		sillas = new BLSimple("sillas", 2000);
	}
	
	@Test
	public void obtenerPeso() {
		//verifico que el peso sea 0 ya que su lista esta vacia
		assertEquals(0, compuesto.getPesoTotal());
		
		//agrego elementos
		compuesto.agregarProducto(latas);
		compuesto.agregarProducto(cajas);
		compuesto.agregarProducto(sillas);
		
		//verifico el peso de los productos agregados
		assertEquals(8000, compuesto.getPesoTotal());
	}
}
