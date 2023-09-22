package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import DAO.HuespedesDao;
import Modelo.RegHuespedes;
import factory.ConnectionFactory;

public class HuespedesController {
	
	private HuespedesDao huespedesDao;
	
	public HuespedesController() {
		Connection con = new ConnectionFactory().conectaFactory();
		this.huespedesDao = new HuespedesDao(con);
	}
	
	public void guardar(RegHuespedes regHuespedes) {
		this.huespedesDao.guardar(regHuespedes);
	}
	
	public List<RegHuespedes> mostrarHuespedes() {
		return this.huespedesDao.mostrar();
	}
	
	public List<RegHuespedes> buscarHuespedes(String id) {
		return this.huespedesDao.buscarId(id);
	}
	
	public void ActualizarHuesp(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		this.huespedesDao.ActualizarHuesp(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void Eliminar(Integer idRererva) {
		this.huespedesDao.Eliminar(idRererva);
	}
	
}
