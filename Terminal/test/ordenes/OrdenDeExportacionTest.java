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

public class OrdenDeExportacionTest {
	public OrdenDeExportacion ordenDeExportacion;
	
	public Cliente mockCliente;
	public Container mockContainer;
	public Viaje mockViaje;
	public Camion mockCamion;
	public Conductor mockConductor;
	public TerminalPortuaria mockTerminalPortuaria;
	
	public ServicioLavado mockLavado;
	public ServicioElectricidad mockElectricidad;
	
	public LocalDateTime turnoFicticio;
	
	@BeforeEach
	public void setUp() {
		mockCliente = mock(Cliente.class);
		mockContainer = mock(Container.class);
		mockViaje = mock(Viaje.class);
		mockCamion = mock(Camion.class);
		mockConductor = mock(Conductor.class);
		mockTerminalPortuaria = mock(TerminalPortuaria.class);
		
		mockLavado = mock(ServicioLavado.class);
		mockElectricidad = mock(ServicioElectricidad.class);
		
		turnoFicticio = LocalDateTime.of(2025, 10, 23, 18, 0);
		
		ordenDeExportacion = new OrdenDeExportacion(mockCliente, mockContainer, mockViaje, mockCamion, mockConductor, mockTerminalPortuaria);
	}
	
	@Test
	public void tunoAsignadoTest() {
		ordenDeExportacion.setTurnoAsignado(turnoFicticio);
		assertEquals(turnoFicticio, ordenDeExportacion.getTurnoAsignado());
	}
	
	@Test
	public void agregarServiciosTest() {
		//verifico que no tengo servicios
		assertTrue(ordenDeExportacion.serviciosContratados().isEmpty());
		
		ordenDeExportacion.agregarServicio(mockElectricidad);
		ordenDeExportacion.agregarServicio(mockLavado);
		
		List<Servicio> serviciosEsperados = Arrays.asList(mockElectricidad, mockLavado);
		
		assertEquals(2, ordenDeExportacion.serviciosContratados().size());
		assertEquals(serviciosEsperados, ordenDeExportacion.serviciosContratados());
	}
	
	@Test
	public void costoTotalTest() {
		assertEquals(0.0, ordenDeExportacion.calcularCostoTotalServicios());
		
		when(mockElectricidad.calcularCosto(any(Orden.class))).thenReturn(20.0);
		when(mockLavado.calcularCosto(any(Orden.class))).thenReturn(10.0);
		
		ordenDeExportacion.agregarServicio(mockLavado);
		ordenDeExportacion.agregarServicio(mockElectricidad);
		
		assertEquals(30.0, ordenDeExportacion.calcularCostoTotalServicios());
	}
	
	@Test
	public void cantidadDeDias() {
		// Set fecha to 10 days before turno date for testing
		ordenDeExportacion.setFecha(LocalDate.of(2025, 10, 20));
		ordenDeExportacion.setTurnoAsignado(LocalDateTime.of(2025, 10, 30, 12, 0));
		assertEquals(10, ordenDeExportacion.cantidadDeDias());
	}
	
	@Test
	public void getVolumenTest() {
		when(mockContainer.getVolumen()).thenReturn(80.0);
		assertEquals(80.0, ordenDeExportacion.getVolumen());
	}
	
	@Test
	public void getContainerTest() {
		assertEquals(mockContainer, ordenDeExportacion.getContainer());
	}
	
	@Test
	public void getViajeTest() {
		assertEquals(mockViaje, ordenDeExportacion.getViaje());
	}
	
	@Test
	public void getClienteTest() {
		assertEquals(mockCliente, ordenDeExportacion.getCliente());
	}
	
	@Test
	public void getCamionTest() {
		assertEquals(mockCamion, ordenDeExportacion.getCamion());
	}
	
	@Test
	public void getConductorTest() {
		assertEquals(mockConductor, ordenDeExportacion.getConductor());
	}
	
	@Test
	public void esOrdenDeExportacionTest() {
		assertTrue(ordenDeExportacion.esOrdenDeExportacion());
	}
	
	@Test
	public void esOrdenDeImportacionTest() {
		assertFalse(ordenDeExportacion.esOrdenDeImportacion());
	}
}
