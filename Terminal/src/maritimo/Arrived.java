package maritimo;

public class Arrived implements FaseBuque {
	
	@Override
    public void darOrdenDeTrabajo(Buque buque) {
        buque.setFase(new Working());
    }
}
