package controller;

import java.sql.Connection;
import java.util.List;

import DAO.ReservacionesDao;
import Modelo.Reservaciones;
import factory.ConnectionFactory;

public class ReservacionesController {
	
	private ReservacionesDao reservacionDao;

	public ReservacionesController(ReservacionesDao reservacionDao) {
		super();
		this.reservacionDao = reservacionDao;
	}
	
	// Constructor adicional sin parámetros
	public ReservacionesController() {
		Connection con = new ConnectionFactory().conectaFactory();
		this.reservacionDao = new ReservacionesDao(con);
	}

	// Método para guardar una reserva utilizando el DAO
	public void guardar(Reservaciones reservaciones) {
		this.reservacionDao.guardar(reservaciones);
	}
	// Método para obtener una lista de reservaciones desde la base de datos.
	public List<Reservaciones> mostrar (){
		return this.reservacionDao.mostrar();
	}
	
}
