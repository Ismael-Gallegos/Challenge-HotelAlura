package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modelo.RegHuespedes;

public class HuespedesDao {
	
	private Connection con;

	public HuespedesDao(Connection con) {
		super();
		this.con = con;
	}
	
	// Método para guardar los datos de los Huespedes en la base de datos	
	public void guardar(RegHuespedes regHuespedes) {
		
		try {
			// Definir la consulta SQL para insertar datos de huéspedes en la tabla "Huespedes"
			String sql = "INSERT INTO Huespedes (nombre, apellido, fechaNacimiento, nacionalidad"
					+ ",telefono, idReserva)"
					+ "VALUES (?,?,?,?,?,?)";
			
			// Preparar el PreparedStatement con la consulta SQL y habilitar la recuperación de claves generadas
			try(PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
				// Establecer los valores de los parámetros en el PreparedStatement
				pstm.setString(1, regHuespedes.getNombre());
				pstm.setString(2, regHuespedes.getApellido());
				pstm.setObject(3, regHuespedes.getFechaNacimiento());
				pstm.setString(4, regHuespedes.getNacionalidad());
				pstm.setString(5, regHuespedes.getTelefono());
				pstm.setInt(6, regHuespedes.getIdReserva());
				
				// Ejecutar la consulta de inserción en la base de datos
				pstm.execute();
				
				// Obtener el ID generado y asignarlo al objeto RegHuespedes
				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						regHuespedes.setId(rst.getInt(1));
					}
				}				
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
