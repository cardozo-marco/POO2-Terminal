package ordenes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.*;

import entidades.Camion;
import entidades.Cliente;
import entidades.Conductor;
import carga.*;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;
import servicios.*;

public class OrdenDeImportacionTest {
	public OrdenDeImportacion ordenDeImportacion;
	
	//mock para crear orden de importacion
	public Cliente mockCliente;
	public Container mockReefer;
	public Viaje mockViaje;
	public Camion mockCamion;
	public Conductor mockConductor;
	public TerminalPortuaria mockTerminaPortuaria;
	
	//mock para servicios
	public ServicioLavado mockServicioLavado;
	public ServicioElectricidad mockServicioElectricidad;
	public ServicioPesado mockServicioPesado;
	
	//mock para probar fecha de llegada
	public LocalDateTime fechaLlegadaFicticia;
	
	@BeforeEach
	public void setUp() {
		mockCliente = mock(Cliente.class);
		mockReefer = mock(Reefer.class);
		mockViaje = mock(Viaje.class);
		mockCamion = mock(Camion.class);
		mockConductor = mock(Conductor.class);
		mockTerminaPortuaria = mock(TerminalPortuaria.class);
		
		mockServicioLavado = mock(ServicioLavado.class);
		mockServicioElectricidad = mock(ServicioElectricidad.class);
		mockServicioPesado = mock(ServicioPesado.class);
		
		fechaLlegadaFicticia = LocalDateTime.of(2025, 10, 30, 12, 0);
		
		when(mockViaje.getFechaLlegada(mockTerminaPortuaria)).thenReturn(fechaLlegadaFicticia);
		
		ordenDeImportacion = new OrdenDeImportacion(mockCliente, mockReefer, mockViaje, mockCamion, mockConductor, mockTerminaPortuaria);
		// Set fecha to 10 days before arrival date for testing
		ordenDeImportacion.setFecha(LocalDate.of(2025, 10, 20));
		
		when(mockReefer.aceptaServicio(any(Servicio.class))).thenReturn(true);
	}
	
	@Test
	public void FechaDeLlegadaTest() {
		assertEquals(fechaLlegadaFicticia, ordenDeImportacion.getFechaLlegadaCarga());
	}
	
	@Test
	public void AgregarServiciosTest() {
		assertTrue(ordenDeImportacion.serviciosContratados().isEmpty()); //la lista de servicios debe empezar vacia
		
		//agrego 3 servicios
		ordenDeImportacion.agregarServicio(mockServicioLavado);
		ordenDeImportacion.agregarServicio(mockServicioElectricidad);
		ordenDeImportacion.agregarServicio(mockServicioPesado);
		
		//verifico que se hayan agregados los 3 servicios
		List<Servicio> serviciosEsperados = Arrays.asList(mockServicioLavado, mockServicioElectricidad, mockServicioPesado);
		assertEquals(3, ordenDeImportacion.serviciosContratados().size());
		assertEquals(serviciosEsperados, ordenDeImportacion.serviciosContratados());
	}
	
	@Test
	public void CostoTotalDeServiciosTest() {
		//verifico que el costo sea 0 ya que no hay servicios
		assertEquals(0.0, ordenDeImportacion.calcularCostoTotalServicios());
		
		//agrego 3 servicios
		ordenDeImportacion.agregarServicio(mockServicioLavado);
		ordenDeImportacion.agregarServicio(mockServicioElectricidad);
		ordenDeImportacion.agregarServicio(mockServicioPesado);
		
		when(mockServicioLavado.calcularCosto(ordenDeImportacion)).thenReturn(10.0);
		when(mockServicioElectricidad.calcularCosto(ordenDeImportacion)).thenReturn(20.0);
		when(mockServicioPesado.calcularCosto(ordenDeImportacion)).thenReturn(15.0);
		
		//verifico que calcule el costo correcto con los servicios agregados
		assertEquals(45.0, ordenDeImportacion.calcularCostoTotalServicios());
	}
	
	@Test
	public void cantidadDiasTest() {
		assertEquals(10, ordenDeImportacion.cantidadDeDias());
	}
	
	@Test
	public void getVolumenTest() {
		when(mockReefer.getVolumen()).thenReturn(80.0);
		assertEquals(80.0, ordenDeImportacion.getVolumen());
	}
	
	@Test
	public void getContainerTest() {
		assertEquals(mockReefer, ordenDeImportacion.getContainer());
	}
	
	@Test
	public void getViajeTest() {
		assertEquals(mockViaje, ordenDeImportacion.getViaje());
	}
	
	@Test
	public void getClienteTest() {
		assertEquals(mockCliente, ordenDeImportacion.getCliente());
	}
	
	@Test
	public void getCamionTest() {
		assertEquals(mockCamion, ordenDeImportacion.getCamion());
	}
	
	@Test
	public void getConductorTest() {
		assertEquals(mockConductor, ordenDeImportacion.getConductor());
	}
	
	@Test
	public void esOrdenDeImportacionTest() {
		assertTrue(ordenDeImportacion.esOrdenDeImportacion());
	}
	
	@Test
	public void esOrdenDeExportacionTest() {
		assertFalse(ordenDeImportacion.esOrdenDeExportacion());
	}
}
