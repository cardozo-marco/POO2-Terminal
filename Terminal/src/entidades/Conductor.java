package entidades;

public class Conductor {
	private String nombre;
	private String dni;
	
	public Conductor(String nombre, String dni) {
		this.nombre = nombre;
		this.dni = dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDni() {
		return dni;
	}
}

