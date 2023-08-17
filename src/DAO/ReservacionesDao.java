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
	
	public void guardar(Reservaciones reservaciones) {
		try {
			String sql = "INSERT INTO reservas (FechaEntrada, FechaSalida, Valor, FormaPago)"
					+ "VALUES (?,?,?,)";
			try(PreparedStatement pstm = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
				pstm.setObject(1, reservaciones.getFechaE());
				pstm.setObject(2, reservaciones.getFechaS());
				pstm.setString(3, reservaciones.getValor());
				pstm.setString(4, reservaciones.getFormaPago());
				pstm.executeUpdate();
				
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
