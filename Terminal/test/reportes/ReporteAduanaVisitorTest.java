package reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.invocation.*;
import org.mockito.stubbing.*;

import carga.*;
import maritimo.Buque;
import maritimo.Naviera;
import maritimo.Viaje;
import ordenes.*;
import terminal.TerminalGestionada;

public class ReporteAduanaVisitorTest {
	public ReporteAduanaVisitor reporte;
	
	public TerminalGestionada mockTerminal;
	public Naviera mockNaviera;
	public Buque mockBuque;
	public OrdenDeImportacion mockOrdenDeImportacion;
	public OrdenDeExportacion mockOrdenDeExportacion;
	public Dry mockDry;
	public Reefer mockReefer;
	public Tanque mockTanque;
	public Viaje mockViaje;
	
	List<Naviera> navieras;
	List<Orden> ordenes;
	List<Buque> buques;
	
	@BeforeEach
	public void setUp() {
		reporte = new ReporteAduanaVisitor();
		
		mockTerminal = mock(TerminalGestionada.class);
		mockNaviera = mock(Naviera.class);
		mockBuque = mock(Buque.class);
		mockOrdenDeImportacion = mock(OrdenDeImportacion.class);
		mockOrdenDeExportacion = mock(OrdenDeExportacion.class);
		mockDry = mock(Dry.class);
		mockReefer = mock(Reefer.class);
		mockTanque = mock(Tanque.class);
		mockViaje = mock(Viaje.class);
		
		navieras = new ArrayList<>();
		ordenes = new ArrayList<>();
		buques = new ArrayList<>();
		
		navieras.add(mockNaviera);
		when(mockTerminal.getNavierasRegistradas()).thenReturn(navieras);
		ordenes.add(mockOrdenDeExportacion);
		ordenes.add(mockOrdenDeImportacion);
		when(mockTerminal.getOrdenes()).thenReturn(ordenes);
		
		buques.add(mockBuque);
		when(mockNaviera.getBuques()).thenReturn(buques);
		
		when(mockBuque.getViajeAsignado()).thenReturn(mockViaje);
		
		when(mockOrdenDeImportacion.getViaje()).thenReturn(mockViaje);
		when(mockOrdenDeImportacion.getContainer()).thenReturn(mockDry);
		
		when(mockOrdenDeExportacion.getViaje()).thenReturn(mockViaje);
		when(mockOrdenDeExportacion.getContainer()).thenReturn(mockReefer);
		
		when(mockBuque.getNombre()).thenReturn("buque");
		
		when(mockViaje.getFechaInicio()).thenReturn(LocalDateTime.of(2025, 11, 7, 12, 00));
		when(mockViaje.getFechaLlegada(mockTerminal.getTerminal())).thenReturn(LocalDateTime.of(2025, 11, 20, 12, 00));
		
		when(mockDry.getId()).thenReturn("asdf1234567");
		when(mockReefer.getId()).thenReturn("qwer9876543");
		when(mockTanque.getId()).thenReturn("zxcv1234567");
		
		
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
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockDry);
				return null;
			}
		}).when(mockDry).accept(any(Visitor.class));	
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockReefer);
				return null;
			}
		}).when(mockReefer).accept(any(Visitor.class));	
		
		
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocacion) {
				Visitor visitante = invocacion.getArgument(0);
				visitante.visit(mockTanque);
				return null;
			}
		}).when(mockTanque).accept(any(Visitor.class));	
	}
	
	
	
	@Test
	public void reporteAduanaTest() {
		mockTerminal.accept(reporte);
		
		String reporteEsperado = "<html><body>\n"
				+ "<h1>Buque: buque</h1>\n"
				+ "<p>Fecha de partida: 2025-11-07T12:00</p>\n"
				+ "<p>Fecha de arribo: 2025-11-20T12:00</p>\n"
				+ "<ul>\n"
				+ "<li>ID: qwer9876543 (Tipo: Reefer)</li>\n"
				+ "<li>ID: asdf1234567 (Tipo: Dry)</li>\n"
				+ "</ul><hr/>\n"
				+ "</body></html>";
		
		assertEquals(reporteEsperado, reporte.getReporte());
	}
}
