package maritimo;

import java.util.ArrayList;
import java.util.List;

public class CircuitoMaritimo {
	private List<Tramo> tramos;
	
	public CircuitoMaritimo(List<Tramo> tramos) {
		this.tramos = tramos;
	}
	
	public double getTiempoTotal() {
		return this.tramos.stream().mapToDouble(tramo -> tramo.getTiempo()).sum();
	}
	
	public double getPrecioTotal() {
		return this.tramos.stream().mapToDouble(tramo -> tramo.getPrecio()).sum();
	}
	
	public List<TerminalPortuaria> getTerminalesIntermedias(TerminalPortuaria origen, TerminalPortuaria destino){
		List<TerminalPortuaria> intermedias = new ArrayList<>();
	    boolean started = false;

	    for (Tramo tramo : tramos) {
	        if (!started) {
	            if (tramo.getOrigen().equals(origen)) {
	                started = true;
	                if (tramo.getDestino().equals(destino)) {
	                    break;
	                }
	                intermedias.add(tramo.getDestino());
	            }
	        } else {
	            if (tramo.getDestino().equals(destino)) {
	                break;
	            } else {
	                intermedias.add(tramo.getDestino());
	            }
	        }
	    }
	    return intermedias;
	}
	
	public List<Tramo> getTramos() {
		return tramos;
	}
}
