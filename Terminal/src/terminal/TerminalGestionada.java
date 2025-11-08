package terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import actores.Camion;
import actores.Cliente;
import actores.Conductor;
import actores.EmpresaTransportista;
import actores.Shipper;
import busqueda.CriterioDeBusqueda;
import busqueda.FiltroDeBusqueda;
import carga.Container;
import maritimo.Buque;
import maritimo.BuqueObserver;
import maritimo.CircuitoMaritimo;
import maritimo.Naviera;
import maritimo.Terminal;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;
import ordenes.Orden;
import ordenes.OrdenDeExportacion;

public class TerminalGestionada implements BuqueObserver {
	private CriterioDeBusqueda criterioBusqueda;
	private TerminalPortuaria miTerminal;
	private List<Naviera> navierasRegistradas;
	private List<Cliente> clientesRegistrados;
	private List<EmpresaTransportista> transportistasRegistrados;
	private List<Orden> ordenes;
	
	public TerminalGestionada(TerminalPortuaria miTerminal, CriterioDeBusqueda criterioInicial) {
		this.miTerminal = miTerminal;
		this.criterioBusqueda = criterioInicial;
		this.navierasRegistradas = new ArrayList<>();
		this.clientesRegistrados = new ArrayList<>();
		this.transportistasRegistrados = new ArrayList<>();
		this.ordenes = new ArrayList<>();
	}
	
	public void registrarNaviera(Naviera naviera) {
		this.navierasRegistradas.add(naviera);
	}
	
	public void registrarCliente(Cliente cliente) {
		this.clientesRegistrados.add(cliente);
	}
	
	public void registrarTransportista(EmpresaTransportista empresa) {
		this.transportistasRegistrados.add(empresa);
	}
	
	public OrdenDeExportacion crearOrdenExportacion(Shipper shipper, Viaje viaje, Container carga, 
			Camion camion, Conductor conductor) {
		OrdenDeExportacion orden = new OrdenDeExportacion(shipper, carga, viaje, camion, conductor, miTerminal);
		this.ordenes.add(orden);
		return orden;
	}
	
	public void recibirCamionExportacion(Camion camion, Conductor conductor) {
		// Validar que el camion y conductor estén registrados
		boolean camionRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getCamiones().contains(camion));
		boolean conductorRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getConductores().contains(conductor));
		
		if (!camionRegistrado || !conductorRegistrado) {
			throw new RuntimeException("Camion o conductor no registrado");
		}
		// Lógica adicional para recibir el camión de exportación
	}
	
	public void recibirCamionImportacion(Camion camion, Conductor conductor, Orden orden) {
		// Validar que el camion y conductor estén registrados
		boolean camionRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getCamiones().contains(camion));
		boolean conductorRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getConductores().contains(conductor));
		
		if (!camionRegistrado || !conductorRegistrado) {
			throw new RuntimeException("Camion o conductor no registrado");
		}
		// Lógica adicional para recibir el camión de importación
	}
	
	public List<Viaje> buscarViajes(FiltroDeBusqueda filtro) {
		List<Viaje> todosLosViajes = new ArrayList<>();
		
		// Obtener todos los viajes de todas las navieras registradas
		for (Naviera naviera : navierasRegistradas) {
			todosLosViajes.addAll(naviera.getViajes());
		}
		
		// Filtrar según el criterio
		return todosLosViajes.stream()
			.filter(viaje -> filtro.cumple(viaje))
			.collect(Collectors.toList());
	}
	
	public CircuitoMaritimo mejorCircuitoHacia(Terminal destino) {
		List<CircuitoMaritimo> todosLosCircuitos = new ArrayList<>();
		
		// Obtener todos los circuitos de todas las navieras registradas
		for (Naviera naviera : navierasRegistradas) {
			todosLosCircuitos.addAll(naviera.getCircuitos());
		}
		
		// Usar el criterio de búsqueda para encontrar el mejor circuito
		return criterioBusqueda.buscarCircuitos(todosLosCircuitos, miTerminal, destino);
	}
	
	public void setCriterioDeBusqueda(CriterioDeBusqueda criterio) {
		this.criterioBusqueda = criterio;
	}
	
	public void update(Buque buque) {
		// Implementación del patrón Observer
		// Se notifica cuando un buque llega o tiene algún cambio
	}
	
	public TerminalPortuaria getTerminal() {
		return miTerminal;
	}
}

