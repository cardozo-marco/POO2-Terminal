package entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Camion;
import entidades.Conductor;
import entidades.EmpresaTransportista;

public class EmpresaTransportistaTest {
	
	private EmpresaTransportista empresa;
	private Camion camion1;
	private Camion camion2;
	private Conductor conductor1;
	private Conductor conductor2;
	
	@BeforeEach
	public void setUp() {
		empresa = new EmpresaTransportista();
		camion1 = new Camion("ABC123");
		camion2 = new Camion("XYZ789");
		conductor1 = new Conductor("Juan Perez", "12345678");
		conductor2 = new Conductor("Maria Garcia", "87654321");
	}
	
	@Test
	public void constructorInicializaListasVaciasTest() {
		assertTrue(empresa.getCamiones().isEmpty());
		assertTrue(empresa.getConductores().isEmpty());
	}
	
	@Test
	public void addCamionTest() {
		empresa.addCamion(camion1);
		assertEquals(1, empresa.getCamiones().size());
		assertTrue(empresa.getCamiones().contains(camion1));
		
		empresa.addCamion(camion2);
		assertEquals(2, empresa.getCamiones().size());
		assertTrue(empresa.getCamiones().contains(camion2));
	}
	
	@Test
	public void addConductorTest() {
		empresa.addConductor(conductor1);
		assertEquals(1, empresa.getConductores().size());
		assertTrue(empresa.getConductores().contains(conductor1));
		
		empresa.addConductor(conductor2);
		assertEquals(2, empresa.getConductores().size());
		assertTrue(empresa.getConductores().contains(conductor2));
	}
	
	@Test
	public void multiplesCamionesYConductoresTest() {
		empresa.addCamion(camion1);
		empresa.addCamion(camion2);
		empresa.addConductor(conductor1);
		empresa.addConductor(conductor2);
		
		assertEquals(2, empresa.getCamiones().size());
		assertEquals(2, empresa.getConductores().size());
	}
}

