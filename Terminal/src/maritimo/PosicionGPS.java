package maritimo;

public class PosicionGPS {
	
	
	private double longitud;
	private double latitud;

	public PosicionGPS(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public double distanciaHasta(PosicionGPS otra) {
        double kmPorGradoLat = 111.32; 
        double kmPorGradoLon = 40075 * Math.cos(Math.toRadians((this.latitud + otra.latitud) / 2)) / 360;

        double deltaLat = otra.getLatitud() - this.latitud;
        double deltaLon = otra.getLongitud() - this.longitud;

        return Math.sqrt(Math.pow(deltaLat * kmPorGradoLat, 2) +
                         Math.pow(deltaLon * kmPorGradoLon, 2));
	}
	
	public double getLatitud() {
		
		return(this.latitud);
	}
	
	public double getLongitud() {
		
		return(this.longitud);
	}
}
