package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Modelo.Reservaciones;

public class ReservacionesDao {
	
	private Connection con;

	public ReservacionesDao(Connection con) {
		super();
		this.con = con;
	}
	
	// Método para guardar una reserva en la base de datos	
	public void guardar(Reservaciones reservaciones) {
	    try {
	        // Definir la consulta SQL para insertar una nueva reserva
	        String sql = "INSERT INTO reservas (FechaEntrada, FechaSalida, Valor, FormaPago)"
	                + "VALUES (?,?,?,?)";

	        // Preparar el PreparedStatement con la consulta SQL y habilitar la recuperación de claves generadas
	        try (PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
	            // Establecer los valores de los parámetros en el PreparedStatement
	            pstm.setObject(1, reservaciones.getFechaE());
	            pstm.setObject(2, reservaciones.getFechaS());

	            // Obtener el valor numérico de la reserva directamente
	            double valorNumerico = Double.parseDouble(reservaciones.getValor());

	            pstm.setDouble(3, valorNumerico);
	            pstm.setString(4, reservaciones.getFormaPago());

	            // Ejecutar la consulta de inserción en la base de datos
	            pstm.executeUpdate();

	            // Obtener el ID generado y asignarlo a la reserva				
	            try (ResultSet rst = pstm.getGeneratedKeys()) {
	                while (rst.next()) {
	                    reservaciones.setId(rst.getInt(1));
	                }
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	// Método para obtener y mostrar las reservaciones
	public List<Reservaciones> mostrar() {
		List<Reservaciones> reservaciones = new ArrayList<>();
		try {
			// Consulta SQL para seleccionar campos de la tabla reservas
			String sql = "SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas";
			try(PreparedStatement pstm = con.prepareStatement(sql)) {
				pstm.execute();
				
				 // Llamada al método para transformar el resultado y llenar la lista de reservaciones
                transformarResultado(reservaciones, pstm);
			}
			return reservaciones;
		} catch (SQLException e) {
			throw new RuntimeException("Verifica Metodo MostrarReserv. ReservDAO" + e.getMessage(),e);
		}
	}
	
	// Método para buscar las reservaciones
		public List<Reservaciones> buscar(String id) {
	    List<Reservaciones> reservaciones = new ArrayList<>();
	    try {
	        // Consulta SQL para buscar reservas por ID
	        String sql = "SELECT id, fechaEntrada, fechaSalida, valor, formaPago FROM reservas WHERE id = ?";
	        try (PreparedStatement pstm = con.prepareStatement(sql)) {
	            pstm.setString(1, id);
	            pstm.execute();
	            
	            // Llena la lista de reservaciones con los resultados de la consulta
	            transformarResultado(reservaciones, pstm);
	        }
	        return reservaciones;
	    } catch (SQLException e) {
	        throw new RuntimeException("Verifica metodo Buscar ReservDAO" + e.getMessage(), e);
	    }
	}
	
	
	// Método para transformar el resultado de la consulta en objetos Reservaciones
	private void transformarResultado(List<Reservaciones> reservaciones, PreparedStatement pstm) throws SQLException {
		try(ResultSet rst = pstm.getResultSet()) {
			while(rst.next()) {
				int id = rst.getInt("id");
				LocalDate fechaE = rst.getDate("fechaEntrada").toLocalDate().plusDays(1);
				LocalDate fechaS = rst.getDate("fechaSalida").toLocalDate().plusDays(1);
				String valor = rst.getString("valor");
				String formaPago = rst.getString("formaPago");
				
				Reservaciones producto = new Reservaciones(id, fechaE, fechaS, valor, formaPago);
				reservaciones.add(producto);
				
			}
		}
	}
}
