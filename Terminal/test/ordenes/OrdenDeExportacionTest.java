package ordenes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.*;

import actores.Camion;
import actores.Conductor;
import actores.Shipper;
import carga.*;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;
import servicios.*;

public class OrdenDeExportacionTest {
	public OrdenDeExportacion ordenDeExportacion;
	
	public Shipper mockShipper;
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
		mockShipper = mock(Shipper.class);
		mockContainer = mock(Container.class);
		mockViaje = mock(Viaje.class);
		mockCamion = mock(Camion.class);
		mockConductor = mock(Conductor.class);
		mockTerminalPortuaria = mock(TerminalPortuaria.class);
		
		mockLavado = mock(ServicioLavado.class);
		mockElectricidad = mock(ServicioElectricidad.class);
		
		turnoFicticio = LocalDateTime.of(2025, 10, 23, 18, 0);
		
		ordenDeExportacion = new OrdenDeExportacion(mockShipper, mockContainer, mockViaje, mockCamion, mockConductor, mockTerminalPortuaria);
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
		ordenDeExportacion.setFecha(LocalDate.of(2025, 10, 20));
		ordenDeExportacion.setTurnoAsignado(LocalDateTime.of(2025, 10, 30, 12, 0));
		assertEquals(10, ordenDeExportacion.cantidadDeDias());
	}
	
	@Test
	public void getVolumenTest() {
		when(mockContainer.getVolumen()).thenReturn(80.0);
		assertEquals(80.0, ordenDeExportacion.getVolumen());
	}
}
