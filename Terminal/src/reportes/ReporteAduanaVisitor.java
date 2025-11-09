package reportes;

import java.util.*;

import carga.*;
import maritimo.Buque;
import maritimo.Naviera;
import maritimo.Viaje;
import ordenes.*;
import terminal.TerminalGestionada;

public class ReporteAduanaVisitor implements Visitor{
	private Map<Viaje, Buque> buquesPorViaje = new HashMap<>();
	
	private Map<Viaje, List<Container>> contenedoresPorViaje = new HashMap<>();
	
	private Viaje viajeActual;
	
	private TerminalGestionada terminal;
	
	private StringBuilder reporte = new StringBuilder("<html><body>\n");

	@Override
	public void visit(TerminalGestionada terminal) {
		for(Naviera naviera : terminal.getNavierasRegistradas()) {
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

	//guardo en viajeActual el viaje de la orden que estoy viendo,
	//para que cuando llame al container lo pueda utilizar
	//una vez que el container ya hizo lo que necesitaba, borro el viaje para que no haya errores en la proximas ordenes
	@Override
	public void visit(OrdenDeImportacion orden) {
		this.viajeActual = orden.getViaje();
		orden.getContainer().accept(this);
		this.viajeActual = null;
	}

	@Override
	public void visit(OrdenDeExportacion orden) {
		this.viajeActual = orden.getViaje();
		orden.getContainer().accept(this);
		this.viajeActual = null;
	}
	
	//computeIfAbsent primero comprueba si en el map existe una clave con ese viaje, 
	//                si existe agrega el contenedor a la lista asociada a esa clave
	//                si no existe, agrega al map ese viaje como clave y le asocia una nueva lista y luego agrega el contenedor
	@Override
	public void visit(Dry contenedor) {
		this.contenedoresPorViaje.computeIfAbsent(viajeActual, v -> new ArrayList<>()).add(contenedor);
	}

	@Override
	public void visit(Reefer contenedor) {
		this.contenedoresPorViaje.computeIfAbsent(viajeActual, v -> new ArrayList<>()).add(contenedor);
	}

	@Override
	public void visit(Tanque contenedor) {
		this.contenedoresPorViaje.computeIfAbsent(viajeActual, v -> new ArrayList<>()).add(contenedor);
	}

	public String getReporte() {
		for(Viaje viaje : this.buquesPorViaje.keySet()) {
			Buque buque = this.buquesPorViaje.get(viaje);
			this.reporte.append("<h1>Buque: ").append(buque.getNombre()).append("</h1>\n");
			this.reporte.append("<p>Fecha de partida: ").append(viaje.getFechaInicio()).append("</p>\n");
			this.reporte.append("<p>Fecha de arribo: ").append(viaje.getFechaLlegada(this.terminal.getTerminal())).append("</p>\n");
			this.reporte.append("<ul>\n");
			
			List<Container> contenedores = this.contenedoresPorViaje.get(viaje);
			if(contenedores != null) {
				for(Container container : contenedores) {
					this.reporte.append("<li>ID: ").append(container.getId())
								.append(" (Tipo: ").append(container.getClass().getSimpleName()).append(")</li>\n");
				}
			}
			this.reporte.append("</ul><hr/>\n");
		}
		this.reporte.append("</body></html>");
		
		return this.reporte.toString();
	}
	
}
