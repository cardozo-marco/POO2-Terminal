package ordenes;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import actores.Camion;
import actores.Cliente;
import actores.Conductor;
import carga.*;
import maritimo.TerminalPortuaria;
import maritimo.Viaje;
import reportes.*;
import servicios.Servicio;

public abstract class Orden implements Visitable{
	protected Cliente cliente;
	
	protected Container carga;
	
	protected Viaje viaje;
	
	protected Camion camion;
	
	protected Conductor conductor;
	
	protected TerminalPortuaria terminal;
	
	protected LocalDate fecha;
	
	protected List<Servicio> serviciosContratados = new ArrayList<>();
	
	public void agregarServicio(Servicio servicio) {
		this.serviciosContratados.add(servicio);
	}
	
	public double calcularCostoTotalServicios() {
		double costoTotal = 0;
		
		for(Servicio servicio : this.serviciosContratados) {
			costoTotal = costoTotal + servicio.calcularCosto(this);
		}
		return costoTotal;
	}

	public Orden(Cliente cliente, Container carga, Viaje viaje, Camion camion, Conductor conductor,
			TerminalPortuaria terminal) {
		this.cliente = cliente;
		this.carga = carga;
		this.viaje = viaje;
		this.camion = camion;
		this.conductor = conductor;
		this.terminal = terminal;
		this.fecha = LocalDate.now();
	}
	
	
	//solo para probar los test
	public List<Servicio> serviciosContratados(){
		return this.serviciosContratados;
	}

	public abstract double cantidadDeDias();
	
	public double getVolumen() {
		return this.carga.getVolumen();
	}
	
	public Container getContainer() {
		return this.carga;
	}
	
	public Viaje getViaje() {
		return this.viaje;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public boolean esOrdenDeImportacion() {
		return false;
	}
	
	public boolean esOrdenDeExportacion() {
		return false;
	}
	
	public Camion getCamion() {
		return this.camion;
	}
	
	public Conductor getConductor() {
		return this.conductor;
	}
	
}
