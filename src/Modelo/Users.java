package Modelo;

public class Users {
	
	private String Nombre;
	private String Contrasena;
	
	public Users(String Nombre, String Contrasena) {
		
		this.Nombre = Nombre;
		this.Contrasena = Contrasena;
	}
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}

	public String getContrasena() {
		return Contrasena;
	}

	public void setContrasena(String contrasena) {
		this.Contrasena = contrasena;
	}
}
