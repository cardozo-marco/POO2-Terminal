package maritimo;

public class Outbound implements FaseBuque {
	@Override
    public void actualizarPosicion(Buque buque, PosicionGPS nuevaPosicion) {
        buque.setPosicionActual(nuevaPosicion);
        double distancia = buque.getPosicionActual().distanciaHasta(
                               buque.getViajeAsignado().getTerminalDestino().getPosicion());

        if (distancia < 50) {
            buque.setFase(new Inbound());
        }
    }
    
    @Override
    public boolean esOutbound() {
    	return true;
    }
}
