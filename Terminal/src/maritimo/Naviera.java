package maritimo;

import java.util.ArrayList;
import java.util.List;

import reportes.Visitable;
import reportes.Visitor;

public class Naviera implements Visitable {
	
	private List <CircuitoMaritimo> circuitos;
	private List <Buque> buques;
	
	public Naviera(){
		this.circuitos = new ArrayList<CircuitoMaritimo>();
		this.buques = new ArrayList<Buque>();
	}
	
	public void addBuque(Buque buque) {
		this.buques.add(buque);
	}
	
	public void addCircuito(CircuitoMaritimo circuito) {
		this.circuitos.add(circuito);
	}

	public List<Buque> getBuques() {
		return buques;
	}
	
	public List<CircuitoMaritimo> getCircuitos() {
		return circuitos;
	}
	
	@Override
	public void accept(Visitor visitante) {
		visitante.visit(this);
	}
	
}




