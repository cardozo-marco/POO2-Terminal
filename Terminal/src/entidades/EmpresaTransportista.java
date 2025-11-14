package entidades;

import java.util.ArrayList;
import java.util.List;

public class EmpresaTransportista {
	private List<Camion> camiones;
	private List<Conductor> conductores;
	
	public EmpresaTransportista() {
		this.camiones = new ArrayList<>();
		this.conductores = new ArrayList<>();
	}
	
	public void addCamion(Camion camion) {
		this.camiones.add(camion);
	}
	
	public void addConductor(Conductor conductor) {
		this.conductores.add(conductor);
	}
	
	public List<Camion> getCamiones() {
		return camiones;
	}
	
	public List<Conductor> getConductores() {
		return conductores;
	}
}

