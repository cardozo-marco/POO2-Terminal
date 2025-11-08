package reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.invocation.*;
import org.mockito.stubbing.*;

import ordenes.*;
import paraPruebas.*;

public class ReporteMuelleVisitorTest {
	public ReporteMuelleVisitor reporte;
	public TerminalGestionada mockTerminal;
	public Naviera mockNaviera;
	public Buque mockBuque;
	public OrdenDeImportacion mockOrdenDeImportacion;
	public OrdenDeExportacion mockOrdenDeExportacion;
	public Viaje mockViaje;
	
	List<Naviera> navieras = new ArrayList<>();
	List<Orden> ordenes = new ArrayList<>();
	List<Buque> buques = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		reporte = new ReporteMuelleVisitor();
		
		mockTerminal = mock(TerminalGestionada.class);
		mockNaviera = mock(Naviera.class);
		mockBuque = mock(Buque.class);
		mockOrdenDeImportacion = mock(OrdenDeImportacion.class);
		mockOrdenDeExportacion = mock(OrdenDeExportacion.class);
		mockViaje = mock(Viaje.class);
		
		navieras.add(mockNaviera);
		when(mockTerminal.getNavieras()).thenReturn(navieras);
		
		ordenes.add(mockOrdenDeExportacion);
		ordenes.add(mockOrdenDeImportacion);
		when(mockTerminal.getOrdenes()).thenReturn(ordenes);
		
		buques.add(mockBuque);
		when(mockNaviera.getBuques()).thenReturn(buques);
		
		when(mockBuque.getViajeAsignado()).thenReturn(mockViaje);
		when(mockBuque.getNombre()).thenReturn("buque1");
		
		when(mockOrdenDeImportacion.getViaje()).thenReturn(mockViaje);
		
		when(mockOrdenDeExportacion.getViaje()).thenReturn(mockViaje);
		
		when(mockViaje.getFechaInicio()).thenReturn(LocalDateTime.of(2025, 11, 7, 12, 00));
		when(mockViaje.getFechaLlegadaA(mockTerminal)).thenReturn(LocalDateTime.of(2025, 11, 20, 12, 00));
		
		
		//le indican al mockito que hacer cuando ivocan el accept
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockTerminal);
				return null;
			}
		}).when(mockTerminal).accept(any(Visitor.class));
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockNaviera);
				return null;
			}
		}).when(mockNaviera).accept(any(Visitor.class));
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockBuque);
				return null;
			}
		}).when(mockBuque).accept(any(Visitor.class));
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockOrdenDeImportacion);
				return null;
			}
		}).when(mockOrdenDeImportacion).accept(any(Visitor.class));
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockOrdenDeExportacion);
				return null;
			}
		}).when(mockOrdenDeExportacion).accept(any(Visitor.class));
	}
	
	@Test
	public void reporteTest() {
		mockTerminal.accept(reporte);
		
		String reporteEsperado = "Buque: buque1\n"
				+ "Fecha de partida: 2025-11-07T12:00\n"
				+ "Fecha de llegada: 2025-11-20T12:00\n"
				+ "Contenedores operados: 2\n";
		
		assertEquals(reporteEsperado, reporte.getReporte());
		
	}
}
