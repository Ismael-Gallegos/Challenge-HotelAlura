package controller;

import java.sql.Connection;

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

}
