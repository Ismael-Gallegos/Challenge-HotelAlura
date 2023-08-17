package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaDeConexion {
	
	public static void main(String[] args) throws SQLException {
		// Se intenta abrir una conexión a la base de datos
        try (Connection cone = new ConnectionFactory().conectaFactory()) {
            System.out.println("Conexión abierta");
		cone.close();
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
    }
}