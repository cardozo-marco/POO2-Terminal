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
	        if (!started) {// si todavía no se encontro la terminal de origen sigue buscando
	            if (tramo.getOrigen().equals(origen)) {
	                started = true;// empieza a contar
	                if (tramo.getDestino().equals(destino)) {
	                    break;// no sigue recorriendo si esta en el destino final
	                }
	                intermedias.add(tramo.getDestino());
	            }
	        } else {
	            if (tramo.getDestino().equals(destino)) {
	                break;// no sigue recorriendo si esta en el destino final
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

	public int cantidadTerminalesEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
		int contador = 0;
	    boolean started = false;

	    for (Tramo tramo : tramos) {
	        if (!started) {// si todavía no se encontro la terminal de origen sigue buscando
	            if (tramo.getOrigen().equals(origen)) {
	                started = true;// empieza a contar
	                // Si el destino del primer tramo es distinto del destino final se cuenta
	                if (!tramo.getDestino().equals(destino)) {
	                    contador++;
	                } else {
	                    break; // no sigue recorriendo si esta en el destino final
	                }
	            }
	        } else {
	            if (tramo.getDestino().equals(destino)) {
	                break; // no sigue recorriendo si esta en el destino final
	            } else {
	                contador++;
	            }
	        }
	    }

	    return contador;
	}

	public boolean contieneTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
		boolean started = false;
		for (Tramo tramo : tramos) {
		    if (!started) {// si todavía no se encontro la terminal de origen sigue buscando
		        if (tramo.getOrigen().equals(origen)) {
		            started = true;
		            if (tramo.getDestino().equals(destino)) {
		                return true;// si esta en el destino final, existe ambos terminales
		            }
		        }
		    } else {
		        if (tramo.getDestino().equals(destino)) {
		            return true;// si esta en el destino final, existe ambos terminales
		        }
		    }
		}
		return false;
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
	                } else {
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
	                } else {
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
