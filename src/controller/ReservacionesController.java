package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.ReservacionesDao;
import Modelo.Reservaciones;
import factory.ConnectionFactory;

public class ReservacionesController {
	
	private ReservacionesDao reservacionDao;

	public ReservacionesController(ReservacionesDao reservacionDao) {
		Connection con = new ConnectionFactory().conectaFactory();
		this.reservacionDao = new ReservacionesDao(con);
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
	public List<Reservaciones> buscar (){
		return this.reservacionDao.buscar();
	}
	// Métod de búsqueda de las Reservaciones.
	public List<Reservaciones> buscarId (String id){
		return this.reservacionDao.buscariD(id);
	}
	// Métod para Actualizar las Reservaciones.
	public void actualizarReservaciones(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago, Integer id) {
		this.reservacionDao.Actualizar(fechaE, fechaS, valor, formaPago, id);
	}
	// Métod para Elinimar las Reservaciones.
	public void Eliminar(Integer id) {
		this.reservacionDao.Eliminar(id);
	}

}
