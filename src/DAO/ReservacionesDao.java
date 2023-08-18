package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
