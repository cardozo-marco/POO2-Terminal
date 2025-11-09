package paraPruebas;

import java.util.*;

import maritimo.Buque;
import reportes.*;

public class Naviera implements Visitable{
	List<Buque> buques = new ArrayList<>();
	
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}

	public List<Buque> getBuques() {
		return this.buques;
	}

}
