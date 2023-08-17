package controller;

import DAO.ReservacionesDao;
import Modelo.Reservaciones;

public class ReservacionesController {
	
	private ReservacionesDao reservacionDao;

	public ReservacionesController(ReservacionesDao reservacionDao) {
		super();
		this.reservacionDao = reservacionDao;
	}
	
	// Constructor adicional sin parámetros
	public ReservacionesController() {
		super();
		this.reservacionDao = reservacionDao;
	}

	// Método para guardar una reserva utilizando el DAO
	public void guardar(Reservaciones reservaciones) {
		this.reservacionDao.guardar(reservaciones);
	}
}
