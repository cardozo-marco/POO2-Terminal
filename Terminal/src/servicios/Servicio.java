package servicios;

import java.util.*;

import ordenes.Orden;

public interface Servicio {
	Set<Compatibilidad> getCompatibilidades();
	
	public double calcularCosto(Orden orden);
}
