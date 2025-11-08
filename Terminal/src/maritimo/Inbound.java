package maritimo;

public class Inbound implements FaseBuque {
	@Override
    public void actualizarPosicion(Buque buque, PosicionGPS nuevaPosicion) {
        buque.setPosicionActual(nuevaPosicion);
        double distancia = buque.getPosicionActual().distanciaHasta(buque.getViajeAsignado().getTerminalDestino().getPosicion());
        if (distancia == 0) {
            buque.setFase(new Arrived());
        }
    }

}
