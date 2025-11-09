package reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import carga.*;
import maritimo.Viaje;
import ordenes.*;
import terminal.TerminalGestionada;

public class ReporteBuqueVisitorTest {
	public ReporteBuqueVisitor reporte;
	
	public TerminalGestionada mockTerminal;
	public OrdenDeImportacion mockOrdenDeImportacion;
	public OrdenDeExportacion mockOrdenDeExportacion;
	
	public Container mockContainerImportacion;
	public Container mockContainerExportacion;
	
	public Viaje mockViaje;
	
	List<Orden> ordenes;
	
	@BeforeEach
	public void setUp() {
		reporte = new ReporteBuqueVisitor();
		
		mockTerminal = mock(TerminalGestionada.class);
		mockOrdenDeImportacion = mock(OrdenDeImportacion.class);
		mockOrdenDeExportacion = mock(OrdenDeExportacion.class);
		
		mockContainerImportacion = mock(Container.class);
		mockContainerExportacion = mock(Container.class);
		
		mockViaje = mock(Viaje.class);
		
		ordenes = new ArrayList<>();
		
		ordenes.add(mockOrdenDeImportacion);
		ordenes.add(mockOrdenDeExportacion);
		
		when(mockTerminal.getOrdenes()).thenReturn(ordenes);
		
		when(mockOrdenDeImportacion.getViaje()).thenReturn(mockViaje);
		when(mockOrdenDeImportacion.getContainer()).thenReturn(mockContainerImportacion);
		
		when(mockOrdenDeExportacion.getViaje()).thenReturn(mockViaje);
		when(mockOrdenDeExportacion.getContainer()).thenReturn(mockContainerExportacion);
		
		when(mockContainerImportacion.getId()).thenReturn("asdf1234567");
		
		when(mockContainerExportacion.getId()).thenReturn("qwer9876543");
		
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
	public void reporteBuqueTest() {
		mockTerminal.accept(reporte);
		
		String reporteEsperado = "<report>\n"
				+ "\t<import>\n"
				+ "\t\t<item>asdf1234567</item>\n"
				+ "\t</import>\n"
				+ "\t<export>\n"
				+ "\t\t<item>qwer9876543</item>\n"
				+ "\t</export>\n"
				+ "</report>\n";
		
		assertEquals(reporteEsperado, reporte.getReporte());
	}
}
