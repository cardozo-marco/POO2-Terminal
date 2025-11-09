package reportes;

import java.util.*;

import carga.*;
import maritimo.Buque;
import maritimo.Naviera;
import maritimo.Viaje;
import ordenes.*;
import terminal.TerminalGestionada;

public class ReporteBuqueVisitor implements Visitor{
	private Map<Viaje, List<String>> idsCargados = new HashMap<>();
	
	private Map<Viaje, List<String>> idsDescargados = new HashMap<>();
	
	private StringBuilder reporte = new StringBuilder();

	@Override
	public void visit(TerminalGestionada terminal) {
		for(Orden orden : terminal.getOrdenes()) {
			orden.accept(this);
		}
	}

	@Override
	public void visit(Naviera naviera) {
		
	}

	@Override
	public void visit(Buque buque) {
		
	}

	@Override
	public void visit(OrdenDeImportacion orden) {
		Viaje viaje = orden.getViaje();
		String id = orden.getContainer().getId();
		this.idsDescargados.computeIfAbsent(viaje, v -> new ArrayList<>()).add(id);
	}

	@Override
	public void visit(OrdenDeExportacion orden) {
		Viaje viaje = orden.getViaje();
		String id = orden.getContainer().getId();
		this.idsCargados.computeIfAbsent(viaje, v -> new ArrayList<>()).add(id);
	}

	@Override
	public void visit(Dry contenedor) {
		
	}

	@Override
	public void visit(Reefer contenedor) {
		
	}

	@Override
	public void visit(Tanque contenedor) {
		
	}

	public String getReporte() {
		this.reporte.append("<report>\n");
		
		this.reporte.append("\t<import>\n");
		
		for(List<String> listaDeIds : this.idsDescargados.values()) {
			for(String id : listaDeIds) {
				this.reporte.append("\t\t<item>").append(id).append("</item>\n");
			}
		}
		
		this.reporte.append("\t</import>\n");
		
		this.reporte.append("\t<export>\n");
		
		for(List<String> listaDeIds : this.idsCargados.values()) {
			for(String id : listaDeIds) {
				this.reporte.append("\t\t<item>").append(id).append("</item>\n");
			}
		}
		
		this.reporte.append("\t</export>\n");

		this.reporte.append("</report>\n");
		
		return this.reporte.toString();
	}
}
