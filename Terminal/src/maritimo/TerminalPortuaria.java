package maritimo;

public class TerminalPortuaria {
	
	private PosicionGPS posicion;
	@SuppressWarnings("unused")
	private String nombre;

	public TerminalPortuaria(String nombre, PosicionGPS posicion) {
		this.posicion = posicion;
		this.nombre = nombre;
	}

	public PosicionGPS getPosicion() {
		return posicion;
	}	
	
}
