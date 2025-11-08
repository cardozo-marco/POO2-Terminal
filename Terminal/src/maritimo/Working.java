package maritimo;

public class Working implements FaseBuque {
	
	@Override
    public void depart(Buque buque) {
        buque.setFase(new Departing());
    }
}
