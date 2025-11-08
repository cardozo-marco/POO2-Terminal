package busqueda;

import java.util.ArrayList;
import java.util.List;

import maritimo.Viaje;

public class FiltroOR implements FiltroDeBusqueda {
	private List<FiltroDeBusqueda> filtros;
	
	public FiltroOR(List<FiltroDeBusqueda> filtros) {
		this.filtros = new ArrayList<>(filtros);
	}
	
	public void addFiltro(FiltroDeBusqueda filtro) {
		this.filtros.add(filtro);
	}
	
	@Override
	public boolean cumple(Viaje viaje) {
		for (FiltroDeBusqueda filtro : filtros) {
			if (filtro.cumple(viaje)) {
				return true;
			}
		}
		return false;
	}
}

