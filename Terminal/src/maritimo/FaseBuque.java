package maritimo;

public interface FaseBuque {
	default void actualizarPosicion(Buque buque,PosicionGPS nuevaPosicion) {};
	default void darOrdenDeTrabajo(Buque buque) {};
	default void depart(Buque buque) {};
}
