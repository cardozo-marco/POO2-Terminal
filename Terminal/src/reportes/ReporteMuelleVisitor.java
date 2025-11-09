package reportes;

import java.util.*;

import carga.*;
import maritimo.Buque;
import ordenes.*;
import paraPruebas.*;

public class ReporteMuelleVisitor implements Visitor{
	private Map<Viaje, Buque> buquesPorViaje = new HashMap<>();
	
	private Map<Viaje, Integer> cantidadDeContenedores = new HashMap<>();
	
	private StringBuilder reporte = new StringBuilder();
	
	private TerminalGestionada terminal;

	@Override
	public void visit(TerminalGestionada terminal) {
		for(Naviera naviera : terminal.getNavieras()) {
			naviera.accept(this);
		}
		
		for(Orden orden : terminal.getOrdenes()) {
			orden.accept(this);
		}
		
		this.terminal = terminal;
	}
	
	@Override
	public void visit(Naviera naviera) {
		for(Buque buque : naviera.getBuques()) {
			buque.accept(this);
		}
	}

	@Override
	public void visit(Buque buque) {
		this.buquesPorViaje.put(buque.getViajeAsignado(), buque);
	}

	@Override
	public void visit(OrdenDeImportacion orden) {
		Viaje viaje = orden.getViaje();
		int cantidad = this.cantidadDeContenedores.getOrDefault(viaje, 0);
		this.cantidadDeContenedores.put(viaje, cantidad + 1);
	}

	@Override
	public void visit(OrdenDeExportacion orden) {
		Viaje viaje = orden.getViaje();
		int cantidad = this.cantidadDeContenedores.getOrDefault(viaje, 0);
		this.cantidadDeContenedores.put(viaje, cantidad + 1);
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
		for(Viaje viaje : this.buquesPorViaje.keySet()) {
			Buque buque = this.buquesPorViaje.get(viaje);
			int cantidad = this.cantidadDeContenedores.getOrDefault(viaje, 0);
			this.reporte.append("Buque: ").append(buque.getNombre()).append("\n");
			this.reporte.append("Fecha de partida: ").append(viaje.getFechaInicio()).append("\n");
			this.reporte.append("Fecha de llegada: ").append(viaje.getFechaLlegadaA(this.terminal)).append("\n");
			this.reporte.append("Contenedores operados: ").append(cantidad).append("\n");
		}
		return reporte.toString();
	}	
}
