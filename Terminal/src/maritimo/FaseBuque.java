package maritimo;

public interface FaseBuque {
	default void actualizarPosicion(Buque buque,PosicionGPS nuevaPosicion) {};
	default void darOrdenDeTrabajo(Buque buque) {};
	default void depart(Buque buque) {};
	default boolean haLlegado() {
		return false;
	}
	default boolean esInbound() {
		return false;
	}
	default boolean esDeparting() {
		return false;
	}
	default boolean esOutbound() {
		return false;
	}
}
