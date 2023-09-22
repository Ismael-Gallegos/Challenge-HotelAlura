package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Modelo.RegHuespedes;
import Modelo.Reservaciones;

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
	
	public List<RegHuespedes> mostrar() {
		List<RegHuespedes> huespedes = new ArrayList<RegHuespedes>();
		try {
			// Consulta SQL para seleccionar campos de la tabla Huespedes
			String sql = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, "
					+ "telefono, idReserva FROM huespedes";
			try(PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();
				
				 // Llamada al método para transformar el resultado y llenar la lista de reservaciones
                transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException("Verifica Metodo MostrarHuesp. HuespDAO" + e.getMessage(),e);
		}
	}
	
	public List<RegHuespedes> buscarId(String id) {
		List<RegHuespedes> huespedes = new ArrayList<RegHuespedes>();
		try {
			// Consulta SQL para seleccionar campos de la tabla Huespedes
			String sql = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, "
					+ "telefono, idReserva FROM huespedes WHERE id=?";
			try(PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();
				
				 // Llamada al método para transformar el resultado y llenar la lista de reservaciones
                transformarResultado(huespedes, pstm);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException("Verifica Metodo BuscarId. HuespDAO" + e.getMessage(),e);
		}
	}
	
	public void ActualizarHuesp(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		try(PreparedStatement stm = con.prepareStatement(""
				+ "UPDATE huespedes SET nombre=?, apellido=?, fechaNacimiento=?, "
				+ "nacionalidad=?, telefono=?, idReserva=? WHERE id=?")) {
			stm.setString(1, nombre);
			stm.setString(2, apellido);
			stm.setObject(3, fechaNacimiento);
			stm.setString(4, nacionalidad);
			stm.setString(5, telefono);
			stm.setInt(6, idReserva);
			stm.setInt(7, id);
			stm.execute();
			
		}catch (SQLException e) {
			throw new RuntimeException("Verifica Metodo Actualizar Huesp HuespDAO" + e.getMessage(),e);
		}
	}
	
	public void Eliminar(Integer id) {
		try(PreparedStatement stm = con.prepareStatement("DELETE FROM huespedes WHERE id=?")) {
			stm.setInt(1, id);
			stm.execute();
		}catch (SQLException e) {
			throw new RuntimeException("Verifica Metodo Elininar Huesp HuespDAO" + e.getMessage(),e);
		}
	}
	
	private void transformarResultado(List<RegHuespedes> huespedes, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.executeQuery()) {
			while(rst.next()) {
				int id = rst.getInt("id");
				String nombre = rst.getString("nombre");
				String apellido = rst.getString("apellido");				
				LocalDate fechaNacimiento = rst.getDate("fechaNacimiento").toLocalDate().plusDays(1);
				String nacionalidad = rst.getString("nacionalidad");	
				String telefono = rst.getString("telefono");
				Integer idReserva = rst.getInt("idReserva");
				
				RegHuespedes huesped = new RegHuespedes(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes.add(huesped);
			}
		}
	}
	
	
}
