package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaDeConexion {
	
	public static void main(String[] args) throws SQLException {
		ConnectionFactory confa = new ConnectionFactory();
		Connection cone = confa.conectaFactory();
		
		System.out.println("Conexión abierta");
		
		cone.close();
		
		System.out.println("Conexión cerrada");
	}
}
