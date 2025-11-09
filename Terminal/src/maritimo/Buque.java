package maritimo;

import java.util.ArrayList;
import java.util.List;

import reportes.Visitable;
import reportes.Visitor;

public class Buque  implements Visitable{
	private FaseBuque faseActual;
	private PosicionGPS posicionActual;
	private Viaje viajeAsignado;
	private List<BuqueObserver> observers = new ArrayList<>();

	
	Buque(Viaje viaje, PosicionGPS posicionInicial){
		this.posicionActual= posicionInicial;
		this.faseActual = new Outbound();
		this.viajeAsignado = viaje;
	}
	
	public void addObserver(BuqueObserver obs){
		this.getObservers().add(obs);
	}
	
	public void removeObserver(BuqueObserver obs) {
		this.getObservers().remove(obs);
	}
	
	public void setFase(FaseBuque nuevaFase) {
		this.faseActual = nuevaFase;
		notifyObservers();
	}
	
	public void notifyObservers() {
		for (BuqueObserver obs : getObservers()) {
            obs.update(this);
        }
	}
	
	public void actualizarPosicion(PosicionGPS nuevaPosicion) {
		this.getFaseActual().actualizarPosicion(this, nuevaPosicion);
	}
	
	public void depart() {
		this.getFaseActual().depart(this);
	}
	
	public void darOrdenDeTrabajo() {
		this.getFaseActual().darOrdenDeTrabajo(this);
	}
	//TODO
//	public void accept(Visitor visitor) {
//		visitor.visitBuque(this);
//	}

	public Viaje getViajeAsignado() {
		return viajeAsignado;
	}

	public PosicionGPS getPosicionActual() {
		return posicionActual;
	}
	
	public void setPosicionActual(PosicionGPS posicionActual) {
		this.posicionActual = posicionActual;
	}

	public FaseBuque getFaseActual() {
		return faseActual;
	}

	public List<BuqueObserver> getObservers() {
		return observers;
	}

	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}


}
