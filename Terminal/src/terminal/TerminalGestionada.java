package terminal;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import actores.Camion;
import actores.Cliente;
import actores.Conductor;
import actores.EmpresaTransportista;
import actores.Consignee;
import actores.Shipper;
import busqueda.CriterioDeBusqueda;
import busqueda.FiltroDeBusqueda;
import carga.Container;
import facturacion.Factura;
import maritimo.Buque;
import maritimo.BuqueObserver;
import maritimo.CircuitoMaritimo;
import maritimo.Naviera;
import maritimo.TerminalPortuaria;
import maritimo.Tramo;
import maritimo.Viaje;
import ordenes.Orden;
import ordenes.OrdenDeExportacion;
import ordenes.OrdenDeImportacion;
import reportes.Visitable;
import reportes.Visitor;
import servicios.Servicio;
import servicios.ServicioAlmacenamientoExcedente;

public class TerminalGestionada implements BuqueObserver, Visitable {
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
		// Registrar esta terminal como observadora de los buques de la naviera
		// que tienen viajes que pasan por esta terminal
		for (Buque buque : naviera.getBuques()) {
			Viaje viaje = buque.getViajeAsignado();
			if (viaje != null && viajeInvolucraEstaTerminal(viaje)) {
				buque.addObserver(this);
			}
		}
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
	
	public OrdenDeImportacion crearOrdenImportacion(Consignee consignee, Viaje viaje, Container carga, 
			Camion camion, Conductor conductor) {
		OrdenDeImportacion orden = new OrdenDeImportacion(consignee, carga, viaje, camion, conductor, miTerminal);
		this.ordenes.add(orden);
		
		// Según la documentación: enviar mail al consignee con fecha y hora de llegada
		LocalDateTime fechaLlegada = orden.getFechaLlegadaCarga();
		String mensaje = String.format("Su carga llegará a la terminal %s el %s a las %s.", 
			miTerminal.toString(), 
			fechaLlegada.toLocalDate().toString(),
			fechaLlegada.toLocalTime().toString());
		consignee.notificarLlegadaBuque(mensaje);
		
		return orden;
	}
	
	public void recibirCamionExportacion(Camion camion, Conductor conductor, OrdenDeExportacion orden) {
		// Validar que el camion y conductor estén registrados
		boolean camionRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getCamiones().contains(camion));
		boolean conductorRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getConductores().contains(conductor));
		
		if (!camionRegistrado || !conductorRegistrado) {
			throw new RuntimeException("Camion o conductor no registrado");
		}
		
		// Según la documentación: verificar que camión y chofer sean los informados por el shipper
		if (!orden.getCamion().equals(camion) || !orden.getConductor().equals(conductor)) {
			throw new RuntimeException("El camion o conductor no coinciden con los informados en la orden");
		}
		
		// Según la documentación: se registra la carga en el puerto
		// La carga queda almacenada a la espera de la llegada del buque
	}
	
	public void recibirCamionImportacion(Camion camion, Conductor conductor, OrdenDeImportacion orden) {
		// Validar que el camion y conductor estén registrados
		boolean camionRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getCamiones().contains(camion));
		boolean conductorRegistrado = transportistasRegistrados.stream()
			.anyMatch(empresa -> empresa.getConductores().contains(conductor));
		
		if (!camionRegistrado || !conductorRegistrado) {
			throw new RuntimeException("Camion o conductor no registrado");
		}
		
		// Según la documentación: verificar que camión y chofer sean los autorizados por el consignee
		if (!orden.getCamion().equals(camion) || !orden.getConductor().equals(conductor)) {
			throw new RuntimeException("El camion o conductor no coinciden con los autorizados en la orden");
		}
		
		// Según la documentación: verificar margen de tolerancia de 24 horas
		LocalDateTime fechaLlegada = orden.getFechaLlegadaCarga();
		LocalDateTime ahora = LocalDateTime.now();
		long horasDiferencia = java.time.Duration.between(fechaLlegada, ahora).toHours();
		
		// Según la documentación: cuando se demoran más de 24 horas, se cobra almacenamiento excedente
		if (horasDiferencia > 24) {
			// Agregar servicio de almacenamiento excedente a la orden
			// El costo por día debe ser configurado (por ahora usamos un valor por defecto)
			// En una implementación real, esto vendría de configuración de la terminal
			double costoPorDia = 100.0; // Valor por defecto, debería ser configurable
			ServicioAlmacenamientoExcedente servicioAlmacenamiento = 
				new ServicioAlmacenamientoExcedente(costoPorDia);
			
			// El servicio calculará automáticamente los días excedentes basándose en la fecha de llegada
			orden.agregarServicio(servicioAlmacenamiento);
		}
		
		// Según la documentación: se registra el retiro de la carga
		// El camión se lleva la carga y la terminal registra el retiro
	}
	
	public List<Viaje> buscarViajes(FiltroDeBusqueda filtro) {
		List<Viaje> todosLosViajes = new ArrayList<>();
		
		// Obtener todos los viajes de todas las navieras registradas
		// Los viajes se obtienen de los buques de cada naviera
		for (Naviera naviera : navierasRegistradas) {
			for (Buque buque : naviera.getBuques()) {
				Viaje viaje = buque.getViajeAsignado();
				if (viaje != null) {
					todosLosViajes.add(viaje);
				}
			}
		}
		
		// Filtrar según el criterio
		return todosLosViajes.stream()
			.filter(viaje -> filtro.cumple(viaje))
			.collect(Collectors.toList());
	}
	
	public CircuitoMaritimo mejorCircuitoHacia(TerminalPortuaria destino) {
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
		// Se notifica cuando un buque cambia de fase
		Viaje viaje = buque.getViajeAsignado();
		
		if (viaje == null) {
			return;
		}
		
		// Según la documentación:
		// - Fase Inbound: notificar a consignees que su carga está llegando
		// - Fase Departing: notificar a shippers que su carga ya ha salido
		// - Fase Outbound (después de Departing): enviar facturas
		
		// Si el buque entró en fase Inbound y está llegando a esta terminal como destino
		TerminalPortuaria destino = viaje.getTerminalDestino();
		if (buque.getFaseActual().esInbound() && destino != null && destino.equals(miTerminal)) {
			// Notificar a los consignees (importadores) que su carga está llegando
			notificarConsigneesLlegada(viaje);
		}
		
		// Si el buque entró en fase Departing y salió desde esta terminal como origen
		TerminalPortuaria origen = viaje.getTerminalOrigen();
		if (buque.getFaseActual().esDeparting() && origen != null && origen.equals(miTerminal)) {
			// Notificar a los shippers (exportadores) que su carga ya ha salido
			notificarShippersSalida(viaje);
		}
		
		// Si el buque pasó a Outbound después de Departing, enviar facturas
		// (cuando el buque pasa a Outbound por haberse retirado)
		// Solo facturar si el buque está en Outbound y a más de 1km de la terminal
		// (esto indica que viene de Departing, no es el Outbound inicial)
		if (buque.getFaseActual().esOutbound()) {
			double distancia = buque.getPosicionActual().distanciaHasta(miTerminal.getPosicion());
			// Si está a más de 1km, significa que viene de Departing (se retiró de la terminal)
			if (distancia > 1.0) {
				// Verificar si salió desde esta terminal (exportación) o llegó a esta terminal (importación)
				if (origen != null && origen.equals(miTerminal)) {
					// Exportación: facturar servicios al shipper
					facturarOrdenesExportacion(viaje);
				}
				if (destino != null && destino.equals(miTerminal)) {
					// Importación: facturar servicios + viaje al consignee
					facturarOrdenesImportacion(viaje);
				}
			}
		}
	}
	
	/**
	 * Notifica a los consignees (importadores) que su carga está llegando
	 * (cuando el buque entra en fase Inbound)
	 */
	private void notificarConsigneesLlegada(Viaje viaje) {
		for (Orden orden : ordenes) {
			if (orden.esOrdenDeImportacion() && orden.getViaje().equals(viaje)) {
				Consignee consignee = (Consignee) orden.getCliente();
				String mensaje = String.format("Su carga está llegando a la terminal %s. El buque se encuentra a menos de 50 km.", 
					miTerminal.toString());
				consignee.notificarLlegadaBuque(mensaje);
			}
		}
	}
	
	/**
	 * Notifica a los shippers (exportadores) que su carga ya ha salido de la terminal
	 * (cuando el buque entra en fase Departing)
	 */
	private void notificarShippersSalida(Viaje viaje) {
		for (Orden orden : ordenes) {
			if (orden.esOrdenDeExportacion() && orden.getViaje().equals(viaje)) {
				Shipper shipper = (Shipper) orden.getCliente();
				String mensaje = String.format("Su carga ya ha salido de la terminal %s en el buque.", 
					miTerminal.toString());
				shipper.notificarLlegadaBuque(mensaje);
			}
		}
	}
	
	public TerminalPortuaria getTerminal() {
		return miTerminal;
	}
	
	/**
	 * Factura las órdenes de exportación cuando el buque pasa a Outbound
	 * Factura servicios al shipper
	 */
	private void facturarOrdenesExportacion(Viaje viaje) {
		for (Orden orden : ordenes) {
			if (orden.esOrdenDeExportacion() && orden.getViaje().equals(viaje)) {
				Shipper shipper = (Shipper) orden.getCliente();
				Factura factura = generarFacturaServicios(orden);
				shipper.enviarFactura(factura);
			}
		}
	}
	
	/**
	 * Factura las órdenes de importación cuando el buque pasa a Outbound
	 * Factura servicios + costo del viaje al consignee
	 */
	private void facturarOrdenesImportacion(Viaje viaje) {
		for (Orden orden : ordenes) {
			if (orden.esOrdenDeImportacion() && orden.getViaje().equals(viaje)) {
				Consignee consignee = (Consignee) orden.getCliente();
				Factura factura = generarFacturaServicios(orden);
				
				// Agregar facturación del viaje (suma de precios de tramos)
				double precioViaje = calcularPrecioViaje(viaje);
				factura.agregarItem("Costo del viaje", precioViaje);
				
				consignee.enviarFactura(factura);
			}
		}
	}
	
	/**
	 * Genera una factura con los servicios contratados en la orden
	 */
	private Factura generarFacturaServicios(Orden orden) {
		Factura factura = new Factura(orden.getCliente());
		
		// Agregar cada servicio como item de factura
		for (Servicio servicio : orden.serviciosContratados()) {
			double costo = servicio.calcularCosto(orden);
			factura.agregarItem(servicio.getClass().getSimpleName(), costo);
		}
		
		return factura;
	}
	
	/**
	 * Calcula el precio del viaje sumando los precios de los tramos desde origen hasta destino
	 */
	private double calcularPrecioViaje(Viaje viaje) {
		CircuitoMaritimo circuito = viaje.getCircuito();
		TerminalPortuaria origen = viaje.getTerminalOrigen();
		TerminalPortuaria destino = viaje.getTerminalDestino();
		
		if (origen == null || destino == null || circuito == null) {
			return 0.0;
		}
		
		double precioTotal = 0.0;
		boolean empezarSumar = false;
		
		// Sumar precios de tramos desde origen hasta destino
		for (Tramo tramo : circuito.getTramos()) {
			if (tramo.getOrigen().equals(origen)) {
				empezarSumar = true;
			}
			if (empezarSumar) {
				precioTotal += tramo.getPrecio();
				if (tramo.getDestino().equals(destino)) {
					break;
				}
			}
		}
		
		return precioTotal;
	}
	
	/**
	 * Verifica si un viaje involucra a esta terminal (como origen o destino)
	 */
	private boolean viajeInvolucraEstaTerminal(Viaje viaje) {
		if (viaje == null) {
			return false;
		}
		TerminalPortuaria origen = viaje.getTerminalOrigen();
		TerminalPortuaria destino = viaje.getTerminalDestino();
		if (origen == null || destino == null) {
			return false;
		}
		return origen.equals(miTerminal) || destino.equals(miTerminal);
	}

	public List<Naviera> getNavierasRegistradas() {
		return navierasRegistradas;
	}
	
	public List<Orden> getOrdenes() {
		return ordenes;
	}
	
	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
}

