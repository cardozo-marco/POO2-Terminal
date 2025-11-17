package maritimo;

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
		return tramos.stream()
		        .dropWhile(t -> !t.getOrigen().equals(origen))
		        .takeWhile(t -> !t.getDestino().equals(destino))
		        .map(Tramo::getDestino)
		        .toList();
	}
	
	public List<Tramo> getTramos() {
		return tramos;
	}

	public int cantidadTerminalesEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
		return (int )tramos.stream()
		        .dropWhile(t -> !t.getOrigen().equals(origen))
		        .takeWhile(t -> !t.getDestino().equals(destino))
		        .count();
	}

	public boolean contieneTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
		return tramos.stream()
		        .dropWhile(t -> !t.getOrigen().equals(origen))
		        .anyMatch(t -> t.getDestino().equals(destino));
	}

	public double precioEntreTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
		double precioTotal = 0.0;
	    boolean started = false;

	    for (Tramo tramo : tramos) {
	        if (!started) {
	            if (tramo.getOrigen().equals(origen)) {
	                started = true;
	                if (tramo.getDestino().equals(destino)) {// si el destino del primer tramo ya es el destino final, sumamos y salimos
	                    precioTotal += tramo.getPrecio();
	                    break;
	                } else {//sino suma el precio del tramo
	                    precioTotal += tramo.getPrecio();
	                }
	            }
	        } else {// ya se encontro el origen,entonces se suma los precios de los tramos hasta el destino
	            precioTotal += tramo.getPrecio();
	            if (tramo.getDestino().equals(destino)) {
	                break;
	            }
	        }
	    }

	    return precioTotal;
	}

	public double tiempoEntreTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
		double tiempoTotal = 0.0;
	    boolean started = false;

	    for (Tramo tramo : tramos) {
	        if (!started) {
	            if (tramo.getOrigen().equals(origen)) {
	                started = true;
	                if (tramo.getDestino().equals(destino)) {// si el destino del primer tramo ya es el destino final, sumamos y salimos
	                	tiempoTotal += tramo.getTiempo();
	                    break;
	                } else {//sino suma el tiempo del tramo
	                	tiempoTotal += tramo.getTiempo();
	                }
	            }
	        } else {// ya se encontro el origen,entonces se suma los precios de los tramos hasta el destino
	        	tiempoTotal += tramo.getTiempo();
	            if (tramo.getDestino().equals(destino)) {
	                break;
	            }
	        }
	    }

	    return tiempoTotal;
	}
}
