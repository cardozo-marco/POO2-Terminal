package maritimo;

public class Tramo {

	private double tiempo;
	private double precio;
	private TerminalPortuaria origen;
	private TerminalPortuaria destino;

	public Tramo(TerminalPortuaria origen, TerminalPortuaria destino, double tiempo, double precio) {
		this.tiempo = tiempo;
		this.precio = precio;
		this.origen = origen;
		this.destino = destino;
	}
	
	public double getTiempo() {
		return(this.tiempo);
	}

	public double getPrecio() {
		
		return(this.precio);
	}

	public TerminalPortuaria getDestino() {
		return this.destino;
	}

	public TerminalPortuaria getOrigen() {
		return (this.origen);
	}
}
