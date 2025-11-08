package maritimo;

public class Departing implements FaseBuque {

	@Override
	public void actualizarPosicion(Buque buque, PosicionGPS nuevaPosicion) {
		buque.setPosicionActual(nuevaPosicion);
        double distancia = buque.getPosicionActual().distanciaHasta(buque.getViajeAsignado().getTerminalOrigen().getPosicion());
        if (distancia > 1) {
        buque.setFase(new Outbound());
        }
	}
}
